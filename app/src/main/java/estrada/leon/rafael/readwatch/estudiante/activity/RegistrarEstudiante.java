package estrada.leon.rafael.readwatch.estudiante.activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
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
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import estrada.leon.rafael.readwatch.R;

public class RegistrarEstudiante extends AppCompatActivity implements Response.Listener<JSONObject>, Response.ErrorListener {
    TextView lblTitulo;
    EditText txtNombre, txtApellido, txtCorreo, txtContrasena, txtTelefono, txtDescripcion;
    Button btnEntrar;
    ImageView btnFoto;
    ProgressDialog progreso;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    Bitmap bitmap;
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
                ActivityCompat.requestPermissions(RegistrarEstudiante.this,
                        new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE},999);
            }
        });

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(txtNombre.getText().toString().equals("")|| txtApellido.getText().toString().equals("")
                || txtCorreo.getText().toString().equals("") || txtCorreo.getText().toString().equals("")
                || txtDescripcion.getText().toString().equals("") || txtTelefono.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Llena todos los campos", Toast.LENGTH_LONG).show();
                }else{
                    if(txtContrasena.getText().length()< 9){

                        Toast.makeText(getApplicationContext(), "La contraseña debe ser mayor a 8 caracteres", Toast.LENGTH_SHORT).show();
                    }else {
                                char clave;
                                int a=0,b=0,c=0;
                                for(int i=0; i<txtContrasena.getText().length(); i++){
                                    clave = txtContrasena.getText().charAt(i);
                                    String passValue = String.valueOf(clave);
                                    if (passValue.matches("[A-Z]")) {
                                        a=1;
                                    } else if (passValue.matches("[a-z]")) {
                                        b=1;
                                    } else if (passValue.matches("[0-9]")) {
                                        c=1;
                                    }
                                }
                                if(a==1 && b==1 && c==1){
                                    cargarWebService();
                                }
                                else{ Toast.makeText(getApplicationContext(),"La contraseña debe contener mayúsculas, minúsculas y números", Toast.LENGTH_SHORT).show();}

                    }
                }


            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==999){
            if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent.createChooser(intent, "Seleccione la aplicación"),999);
            }else{
                Toast.makeText(this, "No tienes permisos", Toast.LENGTH_SHORT).show();
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
            btnFoto.setImageURI(path);
            try {
                InputStream inputStream = getContentResolver().openInputStream(path);

                bitmap = BitmapFactory.decodeStream(inputStream);
                btnFoto.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void cargarWebService() {

        String link = getString(R.string.ip_server_archivos_php)+"guardarImagenesPerfil.php";

        StringRequest request1 = new StringRequest(Request.Method.POST, link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(RegistrarEstudiante.this, "Imagen subida correctamente.", Toast.LENGTH_LONG).show();
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (volleyError != null && volleyError.getMessage() != null) {
                    Toast.makeText(RegistrarEstudiante.this, volleyError.getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(RegistrarEstudiante.this, "Something went wrong", Toast.LENGTH_LONG).show();

                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                String nombre = txtNombre.getText().toString();
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
        request= Volley.newRequestQueue(RegistrarEstudiante.this);

        progreso = new ProgressDialog(this);
        progreso.setMessage("Cargando...");
        progreso.show();
        String url = "https://readandwatch.herokuapp.com/php/registroUsuario.php?" +
                "txtCorreo="+txtCorreo.getText().toString()+
                "&txtContrasena="+txtContrasena.getText().toString()+
                "&txtNombre="+txtNombre.getText().toString()+
                "&txtApellido="+txtApellido.getText().toString()+
                "&txtTelefono="+txtTelefono.getText().toString()+
                "&txtRutaFoto="+ getString(R.string.ip_server_archivos_imagenes_perfil)+txtNombre.getText().toString()+".jpeg"+
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
                btnFoto.setImageURI(null);
                btnFoto.setImageBitmap(null);
                btnFoto.setBackgroundResource(R.drawable.ic_menu_camera);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String convertirImgString(Bitmap bitmap) {
        ByteArrayOutputStream array = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,array);
        byte[] imageByte = array.toByteArray();
        String imagenString = Base64.encodeToString(imageByte,Base64.DEFAULT);
        return imagenString;
    }
}
