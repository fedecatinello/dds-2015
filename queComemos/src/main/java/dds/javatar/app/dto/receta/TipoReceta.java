package dds.javatar.app.dto.receta;

import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.util.BusinessException;

public interface TipoReceta {

	public boolean chequearVisibilidad(Receta receta, Usuario usuario);
	public void agregar(Receta receta, Usuario usuario) throws BusinessException;
	public boolean chequearModificacion(Receta receta, Usuario usuario);

}
