package estrada.leon.rafael.readwatch;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Elige_materia extends Fragment {
    Button btnMenu, btnIngles, btnMatematicas, btnEspanol;
    TextView txtTitulo, txtBarrainferior, txtBarrasuperior;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.elige_materia, container, false);

    }
}
