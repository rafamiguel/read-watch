package estrada.leon.rafael.readwatch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class BotonAgregarVideo extends AppCompatActivity {
    Button btnSubirVideo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boton_agregar_video);
        btnSubirVideo = findViewById(R.id.btnSubirVideo);
        btnSubirVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
    }
    public void openDialog(){
        DialogSubirVideo nuevo = new DialogSubirVideo();
        nuevo.show(getSupportFragmentManager(), "ejemplo");
    }
}