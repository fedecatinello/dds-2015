package dds.javatar.app.dto.receta.builder;

import java.math.BigDecimal;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.receta.RecetaPrivada;
import dds.javatar.app.dto.receta.RecetaPrivadaCompuesta;
import dds.javatar.app.util.exception.RecetaException;

public class RecetaPrivadaCompuestaBuilder {

	private RecetaPrivadaCompuesta recetaPrivadaCompuesta;

	public RecetaPrivadaCompuestaBuilder(String nombre) {
		recetaPrivadaCompuesta = new RecetaPrivadaCompuesta();
		recetaPrivadaCompuesta.setNombre(nombre);
	}

	public RecetaPrivadaCompuestaBuilder inventadaPor(String autor) {
		recetaPrivadaCompuesta.setAutor(autor);
		return this;
	}

	public RecetaPrivadaCompuestaBuilder dificultad(String dificultad) {
		recetaPrivadaCompuesta.setDificultad(dificultad);
		return this;
	}

	public RecetaPrivadaCompuestaBuilder totalCalorias(Integer calorias) {
		recetaPrivadaCompuesta.setCalorias(calorias);
		return this;
	}

	public RecetaPrivadaCompuestaBuilder temporada(String temporada) {
		recetaPrivadaCompuesta.setTemporada(temporada);
		return this;
	}

	public RecetaPrivadaCompuestaBuilder agregarSubReceta(
			RecetaPrivada subReceta) throws RecetaException {
		recetaPrivadaCompuesta.agregarSubReceta(subReceta);
		return this;
	}

	public RecetaPrivadaCompuestaBuilder agregarCondimentos(String condimento,
			BigDecimal cantidad) {
		recetaPrivadaCompuesta.agregarCondimento(condimento, cantidad);
		return this;
	}

	public RecetaPrivadaCompuestaBuilder agregarIngrediente(String ingrediente,
			BigDecimal cantidad) {
		recetaPrivadaCompuesta.agregarIngrediente(ingrediente, cantidad);
		return this;
	}

	public RecetaPrivadaCompuestaBuilder agregarPaso(Integer nroPaso,
			String pasoPreparacion) {
		recetaPrivadaCompuesta.agregarPasoPreparacion(nroPaso, pasoPreparacion);
		return this;
	}

	public RecetaPrivadaCompuestaBuilder tiempoPreparacion(
			Integer tiempoPreparacion) {
		recetaPrivadaCompuesta.setTiempoPreparacion(tiempoPreparacion);
		return this;
	}

	public RecetaPrivadaCompuesta buildReceta() {
		return recetaPrivadaCompuesta;
	}

}