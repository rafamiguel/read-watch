package estrada.leon.rafael.readwatch;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;

public class MainVideos extends AppCompatActivity {
    ProgressDialog progreso;
    JsonObjectRequest jsonObjectRequest;
    RequestQueue request;

    RecyclerView recyclerView;
    Vector<YouTubeVideos> youtubeVideos = new Vector<YouTubeVideos>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_videos);
        request= Volley.newRequestQueue(getApplicationContext());

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.requestFocus();
        recyclerView.setLayoutManager( new LinearLayoutManager(this));

        buscarVideos();

    }

    private void buscarVideos() {
        String url;
        url = "https://readandwatch.herokuapp.com/php/buscarRutaVideo.php";
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String ruta;
                JSONArray json;
                JSONObject jsonObject=null;
                json = response.optJSONArray("usuario");
                for(int i=0;i<json.length();i++) {
                    try {
                        jsonObject = json.getJSONObject(i);
                        ruta = jsonObject.getString("ruta");
                        for(int a=0; a<ruta.length();a++){

                            if (String.valueOf(ruta.charAt(a)).equals("=")) {
                                ruta = ruta.substring(a+1);
                                youtubeVideos.add(new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/" + ruta + "\" frameborder=\"0\" allowFullScreen></iframe>"));
                                break;
                            }
                        }
                            //String[] parts = ruta.split("=");
                            //ruta = parts[1];

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                VideosYouTubeAdapter videoAdapter = new VideosYouTubeAdapter(youtubeVideos);
                recyclerView.setAdapter(videoAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        request.add(jsonObjectRequest);
    }
}
