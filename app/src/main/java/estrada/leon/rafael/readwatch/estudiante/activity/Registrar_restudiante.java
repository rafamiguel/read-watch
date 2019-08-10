package estrada.leon.rafael.readwatch.estudiante.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import estrada.leon.rafael.readwatch.R;

public class Registrar_restudiante extends AppCompatActivity {
    TextView txtTitulo;
    EditText txtNombre, txtApellido, txtCorreo, txtContrasena, txtTelefono, txtDescripcion;
    Button btnFoto, btnEntrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_estudiante);
        txtTitulo = findViewById(R.id.lblTitulo);
        txtNombre = findViewById(R.id.txtNombre);
        txtApellido = findViewById(R.id.txtApellido);
        txtCorreo = findViewById(R.id.txtCorreo);
        txtContrasena = findViewById(R.id.txtContrasena);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtDescripcion = findViewById(R.id.txtDescripcion);
        btnFoto = findViewById(R.id.btnFoto);
        btnEntrar = findViewById(R.id.btnEntrar);

        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
