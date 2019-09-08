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

    Intent entrar;
    ProgressDialog progreso;
    JsonObjectRequest jsonObjectRequest;
    int idTema;
    RequestQueue request;
    RecyclerView recyclerDocumentos;
    DocumentosAdapter adapter;

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
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnVideo:
                interfaceFragments.vistaVideosDoc(true);
                break;
            case R.id.btnDocumento:
                interfaceFragments.vistaVideosDoc(false);
                break;
            case R.id.btnSubirDocumento:
                interfaceFragments.onClickSubirDoc();
                break;

        }
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
        String idUsuario,descripcion,miniatura;
        try {
            for(int i=0;i<json.length();i++){
                jsonObject=json.getJSONObject(i);
                idUsuario=jsonObject.optString("idUsuario");
                descripcion=jsonObject.optString("descripcion");
                miniatura=jsonObject.optString("rutaImagen");
                documento=new Documentos(idUsuario,descripcion,miniatura);
                documentos.add(documento);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        progreso.hide();
        adapter=new DocumentosAdapter(getContext(),documentos,this);
        recyclerDocumentos.setAdapter(adapter);
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
