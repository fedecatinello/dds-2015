package dds.javatar.app.dto.receta;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Componente")
public class Componente {

	@Id
	@GeneratedValue
	@Column(name = "componente_id")
	private Long id;

	@Column
	private String descripcion;

	@Column
	private BigDecimal cantidad;

	/** Ver si agregar dosis y unidades **/

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "ingrediente_receta", joinColumns = @JoinColumn(name = "componente_id"), inverseJoinColumns = @JoinColumn(name = "receta_id"))
	private List<Receta> receta_x_ingrediente;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "condimento_receta", joinColumns = @JoinColumn(name = "componente_id"), inverseJoinColumns = @JoinColumn(name = "receta_id"))
	private List<Receta> receta_x_condimento;

	@OneToMany(targetEntity = PreferenciaUsuario.class, fetch = FetchType.LAZY, mappedBy = "componente")
	private List<PreferenciaUsuario> usuariosQuienesGustan;

	@OneToMany(targetEntity = PreferenciaGrupo.class, fetch = FetchType.LAZY, mappedBy = "componente")
	private List<PreferenciaGrupo> gruposQuienesGustan;

	/** Constructor **/

	public Componente(String descripcion, BigDecimal cantidad) {
		this.descripcion = descripcion;
		this.cantidad = cantidad;
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return this.descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<Receta> getReceta_x_ingrediente() {
		return this.receta_x_ingrediente;
	}

	public void setReceta_x_ingrediente(List<Receta> receta_x_ingrediente) {
		this.receta_x_ingrediente = receta_x_ingrediente;
	}

	public List<Receta> getReceta_x_condimento() {
		return this.receta_x_condimento;
	}

	public void setReceta_x_condimento(List<Receta> receta_x_condimento) {
		this.receta_x_condimento = receta_x_condimento;
	}

	public List<PreferenciaUsuario> getUsuariosQuienesGustan() {
		return this.usuariosQuienesGustan;
	}

	public void setUsuariosQuienesGustan(List<PreferenciaUsuario> usuariosQuienesGustan) {
		this.usuariosQuienesGustan = usuariosQuienesGustan;
	}

	public List<PreferenciaGrupo> getGruposQuienesGustan() {
		return this.gruposQuienesGustan;
	}

	public void setGruposQuienesGustan(List<PreferenciaGrupo> gruposQuienesGustan) {
		this.gruposQuienesGustan = gruposQuienesGustan;
	}

	public BigDecimal getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

}
