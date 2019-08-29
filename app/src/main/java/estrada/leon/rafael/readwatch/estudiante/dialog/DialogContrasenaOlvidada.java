package estrada.leon.rafael.readwatch.estudiante.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Random;

import estrada.leon.rafael.readwatch.CorreoElectronico.SendMail;
import estrada.leon.rafael.readwatch.R;

public class DialogContrasenaOlvidada extends AppCompatDialogFragment {
    private final String caracteres = "abcdefghijklmnñopqrstuvwxyzABCDEFGHIJKLMNÑOPQRSTUVWXYZ1234567890!#$%&/()=?¡¿'|°";
    private String nuevaContraseña="", correo="";
    private Random rand;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final EditText txtCorreo;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

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
                        SendMail sm = new SendMail(getContext(), correo, "Reestablecimiento de contraseña", "Nueva contraseña:\n"+nuevaContraseña);

                        //Executing sendmail to send email
                        sm.execute();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        return builder.create();
    }
}
