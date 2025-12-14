package servidor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantLock;

import mensaje.*;
import usuario.Usuario;

public class OyenteCliente extends Thread{
	
	private Socket s = null;
	
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	private TablaUsr tablaUsr;
	private TablaOut tablaOut;
	private TablaIn tablaIn;
	private ReentrantLock lock;
	
	protected OyenteCliente(Socket s, TablaUsr tablaUsr, TablaIn tablaIn, TablaOut tablaOut, ReentrantLock lock) throws IOException {
		this.s = s;
		this.tablaUsr = tablaUsr;
		this.tablaOut = tablaOut;
		this.tablaIn = tablaIn;
		this.lock = lock;
	}
	
	public void run() {
		try {
			this.out = new ObjectOutputStream(s.getOutputStream());
			this.in = new ObjectInputStream(s.getInputStream());
			boolean b = false;
			while (!b) {
				Mensaje m = (Mensaje) in.readObject();
				switch(m.getTipo()) {
				case CONEXION:
			  		
					Mensaje_Conexion mc = (Mensaje_Conexion) m;
		   			
		   			Usuario nuevoUsr = new Usuario(mc.getOrigen(),  mc.getRecetas());
		   			anadirUsr(nuevoUsr, out, in);
		   			lock.lock();
					out.writeObject(new Mensaje_ConfirmarConexion("servidor", mc.getOrigen()));
					out.flush();
					lock.unlock();
		   			System.out.println("CONEXION INICIADA CON " + nuevoUsr.getNombre());
					break;
		  	
		   		case LISTA_USR:
		   			
		   			lock.lock();   			
		   			out.writeObject(new Mensaje_ConfirmarListaUsr("servidor", m.getOrigen(), tablaUsr.getListaUsr(), tablaUsr.getRecetas()));
		   			out.flush();
		   			lock.unlock(); 
			  		break;
		  		
		   		case CERRAR_CONEXION:
		  		
		   			Mensaje_CerrarConexion mcc = (Mensaje_CerrarConexion) m;
		   			elimUsr(mcc.getOrigen());
		   			lock.lock(); 
		   			out.writeObject(new Mensaje_ConfirmarCerrarConexion("servidor", m.getOrigen()));
		   			out.flush();
		   			lock.unlock(); 
		   			System.out.println("CONEXION CERRADA CON " + mcc.getOrigen());
		   			
		   			b = true;
		   			
			  		break;
		  		
		   		case PEDIR:
		   			
		   			Mensaje_Pedir mp = (Mensaje_Pedir) m;
		   			String receta = mp.getReceta();
		   			String nombre = tablaUsr.getUsrDeReceta(receta);
		   			if(nombre != null) {
		   				ObjectOutputStream out2 = tablaOut.getUserOutputStream(nombre);
		   				if (nombre == mp.getOrigen()) {
			   				out2.writeObject(new Mensaje_Emitir(mp.getOrigen(), nombre, receta));
			   				out2.flush();
					  		System.out.println(m.getOrigen() + " PIDIENDO RECETA " + receta + " AL SERVIDOR");
		   				}
		   				else {
		   					out2.writeObject(new Mensaje_PedirUsr(mp.getOrigen(), nombre, receta));
		   					out2.flush();
					  		System.out.println(m.getOrigen() + " PIDIENDO RECETA " + receta + " AL USUARIO " + nombre);
		   				}
		   			}	   			
		   			else {
		   				lock.lock(); 
		   				out.writeObject(new Mensaje_ArchivoNoExistente("servidor", mp.getOrigen()));
		   				out.flush();
		   				lock.unlock(); 
		   				
		   				System.err.println("LA RECETA " + mp.getReceta() + " NO EXISTE. SOLICITUD IGNORADA");
		   			}
			  		break;
		  		
		   		case PREPARADO_CS:
		  		
		   			Mensaje_PreparadoClienteServidor mcs = (Mensaje_PreparadoClienteServidor) m;		 

		   			ObjectOutputStream out2 = tablaOut.getUserOutputStream(mcs.getDestino());
		   			
		   			out2.writeObject(new Mensaje_PreparadoServidorCliente(mcs.getOrigen(), mcs.getDestino(), mcs.getPuerto()));
		   			out2.flush();
		   			
		   			break;

		  		
		   		default:
		   			break;
				}
				
			}
		} catch(IOException | ClassNotFoundException | InterruptedException e) {
			System.err.println("ERROR AL EJECUTAR OYENTE CLIENTE");
		}
	}
	
	protected void anadirUsr(Usuario usr, ObjectOutputStream out, ObjectInputStream in) throws InterruptedException {

		tablaUsr.anadirUsr(usr);
		tablaOut.put(usr.getNombre(), out);
		tablaIn.put(usr.getNombre(), in);
		
    }
    
    protected void elimUsr(String usr) throws InterruptedException {
		tablaUsr.elimUsr(usr);
		tablaOut.remove(usr, out);
		tablaIn.remove(usr, in);
		
	}

}