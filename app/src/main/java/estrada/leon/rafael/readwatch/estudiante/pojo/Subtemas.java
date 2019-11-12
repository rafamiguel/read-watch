package estrada.leon.rafael.readwatch.estudiante.pojo;

import estrada.leon.rafael.readwatch.estudiante.interfaces.Item;

public class Subtemas implements Item {
   private String nombre;
   private int idSubtema;
   private String tema;
   private int votos;
   private String materia;
   private int idUsuario;

    public Subtemas() {
        nombre = "";
        idSubtema = 0;
        tema = "";
        votos = 0;
        materia = "";
        idUsuario = 0;
    }

    public Subtemas(String nombre, int idSubtema) {
        this.nombre = "";
        this.idSubtema = 0;
        this.tema = "";
        this.votos = 0;
        this.materia = "";
        this.idUsuario = 0;
        this.nombre = nombre;
        this.idSubtema = idSubtema;
    }

    public Subtemas(String nombre, int idSubtema, String tema) {
        this.nombre = "";
        this.idSubtema = 0;
        this.tema = "";
        this.votos = 0;
        this.materia = "";
        this.idUsuario = 0;
        this.nombre = nombre;
        this.idSubtema = idSubtema;
        this.tema = tema;
    }

    public Subtemas(String nombre, String tema, int votos, String materia) {
        this.nombre = "";
        this.idSubtema = 0;
        this.tema = "";
        this.votos = 0;
        this.materia = "";
        this.idUsuario = 0;
        this.nombre = nombre;
        this.tema = tema;
        this.votos = votos;
        this.materia = materia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdSubtema() {
        return idSubtema;
    }

    public void setIdSubtema(int idSubtema) {
        this.idSubtema = idSubtema;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public int getVotos() {
        return votos;
    }

    public void setVotos(int votos) {
        this.votos = votos;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public int getViewType() {
        return 2;
    }
}
