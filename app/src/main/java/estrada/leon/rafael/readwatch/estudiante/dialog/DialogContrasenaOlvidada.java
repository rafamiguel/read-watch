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

import estrada.leon.rafael.readwatch.R;

public class DialogContrasenaOlvidada extends AppCompatDialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final EditText txtCorreo;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_olvido_contrasena, null);
        txtCorreo=view.findViewById(R.id.txtCorreo);
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
