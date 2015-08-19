package dds.javatar.app.ui.binding.transformer;

import java.awt.Color;

import org.apache.commons.collections15.Transformer;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.ui.home.Home;



public class ColorTransformer implements Transformer<Receta, Color>{

	private Home model;
	
	public ColorTransformer(Home model) {
		this.model = model;
	}
	
	@Override
	public Color transform(Receta receta) {
		if (this.model.getUsuarioLogeado().tieneReceta(receta)) {
			return Color.RED;
		} else {
			return Color.BLUE;
		}
	}
	
}
