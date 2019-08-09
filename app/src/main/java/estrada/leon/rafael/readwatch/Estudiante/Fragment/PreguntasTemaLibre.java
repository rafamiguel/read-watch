package estrada.leon.rafael.readwatch.Estudiante.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import estrada.leon.rafael.readwatch.Estudiante.Adapter.TemaLibreAdapter;
import estrada.leon.rafael.readwatch.Estudiante.POJO.TemaLibre;
import estrada.leon.rafael.readwatch.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PreguntasTemaLibre.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PreguntasTemaLibre#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PreguntasTemaLibre extends Fragment {
    RecyclerView recyclerTemas;
    View vista;
    TemaLibreAdapter adapter;
    List<TemaLibre> temaLibreList = new ArrayList<>();
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public PreguntasTemaLibre() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PreguntasTemaLibre.
     */
    // TODO: Rename and change types and number of parameters
    public static PreguntasTemaLibre newInstance(String param1, String param2) {
        PreguntasTemaLibre fragment = new PreguntasTemaLibre();
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
        vista=inflater.inflate(R.layout.fragment_preguntas_tema_libre, container, false);
        recyclerTemas =  vista.findViewById(R.id.recyclerTemas);
        recyclerTemas.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        temaLibreList = new ArrayList<>();
        temaLibreList.add(new TemaLibre("Me urge!!!", "Gallina o huevo"));
        temaLibreList.add(new TemaLibre("Es tarea", "Bueno o malo??"));
        adapter = new TemaLibreAdapter(getContext(),temaLibreList);
        return vista;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
