package estrada.leon.rafael.readwatch.estudiante.pojo;

public class MateriasPropuestas {

    private int id;
    private int votos;
    private String nombre;

    public MateriasPropuestas(int id, int votos, java.lang.String nombre) {
        this.id = id;
        this.votos = votos;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVotos() {
        return votos;
    }

    public void setVotos(int votos) {
        this.votos = votos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
