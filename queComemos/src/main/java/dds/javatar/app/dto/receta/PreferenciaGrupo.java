package dds.javatar.app.dto.receta;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import dds.javatar.app.dto.grupodeusuarios.GrupoDeUsuarios;

@Entity
@Table(name = "PreferenciaGrupo")
public class PreferenciaGrupo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "componente_id")
	protected Componente componente;

	@ManyToOne
	@JoinColumn(name = "grupo_id")
	protected GrupoDeUsuarios grupo;

	@Column(name = "gusta")
	protected Boolean gusta;

	public PreferenciaGrupo() {

	}

	public PreferenciaGrupo(GrupoDeUsuarios grupo, Componente componente, Boolean gusta) {
		this.grupo = grupo;
		this.componente = componente;
		this.gusta = gusta;
	}

	public Componente getComponente() {
		return this.componente;
	}

	public void setComponente(Componente componente) {
		this.componente = componente;
	}

	public GrupoDeUsuarios getGrupo() {
		return this.grupo;
	}

	public void setGrupo(GrupoDeUsuarios grupo) {
		this.grupo = grupo;
	}

	public Boolean getGusta() {
		return this.gusta;
	}

	public void setGusta(Boolean gusta) {
		this.gusta = gusta;
	}

}