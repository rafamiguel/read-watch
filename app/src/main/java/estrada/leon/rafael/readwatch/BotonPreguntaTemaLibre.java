package estrada.leon.rafael.readwatch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import estrada.leon.rafael.readwatch.estudiante.dialog.DialogHacerPregunta;

public class BotonPreguntaTemaLibre extends AppCompatActivity {
    Button btnPreguntar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boton_pregunta_tema_libre);
        btnPreguntar = findViewById(R.id.btnPreguntar);
        btnPreguntar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
    }
    public void openDialog(){
        DialogHacerPregunta nuevo = new DialogHacerPregunta();
        nuevo.show(getSupportFragmentManager(), "ejemplo");
    }
}
