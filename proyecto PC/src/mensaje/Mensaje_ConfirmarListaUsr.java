package mensaje;

import java.util.List;

public class Mensaje_ConfirmarListaUsr extends Mensaje{

    private static final long serialVersionUID = 1L;

    private List<String> listaUsr;
    private List<List<String>> recetas;

    public Mensaje_ConfirmarListaUsr(String origen, String destino, List<String> listaUsr, List<List<String>> recetas) {
        super(TipoMensaje.CONF_LISTA_USR, origen, destino);
        this.recetas = recetas;
        this.listaUsr = listaUsr;
    }

    public List<String> getListaUsr(){
        return this.listaUsr;
    }

    public List<List<String>> getRecetas(){
        return this.recetas;
    }
}