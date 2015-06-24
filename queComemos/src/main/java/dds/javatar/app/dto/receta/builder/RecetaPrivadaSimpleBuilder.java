package dds.javatar.app.dto.receta.builder;

import java.math.BigDecimal;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.receta.RecetaPrivadaSimple;

public class RecetaPrivadaSimpleBuilder {

private RecetaPrivadaSimple recetaPrivadaSimple;
	
	public RecetaPrivadaSimpleBuilder(String nombre) {
		recetaPrivadaSimple = new RecetaPrivadaSimple();
		recetaPrivadaSimple.setNombre(nombre);
	}
	
	public RecetaPrivadaSimpleBuilder inventadaPor(String autor) {
		recetaPrivadaSimple.setAutor(autor);
		return this;
	}
	
	public RecetaPrivadaSimpleBuilder dificultad(String dificultad) {
		recetaPrivadaSimple.setDificultad(dificultad);
		return this;
	}
	
	public RecetaPrivadaSimpleBuilder totalCalorias(Integer calorias) {
		recetaPrivadaSimple.setCalorias(calorias);
		return this;
	}

	public RecetaPrivadaSimpleBuilder temporada(String temporada) {
		recetaPrivadaSimple.setTemporada(temporada);
		return this;
	}

	public RecetaPrivadaSimpleBuilder agregarCondimentos(String condimento, BigDecimal cantidad) {
		recetaPrivadaSimple.agregarCondimento(condimento, cantidad);
		return this;
	}

	public RecetaPrivadaSimpleBuilder agregarIngrediente(String ingrediente, BigDecimal cantidad) {
		recetaPrivadaSimple.agregarIngrediente(ingrediente, cantidad);
		return this;
	}

	public RecetaPrivadaSimpleBuilder agregarPaso(Integer nroPaso, String pasoPreparacion) {
		recetaPrivadaSimple.agregarPasoPreparacion(nroPaso, pasoPreparacion);
		return this;
	}

	public RecetaPrivadaSimpleBuilder tiempoPreparacion(Integer tiempoPreparacion) {
		recetaPrivadaSimple.setTiempoPreparacion(tiempoPreparacion);
		return this;
	}

	public RecetaPrivadaSimple buildReceta() {
		return recetaPrivadaSimple;
	}
}
