package estrada.leon.rafael.readwatch.estudiante.pojo;

import estrada.leon.rafael.readwatch.estudiante.interfaces.Item;

public class Subtemas implements Item {
   private String nombre;
   private int idSubtema;
   private String tema;
   private int votos;

    public Subtemas() {
    }

    public Subtemas(String nombre, int idSubtema) {
        this.nombre = nombre;
        this.idSubtema = idSubtema;
    }

    public Subtemas(String nombre, int idSubtema, String tema) {
        this.nombre = nombre;
        this.idSubtema = idSubtema;
        this.tema = tema;
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

    @Override
    public int getViewType() {
        return 2;
    }
}
