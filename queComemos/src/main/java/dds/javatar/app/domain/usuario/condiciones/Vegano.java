package dds.javatar.app.domain.usuario.condiciones;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonTypeName;

import dds.javatar.app.domain.receta.Receta;
import dds.javatar.app.domain.usuario.Usuario;
import dds.javatar.app.util.exception.UsuarioException;
@JsonTypeName("vegano")
public class Vegano implements CondicionPreexistente {

	private static final Set<String> alimentosProhibidos = new HashSet<String>(Arrays.asList("pollo", "carne", "chivito", "chori"));

	@Override
	public void validarUsuario(Usuario usuario) throws UsuarioException {
		for (String alimentoProhibido : alimentosProhibidos) {
			if (usuario.tienePreferenciaAlimenticia(alimentoProhibido)) {
				throw new UsuarioException(String.format("El usuario no puede tener como preferencia %s por ser vegano", alimentoProhibido));
			}
		}
	}

	@Override
	public Boolean usuarioSigueRutinaSaludable(Usuario usuario) {
		return usuario.tienePreferenciaAlimenticia("fruta");
	}

	@Override
	public boolean validarReceta(Receta receta) {
		for (String alimentoProhibido : alimentosProhibidos) {
			if (receta.contieneIngrediente(alimentoProhibido)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public Boolean esVegano() {
		return Boolean.TRUE;
	}

	@Override
	public String getName() {
		return "Vegano";
	}
}
