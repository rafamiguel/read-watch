package estrada.leon.rafael.readwatch.estudiante.fragment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import estrada.leon.rafael.readwatch.R;
import estrada.leon.rafael.readwatch.estudiante.adapter.AdapterComentario;
import estrada.leon.rafael.readwatch.estudiante.pojo.PojoComentario;

public class MainComentario extends AppCompatActivity {
    Button btnComentario;
    RecyclerView recycler;
    List<PojoComentario> list= new ArrayList<>();
    AdapterComentario adapterComentario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_comentario);
        recycler = findViewById(R.id.recyclerId);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(new LinearLayoutManager
                (this/*SI SE DETIENE LA APLICACION Y SE CAMBIÓ ESTO A UN FRAGMENT O DIALOG (PORQUE
                TENIA QUE SER UN DIALOG) CAMBIAR EL THIS POR GETCONTEXT.*/
                        ,LinearLayoutManager.VERTICAL,false));
        cargarDatos();
        adapterComentario = new AdapterComentario(this, list);
        recycler.setAdapter(adapterComentario);
    }

    public void cargarDatos(){
        list.add(new PojoComentario("Juanito P", "Muy mal video"));
        list.add(new PojoComentario("Andrea J", "Gracias, me sirvio"));
        list.add(new PojoComentario("Justin H.", "Like"));
    }

}
