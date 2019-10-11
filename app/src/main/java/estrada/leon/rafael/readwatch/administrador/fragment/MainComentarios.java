package estrada.leon.rafael.readwatch.administrador.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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

import estrada.leon.rafael.readwatch.DialogModificarEliminarAdm;
import estrada.leon.rafael.readwatch.R;
import estrada.leon.rafael.readwatch.administrador.adapter.AdapterComentario;
import estrada.leon.rafael.readwatch.administrador.pojo.PojoComentario;
import estrada.leon.rafael.readwatch.estudiante.dialog.DialogModificarEliminar;
import estrada.leon.rafael.readwatch.estudiante.fragment.MainComentario;
import estrada.leon.rafael.readwatch.estudiante.interfaces.Item;
import estrada.leon.rafael.readwatch.estudiante.pojo.Comentarios;

public class MainComentarios extends AppCompatActivity implements  Response.Listener<JSONObject>,
        Response.ErrorListener, AdapterComentario.OnComentariosListener, DialogModificarEliminarAdm.IOpcionesVidDoc{
    JsonObjectRequest jsonObjectRequest;
    RequestQueue request;
    RecyclerView recycler;
    List<Item> list= new ArrayList<>();
    AdapterComentario adapterComentario;
    private int []idComentarioUsuario;
    int idVidDoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_comentarios_ad);
        recycler = findViewById(R.id.recyclerId);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(linearLayoutManager);
        Bundle extras = getIntent().getExtras();
        int id = extras.getInt("idVidDoc");
        idVidDoc=id;
        request= Volley.newRequestQueue(this);
        cargarWebService();

    }

    private void cargarWebService() {
        list=new ArrayList<>();
        String url;
        url = "https://readandwatch.herokuapp.com/php/cargarComentarios.php?" +
                "idVidDoc="+idVidDoc;
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }
    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, "Error.\n "+error.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        JSONArray json;
        JSONObject jsonObject=null;
        Comentarios comentario;
        json = response.optJSONArray("usuario");
        String nombre,comentarioString;
        int id;
        try {
            for(int i=0;i<json.length();i++){
                jsonObject=json.getJSONObject(i);
                nombre=jsonObject.optString("idUsuario");
                comentarioString=jsonObject.optString("texto");
                id = jsonObject.getInt("idComentario");
                comentario=new Comentarios(nombre,comentarioString,id);
                list.add(comentario);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapterComentario = new AdapterComentario(this, list, (MainComentarios)this, idComentarioUsuario);
        recycler.setAdapter(adapterComentario);
    }


    @Override
    public void opcionClick(int position, List<Item> list) {
        int idComentario=((Comentarios)list.get(position)).getIdComentario();
        SharedPreferences preferences = getSharedPreferences("comentarioSeleccionado", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("idComentario", idComentario);
        editor.commit();
        DialogModificarEliminarAdm nuevo = new DialogModificarEliminarAdm();
        nuevo.setOpcion(4);
        nuevo.show(getSupportFragmentManager(), "ejemplo");
    }

    @Override
    public void resubirVideo() {

    }

    @Override
    public void resubirDoc() {

    }

    @Override
    public void eliminarVidDoc(int idVidDoc, int opc) {

    }

    @Override
    public void eliminarCom(int idComentario) {
        Toast.makeText(this,String.valueOf(idComentario), Toast.LENGTH_SHORT).show();

        ProgressDialog progreso;
        JsonObjectRequest jsonObjectRequest;
        RequestQueue request;
        String url;
        String ip=getString(R.string.ip);
        url = ip+"/php/eliminarComentario.php?" +
                "idComentario="+idComentario;
        url=url.replace(" ", "%20");
        progreso = new ProgressDialog(this);
        progreso.setMessage("Cargando...");
        progreso.show();
        request= Volley.newRequestQueue(this);
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(MainComentarios.this, "Comentario eliminado con éxito", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainComentarios.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
        request.add(jsonObjectRequest);
        progreso.hide();
    }
}
