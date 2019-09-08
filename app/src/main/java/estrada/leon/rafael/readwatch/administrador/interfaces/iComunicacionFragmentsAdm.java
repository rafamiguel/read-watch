package estrada.leon.rafael.readwatch.administrador.interfaces;

import android.widget.Toast;

public interface iComunicacionFragmentsAdm {
    public void onClickBuscarUsuario(Toast toast);
    public void onClickVideosAdmHolder(int idVidDoc);
    public void vistaVideosDoc(boolean i);
    public void onClickInactivo(Toast toast);
    public void onClickDocumentosHolder(Toast toast);
    public void onClickVidPerfil(int idUsuario);
    public void onClickComentario(int idVidDoc);
}
