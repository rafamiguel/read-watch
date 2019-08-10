package estrada.leon.rafael.readwatch.estudiante.fragment;

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

import java.util.ArrayList;
import java.util.List;

import estrada.leon.rafael.readwatch.estudiante.adapter.HistorialAdapter;
import estrada.leon.rafael.readwatch.R;


public class Historial extends Fragment {
    private List<estrada.leon.rafael.readwatch.estudiante.pojo.Historial> list;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        HistorialAdapter historialAdapter;
        RecyclerView recyclerHistorial;
        View vista;
        vista = inflater.inflate(R.layout.fragment_historial, container, false);
        recyclerHistorial=vista.findViewById(R.id.recyclerHistorial);
        recyclerHistorial.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        cargarDatos();
        historialAdapter=new HistorialAdapter(getContext(),list);
        recyclerHistorial.setAdapter(historialAdapter);

        return vista;
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
        void onFragmentInteraction(Uri uri);
    }
}
