package dds.javatar.app.dto.receta;

import dds.javatar.app.util.exception.RecetaException;


public interface RecetaPublica extends Receta{
	
	public void agregarRecetaAlRepo(RecetaPublica receta);
	
	public RecetaPrivada clonarme(String autor) throws RecetaException;
}
