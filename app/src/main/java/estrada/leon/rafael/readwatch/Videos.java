package estrada.leon.rafael.readwatch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import estrada.leon.rafael.readwatch.Estudiante.POJO.POJOVideos;

public class Videos extends AppCompatActivity {

    RecyclerView recyclerVideo;
    List<POJOVideos> POJOVideosList = new ArrayList<>();
    AdapterVideo adapterVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);

        recyclerVideo = findViewById(R.id.recyclerVideo);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerVideo.setLayoutManager(linearLayoutManager);
        cargarDatos();
        adapterVideo = new AdapterVideo(this, POJOVideosList);
        recyclerVideo.setAdapter(adapterVideo);


    }

    public void cargarDatos(){
        POJOVideosList.add(new POJOVideos("","Rafita", "Video POJO", "", "", "", "Muy buen video", "", ""));
        POJOVideosList.add(new POJOVideos("","MIKE", "Video POJO", "", "", "", "Muy buen video", "", ""));
        POJOVideosList.add(new POJOVideos("","Pintor", "Video POJO", "", "", "", "Muy buen video", "", ""));
        POJOVideosList.add(new POJOVideos("","Gabriel", "Video POJO", "", "", "", "Muy buen video", "", ""));
    }
}
