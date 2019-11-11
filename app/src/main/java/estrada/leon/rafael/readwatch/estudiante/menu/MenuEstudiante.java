package estrada.leon.rafael.readwatch.estudiante.menu;

import android.Manifest;
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
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.webkit.MimeTypeMap;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nbsp.materialfilepicker.ui.FilePickerActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import estrada.leon.rafael.readwatch.estudiante.dialog.DialogModificarEliminar;
import estrada.leon.rafael.readwatch.estudiante.dialog.DialogHacerPregunta;
import estrada.leon.rafael.readwatch.estudiante.dialog.DialogSubirVideo;
import estrada.leon.rafael.readwatch.estudiante.dialog.Dialog_Subir_documento;
import estrada.leon.rafael.readwatch.estudiante.fragment.ElegirDocumento;
import estrada.leon.rafael.readwatch.estudiante.fragment.ElegirMateria;
import estrada.leon.rafael.readwatch.estudiante.fragment.ElegirTema;
import estrada.leon.rafael.readwatch.estudiante.fragment.ElegirVideo;
import estrada.leon.rafael.readwatch.estudiante.fragment.Favoritos;
import estrada.leon.rafael.readwatch.estudiante.fragment.Historial;
import estrada.leon.rafael.readwatch.estudiante.fragment.MainComentario;
import estrada.leon.rafael.readwatch.estudiante.fragment.MateriasPropuestas;
import estrada.leon.rafael.readwatch.estudiante.fragment.ModificarEstudiante;
import estrada.leon.rafael.readwatch.estudiante.fragment.Perfil;
import estrada.leon.rafael.readwatch.estudiante.fragment.PreguntasTemaLibre;
import estrada.leon.rafael.readwatch.estudiante.fragment.TemasPropuestos;
import estrada.leon.rafael.readwatch.estudiante.fragment.lista_materias;
import estrada.leon.rafael.readwatch.estudiante.interfaces.iComunicacionFragments;
import estrada.leon.rafael.readwatch.R;
import estrada.leon.rafael.readwatch.estudiante.fragment.SeleccionarSemestre;
import estrada.leon.rafael.readwatch.estudiante.pojo.Reportes;
import estrada.leon.rafael.readwatch.general.fragments.leerDocumentos;
import estrada.leon.rafael.readwatch.general.pojo.Sesion;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class  MenuEstudiante extends AppCompatActivity
        implements iComunicacionFragments,NavigationView.OnNavigationItemSelectedListener,
        ElegirMateria.OnFragmentInteractionListener, SeleccionarSemestre.OnFragmentInteractionListener,
        ElegirTema.OnFragmentInteractionListener, ElegirVideo.OnFragmentInteractionListener,
        ElegirDocumento.OnFragmentInteractionListener, Historial.OnFragmentInteractionListener,
        PreguntasTemaLibre.OnFragmentInteractionListener, Favoritos.OnFragmentInteractionListener,
        TemasPropuestos.OnFragmentInteractionListener, Perfil.OnFragmentInteractionListener,
        lista_materias.OnFragmentInteractionListener,
        MateriasPropuestas.OnFragmentInteractionListener,
        ModificarEstudiante.OnFragmentInteractionListener,
        DialogModificarEliminar.IOpcionesVidDoc,
        leerDocumentos.OnFragmentInteractionListener {
    Fragment fragment;
    TextView titulo;
    RequestQueue request;
    Button btnEditarFoto;
    ImageView imgCambioFoto;
    ProgressDialog progreso;
    Bitmap bitmap;
    JsonObjectRequest jsonObjectRequest;

    Intent data;
    public Thread hilo;
    public String nombreArchivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        request = Volley.newRequestQueue(getApplicationContext());
        setContentView(R.layout.activity_menu_estudiante);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        titulo=findViewById(R.id.toolbar_title);
        titulo.setText("Elige una materia");
        fragment =new ElegirMateria();
        getSupportFragmentManager().beginTransaction().replace(R.id.layoutPrincipal,fragment).commit();


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_estudiante, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_videosArchivos) {
            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.layoutPrincipal,new ElegirMateria()).addToBackStack(null).commit();
            titulo.setText("VideosAdm");
        } else if (id == R.id.nav_temasLibres) {
            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.layoutPrincipal,new PreguntasTemaLibre()).addToBackStack(null).commit();
            titulo.setText("Temas libres");
        } else if (id == R.id.nav_favoritos) {
            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.layoutPrincipal,new Favoritos()).addToBackStack(null).commit();
            titulo.setText("Favoritos");
        } else if (id == R.id.nav_Perfil) {
            fragment =new Perfil();
            getSupportFragmentManager().beginTransaction().replace(R.id.layoutPrincipal,fragment).addToBackStack(null).commit();
            titulo.setText("");
        } else if (id == R.id.nav_editarPerfil) {
            fragment =new ModificarEstudiante();
            getSupportFragmentManager().beginTransaction().replace(R.id.layoutPrincipal,fragment).addToBackStack(null).commit();
            titulo.setText("Editar Perfil");
        }else if (id == R.id.nav_subirVideo) {
            DialogSubirVideo nuevo = new DialogSubirVideo();
            nuevo.show(getSupportFragmentManager(), "ejemplo");
        } else if (id == R.id.nav_subirArchivo) {
            Dialog_Subir_documento nuevo = new Dialog_Subir_documento();
            nuevo.show(getSupportFragmentManager(), "ejemplo");
        }else if(id==R.id.nav_historial) {
            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.layoutPrincipal,new Historial()).addToBackStack(null).commit();
            titulo.setText("Historial");
        }else if(id==R.id.nav_salir){
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void seleccionarSemestre(int idMateria) {
        fragment =new SeleccionarSemestre();
        getSupportFragmentManager().beginTransaction().replace(R.id.layoutPrincipal,fragment).addToBackStack(null).commit();
        titulo.setText("Seleccione el semestre");
        guardarPreferenciasMateria(idMateria);
    }

    private void guardarPreferenciasMateria(int materia) {
        SharedPreferences preferences = getSharedPreferences("Materia", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("materia", materia);
        Toast.makeText(this, "La materia es : "+materia, Toast.LENGTH_SHORT).show();
        editor.commit();
    }

    @Override
    public void seleccionarTema(int semestre) {
        fragment =new ElegirTema();
        getSupportFragmentManager().beginTransaction().replace(R.id.layoutPrincipal,fragment).addToBackStack(null).commit();
        titulo.setText("Elige un tema");

        guardarPreferencias(semestre);
    }

    private void guardarPreferencias(int semestre) {
        SharedPreferences preferences = getSharedPreferences("Semestre", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("semestre", semestre);
        Toast.makeText(this, "El semestre es: "+semestre, Toast.LENGTH_SHORT).show();
        editor.commit();
    }

    @Override
    public void seleccionarVideo(int idTema, int idUsuario) {
        fragment =new ElegirVideo();
        getSupportFragmentManager().beginTransaction().replace(R.id.layoutPrincipal,fragment).addToBackStack(null).commit();
        titulo.setText("Videos");

        guardarPreferenciasTema(idTema);

    }

    private void guardarPreferenciasTema(int tema) {
        SharedPreferences preferences = getSharedPreferences("Tema", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("tema", tema);
          Toast.makeText(this, "El tema elegido es: "+tema, Toast.LENGTH_SHORT).show();
        editor.commit();
    }

    @Override
    public void onClickDocumentosHolder(Toast toast) {
        toast.show();
    }

    @Override
    public void onClickTemasLibresHolder(Toast toast) {
        toast.show();
    }

    @Override
    public void onClickVidFavHolder(Toast toast) {
        toast.show();
    }

    @Override
    public void onClickDocFavHolder(Toast toast) {
        toast.show();
    }

    @Override
    public void onClickNuevaPregunta() {
        DialogHacerPregunta nuevo = new DialogHacerPregunta();
        nuevo.show(getSupportFragmentManager(), "ejemplo");
    }

    @Override
    public void onClickProponerTema() {
        fragment =new TemasPropuestos();
        getSupportFragmentManager().beginTransaction().replace(R.id.layoutPrincipal,fragment).addToBackStack(null).commit();
        titulo.setText("Temas propuestos");
        Toast.makeText(this,"Vote por una propuesta",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClickProponerMateria() {
        fragment =new MateriasPropuestas();
        getSupportFragmentManager().beginTransaction().replace(R.id.layoutPrincipal,fragment).addToBackStack(null).commit();
        titulo.setText("Materias propuestas");
        Toast.makeText(this,"Vote por una propuesta",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClickVidPerfil(int idUsuario) {

        SharedPreferences preferences = getSharedPreferences("Datos usuario", Context.MODE_PRIVATE);
        int idUsuario2 = preferences.getInt("idUsuario", 0);

        preferences = getSharedPreferences("Perfil Estudiante", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("perfilEstudiante", idUsuario);
        Toast.makeText(this, "El id del perfil es: "+idUsuario, Toast.LENGTH_SHORT).show();
        editor.commit();

        SharedPreferences preference = getSharedPreferences("Dato perfil", Context.MODE_PRIVATE);
        SharedPreferences.Editor edito = preference.edit();
        edito.putString("dato", "PO");
        edito.commit();


       fragment= new Perfil(idUsuario, idUsuario2, 1);
        getSupportFragmentManager().beginTransaction().replace(R.id.layoutPrincipal,fragment).addToBackStack(null).commit();
        titulo.setText("Perfil");

    }

    @Override
    public void onClickDocPerfil(int idUsuario) {

        SharedPreferences preferences = getSharedPreferences("Datos usuario", Context.MODE_PRIVATE);
        int idUsuario2 = preferences.getInt("idUsuario", 0);

        preferences = getSharedPreferences("Perfil Estudiante", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("perfilEstudiante", idUsuario);
        Toast.makeText(this, "El id del perfil es: "+idUsuario, Toast.LENGTH_SHORT).show();
        editor.commit();

        SharedPreferences preference = getSharedPreferences("Dato perfil", Context.MODE_PRIVATE);
        SharedPreferences.Editor edito = preference.edit();
        edito.putString("dato", "PO");
        edito.commit();


        fragment= new Perfil(idUsuario,idUsuario2,1);
        getSupportFragmentManager().beginTransaction().replace(R.id.layoutPrincipal,fragment).addToBackStack(null).commit();
        titulo.setText("Perfil");

    }
    //------------------------------------------------  REPORTAR    ---------------------------------------------------------
    @Override
    public void onClickReportarVidDoc(int idVidDoc) {
        int idUsuario= Sesion.getSesion().getId();
        final Reportes reporte = new Reportes();
        SharedPreferences preferences = getSharedPreferences("reporte", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("idVidDoc", idVidDoc);
        editor.putInt("idUsuario",idUsuario);
        editor.commit();

        final CharSequence iCharSequence [] = {"Contenido sexual u obseno", "Es spam", "No es apropiado al tema o materia", "No se puede visualizar"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        TextView title = new TextView(this);
        title.setText("Reportar");
        title.setGravity(Gravity.CENTER);
        title.setTextSize(24 );
        title.setTextColor(Color.BLACK);
        builder.setCustomTitle(title);


        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SharedPreferences preferences = MenuEstudiante.this.getSharedPreferences("reporte", Context.MODE_PRIVATE);
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

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    @Override
    public void onClickReportarPreg(int idPreg) {
        int idUsuario= Sesion.getSesion().getId();
        final Reportes reporte = new Reportes();
        SharedPreferences preferences = getSharedPreferences("reporte", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("idPreg", idPreg);
        editor.putInt("idUsuario",idUsuario);
        editor.commit();

        final CharSequence iCharSequence [] = {"Contenido sexual u obseno", "Es spam", "No es apropiado al tema o materia", "No se puede visualizar"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        TextView title = new TextView(this);
        title.setText("Reportar");
        title.setGravity(Gravity.CENTER);
        title.setTextSize(24 );
        title.setTextColor(Color.BLACK);
        builder.setCustomTitle(title);


        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SharedPreferences preferences = MenuEstudiante.this.getSharedPreferences("reporte", Context.MODE_PRIVATE);
                int idPreg = preferences.getInt("idPreg", 0);
                int idUsuario = preferences.getInt("idUsuario", 0);
                String tipo = reporte.getMotivo();
                reportarPreg(idUsuario,idPreg,tipo);
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

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    public void reportarVidDoc(final int idUsuario, final int idVidDoc, String tipo){//tipo==motivo
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
                        Toast.makeText(MenuEstudiante.this, "Reporte hecho con exito",
                                Toast.LENGTH_SHORT).show();
                    }else if(repetido){
                        Toast.makeText(MenuEstudiante.this, "Ya has hecho un reporte" +
                                        " a este vídeo.",
                                Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(MenuEstudiante.this, "Error al realizar el reporte.",
                                Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(MenuEstudiante.this, "Error interno.", Toast.LENGTH_SHORT).show();
                }
                progreso.hide();
                verificarReportes(idVidDoc);
                verificarSpam(idVidDoc);
                verificarNoApropiado(idVidDoc);
                verificarNoSeVe(idVidDoc);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progreso.hide();
                Toast.makeText(MenuEstudiante.this, "Error en la comunicación.", Toast.LENGTH_SHORT).show();
            }
        });
        request= Volley.newRequestQueue(this);
        progreso = new ProgressDialog(this);
        progreso.setMessage("Haciendo reporte...");
        progreso.show();
        request.add(jsonObjectRequest);

    }

    private void verificarNoSeVe(int idVidDoc) {
        String url;
        int idUsuario = Sesion.getSesion().getId();
        url = "https://readandwatch.herokuapp.com/php/reporteNoSeVe.php?idVidDoc="+idVidDoc;
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

    private void verificarNoApropiado(int idVidDoc) {
        String url;
        int idUsuario = Sesion.getSesion().getId();
        url = "https://readandwatch.herokuapp.com/php/reporteNoApropiado.php?idVidDoc="+idVidDoc;
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

    private void verificarSpam(int idVidDoc) {
        String url;
        int idUsuario = Sesion.getSesion().getId();
        url = "https://readandwatch.herokuapp.com/php/reporteSpam.php?idVidDoc="+idVidDoc;
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

    private void verificarReportes(int idVidDoc) {
        String url;
        int idUsuario = Sesion.getSesion().getId();
        url = "https://readandwatch.herokuapp.com/php/reporteArribaVidDoc.php?idVidDoc="+idVidDoc;
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


    public void reportarPreg(int idUsuario, final int idPregunta, String tipo){//tipo==motivo
        JsonObjectRequest jsonObjectRequest;
        RequestQueue request;
        String url;
        url = getString(R.string.ip)+"/php/reportarPreg.php?"
                +"idPregunta="+idPregunta+"&tipo="+tipo+"&idUsuario="+idUsuario;
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
                        Toast.makeText(MenuEstudiante.this, "Reporte hecho con exito",
                                Toast.LENGTH_SHORT).show();
                    }else if(repetido){
                        Toast.makeText(MenuEstudiante.this, "Ya has hecho un reporte" +
                                        " a esta pregunta.",
                                Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(MenuEstudiante.this, "Error al realizar el reporte.",
                                Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(MenuEstudiante.this, "Error interno.", Toast.LENGTH_SHORT).show();
                }


                progreso.hide();
                verificarReportesTemaLibre(idPregunta);
                verificarSpamTemaLibre(idPregunta);
                verificarNoApropiadoTemaLibre(idPregunta);
                verificarNoSeVeTemaLibre(idPregunta);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progreso.hide();
                Toast.makeText(MenuEstudiante.this, "Error en la comunicación.", Toast.LENGTH_SHORT).show();
            }
        });
        request= Volley.newRequestQueue(this);
        progreso = new ProgressDialog(this);
        progreso.setMessage("Haciendo reporte...");
        progreso.show();
        request.add(jsonObjectRequest);

    }

    private void verificarNoSeVeTemaLibre(int idPregunta) {
        String url;
        url = "https://readandwatch.herokuapp.com/php/reporteNoSeVeTemaLibre.php?idPregunta="+idPregunta;
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

    private void verificarNoApropiadoTemaLibre(int idPregunta) {
        String url;
        url = "https://readandwatch.herokuapp.com/php/reporteNoApropiadoTemaLibre.php?idPregunta="+idPregunta;
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

    private void verificarSpamTemaLibre(int idPregunta) {
        String url;
        url = "https://readandwatch.herokuapp.com/php/reporteSpamTemaLibre.php?idPregunta="+idPregunta;
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

    private void verificarReportesTemaLibre(int idPregunta) {
        String url;
        url = "https://readandwatch.herokuapp.com/php/reporteSexualTemaLibre.php?idPregunta="+idPregunta;
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
    //------------------------------------------------  REPORTAR    ---------------------------------------------------------

    @Override
    public void onClickSubirDoc() {
        Dialog_Subir_documento nuevo = new Dialog_Subir_documento();
        nuevo.setModo(Dialog_Subir_documento.MATERIA);
        nuevo.setActividad(this);
        nuevo.show(getSupportFragmentManager() , "ejemplo");
    }

    @Override
    public void onClickSubirVid() {
        DialogSubirVideo nuevo = new DialogSubirVideo();
        nuevo.setModo(DialogSubirVideo.MATERIA);
        nuevo.show(getSupportFragmentManager(), "ejemplo");
    }

    @Override
    public void onClickComentario(int idUsuario, int idVidDoc, int idPregunta) {
        Intent entrar = new Intent(this, MainComentario.class);
        entrar.putExtra("idVidDoc",idVidDoc);
        entrar.putExtra("idUsuario",idUsuario);
        entrar.putExtra("idPregunta",idPregunta);
        this.startActivity(entrar);
    }

    @Override
    public void onClickSubirVidPreg(int idPregunta) {
        SharedPreferences preferences = getSharedPreferences("pregunta", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("idPregunta", idPregunta);
        editor.commit();
        DialogSubirVideo nuevo = new DialogSubirVideo();
        nuevo.setModo(DialogSubirVideo.PREGUNTAR);
        nuevo.show(getSupportFragmentManager(), "ejemplo");
    }

    @Override
    public void onClickSubirDocPreg(int idPregunta) {
        SharedPreferences preferences = getSharedPreferences("pregunta", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("idPregunta", idPregunta);
        editor.commit();
        Dialog_Subir_documento nuevo = new Dialog_Subir_documento();
        nuevo.setModo(Dialog_Subir_documento.PREGUNTAR);
        nuevo.show(getSupportFragmentManager() , "ejemplo");
    }

    @Override
    public void onClickOpcion(int idUsuario, int idVidDoc, int opcion) {
        SharedPreferences preferences = getSharedPreferences("VidDocSeleccionado", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("idVidDoc", idVidDoc);
        editor.commit();
        DialogModificarEliminar nuevo = new DialogModificarEliminar();
        nuevo.setOpcion(opcion);
        nuevo.show(getSupportFragmentManager(), "ejemplo");
    }

    @Override
    public void leerDocumento(int idVidDoc) {

        Bundle bundle = new Bundle();
        bundle.putInt("idVidDoc",idVidDoc);

        fragment =new leerDocumentos();
        fragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.layoutPrincipal,fragment).addToBackStack(null).commit();
        titulo.setText("Doc");
    }

    @Override
    public void mostrarGaleria() {
        View view;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = MenuEstudiante.this.getLayoutInflater();
        view = inflater.inflate(R.layout.cambiar_foto, null);
        builder.setView(view);
        builder.setTitle("      Cambiar foto de perfil");

        btnEditarFoto = view.findViewById(R.id.btnEditarFoto);
        imgCambioFoto = view.findViewById(R.id.imgCambioFoto);


        btnEditarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(MenuEstudiante.this,
                       new String[]{
                             Manifest.permission.READ_EXTERNAL_STORAGE},999);
            }
        });


        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String link = getString(R.string.ip_server_archivos_php)+"guardarImagenesPerfil.php";
                StringRequest request1 = new StringRequest(Request.Method.POST, link, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Imagen subida correctamente.", Toast.LENGTH_LONG).show();
                    }
                }
                        , new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        if (volleyError != null && volleyError.getMessage() != null) {
                            Toast.makeText(getApplicationContext(), volleyError.getMessage(), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();

                        }
                    }
                }) {

                    @Override
                    protected Map<String, String> getParams() {
                        String nombre = String.valueOf(Sesion.getSesion().getId());
                        String rutaImagen = convertirImgString(bitmap);
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("imagename", nombre);
                        params.put("imagecode", rutaImagen);
                        return params;
                    }
                };

                RetryPolicy mRetryPolicy = new DefaultRetryPolicy(
                        0,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

                 request1.setRetryPolicy(mRetryPolicy);
                request.add(request1);
                request= Volley.newRequestQueue(MenuEstudiante.this);

                String url = "https://readandwatch.herokuapp.com/php/updateUsuarioFoto.php?" +
                        "idUsuario="+Sesion.getSesion().getId()+
                        "&txtRutaFoto="+ getString(R.string.ip_server_archivos_imagenes_perfil)+(Sesion.getSesion().getId())+".jpeg"+"";
                url=url.replace(" ", "%20");
                jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(MenuEstudiante.this, "Un momento porfavor...", Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MenuEstudiante.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
                request.add(jsonObjectRequest);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();

    }

    private String convertirImgString(Bitmap bitmap) {
        ByteArrayOutputStream array = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,array);
        byte[] imageByte = array.toByteArray();
        String imagenString = Base64.encodeToString(imageByte,Base64.DEFAULT);
        return imagenString;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==999){
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent.createChooser(intent, "Seleccione la aplicación"),999);
            }else{
                Toast.makeText(MenuEstudiante.this, "No tienes permisos", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 999 && resultCode==RESULT_OK && data!=null){
            Uri path = data.getData();
            imgCambioFoto.setImageURI(path);
            try {
                InputStream inputStream = MenuEstudiante.this.getContentResolver().openInputStream(path);

                bitmap = BitmapFactory.decodeStream(inputStream);
                imgCambioFoto.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (requestCode == 10 && resultCode == RESULT_OK && data != null) {
            this.data = data;
            hilo = new Thread(new Runnable() {
                @Override
                public void run() {
                    subirArchivo();
                }
            });
        }
    }

    private void subirArchivo(){
        File f;
        String nombre="";
        String path = data.getStringExtra(FilePickerActivity.RESULT_FILE_PATH);

        String currentFileName = path.substring(path.lastIndexOf("/"));
        currentFileName = currentFileName.substring(1);
        String nombreSinEspacios = currentFileName.replace(" ", "");

        String rutaSinArchivo = path.substring(0, path.lastIndexOf("/"));

        File from      = new File(rutaSinArchivo, currentFileName);
        File to        = new File(rutaSinArchivo, nombreSinEspacios);
        from.renameTo(to);
        f= to;
        if(nombreArchivo!=null) {
            nombre = nombreArchivo + ".pdf";
        }
        String content_type  = getMimeType(f.getPath());

        String file_path = f.getAbsolutePath();
        OkHttpClient client = new OkHttpClient();
        RequestBody file_body = RequestBody.create(MediaType.parse(content_type),f);

        RequestBody request_body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("type",content_type)
                .addFormDataPart("uploaded_file",file_path.substring(file_path.lastIndexOf("/")+1), file_body)
                .addFormDataPart("nombre",nombre)
                .build();
        okhttp3.Request request = new okhttp3.Request.Builder().url(getString(R.string.ip_server_archivos_php) + "guardarArchivos.php").post(request_body).build();
        try {
            okhttp3.Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                Toast.makeText(MenuEstudiante.this, "Error de peticion", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(MenuEstudiante.this, "Se subio correctamente el archivo", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            Toast.makeText(MenuEstudiante.this, "Error de peticion", Toast.LENGTH_SHORT).show();
        }
    }


    private String getMimeType(String path) {
        String extension = MimeTypeMap.getFileExtensionFromUrl(path);
        return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
    }

    @Override
    public void onClickVideosHolder(Toast toast) {
        toast.show();
    }

    @Override
    public void vistaVideosDoc(boolean i, int idUsuario) {
        if(i){
            fragment =new ElegirVideo();
            getSupportFragmentManager().beginTransaction().replace(R.id.layoutPrincipal,fragment).addToBackStack(null).commit();
            titulo.setText("Videos");
        }else{
            fragment =new ElegirDocumento();
            getSupportFragmentManager().beginTransaction().replace(R.id.layoutPrincipal,fragment).addToBackStack(null).commit();
            titulo.setText("Documentos");
        }

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
    public void eliminarVidDoc(int idVidDoc, final int opc) {
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
                    Toast.makeText(MenuEstudiante.this, "Video eliminado con éxito", Toast.LENGTH_SHORT).show();
                }
                else { Toast.makeText(MenuEstudiante.this, "Documento eliminado con éxito", Toast.LENGTH_SHORT).show();}
                }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MenuEstudiante.this, "Errror", Toast.LENGTH_SHORT).show();
            }
        });
        request.add(jsonObjectRequest);
        progreso.hide();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
