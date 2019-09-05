package estrada.leon.rafael.readwatch.estudiante.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import estrada.leon.rafael.readwatch.R;

public class DialogSubirVideo extends AppCompatDialogFragment implements
        Response.Listener<JSONObject>, Response.ErrorListener {
    ProgressDialog progreso;
    JsonObjectRequest jsonObjectRequest;
    StringRequest stringRequest;
    RequestQueue request;
    EditText txtDescripcion,txtLink;
    Spinner spinner_tema,spinner_materia;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_subir_video, null);
        txtDescripcion=view.findViewById(R.id.txtDescripcion);
        txtLink=view.findViewById(R.id.txtLink);
        spinner_tema=view.findViewById(R.id.spinner_tema);
        spinner_materia=view.findViewById(R.id.spinner_materia);
        request= Volley.newRequestQueue(getContext());
        builder.setView(view)
                .setTitle("Subir Video")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Subir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        subirVidWebService();
                    }
                });
        return builder.create();


    }
    public void cargarListasWebService(){
        String url;
        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();
        url = "https://readandwatch.herokuapp.com/php/listaMaterias.php";
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray json;
                JSONObject jsonObject=null;
                json = response.optJSONArray("usuario");
                List<String> materias = new ArrayList<String>();

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, materias);

                spinner_tema.setAdapter(adapter);
                try {
                    for(int i=0;i<json.length();i++){
                        jsonObject=json.getJSONObject(i);
                        materias.add(jsonObject.optString("idUsuario"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                progreso.hide();
            }
        }, this);
        request.add(jsonObjectRequest);
    }
    public void subirVidWebService(){
        String url;
        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();
        url = "https://readandwatch.herokuapp.com/php/cargarVidDoc.php?" +
                "idTema=2&tipo=v";
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, this);
        request.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(JSONObject response) {

    }
}
