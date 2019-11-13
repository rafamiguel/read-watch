package estrada.leon.rafael.readwatch.estudiante.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import estrada.leon.rafael.readwatch.R;
import estrada.leon.rafael.readwatch.estudiante.adapter.TemasPropuestosAdapter;
import estrada.leon.rafael.readwatch.estudiante.dialog.DialogIngresarPropuestaTema;
import estrada.leon.rafael.readwatch.estudiante.pojo.Materias;
import estrada.leon.rafael.readwatch.estudiante.pojo.Votos;
import estrada.leon.rafael.readwatch.general.pojo.Sesion;

public class TemasPropuestos extends Fragment {
    List<estrada.leon.rafael.readwatch.estudiante.pojo.TemasPropuestos> datos;
    ListView lvTemasPropuestos;
    FloatingActionButton fabTemaNuevo;
    Button btnVotarQuitar;
    DatabaseReference rootReference;
    CheckBox cbSeleccionado;
    TextView lblSeleccionado;
    ProgressDialog progreso;
    Context contexto;
    private List<Materias> listMaterias;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public TemasPropuestos() {
    }
    public void cargarDatos(){
        int votos=0;
        for(int i=0;i<datos.size();i++){
            votos+=datos.get(i).getVotos();
        }
        TemasPropuestosAdapter temasPropuestosAdapter=new TemasPropuestosAdapter(contexto,R.layout.tema_propuesto,datos,votos);
        lvTemasPropuestos.setAdapter(temasPropuestosAdapter);

        rootReference.child("votoTema").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Votos voto;
                CheckBox cb;
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    voto = snapshot.getValue(Votos.class);
                    if(voto.getIdUsuario() == Sesion.getSesion().getId()) {
                        for (int x = 0; x < lvTemasPropuestos.getChildCount(); x++) {
                            cb = lvTemasPropuestos.getChildAt(x).findViewById(R.id.cbTemaPropuesto);
                            cb.setEnabled(false);
                        }
                        btnVotarQuitar.setVisibility(View.GONE);
                        Toast.makeText(contexto, "Ya has votado por un tema esta semana.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                btnVotarQuitar.setVisibility(View.VISIBLE);
                Toast.makeText(contexto, "Puedes votar por un tema a la semana.", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public static TemasPropuestos newInstance(String param1, String param2) {
        TemasPropuestos fragment = new TemasPropuestos();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootReference = FirebaseDatabase.getInstance().getReference();
        View view;
        view= inflater.inflate(R.layout.fragment_temas_propuestos, container, false);
        lvTemasPropuestos=view.findViewById(R.id.lvTemasPropuestos);
        lvTemasPropuestos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cbSeleccionado = view.findViewById(R.id.cbMateriaPropuesta);
                lblSeleccionado = view.findViewById(R.id.lblMateriaPropuesta);
            }
        });
        btnVotarQuitar = view.findViewById(R.id.btnVotarQuitar);
        btnVotarQuitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int x = 0; x<lvTemasPropuestos.getChildCount();x++){
                    cbSeleccionado = lvTemasPropuestos.getChildAt(x).findViewById(R.id.cbTemaPropuesto);
                    if(cbSeleccionado.isChecked()){
                        lblSeleccionado = lvTemasPropuestos.getChildAt(x).findViewById(R.id.lblTemaPropuesto);
                        votar(lblSeleccionado.getText().toString());
                    }
                }
            }
        });
        fabTemaNuevo = view.findViewById(R.id.fabTemaNuevo);
        fabTemaNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogIngresarPropuestaTema nuevo = new DialogIngresarPropuestaTema();
                nuevo.show(getActivity().getSupportFragmentManager(), "ejemplo");
            }
        });
        cargarMaterias();
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
        contexto = context;
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
        DatabaseReference votosRef = rootReference.child("votoTema");
        int idUsuario = Sesion.getSesion().getId();
        Votos vote = new Votos(idUsuario,nombre);
        Map<String, Object> voto = new HashMap<>();
        voto.put("idUsuario",vote.getIdUsuario());
        voto.put("nombre",vote.getNombre());
        votosRef.push().setValue(voto);

        rootReference.child("tema").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                estrada.leon.rafael.readwatch.estudiante.pojo.TemasPropuestos tema = null;
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    tema = snapshot.getValue(estrada.leon.rafael.readwatch.estudiante.pojo.TemasPropuestos.class);
                    if(tema.getNombre().equals(nombre)){
                        tema.setVotos(tema.getVotos()+1);
                        DatabaseReference votoNuevoRef = rootReference.child("tema");
                        Map<String, Object> nuevoVoto = new HashMap<>();
                        nuevoVoto.put(snapshot.getKey(),tema);
                        votoNuevoRef.updateChildren(nuevoVoto);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void cargarMaterias(){
        JsonObjectRequest jsonObjectRequest;
        RequestQueue request;
        request= Volley.newRequestQueue(contexto);
        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();
        String url;
        url = "https://readandwatch.herokuapp.com/php/cargarMaterias.php";
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                listMaterias = new ArrayList<>();
                JSONArray json;
                JSONObject jsonObject=null;
                Materias materia;
                json = response.optJSONArray("usuario");
                String nombre,rutaImagen;
                int idMateria, votos,idUsuario;
                try {
                    for(int i=0;i<json.length();i++){
                        jsonObject=json.getJSONObject(i);
                        idMateria=jsonObject.optInt("idMateria");
                        nombre=jsonObject.optString("nombre");
                        rutaImagen=jsonObject.optString("rutaImagen");
                        votos=jsonObject.optInt("votos");
                        idUsuario=jsonObject.optInt("idUsuario");
                        materia=new Materias(idMateria,rutaImagen,nombre);
                        listMaterias.add(materia);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progreso.hide();
                cargarTemas();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                progreso.hide();
            }
        });
        request.add(jsonObjectRequest);
    }

    public void cargarTemas(){
        rootReference.child("tema").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                SharedPreferences preferences = contexto.getSharedPreferences("Materia", Context.MODE_PRIVATE);
                int idMateria = preferences.getInt("materia", 1);

                preferences = contexto.getSharedPreferences("Semestre", Context.MODE_PRIVATE);
                int semestre = preferences.getInt("semestre", 1);

                String materia = "";
                for(int i=0;i<listMaterias.size();i++){
                    if(listMaterias.get(i).getIdMateria()==idMateria){
                        materia = listMaterias.get(i).getNombre();
                        break;
                    }
                }
                datos=new ArrayList<>();
                estrada.leon.rafael.readwatch.estudiante.pojo.TemasPropuestos tema;
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    tema = snapshot.getValue(estrada.leon.rafael.readwatch.estudiante.pojo.TemasPropuestos.class);
                    if(tema.getMateria().equals(materia) && semestre == tema.getSemestre()) {
                        datos.add(tema);
                    }
                }
                cargarDatos();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
