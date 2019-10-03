package estrada.leon.rafael.readwatch.administrador.menu;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import estrada.leon.rafael.readwatch.R;
import estrada.leon.rafael.readwatch.administrador.fragment.BuscarUsuario;
import estrada.leon.rafael.readwatch.administrador.fragment.CambiarContrasena;
import estrada.leon.rafael.readwatch.administrador.fragment.ElegirDocumentoAdm;
import estrada.leon.rafael.readwatch.administrador.fragment.ElegirMateriaAdm;
import estrada.leon.rafael.readwatch.administrador.fragment.MainComentarios;
import estrada.leon.rafael.readwatch.administrador.fragment.ModificarAdmin;
import estrada.leon.rafael.readwatch.administrador.fragment.RegistrarAdmin;
import estrada.leon.rafael.readwatch.administrador.fragment.SeleccionarSemestreAdm;
import estrada.leon.rafael.readwatch.administrador.fragment.UsuariosInactivos;
import estrada.leon.rafael.readwatch.administrador.interfaces.iComunicacionFragmentsAdm;
import estrada.leon.rafael.readwatch.estudiante.fragment.ElegirTema;
import estrada.leon.rafael.readwatch.estudiante.fragment.Perfil;
import estrada.leon.rafael.readwatch.estudiante.fragment.SeleccionarSemestre;
import estrada.leon.rafael.readwatch.estudiante.interfaces.iComunicacionFragments;

