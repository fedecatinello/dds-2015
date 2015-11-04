package dds.javatar.app.domain.receta;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.despegar.integration.mongo.entities.IdentifiableEntity;

import dds.javatar.app.domain.receta.builder.RecetaBuilder;
import dds.javatar.app.domain.sistema.RepositorioRecetas;
import dds.javatar.app.domain.usuario.Usuario;
import dds.javatar.app.domain.usuario.condiciones.CondicionPreexistente;
import dds.javatar.app.util.exception.RecetaException;
import dds.javatar.app.util.exception.UsuarioException;

public class Receta implements IdentifiableEntity {

	protected String id;
	protected String nombre;
	protected Integer calorias;
	protected String dificultad;
	protected String temporada;
	protected Integer tiempoPreparacion;
	protected String autor;
	protected Integer anioCreacion;

	protected Map<String, BigDecimal> condimentos;
	protected Map<String, BigDecimal> ingredientes;
	protected Map<Integer, String> pasosPreparacion;
	protected Set<CondicionPreexistente> condiciones;

	public Receta(String nombre, Integer calorias, String dificultad, String temporada, Map<String, BigDecimal> ingredientes,
			Map<String, BigDecimal> condimentos, Map<Integer, String> pasosPreparacion) {
		this(nombre, null, calorias, dificultad, temporada, ingredientes, condimentos, pasosPreparacion);
	}

	public Receta(String nombre, String autor, Integer calorias, String dificultad, String temporada, Map<String, BigDecimal> ingredientes,
			Map<String, BigDecimal> condimentos, Map<Integer, String> pasosPreparacion) {
		this.nombre = nombre;
		this.autor = autor;
		this.calorias = calorias;
		this.dificultad = dificultad;
		this.temporada = temporada;
		this.condimentos = new HashMap<String, BigDecimal>(condimentos);
		this.ingredientes = new HashMap<String, BigDecimal>(ingredientes);
		this.pasosPreparacion = new HashMap<Integer, String>(pasosPreparacion);
	}

	public Receta() {
		this.condimentos = new HashMap<String, BigDecimal>(this.condimentos);
		this.ingredientes = new HashMap<String, BigDecimal>(this.ingredientes);
		this.pasosPreparacion = new HashMap<Integer, String>(this.pasosPreparacion);
	}

	/** Getters & Setters **/
	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Map<Integer, String> getPasosPreparacion() {
		return this.pasosPreparacion;
	}

	public void agregarPasoPreparacion(Integer nroPaso, String paso) {
		this.pasosPreparacion.put(nroPaso, paso);
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
		this.condimentos.put(condimento, cantidad);
	}

	public Map<String, BigDecimal> getCondimentos() {
		return this.condimentos;
	}

	public void agregarIngrediente(String ingrediente, BigDecimal cantidad) {
		this.ingredientes.put(ingrediente, cantidad);
	}

	public Map<String, BigDecimal> getIngredientes() {
		return this.ingredientes;
	}

	public Integer getTiempoPreparacion() {
		return this.tiempoPreparacion;
	}

	public void setTiempoPreparacion(Integer tiempoPreparacion) {
		this.tiempoPreparacion = tiempoPreparacion;
	}

	public String getAutor() {
		return this.autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public Integer getAnioCreacion() {
		return this.anioCreacion;
	}

	public void setAnioCreacion(Integer anioCreacion) {
		this.anioCreacion = anioCreacion;
	}

	public Set<CondicionPreexistente> getCondiciones() {
		return this.condiciones;
	}

	public void setCondiciones(Set<CondicionPreexistente> condiciones) {
		this.condiciones = condiciones;
	}

	public Boolean contieneIngrediente(String ingrediente) {
		return this.ingredientes.containsKey(ingrediente);
	}

	public Boolean contieneCondimento(String condimento) {
		return this.condimentos.containsKey(condimento);
	}

	public Boolean alimentoSobrepasaCantidad(String alimento, BigDecimal cantidad) {
		if (!this.ingredientes.containsKey(alimento)) {
			return Boolean.FALSE;
		}
		return (this.ingredientes.get(alimento).compareTo(cantidad) == 1);
	}

	public boolean esPrivada() {
		return StringUtils.isNotEmpty(this.autor);
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