package estrada.leon.rafael.readwatch.estudiante.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import estrada.leon.rafael.readwatch.estudiante.pojo.Comentarios;
import estrada.leon.rafael.readwatch.estudiante.pojo.Videos;
import estrada.leon.rafael.readwatch.estudiante.interfaces.iComunicacionFragments;
import estrada.leon.rafael.readwatch.R;

public class ElegirVideo extends Fragment implements View.OnClickListener,
        VideosAdapter.OnVideoListener, Response.Listener<JSONObject>, Response.ErrorListener {
    private iComunicacionFragments interfaceFragments;
    ProgressDialog progreso;
    JsonObjectRequest jsonObjectRequest, jsonObjectRequest2;
    int idTema;
    RequestQueue request, request2;
    View vista;
    Activity actividad;
    List<Videos> videos;
    VideosAdapter videosAdapter;
    RecyclerView recyclerVideos;
    int []idUsuarioVidDoc;
    int []idUsuarioVidDocFav;


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
        buscarVideosFav();
        buscarVideos();


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
    public void reportarClick(int position, List<Videos> list) {
        interfaceFragments.onClickReportarVidDoc(list.get(position).getIdVidDoc());
    }

    @Override
    public void perfilClick(int position,List<Videos> list) {

        ((iComunicacionFragments)interfaceFragments).onClickVidPerfil(list.get(position).getIdUsuario());
    }

    @Override
    public void comentarioClick(int position, List<Videos> list) {
        SharedPreferences preferences = getContext().getSharedPreferences("Datos usuario", Context.MODE_PRIVATE);
        int idUsuario = preferences.getInt("idUsuario", 0);
        int idVidDoc =list.get(position).getIdVidDoc();
        interfaceFragments.onClickComentario(idUsuario,idVidDoc,0);
    }

    @Override
    public void opcionClick(int position, List<Videos> list) {
        SharedPreferences preferences = getContext().getSharedPreferences("Datos usuario", Context.MODE_PRIVATE);
        int idUsuario = preferences.getInt("idUsuario", 0);
        int idVidDoc =list.get(position).getIdVidDoc();
        interfaceFragments.onClickOpcion(idUsuario,idVidDoc,1);
    }

    @Override
    public void agregarFavoritos(int position, List<Videos> list) {
        SharedPreferences preferences = getContext().getSharedPreferences("Datos usuario", Context.MODE_PRIVATE);
        int idUsuario = preferences.getInt("idUsuario", 0);
        int idVidDoc =list.get(position).getIdVidDoc();
        verificarExistencia(idUsuario, idVidDoc);


    }

    private void verificarExistencia(final int idUsuario, final int idVidDoc) {
        String url;
        url = "https://readandwatch.herokuapp.com/php/existenciaFavorito.php?" +
                "idUsuario="+idUsuario+"&idVidDoc="+idVidDoc;
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                int idFavorito;
                JSONArray json;
                JSONObject jsonObject=null;
                json = response.optJSONArray("usuario");
                for(int i=0;i<json.length();i++) {
                    try {
                        jsonObject = json.getJSONObject(i);
                        idFavorito = jsonObject.getInt("idFavorito");

                        if (idFavorito==0){
                            subirFavoritos(idUsuario, idVidDoc);

                        }
                        else {
                            deleteFavoritos(idFavorito);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                progreso.hide();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progreso.hide();
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        request.add(jsonObjectRequest);
    }

    private void deleteFavoritos(int idFavorito) {
        String url;
        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();
        url = "https://readandwatch.herokuapp.com/php/eliminarFavorito.php?" +
                "idFavorito="+idFavorito;
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progreso.hide();
                Toast.makeText(getContext(), "Se elimino de favoritos", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progreso.hide();
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        request.add(jsonObjectRequest);
    }

    private void subirFavoritos(int idUsuario, int idVidDoc) {
        String url;
        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();
        url = "https://readandwatch.herokuapp.com/php/insertFavorito.php?" +
                "idUsuario="+idUsuario+"&idVidDoc="+idVidDoc;
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progreso.hide();
                Toast.makeText(getContext(), "Se agrego a favoritos", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progreso.hide();
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        request.add(jsonObjectRequest);

    }

    @Override
    public void onClick(View v) {
        SharedPreferences preferences = getContext().getSharedPreferences("Datos usuario", Context.MODE_PRIVATE);
        int idUsuario = preferences.getInt("idUsuario", 0);
        switch (v.getId()){
            case R.id.btnVideo:
                interfaceFragments.vistaVideosDoc(true,idUsuario);
                break;
            case R.id.btnDocumento:
                interfaceFragments.vistaVideosDoc(false,idUsuario);
                break;
            case R.id.btnSubirVideo:
                interfaceFragments.onClickSubirVid();
                break;
        }
    }

    private void buscarVideosFav() {
        SharedPreferences preferences = getContext().getSharedPreferences("Datos usuario", Context.MODE_PRIVATE);
        int idUsuario = preferences.getInt("idUsuario", 0);
        String url = "https://readandwatch.herokuapp.com/php/buscarVideoFav.php?" +
                "idUsuario="+idUsuario;
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray json = response.optJSONArray("usuario");
                JSONObject jsonObject=null;
                if(json.length()<1){
                    idUsuarioVidDocFav = new int[1];
                    idUsuarioVidDocFav[0]=0;
                }else {
                    idUsuarioVidDocFav = new int[json.length()];
                }

                for(int i=0;i<json.length();i++){
                    try {
                        jsonObject=json.getJSONObject(i);
                        idUsuarioVidDocFav[i]= jsonObject.getInt("idVidDoc");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        request.add(jsonObjectRequest);
    }

    private void buscarVideos(){
        SharedPreferences preference = getContext().getSharedPreferences("Datos usuario", Context.MODE_PRIVATE);
        int idUsuario = preference.getInt("idUsuario", 0);

        String url = "https://readandwatch.herokuapp.com/php/cargarVidDocUsuario.php?" +
                "idUsuario="+idUsuario+"&tipo=v";
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray json = response.optJSONArray("usuario");
                JSONObject jsonObject=null;
                if(json.length()<1){
                    idUsuarioVidDoc = new int[1];
                    idUsuarioVidDoc[0]=0;
                }else {
                    idUsuarioVidDoc = new int[json.length()];
                }
                for(int i=0;i<json.length();i++){
                    try {
                        jsonObject=json.getJSONObject(i);
                        idUsuarioVidDoc[i]= jsonObject.getInt("idVidDoc");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                cargarWebService();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                cargarWebService();
            }
        });
       request.add(jsonObjectRequest);

    }


    private void cargarWebService() {
        String url;
        progreso = new ProgressDialog(getActivity());
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
        String descripcion,miniatura,ruta, eliminado;
        int idUsuario,idVidDoc;
        try {
            for(int i=0;i<json.length();i++){
            jsonObject=json.getJSONObject(i);
            idUsuario=jsonObject.optInt("idUsuario");
            descripcion=jsonObject.optString("descripcion");
            miniatura=jsonObject.optString("rutaImagen");
            idVidDoc=jsonObject.optInt("idVidDoc");
            ruta = jsonObject.optString("ruta");
            eliminado = jsonObject.optString("eliminado");
            if(eliminado.equals("N")) {
                video = new Videos(Integer.toString(idUsuario), descripcion, miniatura, idUsuario, idVidDoc, ruta);
                videos.add(video);
            }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        videosAdapter=new VideosAdapter(getContext(),videos, this,idUsuarioVidDoc, idUsuarioVidDocFav);
        recyclerVideos.setAdapter(videosAdapter);
        progreso.hide();

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}