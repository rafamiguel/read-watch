package estrada.leon.rafael.readwatch.general.funciones;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

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
    private Activity actividad;
    private ProgressDialog progreso;
    private String url;
    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;


    public ActualizarVotaciones(Activity actividad) {
        this.actividad = actividad;
        rootReference = FirebaseDatabase.getInstance().getReference();
        obtenerMateriaMasVotada();
    }

    public void obtenerMateriaMasVotada(){
        votos = new ArrayList<>();
        rootReference.child("votoMateria").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    votos.add(snapshot.getValue(Votos.class));
                }
                if(votos.size()>0) {
                    contadorMateriaActual = 0;
                    contadorMateriaPasada = 0;
                    for (int i = 0; i < votos.size(); i++) {
                        materiaActual = votos.get(i).getNombre();
                        for (int j = 0; j < votos.size(); j++) {
                            if (votos.get(j).getNombre().equals(materiaActual)) {
                                contadorMateriaActual++;
                            }
                        }
                        if (contadorMateriaActual > contadorMateriaPasada) {
                            materiaMasVotada = materiaActual;
                        }
                    }
                    obtenerMateria();
                }else{
                    obtenerTemaMasVotado();
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
                        obtenerTemaMasVotado();
                        return;
                    }
                }
                obtenerTemaMasVotado();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void obtenerTemaMasVotado(){
        votos = new ArrayList<>();
        rootReference.child("votoTema").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    votos.add(snapshot.getValue(Votos.class));
                }
                if(votos.size()>0) {
                    contadorMateriaActual = 0;
                    contadorMateriaPasada = 0;
                    for (int i = 0; i < votos.size(); i++) {
                        materiaActual = votos.get(i).getNombre();
                        for (int j = 0; j < votos.size(); j++) {
                            if (votos.get(j).getNombre().equals(materiaActual)) {
                                contadorMateriaActual++;
                            }
                        }
                        if (contadorMateriaActual > contadorMateriaPasada) {
                            temaMasVotado = materiaActual;
                        }
                    }
                    obtenerTema();
                }else{
                    obtenerSubtemaMasVotado();
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
                    if(subtemaMasVotado.equals(tema.getNombre())) {
                        obtenerSubtemaMasVotado();
                        return;
                    }
                }
                obtenerSubtemaMasVotado();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void obtenerSubtemaMasVotado(){
        votos = new ArrayList<>();
        rootReference.child("votoSubtema").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    votos.add(snapshot.getValue(Votos.class));
                }
                if(votos.size()>0) {
                    contadorMateriaActual = 0;
                    contadorMateriaPasada = 0;
                    for (int i = 0; i < votos.size(); i++) {
                        materiaActual = votos.get(i).getNombre();
                        for (int j = 0; j < votos.size(); j++) {
                            if (votos.get(j).getNombre().equals(materiaActual)) {
                                contadorMateriaActual++;
                            }
                        }
                        if (contadorMateriaActual > contadorMateriaPasada) {
                            subtemaMasVotado = materiaActual;
                        }
                    }
                    obtenerSubtema();
                }else{
                    insertarMateria();
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
                    if(subtemaMasVotado.equals(subtema.getNombre())){
                        insertarMateria();
                        return;
                    }
                }
                insertarMateria();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void insertarDatos(){
        request = Volley.newRequestQueue(actividad);
        progreso = new ProgressDialog(actividad);
        progreso.setMessage("Espere un momento por favor...");
        progreso.show();
    }

    public void insertarMateria(){
        if(materiaMasVotada!=null){
             url = "https://readandwatch.herokuapp.com/php/insertarMateria.php?" +
                    "nombre="+materiaMasVotada+
                    "&rutaImagen= ";
            url=url.replace(" ", "%20");
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    insertarTema();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    insertarTema();
                }
            });
            request.add(jsonObjectRequest);
        }else{
            insertarTema();
        }
    }

    public void insertarTema(){
        if(tema!=null){
            url = "https://readandwatch.herokuapp.com/php/insertarTemaVotaciones.php?" +
                    "materia="+tema.getMateria()+
                    "&semestre="+tema.getSemestre()+
                    "&idUsuario="+tema.getIdUsuario()+
                    "&nombre="+tema.getNombre();
            url=url.replace(" ", "%20");
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    insertarSubtema();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    insertarSubtema();
                }
            });
            request.add(jsonObjectRequest);
        }else{
            insertarSubtema();
        }
    }

    public void insertarSubtema(){
        if(subtema!=null){
            url = "https://readandwatch.herokuapp.com/php/insertarSubtemaVotaciones.php?" +
                    "tema="+subtema.getTema()+
                    "&idUsuario="+subtema.getIdUsuario()+
                    "&nombre="+subtema.getNombre();
            url=url.replace(" ", "%20");
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    limpiar();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    limpiar();
                }
            });
            request.add(jsonObjectRequest);
        }else{
            limpiar();
        }
    }

    public void limpiar(){
        DatabaseReference update;

        update = rootReference.child("materia");
        update.setValue(null);
        update = rootReference.child("tema");
        update.setValue(null);
        update = rootReference.child("subtema");
        update.setValue(null);
        update = rootReference.child("votoMateria");
        update.setValue(null);
        update = rootReference.child("votoTema");
        update.setValue(null);
        update = rootReference.child("votoSubtema");
        update.setValue(null);
        progreso.hide();
    }


}
