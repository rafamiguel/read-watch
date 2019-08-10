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
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import estrada.leon.rafael.readwatch.estudiante.adapter.DocumentosAdapter;
import estrada.leon.rafael.readwatch.estudiante.pojo.Documentos;
import estrada.leon.rafael.readwatch.estudiante.interfaces.iComunicacionFragments;
import estrada.leon.rafael.readwatch.R;


public class ElegirDocumento extends Fragment implements DocumentosAdapter.OnDocumentosListener, View.OnClickListener {

    private iComunicacionFragments interfaceFragments;
    private List<Documentos> documentosList;

    private OnFragmentInteractionListener mListener;

    public ElegirDocumento() {
    }
    public void cargarDatos(){
        documentosList=new ArrayList<>();
        for(int i=0;i<10;i++){
            documentosList.add(new Documentos("perfil"+i,"video"+i,"@drawable/doc"));
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerDocumentos;
        Button btnVideo,btnDocumento;
        DocumentosAdapter adapter;
        View vista;

        vista=inflater.inflate(R.layout.fragment_elegir_documento, container, false);

        recyclerDocumentos=vista.findViewById(R.id.recyclerDocumentos);
        btnVideo=vista.findViewById(R.id.btnVideo);
        btnDocumento=vista.findViewById(R.id.btnDocumento);

        btnVideo.setOnClickListener(this);
        btnDocumento.setOnClickListener(this);

        recyclerDocumentos.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        cargarDatos();
        adapter=new DocumentosAdapter(getContext(),documentosList,this);
        recyclerDocumentos.setAdapter(adapter);
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
    public void onDocumentosClick(int position, List<Documentos> documentosList, Toast toast) {
        interfaceFragments.onClickDocumentosHolder(toast);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnVideo:
                interfaceFragments.vistaVideosDoc(true);
                break;
            case R.id.btnDocumento:
                interfaceFragments.vistaVideosDoc(false);
                break;

        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
