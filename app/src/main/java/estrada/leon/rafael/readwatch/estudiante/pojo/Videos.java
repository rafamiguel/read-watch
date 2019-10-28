package estrada.leon.rafael.readwatch.estudiante.pojo;

import estrada.leon.rafael.readwatch.estudiante.interfaces.Item;

public class Videos implements Item {
    private String descripcion;
    private String perfil;
    private String rutaImagen;
    private int idUsuario;
    private int idVidDoc;
    private String videoUrl;

    public Videos(String perfil,String descripcion, String rutaImagen, int idUsuario, int idVidDoc, String videoUrl) {
        this.descripcion = descripcion;
        this.perfil = perfil;
        this.rutaImagen = rutaImagen;
        this.idUsuario = idUsuario;
        this.idVidDoc=idVidDoc;
        this.videoUrl=videoUrl;
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

    public String getVideoUrl() {

        for(int a=0; a<videoUrl.length();a++){
            if (String.valueOf(videoUrl.charAt(a)).equals("=")) {
                videoUrl = videoUrl.substring(a+1);
                videoUrl="<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/" + videoUrl + "\" frameborder=\"0\" allowfullscreen></iframe>";
                break;
            }
        }
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    @Override
    public int getViewType() {
        return 2;
    }
}
