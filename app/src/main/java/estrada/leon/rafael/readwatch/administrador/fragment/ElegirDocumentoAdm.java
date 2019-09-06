package estrada.leon.rafael.readwatch.administrador.fragment;

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

import estrada.leon.rafael.readwatch.R;
import estrada.leon.rafael.readwatch.administrador.adapter.DocumentosAdapterAdm;
import estrada.leon.rafael.readwatch.administrador.interfaces.iComunicacionFragmentsAdm;
import estrada.leon.rafael.readwatch.administrador.pojo.DocumentosAdm;

public class ElegirDocumentoAdm extends Fragment implements DocumentosAdapterAdm.OnDocumentosAdmListener,View.OnClickListener {
    private iComunicacionFragmentsAdm comunicacionFragmentsAdm;
    private List<DocumentosAdm> list;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ElegirDocumentoAdm() {
        // Required empty public constructor
    }

    public void cargarDatos(){
        list=new ArrayList<>();
        for(int i=0;i<10;i++){
            list.add(new DocumentosAdm("perfil"+i,"video"+i,"@drawable/doc"));
        }
    }

    public static ElegirDocumentoAdm newInstance(String param1, String param2) {
        ElegirDocumentoAdm fragment = new ElegirDocumentoAdm();
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
        RecyclerView recyclerDocumentos;
        Button btnVideo,btnDocumento;
        DocumentosAdapterAdm adapter;
        View vista;
        vista= inflater.inflate(R.layout.fragment_elegir_documento_adm, container, false);

        recyclerDocumentos=vista.findViewById(R.id.recyclerDocumentos);
        btnVideo=vista.findViewById(R.id.btnVideo);
        btnDocumento=vista.findViewById(R.id.btnDocumento);

        btnVideo.setOnClickListener(this);
        btnDocumento.setOnClickListener(this);

        recyclerDocumentos.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        cargarDatos();
        adapter=new DocumentosAdapterAdm(getContext(),list,this);
        recyclerDocumentos.setAdapter(adapter);
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
        Activity actividad;
        super.onAttach(context);
        if (context instanceof Activity) {
            actividad= (Activity) context;
            comunicacionFragmentsAdm=(iComunicacionFragmentsAdm) actividad;
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
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnVideo:
                comunicacionFragmentsAdm.vistaVideosDoc(true);
                break;
            case R.id.btnDocumento:
                comunicacionFragmentsAdm.vistaVideosDoc(false);
                break;

        }
    }

    @Override
    public void onDocumentosClick(int position, List<DocumentosAdm> list, Toast toast) {
        comunicacionFragmentsAdm.onClickDocumentosHolder(toast);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
