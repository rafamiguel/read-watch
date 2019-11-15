package estrada.leon.rafael.readwatch.general.funciones;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import estrada.leon.rafael.readwatch.estudiante.pojo.Materias;
import estrada.leon.rafael.readwatch.estudiante.pojo.Subtemas;
import estrada.leon.rafael.readwatch.estudiante.pojo.Temas;
import estrada.leon.rafael.readwatch.estudiante.pojo.Votos;

public class ActualizarVotaciones {
    private Materias materia;
    private Temas tema;
    private Subtemas subtema;
    private DatabaseReference rootReference;
    private String materiaMasVotada;
    private String temaMasVotado;
    private String subtemaMasVotado;
    private String materiaActual;
    private List<Votos> votos;
    private int contadorMateriaPasada;
    private int contadorMateriaActual;
    public ActualizarVotaciones() {
        rootReference = FirebaseDatabase.getInstance().getReference();
        obtenerMateria();
    }

    public void obtenerMateriaMasVotada(){
        votos = new ArrayList<>();
        rootReference.child("votoMateria").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    votos.add(snapshot.getValue(Votos.class));
                }
                contadorMateriaActual = 0;
                contadorMateriaPasada = 0;
                for(int i = 0 ; i < votos.size() ; i++){
                    materiaActual = votos.get(i).getNombre();
                    for (int j=0; j<votos.size() ; j++){
                        if(votos.get(j).getNombre().equals(materiaActual)){
                            contadorMateriaActual++;
                        }
                    }
                    if(contadorMateriaActual>contadorMateriaPasada){
                        materiaMasVotada = materiaActual;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void obtenerMateria(){
        rootReference.child("materia").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    materia = snapshot.getValue(Materias.class);
                    if(materiaMasVotada.equals(materia.getNombre())){
                        obtenerTema();
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void obtenerTema(){
        rootReference.child("tema").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    tema = snapshot.getValue(Temas.class);
                    obtenerSubtema();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void obtenerSubtema(){
        rootReference.child("subtema").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    subtema = snapshot.getValue(Subtemas.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
