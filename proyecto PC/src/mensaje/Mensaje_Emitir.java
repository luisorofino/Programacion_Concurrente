package mensaje;

public class Mensaje_Emitir extends Mensaje{
    private static final long serialVersionUID = 1L;

    private String recetaPedida;

    public Mensaje_Emitir(String origen, String destino, String recetaPedida) {
        super(TipoMensaje.EMITIR, origen, destino);
        this.recetaPedida = recetaPedida;
    }

    public String getRecetaPedida() {
        return this.recetaPedida;
    }
}