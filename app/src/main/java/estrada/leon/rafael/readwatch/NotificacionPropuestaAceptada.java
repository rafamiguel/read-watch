package estrada.leon.rafael.readwatch;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;

public class NotificacionPropuestaAceptada extends AppCompatActivity {

    Button btnFelicidades;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacion_propuesta_aceptada);
        btnFelicidades = findViewById(R.id.btnFelicidades);
        btnFelicidades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(NotificacionPropuestaAceptada.this);
                builder.setMessage("El tema que propusiste fue añadido")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });

                builder.setTitle(Html.fromHtml("<font color='#1D56EE'>¡Felicidades!</font>"));
                builder.create().show();
            }
        });
    }
}
