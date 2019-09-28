package estrada.leon.rafael.readwatch.estudiante.fragment;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import estrada.leon.rafael.readwatch.estudiante.adapter.AdapterComentario;
import estrada.leon.rafael.readwatch.estudiante.interfaces.Item;
import estrada.leon.rafael.readwatch.estudiante.pojo.Comentarios;
import estrada.leon.rafael.readwatch.estudiante.pojo.Documentos;
import estrada.leon.rafael.readwatch.estudiante.pojo.Videos;

public class MainComentario extends AppCompatActivity implements  Response.Listener<JSONObject>,
        Response.ErrorListener {
    Button btnComentario;
    JsonObjectRequest jsonObjectRequest;
    RequestQueue request;
    RecyclerView recycler;
    List<Item> list= new ArrayList<>();
    AdapterComentario adapterComentario;
    int idVidDoc=0,idUsuario,idPregunta=0;
    Context contexto=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_comentarios);
        recycler = findViewById(R.id.recyclerId);
        Button btnOk=findViewById(R.id.btnOk);
        final EditText txtNuevoComentario=findViewById(R.id.txtNuevoComentario);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(idVidDoc!=0) {
                    insertarComentario(txtNuevoComentario.getText().toString());
                    request = Volley.newRequestQueue(getApplicationContext());
                    cargarComentariosVidDoc();
                }else {
                    insertarComentarioPreg(txtNuevoComentario.getText().toString());
                    request = Volley.newRequestQueue(getApplicationContext());
                    cargarComentariosPreg();
                }
            }
        });
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(new LinearLayoutManager
                (this/*SI SE DETIENE LA APLICACION Y SE CAMBIÓ ESTO A UN FRAGMENT O DIALOG (PORQUE
                TENIA QUE SER UN DIALOG) CAMBIAR EL THIS POR GETCONTEXT.*/
                        ,LinearLayoutManager.VERTICAL,false));
        Bundle extras = getIntent().getExtras();
        if(extras.getInt("idVidDoc")!=0) {
            idVidDoc = extras.getInt("idVidDoc");
            idUsuario = extras.getInt("idUsuario");
            request = Volley.newRequestQueue(this);
            cargarComentariosVidDoc();
        }
        if(extras.getInt("idVidDoc")==0){
            idPregunta = extras.getInt("idPregunta");
            idUsuario = extras.getInt("idUsuario");
            request = Volley.newRequestQueue(this);
            cargarComentariosPreg();
        }
    }

    private void insertarComentario(String texto){
        String url;
        url = "https://readandwatch.herokuapp.com/php/insertarComentario.php?" +
                "idUsuario="+idUsuario+"&idVidDoc="+idVidDoc+"&texto="+texto;
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(MainComentario.this, "Comentario ingresado correctamente",
                                Toast.LENGTH_SHORT).show();
                    }
                }, this);
        request.add(jsonObjectRequest);
    }

    private void insertarComentarioPreg(String texto){
        String url;
        url = "https://readandwatch.herokuapp.com/php/insertarComentarioPreg.php?" +
                "idUsuario="+idUsuario+"&idPregunta="+idPregunta+"&texto="+texto;
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(MainComentario.this, "Comentario ingresado correctamente",
                                Toast.LENGTH_SHORT).show();
                    }
                }, this);
        request.add(jsonObjectRequest);
    }

    private void cargarComentariosPreg() {
        list=new ArrayList<>();
        String url;
        url = "https://readandwatch.herokuapp.com/php/cargarComentariosPreg.php?" +
                "idPregunta="+idPregunta;
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray json;
                        JSONObject jsonObject=null;
                        Comentarios comentario;
                        Videos video;
                        Documentos documento;
                        json = response.optJSONArray("comentario");
                        String nombre,comentarioString,descripcion,rutaImagen;
                        int idVidDoc,idUsuario;
                        try {
                            if(json!=null) {
                                for (int i = 0; i < json.length(); i++) {
                                    jsonObject = json.getJSONObject(i);
                                    nombre = jsonObject.optString("idUsuario");
                                    comentarioString = jsonObject.optString("texto");
                                    comentario = new Comentarios(nombre, comentarioString);
                                    list.add(comentario);
                                }
                            }
                            json = response.optJSONArray("vidDoc");
                            if(json!=null){
                                for (int i = 0; i < json.length(); i++) {
                                    jsonObject = json.getJSONObject(i);
                                    idVidDoc = jsonObject.optInt("idVidDoc");
                                    descripcion = jsonObject.optString("descripcion");
                                    rutaImagen = jsonObject.optString("rutaImagen");
                                    idUsuario = jsonObject.optInt("idUsuario");
                                    if (jsonObject.optString("tipo").equals("v")) {
                                        video = new Videos(Integer.toString(idUsuario), descripcion, rutaImagen, idUsuario, idVidDoc);
                                        list.add(video);
                                    } else {
                                        documento = new Documentos(Integer.toString(idUsuario), descripcion, rutaImagen, idUsuario, idVidDoc);
                                        list.add(documento);
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        adapterComentario = new AdapterComentario(contexto, list);
                        recycler.setAdapter(adapterComentario);
                        adapterComentario.refresh(list);
                        recycler.invalidate();
                    }
                }, this);
        request.add(jsonObjectRequest);
    }

    private void cargarComentariosVidDoc() {
        list=new ArrayList<>();
        String url;
        url = "https://readandwatch.herokuapp.com/php/cargarComentarios.php?" +
                "idVidDoc="+idVidDoc;
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                this, this);
        request.add(jsonObjectRequest);
    }
    @Override
    public void onErrorResponse(VolleyError error) {
       // Toast.makeText(this, "Error.\n "+error.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        JSONArray json;
        JSONObject jsonObject=null;
        Comentarios comentario;
        json = response.optJSONArray("usuario");
        String nombre,comentarioString;
        try {
            for(int i=0;i<json.length();i++){
                jsonObject=json.getJSONObject(i);
                nombre=jsonObject.optString("idUsuario");
                comentarioString=jsonObject.optString("texto");
                comentario=new Comentarios(nombre,comentarioString);
                list.add(comentario);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapterComentario = new AdapterComentario(this, list);
        recycler.setAdapter(adapterComentario);
        adapterComentario.refresh(list);
        recycler.invalidate();
    }

}