package dds.javatar.app.dto.receta.busqueda;

import java.util.List;

import dds.javatar.app.dto.receta.Receta;

public class PrimerosDiez implements PostProcesamiento{

	@Override
	public void procesar(List<Receta> recetasXusuario) {
		for(int i=recetasXusuario.size()-1; i>9; i--){
			recetasXusuario.remove(i);
		}
	}

}
