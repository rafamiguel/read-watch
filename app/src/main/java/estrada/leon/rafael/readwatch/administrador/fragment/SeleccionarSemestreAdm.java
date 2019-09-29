package estrada.leon.rafael.readwatch.administrador.fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import estrada.leon.rafael.readwatch.R;
import estrada.leon.rafael.readwatch.administrador.interfaces.iComunicacionFragmentsAdm;


public class SeleccionarSemestreAdm extends Fragment implements View.OnClickListener {
    private iComunicacionFragmentsAdm interfaceFragmentAdm;
    private OnFragmentInteractionListener mListener;

    public SeleccionarSemestreAdm() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView lbl1,lbl2,lbl3,lbl4,lbl5,lbl6;

        View vista;
        vista= inflater.inflate(R.layout.fragment_seleccionar_semestre_adm, container, false);
        lbl1=vista.findViewById(R.id.lbl1);
        lbl2=vista.findViewById(R.id.lbl2);
        lbl3=vista.findViewById(R.id.lbl3);
        lbl4=vista.findViewById(R.id.lbl4);
        lbl5=vista.findViewById(R.id.lbl5);
        lbl6=vista.findViewById(R.id.lbl6);
        lbl1.setOnClickListener(this);
        lbl2.setOnClickListener(this);
        lbl3.setOnClickListener(this);
        lbl4.setOnClickListener(this);
        lbl5.setOnClickListener(this);
        lbl6.setOnClickListener(this);
        return vista;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        Activity actividad;
        super.onAttach(context);
        if (context instanceof Activity) {
            actividad= (Activity) context;
            interfaceFragmentAdm=(iComunicacionFragmentsAdm)actividad;
        }
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.lbl1:
                interfaceFragmentAdm.seleccionarTema(1);

                break;
            case R.id.lbl2:
                interfaceFragmentAdm.seleccionarTema(2);
                break;
            case R.id.lbl3:
                interfaceFragmentAdm.seleccionarTema(3);
                break;
            case R.id.lbl4:
                interfaceFragmentAdm.seleccionarTema(4);
                break;
            case R.id.lbl5:
                interfaceFragmentAdm.seleccionarTema(5);
                break;
            case R.id.lbl6:
                interfaceFragmentAdm.seleccionarTema(6);
                break;
        }

    }

    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
}
