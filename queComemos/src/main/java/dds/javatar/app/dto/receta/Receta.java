package dds.javatar.app.dto.receta;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

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
	private Collection<Receta> subrecetas;

	// Builders
	public Receta() {
		this.ingredientes = new HashMap<String, BigDecimal>();
		this.condimentos = new HashMap<String, BigDecimal>();
		this.subrecetas = new HashSet<Receta>();
	}

	public Receta(Integer calorias) {
		this();
		this.calorias = calorias;
	}

	// Setters & Getters

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPreparacion() {
		return preparacion;
	}

	public void setPreparacion(String preparacion) {
		this.preparacion = preparacion;
	}

	public Integer getCalorias() {
		return calorias;
	}

	public void setCalorias(Integer calorias) {
		this.calorias = calorias;
	}

	public String getDificultad() {
		return dificultad;
	}

	public void setDificultad(String dificultad) {
		this.dificultad = dificultad;
	}

	public String getTemporada() {
		return temporada;
	}

	public void setTemporada(String temporada) {
		this.temporada = temporada;
	}

	public Map<String, BigDecimal> getIngredientes() {
		return ingredientes;
	}

	public void setIngredientes(Map<String, BigDecimal> ingredientes) {
		this.ingredientes = ingredientes;
	}

	public Map<String, BigDecimal> getCondimentos() {
		return condimentos;
	}

	public void setCondimentos(Map<String, BigDecimal> condimentos) {
		this.condimentos = condimentos;
	}

	public Collection<Receta> getSubrecetas() {
		return subrecetas;
	}

	public void setSubrecetas(Collection<Receta> subrecetas) {
		this.subrecetas = subrecetas;
	}
	public Usuario getAutor() {
		return autor;
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
		
		return (this.ingredientes.get(alimento).compareTo(new BigDecimal(100)) == 1);
	}

}
