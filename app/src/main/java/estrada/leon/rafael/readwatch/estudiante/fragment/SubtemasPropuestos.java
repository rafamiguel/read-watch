package estrada.leon.rafael.readwatch.estudiante.fragment;

        import android.app.ProgressDialog;
        import android.content.Context;
        import android.content.SharedPreferences;
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
        import estrada.leon.rafael.readwatch.estudiante.adapter.MateriasPropuestasAdapter;
        import estrada.leon.rafael.readwatch.estudiante.adapter.SubtemasPropuestosAdapter;
        import estrada.leon.rafael.readwatch.estudiante.adapter.TemasAdapter;
        import estrada.leon.rafael.readwatch.estudiante.adapter.TemasPropuestosAdapter;
        import estrada.leon.rafael.readwatch.estudiante.dialog.DialogIngresarPropuesta;
        import estrada.leon.rafael.readwatch.estudiante.dialog.DialogInsertarMateria;
        import estrada.leon.rafael.readwatch.estudiante.interfaces.Item;
        import estrada.leon.rafael.readwatch.estudiante.pojo.Materias;
        import estrada.leon.rafael.readwatch.estudiante.pojo.Subtemas;
        import estrada.leon.rafael.readwatch.estudiante.pojo.Votos;
        import estrada.leon.rafael.readwatch.general.pojo.Sesion;

public class SubtemasPropuestos extends Fragment implements TemasAdapter.OnTemasListener {
    Subtemas[] subtemasPropuestos;
    private List<Subtemas> listaSubtemas;
    ListView lvSubtemasPropuestos;
    FloatingActionButton btnSubirPropuesta;
    DatabaseReference rootReference;
    Button btnVotarQuitar;
    CheckBox cbSeleccionado;
    TextView lblSeleccionado;
    SubtemasPropuestosAdapter subtemasPropuestosAdapter;
    private MateriasPropuestas.OnFragmentInteractionListener mListener;
    Context contexto;
    public SubtemasPropuestos() {
    }
    public void cargarDatos(){
        int votos=0;
        subtemasPropuestos=new Subtemas[listaSubtemas.size()];
        for(int i=0;i<listaSubtemas.size();i++){
            votos+=listaSubtemas.get(i).getVotos();
            subtemasPropuestos[i]  = new Subtemas(listaSubtemas.get(i).getNombre(),listaSubtemas.get(i).getIdSubtema(),listaSubtemas.get(i).getTema());
        }
        subtemasPropuestosAdapter =new SubtemasPropuestosAdapter(contexto,R.layout.fragment_subtemas_propuestos,subtemasPropuestos,votos);
        lvSubtemasPropuestos.setAdapter(subtemasPropuestosAdapter);

        rootReference.child("votoSubtema").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Votos voto;
                CheckBox cb;
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    voto = snapshot.getValue(Votos.class);
                    if(voto.getIdUsuario() == Sesion.getSesion().getId()) {
                        for (int x = 0; x < lvSubtemasPropuestos.getChildCount(); x++) {
                            cb = lvSubtemasPropuestos.getChildAt(x).findViewById(R.id.cbTemaPropuesto);
                            cb.setEnabled(false);
                        }
                        btnVotarQuitar.setVisibility(View.GONE);
                        return;
                    }
                }
                btnVotarQuitar.setVisibility(View.VISIBLE);
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

        view= inflater.inflate(R.layout.fragment_subtemas_propuestos, container, false);
        btnVotarQuitar = view.findViewById(R.id.btnVotarQuitar);
        btnVotarQuitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int x = 0; x<lvSubtemasPropuestos.getChildCount();x++){
                    cbSeleccionado = lvSubtemasPropuestos.getChildAt(x).findViewById(R.id.cbTemaPropuesto);
                    if(cbSeleccionado.isChecked()){
                        lblSeleccionado = lvSubtemasPropuestos.getChildAt(x).findViewById(R.id.lblTemaPropuesto);
                        votar(lblSeleccionado.getText().toString());
                    }
                }
            }
        });
        btnSubirPropuesta = view.findViewById(R.id.fabTemaNuevo);
        btnSubirPropuesta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogIngresarPropuesta nuevo = new DialogIngresarPropuesta();
                nuevo.show(getActivity().getSupportFragmentManager(), "ejemplo");
            }
        });
        lvSubtemasPropuestos=view.findViewById(R.id.lvTemasPropuestos);
        lvSubtemasPropuestos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                cbSeleccionado = view.findViewById(R.id.cbTemaPropuesto);
                lblSeleccionado = view.findViewById(R.id.lblTemaPropuesto);
            }
        });
        rootReference.child("subtema").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listaSubtemas=new ArrayList<>();
                Subtemas materia;
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    materia = snapshot.getValue(Subtemas.class);
                    listaSubtemas.add(materia);
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
        if (context instanceof MateriasPropuestas.OnFragmentInteractionListener) {
            mListener = (MateriasPropuestas.OnFragmentInteractionListener) context;
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

    @Override
    public void onTemaClick(int position, List<Item> lista) {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void votar(final String nombre){
        rootReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference votosRef = rootReference.child("votoSubtema");
        int idUsuario = Sesion.getSesion().getId();
        SharedPreferences preferences = contexto.getSharedPreferences("materia", Context.MODE_PRIVATE);
        String nombreMateria = preferences.getString("nombre", "NaN");

        SharedPreferences preference2s = contexto.getSharedPreferences("tema", Context.MODE_PRIVATE);
        String nombreTema = preference2s.getString("nombre", "NaN");

        Votos vote = new Votos(idUsuario,nombre);
        Map<String, Object> voto = new HashMap<>();
        voto.put("idUsuario",vote.getIdUsuario());
        voto.put("nombre",vote.getNombre());
        voto.put("materia",nombreMateria);
        voto.put("tema",nombreTema);
        votosRef.push().setValue(voto);

        rootReference.child("subtema").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Subtemas subtema = null;
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    subtema = snapshot.getValue(Subtemas.class);
                    if(subtema.getNombre().equals(nombre)){
                        subtema.setVotos(subtema.getVotos()+1);
                        DatabaseReference votoNuevoRef = rootReference.child("subtema");
                        Map<String, Object> nuevoVoto = new HashMap<>();
                        nuevoVoto.put(snapshot.getKey(),subtema);
                        votoNuevoRef.updateChildren(nuevoVoto);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
