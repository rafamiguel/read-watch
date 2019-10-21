package estrada.leon.rafael.readwatch.CorreoElectronico;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Random;

import estrada.leon.rafael.readwatch.R;

public class MainCorreo extends AppCompatActivity implements View.OnClickListener{
    private EditText editTextEmail;
    private EditText editTextSubject;
    private EditText editTextMessage;
    private final String caracteres = "abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ1234567890!#$%&/()=?¡¿'|°";
    private String nuevaContraseña="";
    private Random rand;
    //Send button
    private Button buttonSend;
    JsonObjectRequest jsonObjectRequest;
    RequestQueue request;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recuperar_correo);
        request= Volley.newRequestQueue(getApplicationContext());
        //Initializing the views
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextSubject = (EditText) findViewById(R.id.editTextSubject);
        editTextMessage = (EditText) findViewById(R.id.editTextMessage);

        buttonSend = (Button) findViewById(R.id.buttonSend);

        //Adding click listener
        buttonSend.setOnClickListener(this);
    }
    private void sendEmail() {
        //Getting content for email
        String email = editTextEmail.getText().toString().trim();
        String subject = editTextSubject.getText().toString().trim();
        String message = editTextMessage.getText().toString().trim();
        nuevaContraseña="";
        rand = new Random();
        for(int i=0;i<15;i++){
        nuevaContraseña+=caracteres.charAt(rand.nextInt(79));
        }

        //Creating SendMail object
        SendMail sm = new SendMail(this, email, "Reestablecimiento de contraseña", "Nueva contraseña:\n"+nuevaContraseña);

        //Executing sendmail to send email
        sm.execute();


}

    @Override
    public void onClick(View view) {
        sendEmail();
    }
}
