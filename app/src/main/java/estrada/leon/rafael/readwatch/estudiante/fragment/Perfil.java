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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import estrada.leon.rafael.readwatch.R;
import estrada.leon.rafael.readwatch.estudiante.adapter.PerfilAdapter;
import estrada.leon.rafael.readwatch.estudiante.interfaces.Item;
import estrada.leon.rafael.readwatch.estudiante.interfaces.iComunicacionFragments;
import estrada.leon.rafael.readwatch.estudiante.pojo.Documentos;
import estrada.leon.rafael.readwatch.estudiante.pojo.Videos;

public class Perfil extends Fragment implements PerfilAdapter.OnPerfilListener {
    List<Item> list;
    private iComunicacionFragments interfaceFragments;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Perfil() {
    }
    void cargarDatos(){
        list=new ArrayList<>();
        for(int i=1;i<11;i++){
            list.add(new Documentos("perfil"+i,"Documento"+i,"@drawable/btnDocumento"));
            list.add(new Videos("perfil"+i,"video"+i,"@drawable/miniatura"));
        }
    }
    public static Perfil newInstance(String param1, String param2) {
        Perfil fragment = new Perfil();
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
        ImageView fotoPerfil;
        TextView lblNombreApellidos,lblDescripcion,lblCelular;
        RecyclerView recyclerPerfil;
        PerfilAdapter perfilAdapter;
        View view;
        view = inflater.inflate(R.layout.fragment_perfil, container, false);
        fotoPerfil=view.findViewById(R.id.fotoPerfil);
        lblNombreApellidos=view.findViewById(R.id.lblNombreApellidos);
        lblDescripcion=view.findViewById(R.id.lblDescripcion);
        lblCelular=view.findViewById(R.id.lblCelular);
        fotoPerfil.setImageResource(R.drawable.profilepic);
        recyclerPerfil=view.findViewById(R.id.recyclerPerfil);
        recyclerPerfil.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        cargarDatos();
        perfilAdapter=new PerfilAdapter(getContext(),list,this);
        recyclerPerfil.setAdapter(perfilAdapter);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        Activity activity;
        super.onAttach(context);
        if (context instanceof Activity) {
            activity= (Activity) context;
            interfaceFragments=(iComunicacionFragments)activity;
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
    public void onPerfilClick(int position, List<Item> lista, Toast toast) {
        interfaceFragments.onClickDocFavHolder(toast);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
