package estrada.leon.rafael.readwatch.estudiante.interfaces;

import android.widget.Toast;

public interface iComunicacionFragments {
    public void seleccionarSemestre();
    public void seleccionarTema();
    public void seleccionarVideo();
    public void onClickVideosHolder(Toast toast);
    public void vistaVideosDoc(boolean i);
    public void onClickDocumentosHolder(Toast toast);
    public void onClickTemasLibresHolder(Toast toast);
    public void onClickVidFavHolder(Toast toast);
    public void onClickDocFavHolder(Toast toast);
    public void onClickNuevaPregunta();
}