package estrada.leon.rafael.readwatch;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class VentanaEmergenteDeSeleccionDeVistaDePropuestasUsuarioEstudiante extends AppCompatActivity {
    Button btnVerPropuesta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ventana_emergente_de_seleccion_de_vista_de_propuestas_usuario_estudiante);
    btnVerPropuesta = findViewById(R.id.btnVerPropuestas);
    btnVerPropuesta.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            AlertDialog.Builder builder = new AlertDialog.Builder(VentanaEmergenteDeSeleccionDeVistaDePropuestasUsuarioEstudiante.this);
            builder.setMessage("¿Quieres ver la lista de propuestas de materias o de un tema?");
            builder.setPositiveButton("Tema", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.setNegativeButton("Materia", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
           
            AlertDialog alert = builder.create();
            alert.show();
        }
    });
    }
}
