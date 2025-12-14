package mensaje;

public class Mensaje_ListaUsr extends Mensaje {

    private static final long serialVersionUID = 1L;

    public Mensaje_ListaUsr(String origen, String destino) {
        super(TipoMensaje.LISTA_USR, origen, destino);
    }

}