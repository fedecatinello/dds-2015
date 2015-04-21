package dds.javatar.app.dto;

import dds.javatar.app.util.BusinessException;

public class Hipertenso extends UsuarioConPreferencia {

	@Override
	public void validarUsuarioSaludable(Usuario usuario) throws BusinessException {
		if (!usuario.getRutina().esIntensiva()) {
			throw new BusinessException("El usuario es hipertenso y no subsana la condicion");
		}
	}

	@Override
	public void validarReceta(Receta receta) throws BusinessException {
		// TODO : buscaria una mejor solucion para esto tal vez
		if (receta.contieneIngrediente("sal") || receta.contieneIngrediente("caldo") || receta.contieneCondimento("sal") || receta.contieneCondimento("caldo")) {
			throw new BusinessException("El usuario es hipertenso y no tolera los ingredientes o condimentos");
		}
	}

}
