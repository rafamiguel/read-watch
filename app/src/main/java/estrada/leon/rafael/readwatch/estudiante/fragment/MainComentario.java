package estrada.leon.rafael.readwatch.estudiante.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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
import estrada.leon.rafael.readwatch.estudiante.dialog.DialogModificarEliminar;
import estrada.leon.rafael.readwatch.estudiante.dialog.DialogSubirVideo;
import estrada.leon.rafael.readwatch.estudiante.dialog.Dialog_Subir_documento;
import estrada.leon.rafael.readwatch.estudiante.interfaces.Item;
import estrada.leon.rafael.readwatch.estudiante.menu.MenuEstudiante;
import estrada.leon.rafael.readwatch.estudiante.pojo.Comentarios;
import estrada.leon.rafael.readwatch.estudiante.pojo.Documentos;
import estrada.leon.rafael.readwatch.estudiante.pojo.Videos;
import estrada.leon.rafael.readwatch.general.pojo.Estudiante;
import estrada.leon.rafael.readwatch.general.pojo.Sesion;
import estrada.leon.rafael.readwatch.general.pojo.Usuario;

public class MainComentario extends AppCompatActivity implements  Response.Listener<JSONObject>,
        Response.ErrorListener, AdapterComentario.OnComentariosListener, DialogModificarEliminar.IOpcionesComentario {
    Button btnComentario;
    JsonObjectRequest jsonObjectRequest;
    RequestQueue request;
    RecyclerView recycler;
    List<Item> list= new ArrayList<>();
    AdapterComentario adapterComentario;
    int idVidDoc=0,idUsuario,idPregunta=0;
    Context contexto=this;
    private int []idComentarioUsuario;

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
        idUsuario = Sesion.getSesion().getId();
        buscarComentariosUsuario();
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
        request = Volley.newRequestQueue(this);
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
                        int idVidDoc,idUsuario,idComentario;
                        try {
                            if(json!=null) {
                                for (int i = 0; i < json.length(); i++) {
                                    jsonObject = json.getJSONObject(i);
                                    nombre = jsonObject.optString("idUsuario");
                                    comentarioString = jsonObject.optString("texto");
                                    idComentario=jsonObject.optInt("idComentario");
                                    comentario = new Comentarios(nombre, comentarioString,idComentario);
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

                        adapterComentario = new AdapterComentario(contexto, list,(MainComentario)contexto,idComentarioUsuario);
                        recycler.setAdapter(adapterComentario);
                        adapterComentario.refresh(list);
                        recycler.invalidate();
                    }
                }, this);
        request.add(jsonObjectRequest);
    }

    private void cargarComentariosVidDoc() {
        request = Volley.newRequestQueue(this);
        list=new ArrayList<>();
        String url;
        url = "https://readandwatch.herokuapp.com/php/cargarComentarios.php?" +
                "idVidDoc="+idVidDoc;
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                this, this);
        request.add(jsonObjectRequest);
    }

    private void buscarComentariosUsuario(){
        request = Volley.newRequestQueue(this);
        String url = "https://readandwatch.herokuapp.com/php/cargarComentariosUsuario.php?" +
                "idUsuario="+idUsuario;
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray json = response.optJSONArray("usuario");
                JSONObject jsonObject=null;
                if(json.length()<1){
                    idComentarioUsuario = new int[1];
                    idComentarioUsuario[0]=0;
                }else {
                    idComentarioUsuario = new int[json.length()];
                }
                for(int i=0;i<json.length();i++){
                    try {
                        jsonObject=json.getJSONObject(i);
                        idComentarioUsuario[i]= jsonObject.getInt("idComentario");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                Bundle extras = getIntent().getExtras();
                if(extras.getInt("idVidDoc")!=0) {
                    idVidDoc = extras.getInt("idVidDoc");
                    cargarComentariosVidDoc();
                }
                if(extras.getInt("idVidDoc")==0){
                    idPregunta = extras.getInt("idPregunta");
                    cargarComentariosPreg();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Bundle extras = getIntent().getExtras();
                if(extras.getInt("idVidDoc")!=0) {
                    idVidDoc = extras.getInt("idVidDoc");
                    cargarComentariosVidDoc();
                }
                if(extras.getInt("idVidDoc")==0){
                    idPregunta = extras.getInt("idPregunta");
                    cargarComentariosPreg();
                }
            }
        });
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
        int idComentario;
        try {
            for(int i=0;i<json.length();i++){
                jsonObject=json.getJSONObject(i);
                nombre=jsonObject.optString("idUsuario");
                comentarioString=jsonObject.optString("texto");
                idComentario=jsonObject.optInt("idComentario");
                comentario=new Comentarios(nombre,comentarioString,idComentario);
                list.add(comentario);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapterComentario = new AdapterComentario(this, list,this,idComentarioUsuario);
        recycler.setAdapter(adapterComentario);
        adapterComentario.refresh(list);
        recycler.invalidate();
    }

    @Override
    public void opcionClick(int position, List<Item> list) {
        int idComentario=((Comentarios)list.get(position)).getIdComentario();
        SharedPreferences preferences = getSharedPreferences("comentarioSeleccionado", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("idComentario", idComentario);
        editor.commit();
        DialogModificarEliminar nuevo = new DialogModificarEliminar();
        nuevo.setOpcion(3);
        nuevo.show(getSupportFragmentManager(), "ejemplo");
    }



    @Override
    public void resubirCom(int idComentario) {
        cargarAlertDialog(idComentario);


    }

    private void cargarAlertDialog(int idComentario) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainComentario.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_modificar_comentario, null);
        final EditText txtComentario = view.findViewById(R.id.txtComentario);
        final int idComentarios = idComentario;
        final ProgressDialog progreso;
        progreso = new ProgressDialog(MainComentario.this);
        builder.setView(view);
        builder.setTitle("Modificar comentario")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        progreso.setMessage("Cargando...");
                        progreso.show();
                        String comentario = txtComentario.getText().toString();
                        JsonObjectRequest jsonObjectRequest;
                        RequestQueue request;
                        String url;
                        String ip=getString(R.string.ip);
                        url = ip+"/php/updateComentario.php?" +
                                "idComentario="+idComentarios+"&comentario="+comentario;
                        url=url.replace(" ", "%20");
                        request= Volley.newRequestQueue(getApplicationContext());
                        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                progreso.hide();
                                Toast.makeText(MainComentario.this, "Comentario modificado con éxito", Toast.LENGTH_SHORT).show();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progreso.hide();
                                Toast.makeText(MainComentario.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        });
                        request.add(jsonObjectRequest);
                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();


    }

    @Override
    public void eliminarCom(int idComentario) {
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
                Toast.makeText(MainComentario.this, "Comentario eliminado con éxito", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainComentario.this, "Errror", Toast.LENGTH_SHORT).show();
            }
        });
        request.add(jsonObjectRequest);
        progreso.hide();
    }
}