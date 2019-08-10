package estrada.leon.rafael.readwatch.estudiante.pojo;

public class Documentos {
    private String perfil;
    private String descripcion;

    public Documentos(String perfil, String descripcion){
        this.perfil=perfil;
        this.descripcion=descripcion;
    }

    public String getPerfil(){return  perfil;}
    public void setPerfil(String perfil){this.perfil=perfil;}

    public String getDescripcion(){return descripcion;}
    public void setDescripcion(String descripcion){this.descripcion=descripcion;}

}
