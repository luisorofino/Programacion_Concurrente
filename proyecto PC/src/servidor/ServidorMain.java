package servidor;

import java.io.IOException;

public class ServidorMain {

    public static void main(String[] args) {

        try {
            Servidor s = new Servidor();
            s.iniServidor();
        } catch (IOException e) {
            System.err.println("ERROR AL EJECUTAR EL SERVIDOR");
            e.printStackTrace();
        }

    }
}