package estrada.leon.rafael.readwatch.administrador.pojo;

public class DocumentosAdm {
    private String perfil;
    private String descripcion;
    private String rutaImagen;
    public DocumentosAdm(String perfil, String descripcion,String rutaImagen){
        this.perfil=perfil;
        this.descripcion=descripcion;
        this.rutaImagen=rutaImagen;
    }

    public String getPerfil(){return  perfil;}
    public void setPerfil(String perfil){this.perfil=perfil;}

    public String getDescripcion(){return descripcion;}
    public void setDescripcion(String descripcion){this.descripcion=descripcion;}

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }
}
