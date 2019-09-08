package estrada.leon.rafael.readwatch.estudiante.pojo;

import estrada.leon.rafael.readwatch.estudiante.interfaces.Item;

public class Subtemas implements Item {
   private String nombre;
   private int idSubtema;

    public Subtemas(String nombre, int idSubtema) {
        this.nombre = nombre;
        this.idSubtema = idSubtema;
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

    @Override
    public int getViewType() {
        return 2;
    }
}
