package dds.javatar.app.dto;

import dds.javatar.app.util.BusinessException;

public interface CondicionPreexistente {

	void validarUsuario(Usuario usuario) throws BusinessException;
	
	void validarUsuarioSaludable(Usuario usuario) throws BusinessException;
	
	void aceptaReceta(Receta receta) throws BusinessException;
}
