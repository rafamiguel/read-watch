package estrada.leon.rafael.readwatch.estudiante.pojo;

import estrada.leon.rafael.readwatch.estudiante.interfaces.Item;

public class Historial implements Item {
    private  String rutaImagen;
    private String ruta;
    private String motivo;
    private String castigo;
    private String tipo;

    public Historial(String rutaImagen,String ruta,String motivo,String castigo, String tipo){

        this.rutaImagen = rutaImagen;
        this.ruta = ruta;
        this.motivo=motivo;
        this.castigo=castigo;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
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
     //   if(tipo.equals("comentario")){
       //     return 1;
        //}else
            return 2;
   }
}

