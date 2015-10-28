package dds.javatar.app.domain.receta.filtro;

import java.util.Set;

import dds.javatar.app.domain.receta.Receta;
import dds.javatar.app.domain.usuario.Usuario;

public class FiltroGusto extends FiltroTemplate {
	
	private Set<String> _getNombreIngredientesReceta(Receta receta) {
		
		Set<String> ingredientes = receta.getIngredientes().keySet();
		return ingredientes;
		
	}

	@Override
	public boolean validator(Usuario usuario, Receta receta) {
		
		Set<String> ingredientes = _getNombreIngredientesReceta(receta);
		
		for(String ingrediente : ingredientes) {
			
			if(usuario.tieneAlimentoQueLeDisguste(ingrediente)) {
				
				return false;				
			}
		}
		return true;
	}

}
