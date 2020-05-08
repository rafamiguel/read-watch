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
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import estrada.leon.rafael.readwatch.R;

public class DialogHacerPregunta extends AppCompatDialogFragment implements
        Response.Listener<JSONObject>, Response.ErrorListener {
    EditText txtDescripcion, txtTitulo;
    ProgressDialog progreso;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_hacer_pregunta, null);
        txtDescripcion = view.findViewById(R.id.txtDescripcion);
        txtTitulo = view.findViewById(R.id.txtPregunta);
        request= Volley.newRequestQueue(getContext());

        builder.setView(view)
                .setTitle("Hacer pregunta")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        subirPregWebService(txtDescripcion.getText().toString(),txtTitulo.getText().toString());
                    }
                });
        return builder.create();
    }

    public void subirPregWebService(String descripcion, String titulo){
        request= Volley.newRequestQueue(getContext());
        String url;
        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();
        SharedPreferences preferences = getContext().getSharedPreferences("Datos usuario", Context.MODE_PRIVATE);
        int idUsuario = preferences.getInt("idUsuario", 0);
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String datetime = dateformat.format(c.getTime());
        titulo=titulo.replace("%", "%25");
        titulo=titulo.replace(" ", "%20");
        titulo=titulo.replace("+", "%2B");
        titulo=titulo.replace("<", "%3C");
        titulo=titulo.replace(">", "%3E");
        titulo=titulo.replace("#", "%23");
        titulo=titulo.replace("\\", "%5C");
        titulo=titulo.replace("&", "%26");
        descripcion=descripcion.replace("%", "%25");
        descripcion=descripcion.replace(" ", "%20");
        descripcion=descripcion.replace("+", "%2B");
        descripcion=descripcion.replace("<", "%3C");
        descripcion=descripcion.replace(">", "%3E");
        descripcion=descripcion.replace("#", "%23");
        descripcion=descripcion.replace("\\", "%5C");
        descripcion=descripcion.replace("&", "%26");
        url = "https://readandwatch.herokuapp.com/php/insertarPregunta.php?" +
                "idPregunta="+null+"&titulo="+titulo+"&descripcion="+descripcion+"&idUsuario="+idUsuario+"&fechaSubida="+datetime;
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>(){


            @Override
            public void onResponse(JSONObject response) {
                progreso.hide();
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
