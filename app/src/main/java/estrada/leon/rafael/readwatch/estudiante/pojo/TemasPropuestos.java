package estrada.leon.rafael.readwatch.estudiante.pojo;

public class TemasPropuestos {
    private int idUsuario;
    private int votos;
    private String nombre;
    private String materia;
    private int semestre;

    public TemasPropuestos() {
    }

    public TemasPropuestos(int idUsuario, int votos, String nombre, String materia) {
        this.idUsuario = idUsuario;
        this.votos = votos;
        this.nombre = nombre;
        this.materia = materia;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
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

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }
}
