package estrada.leon.rafael.readwatch.administrador.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
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

import estrada.leon.rafael.readwatch.R;
import estrada.leon.rafael.readwatch.administrador.adapter.DocumentosAdapterAdm;
import estrada.leon.rafael.readwatch.administrador.interfaces.iComunicacionFragmentsAdm;
import estrada.leon.rafael.readwatch.administrador.pojo.DocumentosAdm;
import estrada.leon.rafael.readwatch.administrador.pojo.VideosAdm;

public class ElegirDocumentoAdm extends Fragment implements
        DocumentosAdapterAdm.OnDocumentosAdmListener,View.OnClickListener,
        Response.Listener<JSONObject>, Response.ErrorListener{
    private iComunicacionFragmentsAdm comunicacionFragmentsAdm;
    private List<DocumentosAdm> documentos;
    ProgressDialog progreso;
    JsonObjectRequest jsonObjectRequest;
    int idTema;
    RequestQueue request;
    RecyclerView recyclerDocumentos;
    DocumentosAdapterAdm adapter;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ElegirDocumentoAdm() {
        // Required empty public constructor
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
        SharedPreferences preferences = getContext().getSharedPreferences("Tema", Context.MODE_PRIVATE);
        idTema = preferences.getInt("tema", 0);
        request= Volley.newRequestQueue(getContext());
        cargarWebService();
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

    private void cargarWebService() {
        documentos=new ArrayList<>();
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
        Toast.makeText(getContext(), "Error.\n "+error.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        JSONArray json;
        JSONObject jsonObject=null;
        DocumentosAdm documento;
        json = response.optJSONArray("usuario");
        String idUsuario,descripcion,miniatura;
        int idVidDoc;
        try {
            for(int i=0;i<json.length();i++){
                jsonObject=json.getJSONObject(i);
                idUsuario=jsonObject.optString("idUsuario");
                descripcion=jsonObject.optString("descripcion");
                miniatura=jsonObject.optString("rutaImagen");
                idVidDoc=jsonObject.optInt("idVidDoc");
                documento=new DocumentosAdm(idUsuario,descripcion,miniatura,idVidDoc);
                documentos.add(documento);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        progreso.hide();
        adapter=new DocumentosAdapterAdm(getContext(),documentos,this);
        recyclerDocumentos.setAdapter(adapter);
    }

    @Override
    public void onDocumentosClick(int position, List<DocumentosAdm> list, Toast toast) {
        comunicacionFragmentsAdm.onClickDocumentosHolder(toast);
    }

    @Override
    public void comentarioClick(int adapterPosition, List<DocumentosAdm> list) {
        comunicacionFragmentsAdm.onClickComentario(list.get(adapterPosition).getIdVidDoc());
    }

    @Override
    public void opcionClick(int position, List<DocumentosAdm> list) {
        SharedPreferences preferences = getContext().getSharedPreferences("Datos usuario", Context.MODE_PRIVATE);
        int idUsuario = preferences.getInt("idUsuario", 0);
        int idVidDoc =list.get(position).getIdVidDoc();
        comunicacionFragmentsAdm.onClickOpcion(idUsuario,idVidDoc,2);
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
