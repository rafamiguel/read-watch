package estrada.leon.rafael.readwatch.Estudiante.POJO;

import estrada.leon.rafael.readwatch.Interfaces.Item;

public class Temas implements Item {
   private String nombre;

   public Temas(String nombre){
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
        return 1;
    }
}
