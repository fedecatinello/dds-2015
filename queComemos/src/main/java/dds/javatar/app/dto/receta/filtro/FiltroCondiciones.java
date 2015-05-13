package dds.javatar.app.dto.receta.filtro;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.usuario.Usuario;


public class FiltroCondiciones extends FiltroTemplate {

	
	public boolean validator(Usuario usuario, Receta receta) {
		
		return usuario.validarSiAceptaReceta(receta);
	}

}
