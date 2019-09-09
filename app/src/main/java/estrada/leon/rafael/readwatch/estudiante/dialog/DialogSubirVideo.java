package estrada.leon.rafael.readwatch.estudiante.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import estrada.leon.rafael.readwatch.R;

public class DialogSubirVideo extends AppCompatDialogFragment implements
        Response.Listener<JSONObject>, Response.ErrorListener {
    ProgressDialog progreso;
    JsonObjectRequest jsonObjectRequest;
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
        spinner_materia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0){
                    cargarListaTemasWebService(parent.getSelectedItem().toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        request= Volley.newRequestQueue(getContext());
        cargarListaMateriasWebService();
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
                        subirVidWebService(txtDescripcion.getText().toString(),txtLink.getText().toString());
                    }
                });
        return builder.create();


    }
    public void cargarListaMateriasWebService(){
        String url;
        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();
        url = "https://readandwatch.herokuapp.com/php/listaMaterias.php";
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray json;
                JSONObject jsonObject=null;
                json = response.optJSONArray("usuario");
                List<String> materias = new ArrayList<String>();
                materias.add("Selecciona una materia");
                ArrayAdapter<String> adapter;
                try {
                    for(int i=0;i<json.length();i++){
                        jsonObject=json.getJSONObject(i);
                        materias.add(jsonObject.optString("nombre"));
                    }
                    adapter = new ArrayAdapter<String>(getContext(),
                            android.R.layout.simple_spinner_item, materias);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner_materia.setAdapter(adapter);
                    progreso.hide();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, this);
        request.add(jsonObjectRequest);
    }
    public void cargarListaTemasWebService(String materia){
        String url;
        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();
        url = "https://readandwatch.herokuapp.com/php/listaTemas.php?materia="+materia;
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray json;
                        JSONObject jsonObject=null;
                        json = response.optJSONArray("usuario");
                        List<String> materias = new ArrayList<String>();
                        materias.add("Selecciona un tema");
                        ArrayAdapter<String> adapter;
                        try {
                            for(int i=0;i<json.length();i++){
                                jsonObject=json.getJSONObject(i);
                                materias.add(jsonObject.optString("nombre"));
                            }
                            adapter = new ArrayAdapter<String>(getContext(),
                                    android.R.layout.simple_spinner_item, materias);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner_tema.setAdapter(adapter);
                            progreso.hide();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, this);
        request.add(jsonObjectRequest);
    }
    public void subirVidWebService(String descripcion,String ruta){
        request= Volley.newRequestQueue(getContext());
        String url;
        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();
        SharedPreferences preferences = getContext().getSharedPreferences("Datos usuario", Context.MODE_PRIVATE);
        int idUsuario = preferences.getInt("idUsuario", 0);
        int idTema;

        preferences = getContext().getSharedPreferences("Tema", Context.MODE_PRIVATE);
        idTema = preferences.getInt("tema", 0);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String datetime = dateformat.format(c.getTime());
        url = "https://readandwatch.herokuapp.com/php/insertarVidDoc.php?" +
                "idTema="+idTema+"&tipo=v&descripcion="+descripcion+"&ruta="+ruta+"&fechaSubida="+datetime+"&idUsuario="+idUsuario;
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progreso.hide();
                // Toast.makeText(getContext(),"Video insertado con éxito",Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progreso.hide();
            }
        });
        request.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        //Toast.makeText(getContext(),"Error:\n"+error.getMessage(),Toast.LENGTH_LONG);
    }

    @Override
    public void onResponse(JSONObject response) {

    }
}
