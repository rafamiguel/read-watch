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

import estrada.leon.rafael.readwatch.Estudiante.Adapter.VideosAdapter;
import estrada.leon.rafael.readwatch.Estudiante.POJO.Videos;
import estrada.leon.rafael.readwatch.Interfaces.iComunicacionFragments;
import estrada.leon.rafael.readwatch.R;

public class ElegirVideo extends Fragment implements View.OnClickListener, VideosAdapter.OnVideoListener {
    iComunicacionFragments interfaceFragments;
    View vista;
    Activity actividad;
    List<Videos> list;
    RecyclerView recyclerVideos;
    VideosAdapter videosAdapter;
    Button btnVideo,btnDocumento;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ElegirVideo() {
        // Required empty public constructor
    }

    public static ElegirVideo newInstance(String param1, String param2) {
        ElegirVideo fragment = new ElegirVideo();
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
        vista=inflater.inflate(R.layout.fragment_elegir_video, container, false);
        recyclerVideos=vista.findViewById(R.id.recyclerVideos);
        btnVideo=vista.findViewById(R.id.btnVideo);
        btnDocumento=vista.findViewById(R.id.btnDocumento);
        btnVideo.setOnClickListener(this);
        btnDocumento.setOnClickListener(this);

        recyclerVideos.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        list=new ArrayList<>();
        list.add(new Videos("video1"));
        videosAdapter=new VideosAdapter(getContext(),list, this);
        recyclerVideos.setAdapter(videosAdapter);
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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}