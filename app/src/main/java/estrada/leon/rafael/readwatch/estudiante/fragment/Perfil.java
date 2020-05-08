package estrada.leon.rafael.readwatch.estudiante.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import estrada.leon.rafael.readwatch.R;
import estrada.leon.rafael.readwatch.estudiante.pojo.Reportes;
import estrada.leon.rafael.readwatch.general.pojo.Estudiante;
import estrada.leon.rafael.readwatch.estudiante.adapter.PerfilAdapter;
import estrada.leon.rafael.readwatch.estudiante.interfaces.Item;
import estrada.leon.rafael.readwatch.estudiante.interfaces.iComunicacionFragments;
import estrada.leon.rafael.readwatch.estudiante.pojo.Documentos;
import estrada.leon.rafael.readwatch.estudiante.pojo.Videos;
import estrada.leon.rafael.readwatch.general.pojo.Sesion;

import static android.app.Activity.RESULT_OK;

public class Perfil extends Fragment implements PerfilAdapter.OnPerfilListener, Response.Listener<JSONObject>, Response.ErrorListener{
    List<Item> list= new ArrayList<>();
    ProgressDialog progreso;
    TextView lblNombreApellidos,lblDescripcion,lblCelular,lblReportar;
    RequestQueue request;
    int idUsuarios;
    int perfilEstudiante;
    int nuevo, sesion, opcion;
    String dato;
    RecyclerView recyclerPerfil;
    PerfilAdapter perfilAdapter;
    ImageView fotoPerfil, imgCambioFoto;
    Button btnEditar, btnEditarFoto;
    Bitmap  bitmap2;
    int []idUsuarioVidDocFav;
    Activity actividad;
    Context contexto;
    private final boolean BUSCAR=true;
    JsonObjectRequest jsonObjectRequest;
    private iComunicacionFragments interfaceFragments;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    public Perfil(){}

    @SuppressLint("ValidFragment")
    public Perfil(int nuevo, int sesion, int opcion) {
        this.nuevo=nuevo;
        this.sesion=sesion;
        this.opcion = opcion;

    }




