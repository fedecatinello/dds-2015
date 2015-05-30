package dds.javatar.app.dto.usuario;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.util.exception.UsuarioException;

public interface CondicionPreexistente {

	void validarUsuario(Usuario usuario) throws UsuarioException;

	Boolean usuarioSigueRutinaSaludable(Usuario usuario);

	// TODO: analizar si hacerlo con boolean o con exception, para mi no es una
	// regla de negocio sino algo que se quiere conocer
	boolean validarReceta(Receta receta);

}
