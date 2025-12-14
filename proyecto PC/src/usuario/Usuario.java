package usuario;

import java.util.ArrayList;
import java.util.List;

public class Usuario {
	
	private String nombre;
	private List<String> datos;
	
	public Usuario(String nombre, List<String> datos) {
		this.nombre = nombre;
		this.datos = datos;
	}
		
	public String getNombre() {
		return this.nombre;
	}

	public List<String> getDatos() {
		return new ArrayList<String> (this.datos);
	}
}
