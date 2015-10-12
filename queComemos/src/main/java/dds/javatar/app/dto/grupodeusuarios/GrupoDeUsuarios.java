package dds.javatar.app.dto.grupodeusuarios;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;

import dds.javatar.app.dto.usuario.Usuario;

@Entity
public class GrupoDeUsuarios {

	@Id
	@Column(name="grupo_id")
	@GeneratedValue
	private Long idGrupo;
	
	@Column(name="nombre")
	private String nombre;
	
	private Map<String, Boolean> preferenciasAlimenticias;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "grupo_usuario",
	    joinColumns = @JoinColumn(name = "grupo_id"),
	    inverseJoinColumns = @JoinColumn(name = "usuario_id") )
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
