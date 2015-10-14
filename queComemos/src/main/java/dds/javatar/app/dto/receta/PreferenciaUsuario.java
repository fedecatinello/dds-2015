package dds.javatar.app.dto.receta;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import dds.javatar.app.dto.usuario.Usuario;

@Entity
@Table(name="PreferenciaUsuario")
public class PreferenciaUsuario {
	

	@ManyToOne
	@JoinColumn(name="componente_id")
	protected Componente componente;
	
	@ManyToOne
	@JoinColumn(name="usuario_id")
	protected Usuario usuario;
	
	@Column(name="gusta")
	protected Boolean gusta;
	
	public PreferenciaUsuario(){
		
	}

	public PreferenciaUsuario(Usuario usuario, Componente componente, Boolean gusta){
		this.usuario = usuario;
		this.componente = componente;
		this.gusta = gusta;
	}
	
	public Componente getComponente() {
		return componente;
	}

	public void setComponente(Componente componente) {
		this.componente = componente;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Boolean getGusta() {
		return gusta;
	}

	public void setGusta(Boolean gusta) {
		this.gusta = gusta;
	}
	
	
	
}