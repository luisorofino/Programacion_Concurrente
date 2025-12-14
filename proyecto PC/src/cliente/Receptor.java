package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.concurrent.Semaphore;

public class Receptor extends Thread{

    private int puerto;
    private Lock lock;
    private Semaphore sem;

    public Receptor(int puerto, Lock lock, Semaphore sem) {
        this.puerto = puerto;
        this.lock = lock;
        this.sem = sem;
    }

    public void run() {
        try {
            Socket s = new Socket("localhost", puerto);

            ObjectInputStream in = new ObjectInputStream(s.getInputStream());
            Receta r = (Receta) in.readObject();

    		System.out.println("-------------------------------------------------------------------------------------");
            System.out.println("RECETA RECIBIDA CON EXITO");
            System.out.println();

            r.print();

            System.out.println();
    		System.out.println("-------------------------------------------------------------------------------------");
            System.out.println();
            s.close();

            lock.releaseLock();
            sem.release();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("ERROR AL RECIBIR RECETA");
            e.printStackTrace();
        }

    }
}