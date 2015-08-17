package dds.javatar.app.dto.receta.builder;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.receta.RecetaPrivadaCompuesta;
import dds.javatar.app.dto.receta.RecetaPrivadaSimple;
import dds.javatar.app.dto.receta.RecetaPublicaCompuesta;
import dds.javatar.app.dto.receta.RecetaPublicaSimple;
import dds.javatar.app.util.exception.RecetaException;

public class RecetaBuilder {

	private String nombre;
    private String dificultad;
    private Integer calorias;
    private String temporada;
    private Integer tiempoPreparacion;
    private String autor;
    private Integer anioCreacion;
    private HashMap<String, BigDecimal> condimentos;
    private HashMap<String, BigDecimal> ingredientes;
    private HashMap<Integer, String> pasosPreparacion;
    private HashSet<Receta> subrecetas;
    
	
	public RecetaBuilder (String nombre) {
		this.nombre = nombre;
		this.autor = new String();
		this.condimentos = new HashMap<String, BigDecimal>();
		this.ingredientes = new HashMap<String, BigDecimal>();
		this.pasosPreparacion = new HashMap<Integer, String>();
		this.subrecetas = new HashSet<Receta>();
	}

	/** Setters & Getters **/
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = new String(nombre);
	}

	public String getDificultad() {
		return dificultad;
	}

	public void setDificultad(String dificultad) {
		this.dificultad = new String(dificultad);
	}

	public Integer getCalorias() {
		return calorias;
	}

	public void setCalorias(Integer calorias) {
		this.calorias = new Integer(calorias.intValue());
	}

	public String getTemporada() {
		return temporada;
	}

	public void setTemporada(String temporada) {
		this.temporada = new String(temporada);
	}

	public Integer getTiempoPreparacion() {
		return tiempoPreparacion;
	}

	public void setTiempoPreparacion(Integer tiempoPreparacion) {
		this.tiempoPreparacion = new Integer(tiempoPreparacion.intValue());
	}

	public Integer getAnioCreacion() {
		return anioCreacion;
	}

	public void setAnioCreacion(Integer anioCreacion) {
		this.anioCreacion = new Integer(anioCreacion.intValue());
	}
	
	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = new String(autor);
	}

	
	/** Builder methods **/ 
	public RecetaBuilder dificultad(String dificultad) {
		this.setDificultad(dificultad);
		return this;
	}

	public RecetaBuilder totalCalorias(Integer calorias) {
		this.setCalorias(calorias);
		return this;
	}

	public RecetaBuilder temporada(String temporada) {
		this.setTemporada(temporada);
		return this;
	}

	public RecetaBuilder tiempoPreparacion(Integer tiempoPreparacion) {
		this.setTiempoPreparacion(tiempoPreparacion);
		return this;
	}
	
	public RecetaBuilder inventadaPor(String autor) {
		this.setAutor(autor);
		return this;
	}
	
	public RecetaBuilder inventadaEn(Integer anio) {
		this.setAnioCreacion(anio);
		return this;
	}

	public RecetaBuilder agregarCondimento(String condimento, BigDecimal cantidad) {
		this.condimentos.put(condimento, cantidad);
		return this;
	}
	
	public RecetaBuilder agregarCondimentos(HashMap<String, BigDecimal> condimentos) {
		this.condimentos.putAll(condimentos);
		return this;
	}

	public RecetaBuilder agregarIngrediente(String ingrediente, BigDecimal cantidad) {
		this.ingredientes.put(ingrediente, cantidad);
		return this;
	}
	
	public RecetaBuilder agregarIngredientes(HashMap<String, BigDecimal> ingredientes) {
		this.ingredientes.putAll(ingredientes);
		return this;
	}

	public RecetaBuilder agregarPaso(Integer nroPaso, String pasoPreparacion) {
		this.pasosPreparacion.put(nroPaso, pasoPreparacion);
		return this;
	}
	
	public RecetaBuilder agregarPasos(HashMap<Integer, String> pasosPreparacion) {
		this.pasosPreparacion.putAll(pasosPreparacion);
		return this;
	}
	
	public RecetaBuilder agregarSubReceta(Receta subReceta) throws RecetaException {
		this.subrecetas.add(subReceta);
		return this;
	}
	
	public RecetaBuilder agregarSubRecetas(HashSet<Receta> subRecetas) throws RecetaException {
		this.subrecetas.addAll(subRecetas);
		return this;
	}
	
	public Receta buildReceta() {
		
		if(this.autor.isEmpty()) { /** Receta Publica **/			
			
			if(esCompuesta())
				return new RecetaPublicaCompuesta(this.subrecetas);
			else
				return new RecetaPublicaSimple(this.ingredientes, this.condimentos,this.pasosPreparacion);
		}	
		else {                    /** Receta Privada **/
			
			if(esCompuesta())
				return new RecetaPrivadaCompuesta(this.condimentos, this.ingredientes, this.pasosPreparacion, this.subrecetas);
			else
				return new RecetaPrivadaSimple(this.ingredientes, this.condimentos, this.pasosPreparacion);
		}
			
			
	}
	
	public Boolean esCompuesta() {
		
		return (!this.subrecetas.isEmpty());
	}

}
