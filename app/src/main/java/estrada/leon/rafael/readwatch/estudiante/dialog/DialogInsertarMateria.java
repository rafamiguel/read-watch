package estrada.leon.rafael.readwatch.estudiante.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

import estrada.leon.rafael.readwatch.R;
import estrada.leon.rafael.readwatch.estudiante.pojo.Materias;
import estrada.leon.rafael.readwatch.estudiante.pojo.MateriasPropuestas;
import estrada.leon.rafael.readwatch.general.pojo.Sesion;

public class DialogInsertarMateria extends AppCompatDialogFragment {

    EditText txtNombre;
    Button txtFoto;
    DatabaseReference rootReference;
    boolean  mismoUsuario = false;
    boolean existente = false;
    Context context;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        context=activity;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_insertar_materia, null);
        txtNombre = view.findViewById(R.id.txtNombre);
        txtFoto = view.findViewById(R.id.txtFoto);
        rootReference = FirebaseDatabase.getInstance().getReference();


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
                        rootReference.child("materia").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                MateriasPropuestas materia;
                                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                    materia = snapshot.getValue(MateriasPropuestas.class);
                                    if(materia.getIdUsuario() == Sesion.getSesion().getId()){
                                        mismoUsuario = true;
                                        break;
                                    }
                                    if(materia.getNombre().equals((txtNombre.getText().toString()).toLowerCase())){
                                        existente = true;
                                        break;
                                    }
                                }
                                insertarMateria();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                });
        return builder.create();
    }

    private void insertarMateria() {
        if(!mismoUsuario && !existente) {
            String nombre = txtNombre.getText().toString().toLowerCase();
            Map<String, Object> datosMateria = new HashMap<>();
            datosMateria.put("idUsuario", Sesion.getSesion().getId());
            datosMateria.put("nombre", nombre);
            datosMateria.put("votos", 0);
            rootReference.child("materia").push().setValue(datosMateria);
        }else if(existente){
            Toast.makeText(context, "Ya hay una propuesta con este nombre", Toast.LENGTH_LONG).show();
        }else if(mismoUsuario){
            Toast.makeText(context, "Este usuario ya ha registrado una materia esta semana", Toast.LENGTH_LONG).show();
        }
    }
}
