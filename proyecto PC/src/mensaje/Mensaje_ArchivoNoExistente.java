package mensaje;

public class Mensaje_ArchivoNoExistente extends Mensaje {

    private static final long serialVersionUID = 1L;

    public Mensaje_ArchivoNoExistente(String origen, String destino) {
        super(TipoMensaje.ARCHIVO_NO_EXISTENTE, origen, destino);
    }

}