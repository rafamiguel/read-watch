package estrada.leon.rafael.readwatch.administrador.pojo;

public class InactivoAdm {
    String perfil;
    String fotoperfil;

    public InactivoAdm(String perfil, String  fotoperfil){
        this.perfil = perfil;
        this.fotoperfil = fotoperfil;
    }

    public String getPerfil(){return  perfil;}
    public void setPerfil(String perfil){this.perfil=perfil;}

    public String getFotoperfil(){return  fotoperfil;}
    public void setFotoperfil(String fotoperfil){this.fotoperfil=fotoperfil;}

}
