package dds.javatar.app.dto;

public interface CondicionPreexistente {

	void validarUsuario(Usuario usuario) throws BusinessException;
	
	void validarUsuarioSaludable(Usuario usuario) throws BusinessException;
}
