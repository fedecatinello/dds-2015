package dds.javatar.app.dto.usuario;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.util.BusinessException;

public class Vegano implements CondicionPreexistente {

	private static final Set<String> alimentosProhibidos = new HashSet<String>(Arrays.asList("pollo", "carne", "chivito", "chori"));

	@Override
	public void validarUsuario(Usuario usuario) throws BusinessException {
		for (String alimentoProhibido : alimentosProhibidos) {
			if (usuario.tienePreferenciaAlimenticia(alimentoProhibido)) {
				throw new BusinessException(String.format("El usuario no puede tener como preferencia %s por ser vegano", alimentoProhibido));
			}
		}
	}

	@Override
	public Boolean usuarioSigueRutinaSaludable(Usuario usuario) {
		return usuario.tienePreferenciaAlimenticia("fruta");
	}

//	@Override
//	public void validarReceta(Receta receta) throws BusinessException {
//		for (String alimentoProhibido : alimentosProhibidos) {
//			if (receta.contieneIngrediente(alimentoProhibido)) {
//				throw new BusinessException("El usuario es vegetariano y no tolera los ingredientes dados por la receta");
//			}
//		}
//	}
	
	
	@Override
	public boolean validarReceta(Receta receta){
		for (String alimentoProhibido : alimentosProhibidos) {
			if (receta.contieneIngrediente(alimentoProhibido)) {
				return false;
			}
		}
		return true;
	}
}
