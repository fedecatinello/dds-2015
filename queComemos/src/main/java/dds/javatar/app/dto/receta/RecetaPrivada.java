package dds.javatar.app.dto.receta;

import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.util.BusinessException;

public class RecetaPrivada implements TipoReceta{

	private Usuario autor;
	
	public RecetaPrivada(Usuario autor){
		this.autor=autor;
		
	}
	
	@Override
	public boolean chequearVisibilidad(Receta receta, Usuario usuario) {
		if(usuario.getRecetas().contains(receta) || usuario==autor){
			return true;
		}
		return false;
	}

	public void agregar(Receta receta, Usuario usuario) throws BusinessException {
		if(usuario.getRecetas().contains(receta)){
			throw new BusinessException("El usuario ya contiene esta receta");
		} else {
			receta.validar();
			usuario.getRecetas().add(receta);
		}
	}

	public boolean chequearModificacion(Receta receta, Usuario usuario) {
		if(usuario.getRecetas().contains(receta)){
			return true;
		}else{
			return false;
		}
		
	}
}
