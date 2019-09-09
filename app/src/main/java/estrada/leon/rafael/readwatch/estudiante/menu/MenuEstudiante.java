package estrada.leon.rafael.readwatch.estudiante.menu;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import estrada.leon.rafael.readwatch.estudiante.dialog.DialogHacerPregunta;
import estrada.leon.rafael.readwatch.estudiante.dialog.DialogSubirVideo;
import estrada.leon.rafael.readwatch.estudiante.dialog.Dialog_Recuadro_Subir_documento;
import estrada.leon.rafael.readwatch.estudiante.fragment.ElegirDocumento;
import estrada.leon.rafael.readwatch.estudiante.fragment.ElegirMateria;
import estrada.leon.rafael.readwatch.estudiante.fragment.ElegirTema;
import estrada.leon.rafael.readwatch.estudiante.fragment.ElegirVideo;
import estrada.leon.rafael.readwatch.estudiante.fragment.Favoritos;
import estrada.leon.rafael.readwatch.estudiante.fragment.Historial;
import estrada.leon.rafael.readwatch.estudiante.fragment.MateriasPropuestas;
import estrada.leon.rafael.readwatch.estudiante.fragment.ModificarEstudiante;
import estrada.leon.rafael.readwatch.estudiante.fragment.Perfil;
import estrada.leon.rafael.readwatch.estudiante.fragment.PreguntasTemaLibre;
import estrada.leon.rafael.readwatch.estudiante.fragment.TemasPropuestos;
import estrada.leon.rafael.readwatch.estudiante.fragment.lista_materias;
import estrada.leon.rafael.readwatch.estudiante.interfaces.iComunicacionFragments;
import estrada.leon.rafael.readwatch.R;
import estrada.leon.rafael.readwatch.estudiante.fragment.SeleccionarSemestre;

public class  MenuEstudiante extends AppCompatActivity
        implements iComunicacionFragments, NavigationView.OnNavigationItemSelectedListener,
        ElegirMateria.OnFragmentInteractionListener, SeleccionarSemestre.OnFragmentInteractionListener,
        ElegirTema.OnFragmentInteractionListener, ElegirVideo.OnFragmentInteractionListener,
        ElegirDocumento.OnFragmentInteractionListener, Historial.OnFragmentInteractionListener,
        PreguntasTemaLibre.OnFragmentInteractionListener, Favoritos.OnFragmentInteractionListener,
        TemasPropuestos.OnFragmentInteractionListener, Perfil.OnFragmentInteractionListener,
        lista_materias.OnFragmentInteractionListener,MateriasPropuestas.OnFragmentInteractionListener, ModificarEstudiante.OnFragmentInteractionListener {
    Fragment fragment;
    TextView titulo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            fragmentManager.beginTransaction().replace(R.id.layoutPrincipal,new ElegirVideo()).commit();
            titulo.setText("VideosAdm");
        } else if (id == R.id.nav_temasLibres) {
            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.layoutPrincipal,new PreguntasTemaLibre()).commit();
            titulo.setText("Temas libres");
        } else if (id == R.id.nav_favoritos) {
            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.layoutPrincipal,new Favoritos()).commit();
            titulo.setText("Favoritos");
        } else if (id == R.id.nav_Perfil) {
            fragment =new Perfil();
            getSupportFragmentManager().beginTransaction().replace(R.id.layoutPrincipal,fragment).commit();
            titulo.setText("");
        } else if (id == R.id.nav_editarPerfil) {
            fragment =new ModificarEstudiante();
            getSupportFragmentManager().beginTransaction().replace(R.id.layoutPrincipal,fragment).commit();
            titulo.setText("Editar Perfil");
        }else if (id == R.id.nav_subirVideo) {
            DialogSubirVideo nuevo = new DialogSubirVideo();
            nuevo.show(getSupportFragmentManager(), "ejemplo");
        } else if (id == R.id.nav_subirArchivo) {
            Dialog_Recuadro_Subir_documento nuevo = new Dialog_Recuadro_Subir_documento();
            nuevo.show(getSupportFragmentManager(), "ejemplo");
        }else if(id==R.id.nav_historial) {
            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.layoutPrincipal,new Historial()).commit();
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
        getSupportFragmentManager().beginTransaction().replace(R.id.layoutPrincipal,fragment).commit();
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
        getSupportFragmentManager().beginTransaction().replace(R.id.layoutPrincipal,fragment).commit();
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
    public void seleccionarVideo(int idTema) {
        fragment =new ElegirVideo();
        getSupportFragmentManager().beginTransaction().replace(R.id.layoutPrincipal,fragment).commit();
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
        getSupportFragmentManager().beginTransaction().replace(R.id.layoutPrincipal,fragment).commit();
        titulo.setText("Temas propuestos");
        Toast.makeText(this,"Vote por una propuesta",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClickProponerMateria() {
        fragment =new MateriasPropuestas();
        getSupportFragmentManager().beginTransaction().replace(R.id.layoutPrincipal,fragment).commit();
        titulo.setText("Materias propuestas");
        Toast.makeText(this,"Vote por una propuesta",Toast.LENGTH_LONG).show();
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
        getSupportFragmentManager().beginTransaction().replace(R.id.layoutPrincipal,fragment).commit();
        titulo.setText("Perfil");

    }

    @Override
    public void onClickDocPerfil() {

    }

    @Override
    public void onClickReportar() {
        CharSequence iCharSequence [] = {"Contenido sexual u obseno", "Es spam", "No es apropiado al tema o materia", "No se puede visualizar"};
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
                Toast.makeText(MenuEstudiante.this, "Reporte realizado", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.setMultiChoiceItems(iCharSequence, new boolean[iCharSequence.length], new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean b) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    @Override
    public void onClickSubirDoc() {
        Dialog_Recuadro_Subir_documento nuevo = new Dialog_Recuadro_Subir_documento();
        nuevo.show(getSupportFragmentManager() , "ejemplo");
    }

    @Override
    public void onClickSubirVid() {
        DialogSubirVideo nuevo = new DialogSubirVideo();
        nuevo.show(getSupportFragmentManager(), "ejemplo");
    }

    @Override
    public void onClickVideosHolder(Toast toast) {
        toast.show();
    }

    @Override
    public void vistaVideosDoc(boolean i) {
        if(i){
            fragment =new ElegirVideo();
            getSupportFragmentManager().beginTransaction().replace(R.id.layoutPrincipal,fragment).commit();
            titulo.setText("Videos");
        }else{
            fragment =new ElegirDocumento();
            getSupportFragmentManager().beginTransaction().replace(R.id.layoutPrincipal,fragment).commit();
            titulo.setText("Documentos");
        }

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}