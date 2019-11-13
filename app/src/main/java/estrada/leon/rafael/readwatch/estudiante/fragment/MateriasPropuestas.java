package estrada.leon.rafael.readwatch.estudiante.fragment;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import estrada.leon.rafael.readwatch.estudiante.dialog.DialogInsertarMateria;
import estrada.leon.rafael.readwatch.R;
import estrada.leon.rafael.readwatch.estudiante.adapter.MateriasPropuestasAdapter;
import estrada.leon.rafael.readwatch.estudiante.pojo.Votos;
import estrada.leon.rafael.readwatch.general.pojo.Sesion;

public class MateriasPropuestas extends Fragment {
    estrada.leon.rafael.readwatch.estudiante.pojo.MateriasPropuestas[] materiasPropuestas;
    private List<estrada.leon.rafael.readwatch.estudiante.pojo.MateriasPropuestas> listMaterias;
    ListView lvMateriasPropuestas;
    FloatingActionButton btnSubirPropuesta;
    DatabaseReference rootReference;
    Button btnVotarQuitar;
    CheckBox cbSeleccionado;
    TextView lblSeleccionado;
    MateriasPropuestasAdapter materiasPropuestasAdapter;
    private OnFragmentInteractionListener mListener;
    Context contexto;
    public MateriasPropuestas() {
    }
    public void cargarDatos(){
        int votos=0;
        materiasPropuestas=new estrada.leon.rafael.readwatch.estudiante.pojo.MateriasPropuestas[listMaterias.size()];
        for(int i=0;i<listMaterias.size();i++){
            votos+=listMaterias.get(i).getVotos();
            materiasPropuestas[i]  = new estrada.leon.rafael.readwatch.estudiante.pojo.MateriasPropuestas(listMaterias.get(i).getVotos(),listMaterias.get(i).getNombre(),listMaterias.get(i).getIdUsuario());
        }
        materiasPropuestasAdapter =new MateriasPropuestasAdapter(contexto,R.layout.materia_propuesta,materiasPropuestas,votos);
        lvMateriasPropuestas.setAdapter(materiasPropuestasAdapter);

        rootReference.child("votoMateria").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Votos voto;
                CheckBox cb;
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    voto = snapshot.getValue(Votos.class);
                    if (voto.getIdUsuario() == Sesion.getSesion().getId()) {
                        for (int x = 0; x < lvMateriasPropuestas.getChildCount(); x++) {
                            cb = lvMateriasPropuestas.getChildAt(x).findViewById(R.id.cbMateriaPropuesta);
                            btnVotarQuitar.setVisibility(View.GONE);
                            Toast.makeText(contexto, "Ya has votado por una materia esta semana.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                }
                btnVotarQuitar.setVisibility(View.VISIBLE);
                Toast.makeText(contexto, "Puedes votar por una materia a la semana.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        rootReference = FirebaseDatabase.getInstance().getReference();
        View view;

        view= inflater.inflate(R.layout.fragment_materias_propuestas, container, false);
        btnVotarQuitar = view.findViewById(R.id.btnVotarQuitar);
        btnVotarQuitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int x = 0; x<lvMateriasPropuestas.getChildCount();x++){
                    cbSeleccionado = lvMateriasPropuestas.getChildAt(x).findViewById(R.id.cbMateriaPropuesta);
                    if(cbSeleccionado.isChecked()){
                        lblSeleccionado = lvMateriasPropuestas.getChildAt(x).findViewById(R.id.lblMateriaPropuesta);
                        votar(lblSeleccionado.getText().toString());
                    }
                }
            }
        });
        btnSubirPropuesta = view.findViewById(R.id.fabNuevaPregunta);
        btnSubirPropuesta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInsertarMateria nuevo = new DialogInsertarMateria();
                nuevo.show(getActivity().getSupportFragmentManager(), "ejemplo");
            }
        });
        lvMateriasPropuestas=view.findViewById(R.id.lvMateriasPropuestas);
        lvMateriasPropuestas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                cbSeleccionado = view.findViewById(R.id.cbMateriaPropuesta);
                lblSeleccionado = view.findViewById(R.id.lblMateriaPropuesta);
            }
        });
        rootReference.child("materia").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listMaterias=new ArrayList<>();
                estrada.leon.rafael.readwatch.estudiante.pojo.MateriasPropuestas materia;
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    materia = snapshot.getValue(estrada.leon.rafael.readwatch.estudiante.pojo.MateriasPropuestas.class);
                    listMaterias.add(materia);
                }
                cargarDatos();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        return view;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        contexto=context;
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void votar(final String nombre){
        rootReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference votosRef = rootReference.child("votoMateria");
        int idUsuario = Sesion.getSesion().getId();
        Votos vote = new Votos(idUsuario,nombre);
        Map<String, Object> voto = new HashMap<>();
        voto.put("idUsuario",vote.getIdUsuario());
        voto.put("nombre",vote.getNombre());
        votosRef.push().setValue(voto);

        rootReference.child("materia").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                estrada.leon.rafael.readwatch.estudiante.pojo.MateriasPropuestas materia = null;
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    materia = snapshot.getValue(estrada.leon.rafael.readwatch.estudiante.pojo.MateriasPropuestas.class);
                    if(materia.getNombre().equals(nombre)){
                        materia.setVotos(materia.getVotos()+1);
                        DatabaseReference votoNuevoRef = rootReference.child("materia");
                        Map<String, Object> nuevoVoto = new HashMap<>();
                        nuevoVoto.put(snapshot.getKey(),materia);
                        votoNuevoRef.updateChildren(nuevoVoto);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void quitarVoto(){
        String idUsuario = String.valueOf(Sesion.getSesion().getId());
        rootReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference votosRef = rootReference.child("votoMateria");
        DatabaseReference votoRef = rootReference.child(idUsuario);
        votosRef.setValue(null);
    }
}
