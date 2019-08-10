package estrada.leon.rafael.readwatch.estudiante.pojo;

public class TemaLibre {
    String descripcion;
    String pregunta;

    public TemaLibre(String descripcion, String pregunta){
        this.descripcion = descripcion;
        this.pregunta = pregunta;
    }

    public String getDescripcion(){return descripcion;}
    public void  setDescripcion(String descripcion){this.descripcion= descripcion;}

    public String getPregunta(){return  pregunta;}
    public void setPregunta(String pregunta){this.pregunta= pregunta;}
}
