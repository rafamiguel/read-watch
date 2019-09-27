package estrada.leon.rafael.readwatch.estudiante.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import estrada.leon.rafael.readwatch.R;

public class DialogBotonModificarEliminar extends AppCompatDialogFragment {
    TextView lblEliminar, lblModificar;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_boton_modificar_eliminar, null);
        builder.setView(view)
                .setTitle("OPCIONES");

        lblEliminar = view.findViewById(R.id.lblEliminar);
        lblModificar = view.findViewById(R.id.lblAnadir);
        lblEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        lblModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return builder.create();
    }
}
