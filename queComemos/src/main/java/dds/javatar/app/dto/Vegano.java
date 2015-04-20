package dds.javatar.app.dto;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import dds.javatar.app.util.BusinessException;

public class Vegano implements CondicionPreexistente {

	private static final Set<String> preferenciasProhibidas = new HashSet<String>(Arrays.asList("pollo", "carne", "chivito", "chori"));
	
	@Override
	public void validarUsuario(Usuario usuario) throws BusinessException {
		for (String preferenciaProhibida : preferenciasProhibidas) {;
			if (Boolean.TRUE.equals(usuario.getPreferenciasAlimenticias().get(preferenciaProhibida))) {
				throw new BusinessException(String.format("El usuario no puede tener como preferencia %s por ser vegano debe indicar sexo", preferenciaProhibida));
			}
		}
	}

	@Override
	public void validarUsuarioSaludable(Usuario usuario) throws BusinessException {
		// TODO
	}

}
