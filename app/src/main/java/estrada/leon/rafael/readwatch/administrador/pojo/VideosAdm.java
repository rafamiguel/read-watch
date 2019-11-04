package estrada.leon.rafael.readwatch.administrador.pojo;

public class VideosAdm {
    private String descripcion;
    private String perfil;
    private String rutaImagen;
    private int idVidDoc;
    private int idUsuario;
    private String videoUrl;

    public VideosAdm(String descripcion, String perfil, String rutaImagen, int idVidDoc,int idUsuario, String videoUrl) {
        this.descripcion = descripcion;
        this.perfil = perfil;
        this.rutaImagen = rutaImagen;
        this.idVidDoc = idVidDoc;
        this.idUsuario=idUsuario;
        this.videoUrl = videoUrl;
    }

    public String getVideoUrl() {
        for(int a=0; a<videoUrl.length();a++){
            if (String.valueOf(videoUrl.charAt(a)).equals("=")) {
                videoUrl = videoUrl.substring(a+1);
                videoUrl="<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/" + videoUrl + "\" frameborder=\"0\" allowfullscreen=\"allowfullscreen\"></iframe>";
                break;
            }
        }
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
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
