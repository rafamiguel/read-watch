package estrada.leon.rafael.readwatch.Estudiante.Fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import estrada.leon.rafael.readwatch.Estudiante.Adapter.MateriasAdapter;
import estrada.leon.rafael.readwatch.Estudiante.POJO.Materias;
import estrada.leon.rafael.readwatch.Interfaces.iComunicacionFragments;
import estrada.leon.rafael.readwatch.R;

public class ElegirMateria extends Fragment implements MateriasAdapter.OnMateriaListener {
    iComunicacionFragments interfaceFragments;
    View vista;
    Activity actividad;
    List<Materias> listMaterias,listMateriasPropuestas;
    RecyclerView recyclerMaterias,recyclerMateriasPropuestas;
    MateriasAdapter materiasAdapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ElegirMateria() {

    }

    public static ElegirMateria newInstance(String param1, String param2) {
        ElegirMateria fragment = new ElegirMateria();
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
        vista=inflater.inflate(R.layout.fragment_elegir_materia, container, false);
        recyclerMaterias=vista.findViewById(R.id.recyclerMaterias);
        recyclerMateriasPropuestas=vista.findViewById(R.id.recyclerMateriasPropuestas);
        recyclerMaterias.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        recyclerMateriasPropuestas.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        listMaterias=new ArrayList<>();
        listMaterias.add(new Materias("@drawable/espaniol"));
        listMaterias.add(new Materias("@drawable/matematicas"));
        listMaterias.add(new Materias("@drawable/ingles"));
        materiasAdapter= new MateriasAdapter(getContext(), listMaterias,this);
        recyclerMaterias.setAdapter(materiasAdapter);

        listMateriasPropuestas=new ArrayList<>();
        listMateriasPropuestas.add(new Materias("@drawable/programacion"));
        materiasAdapter= new MateriasAdapter(getContext(), listMateriasPropuestas,this);
        recyclerMateriasPropuestas.setAdapter(materiasAdapter);
        return vista;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
    public void onMateriaClick(int position, List<Materias> lista) {
        if(lista.equals(listMaterias)){
            Toast.makeText(actividad, "Tocaste el elemento: "+listMaterias.get(position).getRutaImagen(), Toast.LENGTH_SHORT).show();
            interfaceFragments.seleccionarSemestre();
        }else if(lista.equals(listMateriasPropuestas)){
            Toast.makeText(actividad, "Tocaste el elemento: "+listMateriasPropuestas.get(position).getRutaImagen(), Toast.LENGTH_SHORT).show();
            interfaceFragments.seleccionarSemestre();
        }

    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
