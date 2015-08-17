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
    private HashMap<String, BigDecimal> condimentos;
    private HashMap<String, BigDecimal> ingredientes;
    private HashMap<Integer, String> pasosPreparacion;
    private HashSet<Receta> subrecetas;
    
	
	public RecetaBuilder (String nombre) {
		this.nombre = nombre;
	}

	public RecetaBuilder dificultad(String dificultad) {
		this.dificultad = dificultad;
		return this;
	}

	public RecetaBuilder totalCalorias(Integer calorias) {
		this.calorias = calorias ;
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

	public RecetaBuilder agregarCondimentos(String condimento, BigDecimal cantidad) {
		this.condimentos.put(condimento, cantidad);
		return this;
	}

	public RecetaBuilder agregarIngrediente(String ingrediente, BigDecimal cantidad) {
		this.ingredientes.put(ingrediente, cantidad);
		return this;
	}

	public RecetaBuilder agregarPaso(Integer nroPaso, String pasoPreparacion) {
		this.pasosPreparacion.put(nroPaso, pasoPreparacion);
		return this;
	}
	
	public RecetaBuilder agregarSubReceta(Receta subReceta) throws RecetaException {
		this.subrecetas.add(subReceta);
		return this;
	}
	
	public Receta buildReceta() {
		
		if(this.autor.isEmpty()) { /** Receta Publica **/			
			
			if(esCompuesta())
				return new RecetaPublicaCompuesta();
			else
				return new RecetaPublicaSimple();
		}	
		else {                    /** Receta Privada **/
			
			if(esCompuesta())
				return new RecetaPrivadaCompuesta();
			else
				return new RecetaPrivadaSimple();
		}
			
			
	}
	
	public Boolean esCompuesta() {
		
		if(this.subrecetas.isEmpty()) 
			return true;
		else
			return false;
	}

}
