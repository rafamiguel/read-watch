package estrada.leon.rafael.readwatch.estudiante.pojo;

import estrada.leon.rafael.readwatch.estudiante.interfaces.Item;

public class Favoritos implements Item {
    private int idUsuario;
    private int idVidDoc;
    private int idFavorito;

    public Favoritos(int idUsuario, int idVidDoc, int idFavorito) {
        this.idUsuario = idUsuario;
        this.idVidDoc = idVidDoc;
        this.idFavorito = idFavorito;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdVidDoc() {
        return idVidDoc;
    }

    public void setIdVidDoc(int idVidDoc) {
        this.idVidDoc = idVidDoc;
    }

    public int getIdFavorito() {
        return idFavorito;
    }

    public void setIdFavorito(int idFavorito) {
        this.idFavorito = idFavorito;
    }

    @Override
    public int getViewType() {
        return 0;
    }
}
