package estrada.leon.rafael.readwatch.estudiante.fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import estrada.leon.rafael.readwatch.estudiante.adapter.MateriasAdapter;
import estrada.leon.rafael.readwatch.estudiante.pojo.Materias;
import estrada.leon.rafael.readwatch.estudiante.interfaces.iComunicacionFragments;
import estrada.leon.rafael.readwatch.R;

public class ElegirMateria extends Fragment implements MateriasAdapter.OnMateriaListener {
    private iComunicacionFragments interfaceFragments;
    private Activity actividad;
    private List<Materias> listMaterias,listMateriasPropuestas;

    private OnFragmentInteractionListener mListener;

    public ElegirMateria() {
    }
    public void cargarDatos(){
        listMaterias=new ArrayList<>();
        listMateriasPropuestas = new ArrayList<>();

        for (int i=0;i<1;i++) {
            listMaterias.add(new Materias("@drawable/espaniol","español"));
            listMaterias.add(new Materias("@drawable/matematicas","matematicas"));
            listMaterias.add(new Materias("@drawable/ingles","ingles"));

            listMateriasPropuestas.add(new Materias("@drawable/programacion","programacion"));
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerMaterias,recyclerMateriasPropuestas;
        TextView lblTemaInexistente;
        MateriasAdapter materiasAdapter;
        View vista;
        vista=inflater.inflate(R.layout.fragment_elegir_materia, container, false);
        lblTemaInexistente=vista.findViewById(R.id.lblTemaInexistente);
        lblTemaInexistente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfaceFragments.onClickProponerMateria();
            }
        });
        recyclerMaterias=vista.findViewById(R.id.recyclerMaterias);
        recyclerMateriasPropuestas=vista.findViewById(R.id.recyclerMateriasPropuestas);
        recyclerMaterias.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        recyclerMateriasPropuestas.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        cargarDatos();
        materiasAdapter = new MateriasAdapter(getContext(), listMaterias, this);
        recyclerMaterias.setAdapter(materiasAdapter);
        materiasAdapter = new MateriasAdapter(getContext(), listMateriasPropuestas, this);
        recyclerMateriasPropuestas.setAdapter(materiasAdapter);
        return vista;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            actividad= (Activity) context;
            interfaceFragments=(iComunicacionFragments)actividad;
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
    public void onMateriaClick(int position, List<Materias> lista) {
        if(lista.equals(listMaterias)){
            Toast.makeText(actividad, "Tocaste el elemento: "+listMaterias.get(position).getRutaImagen(), Toast.LENGTH_SHORT).show();
            //interfaceFragments.seleccionarSemestre();
        }else if(lista.equals(listMateriasPropuestas)){
            Toast.makeText(actividad, "Tocaste el elemento: "+listMateriasPropuestas.get(position).getRutaImagen(), Toast.LENGTH_SHORT).show();
            //interfaceFragments.seleccionarSemestre();
        }

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
