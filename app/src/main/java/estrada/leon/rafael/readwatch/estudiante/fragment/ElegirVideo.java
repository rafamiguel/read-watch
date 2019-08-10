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

import estrada.leon.rafael.readwatch.estudiante.adapter.VideosAdapter;
import estrada.leon.rafael.readwatch.estudiante.pojo.Videos;
import estrada.leon.rafael.readwatch.estudiante.interfaces.iComunicacionFragments;
import estrada.leon.rafael.readwatch.R;

public class ElegirVideo extends Fragment implements View.OnClickListener, VideosAdapter.OnVideoListener {
    private iComunicacionFragments interfaceFragments;
    View vista;
    Activity actividad;
    private List<Videos> list;

    private OnFragmentInteractionListener mListener;

    public ElegirVideo() {
    }
    public void cargarDatos(){
        list=new ArrayList<>();
        for (int i=0;i<20;i++){
            list.add(new Videos("perfil"+1,"video"+1,"@drawable/miniatura"));
        }

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Button btnVideo,btnDocumento;
        VideosAdapter videosAdapter;
        RecyclerView recyclerVideos;
        vista=inflater.inflate(R.layout.fragment_elegir_video, container, false);
        recyclerVideos=vista.findViewById(R.id.recyclerVideos);
        btnVideo=vista.findViewById(R.id.btnVideo);
        btnDocumento=vista.findViewById(R.id.btnDocumento);
        btnVideo.setOnClickListener(this);
        btnDocumento.setOnClickListener(this);
        recyclerVideos.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        cargarDatos();
        videosAdapter=new VideosAdapter(getContext(),list, this);
        recyclerVideos.setAdapter(videosAdapter);
        return vista;
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
    public void onVideoClick(int position, List<Videos> list, Toast toast) {
        interfaceFragments.onClickVideosHolder(toast);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
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