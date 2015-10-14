package dds.javatar.app.dto.grupodeusuarios;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import dds.javatar.app.dto.receta.Componente;
import dds.javatar.app.dto.receta.PreferenciaGrupo;
import dds.javatar.app.dto.usuario.Usuario;

@Entity
public class GrupoDeUsuarios {

	@Id
	@Column(name="grupo_id")
	@GeneratedValue
	private Long idGrupo;
	
	@Column(name="nombre")
	private String nombre;
	
	@ManyToOne
	private Componente componente;
	
	@ManyToMany(fetch = FetchType.LAZY, mappedBy="grupo")
	private List<PreferenciaGrupo> preferenciasAlimenticias;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "grupo_usuario",
	    joinColumns = @JoinColumn(name = "grupo_id"),
	    inverseJoinColumns = @JoinColumn(name = "usuario_id") )
	private Set<Usuario> miembros;

	/**** Constructor ****/
	public GrupoDeUsuarios() {
		this.preferenciasAlimenticias = new ArrayList<PreferenciaGrupo>();
		this.miembros = new HashSet<Usuario>();
	}

	/**** Setters y getters ****/

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<PreferenciaGrupo> getPreferenciasAlimenticias() {
		return this.preferenciasAlimenticias;
	}

	public void setPreferenciasAlimenticias(
			List<PreferenciaGrupo> preferenciasAlimenticias) {
		this.preferenciasAlimenticias = preferenciasAlimenticias;
	}

	public Set<Usuario> getUsuarios() {
		return this.miembros;
	}

	public void setUsuarios(Set<Usuario> usuarios) {
		this.miembros = usuarios;
	}

	public void agregarUsuario(Usuario usuario) {
		usuario.agregarGruposAlQuePertenece(this);
		this.miembros.add(usuario);
	}

}
