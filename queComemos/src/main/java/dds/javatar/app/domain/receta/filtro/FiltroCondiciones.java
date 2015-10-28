package dds.javatar.app.domain.receta.filtro;

import dds.javatar.app.domain.receta.Receta;
import dds.javatar.app.domain.usuario.Usuario;


public class FiltroCondiciones extends FiltroTemplate {
	
	public boolean validator(Usuario usuario, Receta receta) {		
		return usuario.validarSiAceptaReceta(receta);
	}

}
