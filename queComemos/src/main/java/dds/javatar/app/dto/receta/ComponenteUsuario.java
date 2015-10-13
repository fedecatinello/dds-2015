package dds.javatar.app.dto.receta;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import dds.javatar.app.dto.usuario.Usuario;

@Entity
@Table(name="ComponenteUsuario")
public class ComponenteUsuario {
	

	@ManyToOne
	@JoinColumn(name="componente_id")
	protected Componente componente;
	
	@ManyToOne
	@JoinColumn(name="usuario_id")
	protected Usuario usuario;
	
	@Column(name="gusta")
	protected Boolean gusta;
	
	public ComponenteUsuario(){
		
	}
}