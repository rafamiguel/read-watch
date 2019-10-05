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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import estrada.leon.rafael.readwatch.MainFileManager;
import estrada.leon.rafael.readwatch.estudiante.adapter.DocumentosAdapter;
import estrada.leon.rafael.readwatch.estudiante.pojo.Documentos;
import estrada.leon.rafael.readwatch.estudiante.interfaces.iComunicacionFragments;
import estrada.leon.rafael.readwatch.R;


public class ElegirDocumento extends Fragment implements DocumentosAdapter.OnDocumentosListener,
        View.OnClickListener, Response.Listener<JSONObject>, Response.ErrorListener {

    private iComunicacionFragments interfaceFragments;
    private List<Documentos> documentos;

    ProgressDialog progreso;
    JsonObjectRequest jsonObjectRequest;
    int idTema;
    RequestQueue request;
    RecyclerView recyclerDocumentos;
    DocumentosAdapter adapter;

    int []idUsuarioVidDoc;

    private OnFragmentInteractionListener mListener;

    public ElegirDocumento() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView lblElegirDocumento;
        Button btnVideo,btnDocumento,btnSubirDocumento;
        View vista;
        documentos=new ArrayList<>();
        vista=inflater.inflate(R.layout.fragment_elegir_documento, container, false);
        recyclerDocumentos=vista.findViewById(R.id.recyclerDocumentos);
        btnVideo=vista.findViewById(R.id.btnVideo);
        btnDocumento=vista.findViewById(R.id.btnDocumento);
        btnSubirDocumento=vista.findViewById(R.id.btnSubirDocumento);

        btnVideo.setOnClickListener(this);
        btnDocumento.setOnClickListener(this);
        btnSubirDocumento.setOnClickListener(this);

        recyclerDocumentos.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL,false));

        SharedPreferences preferences = getContext().getSharedPreferences("Tema", Context.MODE_PRIVATE);
        idTema = preferences.getInt("tema", 0);

        request= Volley.newRequestQueue(getContext());
        buscarDoc();
        cargarWebService();
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
    public void reportarClick() {
        interfaceFragments.onClickReportar();
    }

    @Override
    public void perfilClick(int position, List<Documentos> documentosList) {
        ((iComunicacionFragments)interfaceFragments).onClickVidPerfil(documentosList.get(position).getIdUsuario());
    }

    @Override
    public void comentarioClick(int position, List<Documentos> list) {
        SharedPreferences preferences = getContext().getSharedPreferences("Datos usuario", Context.MODE_PRIVATE);
        int idUsuario = preferences.getInt("idUsuario", 0);
        int idVidDoc =list.get(position).getIdVidDoc();
        interfaceFragments.onClickComentario(idUsuario,idVidDoc,0);
    }

    @Override
    public void opcionClick(int position, List<Documentos> list) {
        SharedPreferences preferences = getContext().getSharedPreferences("Datos usuario", Context.MODE_PRIVATE);
        int idUsuario = preferences.getInt("idUsuario", 0);
        int idVidDoc =list.get(position).getIdVidDoc();
        interfaceFragments.onClickOpcion(idUsuario,idVidDoc,2);
    }


    @Override
    public void onClick(View v) {
        SharedPreferences preferences = getContext().getSharedPreferences("Datos usuario", Context.MODE_PRIVATE);
        int idUsuario = preferences.getInt("idUsuario", 0);
        switch(v.getId()){
            case R.id.btnVideo:
                interfaceFragments.vistaVideosDoc(true,idUsuario);
                break;
            case R.id.btnDocumento:
                interfaceFragments.vistaVideosDoc(false,idUsuario);
                break;
            case R.id.btnSubirDocumento:
                interfaceFragments.onClickSubirDoc();
                break;

        }
    }
    private void buscarDoc(){
        SharedPreferences preference = getContext().getSharedPreferences("Datos usuario", Context.MODE_PRIVATE);
        int idUsuario = preference.getInt("idUsuario", 0);

        String url = "https://readandwatch.herokuapp.com/php/cargarVidDocUsuario.php?" +
                "idUsuario="+idUsuario+"&tipo=d";
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



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        request.add(jsonObjectRequest);

    }
    private void cargarWebService() {
        String url;
        String ip=getString(R.string.ip);
        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();
        url = ip+"/php/cargarVidDoc.php?" +
                "idTema="+idTema+"&tipo=d";
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
        Documentos documento;
        json = response.optJSONArray("usuario");
        String descripcion,miniatura;
        int idUsuario,idVidDoc;
        try {
            for(int i=0;i<json.length();i++){
                jsonObject=json.getJSONObject(i);
                idUsuario=jsonObject.optInt("idUsuario");
                descripcion=jsonObject.optString("descripcion");
                miniatura=jsonObject.optString("rutaImagen");
                idVidDoc=jsonObject.optInt("idVidDoc");
                documento=new Documentos(Integer.toString(idUsuario),descripcion,miniatura,idUsuario,idVidDoc);
                documentos.add(documento);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        progreso.hide();
        adapter=new DocumentosAdapter(getContext(),documentos,this,idUsuarioVidDoc);
        recyclerDocumentos.setAdapter(adapter);
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
