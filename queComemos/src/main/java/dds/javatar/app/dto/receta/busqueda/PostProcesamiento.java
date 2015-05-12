package dds.javatar.app.dto.receta.busqueda;

import java.util.List;

import dds.javatar.app.dto.receta.Receta;

public interface PostProcesamiento {

	public List<Receta> procesar(List<Receta> recetasXusuario);
	
}
