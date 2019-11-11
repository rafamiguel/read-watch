package estrada.leon.rafael.readwatch.estudiante.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import estrada.leon.rafael.readwatch.R;
import estrada.leon.rafael.readwatch.estudiante.fragment.TemasPropuestos;

public class DialogOpcTemaSubtema extends AppCompatDialogFragment {

    Context context;
    Button btnTema, btnSubtema;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context=activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_opc_tema_subtema, null);
        btnTema = view.findViewById(R.id.btnTema);
        btnTema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnSubtema = view.findViewById(R.id.btnSubtema);
        btnSubtema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        builder.setView(view)
                .setTitle("¿Quieres ver la lista de temas o subtemas?");
        return builder.create();
    }
    }