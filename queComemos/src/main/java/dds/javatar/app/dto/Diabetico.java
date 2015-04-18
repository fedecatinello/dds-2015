package dds.javatar.app.dto;

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
		if (usuario.getPeso().intValue() > MAX_PESO && true ) {
			throw new BusinessException("El usuario es diabetico y no subsana la condicion");
		}
	}

}
