package estrada.leon.rafael.readwatch.administrador.pojo;

public class BuscarUsuarioAd {
    String perfil;
    String apellido;
    String rutaImagen;

    public BuscarUsuarioAd(String perfil, String apellido, String rutaImagen){
        this.perfil = perfil;
        this.rutaImagen = rutaImagen;
        this.apellido = apellido;
    }

    public String getPerfil(){return  perfil;}
    public void setPerfil(String perfil){this.perfil=perfil;}

    public String getApellido() {return apellido;
    }
    public void setApellido(String apellido) { this.apellido = apellido;
    }

    public String getRutaImagen(){return  rutaImagen;}
    public void setRutaImagen(String rutaImagen){this.rutaImagen=rutaImagen;}

}
