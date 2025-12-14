package mensaje;

public class Mensaje_ConfirmarConexion extends Mensaje{
	
	private static final long serialVersionUID = 1L;
	
	public Mensaje_ConfirmarConexion(String origen, String destino) {
		super(TipoMensaje.CONF_CONEXION, origen, destino);
	}
}
