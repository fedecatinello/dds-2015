package dds.javatar.app.dto.usuario;

import java.math.BigDecimal;

import dds.javatar.app.dto.receta.Receta;
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
	public Boolean usuarioSigueRutinaSaludable(Usuario usuario) {
		return (usuario.getPeso().intValue() <= MAX_PESO || usuario.getRutina().esActiva());
	}

//	@Override
//	public void validarReceta(Receta receta) throws BusinessException {
//		if (receta.alimentoSobrepasaCantidad("azucar", new BigDecimal(100))) {
//			throw new BusinessException("El usuario es diabetico y no puede consumir mas de 100gr de azucar");
//		}
//	}
	
	public boolean validarReceta(Receta receta){
		if (receta.alimentoSobrepasaCantidad("azucar", new BigDecimal(100))) {
		return false; 
		}else{
			return true;
		}
	}
	

}
