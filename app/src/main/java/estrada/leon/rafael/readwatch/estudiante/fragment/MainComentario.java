package estrada.leon.rafael.readwatch.estudiante.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.krishna.fileloader.FileLoader;
import com.krishna.fileloader.listener.FileRequestListener;
import com.krishna.fileloader.pojo.FileResponse;
import com.krishna.fileloader.request.FileLoadRequest;
import com.shockwave.pdfium.PdfDocument;
import com.shockwave.pdfium.PdfiumCore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import estrada.leon.rafael.readwatch.R;
import estrada.leon.rafael.readwatch.estudiante.adapter.AdapterComentario;
import estrada.leon.rafael.readwatch.estudiante.dialog.DialogModificarEliminar;
import estrada.leon.rafael.readwatch.estudiante.dialog.DialogSubirVideo;
import estrada.leon.rafael.readwatch.estudiante.dialog.Dialog_Subir_documento;
import estrada.leon.rafael.readwatch.estudiante.interfaces.Item;
import estrada.leon.rafael.readwatch.estudiante.pojo.Comentarios;
import estrada.leon.rafael.readwatch.estudiante.pojo.Documentos;
import estrada.leon.rafael.readwatch.estudiante.pojo.Reportes;
import estrada.leon.rafael.readwatch.estudiante.pojo.Videos;
import estrada.leon.rafael.readwatch.general.fragments.leerDocumentos;
import estrada.leon.rafael.readwatch.general.pojo.Sesion;

