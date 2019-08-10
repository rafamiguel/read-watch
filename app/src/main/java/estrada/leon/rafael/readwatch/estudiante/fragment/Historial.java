package estrada.leon.rafael.readwatch.estudiante.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import estrada.leon.rafael.readwatch.estudiante.adapter.HistorialAdapter;
import estrada.leon.rafael.readwatch.R;


public class Historial extends Fragment {
    View vista;
    List<estrada.leon.rafael.readwatch.estudiante.pojo.Historial> list;
    RecyclerView recyclerHistorial;
    HistorialAdapter historialAdapter;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public void cargarDatos(){
        list=new ArrayList<>();
        for(int i=0;i<11;i++){
            list.add(new estrada.leon.rafael.readwatch.estudiante.pojo.Historial(i,"comentario","Es spam","El comentario ha sido eliminado"));
            int imageResource = getContext().getResources().getIdentifier("drawable/doc",null,getContext().getPackageName());
            list.add(new estrada.leon.rafael.readwatch.estudiante.pojo.Historial(imageResource,"documento","No es apropiado al tema o materia","El documento ha sido eliminado"));
            imageResource = getContext().getResources().getIdentifier("drawable/miniatura",null,getContext().getPackageName());
            list.add(new estrada.leon.rafael.readwatch.estudiante.pojo.Historial(imageResource,"video","No es apropiado al tema o materia","El video ha sido eliminado"));
        }
    }
    public Historial() {
    }

    public static Historial newInstance(String param1, String param2) {
        Historial fragment = new Historial();
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
        vista = inflater.inflate(R.layout.fragment_historial, container, false);
        recyclerHistorial=vista.findViewById(R.id.recyclerHistorial);
        recyclerHistorial.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        cargarDatos();
        historialAdapter=new HistorialAdapter(getContext(),list);
        recyclerHistorial.setAdapter(historialAdapter);

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
