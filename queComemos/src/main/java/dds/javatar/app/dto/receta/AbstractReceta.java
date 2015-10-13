package dds.javatar.app.dto.receta;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Receta")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_receta", discriminatorType = DiscriminatorType.STRING)
public abstract class AbstractReceta implements Receta {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
    @Column(name = "receta_id")
	 private Long id;
	
	@Basic(optional=false)
	@Column(length=100, nullable=false)
	protected String nombre;
	
	@Column
	protected Integer calorias;
	
	@Column
	protected String dificultad;
	
	@Column
	protected String temporada;
	
	@Column
	protected Integer tiempoPreparacion;
	
	@ManyToOne(fetch=FetchType.LAZY, optional=false)
	protected String autor;
	
	@Column
	protected Integer anioCreacion;
	
	@ManyToMany(mappedBy="receta_x_condimento")
	protected List<Componente> condimentos;
	
	@ManyToMany(mappedBy="receta_x_ingrediente")
	protected List<Componente> ingredientes;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "receta")
	protected List<Paso> pasosPreparacion;
	
	/** Getters & Setters **/
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Paso> getPasosPreparacion() {
		return this.pasosPreparacion;
	}

	public void agregarPasoPreparacion(Integer nroPaso,String preparacion) {
		Paso paso = new Paso(nroPaso, preparacion);
		this.pasosPreparacion.add(paso);
	}
	
	public Integer getCalorias() {
		return this.calorias;
	}

	public void setCalorias(Integer calorias) {
		this.calorias = calorias;
	}

	public String getDificultad() {
		return this.dificultad;
	}

	public void setDificultad(String dificultad) {
		this.dificultad = dificultad;
	}

	public String getTemporada() {
		return this.temporada;
	}

	public void setTemporada(String temporada) {
		this.temporada = temporada;
	}

	public void agregarCondimento(String condimento, BigDecimal cantidad) {
		Componente componente = new Componente(condimento,cantidad);
		this.condimentos.add(componente);
	}

	public List<Componente> getCondimentos() {
		return this.condimentos;
	}

	public void agregarIngrediente(String ingrediente, BigDecimal cantidad) {
		Componente componente = new Componente(ingrediente,cantidad);
		this.ingredientes.add(componente);
	}

	public List<Componente> getIngredientes() {
		return this.ingredientes;
	}

	public Integer getTiempoPreparacion() {
		return tiempoPreparacion;
	}

	public void setTiempoPreparacion(Integer tiempoPreparacion) {
		this.tiempoPreparacion = tiempoPreparacion;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public Integer getAnioCreacion() {
		return anioCreacion;
	}

	public void setAnioCreacion(Integer anioCreacion) {
		this.anioCreacion = anioCreacion;
	}

	public void setCondimentos(List<Componente> condimentos) {
		this.condimentos = condimentos;
	}

	public void setIngredientes(List<Componente> ingredientes) {
		this.ingredientes = ingredientes;
	}

	public void setPasosPreparacion(List<Paso> pasosPreparacion) {
		this.pasosPreparacion = pasosPreparacion;
	}
	
	
	public Boolean containsIngrediente(String ingrediente){
		Boolean tiene = false;
		for(Componente comp : this.ingredientes){
			if(comp.getDescripcion().equalsIgnoreCase(ingrediente)) tiene=true;
		}
		return tiene;
	}
	
	public Boolean containsCondimento(String condimento){
		Boolean tiene = false;
		for(Componente comp : this.ingredientes){
			if(comp.getDescripcion().equalsIgnoreCase(condimento)) tiene=true;
		}
		return tiene;
	}
	
	public Boolean alimentoSobrepasaCantidad(String alimento,
			BigDecimal cantidad) {
		this.getIngredientes();
		if (!this.containsIngrediente(alimento)) {
			return Boolean.FALSE;
		}
		return (this.getIngredienteByNombre(alimento).getCantidad().compareTo(cantidad) == 1);
	}
	
	public Componente getIngredienteByNombre(String nombre){
		for(Componente comp : this.getIngredientes()){
			if(comp.getDescripcion().equalsIgnoreCase(nombre)) return comp;
		}
		return null;
	}
}
