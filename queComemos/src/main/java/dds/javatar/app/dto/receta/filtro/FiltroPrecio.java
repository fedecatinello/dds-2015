package dds.javatar.app.dto.receta.filtro;

import java.util.ArrayList;
import java.util.List;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.usuario.Usuario;

public class FiltroPrecio extends FiltroTemplate {

	List<String> ingredientesCaros;

	@Override
	public boolean validator(Usuario usuario, Receta receta) {
		boolean resultado = false;
		for (String ingrediente : ingredientesCaros) {
			if (receta.contieneIngrediente(ingrediente)) {
				resultado= true;
			}
		}	
		return resultado;
	}

	/* Setters y Getters */

	public List<String> getIngredientesCaros() {
		if (ingredientesCaros == null)
			return new ArrayList<String>();
		return ingredientesCaros;
	}

	public void setIngredientesCaros(List<String> ingredientesCaros) {
		this.ingredientesCaros = ingredientesCaros;
	}

}
