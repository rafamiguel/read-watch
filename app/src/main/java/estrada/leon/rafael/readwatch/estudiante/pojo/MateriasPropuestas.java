package estrada.leon.rafael.readwatch.estudiante.pojo;

public class MateriasPropuestas {

    private int votos;
    private String nombre;
    private int idUsuario;

    public MateriasPropuestas() {
    }

    public MateriasPropuestas(int votos, String nombre,int idUsuario) {
        this.votos = votos;
        this.nombre = nombre;
        this.idUsuario = idUsuario;
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

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}
