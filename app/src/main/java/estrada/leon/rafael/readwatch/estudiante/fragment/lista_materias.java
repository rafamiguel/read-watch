package estrada.leon.rafael.readwatch.estudiante.fragment;

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
import android.widget.ListView;

import java.util.List;

import estrada.leon.rafael.readwatch.R;
import estrada.leon.rafael.readwatch.estudiante.adapter.ListaMateriasAdapter;
import estrada.leon.rafael.readwatch.estudiante.pojo.Materias;


public class lista_materias extends Fragment {
    Materias[] list;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public lista_materias() {
    }

    public void cargarDatos(){
        list=new Materias[]{
                new Materias("@drawable/quimica","Química"),
                new Materias("@drawable/electronica","Electrónica"),
                new Materias("@drawable/bases_datos","Bases de datos"),
                new Materias("@drawable/geografia","Geografía"),
                new Materias("@drawable/historia","Historia"),
                new Materias("@drawable/biologia","Biología"),
                new Materias("@drawable/fisica","Física"),
                new Materias("@drawable/literatura","Literatura"),
                new Materias("@drawable/astronomia","Astronomía"),
        };
    }

    public static lista_materias newInstance(String param1, String param2) {
        lista_materias fragment = new lista_materias();
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
        ListView lvListaMaterias;
        View view;
        view = inflater.inflate(R.layout.fragment_lista_materias, container, false);
        lvListaMaterias = view.findViewById(R.id.lvListaMaterias);
        cargarDatos();
        ListaMateriasAdapter listaMateriasAdapter=new ListaMateriasAdapter(getContext(),R.layout.lista_materias,list);
        lvListaMaterias.setAdapter(listaMateriasAdapter);
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
}
