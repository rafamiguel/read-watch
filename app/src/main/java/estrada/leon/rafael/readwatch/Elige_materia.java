package estrada.leon.rafael.readwatch;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Elige_materia extends Fragment implements View.OnClickListener {
    Button btnMenu, btnIngles, btnMatematicas, btnEspanol;
    TextView txtTitulo, txtBarrainferior, txtBarrasuperior;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        btnIngles=getView().findViewById(R.id.btnIngles);
        btnMatematicas=getView().findViewById(R.id.btnMatematicas);
        btnEspanol=getView().findViewById(R.id.btnEspanol);
        return inflater.inflate(R.layout.elige_materia, container, false);

    }

    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager= getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.layoutPrincipal, new ElegirTema()).commit();
    }
}
