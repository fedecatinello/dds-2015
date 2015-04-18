package dds.javatar.app.dto;

public abstract class UsuarioConPreferencia implements CondicionPreexistente {

	
	@Override
	public void validarUsuario(Usuario usuario) throws BusinessException {
		if (usuario.getPreferenciasAlimenticias() != null) {
			if (!usuario.getPreferenciasAlimenticias().values().contains(Boolean.TRUE)) {
				throw new BusinessException("El usuario debe tener como minimo una preferencia");
			}
		}
	}

}