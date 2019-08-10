package estrada.leon.rafael.readwatch.estudiante.pojo;

import estrada.leon.rafael.readwatch.estudiante.interfaces.Item;

public class Subtemas implements Item {
   private String nombre;

   public Subtemas(String nombre){
       this.nombre=nombre;
   }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public int getViewType() {
        return 2;
    }
}
