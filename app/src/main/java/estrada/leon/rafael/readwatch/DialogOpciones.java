package estrada.leon.rafael.readwatch;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class DialogOpciones extends AppCompatDialogFragment {
    TextView lblEliminar, lblModificar, lblAnadir, title;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_opciones, null);
        builder.setView(view);
        title = new TextView(getContext());
        title.setText("OPCIONES");
        title.setGravity(Gravity.CENTER);
        title.setTextSize(20);
        title.setTextColor(Color.BLACK);

        lblEliminar = view.findViewById(R.id.lblEliminar);
        lblModificar = view.findViewById(R.id.lblModificar);
        lblAnadir = view.findViewById(R.id.lblAnadir);
        builder.setCustomTitle(title);
        return builder.create();

    }
}
