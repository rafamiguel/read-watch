package estrada.leon.rafael.readwatch.estudiante.pojo;

public class Materias {
    private int idMateria;
    private String nombre;
    private String rutaImagen;

    public Materias(int idMateria,String rutaImagen,String nombre){
        this.idMateria=idMateria;
        this.rutaImagen=rutaImagen;
        this.nombre=nombre;
    }

    public int getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(int idMateria) {
        this.idMateria = idMateria;
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
