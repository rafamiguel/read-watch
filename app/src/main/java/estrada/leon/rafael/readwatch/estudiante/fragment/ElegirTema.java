package estrada.leon.rafael.readwatch.estudiante.fragment;


import android.app.Activity;
import android.app.ProgressDialog;
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
import estrada.leon.rafael.readwatch.estudiante.adapter.TemasAdapter;
import estrada.leon.rafael.readwatch.estudiante.pojo.Subtemas;
import estrada.leon.rafael.readwatch.estudiante.pojo.Temas;
import estrada.leon.rafael.readwatch.estudiante.interfaces.Item;
import estrada.leon.rafael.readwatch.estudiante.interfaces.iComunicacionFragments;
import estrada.leon.rafael.readwatch.R;


public class ElegirTema extends Fragment implements TemasAdapter.OnTemasListener,
        Response.Listener<JSONObject>, Response.ErrorListener{
        private iComunicacionFragments interfaceFragments;
    List<Item> temasList;
    ProgressDialog progreso;
    JsonObjectRequest jsonObjectRequest;
    RequestQueue request;

    TemasAdapter temasAdapter;
    RecyclerView temas;

    private OnFragmentInteractionListener mListener;

        public ElegirTema() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            TextView lblTemaInexistente;
            View vista;
            vista=inflater.inflate(R.layout.fragment_elegir_tema, container, false);
            temas=vista.findViewById(R.id.temas);
            lblTemaInexistente=vista.findViewById(R.id.lblTemaInexistente);
            lblTemaInexistente.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    interfaceFragments.onClickProponerTema();
                }
            });
            temas.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

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
    public void onTemaClick(int position, List<Item> lista) {
        interfaceFragments.seleccionarVideo(((Subtemas) (temasList.get(position))).getNombre());
    }

    private void cargarWebService() {
        String url;
        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();
        url = "https://readandwatch.herokuapp.com/php/cargarTemas.php?" +
                "idMateria=1&semestre=1";
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this , this);
        request.add(jsonObjectRequest);
    }
    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.hide();
        Toast.makeText(getContext(), "Error.\n "+error.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        String nombre,rutaImagen,tipo;
        int idTema, idMateria,idUsuario, votos, semestre;
        Temas tema;
        Subtemas subtema;
        temasList=new ArrayList<>();
        JSONArray json;
        JSONObject jsonObject=null;
        json = response.optJSONArray("usuario");

        try {
            for(int i=0;i<json.length();i++){
                jsonObject=json.getJSONObject(i);
                tipo=jsonObject.optString("tipo");
                if(tipo.equals("tema")) {
                    tema = new Temas(jsonObject.optString("nombre"));
                    temasList.add(tema);
                }else if(tipo.equals("subtema")){
                    subtema = new Subtemas(jsonObject.optString("nombre"));
                    temasList.add(subtema);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        progreso.hide();
        temasAdapter= new TemasAdapter(getContext(),temasList,this);
        temas.setAdapter(temasAdapter);
    }

    public interface OnFragmentInteractionListener {
            void onFragmentInteraction(Uri uri);
        }
    }
