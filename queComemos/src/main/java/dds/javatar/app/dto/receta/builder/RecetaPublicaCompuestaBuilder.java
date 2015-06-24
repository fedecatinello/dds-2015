package dds.javatar.app.dto.receta.builder;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.receta.RecetaPublicaCompuesta;

public class RecetaPublicaCompuestaBuilder {

	private RecetaPublicaCompuesta recetaPublicaCompuesta;

	private RecetaPublicaCompuesta recetaPrivadaCompuesta;

	public RecetaPublicaCompuestaBuilder(String nombre) {
		recetaPublicaCompuesta = new RecetaPublicaCompuesta();
		recetaPublicaCompuesta.setNombre(nombre);
	}

	public RecetaPublicaCompuestaBuilder dificultad(String dificultad) {
		recetaPublicaCompuesta.setDificultad(dificultad);
		return this;
	}

	public RecetaPublicaCompuestaBuilder totalCalorias(Integer calorias) {
		recetaPublicaCompuesta.setCalorias(calorias);
		return this;
	}

	public RecetaPublicaCompuestaBuilder temporada(String temporada) {
		recetaPublicaCompuesta.setTemporada(temporada);
		return this;
	}

	public RecetaPublicaCompuestaBuilder tiempoPreparacion(
			Integer tiempoPreparacion) {
		recetaPublicaCompuesta.setTiempoPreparacion(tiempoPreparacion);
		return this;
	}

	public RecetaPublicaCompuesta buildReceta() {
		return recetaPublicaCompuesta;
	}

}
