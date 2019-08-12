package estrada.leon.rafael.readwatch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import estrada.leon.rafael.readwatch.general.DialogBotonModificarEliminar;

public class ModificarEliminar extends AppCompatActivity {
    Button btnModificar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_eliminar);
        btnModificar = findViewById(R.id.btnModificar);

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
    }
    public void openDialog() {
        DialogBotonModificarEliminar nuevo = new DialogBotonModificarEliminar();
        nuevo.show(getSupportFragmentManager(), "ejemplo");
    }
}
