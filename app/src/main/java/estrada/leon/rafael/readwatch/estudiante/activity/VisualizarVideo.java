package estrada.leon.rafael.readwatch.estudiante.activity;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import estrada.leon.rafael.readwatch.R;

public class VisualizarVideo extends AppCompatActivity {
    WebView btnMiniatura;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_video);
        btnMiniatura = findViewById(R.id.btnMiniatura);
        btnMiniatura.getSettings().setJavaScriptEnabled(true);
        btnMiniatura.setWebChromeClient(new WebChromeClient() {});
        Bundle extras = getIntent().getExtras();
        url = extras.getString("url");
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        btnMiniatura.loadData(url, "text/html" , "utf-8" );
    }
}
