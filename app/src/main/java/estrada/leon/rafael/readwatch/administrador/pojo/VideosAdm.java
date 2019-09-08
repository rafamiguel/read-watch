package estrada.leon.rafael.readwatch.administrador.pojo;

public class VideosAdm {
    private String descripcion;
    private String perfil;
    private String rutaImagen;
    private int idVidDoc;
    private int idUsuario;

    public VideosAdm(String descripcion, String perfil, String rutaImagen, int idVidDoc,int idUsuario) {
        this.descripcion = descripcion;
        this.perfil = perfil;
        this.rutaImagen = rutaImagen;
        this.idVidDoc = idVidDoc;
        this.idUsuario=idUsuario;
    }

    public String getDescripcion(){
        return this.descripcion;
    }
    public void setDescripcion(String descripcion){
        this.descripcion=descripcion;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public int getIdVidDoc() {
        return idVidDoc;
    }

    public void setIdVidDoc(int idVidDoc) {
        this.idVidDoc = idVidDoc;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}
