package dds.javatar.app.dto.usuario;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.util.exception.UsuarioException;

public interface CondicionPreexistente {

	void validarUsuario(Usuario usuario) throws UsuarioException;

	Boolean usuarioSigueRutinaSaludable(Usuario usuario);

	boolean validarReceta(Receta receta);
	
	Boolean esVegano();

}
