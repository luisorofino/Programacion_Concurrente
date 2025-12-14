package mensaje;

public class Mensaje_Pedir extends Mensaje{

    private static final long serialVersionUID = 1L;

    private String receta;

    public Mensaje_Pedir(String origen, String destino, String receta) {
        super(TipoMensaje.PEDIR, origen, destino);
        this.receta = receta;
    }


    public String getReceta() {
        return this.receta;
    }
}