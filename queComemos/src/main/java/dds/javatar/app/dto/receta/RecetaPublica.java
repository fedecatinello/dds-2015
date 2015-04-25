package dds.javatar.app.dto.receta;

import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.util.BusinessException;

public class RecetaPublica implements TipoReceta{

	@Override
	public boolean chequearVisibilidad(Receta receta, Usuario usuario) {
		return true;
	}

	@Override
	public void agregar(Receta receta, Usuario usuario) throws BusinessException {
		receta.validar();
		usuario.getRecetas().add(convertirEnPrivada(receta, usuario));
	}

	private Receta convertirEnPrivada(Receta receta, Usuario usuario) {
		Receta copiaReceta = receta.clone();
		copiaReceta.setTipo(new RecetaPrivada(usuario));
		return copiaReceta;
	}

	public boolean chequearModificacion(Receta receta, Usuario usuario) {
		return true;
	}

	
	

}
