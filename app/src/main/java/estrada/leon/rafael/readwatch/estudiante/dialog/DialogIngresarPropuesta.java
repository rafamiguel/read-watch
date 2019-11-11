package estrada.leon.rafael.readwatch.estudiante.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import estrada.leon.rafael.readwatch.R;
import estrada.leon.rafael.readwatch.general.pojo.Sesion;

public class DialogIngresarPropuesta extends AppCompatDialogFragment implements
        Response.Listener<JSONObject>, Response.ErrorListener  {
    EditText txtNombre;
    Spinner spinner_tema,spinner_materia;
    ProgressDialog progreso;
    JsonObjectRequest jsonObjectRequest;
    RequestQueue request;
    DatabaseReference rootReference;
    boolean  mismoUsuario = false;
    boolean existente = false;
    Context context;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_boton_hacer_propuesta, null);
        txtNombre=view.findViewById(R.id.txtNombreSubtema);
        spinner_tema=view.findViewById(R.id.spinner_tema);
        spinner_materia=view.findViewById(R.id.spinner_materia);
        request= Volley.newRequestQueue(getContext());
        rootReference = FirebaseDatabase.getInstance().getReference();
        cargarListaMateriasWebService();
        builder.setView(view)
                .setTitle("Ingresa tu propuesta de tema")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String materia = spinner_materia.getSelectedItem().toString();
                        String tema = spinner_tema.getSelectedItem().toString();
                        if(tema.equals("Selecciona un tema") || tema.equals("Seleccionar tema")){
                        subirPropuestaTema(materia);
                        }else{
                            subirPropuestaSubTema(materia,tema);
                        }
                    }
                });
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
        return builder.create();
    }

    private void subirPropuestaTema(String materia) {
        if(!mismoUsuario && !existente) {
            String nombre = txtNombre.getText().toString().toLowerCase();
            Map<String, Object> datosMateria = new HashMap<>();
            datosMateria.put("idUsuario", Sesion.getSesion().getId());
            datosMateria.put("nombre", nombre);
            datosMateria.put("votos", 0);
            datosMateria.put("materia",materia);
            rootReference.child("tema").push().setValue(datosMateria);
        }else if(existente){
            Toast.makeText(context, "Ya hay una propuesta con este nombre", Toast.LENGTH_LONG).show();
        }else if(mismoUsuario){
            Toast.makeText(context, "Este usuario ya ha registrado una materia esta semana", Toast.LENGTH_LONG).show();
        }
    }

    private void subirPropuestaSubTema(String materia, String tema) {
        if(!mismoUsuario && !existente) {
            String nombre = txtNombre.getText().toString().toLowerCase();
            Map<String, Object> datosMateria = new HashMap<>();
            datosMateria.put("idUsuario", Sesion.getSesion().getId());
            datosMateria.put("nombre", nombre);
            datosMateria.put("votos", 0);
            datosMateria.put("materia",materia);
            datosMateria.put("tema",tema);
            rootReference.child("subtema").push().setValue(datosMateria);
        }else if(existente){
            Toast.makeText(context, "Ya hay una propuesta con este nombre", Toast.LENGTH_LONG).show();
        }else if(mismoUsuario){
            Toast.makeText(context, "Este usuario ya ha registrado una materia esta semana", Toast.LENGTH_LONG).show();
        }
    }

    public void cargarListaMateriasWebService(){
        String url;
        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Cargando materias...");
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
        progreso.setMessage("Cargando temas...");
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


    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.hide();
    }

    @Override
    public void onResponse(JSONObject response) {

    }
}
