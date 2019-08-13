package estrada.leon.rafael.readwatch.administrador.pojo;

public class VideosAdm {
    private String descripcion;
    private String perfil;
    private String rutaImagen;
    public VideosAdm(String perfil, String descripcion, String rutaImagen){
        this.perfil=perfil;
        this.descripcion=descripcion;
        this.rutaImagen=rutaImagen;
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

}
