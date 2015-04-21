package dds.javatar.app.dto;

import java.math.BigDecimal;

import dds.javatar.app.util.BusinessException;

public class Diabetico extends UsuarioConPreferencia {

	private static final Integer MAX_PESO = 70;

	@Override
	public void validarUsuario(Usuario usuario) throws BusinessException {
		super.validarUsuario(usuario);

		if (usuario.getSexo() == null) {
			throw new BusinessException("El usuario diabetico debe indicar sexo");
		}
	}

	@Override
	public void validarUsuarioSaludable(Usuario usuario) throws BusinessException {
		if (usuario.getPeso().intValue() > MAX_PESO && !usuario.getRutina().esActiva()) {
			throw new BusinessException("El usuario es diabetico y no subsana la condicion");
		}
	}

	@Override
	public void validarReceta(Receta receta) throws BusinessException {
		if (receta.alimentoSobrepasaCantidad("azucar", new BigDecimal(100))) {
			throw new BusinessException("El usuario es diabetico y no puede consumir mas de 100gr de azucar");
		}
	}

}
