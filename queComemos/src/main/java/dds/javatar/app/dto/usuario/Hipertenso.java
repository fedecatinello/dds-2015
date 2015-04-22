package dds.javatar.app.dto.usuario;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.util.BusinessException;

public class Hipertenso extends UsuarioConPreferencia {
	
	private static final Set<String> ingredientesProhibidos = new HashSet<String>(Arrays.asList("sal", "caldo"));
	
	@Override
	public Boolean usuarioSigueRutinaSaludable(Usuario usuario) {
		return usuario.getRutina().esIntensiva();
	}

	@Override
	public void validarReceta(Receta receta) throws BusinessException {
		for (String ingredienteProhibido : ingredientesProhibidos) {
			if (receta.contieneIngrediente(ingredienteProhibido) || receta.contieneCondimento(ingredienteProhibido)) {
				throw new BusinessException("El usuario es hipertenso y no tolera los ingredientes o condimentos");
			}
		}
	}

}