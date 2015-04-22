package dds.javatar.app.dto.receta;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.util.BusinessException;

public class Receta {

	private String nombre;
	private String preparacion;
	private Integer calorias;
	private String dificultad;
	private String temporada;
	private Usuario autor;

	private Map<String, BigDecimal> ingredientes;
	private Map<String, BigDecimal> condimentos;
	private Set<Receta> subRecetas;

	// Builders
	public Receta() {
		this.ingredientes = new HashMap<String, BigDecimal>();
		this.condimentos = new HashMap<String, BigDecimal>();
		this.subRecetas = new HashSet<Receta>();
	}

	public Receta(Integer calorias) {
		this();
		this.calorias = calorias;
	}

	// Setters & Getters

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPreparacion() {
		return this.preparacion;
	}

	public void setPreparacion(String preparacion) {
		this.preparacion = preparacion;
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

	public Set<Receta> getSubRecetas() {
		return this.subRecetas;
	}

	public Usuario getAutor() {
		return this.autor;
	}

	public void setAutor(Usuario autor) {
		this.autor = autor;
	}

	// Metodos

	public void validar() throws BusinessException {
		if (this.ingredientes.isEmpty()) {
			throw new BusinessException("La receta no es valida ya que no tiene ingredientes!");
		}
		if (this.calorias < 10 || this.calorias > 5000) {
			throw new BusinessException("La receta no es valida por su cantidad de calorias!");
		}
	}

	public void agregarIngrediente(String ingrediente, BigDecimal cantidad) {
		this.ingredientes.put(ingrediente, cantidad);
	}

	public void agregarSubReceta(Receta subReceta) {
		this.subRecetas.add(subReceta);
	}

	public Boolean contieneIngrediente(String ingrediente) {
		// TODO: habria que chequear en las subrecetas?
		return this.ingredientes.containsKey(ingrediente);
	}

	public Boolean contieneCondimento(String condimento) {
		// TODO: habria que chequear en las subrecetas?
		return this.condimentos.containsKey(condimento);
	}

	public Boolean alimentoSobrepasaCantidad(String alimento, BigDecimal cantidad) {
		// TODO: habria que chequear en las subrecetas?

		if (!this.ingredientes.containsKey(alimento)) {
			return Boolean.FALSE;
		}

		return (this.ingredientes.get(alimento).compareTo(cantidad) == 1);
	}

}
