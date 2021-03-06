package estrada.leon.rafael.readwatch.estudiante.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.BaseMultiplePermissionsListener;
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

import estrada.leon.rafael.readwatch.estudiante.adapter.DocumentosAdapter;
import estrada.leon.rafael.readwatch.estudiante.menu.MenuEstudiante;
import estrada.leon.rafael.readwatch.estudiante.pojo.Documentos;
import estrada.leon.rafael.readwatch.estudiante.interfaces.iComunicacionFragments;
import estrada.leon.rafael.readwatch.R;


public class ElegirDocumento extends Fragment implements DocumentosAdapter.OnDocumentosListener,
        View.OnClickListener, Response.Listener<JSONObject>, Response.ErrorListener{

    private iComunicacionFragments interfaceFragments;
    private List<Documentos> documentos;

    ProgressDialog progreso;
    JsonObjectRequest jsonObjectRequest;
    int idTema;
    RequestQueue request;
    RecyclerView recyclerDocumentos;
    DocumentosAdapter adapter;

    int []idUsuarioVidDoc;
    int []idUsuarioVidDocFav;
    int contador=0;
    
    Context contexto;


    private OnFragmentInteractionListener mListener;

    public ElegirDocumento() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Dexter.withActivity(getActivity()).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE).withListener(
                        new BaseMultiplePermissionsListener(){
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                super.onPermissionsChecked(report);
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                                super.onPermissionRationaleShouldBeShown(permissions, token);
                            }
                        }
        ).check();
        TextView lblElegirDocumento;
        Button btnVideo,btnDocumento,btnSubirDocumento;
        View vista;
        documentos=new ArrayList<>();
        vista=inflater.inflate(R.layout.fragment_elegir_documento, container, false);
        recyclerDocumentos=vista.findViewById(R.id.recyclerDocumentos);
        btnVideo=vista.findViewById(R.id.btnVideo);
        btnDocumento=vista.findViewById(R.id.btnDocumento);
        btnSubirDocumento=vista.findViewById(R.id.btnSubirDocumento);

        btnVideo.setOnClickListener(this);
        btnDocumento.setOnClickListener(this);
        btnSubirDocumento.setOnClickListener(this);

        recyclerDocumentos.setLayoutManager(new LinearLayoutManager(contexto,
                LinearLayoutManager.VERTICAL,false));

        SharedPreferences preferences = contexto.getSharedPreferences("Tema", Context.MODE_PRIVATE);
        idTema = preferences.getInt("tema", 0);

        request= Volley.newRequestQueue(contexto);
        buscarDocFav();
        buscarDoc();
        return vista;
    }

    private void buscarDocFav() {
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

                for(int i=0;i<json.length();i++){
                    try {
                        jsonObject=json.getJSONObject(i);
                        idUsuarioVidDocFav[i]= jsonObject.getInt("idVidDoc");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

               // Toast.makeText(contexto, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        request.add(jsonObjectRequest);
    }

    @Override
    public void onAttach(Context context) {
        Activity actividad;
        super.onAttach(context);
        contexto = context;
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
    public void onDocumentosClick(int position, List<Documentos> documentosList, Toast toast) {
        interfaceFragments.onClickDocumentosHolder(toast);
    }

    @Override
    public void reportarClick(int position, List<Documentos> documentosList) {
        interfaceFragments.onClickReportarVidDoc(documentosList.get(position).getIdVidDoc());
    }

    @Override
    public void perfilClick(int position, List<Documentos> documentosList) {
        ((iComunicacionFragments)interfaceFragments).onClickVidPerfil(documentosList.get(position).getIdUsuario());
    }

    @Override
    public void comentarioClick(int position, List<Documentos> list) {
        SharedPreferences preferences = contexto.getSharedPreferences("Datos usuario", Context.MODE_PRIVATE);
        int idUsuario = preferences.getInt("idUsuario", 0);
        int idVidDoc =list.get(position).getIdVidDoc();
        interfaceFragments.onClickComentario(idUsuario,idVidDoc,0);
    }

    @Override
    public void opcionClick(int position, List<Documentos> list) {
        SharedPreferences preferences = contexto.getSharedPreferences("Datos usuario", Context.MODE_PRIVATE);
        int idUsuario = preferences.getInt("idUsuario", 0);
        int idVidDoc =list.get(position).getIdVidDoc();
        interfaceFragments.onClickOpcion(idUsuario,idVidDoc,2);
    }

    @Override
    public void agregarFavoritos(int adapterPosition, List<Documentos> documentosList) {
        SharedPreferences preferences = contexto.getSharedPreferences("Datos usuario", Context.MODE_PRIVATE);
        int idUsuario = preferences.getInt("idUsuario", 0);
        int idVidDoc =documentosList.get(adapterPosition).getIdVidDoc();
        verificarExistencia(idUsuario, idVidDoc);
    }

    @Override
    public void leerDocumento(int idVidDoc) {
        interfaceFragments.leerDocumento(idVidDoc);
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
                            //estrella=false;
                        }
                        else {
                            deleteFavoritos(idFavorito);
                           // estrella=true;
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
                SharedPreferences preferences = contexto.getSharedPreferences("Datos usuario", Context.MODE_PRIVATE);
                int idUsuario = preferences.getInt("idUsuario", 0);
                interfaceFragments.vistaVideosDoc(false,idUsuario);
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
                SharedPreferences preferences = contexto.getSharedPreferences("Datos usuario", Context.MODE_PRIVATE);
                int idUsuario = preferences.getInt("idUsuario", 0);
                interfaceFragments.vistaVideosDoc(false,idUsuario);
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


    @Override
    public void onClick(View v) {
        SharedPreferences preferences = contexto.getSharedPreferences("Datos usuario", Context.MODE_PRIVATE);
        int idUsuario = preferences.getInt("idUsuario", 0);
        switch(v.getId()){
            case R.id.btnVideo:
                interfaceFragments.vistaVideosDoc(true,idUsuario);
                break;
            case R.id.btnDocumento:
                interfaceFragments.vistaVideosDoc(false,idUsuario);
                break;
            case R.id.btnSubirDocumento:
                ((MenuEstudiante)contexto).fragment = this;
                interfaceFragments.onClickSubirDoc();
                break;

        }
    }
    private void buscarDoc(){
        SharedPreferences preference = contexto.getSharedPreferences("Datos usuario", Context.MODE_PRIVATE);
        int idUsuario = preference.getInt("idUsuario", 0);

        String url = "https://readandwatch.herokuapp.com/php/cargarVidDocUsuario.php?" +
                "idUsuario="+idUsuario+"&tipo=d";
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONArray json = response.optJSONArray("usuario");
                JSONObject jsonObject=null;
                if(json.length()<1){
                    idUsuarioVidDoc = new int[1];
                    idUsuarioVidDoc[0]=0;
                }else {
                    idUsuarioVidDoc = new int[json.length()];
                }
                for(int i=0;i<json.length();i++){
                    try {
                        jsonObject=json.getJSONObject(i);
                        idUsuarioVidDoc[i]= jsonObject.getInt("idVidDoc");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                cargarWebService();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                cargarWebService();
            }
        });
        request.add(jsonObjectRequest);
    }

    private void cargarWebService() {
        String url;
        String ip=getString(R.string.ip);
        progreso = new ProgressDialog(contexto);
        progreso.setMessage("Cargando...");
        progreso.show();
        url = ip+"/php/cargarVidDoc.php?" +
                "idTema="+idTema+"&tipo=d";
        url=url.replace(" ", "%20");
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        request.add(jsonObjectRequest);
    }
    @Override
    public void onErrorResponse(VolleyError error) {
        progreso.hide();
    }

    @Override
    public void onResponse(JSONObject response) {
        JSONArray json;
        JSONObject jsonObject=null;
        Documentos documento;
        json = response.optJSONArray("usuario");
        String descripcion,miniatura, eliminado;
        int idUsuario,idVidDoc;
        try {
            for(int i=0;i<json.length();i++){
                jsonObject=json.getJSONObject(i);
                idUsuario=jsonObject.optInt("idUsuario");
                descripcion=jsonObject.optString("descripcion");
                miniatura=jsonObject.optString("rutaImagen");
                idVidDoc=jsonObject.optInt("idVidDoc");
                eliminado = jsonObject.optString("eliminado");
                if(eliminado.equals("N")) {
                    documento = new Documentos(Integer.toString(idUsuario), descripcion, miniatura, idUsuario, idVidDoc);
                    documentos.add(documento);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter=new DocumentosAdapter(contexto,documentos,this,idUsuarioVidDoc, idUsuarioVidDocFav);
        contador=0;
        for (int i=0;i<documentos.size();i++) {
            FileLoader.with(contexto).load("https://readandwatch.000webhostapp.com/archivos/"+documentos.get(i).getIdVidDoc()+".pdf").fromDirectory("PDFFiles", FileLoader.DIR_EXTERNAL_PUBLIC).asFile(new FileRequestListener<File>() {
                @Override
                public void onLoad(FileLoadRequest request, FileResponse<File> response) {
                    File pdf = response.getBody();

                    FileInputStream fileInputStream = null;
                    byte[] bytesArray = null;
                    try {
                        bytesArray = new byte[(int) pdf.length()];
                    }catch(Exception e){
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
                        String fileName = pdf.getName().substring(index+1,pdf.getName().length()-4);
                        for(int j=0;j<documentos.size();j++){
                            if(Integer.toString(documentos.get(j).getIdVidDoc()).equals(fileName)){
                                documentos.get(j).setImagen(bmp);
                                break;
                            }
                        }
                        contador++;
                        if(contador==documentos.size()){
                            progreso.hide();
                            recyclerDocumentos.setAdapter(adapter);
                        }
                    } catch (IOException e) {
                        progreso.hide();
                        Toast.makeText(contexto, "Error al descargar los archivos.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onError(FileLoadRequest request, Throwable t) {
                    Toast.makeText(contexto, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
