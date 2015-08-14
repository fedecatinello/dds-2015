package dds.javatar.app.dto.receta.builder;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.receta.RecetaCompuesta;
import dds.javatar.app.util.exception.RecetaException;

public class RecetaCompuestaBuilder extends RecetaBuilder {

	public RecetaCompuesta recetaCompuesta;

	public RecetaBuilder agregarSubReceta(Receta subReceta) throws RecetaException {
		recetaCompuesta.agregarSubReceta(subReceta);
		return this;
	}
	
	
}
