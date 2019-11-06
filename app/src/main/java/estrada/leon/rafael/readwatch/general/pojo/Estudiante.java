package estrada.leon.rafael.readwatch.general.pojo;

import estrada.leon.rafael.readwatch.estudiante.interfaces.iSesion;

public class Estudiante extends Usuario implements iSesion {

    private String telefono;
    private String descripcion;

    public Estudiante(int id, String nombre, String apellidos, String correo, String contrasena, String telefono, String descripcion, String foto) {
        super(id, nombre, apellidos, correo, contrasena);
        this.telefono = telefono;
        this.descripcion = descripcion;
    }

    public Estudiante() {
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public char getViewType() {
        return 'E';
    }
}
