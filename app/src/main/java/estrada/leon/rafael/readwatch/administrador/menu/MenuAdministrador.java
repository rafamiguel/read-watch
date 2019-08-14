package estrada.leon.rafael.readwatch.administrador.menu;

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

import estrada.leon.rafael.readwatch.R;
import estrada.leon.rafael.readwatch.administrador.fragment.BuscarUsuario;
import estrada.leon.rafael.readwatch.administrador.fragment.CambiarContrasena;
import estrada.leon.rafael.readwatch.administrador.fragment.ElegirVideoAdm;
import estrada.leon.rafael.readwatch.administrador.fragment.RegistrarAdmin;
import estrada.leon.rafael.readwatch.administrador.interfaces.iComunicacionFragmentsAdm;

public class MenuAdministrador extends AppCompatActivity
        implements iComunicacionFragmentsAdm,  NavigationView.OnNavigationItemSelectedListener,
        BuscarUsuario.OnFragmentInteractionListener,ElegirVideoAdm.OnFragmentInteractionListener, RegistrarAdmin.OnFragmentInteractionListener, CambiarContrasena.OnFragmentInteractionListener {
    Fragment fragment;
    TextView titulo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        } else if (id == R.id.nav_agregar_admin) {
            fragment =new RegistrarAdmin();
            getSupportFragmentManager().beginTransaction().replace(R.id.layoutPrincipalAdm,fragment).commit();
            titulo.setText("R&W");
        } else if (id == R.id.nav_ver_temas) {
            fragment =new ElegirVideoAdm();
            getSupportFragmentManager().beginTransaction().replace(R.id.layoutPrincipalAdm,fragment).commit();
            titulo.setText("VideosAdm");
        } else if (id == R.id.nav_cambiar_contra) {
            fragment =new CambiarContrasena();
            getSupportFragmentManager().beginTransaction().replace(R.id.layoutPrincipalAdm,fragment).commit();
            titulo.setText("Cambiar contraseña");
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
    public void onClickVideosAdmHolder(Toast toast) {
        toast.show();
    }

    @Override
    public void vistaVideosDoc(boolean i) {
        if(i){
            fragment =new ElegirVideoAdm();
            getSupportFragmentManager().beginTransaction().replace(R.id.layoutPrincipal,fragment).commit();
            titulo.setText("VideosAdm");
        }else{
            //fragment =new ElegirDocumento();
            getSupportFragmentManager().beginTransaction().replace(R.id.layoutPrincipal,fragment).commit();
            titulo.setText("Documentos");
        }
    }

}
