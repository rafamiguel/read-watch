package estrada.leon.rafael.readwatch.estudiante.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import estrada.leon.rafael.readwatch.R;
import estrada.leon.rafael.readwatch.estudiante.fragment.ElegirDocumento;
import estrada.leon.rafael.readwatch.estudiante.fragment.ElegirVideo;
import estrada.leon.rafael.readwatch.estudiante.fragment.MainComentario;
import estrada.leon.rafael.readwatch.estudiante.interfaces.iComunicacionFragments;
import estrada.leon.rafael.readwatch.estudiante.menu.MenuEstudiante;

public class DialogModificarEliminar extends AppCompatDialogFragment {
    TextView lblEliminar, lblModificar;
    IOpcionesComentario listenerComentario;
    IOpcionesVidDoc listenerVidDoc;
    int opcion;
    Context contexto;

    public void setOpcion(int opcion){
        this.opcion=opcion;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(contexto);
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_modificar_eliminar, null);
        builder.setView(view)
                .setTitle("OPCIONES");

        lblEliminar = view.findViewById(R.id.lblEliminar);
        lblModificar = view.findViewById(R.id.lblAnadir);
        lblEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(opcion==1){
                    SharedPreferences preferences = getContext().getSharedPreferences("VidDocSeleccionado", Context.MODE_PRIVATE);
                    int idVidDoc = preferences.getInt("idVidDoc",0);
                    listenerVidDoc.eliminarVidDoc(idVidDoc, 1);
                    Fragment fragment =new ElegirVideo();
                    if(contexto instanceof MenuEstudiante) {
                        ((MenuEstudiante) contexto).getSupportFragmentManager().beginTransaction().replace(R.id.layoutPrincipal, fragment).commit();
                    }
                }
                if(opcion==2){
                    SharedPreferences preferences = getContext().getSharedPreferences("VidDocSeleccionado", Context.MODE_PRIVATE);
                    int idVidDoc = preferences.getInt("idVidDoc",0);
                    listenerVidDoc.eliminarVidDoc(idVidDoc, 2);
                    Fragment fragment =new ElegirDocumento();
                    if(contexto instanceof MenuEstudiante) {
                        ((MenuEstudiante) contexto).getSupportFragmentManager().beginTransaction().replace(R.id.layoutPrincipal, fragment).commit();
                    }
                }
                if(opcion==3) {
                    SharedPreferences preferences = getContext().getSharedPreferences("comentarioSeleccionado", Context.MODE_PRIVATE);
                    int idComentario = preferences.getInt("idComentario", 0);
                    listenerComentario.eliminarCom(idComentario);
//                    if(contexto instanceof MainComentario) {
//                        Intent entrar = new Intent(contexto, MainComentario.class);
//                        contexto.startActivity(entrar);
//                    }
                }

                dismiss();
            }
        });
        lblModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(opcion==1) {
                    listenerVidDoc.resubirVideo();
                    Fragment fragment =new ElegirVideo();
                    if(contexto instanceof MenuEstudiante) {
                        ((MenuEstudiante) contexto).getSupportFragmentManager().beginTransaction().replace(R.id.layoutPrincipal, fragment).commit();
                    }
                }else if(opcion==2){
                    listenerVidDoc.resubirDoc();
                    Fragment fragment =new ElegirDocumento();
                    if(contexto instanceof MenuEstudiante) {
                        ((MenuEstudiante) contexto).getSupportFragmentManager().beginTransaction().replace(R.id.layoutPrincipal, fragment).commit();
                    }
                }else if(opcion==3){
                    SharedPreferences preferences = getContext().getSharedPreferences("comentarioSeleccionado", Context.MODE_PRIVATE);
                    int idComentario = preferences.getInt("idComentario", 0);
                    listenerComentario.resubirCom(idComentario);
                }
                dismiss();
            }
        });
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity actividad;
        contexto = context;
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
        }
        if(context instanceof IOpcionesVidDoc) {
            listenerVidDoc = (IOpcionesVidDoc) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public interface IOpcionesComentario{
        void resubirCom(int idComentario);
        void eliminarCom(int idComentario);
    }
    public interface IOpcionesVidDoc{
        void resubirVideo();
        void resubirDoc();
        void eliminarVidDoc(int idVidDoc, int opc);
    }
}
