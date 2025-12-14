package cliente;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.concurrent.Semaphore;

import mensaje.*;

public class OyenteServidor extends Thread{
	private Cliente cliente;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private Lock lock; 
	private Semaphore sem;
	
	protected OyenteServidor(Lock lock, Semaphore sem, Cliente cliente) {
		this.cliente = cliente;
		this.lock = lock;
		this.out = cliente.getOutputStream();
		this.in = cliente.getInputStream();
		this.sem = sem;
	}
	
	public void run() {
		try {
			boolean b = false;
			while(!b) {
				Mensaje m = (Mensaje)in.readObject();
				switch(m.getTipo()) {
					case CONF_CONEXION:
						
						lock.takeLock();
						System.out.println("CONEXION INICIADA CON EL SERVIDOR");
						lock.releaseLock();
						sem.release();
						break;
						
					case CONF_LISTA_USR:
						
						lock.takeLock();
						Mensaje_ConfirmarListaUsr mlu = (Mensaje_ConfirmarListaUsr) m;
						System.out.println("-------------------------------------------------------------------------------------");
						System.out.println("USUARIOS CONECTADOS:");
						System.out.println();
				  		List<String> usuarios = mlu.getListaUsr();
				  		
				  		List<List<String>> recetas = mlu.getRecetas();
				  		
				  		for(int i = 0; i < usuarios.size(); i++) {
				  			System.out.println(usuarios.get(i));
				  			System.out.println("RECETAS: ");
				  			for(int j = 0;  j < recetas.get(i).size(); j++) {
				  				System.out.println("\t"+recetas.get(i).get(j));
				  			}
				  			System.out.println();
				  		}
						System.out.println("-------------------------------------------------------------------------------------");
						lock.releaseLock();
						sem.release();
						break;
						
					case EMITIR:
				  		
				  		Mensaje_Emitir me = (Mensaje_Emitir) m;
				  		int nuevoP = (int) (Math.floor(Math.random()*5000)) + 5000;
				  		out.writeObject(new Mensaje_PreparadoClienteServidor(cliente.getNombre(), me.getOrigen(), nuevoP));
				  		out.flush();
				  		(new Emisor(nuevoP, me.getRecetaPedida(), cliente)).start();			  		
				  		break;
				  		
				  	case PREPARADO_SC:
				  		lock.takeLock();		

				  		Mensaje_PreparadoServidorCliente msc = (Mensaje_PreparadoServidorCliente) m;
				  		(new Receptor(msc.getPuerto(), lock, sem)).start();
				  		break;
				  		
				  	case CONF_CERRAR_CONEXION:

				  		System.out.println("CONEXION CERRADA CON EXITO");
						System.out.println("-------------------------------------------------------------------------------------");

				  		b = true;
				  		
				  		break;
				  	
				  	case ARCHIVO_NO_EXISTENTE:
				  		
				  		lock.takeLock();		
				  		System.err.println("ARCHIVO NO EXISTENTE");
				  		lock.releaseLock();
				  		sem.release();
				  		break;
				  		
				  	case PEDIR_USR:
				  		Mensaje_PedirUsr mp = (Mensaje_PedirUsr) m;
				  	    System.out.println("\n" + mp.getOrigen() +  " PIDIENDO RECETA " + mp.getReceta());
						System.out.print("OPCION >> ");
					  	int nuevoP2 =  (int) (Math.floor(Math.random()*5000)) + 5000;
					  	out.writeObject(new Mensaje_PreparadoClienteServidor(cliente.getNombre(), mp.getOrigen(), nuevoP2));
					  	out.flush();
					  	(new Emisor(nuevoP2, mp.getReceta(), cliente)).start();
				  		break;

				 	default:
				 		break;
				}
			}
		}catch(IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
