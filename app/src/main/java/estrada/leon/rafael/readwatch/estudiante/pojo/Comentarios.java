package estrada.leon.rafael.readwatch.estudiante.pojo;

import estrada.leon.rafael.readwatch.estudiante.interfaces.Item;

public class Comentarios implements Item {
    private String Perfil;
    private String Comentario;

    public Comentarios(String Perfil, String Comentario){
        this.Comentario = Comentario;
        this.Perfil = Perfil;
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
