package dds.javatar.app.dto.receta.builder;

import java.math.BigDecimal;
import java.util.Map;

import dds.javatar.app.dto.receta.RecetaPrivada;
import dds.javatar.app.util.exception.RecetaException;

public abstract class RecetaPrivadaBuilder extends RecetaBuilder {

	public RecetaPrivada recetaPrivada;
	
	private String autor;
	
	public RecetaBuilder inventadaPor(String autor) {
		this.autor = autor;
		return this;
	}

	public RecetaBuilder agregarCondimentos(String condimento, BigDecimal cantidad) {
		recetaPrivada.agregarCondimento(condimento, cantidad);		
		return this;
	}

	public RecetaBuilder agregarIngrediente(String ingrediente, BigDecimal cantidad) {
		recetaPrivada.agregarIngrediente(ingrediente, cantidad);
		return this;
	}

	public RecetaBuilder agregarPaso(Integer nroPaso, String pasoPreparacion) {
		recetaPrivada.agregarPasoPreparacion(nroPaso, pasoPreparacion);
		return this;
	}
	
}
