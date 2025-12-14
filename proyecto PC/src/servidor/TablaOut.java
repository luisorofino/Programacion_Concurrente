package servidor;

import java.io.ObjectOutputStream;
import java.util.HashMap;

public class TablaOut {
    private SemLectoresEscritores sem;
    private HashMap<String,ObjectOutputStream> tablaOut;

    public TablaOut() {
        this.tablaOut = new HashMap<String,ObjectOutputStream>();
        this.sem = new SemLectoresEscritores();
    }
    public void put(String nombre,ObjectOutputStream out ) {
        sem.requestWrite();
        tablaOut.put(nombre, out);
        sem.releaseWrite();
    }
    public void remove(String nombre,ObjectOutputStream out ) {
        sem.requestWrite();
        tablaOut.remove(nombre, out);
        sem.releaseWrite();
    }
    protected ObjectOutputStream getUserOutputStream(String nombre) throws InterruptedException {
        sem.requestRead();
        ObjectOutputStream out = tablaOut.get(nombre);
        sem.releaseRead();
        return out;
    }
}