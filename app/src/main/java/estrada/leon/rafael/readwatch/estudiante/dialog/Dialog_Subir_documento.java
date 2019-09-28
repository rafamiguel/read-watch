package estrada.leon.rafael.readwatch.estudiante.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.TextView;

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

import estrada.leon.rafael.readwatch.MainFileManager;
import estrada.leon.rafael.readwatch.R;

public class Dialog_Subir_documento extends AppCompatDialogFragment implements
        Response.Listener<JSONObject>, Response.ErrorListener{
    Intent entrar;
    ProgressDialog progreso;
    JsonObjectRequest jsonObjectRequest;
    RequestQueue request;
    EditText txtDescripcion,txtTitulo;
    Spinner spinner_tema,spinner_materia;
    public static final int PREGUNTAR=1,RESUBIR=2, MATERIA=3;
    int modo;

    public void setModo(int modo){
        this.modo = modo;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        TextView lblElegirDocumento;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_subir_documento, null);
        txtDescripcion=view.findViewById(R.id.txtDescripcion);
        txtTitulo=view.findViewById(R.id.txtTitulo);
        spinner_tema=view.findViewById(R.id.spinner_tema);
        spinner_materia=view.findViewById(R.id.spinner_materia);
        if(modo==PREGUNTAR || modo==RESUBIR || modo==MATERIA){
            spinner_materia.setVisibility(View.GONE);
            spinner_tema.setVisibility(View.GONE);
        }
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
                .setTitle("Subir documento")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Subir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        subirDocWebService(txtDescripcion.getText().toString(),txtTitulo.getText().toString());
                    }
                });

        lblElegirDocumento = view.findViewById(R.id.lblElegirDocumento);
        lblElegirDocumento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                entrar = new Intent(getContext(), MainFileManager.class);
                startActivity(entrar);
            }
        });
        if(modo==RESUBIR){
            builder.setView(view)
                    .setTitle("Modificar")
                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .setPositiveButton("Modificar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            subirDocWebService(txtDescripcion.getText().toString(),txtTitulo.getText().toString());
                        }
                    });
        }else{
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
                            subirDocWebService(txtDescripcion.getText().toString(),txtTitulo.getText().toString());
                        }
                    });
        }
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
    public void subirDocWebService(String descripcion,String ruta){
        SharedPreferences preferences = getContext().getSharedPreferences("Datos usuario", Context.MODE_PRIVATE);
        int idUsuario = preferences.getInt("idUsuario", 0);
        int idTema, idPregunta,idVidDoc;

        preferences = getContext().getSharedPreferences("Tema", Context.MODE_PRIVATE);
        idTema = preferences.getInt("tema", 0);

        request= Volley.newRequestQueue(getContext());
        String url="";
        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String datetime = dateformat.format(c.getTime());
        switch(modo){
            case PREGUNTAR:
                preferences = getContext().getSharedPreferences("pregunta", Context.MODE_PRIVATE);
                idPregunta = preferences.getInt("idPregunta",0);
                //Opción del documento en Temas Libres
                url = "https://readandwatch.herokuapp.com/php/insertarDocPreg.php?" +
                        "idPregunta=" + idPregunta  + "&tipo=d&descripcion=" + descripcion + "&ruta=" + ruta + "&fechaSubida=" + datetime + "&idUsuario=" + idUsuario;
                url=url.replace(" ", "%20");
                break;
            case RESUBIR:
                preferences = getContext().getSharedPreferences("VidDocSeleccionado", Context.MODE_PRIVATE);
                idVidDoc = preferences.getInt("idVidDoc",0);
                url = "https://readandwatch.herokuapp.com/php/updateVidDoc.php?" +
                        "idVidDoc=" + idVidDoc+"&idTema="+idTema + "&tipo=d&descripcion=" + descripcion + "&ruta=" + ruta + "&fechaSubida=" + datetime + "&idUsuario=" + idUsuario;
                url=url.replace(" ", "%20");
                break;
            case MATERIA:
                url = "https://readandwatch.herokuapp.com/php/insertarVidDoc.php?" +
                        "idTema=" + idTema + "&tipo=d&descripcion=" + descripcion + "&ruta=" + ruta + "&fechaSubida=" + datetime + "&idUsuario=" + idUsuario;
                url = url.replace(" ", "%20");
                break;
            default:

        }

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

    }

    @Override
    public void onResponse(JSONObject response) {

    }
}
