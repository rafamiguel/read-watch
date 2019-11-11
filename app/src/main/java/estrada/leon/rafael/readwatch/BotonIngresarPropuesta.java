package estrada.leon.rafael.readwatch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import estrada.leon.rafael.readwatch.estudiante.dialog.DialogIngresarPropuesta;

public class BotonIngresarPropuesta extends AppCompatActivity {

    Button btnPropuesta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boton_ingresar_propuesta);

        btnPropuesta = findViewById(R.id.btnPropuesta);
        btnPropuesta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
    }
    public void openDialog(){
        DialogIngresarPropuesta nuevo = new DialogIngresarPropuesta();
        nuevo.show(getSupportFragmentManager(), "ejemplo");
    }
}
