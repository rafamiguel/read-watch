package estrada.leon.rafael.readwatch.estudiante.fragment;

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

import estrada.leon.rafael.readwatch.estudiante.adapter.HistorialAdapter;
import estrada.leon.rafael.readwatch.R;
import estrada.leon.rafael.readwatch.general.pojo.Sesion;


public class Historial extends Fragment {
    private List<estrada.leon.rafael.readwatch.estudiante.pojo.Historial> list =new ArrayList<>();
    JsonObjectRequest jsonObjectRequest;
    RequestQueue request;
    HistorialAdapter historialAdapter;
    RecyclerView recyclerHistorial;
    private OnFragmentInteractionListener mListener;
    private String Castigo;
    int contador =0;
    public void cargarDatos(){

        for(int i=0;i<11;i++){
          //  list.add(new estrada.leon.rafael.readwatch.estudiante.pojo.Historial(i,"comentario","Es spam","El comentario ha sido eliminado"));
            int imageResource = getContext().getResources().getIdentifier("drawable/doc",null,getContext().getPackageName());
          //  list.add(new estrada.leon.rafael.readwatch.estudiante.pojo.Historial(imageResource,"documento","No es apropiado al tema o materia","El documento ha sido eliminado"));
            imageResource = getContext().getResources().getIdentifier("drawable/miniatura",null,getContext().getPackageName());
          //  list.add(new estrada.leon.rafael.readwatch.estudiante.pojo.Historial(imageResource,"video","No es apropiado al tema o materia","El video ha sido eliminado"));
        }
    }
    public Historial() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        request = Volley.newRequestQueue(getContext());
        View vista;
        vista = inflater.inflate(R.layout.fragment_historial, container, false);
        recyclerHistorial=vista.findViewById(R.id.recyclerHistorial);
        recyclerHistorial.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        buscarHistorial();




        //cargarDatos();

        return vista;
    }

    private void suspenderUsuario() {
        String url;
        int idUsuario = Sesion.getSesion().getId();
        url = "https://readandwatch.herokuapp.com/php/suspenderUsuario.php?" +
                "idUsuario="+idUsuario;
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        request.add(jsonObjectRequest);
    }

    private void buscarPreguntas() {
        String url;
        int idUsuario = Sesion.getSesion().getId();
        url = "https://readandwatch.herokuapp.com/php/historialPreguntas.php?" +
                "idUsuario="+idUsuario;
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String titulo="", rutaImagen="", tipo="", castigo="";
                JSONArray json;
                int idCastigo=0;
                JSONObject jsonObject=null;
                json = response.optJSONArray("usuario");
                estrada.leon.rafael.readwatch.estudiante.pojo.Historial hostorial;
                for(int i=0;i<json.length();i++) {
                    try {
                        jsonObject = json.getJSONObject(i);
                        titulo = jsonObject.getString("titulo");
                        tipo = jsonObject.getString("tipo");
                        idCastigo = jsonObject.getInt("idCastigo");
                        castigo = jsonObject.getString("castigo");

                        if(tipo.equals("Contenido sexual u obseno") || tipo.equals("Es spam")){
                            contador = contador + 1;
                        }

                        hostorial = new estrada.leon.rafael.readwatch.estudiante.pojo.Historial("pregunta",titulo,tipo, castigo, "Video");
                        list.add(hostorial);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


                historialAdapter=new HistorialAdapter(getContext(),list);
                recyclerHistorial.setAdapter(historialAdapter);
                if (contador >=3){
                    suspenderUsuario();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (contador >=3){
                    suspenderUsuario();
                }

            }
        });
        request.add(jsonObjectRequest);


    }

    private void buscarComentarios() {
        String url;
        int idUsuario = Sesion.getSesion().getId();
        url = "https://readandwatch.herokuapp.com/php/historialComentario.php?" +
                "idUsuario="+idUsuario;
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String texto="", rutaImagen="", tipo="", castigo="";
                JSONArray json;
                int idCastigo=0;
                JSONObject jsonObject=null;
                json = response.optJSONArray("usuario");
                estrada.leon.rafael.readwatch.estudiante.pojo.Historial hostorial;
                for(int i=0;i<json.length();i++) {
                    try {
                        jsonObject = json.getJSONObject(i);
                        texto = jsonObject.getString("texto");
                        tipo = jsonObject.getString("tipo");
                        idCastigo = jsonObject.getInt("idCastigo");
                        castigo = jsonObject.getString("castigo");

                        if(tipo.equals("Contenido sexual u obseno") || tipo.equals("Es spam")){
                            contador = contador + 1;
                        }

                        hostorial = new estrada.leon.rafael.readwatch.estudiante.pojo.Historial("comentario",texto,tipo, castigo, "Video");
                        list.add(hostorial);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                historialAdapter=new HistorialAdapter(getContext(),list);
                recyclerHistorial.setAdapter(historialAdapter);
                buscarPreguntas();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                buscarPreguntas();
            }
        });
        request.add(jsonObjectRequest);


    }

    private void buscarHistorial() {
        String url;
        int idUsuario = Sesion.getSesion().getId();
        url = "https://readandwatch.herokuapp.com/php/historialContenido.php?" +
                "idUsuario="+idUsuario;
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String ruta="", rutaImagen="", tipo="", castigo="";
                JSONArray json;
                int idCastigo=0;
                JSONObject jsonObject=null;
                json = response.optJSONArray("usuario");
                estrada.leon.rafael.readwatch.estudiante.pojo.Historial hostorial;
                for(int i=0;i<json.length();i++) {
                    try {
                        jsonObject = json.getJSONObject(i);
                        ruta = jsonObject.getString("ruta");
                        rutaImagen = jsonObject.getString("rutaImagen");
                        tipo = jsonObject.getString("tipo");
                        idCastigo = jsonObject.getInt("idCastigo");
                        castigo = jsonObject.getString("castigo");
                        if(tipo.equals("Contenido sexual u obseno") || tipo.equals("Es spam")){
                            contador = contador + 1;
                        }

                        hostorial = new estrada.leon.rafael.readwatch.estudiante.pojo.Historial(rutaImagen,ruta,tipo, castigo, "Video");
                        list.add(hostorial);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


                historialAdapter=new HistorialAdapter(getContext(),list);
                recyclerHistorial.setAdapter(historialAdapter);
                buscarComentarios();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                buscarComentarios();
            }
        });
        request.add(jsonObjectRequest);

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
