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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import estrada.leon.rafael.readwatch.R;
import estrada.leon.rafael.readwatch.estudiante.adapter.FavoritosAdapter;
import estrada.leon.rafael.readwatch.estudiante.interfaces.Item;
import estrada.leon.rafael.readwatch.estudiante.interfaces.iComunicacionFragments;
import estrada.leon.rafael.readwatch.estudiante.pojo.Documentos;
import estrada.leon.rafael.readwatch.estudiante.pojo.Videos;

public class Favoritos extends Fragment implements FavoritosAdapter.OnFavoritosListener {
    private List<Item> list;
    private iComunicacionFragments interfaceFragments;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Favoritos() {
    }

    public void cargarDatos(){
        list=new ArrayList<>();
        for(int i=1;i<11;i++){
            list.add(new Documentos("perfil"+i,"Documento"+i,"@drawable/btnDocumento",i,1));
            list.add(new Videos("perfil"+i,"video"+i,"@drawable/miniatura",i,1));
        }
    }

    public static Favoritos newInstance(String param1, String param2) {
        Favoritos fragment = new Favoritos();
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
        RecyclerView recyclerFavoritos;
        FavoritosAdapter favoritosAdapter;
        View vista;
        vista= inflater.inflate(R.layout.fragment_favoritos, container, false);
        recyclerFavoritos=vista.findViewById(R.id.recyclerFavoritos);
        recyclerFavoritos.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        cargarDatos();
        favoritosAdapter=new FavoritosAdapter(getContext(),list,this);
        recyclerFavoritos.setAdapter(favoritosAdapter);
        return vista;
    }

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
    public void onFavoritoClick(int position, List<Item> lista, Toast toast) {
        interfaceFragments.onClickDocFavHolder(toast);
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