public class MainComentario extends AppCompatActivity implements  Response.Listener<JSONObject>,
        Response.ErrorListener, AdapterComentario.OnComentariosListener,
        DialogModificarEliminar.IOpcionesComentario, DialogModificarEliminar.IOpcionesVidDoc,
        leerDocumentos.OnFragmentInteractionListener{
    JsonObjectRequest jsonObjectRequest;
    RequestQueue request;
    RecyclerView recycler;
    List<Item> list= new ArrayList<>();
    AdapterComentario adapterComentario;
    int idVidDoc=0,idUsuario,idPregunta=0;
    Context contexto=this;
    ProgressDialog progreso;
    private int []idComentarioUsuario;
    private int []idVideoEnComentarioUsuario;
    Documentos documento;

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
                    soloUnComentario(txtNuevoComentario.getText().toString());
                    request = Volley.newRequestQueue(getApplicationContext());
                }else {
                    insertarComentarioPreg(txtNuevoComentario.getText().toString());
                    request = Volley.newRequestQueue(getApplicationContext());
                }
                txtNuevoComentario.setText("");
                txtNuevoComentario.clearFocus();
                View view = getCurrentFocus();
                if (view != null) {
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
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

    private void soloUnComentario(final String texto) {
        list= new ArrayList<>();
        String url;
        url = "https://readandwatch.herokuapp.com/php/soloUnComentario.php?" +
                "idUsuario="+idUsuario+"&idVidDoc="+idVidDoc;
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray json;
                        JSONObject jsonObject=null;
                        json = response.optJSONArray("usuario");
                        int numeroComentario;
                        try {
                            jsonObject = json.getJSONObject(0);
                            numeroComentario = jsonObject.getInt("idComentario");
                            if(numeroComentario==0){
                                insertarComentario(texto);
                            }
                            else {Toast.makeText(getApplicationContext(),"Ya cuentas con un comentario",Toast.LENGTH_LONG).show();}

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, this);
        request.add(jsonObjectRequest);
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
                        buscarComentariosUsuario();
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
                        buscarComentariosUsuario();

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
                        json = response.optJSONArray("comentario");
                        String nombre,comentarioString,descripcion,rutaImagen,ruta, eliminado;
                        int idVidDoc,idUsuario,idComentario;
                        try {
                            if(json!=null) {
                                for (int i = 0; i < json.length(); i++) {
                                    jsonObject = json.getJSONObject(i);
                                    nombre = jsonObject.optString("idUsuario");
                                    comentarioString = jsonObject.optString("texto");
                                    idComentario=jsonObject.optInt("idComentario");
                                    eliminado= jsonObject.optString("eliminado");
                                    if (eliminado.equals("N")) {
                                        comentario = new Comentarios(nombre, comentarioString, idComentario);
                                        list.add(comentario);
                                    }
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
                                    ruta = jsonObject.optString("ruta");
                                    eliminado = jsonObject.optString("eliminado");
                                    if(eliminado.equals("N")) {
                                        if (jsonObject.optString("tipo").equals("v")) {
                                            video = new Videos(Integer.toString(idUsuario), descripcion, rutaImagen, idUsuario, idVidDoc, ruta);
                                            list.add(video);
                                        } else {
                                            documento = new Documentos(Integer.toString(idUsuario), descripcion, rutaImagen, idUsuario, idVidDoc);
                                            FileLoader.with(contexto).load("https://readandwatch.000webhostapp.com/archivos/"+documento.getIdVidDoc()+".pdf").fromDirectory("PDFFiles", FileLoader.DIR_EXTERNAL_PUBLIC).asFile(new FileRequestListener<File>() {
                                                @Override
                                                public void onLoad(FileLoadRequest request, FileResponse<File> response) {
                                                    File pdf = response.getBody();

                                                    FileInputStream fileInputStream = null;
                                                    byte[] bytesArray = null;
                                                    try {
                                                        bytesArray = new byte[(int) pdf.length()];
                                                    }catch(Exception e){
                                                        Toast.makeText(contexto, "El archivo es demasiado grande.", Toast.LENGTH_SHORT).show();
                                                    }
                                                    //read file into bytes[]
                                                    try {
                                                        fileInputStream = new FileInputStream(pdf);
                                                        fileInputStream.read(bytesArray);
                                                        int pageNumber = 0;
                                                        PdfiumCore pdfiumCore = new PdfiumCore(contexto);
                                                        PdfDocument pdfDocument = null;
                                                        pdfDocument = pdfiumCore.newDocument(bytesArray);
                                                        pdfiumCore.openPage(pdfDocument, pageNumber);
                                                        //int width = pdfiumCore.getPageWidthPoint(pdfDocument, pageNumber);
                                                        //int height = pdfiumCore.getPageHeightPoint(pdfDocument, pageNumber);
                                                        int width = 300;
                                                        int height = 300;
                                                        Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                                                        pdfiumCore.renderPageBitmap(pdfDocument, bmp, pageNumber, 0, 0, width, height);
                                                        pdfiumCore.closeDocument(pdfDocument); // important!
                                                        documento.setImagen(bmp);
                                                        list.add(documento);
                                                        adapterComentario = new AdapterComentario(contexto, list,(MainComentario)contexto,idComentarioUsuario,idVideoEnComentarioUsuario);
                                                        recycler.setAdapter(adapterComentario);
                                                        adapterComentario.refresh(list);
                                                        recycler.invalidate();
                                                    } catch (IOException e) {
                                                        progreso.hide();
                                                        Toast.makeText(contexto, "Error al descargar los archivos.", Toast.LENGTH_SHORT).show();
                                                    }
                                                }

                                                @Override
                                                public void onError(FileLoadRequest request, Throwable t) {
                                                    Toast.makeText(contexto, t.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                        }
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        adapterComentario = new AdapterComentario(contexto, list,(MainComentario)contexto,idComentarioUsuario,idVideoEnComentarioUsuario);
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
                JSONArray json2 = response.optJSONArray("videosUsuario");
                JSONObject jsonObject=null;
                int tamaño=0;
                int tamaño2=0;
                if(json!=null) {
                    if (json.length() < 1) {

                    } else {
                        tamaño = json.length();
                    }
                }
                if(json2!=null) {
                    if (json2.length() > 0) {
                        tamaño2 = json2.length();
                    }

                }
                if (tamaño == 0) {
                    idComentarioUsuario = new int[1];
                    idComentarioUsuario[0] = 0;
                }
                if(tamaño2==0){
                    idVideoEnComentarioUsuario = new int[1];
                    idVideoEnComentarioUsuario[0] = 0;
                }
                idComentarioUsuario = new int[tamaño];
                idVideoEnComentarioUsuario = new int[tamaño2];
                if(json!=null) {
                    for (int i = 0; i < json.length(); i++) {
                        try {
                            jsonObject = json.getJSONObject(i);
                            idComentarioUsuario[i] = jsonObject.getInt("idComentario");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if(json2!=null) {
                    for (int i = 0; i < tamaño2; i++) {
                        try {
                            jsonObject = json2.getJSONObject(i);
                            idVideoEnComentarioUsuario[i] = jsonObject.getInt("idVidDoc");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
        String nombre,comentarioString, eliminado;
        int idComentario;
        try {
            for(int i=0;i<json.length();i++) {
                jsonObject = json.getJSONObject(i);
                nombre = jsonObject.optString("idUsuario");
                comentarioString = jsonObject.optString("texto");
                eliminado = jsonObject.optString("eliminado");
                idComentario = jsonObject.optInt("idComentario");
                if(eliminado.equals("N")) {
                    comentario = new Comentarios(nombre, comentarioString, idComentario);
                    list.add(comentario);
                }
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
    public void opcionVideoClick(int position, List<Item> list) {
        int idVideoDoc=((Videos)list.get(position)).getIdVidDoc();
        SharedPreferences preferences = getSharedPreferences("VidDocSeleccionado", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("idVidDoc", idVideoDoc);
        editor.commit();
        DialogModificarEliminar nuevo = new DialogModificarEliminar();
        nuevo.setOpcion(1);
        nuevo.show(getSupportFragmentManager(), "ejemplo");

    }

    @Override
    public void opcionDocClick(int position, List<Item> list) {
        int idVideoDoc=((Documentos)list.get(position)).getIdVidDoc();
        SharedPreferences preferences = getSharedPreferences("VidDocSeleccionado", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("idVidDoc", idVideoDoc);
        editor.commit();
        DialogModificarEliminar nuevo = new DialogModificarEliminar();
        nuevo.setOpcion(2);
        nuevo.show(getSupportFragmentManager(), "ejemplo");

    }

    @Override
    public void reportarComentario(int position, List<Item> list) {
            int idComentario = ((Comentarios)list.get(position)).getIdComentario();
            int idUsuario= Sesion.getSesion().getId();
            final Reportes reporte = new Reportes();
            SharedPreferences preferences = getSharedPreferences("reporte", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("idComentario", idComentario);
            editor.putInt("idUsuario",idUsuario);
            editor.commit();

            final CharSequence iCharSequence [] = {"Contenido sexual u obseno", "Es spam", "No es apropiado al tema o materia", "No se puede visualizar"};
            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
            TextView title = new TextView(this);
            title.setText("Reportar");
            title.setGravity(Gravity.CENTER);
            title.setTextSize(24 );
            title.setTextColor(Color.BLACK);
            builder.setCustomTitle(title);


            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    SharedPreferences preferences = MainComentario.this.getSharedPreferences("reporte", Context.MODE_PRIVATE);
                    int idComentario = preferences.getInt("idComentario", 0);
                    int idUsuario = preferences.getInt("idUsuario", 0);
                    String tipo = reporte.getMotivo();
                    reportarComentarioWebService(idUsuario,idComentario,tipo);
                }
            });
            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

            builder.setSingleChoiceItems(iCharSequence, -1, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    reporte.setMotivo((iCharSequence[which]).toString());
                }
            });

            android.support.v7.app.AlertDialog alertDialog = builder.create();
            alertDialog.setCancelable(false);
            alertDialog.show();
        }

    public void reportarComentarioWebService(int idUsuario, final int idComentario, String tipo){//tipo==motivo
        JsonObjectRequest jsonObjectRequest;
        RequestQueue request;
        String url;
        url = getString(R.string.ip)+"/php/reportarComentario.php?"
                +"idComentario="+idComentario+"&tipo="+tipo+"&idUsuario="+idUsuario;
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                boolean exito;
                boolean repetido;
                JSONArray json;
                JSONObject jsonObject=null;
                json = response.optJSONArray("usuario");
                try {
                    jsonObject = json.getJSONObject(0);
                    repetido = jsonObject.getBoolean("repetido");
                    jsonObject = json.getJSONObject(1);
                    exito = jsonObject.getBoolean("success");
                    if(exito && !repetido){
                        Toast.makeText(MainComentario.this, "Reporte hecho con exito",
                                Toast.LENGTH_SHORT).show();
                    }else if(repetido){
                        Toast.makeText(MainComentario.this, "Ya has hecho un reporte" +
                                        " a este comentario.",
                                Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(MainComentario.this, "Error al realizar el reporte.",
                                Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(MainComentario.this, "Error interno.", Toast.LENGTH_SHORT).show();
                }


                progreso.hide();
                reporteSexual(idComentario);
                reporteSpam(idComentario);
                reporteNoApropiado(idComentario);
                reporteNoSeVe(idComentario);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progreso.hide();
                Toast.makeText(MainComentario.this, "Error en la comunicación.", Toast.LENGTH_SHORT).show();
            }
        });
        request= Volley.newRequestQueue(this);
        progreso = new ProgressDialog(this);
        progreso.setMessage("Haciendo reporte...");
        progreso.show();
        request.add(jsonObjectRequest);

    }

    private void reporteNoSeVe(int idComentario) {
        String url;
        url = "https://readandwatch.herokuapp.com/php/reporteComentarioNoSeVe.php?idComentario="+idComentario;
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

    private void reporteNoApropiado(int idComentario) {
        String url;
        url = "https://readandwatch.herokuapp.com/php/reporteComentarioNoApropiado.php?idComentario="+idComentario;
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

    private void reporteSpam(int idComentario) {
        String url;
        url = "https://readandwatch.herokuapp.com/php/reporteComentarioSpam.php?idComentario="+idComentario;
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

    private void reporteSexual(int idComentario) {
        String url;
        url = "https://readandwatch.herokuapp.com/php/reporteComentarioSexual.php?idComentario="+idComentario;
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

    @Override
    public void reportarVideo(int position, List<Item> list) {
        int idVidDoc = ((Videos)(list.get(position))).getIdVidDoc();
        int idUsuario= Sesion.getSesion().getId();
        final Reportes reporte = new Reportes();
        SharedPreferences preferences = getSharedPreferences("reporte", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("idVidDoc", idVidDoc);
        editor.putInt("idUsuario",idUsuario);
        editor.commit();

        final CharSequence iCharSequence [] = {"Contenido sexual u obseno", "Es spam", "No es apropiado al tema o materia", "No se puede visualizar"};
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        TextView title = new TextView(this);
        title.setText("Reportar");
        title.setGravity(Gravity.CENTER);
        title.setTextSize(24 );
        title.setTextColor(Color.BLACK);
        builder.setCustomTitle(title);


        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SharedPreferences preferences = MainComentario.this.getSharedPreferences("reporte", Context.MODE_PRIVATE);
                int idVidDoc = preferences.getInt("idVidDoc", 0);
                int idUsuario = preferences.getInt("idUsuario", 0);
                String tipo = reporte.getMotivo();
                reportarVidDoc(idUsuario,idVidDoc,tipo);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.setSingleChoiceItems(iCharSequence, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                reporte.setMotivo((iCharSequence[which]).toString());
            }
        });

        android.support.v7.app.AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    @Override
    public void reportarDoc(int idVidDoc) {
        int idUsuario= Sesion.getSesion().getId();
        final Reportes reporte = new Reportes();
        SharedPreferences preferences = getSharedPreferences("reporte", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("idVidDoc", idVidDoc);
        editor.putInt("idUsuario",idUsuario);
        editor.commit();

        final CharSequence iCharSequence [] = {"Contenido sexual u obseno", "Es spam", "No es apropiado al tema o materia", "No se puede visualizar"};
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        TextView title = new TextView(this);
        title.setText("Reportar");
        title.setGravity(Gravity.CENTER);
        title.setTextSize(24 );
        title.setTextColor(Color.BLACK);
        builder.setCustomTitle(title);


        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SharedPreferences preferences = MainComentario.this.getSharedPreferences("reporte", Context.MODE_PRIVATE);
                int idVidDoc = preferences.getInt("idVidDoc", 0);
                int idUsuario = preferences.getInt("idUsuario", 0);
                String tipo = reporte.getMotivo();
                reportarVidDoc(idUsuario,idVidDoc,tipo);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.setSingleChoiceItems(iCharSequence, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                reporte.setMotivo((iCharSequence[which]).toString());
            }
        });

        android.support.v7.app.AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    @Override
    public void leerDoc(int idVidDoc) {
        Bundle bundle = new Bundle();
        bundle.putInt("idVidDoc",idVidDoc);

        Fragment fragment =new leerDocumentos();
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.layoutComentarios,fragment).addToBackStack(null).commit();
    }

    public void reportarVidDoc(int idUsuario,int idVidDoc, String tipo){//tipo==motivo
        JsonObjectRequest jsonObjectRequest;
        RequestQueue request;
        String url;
        url = getString(R.string.ip)+"/php/reportarVidDoc.php?"
                +"idVidDoc="+idVidDoc+"&tipo="+tipo+"&idUsuario="+idUsuario;
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                boolean exito;
                boolean repetido;
                JSONArray json;
                JSONObject jsonObject=null;
                json = response.optJSONArray("usuario");
                try {
                    jsonObject = json.getJSONObject(0);
                    repetido = jsonObject.getBoolean("repetido");
                    jsonObject = json.getJSONObject(1);
                    exito = jsonObject.getBoolean("success");
                    if(exito && !repetido){
                        Toast.makeText(MainComentario.this, "Reporte hecho con exito",
                                Toast.LENGTH_SHORT).show();
                    }else if(repetido){
                        Toast.makeText(MainComentario.this, "Ya has hecho un reporte" +
                                        " a este video o documento.",
                                Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(MainComentario.this, "Error al realizar el reporte.",
                                Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(MainComentario.this, "Error interno.", Toast.LENGTH_SHORT).show();
                }


                progreso.hide();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progreso.hide();
                Toast.makeText(MainComentario.this, "Error en la comunicación.", Toast.LENGTH_SHORT).show();
            }
        });
        request= Volley.newRequestQueue(this);
        progreso = new ProgressDialog(this);
        progreso.setMessage("Haciendo reporte...");
        progreso.show();
        request.add(jsonObjectRequest);

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
                                buscarComentariosUsuario();
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
                buscarComentariosUsuario();
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

    @Override
    public void resubirVideo() {
        DialogSubirVideo nuevo = new DialogSubirVideo();
        nuevo.setModo(DialogSubirVideo.RESUBIR);
        nuevo.show(getSupportFragmentManager(), "ejemplo");
    }


    @Override
    public void resubirDoc() {
        Dialog_Subir_documento nuevo = new Dialog_Subir_documento();
        nuevo.setModo(DialogSubirVideo.RESUBIR);
        nuevo.show(getSupportFragmentManager(), "ejemplo");
    }

    @Override
    public void eliminarVidDoc(int idVidDoc, int opc) {
        ProgressDialog progreso;
        JsonObjectRequest jsonObjectRequest;
        RequestQueue request;
        String url;
        String ip=getString(R.string.ip);
        url = ip+"/php/eliminarVideo.php?" +
                "idVidDoc="+idVidDoc;
        url=url.replace(" ", "%20");
        progreso = new ProgressDialog(this);
        progreso.setMessage("Cargando...");
        progreso.show();
        final int a = opc;
        request= Volley.newRequestQueue(this);
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (a == 1) {
                    Toast.makeText(MainComentario.this, "Video eliminado con éxito", Toast.LENGTH_SHORT).show();
                }
                else { Toast.makeText(MainComentario.this, "Documento eliminado con éxito", Toast.LENGTH_SHORT).show();}
                buscarComentariosUsuario();
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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}