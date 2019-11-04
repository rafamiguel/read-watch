package estrada.leon.rafael.readwatch.administrador.pojo;

public class DocumentosAdm {
    private String perfil;
    private String descripcion;
    private int idVidDoc, idUsuario;
    private String rutaImagen;
    public DocumentosAdm(String perfil, String descripcion,String rutaImagen, int idVidDoc, int idUsuario){
        this.perfil=perfil;
        this.descripcion=descripcion;
        this.rutaImagen=rutaImagen;
        this.idVidDoc=idVidDoc;
        this.idUsuario = idUsuario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getPerfil(){return  perfil;}
    public void setPerfil(String perfil){this.perfil=perfil;}

    public String getDescripcion(){return descripcion;}
    public void setDescripcion(String descripcion){this.descripcion=descripcion;}
    public int getIdVidDoc() {
        return idVidDoc;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }
}
