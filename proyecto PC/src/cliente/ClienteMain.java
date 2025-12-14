package cliente;

import java.io.IOException;

public class ClienteMain {

    public static void main(String[] args) {
        Cliente cliente;
        try {
            cliente = new Cliente();
            cliente.iniCliente();
        }
        catch(IOException | InterruptedException e) {
            System.err.println("ERROR AL EJECUTAR UN CLIENTE");
            e.printStackTrace();
        }
    }
}