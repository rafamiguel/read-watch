package estrada.leon.rafael.readwatch.estudiante.fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import estrada.leon.rafael.readwatch.estudiante.adapter.TemaLibreAdapter;
import estrada.leon.rafael.readwatch.estudiante.pojo.TemaLibre;
import estrada.leon.rafael.readwatch.estudiante.interfaces.iComunicacionFragments;
import estrada.leon.rafael.readwatch.R;

public class PreguntasTemaLibre extends Fragment implements TemaLibreAdapter.OnTemaListener {
    iComunicacionFragments interfaceFragments;
    FloatingActionButton fabNuevaPregunta;
    Activity actividad;
    RecyclerView recyclerTemas;
    View vista;
    TemaLibreAdapter adapter;
    List<TemaLibre> temaLibreList = new ArrayList<>();

    private OnFragmentInteractionListener mListener;

    public PreguntasTemaLibre() {
    }
    public void cargarDatos(){
        temaLibreList = new ArrayList<>();
        temaLibreList.add(new TemaLibre("Me urge!!!", "Gallina o huevo"));
        temaLibreList.add(new TemaLibre("Es tarea", "Bueno o malo??"));
        temaLibreList.add(new TemaLibre("salu2", "¿Cuál es el sentido de la vida?"));
        temaLibreList.add(new TemaLibre("jaja que pregunton soi", "¿Habrá vida en otros planetas?"));
        temaLibreList.add(new TemaLibre("ola","¿Cómo puede ser infinito el Universo?"));
        temaLibreList.add(new TemaLibre("me encontre esta pregunta en yahoo respuestas jaja",
                "¿Ayúdenme porfavor, la suma 11111111-1111111+111111.......... Así hasta llegar al uno," +
                        " cuál sería el resultado? Y cómo sacarían la respuesta? "));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista=inflater.inflate(R.layout.fragment_preguntas_tema_libre, container, false);
        fabNuevaPregunta = vista.findViewById(R.id.fabNuevaPregunta2);
      fabNuevaPregunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getContext(), "Nueva Pregunta", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        recyclerTemas =  vista.findViewById(R.id.recyclerTemas);
        recyclerTemas.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        cargarDatos();
        adapter = new TemaLibreAdapter(getContext(),temaLibreList, this);
        recyclerTemas.setAdapter(adapter);
        return vista;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            actividad= (Activity) context;
            interfaceFragments=(iComunicacionFragments)actividad;
        }
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

    @Override
    public void onTemaClick(int position, List<TemaLibre> temaLibreList, Toast toast) {
        interfaceFragments.onClickTemasLibresHolder(toast);
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
