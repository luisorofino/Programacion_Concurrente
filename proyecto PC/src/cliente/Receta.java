package cliente;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;


public class Receta implements Serializable{

    private static final long serialVersionUID = 1L;

    private String receta = "";
    private String ingredientes = "";

    public Receta(String receta, String path) {
        this.receta = receta;
        //System.out.println(path);
        try (BufferedReader br = new BufferedReader(new FileReader(path))){
            String linea;
            while((linea = br.readLine()) != null) {
                this.ingredientes += linea + "\n";
            }
        } catch (IOException e) {
            System.err.println("ERROR AL ABRIR LA RECETA " + receta + " " + e.getLocalizedMessage());
        }
    }

    public String getReceta() {
        return receta;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public void print() throws UnsupportedEncodingException {
        System.out.print(ingredientes);
    }
}