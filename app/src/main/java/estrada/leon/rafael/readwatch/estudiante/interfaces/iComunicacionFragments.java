package estrada.leon.rafael.readwatch.estudiante.interfaces;

import android.widget.Toast;

public interface iComunicacionFragments {
    public void seleccionarSemestre(String materia);
    public void seleccionarTema(int semestre);
    public void seleccionarVideo(String tema);
    public void onClickVideosHolder(Toast toast);
    public void vistaVideosDoc(boolean i);
    public void onClickDocumentosHolder(Toast toast);
    public void onClickTemasLibresHolder(Toast toast);
    public void onClickVidFavHolder(Toast toast);
    public void onClickDocFavHolder(Toast toast);
    public void onClickNuevaPregunta();
    public void onClickProponerTema();
    public void onClickProponerMateria();
    public void onClickVidPerfil();
    public void onClickDocPerfil();
    public void onClickReportar();
    public void onClickSubirDoc();
    public void onClickSubirVid();
}