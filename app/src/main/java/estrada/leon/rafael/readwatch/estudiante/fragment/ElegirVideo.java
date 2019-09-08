package estrada.leon.rafael.readwatch.estudiante.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import estrada.leon.rafael.readwatch.estudiante.adapter.VideosAdapter;
import estrada.leon.rafael.readwatch.estudiante.pojo.Videos;
import estrada.leon.rafael.readwatch.estudiante.interfaces.iComunicacionFragments;
import estrada.leon.rafael.readwatch.R;

public class ElegirVideo extends Fragment implements View.OnClickListener,
        VideosAdapter.OnVideoListener, Response.Listener<JSONObject>, Response.ErrorListener {
    private iComunicacionFragments interfaceFragments;
    ProgressDialog progreso;
    JsonObjectRequest jsonObjectRequest;
    int idTema;
    RequestQueue request;
    View vista;
    Activity actividad;
    List<Videos> videos;
    VideosAdapter videosAdapter;
    RecyclerView recyclerVideos;
    private List<Videos> list;

    private OnFragmentInteractionListener mListener;

    public ElegirVideo() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Button btnVideo,btnDocumento,btnSubirVideo;
        videos=new ArrayList<>();


        vista=inflater.inflate(R.layout.fragment_elegir_video, container, false);
        recyclerVideos=vista.findViewById(R.id.recyclerVideos);
        btnVideo=vista.findViewById(R.id.btnVideo);
        btnDocumento=vista.findViewById(R.id.btnDocumento);
        btnSubirVideo=vista.findViewById(R.id.btnSubirVideo);
        btnVideo.setOnClickListener(this);
        btnDocumento.setOnClickListener(this);
        btnSubirVideo.setOnClickListener(this);
        recyclerVideos.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        SharedPreferences preferences = getContext().getSharedPreferences("Tema", Context.MODE_PRIVATE);
        idTema = preferences.getInt("tema", 0);

        request= Volley.newRequestQueue(getContext());
        cargarWebService();
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
    public void reportarClick() {
        interfaceFragments.onClickReportar();
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
            case R.id.btnSubirVideo:
                interfaceFragments.onClickSubirVid();
                break;
        }
    }
    private void cargarWebService() {
        String url;
        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();
        url = "https://readandwatch.herokuapp.com/php/cargarVidDoc.php?" +
                "idTema="+idTema+"&tipo=v";
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }
    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.hide();
    }

    @Override
    public void onResponse(JSONObject response) {
        JSONArray json;
        JSONObject jsonObject=null;
        Videos video;
        json = response.optJSONArray("usuario");
        String idUsuario,descripcion,miniatura;
        try {
            for(int i=0;i<json.length();i++){
            jsonObject=json.getJSONObject(i);
            idUsuario=jsonObject.optString("idUsuario");
            descripcion=jsonObject.optString("descripcion");
            miniatura=jsonObject.optString("rutaImagen");
            video=new Videos(idUsuario,descripcion,miniatura);

            videos.add(video);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        progreso.hide();
        videosAdapter=new VideosAdapter(getContext(),videos, this);
        recyclerVideos.setAdapter(videosAdapter);
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}