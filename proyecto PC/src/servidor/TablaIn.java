package servidor;

import java.io.ObjectInputStream;
import java.util.HashMap;

public class TablaIn {
    private SemLectoresEscritores sem;
    private HashMap<String,ObjectInputStream> tablaIn;

    public TablaIn() {
        this.tablaIn = new HashMap<String,ObjectInputStream>();
        this.sem = new SemLectoresEscritores();
    }
    public void put(String nombre,ObjectInputStream in ) {
        sem.requestWrite();
        tablaIn.put(nombre, in);
        sem.releaseWrite();
    }
    public void remove(String nombre,ObjectInputStream in ) {
        sem.requestWrite();
        tablaIn.remove(nombre, in);
        sem.releaseWrite();
    }
    protected ObjectInputStream getUserInputStream(String nombre) throws InterruptedException {
        sem.requestRead();
        ObjectInputStream in = tablaIn.get(nombre);
        sem.releaseRead();
        return in;
    }
}