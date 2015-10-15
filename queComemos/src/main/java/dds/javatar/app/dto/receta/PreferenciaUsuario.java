package dds.javatar.app.dto.receta;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import dds.javatar.app.dto.usuario.Usuario;

@Entity
@Table(name = "PreferenciaUsuario")
public class PreferenciaUsuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "componente_id")
	private Componente componente;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;

	@Column(name = "gusta")
	private Boolean gusta;

	public PreferenciaUsuario() {

	}

	public PreferenciaUsuario(Usuario usuario, Componente componente, Boolean gusta) {
		this.usuario = usuario;
		this.componente = componente;
		this.gusta = gusta;
	}

	public Componente getComponente() {
		return this.componente;
	}

	public void setComponente(Componente componente) {
		this.componente = componente;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Boolean getGusta() {
		return this.gusta;
	}

	public void setGusta(Boolean gusta) {
		this.gusta = gusta;
	}

}