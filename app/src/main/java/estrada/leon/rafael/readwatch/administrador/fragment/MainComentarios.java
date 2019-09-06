package estrada.leon.rafael.readwatch.administrador.fragment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import estrada.leon.rafael.readwatch.R;
import estrada.leon.rafael.readwatch.administrador.adapter.AdapterComentario;
import estrada.leon.rafael.readwatch.administrador.pojo.PojoComentario;

public class MainComentarios extends AppCompatActivity {
    Button btnComentario;
    RecyclerView recycler;
    List<PojoComentario> list= new ArrayList<>();
    AdapterComentario adapterComentario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_comentarios_ad);
        recycler = findViewById(R.id.recyclerId);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(linearLayoutManager);
        cargarDatos();
        adapterComentario = new AdapterComentario(this, list);
        recycler.setAdapter(adapterComentario);
    }

    public void cargarDatos(){
        list.add(new PojoComentario("Juanito P", "Muy mal video la verdad me gustaria que todo fuera mejor explicado"));
        list.add(new PojoComentario("Andrea J", "Gracias, me sirvio"));
        list.add(new PojoComentario("Justin H.", "Like"));
    }

}
