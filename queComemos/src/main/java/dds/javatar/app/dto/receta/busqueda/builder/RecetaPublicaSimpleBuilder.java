package dds.javatar.app.dto.receta.busqueda.builder;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.receta.RecetaPublicaSimple;

public class RecetaPublicaSimpleBuilder {

	private RecetaPublicaSimple recetaPublicaSimple;

	public RecetaPublicaSimpleBuilder(String nombre) {
		recetaPublicaSimple = new RecetaPublicaSimple();
		recetaPublicaSimple.setNombre(nombre);
	}

	public RecetaPublicaSimpleBuilder dificultad(String dificultad) {
		recetaPublicaSimple.setDificultad(dificultad);
		return this;
	}

	public RecetaPublicaSimpleBuilder totalCalorias(Integer calorias) {
		recetaPublicaSimple.setCalorias(calorias);
		return this;
	}

	public RecetaPublicaSimpleBuilder temporada(String temporada) {
		recetaPublicaSimple.setTemporada(temporada);
		return this;
	}

	public RecetaPublicaSimpleBuilder tiempoPreparacion(
			Integer tiempoPreparacion) {
		recetaPublicaSimple.setTiempoPreparacion(tiempoPreparacion);
		return this;
	}

	public RecetaPublicaSimple buildReceta() {
		return recetaPublicaSimple;
	}

}
