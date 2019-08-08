package estrada.leon.rafael.readwatch.Estudiante.Fragment;

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

import estrada.leon.rafael.readwatch.Estudiante.Adapter.DocumentosAdapter;
import estrada.leon.rafael.readwatch.Estudiante.POJO.Documentos;
import estrada.leon.rafael.readwatch.Interfaces.iComunicacionFragments;
import estrada.leon.rafael.readwatch.R;


public class ElegirDocumento extends Fragment implements DocumentosAdapter.OnDocumentosListener, View.OnClickListener {
    RecyclerView recyclerDocumentos;
    Activity actividad;
    iComunicacionFragments interfaceFragments;
    View vista;
    List<Documentos> documentosList = new ArrayList<>(); ;
    DocumentosAdapter adapter;
    Button btnVideo,btnDocumento;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ElegirDocumento() {
        // Required empty public constructor
    }

    public static ElegirDocumento newInstance(String param1, String param2) {
        ElegirDocumento fragment = new ElegirDocumento();
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
        vista=inflater.inflate(R.layout.fragment_elegir_documento, container, false);

        recyclerDocumentos=vista.findViewById(R.id.recyclerDocumentos);
        btnVideo=vista.findViewById(R.id.btnVideo);
        btnDocumento=vista.findViewById(R.id.btnDocumento);

        btnVideo.setOnClickListener(this);
        btnDocumento.setOnClickListener(this);

        recyclerDocumentos.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        documentosList=new ArrayList<>();
        documentosList.add(new Documentos("Ricardo", "Polinomio"));
        documentosList.add(new Documentos("Jose", "Lectura"));

        adapter=new DocumentosAdapter(getContext(),documentosList,this);
        recyclerDocumentos.setAdapter(adapter);
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
