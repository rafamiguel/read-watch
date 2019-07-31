package estrada.leon.rafael.readwatch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Elige_una_materia_usuario_estudiante extends AppCompatActivity {
    Button btnMenu, btnIngles, btnMatematicas, btnEspanol;
    TextView txtTitulo, txtBarrainferior, txtBarrasuperior;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elige_una_materia_usuario_estudiante);

        btnMenu = findViewById(R.id.btnMenu);
        btnIngles = findViewById(R.id.btnIngles);
        btnMatematicas = findViewById(R.id.btnMatematicas);
        btnEspanol = findViewById(R.id.btnEspanol);
        txtTitulo = findViewById(R.id.txtTitulo);
        txtBarrainferior = findViewById(R.id.txtBarrainferior);
        txtBarrasuperior = findViewById(R.id.txtBarrasuperior);

        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnIngles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnMatematicas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnEspanol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}
