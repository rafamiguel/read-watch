package estrada.leon.rafael.readwatch.estudiante.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.krishna.fileloader.FileLoader;
import com.krishna.fileloader.listener.FileRequestListener;
import com.krishna.fileloader.pojo.FileResponse;
import com.krishna.fileloader.request.FileLoadRequest;
import com.shockwave.pdfium.PdfDocument;
import com.shockwave.pdfium.PdfiumCore;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import estrada.leon.rafael.readwatch.R;
import estrada.leon.rafael.readwatch.estudiante.adapter.FavoritosAdapter;
import estrada.leon.rafael.readwatch.estudiante.interfaces.Item;
import estrada.leon.rafael.readwatch.estudiante.interfaces.iComunicacionFragments;
import estrada.leon.rafael.readwatch.estudiante.pojo.Documentos;
import estrada.leon.rafael.readwatch.estudiante.pojo.Videos;
import estrada.leon.rafael.readwatch.general.pojo.Sesion;

public class Favoritos extends Fragment implements FavoritosAdapter.OnFavoritosListener {
    private List<Item> list = new ArrayList<>();
    private iComunicacionFragments interfaceFragments;
    ProgressDialog progreso;
    JsonObjectRequest jsonObjectRequest;
    RequestQueue request;
    RecyclerView recyclerFavoritos;
    FavoritosAdapter favoritosAdapter;
    Context contexto;
    int []idUsuarioVidDocFav;
    int contador;
    int contadorDocumentos;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public Favoritos() {
    }

