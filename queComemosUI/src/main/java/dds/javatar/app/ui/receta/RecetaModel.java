package dds.javatar.app.ui.receta;

import org.uqbar.commons.utils.Observable;

import dds.javatar.app.dto.receta.Receta;


@Observable
public class RecetaModel {

	private Receta receta;

	public Receta getReceta() {
		return receta;
	}

	public void setReceta(Receta receta) {
		this.receta = receta;
	}

	
}
