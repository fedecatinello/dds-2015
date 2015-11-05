package dds.javatar.app.dto.receta.builder;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

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
		initializeComparatorFields();
		this.condimentos = new HashMap<String, BigDecimal>();
		this.ingredientes = new HashMap<String, BigDecimal>();
		this.pasosPreparacion = new HashMap<Integer, String>();
		this.subrecetas = new HashSet<Receta>();
	}
	
	public void initializeComparatorFields() {  /** To avoid null pointer exception in comparisons **/
		this.autor = new String();
		this.calorias = 0;
	}

	
	/** Builder methods **/ 
	public RecetaBuilder dificultad(String dificultad) {
		this.dificultad = dificultad;
		return this;
	}

	public RecetaBuilder totalCalorias(Integer calorias) {
		this.calorias = calorias;
		return this;
	}

	public RecetaBuilder temporada(String temporada) {
		this.temporada = temporada;
		return this;
	}

	public RecetaBuilder tiempoPreparacion(Integer tiempoPreparacion) {
		this.tiempoPreparacion = tiempoPreparacion;
		return this;
	}
	
	public RecetaBuilder inventadaPor(String autor) {
		this.autor = autor;
		return this;
	}
	
	public RecetaBuilder inventadaEn(Integer anio) {
		this.anioCreacion = anio;
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
		subReceta.validarSiLaRecetaEsValida();
		this.subrecetas.add(subReceta);
		return this;
	}
	
	public RecetaBuilder agregarSubRecetas(HashSet<Receta> subRecetas) throws RecetaException {
		
		validarSubrecetas(subRecetas);
		this.subrecetas.addAll(subRecetas);
		return this;
	}
	
	/** Util **/
	public void validarSubrecetas(HashSet<Receta> subRecetas) throws RecetaException {
		
		Iterator<Receta> iteratorRecetas = subRecetas.iterator();
		
		while(iteratorRecetas.hasNext()) {
			Receta receta = iteratorRecetas.next();
			receta.validarSiLaRecetaEsValida();
		}
		
	}
	
	public Receta buildReceta() {
		
		if(this.autor.isEmpty()) { /** Receta Publica **/			
			
			if(esCompuesta())
				return new RecetaPublicaCompuesta(
									this.nombre, 
									this.calorias, 
									this.dificultad, 
									this.temporada, 
									this.ingredientes, 
									this.condimentos, 
									this.pasosPreparacion,
									this.subrecetas);
			else
				return new RecetaPublicaSimple(
									this.nombre, 
									this.calorias, 
									this.dificultad, 
									this.temporada, 
									this.ingredientes, 
									this.condimentos, 
									this.pasosPreparacion);
		}	
		else {                    /** Receta Privada **/
			
			if(esCompuesta())
				return new RecetaPrivadaCompuesta(
									this.nombre, 
									this.autor, 
									this.calorias, 
									this.dificultad, 
									this.temporada, 
									this.condimentos, 
									this.ingredientes, 
									this.pasosPreparacion, 
									this.subrecetas);
			else
				return new RecetaPrivadaSimple(
									this.nombre, 
									this.autor, 
									this.calorias, 
									this.dificultad, 
									this.temporada, 
									this.ingredientes, 
									this.condimentos, 
									this.pasosPreparacion);
		}
			
			
	}
	
	public Boolean esCompuesta() {
		
		return (!this.subrecetas.isEmpty());
	}

}
