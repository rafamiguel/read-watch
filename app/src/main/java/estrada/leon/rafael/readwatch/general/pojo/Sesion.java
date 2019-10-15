package estrada.leon.rafael.readwatch.general.pojo;

public class Sesion {
    private static Usuario usuario;
    private Sesion(){

    }

    public synchronized static Usuario getSesion() {
        if(usuario==null){
            usuario = new Usuario();
        }
        return usuario;
    }
}