public class MenuAdministrador extends AppCompatActivity
        implements iComunicacionFragmentsAdm,   NavigationView.OnNavigationItemSelectedListener,
        BuscarUsuario.OnFragmentInteractionListener, ElegirMateriaAdm.OnFragmentInteractionListener,
        RegistrarAdmin.OnFragmentInteractionListener, CambiarContrasena.OnFragmentInteractionListener,
        UsuariosInactivos.OnFragmentInteractionListener, ElegirDocumentoAdm.OnFragmentInteractionListener,
        ModificarAdmin.OnFragmentInteractionListener, SeleccionarSemestreAdm.OnFragmentInteractionListener,
        iComunicacionFragments, Perfil.OnFragmentInteractionListener{
    Fragment fragment;
    ProgressDialog progreso;
    JsonObjectRequest jsonObjectRequest;
    TextView titulo;
    Intent entrar;
    TextView lblEliminar, lblModificar, lblAnadir, title, lblNombre, lblFoto;
    EditText txtNombre, txtFoto;
    RequestQueue request;
    String url="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        request= Volley.newRequestQueue(getApplicationContext());
        setContentView(R.layout.activity_menu_administrador);
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
        titulo=findViewById(R.id.toolbar_title_adm);

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
        getMenuInflater().inflate(R.menu.menu_administrador, menu);
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

        if (id == R.id.nav_buscar_usuario) {
            fragment =new BuscarUsuario();
            getSupportFragmentManager().beginTransaction().replace(R.id.layoutPrincipalAdm,fragment).commit();
            titulo.setText("Buscar usuario");
        } else if (id == R.id.nav_usuarios_inactivos) {
            fragment =new UsuariosInactivos();
            getSupportFragmentManager().beginTransaction().replace(R.id.layoutPrincipalAdm,fragment).commit();
            titulo.setText("Usuarios inactivos");
        } else if (id == R.id.nav_agregar_admin) {
            fragment =new RegistrarAdmin();
            getSupportFragmentManager().beginTransaction().replace(R.id.layoutPrincipalAdm,fragment).commit();
            titulo.setText("R&W");
        } else if (id == R.id.nav_ver_temas) {
            fragment =new ElegirMateriaAdm();
            getSupportFragmentManager().beginTransaction().replace(R.id.layoutPrincipalAdm,fragment).commit();
            titulo.setText("VideosAdm");
        } else if (id == R.id.nav_cambiar_contra) {
            fragment = new CambiarContrasena();
            getSupportFragmentManager().beginTransaction().replace(R.id.layoutPrincipalAdm, fragment).commit();
            titulo.setText("Cambiar contraseña");
        }else if(id == R.id.nav_modificar_datos){
            fragment = new ModificarAdmin();
            getSupportFragmentManager().beginTransaction().replace(R.id.layoutPrincipalAdm, fragment).commit();
            titulo.setText("Cambiar datos");

        } else if (id == R.id.nav_salir) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void onClickBuscarUsuario(Toast toast) {
        toast.show();
    }

    @Override
    public void onClickVideosAdmHolder(int idVidDoc) {
        SharedPreferences preferences = getSharedPreferences("IdVidDoc", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("idVidDoc", idVidDoc);
    }

    @Override
    public void seleccionarSemestre(int idMateria) {
        fragment =new SeleccionarSemestreAdm();
        getSupportFragmentManager().beginTransaction().replace(R.id.layoutPrincipalAdm,fragment).addToBackStack(null).commit();
        titulo.setText("Seleccione el semestre");
        guardarPreferenciasMateria(idMateria);

    }

    private void guardarPreferenciasMateria(int idMateria) {
        SharedPreferences preferences = getSharedPreferences("Materia", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("materia", idMateria);
        Toast.makeText(this, "La materia es : "+idMateria, Toast.LENGTH_SHORT).show();
        editor.commit();
    }

    @Override
    public void seleccionarTema(int semestre) {
        //fragment =new ElegirTema();
       // getSupportFragmentManager().beginTransaction().replace(R.id.layoutPrincipalAdm,fragment).addToBackStack(null).commit();
       // titulo.setText("Elige un tema");

       // guardarPreferencias(semestre);

    }

    @Override
    public void seleccionarVideo(int idTema, int idUsuario) {

    }

    private void guardarPreferencias(int semestre) {
        SharedPreferences preferences = getSharedPreferences("Semestre", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("semestre", semestre);
        Toast.makeText(this, "El semestre es: "+semestre, Toast.LENGTH_SHORT).show();
        editor.commit();
    }


    @Override
    public void onClickVideosHolder(Toast toast) {

    }

    @Override
    public void vistaVideosDoc(boolean i, int idUsuario) {

    }

    @Override
    public void vistaVideosDoc(boolean i) {
        if(i){
            fragment =new ElegirMateriaAdm();
            getSupportFragmentManager().beginTransaction().replace(R.id.layoutPrincipalAdm,fragment).commit();
            titulo.setText("VideosAdm");
        }else{
            fragment =new ElegirDocumentoAdm();
            getSupportFragmentManager().beginTransaction().replace(R.id.layoutPrincipalAdm,fragment).commit();
            titulo.setText("DocumentosAdm");
        }
    }

    @Override
    public void onClickInactivo(Toast toast) {
        toast.show();
    }

    @Override
    public void onClickDocumentosHolder(Toast toast) {
        toast.show();
    }

    @Override
    public void onClickTemasLibresHolder(Toast toast) {

    }

    @Override
    public void onClickVidFavHolder(Toast toast) {

    }

    @Override
    public void onClickDocFavHolder(Toast toast) {

    }

    @Override
    public void onClickNuevaPregunta() {

    }

    @Override
    public void onClickProponerTema() {

    }

    @Override
    public void onClickProponerMateria() {

    }

    @Override
    public void onClickVidPerfil(int idUsuario) {
        SharedPreferences preferences = getSharedPreferences("Perfil Estudiante", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("perfilEstudiante", idUsuario);
        Toast.makeText(this, "El id del perfil es: "+idUsuario, Toast.LENGTH_SHORT).show();
        editor.commit();

        SharedPreferences preference = getSharedPreferences("Dato perfil", Context.MODE_PRIVATE);
        SharedPreferences.Editor edito = preference.edit();
        edito.putString("dato", "PO");
        edito.commit();


        fragment= new Perfil();
        getSupportFragmentManager().beginTransaction().replace(R.id.layoutPrincipalAdm,fragment).commit();
        titulo.setText("Perfil");
    }

    @Override
    public void onClickDocPerfil(int idUsuario) {

    }

    @Override
    public void onClickReportar() {

    }

    @Override
    public void onClickSubirDoc() {

    }

    @Override
    public void onClickSubirVid() {

    }

    @Override
    public void onClickComentario(int idUsuario, int idVidDoc, int idPregunta) {

    }


    @Override
    public void onClickSubirVidPreg(int idPregunta) {

    }

    @Override
    public void onClickSubirDocPreg(int IdPregunta) {

    }

    @Override
    public void onClickOpcion(int idUsuario, int idVidDoc, int opcion) {

    }

    @Override
    public void onClickComentario(int idVidDoc) {

        entrar = new Intent(this, MainComentarios.class);
        entrar.putExtra("idVidDoc",idVidDoc);
        startActivity(entrar);
    }

    @Override
    public void onClickOpciones(final int idMateria) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final int idMaterias = idMateria;
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_opciones, null);
        builder.setView(view);
        title = new TextView(this);
        title.setText("OPCIONES");
        title.setGravity(Gravity.CENTER);
        title.setTextSize(20);
        title.setTextColor(Color.BLACK);

        lblEliminar = view.findViewById(R.id.lblEliminar);
        lblModificar = view.findViewById(R.id.lblModificar);
        lblAnadir = view.findViewById(R.id.lblAnadir);
        builder.setCustomTitle(title);

        lblEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirDialogEliminar(idMaterias);

            }
        });

        lblAnadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirDialog();
            }
        });

        lblModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirDialogModificar(idMaterias);
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(true);
        alertDialog.show();
    }


    private void abrirDialogEliminar(final int idMateria) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Toast.makeText(getApplicationContext(),String.valueOf(idMateria),Toast.LENGTH_SHORT).show();
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_eliminar_materia, null);
        builder.setView(view);
        title = new TextView(this);
        title.setText("Eliminar Materia");
        title.setGravity(Gravity.CENTER);
        title.setTextSize(20);
        title.setTextColor(Color.BLACK);


        builder.setCustomTitle(title);
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        })
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        progreso = new ProgressDialog(MenuAdministrador.this);
                        progreso.setMessage("Cargando...");
                        progreso.show();
                        url = "https://readandwatch.herokuapp.com/php/deleteMateria.php?idMateria=" + idMateria ;
                        url=url.replace(" ", "%20");
                        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                                null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                progreso.hide();
                                Toast.makeText(getApplicationContext(),"Materia eliminada con éxito",Toast.LENGTH_SHORT).show();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progreso.hide();
                                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
                            }
                        });
                        request.add(jsonObjectRequest);

                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(true);
        alertDialog.show();
    }

    private void abrirDialogModificar(final int idMaterias) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        Toast.makeText(getApplicationContext(),String.valueOf(idMaterias),Toast.LENGTH_SHORT).show();
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_modificar_materia, null);
        builder.setView(view);
        title = new TextView(this);
        title.setText("Modificar Materia");
        title.setGravity(Gravity.CENTER);
        title.setTextSize(20);
        title.setTextColor(Color.BLACK);

        txtNombre = view.findViewById(R.id.txtNombre);
        txtFoto = view.findViewById(R.id.txtFoto);
        builder.setCustomTitle(title);
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        })
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        progreso = new ProgressDialog(MenuAdministrador.this);
                        progreso.setMessage("Cargando...");
                        progreso.show();
                        url = "https://readandwatch.herokuapp.com/php/updateMateria.php?idMateria=" + idMaterias  + "&nombre=" + txtNombre.getText().toString() + "&rutaImagen=" + txtFoto.getText().toString();
                        url=url.replace(" ", "%20");
                        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                                null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                progreso.hide();
                                Toast.makeText(getApplicationContext(),"Materia modificada con éxito",Toast.LENGTH_SHORT).show();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                progreso.hide();
                                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
                            }
                        });
                        request.add(jsonObjectRequest);
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(true);
        alertDialog.show();



    }

    private void abrirDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_insertar_materia, null);
        builder.setView(view);
        title = new TextView(this);
        title.setText("Agregar Materia");
        title.setGravity(Gravity.CENTER);
        title.setTextSize(20);
        title.setTextColor(Color.BLACK);

        txtNombre = view.findViewById(R.id.txtNombre);
        txtFoto = view.findViewById(R.id.txtFoto);
        builder.setCustomTitle(title);
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        })
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        insertarMateriaWebService(txtNombre.getText().toString(),txtFoto.getText().toString());
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(true);
        alertDialog.show();
    }

    private void insertarMateriaWebService(String nombre, String rutaImagen) {
        request= Volley.newRequestQueue(getApplicationContext());
        String url;
        progreso = new ProgressDialog(this);
        progreso.setMessage("Cargando...");
        progreso.show();


        url = "https://readandwatch.herokuapp.com/php/insertarMateria.php?nombre="+nombre+"&rutaImagen="+rutaImagen;
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>(){


            @Override
            public void onResponse(JSONObject response) {
                progreso.hide();Toast.makeText(MenuAdministrador.this, "Subida con exito", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progreso.hide();
                error.printStackTrace();
                Toast.makeText(MenuAdministrador.this, error.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        request.add(jsonObjectRequest);


    }

}
