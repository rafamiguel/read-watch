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
import android.widget.EditText;
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

import java.util.Random;

import estrada.leon.rafael.readwatch.CorreoElectronico.SendMail;
import estrada.leon.rafael.readwatch.R;

public class DialogContrasenaOlvidada extends AppCompatDialogFragment {
    private final String caracteres = "abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ1234567890!#$%&/()=?¡¿'|°";
    private String nuevaContraseña="", correo="";
    private Random rand;
    private Context context;
    ProgressDialog progreso;
    JsonObjectRequest jsonObjectRequest;
    RequestQueue request;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final EditText txtCorreo;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        request= Volley.newRequestQueue(getActivity());
        context = DialogContrasenaOlvidada.this.getContext();
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_olvido_contrasena, null);
        txtCorreo=view.findViewById(R.id.txtContrasena);
        builder.setView(view)
                .setMessage("Ingresa tu correo electrónico y presiona enviar")
                .setTitle("Reestablecer contraseña")
                .setCancelable(false)
                .setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(getContext(), "Si el correo '"+txtCorreo.getText()+
                                "' está registrado se enviará la nueva contraseña. Espere unos minutos.",
                                Toast.LENGTH_LONG).show();
                        correo=txtCorreo.getText().toString();

                        nuevaContraseña="";
                        rand = new Random();
                        for(int iterador=0;iterador<15;iterador++){
                            nuevaContraseña+=caracteres.charAt(rand.nextInt(79));
                        }

                        //Creating SendMail object
                        SendMail sm = new SendMail(context, correo, "Reestablecimiento de contraseña", "Nueva contraseña:\n"+nuevaContraseña);

                        //Executing sendmail to send email
                        sm.execute();

                        verificarExistencia(correo, nuevaContraseña);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        return builder.create();
    }

    private void verificarExistencia(String correo, String nuevaContraseña) {
        String url;
        url = "https://readandwatch.herokuapp.com/php/updateContrasenaCorreo.php?" +
                "correo="+correo+"&contrasena="+nuevaContraseña;
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                int success;
                JSONArray json;
                JSONObject jsonObject=null;
                json = response.optJSONArray("usuario");

                    try {
                        jsonObject = json.getJSONObject(0);
                        success = jsonObject.getInt("success");

                        if (success==0){
                           // Toast.makeText(context, "No se encontro el correo", Toast.LENGTH_SHORT).show();

                        }
                        else {
                          //  Toast.makeText(context, "Mensaje enviado", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        request.add(jsonObjectRequest);
    }
}
