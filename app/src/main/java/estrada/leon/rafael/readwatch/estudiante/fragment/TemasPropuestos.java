package estrada.leon.rafael.readwatch.estudiante.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import estrada.leon.rafael.readwatch.R;
import estrada.leon.rafael.readwatch.estudiante.adapter.TemasPropuestosAdapter;

public class TemasPropuestos extends Fragment {
    List<estrada.leon.rafael.readwatch.estudiante.pojo.TemasPropuestos> datos;;
    ListView lvTemasPropuestos;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public TemasPropuestos() {
    }
    public void cargarDatos(){
        datos=new ArrayList<>();
        datos.add(new estrada.leon.rafael.readwatch.estudiante.pojo.TemasPropuestos(0,1,"Quimica"));
        datos.add(new estrada.leon.rafael.readwatch.estudiante.pojo.TemasPropuestos(1,10,"Electrónica"));
        datos.add(new estrada.leon.rafael.readwatch.estudiante.pojo.TemasPropuestos(2,2,"Bases de datos"));
        datos.add(new estrada.leon.rafael.readwatch.estudiante.pojo.TemasPropuestos(3,6,"Geografía"));
        datos.add(new estrada.leon.rafael.readwatch.estudiante.pojo.TemasPropuestos(4,7,"Historia"));
        datos.add(new estrada.leon.rafael.readwatch.estudiante.pojo.TemasPropuestos(5,2,"Biología"));
        datos.add(new estrada.leon.rafael.readwatch.estudiante.pojo.TemasPropuestos(6,4,"Física"));
        datos.add(new estrada.leon.rafael.readwatch.estudiante.pojo.TemasPropuestos(7,2,"Literatura"));
        datos.add(new estrada.leon.rafael.readwatch.estudiante.pojo.TemasPropuestos(8,8,"Astronomía"));
        datos.add(new estrada.leon.rafael.readwatch.estudiante.pojo.TemasPropuestos(9,3,"Robótica"));
        datos.add(new estrada.leon.rafael.readwatch.estudiante.pojo.TemasPropuestos(10,5,"Arte"));
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
        View view;
        int votos=0;
        view= inflater.inflate(R.layout.fragment_temas_propuestos, container, false);
        lvTemasPropuestos=view.findViewById(R.id.lvTemasPropuestos);
        cargarDatos();
        for(int i=0;i<datos.size();i++){
            votos+=datos.get(i).getVotos();
        }
        TemasPropuestosAdapter temasPropuestosAdapter=new TemasPropuestosAdapter(getContext(),R.layout.tema_propuesto,datos,votos);
        lvTemasPropuestos.setAdapter(temasPropuestosAdapter);
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
