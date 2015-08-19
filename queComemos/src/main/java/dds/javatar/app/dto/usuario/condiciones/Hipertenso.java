package dds.javatar.app.dto.usuario.condiciones;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.usuario.Usuario;

public class Hipertenso extends UsuarioConPreferencia {

	private static final Set<String> ingredientesProhibidos = new HashSet<String>(Arrays.asList("sal", "caldo"));

	@Override
	public Boolean usuarioSigueRutinaSaludable(Usuario usuario) {
		return usuario.getRutina()
			.esIntensiva();
	}

	@Override
	public boolean validarReceta(Receta receta) {
		for (String ingredienteProhibido : ingredientesProhibidos) {
			if (receta.contieneIngrediente(ingredienteProhibido) || receta.contieneCondimento(ingredienteProhibido)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public Boolean esVegano() {
		return Boolean.FALSE;
	}

	@Override
	public String getName() {
		return "Hipertenso";
	}
}
