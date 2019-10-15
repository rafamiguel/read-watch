package estrada.leon.rafael.readwatch.general.pojo;

import estrada.leon.rafael.readwatch.estudiante.interfaces.iSesion;

public class Admin extends Usuario implements iSesion {

    public Admin(int id, String nombre, String apellidos, String correo, String contrasena) {
        super(id, nombre, apellidos, correo, contrasena);
    }

    public Admin() {
    }

    @Override
    public char getViewType() {
        return 'A';
    }
}
