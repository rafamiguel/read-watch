package estrada.leon.rafael.readwatch.General.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import estrada.leon.rafael.readwatch.BtnOlvidoContrasena;
import estrada.leon.rafael.readwatch.Estudiante.Menu.MenuEstudiante;
import estrada.leon.rafael.readwatch.R;
import estrada.leon.rafael.readwatch.Estudiante.Activity.registrar_restudiante;

public class MainActivity extends AppCompatActivity {
    ImageView logo;
    EditText txtUsuario,txtContra;
    Button btnEntrar;
    TextView lblContra,lblCuenta,lblRegistrar;
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
                Intent registrar = new Intent(MainActivity.this, registrar_restudiante.class);
                startActivity(registrar);

            }
        });
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent entrar = new Intent(MainActivity.this, MenuEstudiante.class);
                startActivity(entrar);
            }
        });

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent entrar = new Intent(MainActivity.this, BtnOlvidoContrasena.class);
                startActivity(entrar);
            }
        });
    }
}
