package estrada.leon.rafael.readwatch.administrador.pojo;

public class VideosAdm {
    private String descripcion;
    private String perfil;
    private String rutaImagen;
    private int idVidDoc;

    public VideosAdm(String descripcion, String perfil, String rutaImagen, int idVidDoc) {
        this.descripcion = descripcion;
        this.perfil = perfil;
        this.rutaImagen = rutaImagen;
        this.idVidDoc = idVidDoc;
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
}
