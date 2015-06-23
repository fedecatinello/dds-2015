package dds.javatar.app.dto.receta.busqueda.builder;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Map;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.receta.RecetaPrivada;
import dds.javatar.app.dto.receta.RecetaPrivadaCompuesta;
import dds.javatar.app.dto.receta.RecetaPublicaCompuesta;
import dds.javatar.app.util.exception.RecetaException;

public class RecetaPublicaCompuestaBuilder implements RecetaBuilder{

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
