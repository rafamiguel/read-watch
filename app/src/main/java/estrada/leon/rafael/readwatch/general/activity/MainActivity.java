package estrada.leon.rafael.readwatch.general.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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

import estrada.leon.rafael.readwatch.MainFileManager;
import estrada.leon.rafael.readwatch.estudiante.dialog.DialogContrasenaOlvidada;
import estrada.leon.rafael.readwatch.administrador.menu.MenuAdministrador;
import estrada.leon.rafael.readwatch.estudiante.menu.MenuEstudiante;
import estrada.leon.rafael.readwatch.R;
import estrada.leon.rafael.readwatch.estudiante.activity.RegistrarEstudiante;

public class MainActivity extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {
    ImageView logo;
    EditText txtUsuario,txtContra;
    Button btnEntrar;
    TextView lblContra,lblCuenta,lblRegistrar;
    Intent entrar;
    ProgressDialog progreso;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

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
                //entrar = new Intent(MainActivity.this, Notificaciones.class);
                //startActivity(entrar);
                entrar = new Intent(MainActivity.this, MainFileManager.class);
                startActivity(entrar);
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
        request.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.hide();
        Toast.makeText(getApplicationContext(), "Error en la conexión\n"+error.toString(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        JSONArray json = response.optJSONArray("usuario");
        JSONObject jsonObject=null;
        try {
            jsonObject=json.getJSONObject(0);
            if(jsonObject.optInt("idUsuario")!=-1){
                if(jsonObject.optString("tipo").equals("A")) {
                    entrar = new Intent(MainActivity.this, MenuAdministrador.class);
                    startActivity(entrar);
                }else{
                    entrar = new Intent(MainActivity.this, MenuEstudiante.class);
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
}
