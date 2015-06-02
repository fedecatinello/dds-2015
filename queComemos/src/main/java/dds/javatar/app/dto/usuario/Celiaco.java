package dds.javatar.app.dto.usuario;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.util.exception.UsuarioException;

public class Celiaco implements CondicionPreexistente {

	@Override
	public Boolean usuarioSigueRutinaSaludable(Usuario usuario) {
		// Para los celiacos no hace falta que cumplan con alguna condición.
		return Boolean.TRUE;
	}

	@Override
	public void validarUsuario(Usuario usuario) throws UsuarioException {
		// TODO: no hay información al respecto, deberia solicitarse?
	}

	@Override
	public boolean validarReceta(Receta receta) {
		return true;
	}

	@Override
	public Boolean esVegano() {
		return Boolean.FALSE;
	}

}
