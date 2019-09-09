package estrada.leon.rafael.readwatch.estudiante.fragment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
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

import java.util.ArrayList;
import java.util.List;

import estrada.leon.rafael.readwatch.R;
import estrada.leon.rafael.readwatch.estudiante.adapter.AdapterComentario;
import estrada.leon.rafael.readwatch.estudiante.pojo.PojoComentario;

public class MainComentario extends AppCompatActivity implements  Response.Listener<JSONObject>,
        Response.ErrorListener {
    Button btnComentario;
    JsonObjectRequest jsonObjectRequest;
    RequestQueue request;
    RecyclerView recycler;
    List<PojoComentario> list= new ArrayList<>();
    AdapterComentario adapterComentario;
    int idVidDoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_comentario);
        recycler = findViewById(R.id.recyclerId);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(new LinearLayoutManager
                (this/*SI SE DETIENE LA APLICACION Y SE CAMBIÓ ESTO A UN FRAGMENT O DIALOG (PORQUE
                TENIA QUE SER UN DIALOG) CAMBIAR EL THIS POR GETCONTEXT.*/
                        ,LinearLayoutManager.VERTICAL,false));
        Bundle extras = getIntent().getExtras();
        int id = extras.getInt("idVidDoc");
        idVidDoc=id;
        request= Volley.newRequestQueue(this);
        cargarWebService();
    }

    private void cargarWebService() {
        list=new ArrayList<>();
        String url;
        url = "https://readandwatch.herokuapp.com/php/cargarComentarios.php?" +
                "idVidDoc="+idVidDoc;
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }
    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, "Error.\n "+error.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        JSONArray json;
        JSONObject jsonObject=null;
        PojoComentario comentario;
        json = response.optJSONArray("usuario");
        String nombre,comentarioString;
        try {
            for(int i=0;i<json.length();i++){
                jsonObject=json.getJSONObject(i);
                nombre=jsonObject.optString("idUsuario");
                comentarioString=jsonObject.optString("texto");
                comentario=new PojoComentario(nombre,comentarioString);
                list.add(comentario);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        adapterComentario = new AdapterComentario(this, list);
        recycler.setAdapter(adapterComentario);
    }

}
