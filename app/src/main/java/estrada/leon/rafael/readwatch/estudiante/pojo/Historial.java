package estrada.leon.rafael.readwatch.estudiante.pojo;

import estrada.leon.rafael.readwatch.estudiante.interfaces.Item;

public class Historial implements Item {
    private int id;
    private String tipo;
    private String motivo;
    private String castigo;

    public Historial(int id,String tipo,String motivo,String castigo){
        this.id=id;
        this.tipo=tipo;
        this.motivo=motivo;
        this.castigo=castigo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getCastigo() {
        return castigo;
    }

    public void setCastigo(String castigo) {
        this.castigo = castigo;
    }

    @Override
    public int getViewType() {
        if(tipo.equals("comentario")){
            return 1;
        }else
            return 2;
    }
}

