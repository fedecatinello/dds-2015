package dds.javatar.app.dto;

import dds.javatar.app.util.BusinessException;

public class Hipertenso extends UsuarioConPreferencia {


	@Override
	public void validarUsuarioSaludable(Usuario usuario) throws BusinessException {
		if(!esRutinaIntensiva(usuario.getRutina())){
			throw new BusinessException("El usuario es hipertenso y no subsana la condicion");
		}
	}

	public boolean esRutinaIntensiva(Rutina rutina){
		return rutina.getTipo().toString().equalsIgnoreCase("INTENSIVO");
	}

	@Override
	public void aceptaReceta(Receta receta) throws BusinessException {
		if(receta.contieneIngrediente("sal") || receta.contieneIngrediente("caldo")
				|| receta.contieneCondimento("sal") || receta.contieneCondimento("caldo"))
		{
			throw new BusinessException("El usuario es hipertenso y no tolera los ingredientes o condimentos");
		}
	}
		
	}
	


