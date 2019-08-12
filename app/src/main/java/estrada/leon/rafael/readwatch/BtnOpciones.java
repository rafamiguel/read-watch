package estrada.leon.rafael.readwatch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BtnOpciones extends AppCompatActivity {
    Button btnOpciones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_btn_opciones);
        btnOpciones = findViewById(R.id.btnOpciones);
        btnOpciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
    }
    public void openDialog(){
        DialogOpciones dialogOpciones = new DialogOpciones();
        dialogOpciones.show(getSupportFragmentManager(), "ejemplo");
    }
}
