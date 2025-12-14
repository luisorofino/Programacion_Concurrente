package servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import java.util.concurrent.locks.ReentrantLock;

public class Servidor {

    private ServerSocket servidor;

    private Socket s;

    private final static int port = 1111;

    private TablaUsr tablaUsr;
    private TablaIn tablaIn;
    private TablaOut tablaOut;
    private ReentrantLock lock;

    protected Servidor() throws IOException {

        servidor = new ServerSocket(port);
        tablaUsr = new TablaUsr();
        tablaIn = new TablaIn();
        tablaOut = new TablaOut();
        lock = new ReentrantLock();
    }

    protected void iniServidor() throws IOException{

        System.out.println("SERVIDOR INICIADO");

        try {
            while(true) {
                s = servidor.accept();
                (new OyenteCliente(s, tablaUsr, tablaIn, tablaOut, lock)).start();
            }
        }catch(IOException e) {
            servidor.close();
            System.out.println("CERRANDO SERVIDOR.");
        }

    }

}