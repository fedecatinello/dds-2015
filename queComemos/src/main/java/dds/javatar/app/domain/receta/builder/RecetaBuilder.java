package dds.javatar.app.domain.receta.builder;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import dds.javatar.app.domain.receta.Receta;
import dds.javatar.app.domain.receta.RecetaCompuesta;
import dds.javatar.app.util.exception.RecetaException;


    public class RecetaBuilder {

    	private String nombre;
    	private String dificultad;
    	private Integer calorias;
    	private String temporada;
    	private Integer tiempoPreparacion;
    	private String autor;
    	private Integer anioCreacion;
        private Map<String, BigDecimal> condimentos;
        private Map<String, BigDecimal> ingredientes;
        private Map<Integer, String> pasosPreparacion;
    	private Set<Receta> subrecetas;

    	public RecetaBuilder(String nombre) {
    		this.nombre = nombre;
    		this.initializeComparatorFields();
    		this.condimentos = new HashMap<>();
    		this.ingredientes =new HashMap<>();
    		this.pasosPreparacion = new HashMap<>();
    		this.subrecetas = new HashSet<>();
    	}

    	public void initializeComparatorFields() {
    		/** To avoid null pointer exception in comparisons **/
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
    	
    	public RecetaBuilder agregarCondimentos(Map<String, BigDecimal> condimentos) {
    		this.condimentos.putAll(condimentos);
    		return this;
    	}

    	public RecetaBuilder agregarIngrediente(String ingrediente, BigDecimal cantidad) {
    		this.ingredientes.put(ingrediente, cantidad);
    		return this;
    	}
    	
    	public RecetaBuilder agregarIngredientes(Map<String, BigDecimal> ingredientes) {
    		this.ingredientes.putAll(ingredientes);
    		return this;
    	}

    	public RecetaBuilder agregarPaso(Integer nroPaso, String pasoPreparacion) {
    		this.pasosPreparacion.put(nroPaso, pasoPreparacion);
    		return this;
    	}
    	
    	public RecetaBuilder agregarPasos(Map<Integer, String> pasosPreparacion) {
    		this.pasosPreparacion.putAll(pasosPreparacion);
    		return this;
    	}
    	
    	public RecetaBuilder agregarSubReceta(Receta subReceta) throws RecetaException {
    		subReceta.validar();
    		this.subrecetas.add(subReceta);
    		return this;
    	}
    	
    	public RecetaBuilder agregarSubRecetas(Set<Receta> subRecetas) throws RecetaException {
    		
    		this.validarSubrecetas(subRecetas);
    		this.subrecetas.addAll(subRecetas);
    		return this;
    	}

    	/** Util **/
    	public void validarSubrecetas(Set<Receta> subRecetas) throws RecetaException {

    		Iterator<Receta> iteratorRecetas = subRecetas.iterator();

    		while (iteratorRecetas.hasNext()) {
    			Receta receta = iteratorRecetas.next();
    			receta.validar();
    		}
    	}


    	public Receta buildReceta() {
    		if (this.esCompuesta()) {
    			return new RecetaCompuesta(this.nombre, this.autor, this.calorias, this.dificultad, this.temporada, this.ingredientes, this.condimentos,
    					this.pasosPreparacion, this.subrecetas);
    		} else {
    			return new Receta(this.nombre, this.autor, this.calorias, this.dificultad, this.temporada, this.ingredientes, this.condimentos,
    					this.pasosPreparacion);
    		}
    	}

    	public Boolean esCompuesta() {
    		return (!this.subrecetas.isEmpty());
    	}

    }