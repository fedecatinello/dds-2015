package dds.javatar.app.dto.receta.busqueda.builder;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.receta.RecetaPublicaCompuesta;

public class RecetaPublicaCompuestaBuilder implements RecetaBuilder {

	private RecetaPublicaCompuesta recetaPrivadaCompuesta;

	public RecetaPublicaCompuestaBuilder(String nombre) {
		recetaPrivadaCompuesta = new RecetaPublicaCompuesta();
		recetaPrivadaCompuesta.setNombre(nombre);
	}

	public RecetaPublicaCompuestaBuilder dificultad(String dificultad) {
		recetaPrivadaCompuesta.setDificultad(dificultad);
		return this;
	}

	public RecetaPublicaCompuestaBuilder totalCalorias(Integer calorias) {
		recetaPrivadaCompuesta.setCalorias(calorias);
		return this;
	}

	public RecetaPublicaCompuestaBuilder temporada(String temporada) {
		recetaPrivadaCompuesta.setTemporada(temporada);
		return this;
	}

	public RecetaPublicaCompuestaBuilder tiempoPreparacion(Integer tiempoPreparacion) {
		recetaPrivadaCompuesta.setTiempoPreparacion(tiempoPreparacion);
		return this;
	}

	public Receta buildReceta() {
		return recetaPrivadaCompuesta;
	}

}
