package estrada.leon.rafael.readwatch.Administrador.POJO;

public class BuscarUsuarioAd {
    String perfil;
    String rutaImagen;

    public BuscarUsuarioAd(String perfil, String rutaImagen){
        this.perfil = perfil;
        this.rutaImagen = rutaImagen;
    }

    public String getPerfil(){return  perfil;}
    public void setPerfil(String perfil){this.perfil=perfil;}

    public String getRutaImagen(){return  rutaImagen;}
    public void setRutaImagen(String rutaImagen){this.rutaImagen=rutaImagen;}

}
