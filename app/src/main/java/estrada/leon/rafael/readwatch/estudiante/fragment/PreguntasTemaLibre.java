package estrada.leon.rafael.readwatch.estudiante.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import estrada.leon.rafael.readwatch.estudiante.adapter.TemaLibreAdapter;
import estrada.leon.rafael.readwatch.estudiante.pojo.TemaLibre;
import estrada.leon.rafael.readwatch.estudiante.interfaces.iComunicacionFragments;
import estrada.leon.rafael.readwatch.R;

public class PreguntasTemaLibre extends Fragment implements TemaLibreAdapter.OnTemaListener,
        Response.Listener<JSONObject>, Response.ErrorListener {
    iComunicacionFragments interfaceFragments;
    FloatingActionButton fabNuevaPregunta;
    Activity actividad;
    RecyclerView recyclerTemas;
    View vista;
    TemaLibreAdapter adapter;
    List<TemaLibre> temaLibreList = new ArrayList<>();
    ProgressDialog progreso;
    JsonObjectRequest jsonObjectRequest;
    RequestQueue request;
    List<TemaLibre> preguntas;
    TemaLibreAdapter temaLibreAdapter;

    private OnFragmentInteractionListener mListener;

    public PreguntasTemaLibre() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        preguntas=new ArrayList<>();

        vista=inflater.inflate(R.layout.fragment_preguntas_tema_libre, container, false);
        fabNuevaPregunta = vista.findViewById(R.id.fabNuevaPregunta2);
        fabNuevaPregunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              interfaceFragments.onClickNuevaPregunta();
            }
        });

        recyclerTemas =  vista.findViewById(R.id.recyclerTemas);
        recyclerTemas.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
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
    public void onTemaClick(int position, List<TemaLibre> temaLibreList, Toast toast) {
        interfaceFragments.onClickTemasLibresHolder(toast);
    }

    @Override
    public void onClickReportar() {
        interfaceFragments.onClickReportar();
    }

    @Override
    public void onClickSubirVid() {
        interfaceFragments.onClickSubirVidPreg();
    }

    @Override
    public void onClickSubirDoc() {
        interfaceFragments.onClickSubirDocPreg();
    }

    private void cargarWebService(){
        String url;
        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();
        url = "https://readandwatch.herokuapp.com/php/cargarPregunta.php";
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
        TemaLibre temaLibre;
        json = response.optJSONArray("usuario");
        String descripcion,titulo;
        try {
            for(int i=0;i<json.length();i++){
                jsonObject=json.getJSONObject(i);
                descripcion=jsonObject.optString("descripcion");
                titulo=jsonObject.optString("titulo");
                temaLibre=new TemaLibre(descripcion,titulo);

                preguntas.add(temaLibre);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        progreso.hide();
        temaLibreAdapter=new TemaLibreAdapter(getContext(),preguntas, this);
        recyclerTemas.setAdapter(temaLibreAdapter);
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
