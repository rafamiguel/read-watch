package estrada.leon.rafael.readwatch.administrador.fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import estrada.leon.rafael.readwatch.R;
import estrada.leon.rafael.readwatch.administrador.adapter.UsuarioInactivoAdapter;
import estrada.leon.rafael.readwatch.administrador.interfaces.iComunicacionFragmentsAdm;
import estrada.leon.rafael.readwatch.administrador.pojo.InactivoAdm;
import estrada.leon.rafael.readwatch.estudiante.adapter.HistorialAdapter;
import estrada.leon.rafael.readwatch.general.pojo.Sesion;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UsuariosInactivos.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UsuariosInactivos#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UsuariosInactivos extends Fragment implements UsuarioInactivoAdapter.OnInactivoListener {
    View vista;
    List<InactivoAdm> list;
    Activity actividad;
    JsonObjectRequest jsonObjectRequest;
    RecyclerView recyclerInactivos;
    RequestQueue request;
    private iComunicacionFragmentsAdm interfaceFragments;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public UsuariosInactivos() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UsuariosInactivos.
     */
    // TODO: Rename and change types and number of parameters
    public static UsuariosInactivos newInstance(String param1, String param2) {
        UsuariosInactivos fragment = new UsuariosInactivos();
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

        vista = inflater.inflate(R.layout.fragment_usuarios_inactivos, container, false);
        recyclerInactivos=vista.findViewById(R.id.recyclerInactivos);
        request = Volley.newRequestQueue(getContext());
        recyclerInactivos.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));


        hora();
        return vista;
    }

    private void hora() {
        final Calendar c = Calendar.getInstance();
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String datetime = dateformat.format(c.getTime());

        String url;
        int idUsuario = Sesion.getSesion().getId();
        url = "https://readandwatch.herokuapp.com/php/buscarTiempoUsuario.php?" +
                "idUsuario="+idUsuario;
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String nombre="", rutaFoto="", ultimoInicio="";
                JSONArray json;
                JSONObject jsonObject=null;
                json = response.optJSONArray("usuario");
                for(int i=0;i<json.length();i++) {
                    try {
                        jsonObject = json.getJSONObject(i);
                        nombre= jsonObject.getString("nombre");
                        rutaFoto = jsonObject.getString("rutaFoto");
                        ultimoInicio = jsonObject.getString("ultimoInicio");

                        Calendar cal = Calendar.getInstance();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
                        try {
                            cal.setTime(sdf.parse(ultimoInicio));// all done
                            cal.add(Calendar.MONTH,12);
                            if(cal.before(c)){
                                list=new ArrayList<>();
                                list.add(new InactivoAdm(nombre, rutaFoto));
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                UsuarioInactivoAdapter h = new UsuarioInactivoAdapter(getContext(),list, UsuariosInactivos.this);
                recyclerInactivos.setAdapter(h);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        request.add(jsonObjectRequest);

    }

    private void pasoAno() {
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
        if (context instanceof Activity) {
            actividad= (Activity) context;
            interfaceFragments=(iComunicacionFragmentsAdm) actividad;
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
    public void OnInactivoClick(int posicion, List<InactivoAdm> list, Toast toast) {
        interfaceFragments.onClickInactivo(toast);
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
