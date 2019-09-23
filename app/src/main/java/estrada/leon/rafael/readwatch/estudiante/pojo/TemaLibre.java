package estrada.leon.rafael.readwatch.estudiante.pojo;

public class TemaLibre {
    String descripcion;
    String pregunta;
    int id;

    public TemaLibre(String descripcion, String pregunta, int id){
        this.descripcion = descripcion;
        this.pregunta = pregunta;
        this.id=id;
    }

    public String getDescripcion(){return descripcion;}
    public void  setDescripcion(String descripcion){this.descripcion= descripcion;}

    public String getPregunta(){return  pregunta;}
    public void setPregunta(String pregunta){this.pregunta= pregunta;}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
