package dds.javatar.app.domain.receta.busqueda;

import java.util.List;

import dds.javatar.app.domain.receta.Receta;

public class PrimerosDiez implements PostProcesamiento{

	@Override
	public void procesar(List<Receta> recetasXusuario) {
		for(int i=recetasXusuario.size()-1; i>9; i--){
			recetasXusuario.remove(i);
		}
	}

}
