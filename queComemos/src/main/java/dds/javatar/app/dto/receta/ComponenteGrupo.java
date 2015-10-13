package dds.javatar.app.dto.receta;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import dds.javatar.app.dto.grupodeusuarios.GrupoDeUsuarios;

@Entity
@Table(name="ComponenteGrupo")
public class ComponenteGrupo {
	

	@ManyToOne
	@JoinColumn(name="componente_id")
	protected Componente componente;
	
	@ManyToOne
	@JoinColumn(name="grupo_id")
	protected GrupoDeUsuarios grupo;
	
	@Column(name="gusta")
	protected Boolean gusta;
	
	public ComponenteGrupo(){
		
	}
}