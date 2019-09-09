package estrada.leon.rafael.readwatch.estudiante.pojo;

import estrada.leon.rafael.readwatch.estudiante.interfaces.Item;

public class Videos implements Item {
    private String descripcion;
    private String perfil;
    private String rutaImagen;
    private int idUsuario;
    private int idVidDoc;

    public Videos(String perfil,String descripcion, String rutaImagen, int idUsuario, int idVidDoc) {
        this.descripcion = descripcion;
        this.perfil = perfil;
        this.rutaImagen = rutaImagen;
        this.idUsuario = idUsuario;
        this.idVidDoc=idVidDoc;
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

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdVidDoc() {
        return idVidDoc;
    }

    public void setIdVidDoc(int idVidDoc) {
        this.idVidDoc = idVidDoc;
    }

    @Override
    public int getViewType() {
        return 2;
    }
}
