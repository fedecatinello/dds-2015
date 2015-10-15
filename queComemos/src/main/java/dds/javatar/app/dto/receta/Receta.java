package dds.javatar.app.dto.receta;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

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

import dds.javatar.app.dto.receta.builder.RecetaBuilder;
import dds.javatar.app.dto.sistema.RepositorioRecetas;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.util.exception.RecetaException;
import dds.javatar.app.util.exception.UsuarioException;

@Entity
@Table(name = "Receta")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_receta", discriminatorType = DiscriminatorType.STRING)
public class Receta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "receta_id")
	private Long id;

	@Basic(optional = false)
	@Column(length = 100, nullable = false)
	protected String nombre;

	@Column
	protected Integer calorias;

	@Column
	protected String dificultad;

	@Column
	protected String temporada;

	@Column
	protected Integer tiempoPreparacion;

	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	protected Usuario autor;

	@Column
	protected Integer anioCreacion;

	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "receta_x_condimento")
	protected Set<Componente> condimentos;

	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "receta_x_ingrediente")
	protected Set<Componente> ingredientes;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "receta")
	protected Set<Paso> pasosPreparacion;

	public Receta() {

	}

	public Receta(String nombre, Integer calorias, String dificultad, String temporada, Set<Componente> ingredientes, Set<Componente> condimentos,
			Set<Paso> pasosPreparacion) {
		this(nombre, null, calorias, dificultad, temporada, ingredientes, condimentos, pasosPreparacion);
	}

	public Receta(String nombre, Usuario autor, Integer calorias, String dificultad, String temporada, Set<Componente> ingredientes,
			Set<Componente> condimentos, Set<Paso> pasosPreparacion) {
		this.nombre = nombre;
		this.autor = autor;
		this.calorias = calorias;
		this.dificultad = dificultad;
		this.temporada = temporada;
		this.ingredientes = new HashSet<Componente>(ingredientes);
		this.condimentos = new HashSet<Componente>(condimentos);
		this.pasosPreparacion = new HashSet<Paso>();
		this.pasosPreparacion.addAll(pasosPreparacion);
	}

	/** Getters & Setters **/
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set<Paso> getPasosPreparacion() {
		return this.pasosPreparacion;
	}

	public void agregarPasoPreparacion(Integer nroPaso, String preparacion) {
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
		Componente componente = new Componente(condimento, cantidad);
		this.condimentos.add(componente);
	}

	public Set<Componente> getCondimentos() {
		return this.condimentos;
	}

	public void agregarIngrediente(String ingrediente, BigDecimal cantidad) {
		Componente componente = new Componente(ingrediente, cantidad);
		this.ingredientes.add(componente);
	}

	public Set<Componente> getIngredientes() {
		return this.ingredientes;
	}

	public Integer getTiempoPreparacion() {
		return this.tiempoPreparacion;
	}

	public void setTiempoPreparacion(Integer tiempoPreparacion) {
		this.tiempoPreparacion = tiempoPreparacion;
	}

	public Usuario getAutor() {
		return this.autor;
	}

	public void setAutor(Usuario autor) {
		this.autor = autor;
	}

	public Integer getAnioCreacion() {
		return this.anioCreacion;
	}

	public void setAnioCreacion(Integer anioCreacion) {
		this.anioCreacion = anioCreacion;
	}

	public void setCondimentos(Set<Componente> condimentos) {
		this.condimentos = condimentos;
	}

	public void setIngredientes(Set<Componente> ingredientes) {
		this.ingredientes = ingredientes;
	}

	public void setPasosPreparacion(Set<Paso> pasosPreparacion) {
		this.pasosPreparacion = pasosPreparacion;
	}

	public Boolean contieneIngrediente(String ingrediente) {
		Boolean tiene = false;
		for (Componente comp : this.ingredientes) {
			if (comp.getDescripcion().equalsIgnoreCase(ingrediente))
				tiene = true;
		}
		return tiene;
	}

	public Boolean contieneCondimento(String condimento) {
		Boolean tiene = false;
		for (Componente comp : this.ingredientes) {
			if (comp.getDescripcion().equalsIgnoreCase(condimento))
				tiene = true;
		}
		return tiene;
	}

	public Boolean alimentoSobrepasaCantidad(String alimento, BigDecimal cantidad) {
		if (!this.contieneIngrediente(alimento)) {
			return Boolean.FALSE;
		}
		return (this.getIngredienteByNombre(alimento).getCantidad().compareTo(cantidad) == 1);
	}

	public Componente getIngredienteByNombre(String nombre) {
		for (Componente comp : this.getIngredientes()) {
			if (comp.getDescripcion().equalsIgnoreCase(nombre))
				return comp;
		}
		return null;
	}

	public boolean esPrivada() {
		return this.autor != null;
	}

	public Receta privatizarSiCorresponde(Usuario user) throws UsuarioException, RecetaException {
		if (this.esPrivada()) {
			return this;
		} else {
			return this.hacerPrivada(user.getNombre());
		}
	}

	public Boolean chequearVisibilidad(Receta receta, Usuario usuario) {
		if (usuario.getRecetas().contains(receta)) {
			return true;
		}
		return false;
	}

	public void validar() throws RecetaException {
		if (this.ingredientes.isEmpty()) {
			throw new RecetaException("La receta no es valida ya que no tiene ingredientes!");
		}
		if (this.calorias.intValue() < 10 || this.calorias.intValue() > 5000) {
			throw new RecetaException("La receta no es valida por su cantidad de calorias!");
		}
	}

	public void agregarRecetaAlRepo(Receta receta) {
		RepositorioRecetas.getInstance().add(receta);
	}

	public Receta hacerPrivada(String autor) throws RecetaException {
		Receta recetaClonada = new RecetaBuilder(this.nombre)
			.totalCalorias(this.calorias)
			.dificultad(this.dificultad)
			.temporada(this.temporada)
			.agregarIngredientes(this.ingredientes)
			.agregarCondimentos(this.condimentos)
			.agregarPasos(this.pasosPreparacion)
			.inventadaPor(autor)
			.buildReceta();

		return recetaClonada;
	}

}
