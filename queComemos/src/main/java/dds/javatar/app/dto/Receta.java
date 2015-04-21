package dds.javatar.app.dto;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Receta {
	
	private String nombre;
	private String preparacion;
	private Integer calorias;
	private String dificultad;
	private String temporada;
	
	private Map<String, BigDecimal> ingredientes;
	private Map<String, BigDecimal> condimentos;
	private Set<CondicionPreexistente> condicionesPreexistentes;
	private Collection<Receta> subrecetas;
	
	
	// Builders
	public Receta() {
		this.condicionesPreexistentes = new HashSet<CondicionPreexistente>();
		this.ingredientes = new HashMap<String, BigDecimal>();
		this.condimentos = new HashMap<String, BigDecimal>();
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
	public Set<CondicionPreexistente> getCondicionesPreexistentes() {
		return condicionesPreexistentes;
	}
	public void setCondicionesPreexistentes(
			Set<CondicionPreexistente> condicionesPreexistentes) {
		this.condicionesPreexistentes = condicionesPreexistentes;
	}
	public Collection<Receta> getSubrecetas() {
		return subrecetas;
	}
	public void setSubrecetas(Collection<Receta> subrecetas) {
		this.subrecetas = subrecetas;
	}
	
	
	
	
}
