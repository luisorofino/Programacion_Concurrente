package mensaje;

import java.io.Serializable;
import java.util.List;

public class Mensaje_Conexion extends Mensaje implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<String> recetas;

    public Mensaje_Conexion(String origen, String destino,List<String> recetas) {
        super(TipoMensaje.CONEXION, origen, destino);
        this.recetas = recetas;
    }

    public List<String> getRecetas(){
        return this.recetas;
    }

}