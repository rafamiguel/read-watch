package estrada.leon.rafael.readwatch.estudiante.activity;

import android.app.ProgressDialog;
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

import estrada.leon.rafael.readwatch.R;

public class RegistrarEstudiante extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {
    TextView lblTitulo;
    EditText txtNombre, txtApellido, txtCorreo, txtContrasena, txtTelefono, txtDescripcion;
    Button btnEntrar;
    ImageView btnFoto;
    ProgressDialog progreso;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_estudiante);
        lblTitulo = findViewById(R.id.lblTitulo);
        txtNombre = findViewById(R.id.txtNombre);
        txtApellido = findViewById(R.id.txtApellido);
        txtCorreo = findViewById(R.id.txtCorreo);
        txtContrasena = findViewById(R.id.txtContrasena);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtDescripcion = findViewById(R.id.lblDescripcion);
        btnFoto = findViewById(R.id.btnFoto);
        btnEntrar = findViewById(R.id.btnEntrar);

        request = Volley.newRequestQueue(getApplicationContext());

        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarWebService();
            }
        });
    }

    private void cargarWebService() {
        progreso = new ProgressDialog(this);
        progreso.setMessage("Cargando...");
        progreso.show();
        String url = "http://192.168.1.65/randwBDRemota/registroUsuario.php?txtCorreo="+txtCorreo.getText().toString()+
                "&txtContrasena="+txtContrasena.getText().toString()+
                "&txtNombre="+txtNombre.getText().toString()+
                "&txtApellido="+txtApellido.getText().toString()+
                "&txtTelefono="+txtTelefono.getText().toString()+
                "&txtDescripcion="+txtDescripcion.getText().toString()+"";
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.hide();
        Toast.makeText(getApplicationContext(), "No se pudo registrar"+error.toString(),Toast.LENGTH_LONG).show();

    }

    @Override
    public void onResponse(JSONObject response) {
        JSONArray json = response.optJSONArray("usuario");
        JSONObject jsonObject=null;
        try {
            jsonObject=json.getJSONObject(0);
            if((jsonObject.optString("existencia")).equals("si")){
                Toast.makeText(getApplicationContext(), "Este correo electrónico ya está registrado.", Toast.LENGTH_SHORT).show();
                progreso.hide();
            }else{
                Toast.makeText(getApplicationContext(), "Se ha registrado exitosamente", Toast.LENGTH_SHORT).show();
                progreso.hide();
                txtDescripcion.setText("");
                txtTelefono.setText("");
                txtApellido.setText("");
                txtNombre.setText("");
                txtContrasena.setText("");
                txtCorreo.setText("");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
