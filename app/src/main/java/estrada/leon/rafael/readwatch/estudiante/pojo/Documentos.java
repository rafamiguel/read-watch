package estrada.leon.rafael.readwatch.estudiante.pojo;

import estrada.leon.rafael.readwatch.estudiante.interfaces.Item;

public class Documentos implements Item {
    private String perfil;
    private String descripcion;
    private String rutaImagen;
    public Documentos(String perfil, String descripcion,String rutaImagen){
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

    @Override
    public int getViewType() {
        return 1;
    }
}
