package estrada.leon.rafael.readwatch.estudiante.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import estrada.leon.rafael.readwatch.MainFileManager;
import estrada.leon.rafael.readwatch.R;
import estrada.leon.rafael.readwatch.general.activity.MainActivity;

public class Dialog_Recuadro_Subir_documento extends AppCompatDialogFragment {
    Intent entrar;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        TextView lblElegirDocumento;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.estilo_recuadro_subir_documento, null);
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
        return builder.create();
    }
}
