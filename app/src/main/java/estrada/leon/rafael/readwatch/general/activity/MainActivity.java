package estrada.leon.rafael.readwatch.general.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import estrada.leon.rafael.readwatch.MainFileManager;
import estrada.leon.rafael.readwatch.MainVideos;
import estrada.leon.rafael.readwatch.administrador.fragment.SeleccionarSemestreAdm;
import estrada.leon.rafael.readwatch.estudiante.dialog.DialogContrasenaOlvidada;
import estrada.leon.rafael.readwatch.administrador.menu.MenuAdministrador;
import estrada.leon.rafael.readwatch.estudiante.interfaces.iSesion;
import estrada.leon.rafael.readwatch.estudiante.menu.MenuEstudiante;
import estrada.leon.rafael.readwatch.R;
import estrada.leon.rafael.readwatch.estudiante.activity.RegistrarEstudiante;
import estrada.leon.rafael.readwatch.general.funciones.ObtenerTiempo;
import estrada.leon.rafael.readwatch.general.pojo.Admin;
import estrada.leon.rafael.readwatch.general.pojo.Estudiante;
import estrada.leon.rafael.readwatch.general.pojo.Fecha;
import estrada.leon.rafael.readwatch.general.pojo.Sesion;
import estrada.leon.rafael.readwatch.general.pojo.Usuario;

public class MainActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {
 ;
    ImageView logo;
    EditText txtUsuario,txtContra;
    Button btnEntrar;
    TextView lblContra,lblCuenta,lblRegistrar;
    Intent entrar;
    ProgressDialog progreso;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    DatabaseReference rootReference = FirebaseDatabase.getInstance().getReference();
    Fecha fecha;

