package estrada.leon.rafael.readwatch.estudiante.pojo;

import estrada.leon.rafael.readwatch.estudiante.interfaces.Item;

public class Temas implements Item {
   private String nombre;
   private int idTema;
   private String materia;

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

    @Override
    public int getViewType() {
        return 1;
    }
}
