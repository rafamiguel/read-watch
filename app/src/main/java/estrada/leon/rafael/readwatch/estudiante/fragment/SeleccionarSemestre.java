package estrada.leon.rafael.readwatch.estudiante.fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import estrada.leon.rafael.readwatch.estudiante.interfaces.iComunicacionFragments;
import estrada.leon.rafael.readwatch.R;

public class SeleccionarSemestre extends Fragment implements View.OnClickListener{
TextView lbl1,lbl2,lbl3,lbl4,lbl5,lbl6;
    iComunicacionFragments interfaceFragments;
    View vista;
    Activity actividad;
    private OnFragmentInteractionListener mListener;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public SeleccionarSemestre(){}

    public static SeleccionarSemestre newInstance(String param1, String param2) {
        SeleccionarSemestre fragment = new SeleccionarSemestre();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

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
        lbl1.setOnClickListener(this);
        lbl2.setOnClickListener(this);
        lbl3.setOnClickListener(this);
        lbl4.setOnClickListener(this);
        lbl5.setOnClickListener(this);
        lbl6.setOnClickListener(this);
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

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        interfaceFragments.seleccionarTema();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
