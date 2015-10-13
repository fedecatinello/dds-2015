package dds.javatar.app.dto.receta;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import dds.javatar.app.dto.grupodeusuarios.GrupoDeUsuarios;
import dds.javatar.app.dto.usuario.Usuario;

@Entity
@Table(name="Componente")
public class Componente {
	
	@Id
	@GeneratedValue
	@Column(name="componente_id")
	private Long id;
	
	@Column
	private String descripcion;
	
	@Column
	private BigDecimal cantidad;
	
	/** Ver si agregar dosis y unidades**/
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name ="ingrediente_receta",
	    joinColumns = @JoinColumn(name = "componente_id"),
	    inverseJoinColumns = @JoinColumn(name = "receta_id") )
	private Receta receta_x_ingrediente;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name ="condimento_receta",
	    joinColumns = @JoinColumn(name = "componente_id"),
	    inverseJoinColumns = @JoinColumn(name = "receta_id") )
	private Receta receta_x_condimento;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "usuario_preferencia",
	    joinColumns = @JoinColumn(name = "componente_id"),
	    inverseJoinColumns = @JoinColumn(name = "usuario_id") )
	private Usuario usuario;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "grupo_preferencia",
	    joinColumns = @JoinColumn(name = "componente_id"),
	    inverseJoinColumns = @JoinColumn(name = "grupo_id") )
	private GrupoDeUsuarios grupo;
	
	/** Constructor **/
	
	public Componente(String descripcion, BigDecimal cantidad) {
		this.descripcion = descripcion;
		this.cantidad = cantidad;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Receta getReceta_x_ingrediente() {
		return receta_x_ingrediente;
	}

	public void setReceta_x_ingrediente(Receta receta_x_ingrediente) {
		this.receta_x_ingrediente = receta_x_ingrediente;
	}

	public Receta getReceta_x_condimento() {
		return receta_x_condimento;
	}

	public void setReceta_x_condimento(Receta receta_x_condimento) {
		this.receta_x_condimento = receta_x_condimento;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public GrupoDeUsuarios getGrupo() {
		return grupo;
	}

	public void setGrupo(GrupoDeUsuarios grupo) {
		this.grupo = grupo;
	}

	public BigDecimal getCantidad() {
		return cantidad;
	}

	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

	
	
}
