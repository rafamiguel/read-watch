package estrada.leon.rafael.readwatch.estudiante.menu;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
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
import estrada.leon.rafael.readwatch.estudiante.fragment.PreguntasTemaLibre;
import estrada.leon.rafael.readwatch.estudiante.interfaces.iComunicacionFragments;
import estrada.leon.rafael.readwatch.R;
import estrada.leon.rafael.readwatch.estudiante.fragment.SeleccionarSemestre;

public class  MenuEstudiante extends AppCompatActivity
        implements iComunicacionFragments, NavigationView.OnNavigationItemSelectedListener,
        ElegirMateria.OnFragmentInteractionListener, SeleccionarSemestre.OnFragmentInteractionListener,
        ElegirTema.OnFragmentInteractionListener, ElegirVideo.OnFragmentInteractionListener,
        ElegirDocumento.OnFragmentInteractionListener, Historial.OnFragmentInteractionListener,
        PreguntasTemaLibre.OnFragmentInteractionListener, Favoritos.OnFragmentInteractionListener{
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
            titulo.setText("Videos");
        } else if (id == R.id.nav_temasLibres) {
            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.layoutPrincipal,new PreguntasTemaLibre()).commit();
            titulo.setText("Temas libres");
        } else if (id == R.id.nav_favoritos) {
            FragmentManager fragmentManager=getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.layoutPrincipal,new Favoritos()).commit();
            titulo.setText("Favoritos");
        } else if (id == R.id.nav_editarPerfil) {

        } else if (id == R.id.nav_subirVideo) {
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
    public void seleccionarSemestre() {
        fragment =new SeleccionarSemestre();
        getSupportFragmentManager().beginTransaction().replace(R.id.layoutPrincipal,fragment).commit();
        titulo.setText("Seleccione el semestre");
    }

    @Override
    public void seleccionarTema() {
        fragment =new ElegirTema();
        getSupportFragmentManager().beginTransaction().replace(R.id.layoutPrincipal,fragment).commit();
        titulo.setText("Elige un tema");
    }

    @Override
    public void seleccionarVideo() {
        fragment =new ElegirVideo();
        getSupportFragmentManager().beginTransaction().replace(R.id.layoutPrincipal,fragment).commit();
        titulo.setText("Videos");
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