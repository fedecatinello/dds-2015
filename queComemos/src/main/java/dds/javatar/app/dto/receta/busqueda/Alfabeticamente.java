package dds.javatar.app.dto.receta.busqueda;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import dds.javatar.app.dto.receta.Receta;

public class Alfabeticamente implements Criterio{

	@Override
	public List<Receta> ascendente(List<Receta> recetasXusuario) {
		
		Collections.sort(recetasXusuario, new Comparator<Receta>(){
			@Override
			public int compare(Receta o1, Receta o2) {
				return o1.getNombre().compareTo(o2.getNombre());
			}
				});
		return recetasXusuario;
	}


}
