package estrada.leon.rafael.readwatch;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class SeleccionarSemestre extends Fragment implements View.OnClickListener{
    TextView lbl1,lbl2,lbl3,lbl4,lbl5,lbl6;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        lbl1=getView().findViewById(R.id.lbl1);
        lbl2=getView().findViewById(R.id.lbl2);
        lbl3=getView().findViewById(R.id.lbl3);
        lbl4=getView().findViewById(R.id.lbl4);
        lbl5=getView().findViewById(R.id.lbl5);
        lbl6=getView().findViewById(R.id.lbl6);

        return inflater.inflate(R.layout.fragment_seleccionar_semestre, container, false);
    }

    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager= getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.layoutPrincipal, new ElegirTema()).commit();
    }
}
