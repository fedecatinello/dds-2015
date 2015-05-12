package dds.javatar.app.dto.receta.busqueda;

import java.util.List;

import dds.javatar.app.dto.receta.Receta;

public class PrimerosDiez implements PostProcesamiento{

	@Override
	public List<Receta> procesar(List<Receta> recetasXusuario) {
		return recetasXusuario.subList(0, 9);
	}

}
