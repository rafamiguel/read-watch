package estrada.leon.rafael.readwatch.estudiante.pojo;

import estrada.leon.rafael.readwatch.estudiante.interfaces.Item;

public class Temas implements Item {
    private int idUsuario;
    private int votos;
   private String nombre;
   private int idTema;
   private String materia;
   private int semestre;

    public Temas() {
    }

    public Temas(String nombre, int idTema) {
        this.nombre = nombre;
        this.idTema = idTema;
    }

    public Temas(String nombre, int idTema, String materia) {
        this.nombre = nombre;
        this.idTema = idTema;
        this.materia = materia;
    }

    public Temas(int idUsuario, int votos, String nombre, int idTema, String materia, int semestre) {
        this.idUsuario = idUsuario;
        this.votos = votos;
        this.nombre = nombre;
        this.idTema = idTema;
        this.materia = materia;
        this.semestre = semestre;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdTema() {
        return idTema;
    }

    public void setIdTema(int idTema) {
        this.idTema = idTema;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
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

    @Override
    public int getViewType() {
        return 1;
    }
}
