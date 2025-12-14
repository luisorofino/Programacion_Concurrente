package mensaje;

public class Mensaje_PreparadoServidorCliente extends Mensaje{

	private static final long serialVersionUID = 1L;

	private int puerto;
	
	public Mensaje_PreparadoServidorCliente(String origen, String destino, int puerto) {
		super(TipoMensaje.PREPARADO_SC, origen, destino);
		this.puerto = puerto;
	}
	
	public int getPuerto() {
		return this.puerto;
	}
}
