package dds.javatar.app.dto.receta;

import javax.persistence.*;

@Entity
@Table(name="PasoPreparacion")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class Paso {

	@Id
	@GeneratedValue
	@Column(name="paso_id")
	private Long id;
	
	@Basic(optional=false)
	@Column
	private Integer orden;
	
	@Basic(optional=false)
	@Column
	private String descripcion;
	
	@ManyToOne
	@JoinColumn(name="receta_id")
	private Receta receta;

	/** Getters & Setters **/
	
	public Paso(Integer orden, String descripcion) {
		this.orden = orden;
		this.descripcion = descripcion;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
}
