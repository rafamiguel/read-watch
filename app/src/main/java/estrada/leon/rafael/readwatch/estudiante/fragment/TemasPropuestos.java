package estrada.leon.rafael.readwatch.estudiante.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import estrada.leon.rafael.readwatch.R;
import estrada.leon.rafael.readwatch.estudiante.adapter.TemasPropuestosAdapter;

public class TemasPropuestos extends Fragment {
    estrada.leon.rafael.readwatch.estudiante.pojo.TemasPropuestos[] temasPropuestos;
    ListView lvTemasPropuestos;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public TemasPropuestos() {
    }
    public void cargarDatos(){
        temasPropuestos=new estrada.leon.rafael.readwatch.estudiante.pojo.TemasPropuestos[]{
                new estrada.leon.rafael.readwatch.estudiante.pojo.TemasPropuestos(0,1,"Química"),
                new estrada.leon.rafael.readwatch.estudiante.pojo.TemasPropuestos(1,10,"Electrónica"),
                new estrada.leon.rafael.readwatch.estudiante.pojo.TemasPropuestos(2,2,"Bases de datos"),
                new estrada.leon.rafael.readwatch.estudiante.pojo.TemasPropuestos(3,6,"Geografía"),
                new estrada.leon.rafael.readwatch.estudiante.pojo.TemasPropuestos(4,7,"Historia"),
                new estrada.leon.rafael.readwatch.estudiante.pojo.TemasPropuestos(5,2,"Biología"),
                new estrada.leon.rafael.readwatch.estudiante.pojo.TemasPropuestos(6,4,"Física"),
                new estrada.leon.rafael.readwatch.estudiante.pojo.TemasPropuestos(8,2,"Literatura"),
                new estrada.leon.rafael.readwatch.estudiante.pojo.TemasPropuestos(7,8,"Astronomía"),
                new estrada.leon.rafael.readwatch.estudiante.pojo.TemasPropuestos(9,3,"Robótica"),
                new estrada.leon.rafael.readwatch.estudiante.pojo.TemasPropuestos(10,5,"Arte"),
        };
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
        for(int i=0;i<temasPropuestos.length;i++){
            votos+=temasPropuestos[i].getVotos();
        }
        TemasPropuestosAdapter temasPropuestosAdapter=new TemasPropuestosAdapter(getContext(),R.layout.tema_propuesto,temasPropuestos,votos);
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
