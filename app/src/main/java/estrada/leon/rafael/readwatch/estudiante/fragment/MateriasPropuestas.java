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
import estrada.leon.rafael.readwatch.estudiante.adapter.MateriasPropuestasAdapter;

public class MateriasPropuestas extends Fragment {
    estrada.leon.rafael.readwatch.estudiante.pojo.MateriasPropuestas[] materiasPropuestas;
    ListView lvMateriasPropuestas;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public MateriasPropuestas() {
    }
    public void cargarDatos(){
        materiasPropuestas=new estrada.leon.rafael.readwatch.estudiante.pojo.MateriasPropuestas[]{
                new estrada.leon.rafael.readwatch.estudiante.pojo.MateriasPropuestas(0,1,"Química"),
                new estrada.leon.rafael.readwatch.estudiante.pojo.MateriasPropuestas(1,10,"Electrónica"),
                new estrada.leon.rafael.readwatch.estudiante.pojo.MateriasPropuestas(2,2,"Bases de datos"),
                new estrada.leon.rafael.readwatch.estudiante.pojo.MateriasPropuestas(3,6,"Geografía"),
                new estrada.leon.rafael.readwatch.estudiante.pojo.MateriasPropuestas(4,7,"Historia"),
                new estrada.leon.rafael.readwatch.estudiante.pojo.MateriasPropuestas(5,2,"Biología"),
                new estrada.leon.rafael.readwatch.estudiante.pojo.MateriasPropuestas(6,4,"Física"),
                new estrada.leon.rafael.readwatch.estudiante.pojo.MateriasPropuestas(8,2,"Literatura"),
                new estrada.leon.rafael.readwatch.estudiante.pojo.MateriasPropuestas(7,8,"Astronomía"),
                new estrada.leon.rafael.readwatch.estudiante.pojo.MateriasPropuestas(9,3,"Robótica"),
                new estrada.leon.rafael.readwatch.estudiante.pojo.MateriasPropuestas(10,5,"Arte")
        };
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
        view= inflater.inflate(R.layout.fragment_materias_propuestas, container, false);
        lvMateriasPropuestas=view.findViewById(R.id.lvMateriasPropuestas);
        cargarDatos();
        for(int i=0;i<materiasPropuestas.length;i++){
            votos+=materiasPropuestas[i].getVotos();
        }
        MateriasPropuestasAdapter materiasPropuestasAdapter=new MateriasPropuestasAdapter(getContext(),R.layout.materia_propuesta,materiasPropuestas,votos);
        lvMateriasPropuestas.setAdapter(materiasPropuestasAdapter);
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
