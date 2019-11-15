package estrada.leon.rafael.readwatch.general.funciones;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

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

import estrada.leon.rafael.readwatch.R;
import estrada.leon.rafael.readwatch.estudiante.fragment.ElegirMateria;
import estrada.leon.rafael.readwatch.estudiante.menu.MenuEstudiante;
import estrada.leon.rafael.readwatch.estudiante.pojo.Materias;
import estrada.leon.rafael.readwatch.estudiante.pojo.Subtemas;
import estrada.leon.rafael.readwatch.estudiante.pojo.Temas;
import estrada.leon.rafael.readwatch.estudiante.pojo.Votos;
import estrada.leon.rafael.readwatch.general.pojo.Sesion;

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

    private final static String CHANNEL_ID = "NOTIFICACION";
    private final static int NOTIFICACION_ID = 0;

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
                    if(materiaMasVotada==null){
                        materiaMasVotada = materiaActual;
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
                    if(temaMasVotado==null){
                        temaMasVotado = materiaActual;
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
                        if(temaMasVotado.equals(tema.getNombre())) {
                        break;
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
                    if(subtemaMasVotado==null){
                        subtemaMasVotado = materiaActual;
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
                        break;
                    }
                }
                insertarDatos();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                insertarDatos();
            }
        });
    }

    public void insertarDatos(){
        request = Volley.newRequestQueue(actividad);
        progreso = new ProgressDialog(actividad);
        progreso.setMessage("Espere un momento por favor...");
        progreso.show();
        insertarMateria();
    }

    public void insertarMateria(){
        if(materiaMasVotada!=null){
             url = "https://readandwatch.herokuapp.com/php/insertarMateriaVotaciones.php?" +
                    "nombre="+materiaMasVotada+
                    "&rutaImagen= "+
                    "&idUsuario="+materia.getIdUsuario();
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
        if(materia.getIdUsuario() == Sesion.getSesion().getId()){
            notificacion();
        }
        else if(tema.getIdUsuario() == Sesion.getSesion().getId()){
            notificacion();
        }
        else if(subtema.getIdUsuario() == Sesion.getSesion().getId()){
            notificacion();
        }
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
        Fragment fragment =new ElegirMateria();
        ((MenuEstudiante)actividad).getSupportFragmentManager().beginTransaction().replace(R.id.layoutPrincipal,fragment).commit();
        progreso.hide();
    }

    public void notificacion(){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "Notificacion";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID,name,
                    NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager)actividad.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        Intent intent = new Intent(actividad, MenuEstudiante.class);
        PendingIntent contentIntent = PendingIntent.getActivity(actividad, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(actividad,CHANNEL_ID);
        builder.setSmallIcon(R.drawable.logo8);
        builder.setContentTitle("Notificacion Android");
        builder.setContentText("Titulo");
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setLights(Color.GREEN,1000,1000);
        builder.setVibrate(new long[]{1000,1000,1000,1000});
        builder.setDefaults(Notification.DEFAULT_SOUND);
        builder.setContentIntent(contentIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(actividad);
        notificationManagerCompat.notify(NOTIFICACION_ID,builder.build());
    }


}
