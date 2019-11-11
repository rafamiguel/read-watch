package estrada.leon.rafael.readwatch.estudiante.pojo;

public class Materias {
    private int idMateria;
    private String nombre;
    private String rutaImagen;
    private int idUsuario;
    private int votos;

    public Materias(){}

    public Materias(int idMateria,String rutaImagen,String nombre){
        this.idMateria=idMateria;
        this.rutaImagen=rutaImagen;
        this.nombre=nombre;
    }

    public Materias(int idUsuario,String nombre) {
        this.nombre = nombre;
        this.idUsuario = idUsuario;
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
}
