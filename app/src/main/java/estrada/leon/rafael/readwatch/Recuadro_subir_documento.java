package estrada.leon.rafael.readwatch;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Recuadro_subir_documento extends AppCompatActivity {
    Button btnSubir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuadro_subir_documento);
        btnSubir = findViewById(R.id.btnSubir);

        btnSubir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }

        });
    }
    public void openDialog(){
        Dialog_Recuadro_Subir_documento nuevo = new Dialog_Recuadro_Subir_documento();
        nuevo.show(getSupportFragmentManager(), "ejemplo");
    }
}
