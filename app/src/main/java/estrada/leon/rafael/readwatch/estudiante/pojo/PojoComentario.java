package estrada.leon.rafael.readwatch.estudiante.pojo;

public class PojoComentario {
    private String Perfil;
    private String Comentario;

    public PojoComentario(String Perfil, String Comentario){
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
}
