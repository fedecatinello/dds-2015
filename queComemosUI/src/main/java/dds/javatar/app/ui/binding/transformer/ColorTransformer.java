package dds.javatar.app.ui.binding.transformer;

import java.awt.Color;

import org.apache.commons.collections15.Transformer;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.ui.home.Home;



public class ColorTransformer implements Transformer<Receta, Color>{

	private Home model = new Home();
	
	@Override
	public Color transform(Receta receta) {
		if (model.getUsuarioLogeado().tieneReceta(receta)) {
			return Color.RED;
		} else {
			return Color.BLUE;
		}

	}


	
}
