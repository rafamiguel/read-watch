package estrada.leon.rafael.readwatch;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import estrada.leon.rafael.readwatch.administrador.menu.MenuAdministrador;
import estrada.leon.rafael.readwatch.estudiante.fragment.MainComentario;

public class DialogModificarEliminarAdm extends AppCompatDialogFragment {
    TextView lblEliminar;
    TextView lblModificar;
    //IOpcionesComentario listenerComentario;
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
        lblModificar = view.findViewById(R.id.lblAnadir);
        if (opcion==4){ lblModificar.setVisibility(View.INVISIBLE);}
        lblEliminar = view.findViewById(R.id.lblEliminar);

        lblEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(opcion==1){
                    SharedPreferences preferences = getContext().getSharedPreferences("VidDocSeleccionado", Context.MODE_PRIVATE);
                    int idVidDoc = preferences.getInt("idVidDoc",0);
                    listenerVidDoc.eliminarVidDoc(idVidDoc, 1);
                }
                if(opcion==2){
                    SharedPreferences preferences = getContext().getSharedPreferences("VidDocSeleccionado", Context.MODE_PRIVATE);
                    int idVidDoc = preferences.getInt("idVidDoc",0);
                    listenerVidDoc.eliminarVidDoc(idVidDoc, 2);
                }
                if(opcion==3){
                    SharedPreferences preferences = getContext().getSharedPreferences("comentarioSeleccionado", Context.MODE_PRIVATE);
                    int idComentario = preferences.getInt("idComentario", 0);
                  //  listenerComentario.eliminarCom(idComentario);
                }
                if (opcion==4){
                    //lblModificar.setEnabled(false);

                    SharedPreferences preferences = getContext().getSharedPreferences("comentarioSeleccionado", Context.MODE_PRIVATE);
                    int idComentario = preferences.getInt("idComentario", 0);
                    listenerVidDoc.eliminarCom(idComentario);
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
                    //listenerComentario.resubirCom();
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
        if (context instanceof MenuAdministrador) {
            actividad = (Activity) context;
            listenerVidDoc = (IOpcionesVidDoc) actividad;
        }
        else if(context instanceof IOpcionesVidDoc) {
            listenerVidDoc = (IOpcionesVidDoc) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public interface IOpcionesVidDoc{
        void resubirVideo();
        void resubirDoc();
        void eliminarVidDoc(int idVidDoc, int opc);
        void eliminarCom(int idComentario);
    }
}
