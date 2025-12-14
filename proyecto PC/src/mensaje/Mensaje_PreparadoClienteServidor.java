package mensaje;

public class Mensaje_PreparadoClienteServidor extends Mensaje {

	private static final long serialVersionUID = 1L;
	
	private int puerto;

	public Mensaje_PreparadoClienteServidor(String origen, String destino, int puerto) {
		super(TipoMensaje.PREPARADO_CS, origen, destino);
		this.puerto = puerto;
	}

	
	public int getPuerto() {
		return this.puerto;
	}
}
