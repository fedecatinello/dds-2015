package dds.javatar.app.dto.receta;

import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.util.exception.RecetaException;
import dds.javatar.app.util.exception.UsuarioException;

public interface RecetaPublica extends Receta{
	
	public void agregarRecetaAlRepo(RecetaPublica receta);
}
