package estrada.leon.rafael.readwatch.estudiante.pojo;

public class Materias {
    private String nombre;
    private String rutaImagen;

    public Materias(String rutaImagen,String nombre){
        this.rutaImagen=rutaImagen;
        this.nombre=nombre;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
