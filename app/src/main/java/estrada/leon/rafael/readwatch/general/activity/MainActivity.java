package estrada.leon.rafael.readwatch.general.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import estrada.leon.rafael.readwatch.BtnOpciones;
import estrada.leon.rafael.readwatch.ModificarEliminar;
import estrada.leon.rafael.readwatch.NotificacionPropuestaAceptada;
import estrada.leon.rafael.readwatch.NotificacionPublicacionEliminada;
import estrada.leon.rafael.readwatch.estudiante.dialog.DialogContrasenaOlvidada;
import estrada.leon.rafael.readwatch.administrador.menu.MenuAdministrador;
import estrada.leon.rafael.readwatch.estudiante.menu.MenuEstudiante;
import estrada.leon.rafael.readwatch.R;
import estrada.leon.rafael.readwatch.estudiante.activity.RegistrarEstudiante;

public class MainActivity extends AppCompatActivity {
    ImageView logo;
    EditText txtUsuario,txtContra;
    Button btnEntrar;
    TextView lblContra,lblCuenta,lblRegistrar;
    Intent entrar;
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
                String user=txtUsuario.getText().toString();

                if(user.equals("admin")) {
                    entrar = new Intent(MainActivity.this, MenuAdministrador.class);
                    startActivity(entrar);
                }else{
                    entrar = new Intent(MainActivity.this, MenuEstudiante.class);
                    startActivity(entrar);
                }
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
                entrar = new Intent(MainActivity.this, NotificacionPropuestaAceptada.class);
                startActivity(entrar);
            }
        });
    }
}
