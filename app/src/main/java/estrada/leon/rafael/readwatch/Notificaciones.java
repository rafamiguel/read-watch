package estrada.leon.rafael.readwatch;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import estrada.leon.rafael.readwatch.general.activity.MainActivity;

public class Notificaciones extends AppCompatActivity {
    NotificationCompat.Builder notificacion, notificacion2;
    Button btnEmpezar, btnTerminar;
    private static final int idUnica =7777;
    private static final int idUnica2 =111;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificaciones);

        btnEmpezar = findViewById(R.id.btnComenzar);
        notificacion2 = new NotificationCompat.Builder(this);
        notificacion2.setAutoCancel(true);

        btnTerminar = findViewById(R.id.btnFinalizar);
        notificacion = new NotificationCompat.Builder(this);
        notificacion.setAutoCancel(true);

        btnEmpezar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                notificacion.setSmallIcon(R.drawable.camara);
                notificacion.setTicker("Nueva notificación");
                notificacion.setWhen(System.currentTimeMillis());
                notificacion.setContentTitle("R&W");
                notificacion.setContentText("¡Las votaciones han comenzado! Puedes votar o proponer un tema");
                Intent intent = new Intent(Notificaciones.this, MainActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(Notificaciones.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                notificacion.setContentIntent(pendingIntent);


                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(idUnica, notificacion.build());
            }

        });

        btnTerminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notificacion2.setSmallIcon(R.drawable.camara);
                notificacion2.setTicker("Nueva notificación");
                notificacion2.setPriority(Notification.PRIORITY_HIGH);
                notificacion2.setWhen(System.currentTimeMillis());
                notificacion2.setContentTitle("R&W");
                notificacion2.setContentText("¡Las votaciones están por terminar! Puedes votar o\n" +
                        "proponer un tema");
                Intent intent = new Intent(Notificaciones.this, MainActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(Notificaciones.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                notificacion2.setContentIntent(pendingIntent);


                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(idUnica2, notificacion2.build());
            }
        });
    }
}
