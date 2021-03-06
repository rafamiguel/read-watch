package estrada.leon.rafael.readwatch.estudiante.interfaces;

import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Map;

public interface iComunicacionFragments {
    public void seleccionarSemestre(int idMateria);
    public void seleccionarTema(int semestre);
    public void seleccionarVideo(int idTema, int idUsuario);
    public void onClickVideosHolder(Toast toast);
    public void vistaVideosDoc(boolean i, int idUsuario);
    public void onClickDocumentosHolder(Toast toast);
    public void onClickTemasLibresHolder(Toast toast);
    public void onClickVidFavHolder(Toast toast);
    public void onClickDocFavHolder(Toast toast);
    public void onClickNuevaPregunta();
    public void onClickProponerTema();
    public void onClickProponerMateria();
    public void onClickVidPerfil(int idUsuario);
    public void onClickDocPerfil(int idUsuario);
    public void onClickReportarVidDoc(int idVidDoc);
    public void onClickReportarPreg(int idPreg);
    public void onClickSubirDoc();
    public void onClickSubirVid();
    public void onClickComentario(int idUsuario, int idVidDoc,  int idPregunta);
    public void onClickSubirVidPreg(int idPregunta);
    public void onClickSubirDocPreg(int idPregunta);
    public void onClickOpcion(int idUsuario,int idVidDoc, int opcion);
    public void leerDocumento(int idVidDoc);
    public void mostrarGaleria();

}