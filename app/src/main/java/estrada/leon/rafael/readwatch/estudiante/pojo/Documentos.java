package estrada.leon.rafael.readwatch.estudiante.pojo;

import android.graphics.Bitmap;

import estrada.leon.rafael.readwatch.estudiante.interfaces.Item;

public class Documentos implements Item {
    private String perfil;
    private String descripcion;
    private String rutaImagen;
    private int idVidDoc,idUsuario;
    private Bitmap imagen;
    public Documentos(String perfil,String descripcion, String rutaImagen, int idUsuario, int idVidDoc){
        this.perfil=perfil;
        this.descripcion=descripcion;
        this.rutaImagen=rutaImagen;
        this.idVidDoc=idVidDoc;
        this.idUsuario=idUsuario;
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

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    @Override
    public int getViewType() {
        return 1;
    }
}
