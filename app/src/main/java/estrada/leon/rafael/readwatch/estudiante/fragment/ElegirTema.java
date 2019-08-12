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

import java.util.ArrayList;
import java.util.List;
import estrada.leon.rafael.readwatch.estudiante.adapter.TemasAdapter;
import estrada.leon.rafael.readwatch.estudiante.pojo.Subtemas;
import estrada.leon.rafael.readwatch.estudiante.pojo.Temas;
import estrada.leon.rafael.readwatch.estudiante.interfaces.Item;
import estrada.leon.rafael.readwatch.estudiante.interfaces.iComunicacionFragments;
import estrada.leon.rafael.readwatch.R;


public class ElegirTema extends Fragment implements TemasAdapter.OnTemasListener {
        private List<Item> temasList=new ArrayList<>();
        private iComunicacionFragments interfaceFragments;

        private OnFragmentInteractionListener mListener;

        public ElegirTema() {
        }

        public void cargarDatos(){
            for (int j=1;j<6;j++){
                temasList.add(new Temas("Tema"+j));
                for(int i=1;i<6;i++){
                    temasList.add(new Subtemas("SubTema"+i));
                }
            }
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            TemasAdapter temasAdapter;
            TextView lblTemaInexistente;
            RecyclerView temas;
            View vista;
            vista=inflater.inflate(R.layout.fragment_elegir_tema, container, false);
            temas=vista.findViewById(R.id.temas);
            lblTemaInexistente=vista.findViewById(R.id.lblTemaInexistente);
            lblTemaInexistente.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    interfaceFragments.onClickProponerTema();
                }
            });
            temas.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

            cargarDatos();
            temasAdapter= new TemasAdapter(getContext(),temasList,this);
            temas.setAdapter(temasAdapter);
            return vista;
        }

        @Override
        public void onAttach(Context context) {
            Activity actividad;
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
    }
