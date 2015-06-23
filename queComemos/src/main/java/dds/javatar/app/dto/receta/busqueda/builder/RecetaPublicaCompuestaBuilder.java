package dds.javatar.app.dto.receta.busqueda.builder;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Map;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.receta.RecetaPrivada;
import dds.javatar.app.dto.receta.RecetaPrivadaCompuesta;

public class RecetaPublicaCompuestaBuilder implements StrategyBuilderReceta{

	private HashSet<RecetaPrivada> subRecetas;
	private Map<String, BigDecimal> condimentos;
	private Map<String, BigDecimal> ingredientes;
	private Map<Integer, String> pasosPreparacion;
	private String nombre;
	private String dificultad;
	private Integer calorias;
	private String temporada;
	private Integer tiempoPreparacion;

	/**** Setters y getters ****/
	public RecetaPublicaCompuestaBuilder subRecetas(HashSet<RecetaPrivada> subRecetas) {
		this.subRecetas = subRecetas;
		return this;
	}

	public RecetaPublicaCompuestaBuilder condimentos(Map<String, BigDecimal> condimentos) {
		this.condimentos = condimentos;
		return this;
	}

	public RecetaPublicaCompuestaBuilder ingredientes(Map<String, BigDecimal> ingredientes) {
		this.ingredientes = ingredientes;
		return this;
	}

	public RecetaPublicaCompuestaBuilder pasosPreparacion(Map<Integer, String> pasosPreparacion) {
		this.pasosPreparacion = pasosPreparacion;
		return this;
	}

	public RecetaPublicaCompuestaBuilder nombre(String nombre) {
		this.nombre = nombre;
		return this;
	}
	
	public RecetaPublicaCompuestaBuilder dificultad(String dificultad) {
		this.dificultad = dificultad;
		return this;
	}

	public RecetaPublicaCompuestaBuilder calorias(Integer calorias) {
		this.calorias = calorias;
		return this;
	}

	public RecetaPublicaCompuestaBuilder temporada(String temporada) {
		this.temporada = temporada;
		return this;
	}

	public RecetaPublicaCompuestaBuilder tiempoPreparacion(Integer tiempoPreparacion) {
		this.tiempoPreparacion = tiempoPreparacion;
		return this;
	}


	@Override
	public Receta buildReceta() {
		return new RecetaPrivadaCompuesta.RecetaPrivadaCompuestaBuilder().nombre(this.nombre).subRecetas(this.subRecetas).condimentos(this.condimentos).ingredientes(this.ingredientes).pasosPreparacion(this.pasosPreparacion).dificultad(this.dificultad).calorias(this.calorias).temporada(this.temporada).tiempoPreparacion(this.tiempoPreparacion).build();

	}

}
