package estrada.leon.rafael.readwatch;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class DialogInsertarMateria extends AppCompatDialogFragment {

    EditText txtNombre, txtFoto;
    RequestQueue request;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_insertar_materia, null);
        txtNombre = view.findViewById(R.id.txtNombre);
        txtFoto = view.findViewById(R.id.txtFoto);
        request= Volley.newRequestQueue(getContext());

        builder.setView(view)
                .setTitle("Nueva Materia")
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        return builder.create();
    }
}
