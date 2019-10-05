package estrada.leon.rafael.readwatch.estudiante.pojo;

import estrada.leon.rafael.readwatch.estudiante.interfaces.Item;

public class Comentarios implements Item {
    private String Perfil;
    private String Comentario;
    private int idComentario;
    public Comentarios(String Perfil, String Comentario,int idComentario){
        this.Comentario = Comentario;
        this.Perfil = Perfil;
        this.idComentario=idComentario;
    }

    public int getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(int idComentario) {
        this.idComentario = idComentario;
    }

    public String getPerfil() {
        return Perfil;
    }

    public void setPerfil(String perfil) {
        Perfil = perfil;
    }

    public String getComentario() {
        return Comentario;
    }

    public void setComentario(String comentario) {
        Comentario = comentario;
    }

    @Override
    public int getViewType() {
        return 3;
    }
}
