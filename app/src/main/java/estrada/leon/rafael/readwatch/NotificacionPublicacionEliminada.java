package estrada.leon.rafael.readwatch;

import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NotificacionPublicacionEliminada extends AppCompatActivity {
    Button btnEliminar;
    TextView txtTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificacion_publicacion_eliminada);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(NotificacionPublicacionEliminada.this);
                builder.setMessage("Una de tus publicaciones ha sido eliminada, para ver más detalles ve a la sección del historial")
                        .setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                AlertDialog titulo = builder.create();
                titulo.setTitle("Publicación eliminada");
                titulo.show();

            }
        });
    }
}
