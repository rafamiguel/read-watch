package estrada.leon.rafael.readwatch.Estudiante.POJO;

public class POJOVideos {
    String miniatura;
    String perfil;
    String descripcion;
    String iconoAdvertencia;
    String estrellita;
    String opciones;
    String comentario;
    String reportar;
    String editar;

    public POJOVideos (String miniatura, String perfil, String descripcion, String iconoAdvertencia, String estrellita, String opciones, String comentario, String reportar, String editar)
    {
        this.miniatura = miniatura;
        this.perfil= perfil;
        this.descripcion = descripcion;
        this.iconoAdvertencia = iconoAdvertencia;
        this.estrellita = estrellita;
        this.opciones = opciones;
        this.comentario = comentario;
        this.reportar = reportar;
        this.editar = editar;
    }

    public String getMiniatura(){return miniatura; }
    public void setMiniatura(String miniatura){this.miniatura = miniatura;}

    public String getPerfil(){return  perfil;}
    public void setPerfil(String perfil){this.perfil = perfil;}

    public String getDescripcion(){return descripcion;}
    public void setDescripcion(String descripcion){this.descripcion = descripcion;}

    public String getIconoAdvertencia(){return iconoAdvertencia;}
    public void setIconoAdvertencia(String iconoAdvertencia){this.iconoAdvertencia=iconoAdvertencia;}

    public String getEstrellita(){return estrellita;}
    public void setEstrellita(String estrellita){this.estrellita=estrellita;}

    public String getOpciones(){return opciones;}
    public void setOpciones(String opciones){this.opciones=opciones;}

    public String getComentario(){return comentario;}
    public void setComentario(String comentario){this.comentario=comentario;}

    public String getReportar(){return reportar;}
    public void setReportar(String reportar){this.reportar = reportar;}

    public String getEditar(){return reportar;}
    public void setEditar(String editar){this.editar=editar;}
}
