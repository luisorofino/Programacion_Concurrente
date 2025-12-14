package servidor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import usuario.*;

public class TablaUsr {
	private MonitorServidor monitor;
	private HashMap<String,Usuario> tablaUsr;
	
	public TablaUsr() {
		this.tablaUsr = new HashMap<String,Usuario>();
		this.monitor = new MonitorServidor();
	}
	
	protected void anadirUsr(Usuario usuario) throws InterruptedException {
		monitor.requestWrite();
		tablaUsr.put(usuario.getNombre(), usuario);
		monitor.releaseWrite();
	}
    protected void elimUsr(String nombre) throws InterruptedException {
    	
    	monitor.requestWrite();
    	tablaUsr.remove(nombre);
    	monitor.releaseWrite();
    }
    
    protected List<String> getListaUsr() throws InterruptedException{
		
    	monitor.requestRead();
		
		List<String> usuarios = new ArrayList<String>();
		
		for (Usuario valor : tablaUsr.values()) {
			usuarios.add(valor.getNombre());
		}
		
		monitor.releaseRead();
		return usuarios;
	}
    protected List<List<String>> getRecetas() throws InterruptedException {
		
    	monitor.requestRead();
		
		List<List<String>> recetas = new ArrayList<List<String>>();
		
		for (Usuario usuario : tablaUsr.values()) {
			
			List<String> ingredientes = new ArrayList<String>();
			
			for(String ingrediente : usuario.getDatos()) {
				ingredientes.add(ingrediente);
			}
			
			recetas.add(ingredientes);
			
		}
		
		monitor.releaseRead();
		return recetas;
	}
	protected String getUsrDeReceta(String receta) throws InterruptedException {
		
		monitor.requestRead();
		String name = null;
		for(Usuario usuario : tablaUsr.values()) {			
			for(String rec : usuario.getDatos()) {				
				if(receta.equals(rec)) {
					name = usuario.getNombre();
					break;
				}
			}		
			if(name != null) {
				break;
			}
		}	
		monitor.releaseRead();
		return name;
	}
}