    public void setIdUsuario(int idUsuario){
        this.idUsuarios = idUsuario;
    }
    void cargarDatos(){
        list= new ArrayList<>();
        idUsuarios = nuevo;
        String url;
        url = "https://readandwatch.herokuapp.com/php/cargarVidDocPerfil.php?" +
                "idUsuario="+idUsuarios+"&tipo=v";
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                JSONArray json;
                JSONObject jsonObject=null;
                Videos video;
                json = response.optJSONArray("usuario");
                String descripcion,miniatura,ruta;
                int idUsuario,idVidDoc;
                list=new ArrayList<>();
                try {
                    for(int i=0;i<json.length();i++){
                        jsonObject=json.getJSONObject(i);
                        idUsuario=jsonObject.optInt("idUsuario");
                        descripcion=jsonObject.optString("descripcion");
                        miniatura=jsonObject.optString("rutaImagen");
                        idVidDoc=jsonObject.optInt("idVidDoc");
                        ruta = jsonObject.optString("ruta");
                        list.add(new Videos(String.valueOf(idUsuario),descripcion,"",idUsuario,idVidDoc,ruta));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            //    perfilAdapter=new PerfilAdapter(getContext(),list,Perfil.this,nuevo, idUsuarioVidDocFav);
             //  recyclerPerfil.setAdapter(perfilAdapter);
                idUsuarios=sesion;
                cargarDoc();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                cargarDoc();
            }
        });
        request.add(jsonObjectRequest);

    }

    private void cargarDoc() {
        idUsuarios = nuevo;
        String url;
        url = "https://readandwatch.herokuapp.com/php/cargarVidDocPerfil.php?" +
                "idUsuario="+idUsuarios+"&tipo=d";
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                JSONArray json;
                JSONObject jsonObject=null;
                json = response.optJSONArray("usuario");
                String descripcion,miniatura,ruta;
                int idUsuario,idVidDoc;
                try {
                    for(int i=0;i<json.length();i++){
                        jsonObject=json.getJSONObject(i);
                        idUsuario=jsonObject.optInt("idUsuario");
                        descripcion=jsonObject.optString("descripcion");
                        miniatura=jsonObject.optString("rutaImagen");
                        idVidDoc=jsonObject.optInt("idVidDoc");
                        ruta = jsonObject.optString("ruta");
                        list.add(new Documentos(String.valueOf(idUsuario), descripcion, miniatura, idUsuario, idVidDoc));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                idUsuarios=sesion;
                perfilAdapter=new PerfilAdapter(getContext(),list,Perfil.this,nuevo,idUsuarioVidDocFav);
                recyclerPerfil.setAdapter(perfilAdapter);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                perfilAdapter=new PerfilAdapter(getContext(),list,Perfil.this,nuevo,idUsuarioVidDocFav);
                recyclerPerfil.setAdapter(perfilAdapter);
            }
        });
        request.add(jsonObjectRequest);

    }


    private void cargarDatosPerfil() {

        final int idUsuarios = Sesion.getSesion().getId();
        String url;
        url = "https://readandwatch.herokuapp.com/php/cargarVidDocPerfil.php?" +
                "idUsuario="+idUsuarios+"&tipo=v";
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                JSONArray json;
                JSONObject jsonObject=null;
                Videos video;
                json = response.optJSONArray("usuario");
                String descripcion,miniatura,ruta;
                int idUsuario,idVidDoc;
                try {
                    for(int i=0;i<json.length();i++){
                        jsonObject=json.getJSONObject(i);
                        idUsuario=jsonObject.optInt("idUsuario");
                        descripcion=jsonObject.optString("descripcion");
                        miniatura=jsonObject.optString("rutaImagen");
                        idVidDoc=jsonObject.optInt("idVidDoc");
                        ruta = jsonObject.optString("ruta");
                        list.add(new Videos(String.valueOf(idUsuario),descripcion,"",idUsuario,idVidDoc,ruta));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

             //   perfilAdapter=new PerfilAdapter(getContext(),list,Perfil.this,idUsuarios,idUsuarioVidDocFav);
              //  recyclerPerfil.setAdapter(perfilAdapter);
                cargarDocPerfil();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                cargarDocPerfil();
            }
        });
        request.add(jsonObjectRequest);

    }

    private void cargarDocPerfil() {
        final int idUsuarios = Sesion.getSesion().getId();
        String url;
        url = "https://readandwatch.herokuapp.com/php/cargarVidDocPerfil.php?" +
                "idUsuario="+idUsuarios+"&tipo=d";
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                JSONArray json;
                JSONObject jsonObject=null;
                json = response.optJSONArray("usuario");
                String descripcion,miniatura,ruta;
                int idUsuario,idVidDoc;

                try {
                    for(int i=0;i<json.length();i++){
                        jsonObject=json.getJSONObject(i);
                        idUsuario=jsonObject.optInt("idUsuario");
                        descripcion=jsonObject.optString("descripcion");
                        miniatura=jsonObject.optString("rutaImagen");
                        idVidDoc=jsonObject.optInt("idVidDoc");
                        ruta = jsonObject.optString("ruta");
                        list.add(new Documentos(String.valueOf(idUsuario), descripcion, miniatura, idUsuario, idVidDoc));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                perfilAdapter= new PerfilAdapter(getContext(),list, Perfil.this, idUsuarios,idUsuarioVidDocFav);
                recyclerPerfil.setAdapter(perfilAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                perfilAdapter= new PerfilAdapter(getContext(),list, Perfil.this, idUsuarios,idUsuarioVidDocFav);
                recyclerPerfil.setAdapter(perfilAdapter);
            }
        });
        request.add(jsonObjectRequest);
    }

    public static Perfil newInstance(String param1, String param2) {
        Perfil fragment = new Perfil();
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

        request= Volley.newRequestQueue(getContext());

        SharedPreferences preferenc = getContext().getSharedPreferences("Dato perfil", Context.MODE_PRIVATE);
        dato = preferenc.getString("dato", "");
        if(dato.equalsIgnoreCase("PP")) {
            SharedPreferences preferences = getContext().getSharedPreferences("Datos usuario", Context.MODE_PRIVATE);
            idUsuarios = preferences.getInt("idUsuario", 0);
            cargarWebServices(1);
        }

        if(dato.equalsIgnoreCase("PO")) {
            SharedPreferences preference = getContext().getSharedPreferences("Perfil Estudiante", Context.MODE_PRIVATE);
            perfilEstudiante = preference.getInt("perfilEstudiante", 0);
            cargarWebServices(2);
        }

        SharedPreferences preference = getContext().getSharedPreferences("Dato perfil", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preference.edit();
        edit.putString("dato", "PP");
        edit.commit();


        View view;
        view = inflater.inflate(R.layout.fragment_perfil, container, false);
        fotoPerfil=view.findViewById(R.id.fotoPerfil);
        lblNombreApellidos=view.findViewById(R.id.lblNombreApellidos);
        lblDescripcion=view.findViewById(R.id.lblDescripcion);
        lblCelular=view.findViewById(R.id.lblCelular);
        lblReportar=view.findViewById(R.id.lblReportar);
        btnEditar = view.findViewById(R.id.btnEditar);
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interfaceFragments.mostrarGaleria();
            }
        });


        if(idUsuarios == Sesion.getSesion().getId()){
            lblReportar.setVisibility(View.GONE);
            btnEditar.setVisibility(View.VISIBLE);
        }else{btnEditar.setVisibility(View.GONE);}
        lblReportar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reportarPerfil();
            }


        });
        fotoPerfil.setImageResource(R.drawable.profilepic);
        recyclerPerfil=view.findViewById(R.id.recyclerPerfil);
        recyclerPerfil.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        buscarVideosFav();
        if(idUsuarios!= Sesion.getSesion().getId()) {
            cargarDatos();
        }else{
            cargarDatosPerfil();
        }


        return view;


    }

    public void obtenerMap(Bitmap mapa) {
        bitmap2 = mapa;
    }


    private String convertirImgString(Bitmap bitmap) {
        ByteArrayOutputStream array = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,array);
        byte[] imageByte = array.toByteArray();
        String imagenString = Base64.encodeToString(imageByte,Base64.DEFAULT);
        return imagenString;
    }


    private void buscarVideosFav() {
        int idUsuario = Sesion.getSesion().getId();
        String url = "https://readandwatch.herokuapp.com/php/buscarVideoFav.php?" +
                "idUsuario="+idUsuario;
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray json = response.optJSONArray("usuario");
                JSONObject jsonObject=null;
                if(json.length()<1){
                    idUsuarioVidDocFav = new int[1];
                    idUsuarioVidDocFav[0]=0;
                }else {
                    idUsuarioVidDocFav = new int[json.length()];
                }

                for(int i=0;i<json.length();i++){
                    try {
                        jsonObject=json.getJSONObject(i);
                        idUsuarioVidDocFav[i]= jsonObject.getInt("idVidDoc");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        request.add(jsonObjectRequest);
    }


    public void reportarPerfil(){
        int idPerfil = perfilEstudiante;
        int idUsuario= Sesion.getSesion().getId();
        final Reportes reporte = new Reportes();
        SharedPreferences preferences = getContext().getSharedPreferences("reporte", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("idPerfil", idPerfil);
        editor.putInt("idUsuario",idUsuario);
        editor.commit();

        final CharSequence iCharSequence [] = {"Contenido sexual u obseno", "Es spam"};
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext());
        TextView title = new TextView(getContext());
        title.setText("Reportar");
        title.setGravity(Gravity.CENTER);
        title.setTextSize(24 );
        title.setTextColor(Color.BLACK);
        builder.setCustomTitle(title);


        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SharedPreferences preferences = getContext().getSharedPreferences("reporte", Context.MODE_PRIVATE);
                int idPerfil = preferences.getInt("idPerfil", 0);
                int idUsuario = preferences.getInt("idUsuario", 0);
                String tipo = reporte.getMotivo();
                reportarPerfilWebService(idUsuario,idPerfil,tipo);
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
    public void reportarPerfilWebService(int idUsuario, final int idPerfil, String tipo) {//tipo==motivo
        JsonObjectRequest jsonObjectRequest;
        RequestQueue request;
        String url;
        url = getString(R.string.ip) + "/php/reportarPerfil.php?"
                + "idPerfil=" + idPerfil + "&tipo=" + tipo + "&idUsuario=" + idUsuario;
        url = url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                boolean exito;
                boolean repetido;
                JSONArray json;
                JSONObject jsonObject = null;
                json = response.optJSONArray("usuario");
                try {
                    jsonObject = json.getJSONObject(0);
                    repetido = jsonObject.getBoolean("repetido");
                    jsonObject = json.getJSONObject(1);
                    exito = jsonObject.getBoolean("success");
                    if (exito && !repetido) {
                        Toast.makeText(getContext(), "Reporte hecho con exito",
                                Toast.LENGTH_SHORT).show();
                    } else if (repetido) {
                        Toast.makeText(getContext(), "Ya has hecho un reporte" +
                                        " a este perfil.",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getContext(), "Error al realizar el reporte.",
                                Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Error interno.", Toast.LENGTH_SHORT).show();
                }


                progreso.hide();
                verificarPerfilSexual(idPerfil);
                verificarPerfilSpam(idPerfil);
               // verificarSpamTemaLibre(idPerfil);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progreso.hide();
                Toast.makeText(getContext(), "Error en la comunicación.", Toast.LENGTH_SHORT).show();
            }
        });
        request = Volley.newRequestQueue(getContext());
        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Haciendo reporte...");
        progreso.show();
        request.add(jsonObjectRequest);

    }

    private void verificarPerfilSpam(int idPerfil) {
        String url;
        url = "https://readandwatch.herokuapp.com/php/eliminarPerfilSpam.php?idPerfil="+idPerfil;
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

    private void verificarPerfilSexual(int idPerfil) {
        String url;
        url = "https://readandwatch.herokuapp.com/php/eliminarPerfilReporte.php?idPerfil="+idPerfil;
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

    private void cargarWebServices(int a) {
        String url;
        progreso = new ProgressDialog(contexto);
        progreso.setMessage("Cargando Datos...");
        progreso.show();
        if(a==2) {
            url = "https://readandwatch.herokuapp.com/php/buscarEstudiante.php?idUsuario=" + perfilEstudiante;
            url = url.replace(" ", "%20");
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        }
        if (a==1) {
            url = "https://readandwatch.herokuapp.com/php/buscarEstudiante.php?idUsuario=" + idUsuarios;
            url = url.replace(" ", "%20");
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        }
        request.add(jsonObjectRequest);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        Activity activity;
        super.onAttach(context);
        contexto = context;
        if (context instanceof Activity) {
            activity= (Activity) context;
            actividad = (Activity) context;
            interfaceFragments=(iComunicacionFragments)activity;
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
    public void onPerfilClick(int position, List<Item> lista, Toast toast) {
        interfaceFragments.onClickDocFavHolder(toast);
    }

    @Override
    public void comentarioClickVid(int position, List<Item> list) {
        SharedPreferences preferences = getContext().getSharedPreferences("Datos usuario", Context.MODE_PRIVATE);
        int idUsuario = preferences.getInt("idUsuario", 0);
        int idVidDoc =((Videos)(list.get(position))).getIdVidDoc();
        interfaceFragments.onClickComentario(idUsuario,idVidDoc,0);
    }

    @Override
    public void agregarFavoritos(int position, List<Item> list) {
        SharedPreferences preferences = getContext().getSharedPreferences("Datos usuario", Context.MODE_PRIVATE);
        int idUsuario = preferences.getInt("idUsuario", 0);
        int idVidDoc = ((Videos)(list.get(position))).getIdVidDoc();
        verificarExistencia(idUsuario, idVidDoc);
    }

    @Override
    public void agregarFavoritosDoc(int position, List<Item> list) {
        SharedPreferences preferences = getContext().getSharedPreferences("Datos usuario", Context.MODE_PRIVATE);
        int idUsuario = preferences.getInt("idUsuario", 0);
        int idVidDoc = ((Documentos)(list.get(position))).getIdVidDoc();
        verificarExistencia(idUsuario, idVidDoc);
    }

    @Override
    public void comentarioClickVidDoc(int position, List<Item> list) {
        SharedPreferences preferences = getContext().getSharedPreferences("Datos usuario", Context.MODE_PRIVATE);
        int idUsuario = preferences.getInt("idUsuario", 0);
        int idVidDoc =((Documentos)(list.get(position))).getIdVidDoc();
        interfaceFragments.onClickComentario(idUsuario,idVidDoc,0);

    }

    @Override
    public void reportarClick(int position, List<Item> list) {
        interfaceFragments.onClickReportarVidDoc(((Videos)(list.get(position))).getIdVidDoc());
    }

    @Override
    public void reportarClickDoc(int position, List<Item> list) {
        interfaceFragments.onClickReportarVidDoc(((Documentos)(list.get(position))).getIdVidDoc());
    }

    @Override
    public void opcionClickDoc(int position, List<Item> list) {
        SharedPreferences preferences = getContext().getSharedPreferences("Datos usuario", Context.MODE_PRIVATE);
        int idUsuario = preferences.getInt("idUsuario", 0);
        int idVidDoc = ((Documentos)(list.get(position))).getIdVidDoc();
        interfaceFragments.onClickOpcion(idUsuario,idVidDoc,2);
    }

    @Override
    public void opcionClick(int position, List<Item> list) {
        SharedPreferences preferences = getContext().getSharedPreferences("Datos usuario", Context.MODE_PRIVATE);
        int idUsuario = preferences.getInt("idUsuario", 0);
        int idVidDoc = ((Videos)(list.get(position))).getIdVidDoc();
        interfaceFragments.onClickOpcion(idUsuario,idVidDoc,1);
    }

    private void verificarExistencia(final int idUsuario, final int idVidDoc) {
        String url;
        url = "https://readandwatch.herokuapp.com/php/existenciaFavorito.php?" +
                "idUsuario="+idUsuario+"&idVidDoc="+idVidDoc;
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                int idFavorito;
                JSONArray json;
                JSONObject jsonObject=null;
                json = response.optJSONArray("usuario");
                for(int i=0;i<json.length();i++) {
                    try {
                        jsonObject = json.getJSONObject(i);
                        idFavorito = jsonObject.getInt("idFavorito");

                        if (idFavorito==0){
                            subirFavoritos(idUsuario, idVidDoc);

                        }
                        else {
                            deleteFavoritos(idFavorito);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                progreso.hide();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progreso.hide();
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        request.add(jsonObjectRequest);
    }

    private void deleteFavoritos(int idFavorito) {
        String url;
        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();
        url = "https://readandwatch.herokuapp.com/php/eliminarFavorito.php?" +
                "idFavorito="+idFavorito;
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progreso.hide();
                Toast.makeText(getContext(), "Se elimino de favoritos", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progreso.hide();
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        request.add(jsonObjectRequest);
    }

    private void subirFavoritos(int idUsuario, int idVidDoc) {
        String url;
        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();
        url = "https://readandwatch.herokuapp.com/php/insertFavorito.php?" +
                "idUsuario="+idUsuario+"&idVidDoc="+idVidDoc;
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progreso.hide();
                Toast.makeText(getContext(), "Se agrego a favoritos", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progreso.hide();
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        request.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.hide();
        Toast.makeText(getContext(), "No se pudieron mostrar los datos "+error.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        Estudiante estudiante = new Estudiante();
        JSONArray json = response.optJSONArray("usuario");
        JSONObject jsonObject=null;
        try {
            jsonObject=json.getJSONObject(0);
            estudiante.setNombre(jsonObject.optString("nombre"));
            estudiante.setApellidos(jsonObject.optString("apellidos"));
            estudiante.setContrasena(jsonObject.optString("contrasena"));
            estudiante.setDescripcion(jsonObject.optString("descripcion"));
            estudiante.setTelefono(jsonObject.optString("telefono"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String foto="";

                try {
                    jsonObject = json.getJSONObject(0);
                    foto = jsonObject.optString("rutaFoto");
                } catch (JSONException e) {
                    e.printStackTrace();
                }



        lblNombreApellidos.setText(estudiante.getNombre()+" "+estudiante.getApellidos());
        lblDescripcion.setText(estudiante.getDescripcion());
        lblCelular.setText(estudiante.getTelefono());
        cargarFoto(foto);
    }

    private void cargarFoto(String foto) {
            request= Volley.newRequestQueue(getContext());
            ImageRequest imageRequest= new ImageRequest(foto, new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    progreso.hide();
                    fotoPerfil.setImageBitmap(response);
                }

            },0,0,ImageView.ScaleType.CENTER,null, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    progreso.hide();
                    Toast.makeText(getContext(), "No se pudieron cargar las imagenes", Toast.LENGTH_SHORT).show();
                }});
            request.add(imageRequest);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
