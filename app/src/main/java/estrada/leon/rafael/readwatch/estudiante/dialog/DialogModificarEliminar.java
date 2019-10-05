package estrada.leon.rafael.readwatch.estudiante.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import estrada.leon.rafael.readwatch.R;
import estrada.leon.rafael.readwatch.estudiante.fragment.ElegirDocumento;
import estrada.leon.rafael.readwatch.estudiante.fragment.MainComentario;
import estrada.leon.rafael.readwatch.estudiante.interfaces.iComunicacionFragments;
import estrada.leon.rafael.readwatch.estudiante.menu.MenuEstudiante;

public class DialogModificarEliminar extends AppCompatDialogFragment {
    TextView lblEliminar, lblModificar;
    IOpcionesComentario listenerComentario;
    IOpcionesVidDoc listenerVidDoc;
    int opcion;

    public void setOpcion(int opcion){
        this.opcion=opcion;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_modificar_eliminar, null);
        builder.setView(view)
                .setTitle("OPCIONES");

        lblEliminar = view.findViewById(R.id.lblEliminar);
        lblModificar = view.findViewById(R.id.lblAnadir);
        lblEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(opcion==3){
                    SharedPreferences preferences = getContext().getSharedPreferences("comentarioSeleccionado", Context.MODE_PRIVATE);
                    int idComentario = preferences.getInt("idComentario", 0);
                    listenerComentario.eliminarCom(idComentario);
                }
            }
        });
        lblModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(opcion==1) {
                    listenerVidDoc.resubirVideo();
                }else if(opcion==2){
                    listenerVidDoc.resubirDoc();
                }else if(opcion==3){
                    listenerComentario.resubirCom();
                }
            }
        });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity actividad;
        super.onAttach(context);
        if (context instanceof MenuEstudiante) {
            actividad= (Activity) context;
            listenerVidDoc=(IOpcionesVidDoc) actividad;
        }else if(context instanceof MainComentario){
            actividad= (Activity) context;
            listenerComentario=(IOpcionesComentario) actividad;
        }
        if (context instanceof IOpcionesComentario) {
            listenerComentario = (IOpcionesComentario) context;
        }else if(context instanceof IOpcionesVidDoc) {
            listenerVidDoc = (IOpcionesVidDoc) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public interface IOpcionesComentario{
        void resubirCom();
        void eliminarCom(int idComentario);
    }
    public interface IOpcionesVidDoc{
        void resubirVideo();
        void resubirDoc();
    }
}