    private void cargarDatosVid() {
        list = new ArrayList<>();
        SharedPreferences preferences = contexto.getSharedPreferences("Datos usuario", Context.MODE_PRIVATE);
        int idUsuario = preferences.getInt("idUsuario", 0);
        String url;
        progreso = new ProgressDialog(contexto);
        progreso.setMessage("Cargando...");
        progreso.show();
        url = "https://readandwatch.herokuapp.com/php/videosFav.php?" +
                "idUsuario="+idUsuario;
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progreso.hide();
                JSONArray json = response.optJSONArray("usuario");
                JSONObject jsonObject=null;
                int idUsuario, idVidDoc;
                String descripcion, miniatura,ruta;
                if (json.length()>=1) {
                    for (int i = 0; i < json.length(); i++) {
                        try {
                            jsonObject = json.getJSONObject(i);
                            idUsuario = jsonObject.optInt("idUsuario");
                            descripcion = jsonObject.optString("descripcion");
                            miniatura = jsonObject.optString("rutaImagen");
                            idVidDoc = jsonObject.optInt("idVidDoc");
                            ruta = jsonObject.optString("ruta");
                            list.add(new Videos(String.valueOf(idUsuario), descripcion, miniatura, idUsuario, idVidDoc,ruta));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    cargarDatos();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progreso.hide();
                cargarDatos();
                //Toast.makeText(contexto, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        request.add(jsonObjectRequest);

    }

    public void cargarDatos(){
        SharedPreferences preferences = contexto.getSharedPreferences("Datos usuario", Context.MODE_PRIVATE);
        int idUsuario = preferences.getInt("idUsuario", 0);
        String url;
        url = "https://readandwatch.herokuapp.com/php/documentosFav.php?" +
                "idUsuario="+idUsuario;
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progreso.hide();
                Toast.makeText(contexto, "Tus videos y documentos favoritos", Toast.LENGTH_SHORT).show();
                JSONArray json = response.optJSONArray("usuario");
                JSONObject jsonObject=null;
                int idUsuario, idVidDoc;
                String descripcion, miniatura;
                contadorDocumentos = 0;
                if(json.length()>0) {
                    for (int i = 0; i < json.length(); i++) {
                        try {
                            jsonObject = json.getJSONObject(i);
                            idUsuario = jsonObject.optInt("idUsuario");
                            descripcion = jsonObject.optString("descripcion");
                            miniatura = jsonObject.optString("rutaImagen");
                            idVidDoc = jsonObject.optInt("idVidDoc");
                            list.add(new Documentos(String.valueOf(idUsuario), descripcion, miniatura, idUsuario, idVidDoc));
                            contadorDocumentos++;
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    cargarMiniaturasDoc();
                }
                favoritosAdapter=new FavoritosAdapter(contexto,list,Favoritos.this, idUsuarioVidDocFav);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progreso.hide();
                favoritosAdapter=new FavoritosAdapter(contexto,list,Favoritos.this, idUsuarioVidDocFav);
                recyclerFavoritos.setAdapter(favoritosAdapter);
                //Toast.makeText(contexto, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        request.add(jsonObjectRequest);
    }

    private void buscarVideosFav() {
        SharedPreferences preferences = contexto.getSharedPreferences("Datos usuario", Context.MODE_PRIVATE);
        int idUsuario = preferences.getInt("idUsuario", 0);
        String url = "https://readandwatch.herokuapp.com/php/buscarVideoFav.php?" +
                "idUsuario="+idUsuario;
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray json = response.optJSONArray("usuario");
                JSONObject jsonObject=null;
                if(json.length()<1){
                    idUsuarioVidDocFav = new int[1];
                    idUsuarioVidDocFav[0]=0;
                }else {
                    idUsuarioVidDocFav = new int[json.length()];
                }

                for(int i=0;i<json.length();i++) {
                    try {
                        jsonObject = json.getJSONObject(i);
                        idUsuarioVidDocFav[i] = jsonObject.getInt("idVidDoc");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //Toast.makeText(contexto, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        request.add(jsonObjectRequest);
    }

    public static Favoritos newInstance(String param1, String param2) {
        Favoritos fragment = new Favoritos();
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


        View vista;
        vista= inflater.inflate(R.layout.fragment_favoritos, container, false);
        recyclerFavoritos=vista.findViewById(R.id.recyclerFavoritos);
        recyclerFavoritos.setLayoutManager(new LinearLayoutManager(contexto,LinearLayoutManager.VERTICAL,false));
        request= Volley.newRequestQueue(contexto);
        buscarVideosFav();
        cargarDatosVid();
        return vista;
    }


    private void cargarMiniaturasDoc(){
        contador=0;
        for (int i=0;i<list.size();i++) {
            if (list.get(i).getViewType() == 1) {
                FileLoader.with(contexto).load("https://readandwatch.000webhostapp.com/archivos/" + ((Documentos)list.get(i)).getIdVidDoc() + ".pdf").fromDirectory("PDFFiles", FileLoader.DIR_EXTERNAL_PUBLIC).asFile(new FileRequestListener<File>() {
                    @Override
                    public void onLoad(FileLoadRequest request, FileResponse<File> response) {
                        File pdf = response.getBody();

                        FileInputStream fileInputStream = null;
                        byte[] bytesArray = null;
                        try {
                            bytesArray = new byte[(int) pdf.length()];
                        } catch (Exception e) {
                            Toast.makeText(contexto, "El archivo es demasiado grande.", Toast.LENGTH_SHORT).show();
                        }
                        //read file into bytes[]
                        try {
                            fileInputStream = new FileInputStream(pdf);
                            fileInputStream.read(bytesArray);
                            int pageNumber = 0;
                            PdfiumCore pdfiumCore = new PdfiumCore(contexto);
                            PdfDocument pdfDocument = null;
                            pdfDocument = pdfiumCore.newDocument(bytesArray);
                            pdfiumCore.openPage(pdfDocument, pageNumber);
                            //int width = pdfiumCore.getPageWidthPoint(pdfDocument, pageNumber);
                            //int height = pdfiumCore.getPageHeightPoint(pdfDocument, pageNumber);
                            int width = 300;
                            int height = 300;
                            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                            pdfiumCore.renderPageBitmap(pdfDocument, bmp, pageNumber, 0, 0, width, height);
                            pdfiumCore.closeDocument(pdfDocument); // important!
                            int index = pdf.getName().lastIndexOf('/');
                            String fileName = pdf.getName().substring(index + 1, pdf.getName().length() - 4);
                            for (int j = 0; j < list.size(); j++) {
                                if(list.get(j).getViewType()==1) {
                                    if (Integer.toString(((Documentos)list.get(j)).getIdVidDoc()).equals(fileName)) {
                                        ((Documentos)list.get(j)).setImagen(bmp);
                                        break;
                                    }
                                }
                            }
                            contador++;
                            if (contador == contadorDocumentos) {
                                recyclerFavoritos.setAdapter(favoritosAdapter);
                                progreso.hide();
                            }
                        } catch (IOException e) {
                            progreso.hide();
                            Toast.makeText(contexto, "Error al descargar los archivos.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(FileLoadRequest request, Throwable t) {
                        //Toast.makeText(contexto, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        Activity actividad;
        contexto = context;
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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onFavoritoClick(int position, List<Item> lista, Toast toast) {
        interfaceFragments.onClickDocFavHolder(toast);
    }

    @Override
    public void agregarFavoritosVid(int position, List<Item> list) {
        SharedPreferences preferences = contexto.getSharedPreferences("Datos usuario", Context.MODE_PRIVATE);
        int idUsuario = preferences.getInt("idUsuario", 0);
        int idVidDoc = ((Videos) list.get(position)).getIdVidDoc();
        verificarExistencia(idUsuario, idVidDoc);
    }

    @Override
    public void agregarFavoritosDoc(int position, List<Item> list) {
        SharedPreferences preferences = contexto.getSharedPreferences("Datos usuario", Context.MODE_PRIVATE);
        int idUsuario = preferences.getInt("idUsuario", 0);
        int idVidDoc = ((Documentos) list.get(position)).getIdVidDoc();
        verificarExistencia(idUsuario, idVidDoc);
    }

    @Override
    public void perfilClick(int position, List<Item> list) {
        ((iComunicacionFragments)interfaceFragments).onClickVidPerfil(((Documentos)list.get(position)).getIdUsuario());
    }

    @Override
    public void perfilClickVid(int adapterPosition, List<Item> list) {
        ((iComunicacionFragments)interfaceFragments).onClickVidPerfil(((Videos)list.get(adapterPosition)).getIdUsuario());
    }

    @Override
    public void leerDocumento(int id) {
        interfaceFragments.leerDocumento(id);
    }

    private void verificarExistencia(final int idUsuario, final int idVidDoc) {
        String url;
        url = "https://readandwatch.herokuapp.com/php/existenciaFavorito.php?" +
                "idUsuario="+idUsuario+"&idVidDoc="+idVidDoc;
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                int idFavorito;
                JSONArray json;
                JSONObject jsonObject=null;
                json = response.optJSONArray("usuario");
                for(int i=0;i<json.length();i++) {
                    try {
                        jsonObject = json.getJSONObject(i);
                        idFavorito = jsonObject.getInt("idFavorito");

                        if (idFavorito==0){
                            subirFavoritos(idUsuario, idVidDoc);

                        }
                        else {
                            deleteFavoritos(idFavorito);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                progreso.hide();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progreso.hide();
                Toast.makeText(contexto, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        request.add(jsonObjectRequest);
    }

    private void subirFavoritos(int idUsuario, int idVidDoc) {
        String url;
        progreso = new ProgressDialog(contexto);
        progreso.setMessage("Cargando...");
        progreso.show();
        url = "https://readandwatch.herokuapp.com/php/insertFavorito.php?" +
                "idUsuario="+idUsuario+"&idVidDoc="+idVidDoc;
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progreso.hide();
                Toast.makeText(contexto, "Se agrego a favoritos", Toast.LENGTH_SHORT).show();
                FragmentManager fragmentManager= getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.layoutPrincipal,new Favoritos()).addToBackStack(null).commit();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progreso.hide();
                Toast.makeText(contexto, "No se pudo agregar a favoritos.", Toast.LENGTH_SHORT).show();
            }
        });
        request.add(jsonObjectRequest);
    }

    private void deleteFavoritos(int idFavorito) {
        String url;
        progreso = new ProgressDialog(contexto);
        progreso.setMessage("Cargando...");
        progreso.show();
        url = "https://readandwatch.herokuapp.com/php/eliminarFavorito.php?" +
                "idFavorito="+idFavorito;
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progreso.hide();
                Toast.makeText(contexto, "Se elimino de favoritos", Toast.LENGTH_SHORT).show();
                FragmentManager fragmentManager= getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.layoutPrincipal,new Favoritos()).addToBackStack(null).commit();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progreso.hide();
                Toast.makeText(contexto, "No se pudo eliminar", Toast.LENGTH_SHORT).show();
            }
        });
        request.add(jsonObjectRequest);
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
