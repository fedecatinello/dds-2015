package dds.javatar.app.dto.usuario.condiciones;

import java.math.BigDecimal;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.util.exception.UsuarioException;

public class Diabetico extends UsuarioConPreferencia {

	private static final Integer MAX_PESO = 70;

	@Override
	public void validarUsuario(Usuario usuario) throws UsuarioException {
		super.validarUsuario(usuario);

		if (usuario.getSexo() == null) {
			throw new UsuarioException("El usuario diabetico debe indicar sexo");
		}
	}

	@Override
	public Boolean usuarioSigueRutinaSaludable(Usuario usuario) {
		return (usuario.getPeso()
			.intValue() <= MAX_PESO || usuario.getRutina()
			.esActiva());
	}

	@Override
	public boolean validarReceta(Receta receta) {
		if (receta.alimentoSobrepasaCantidad("azucar", new BigDecimal(100))) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public Boolean esVegano() {
		return Boolean.FALSE;
	}

	@Override
	public String getName() {
		return "Diabetico";
	}

}
