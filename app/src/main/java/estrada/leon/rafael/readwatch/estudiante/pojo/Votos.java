package estrada.leon.rafael.readwatch.estudiante.pojo;

public class Votos {
    private int idUsuario;
    private String nombre;
    private String materia;
    private String tema;

    public Votos() {
    }

    public Votos(int idUsuario, String nombre) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
    }

    public Votos(int idUsuario, String nombre, String materia) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.materia = materia;
    }

    public Votos(int idUsuario, String nombre, String materia, String tema) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.materia = materia;
        this.tema = tema;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }
}
