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
import java.util.ArrayList;
import java.util.List;
import estrada.leon.rafael.readwatch.estudiante.adapter.TemasAdapter;
import estrada.leon.rafael.readwatch.estudiante.pojo.Subtemas;
import estrada.leon.rafael.readwatch.estudiante.pojo.Temas;
import estrada.leon.rafael.readwatch.estudiante.interfaces.Item;
import estrada.leon.rafael.readwatch.estudiante.interfaces.iComunicacionFragments;
import estrada.leon.rafael.readwatch.R;


public class ElegirTema extends Fragment implements TemasAdapter.OnTemasListener {
        RecyclerView temas;
        List<Item> temasList=new ArrayList<>();
        TemasAdapter temasAdapter;
        iComunicacionFragments interfaceFragments;
        View vista;
        Activity actividad;

        private static final String ARG_PARAM1 = "param1";
        private static final String ARG_PARAM2 = "param2";

        private String mParam1;
        private String mParam2;

        private OnFragmentInteractionListener mListener;

        public ElegirTema() {
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
            vista=inflater.inflate(R.layout.fragment_elegir_tema, container, false);
            temas=vista.findViewById(R.id.temas);
            temas.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
            for (int j=1;j<6;j++){
                temasList.add(new Temas("Tema"+j));
                for(int i=1;i<6;i++){
                    temasList.add(new Subtemas("SubTema"+i));
                }
            }
            temasAdapter= new TemasAdapter(getContext(),temasList,this);
            temas.setAdapter(temasAdapter);
            return vista;
        }

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
    public void onTemaClick(int position, List<Item> lista) {
        interfaceFragments.seleccionarVideo();
    }

    public interface OnFragmentInteractionListener {
            void onFragmentInteraction(Uri uri);
        }
        public void cargarDatos(){
            for (int j=1;j<6;j++){
                temasList.add(new Temas("Tema"+j));
                for(int i=1;i<6;i++){
                    temasList.add(new Temas("SubTema"+i));
                }
            }
        }
    }
