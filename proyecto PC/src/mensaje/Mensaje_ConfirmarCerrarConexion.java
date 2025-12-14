package mensaje;

public class Mensaje_ConfirmarCerrarConexion extends Mensaje {

    private static final long serialVersionUID = 1L;

    public Mensaje_ConfirmarCerrarConexion(String origen, String destino) {
        super(TipoMensaje.CONF_CERRAR_CONEXION, origen, destino);
    }

}