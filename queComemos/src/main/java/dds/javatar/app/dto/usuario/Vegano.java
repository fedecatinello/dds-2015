package dds.javatar.app.dto.usuario;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.util.BusinessException;

public class Vegano implements CondicionPreexistente {

	private static final Set<String> preferenciasProhibidas = new HashSet<String>(Arrays.asList("pollo", "carne", "chivito", "chori"));

	@Override
	public void validarUsuario(Usuario usuario) throws BusinessException {
		for (String preferenciaProhibida : preferenciasProhibidas) {
			if (Boolean.TRUE.equals(usuario.getPreferenciasAlimenticias().get(preferenciaProhibida))) {
				throw new BusinessException(String.format("El usuario no puede tener como preferencia %s por ser vegano", preferenciaProhibida));
			}
		}
	}

	@Override
	public void validarUsuarioSaludable(Usuario usuario) throws BusinessException {
		if (!usuario.tienePreferenciaAlimenticia("fruta")) {
			throw new BusinessException("El usuario vegano debería tener como preferencia a las frutas");
		}
	}

	@Override
	public void validarReceta(Receta receta) throws BusinessException {
		// TODO: codigo repetido...
		if (receta.contieneIngrediente("pollo") || receta.contieneIngrediente("carne") || receta.contieneIngrediente("chivito") || receta.contieneIngrediente("chori")) {
			throw new BusinessException("El usuario es vegetariano y no tolera los ingredientes");
		}
	}

}
