package estrada.leon.rafael.readwatch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import estrada.leon.rafael.readwatch.estudiante.dialog.DialogContrasenaOlvidada;

public class BtnOlvidoContrasena extends AppCompatActivity {
    Button btnContrasena;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btn_olvido_contrasena);
        btnContrasena = findViewById(R.id.btnContrasena);
        btnContrasena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();

            }
        });
    }

    public void openDialog() {
        DialogContrasenaOlvidada dialogContrasenaOlvidada = new DialogContrasenaOlvidada();
        dialogContrasenaOlvidada.show(getSupportFragmentManager(), "Example");
    }
}
