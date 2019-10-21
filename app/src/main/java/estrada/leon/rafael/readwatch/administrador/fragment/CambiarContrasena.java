package estrada.leon.rafael.readwatch.administrador.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

import estrada.leon.rafael.readwatch.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CambiarContrasena.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CambiarContrasena#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CambiarContrasena extends Fragment {
    ProgressDialog progreso;
    JsonObjectRequest jsonObjectRequest;
    RequestQueue request;
    String contraseña;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CambiarContrasena() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CambiarContrasena.
     */
    // TODO: Rename and change types and number of parameters
    public static CambiarContrasena newInstance(String param1, String param2) {
        CambiarContrasena fragment = new CambiarContrasena();
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
        View vista;
        final EditText txtContrasenaActual, txtContrasenaNueva;
        Button btnRegistrar;


        vista=inflater.inflate(R.layout.fragment_cambiar_contrasena, container, false);
        request= Volley.newRequestQueue(getContext());

        txtContrasenaActual = vista.findViewById(R.id.txtContrasenaActual);
        txtContrasenaNueva = vista.findViewById(R.id.txtContrasenaNueva);
        btnRegistrar = vista.findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               verificarContraseña(txtContrasenaActual.getText().toString(), txtContrasenaNueva.getText().toString());
            }
        });

        return vista;
    }

    private void verificarContraseña(final String antigua, final String nueva) {

        SharedPreferences preferences = getContext().getSharedPreferences("Datos usuario", Context.MODE_PRIVATE);
        int idUsuario = preferences.getInt("idUsuario", 0);

        String url;
        progreso = new ProgressDialog(getContext());
        progreso.setMessage("Modificando...");
        progreso.show();
        url = "https://readandwatch.herokuapp.com/php/buscarContrasena.php?" +
                "idUsuario="+idUsuario;
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray json;
                progreso.hide();
                JSONObject jsonObject=null;
                json = response.optJSONArray("usuario");
                try {
                    for(int i=0;i<json.length();i++){
                        jsonObject=json.getJSONObject(i);
                        contraseña = jsonObject.optString("contrasena");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                compararContrasena(contraseña,antigua,nueva);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progreso.hide();
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        request.add(jsonObjectRequest);

    }

    private void compararContrasena(String contraseña, String antigua, String nueva) {
        SharedPreferences preferences = getContext().getSharedPreferences("Datos usuario", Context.MODE_PRIVATE);
        int idUsuario = preferences.getInt("idUsuario", 0);
        if(contraseña.equals(antigua)){
            String url;
            url = "https://readandwatch.herokuapp.com/php/updateContrasena.php?" +
                    "contrasena="+nueva+"&idUsuario="+idUsuario;
            url=url.replace(" ", "%20");
            jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                   Toast.makeText(getContext(), "Se cambio correctamente la contraseña", Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            request.add(jsonObjectRequest);
        }
        else {
            Toast.makeText(getContext(), "La contraseña actual no es correcta ", Toast.LENGTH_SHORT).show();
        }
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
