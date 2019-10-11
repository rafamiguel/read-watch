package estrada.leon.rafael.readwatch.administrador.pojo;

public class PojoComentario {
    private String Perfil;
    private String Comentario;
    private int idComentario;

    public PojoComentario(String Perfil, String Comentario, int idComentario){
        this.Comentario = Comentario;
        this.Perfil = Perfil;
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

    public int getIdComentario() {
        return idComentario;
    }
}
