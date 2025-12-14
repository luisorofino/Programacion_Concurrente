package cliente;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import java.util.Scanner;
import java.util.concurrent.Semaphore;

import mensaje.*;

public class Cliente {
	final String serverIP = "localhost";
	final int port = 1111;
	
	private String nombre;
	private static Scanner scanner = new Scanner(System.in);
	private List<String> recetas;
	private Lock lock;
	private Socket s;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private Semaphore sem;
	
	public Cliente() throws IOException{
		this.recetas = new ArrayList<String>();
		this.s = new Socket(serverIP, port);
		this.out = new ObjectOutputStream(s.getOutputStream());
		this.in = new ObjectInputStream(s.getInputStream());
		this.lock = new LockTicket(2);
		this.sem = new Semaphore(1);
	}

	protected void iniCliente() throws IOException, InterruptedException{
		pedirDatos();
		
		Thread oyente = new OyenteServidor(lock, sem, this);
		oyente.start();
		
		leerRecetas();
		
		Mensaje_Conexion mensaje = new Mensaje_Conexion(nombre,"servidor", recetas);
		sem.acquire();
		out.writeObject(mensaje);
		out.flush();
		
		while(true) {
			sem.acquire();
			lock.takeLock();
			int opcion = getOpcion();
			if(opcion==0) {
				lock.releaseLock();
				break;
			}
			if (opcion == 1) {
				System.out.println("HA ELEGIDO CONSULTAR LA LISTA DE USUARIOS");
				out.writeObject(new Mensaje_ListaUsr(nombre,"servidor"));
				out.flush();
			}
			else if (opcion == 2){
				System.out.println("HA ELEGIDO DESCARGAR UNA RECETA. INTRODUZCA EL NOMBRE DE LA RECETA:");
				String archivo = scanner.next();
				out.writeObject(new Mensaje_Pedir(nombre,"servidor",archivo));
				out.flush();
			}
			lock.releaseLock();
		}
		System.out.println("ACABANDO LA CONEXION CON EL SERVIDOR");
		out.writeObject(new Mensaje_CerrarConexion(nombre,"servidor"));
		out.flush();
		oyente.join();
		scanner.close();
		s.close();
	}
	
	private void pedirDatos() {
		System.out.println("INTRODUZCA SU NOMBRE DE USUARIO: ");
		String input = scanner.nextLine();
		File f = null;
		do {
			f = new File(System.getProperty("user.dir") + "\\src\\baseDatos\\" + input + "\\recetas.txt");
			if(!f.exists()) {
				System.out.println("");
				System.out.println("ERROR. NO EXISTE ARCHIVO EN " + System.getProperty("user.dir")+"\\src\\baseDatos CON EL NOMBRE "+ input );
				System.out.println("INTRODUZCA SU NOMBRE DE USUARIO: ");
				input = scanner.nextLine();
			}
		} while(!f.exists());
		
		this.nombre = input;
		System.out.println("BIENVENIDO " + this.nombre);
	}
	
	private void leerRecetas() throws IOException {

	     FileReader fr = new FileReader (new File(System.getProperty("user.dir") + "\\src\\baseDatos\\" + this.nombre + "\\recetas.txt"));
	     BufferedReader br = new BufferedReader(fr);
         String linea;
         while((linea=br.readLine())!=null)
            this.recetas.add(linea);
	}
	
	private int getOpcion() {
		int op = -1;
		boolean excepcion;
		System.out.println("-------------------------------------------------------------------------------------");
		System.out.println("INTRODUZCA LA OPCION QUE DESEA");
		System.out.println();
		System.out.println(" 1. CONSULTAR LA LISTA DE USUARIOS Y SU INFORMACION");
		System.out.println(" 2. DESCARGAR RECETA");
		System.out.println(" 0. CERRAR CONEXION");
		System.out.println();
		System.out.println("-------------------------------------------------------------------------------------");
		System.out.print("OPCION >> ");
		do {
			excepcion = false;
			try {
				 op = Integer.parseInt(scanner.next()); 
			} catch(NumberFormatException e) {
				excepcion = true;
			}
			if(excepcion || op < 0 || op > 2) {
				System.out.println("\nERROR, OPCION NO VALIDA");
				System.out.print("OPCION >> ");
			}
		} while(excepcion || op < 0 || op > 2);
		return op;
	}
	
	
	protected String getNombre() {
		return this.nombre;
	}
	
	protected ObjectInputStream getInputStream() {
		return this.in;
	}
	
	protected ObjectOutputStream getOutputStream() {
		return this.out;
	}
	
}