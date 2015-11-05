package dds.javatar.app.dto.grupodeusuarios;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import dds.javatar.app.dto.usuario.Usuario;

public class GrupoDeUsuarios {

	private String nombre;
	private Map<String, Boolean> preferenciasAlimenticias;
	private Set<Usuario> miembros;

	/**** Constructor ****/
	public GrupoDeUsuarios() {
		this.preferenciasAlimenticias = new HashMap<String, Boolean>();
		this.miembros = new HashSet<Usuario>();
	}

	/**** Setters y getters ****/

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Map<String, Boolean> getPreferenciasAlimenticias() {
		return preferenciasAlimenticias;
	}

	public void setPreferenciasAlimenticias(
			Map<String, Boolean> preferenciasAlimenticias) {
		this.preferenciasAlimenticias = preferenciasAlimenticias;
	}

	public Set<Usuario> getUsuarios() {
		return miembros;
	}

	public void setUsuarios(Set<Usuario> usuarios) {
		this.miembros = usuarios;
	}

	public void agregarUsuario(Usuario usuario) {
		usuario.agregarGruposAlQuePertenece(this);
		this.miembros.add(usuario);
	}

}
