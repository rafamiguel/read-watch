package estrada.leon.rafael.readwatch;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SeleccionarSemestre extends Fragment {
TextView lbl1,lbl2,lbl3,lbl4,lbl5,lbl6;
    iComunicacionFragments interfaceFragments;
    View vista;
    Activity actividad;
    private OnFragmentInteractionListener mListener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista= inflater.inflate(R.layout.fragment_seleccionar_semestre, container, false);
        lbl1=vista.findViewById(R.id.lbl1);
        lbl2=vista.findViewById(R.id.lbl2);
        lbl3=vista.findViewById(R.id.lbl3);
        lbl4=vista.findViewById(R.id.lbl4);
        lbl5=vista.findViewById(R.id.lbl5);
        lbl6=vista.findViewById(R.id.lbl6);
        lbl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfaceFragments.seleccionarTema();
            }
        });
        return vista;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            actividad= (Activity) context;
            interfaceFragments=(iComunicacionFragments)actividad;
        }
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
