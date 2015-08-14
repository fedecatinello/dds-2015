package dds.javatar.app.dto.receta.builder;

import java.math.BigDecimal;
import java.util.Map;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.receta.RecetaPrivada;
import dds.javatar.app.dto.receta.RecetaPrivadaCompuesta;
import dds.javatar.app.util.exception.RecetaException;

public abstract class RecetaBuilder {

	private String nombre;
	private String temporada;
	private Integer calorias;
	private String dificultad;
	private Integer tiempoPreparacion;
	
	private Receta receta;
	
	public RecetaBuilder nombre(String nombre) {
		this.nombre = nombre;
		return this;
	}

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

	public RecetaBuilder buildReceta() {
		return recetaPrivada;
	}
}
