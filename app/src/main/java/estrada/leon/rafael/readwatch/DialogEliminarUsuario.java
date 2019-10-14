package estrada.leon.rafael.readwatch;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class DialogEliminarUsuario extends AppCompatDialogFragment implements Response.ErrorListener, Response.Listener {
    ProgressDialog progreso;
    JsonObjectRequest jsonObjectRequest;
    RequestQueue request;
    private Button btnEliminar, btnSuspender;
    String nombre;
    String apellidos;
    String url;

    public void DialogEliminarUsuario(String nombre, String apellido){

        this.nombre = nombre;
        this.apellidos= apellido;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialogeliminarusuario, null);
        request= Volley.newRequestQueue(getContext());

        builder.setView(view)

                .setMessage("¿Que desea hacer con el usuario?")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        btnEliminar = view.findViewById(R.id.btnEliminar);
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarUsuario();
            }
        });
        btnSuspender = view.findViewById(R.id.btnSuspender);
        btnSuspender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                suspenderUsuario();
            }
        });

        return builder.create();
    }

    private void suspenderUsuario() {
        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();
        url = "https://readandwatch.herokuapp.com/php/suspenderEstudiante.php?nombre="+nombre+"&apellidos="+apellidos;
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progreso.hide();
                Toast.makeText(getContext(),"Se suspendio al alumno correctamente",Toast.LENGTH_SHORT).show();
            }
        }, this);
        request.add(jsonObjectRequest);
    }

    private void eliminarUsuario() {
        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Cargando...");
        progreso.show();
        url = "https://readandwatch.herokuapp.com/php/eliminarUsuario.php?nombre="+nombre+"&apellidos="+apellidos;
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this,this);
        request.add(jsonObjectRequest);
    }


    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.hide();
        Toast.makeText(getContext(),error.toString(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(Object response) {
        progreso.hide();
        Toast.makeText(getContext(),"Se elimino correctamente",Toast.LENGTH_SHORT).show();
    }
}
