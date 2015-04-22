package dds.javatar.app.dto.usuario;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.util.BusinessException;

public interface CondicionPreexistente {

	void validarUsuario(Usuario usuario) throws BusinessException;
	
	Boolean usuarioSigueRutinaSaludable(Usuario usuario);
	
	void validarReceta(Receta receta) throws BusinessException;
}
