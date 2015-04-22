package dds.javatar.app.dto.usuario;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.util.BusinessException;

public class Celiaco implements CondicionPreexistente {

	@Override
	public Boolean usuarioSigueRutinaSaludable(Usuario usuario) {
		// Para los celiacos no hace falta que cumplan con alguna condición.
		return Boolean.TRUE;
	}

	@Override
	public void validarUsuario(Usuario usuario) throws BusinessException {
		// TODO
	}

	@Override
	public void validarReceta(Receta receta) throws BusinessException {
		// TODO
	}

}
