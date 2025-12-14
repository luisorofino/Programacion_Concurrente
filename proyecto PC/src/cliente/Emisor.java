package cliente;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Emisor extends Thread{

    private String receta;

    private int puerto;

    Cliente cliente;

    protected Emisor(int puerto, String receta, Cliente cliente) {
        this.puerto = puerto;
        this.receta = receta;
        this.cliente = cliente;
    }

    public void run() {
        try {
            ServerSocket servidor = new ServerSocket(puerto);
            Socket s = servidor.accept();
            Receta f = new Receta(receta, System.getProperty("user.dir") + "/src/baseDatos/" + cliente.getNombre() + "/Recetas/" + receta);
            ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
            out.writeObject(f);
            out.flush();

            servidor.close();
            s.close();

        } catch (IOException e) {
            System.err.println("ERROR AL EMITIR RECETA");
            e.printStackTrace();
        }

    }


}