    private String contra="",usuario="",user="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logo = findViewById(R.id.logo);
        txtUsuario = findViewById(R.id.txtUsuario);
        txtContra = findViewById(R.id.txtContra);
        btnEntrar = findViewById(R.id.btnEntrar);
        lblContra = findViewById(R.id.lblContra);
        lblCuenta = findViewById(R.id.lblCuenta);
        lblRegistrar = findViewById(R.id.lblRegistrar);
        request = Volley.newRequestQueue(getApplicationContext());
        lblRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                entrar = new Intent(MainActivity.this, RegistrarEstudiante.class);
                startActivity(entrar);

            }
        });
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuario=txtUsuario.getText().toString();
                contra=txtContra.getText().toString();
                user=txtUsuario.getText().toString();
                cargarWebService();
            }
        });
        lblContra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogContrasenaOlvidada dialogContrasenaOlvidada = new DialogContrasenaOlvidada();
                dialogContrasenaOlvidada.show(getSupportFragmentManager(), "Example");
            }
        });

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                entrar = new Intent(MainActivity.this, MainVideos.class);
                startActivity(entrar);
            }
        });


        rootReference.child("actualizacion").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    fecha = new Fecha();
                    fecha = snapshot.getValue(Fecha.class);

                    if(ObtenerTiempo.reiniciarVotaciones(fecha)){

                    }


                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
                    String fechaActual = dateformat.format(c.getTime());

                    Map<String, Object> actualizacion = new HashMap<>();
                    actualizacion.put("materia",fechaActual);
                    rootReference.setValue(actualizacion);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void cargarWebService() {
        progreso = new ProgressDialog(this);
        progreso.setMessage("Cargando...");
        progreso.show();
        String url = "https://readandwatch.herokuapp.com/php/inicioSesion.php?" +
                "txtCorreo="+txtUsuario.getText().toString()+
                "&txtContrasena="+txtContra.getText().toString()+ "";
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        request.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.hide();
        Toast.makeText(getApplicationContext(), "Error en la conexión\n"+error.toString(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        Fragment fragment = new Fragment();
        Bundle bundle = new Bundle();
        JSONArray json = response.optJSONArray("usuario");
        JSONObject jsonObject=null;

        try {
            jsonObject=json.getJSONObject(0);
            if(jsonObject.optInt("idUsuario")!=-1){
                if(jsonObject.optString("tipo").equals("A")) {
                    guardarPreferencias(jsonObject);
                    int idUsuario = jsonObject.optInt("idUsuario");
                    String nombre = jsonObject.optString("nombre");
                    String apellidos = jsonObject.optString("apellidos");
                    String correo = jsonObject.optString("correo");
                    String contrasena = jsonObject.optString("contrasena");

                    Usuario admin = (Usuario)Sesion.getSesion();
                    admin.setId(idUsuario);
                    admin.setNombre(nombre);
                    admin.setApellidos(apellidos);
                    admin.setCorreo(correo);
                    admin.setContrasena(contrasena);


                    entrar = new Intent(MainActivity.this, MenuAdministrador.class);
                    startActivity(entrar);

                }else{
                    guardarPreferencias(jsonObject);
                    int idUsuario = jsonObject.optInt("idUsuario");
                    String nombre = jsonObject.optString("nombre");
                    String apellidos = jsonObject.optString("apellidos");
                    String correo = jsonObject.optString("correo");
                    String contrasena = jsonObject.optString("contrasena");
                    String telefono = jsonObject.optString("telefono");
                    String descripcion = jsonObject.optString("descripcion");
                    Usuario estudiante = (Usuario)Sesion.getSesion();
                    estudiante.setId(idUsuario);
                    estudiante.setNombre(nombre);
                    estudiante.setApellidos(apellidos);
                    estudiante.setCorreo(correo);
                    estudiante.setContrasena(contrasena);
                    estudiante.setTelefono(telefono);
                    estudiante.setDescripcion(descripcion);
                    entrar = new Intent(MainActivity.this, MenuEstudiante.class);
                    updateEstudiante();
                    startActivity(entrar);
                }
                progreso.hide();
            }else{
                Toast.makeText(getApplicationContext(), "Correo o contraseña incorrecta.", Toast.LENGTH_SHORT).show();
                progreso.hide();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void updateEstudiante() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String datetime = dateformat.format(c.getTime());
        String url;
        int idUsuario = Sesion.getSesion().getId();
        url = "https://readandwatch.herokuapp.com/php/updateHoraUsuario.php?" +
                "idUsuario="+idUsuario+"&datetime="+datetime;
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

    private void guardarPreferencias(JSONObject jsonObject) {
        SharedPreferences preferences = getSharedPreferences("Datos usuario", Context.MODE_PRIVATE);
        int idUsuario = jsonObject.optInt("idUsuario");
        String correo = jsonObject.optString("correo");
        String contrasena = jsonObject.optString("contrasena");
        String nombre = jsonObject.optString("nombre");
        String apellidos = jsonObject.optString("apellidos");
        String telefono = jsonObject.optString("telefono");
        String descripcion = jsonObject.optString("descripcion");
        String rutaFoto = jsonObject.optString("rutaFoto");
        String tipo = jsonObject.optString("tipo");
        String estado = jsonObject.optString("estado");

        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("idUsuario", idUsuario);
        editor.putString("correo", correo);
        editor.putString("contrasena", contrasena);
        editor.putString("nombre", nombre);
        editor.putString("apellidos", apellidos);
        editor.putString("telefono", telefono);
        editor.putString("descripcion", descripcion);
        editor.putString("rutaFoto", rutaFoto);
        editor.putString("tipo", tipo);
        editor.putString("estado", estado);

        Toast.makeText(getApplicationContext(), "idUsuario:"+idUsuario+" correo: "+correo+" contra:"+contrasena+" nombre: "+nombre+" tipo: "+tipo, Toast.LENGTH_SHORT).show();
        editor.commit();

        SharedPreferences preference = getSharedPreferences("Dato perfil", Context.MODE_PRIVATE);
        SharedPreferences.Editor edito = preference.edit();
        edito.putString("dato", "PP");
        edito.commit();
    }
}
