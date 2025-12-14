package mensaje;

public class Mensaje_PedirUsr extends Mensaje{
	
	private static final long serialVersionUID = 1L;

    private String receta;

    public Mensaje_PedirUsr(String origen, String destino, String receta) {
        super(TipoMensaje.PEDIR_USR, origen, destino);
        this.receta = receta;
    }

    public String getReceta() {
        return this.receta;
    }
}
