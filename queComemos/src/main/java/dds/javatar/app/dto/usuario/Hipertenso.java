package dds.javatar.app.dto.usuario;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.util.BusinessException;

public class Hipertenso extends UsuarioConPreferencia {
	
	
	
	@Override
	public Boolean usuarioSigueRutinaSaludable(Usuario usuario) {
		return usuario.getRutina().esIntensiva();
	}

	@Override
	public void validarReceta(Receta receta) throws BusinessException {
		// TODO : buscaria una mejor solucion para esto tal vez
		if (receta.contieneIngrediente("sal") || receta.contieneIngrediente("caldo") || receta.contieneCondimento("sal") || receta.contieneCondimento("caldo")) {
			throw new BusinessException("El usuario es hipertenso y no tolera los ingredientes o condimentos");
		}
	}

}
