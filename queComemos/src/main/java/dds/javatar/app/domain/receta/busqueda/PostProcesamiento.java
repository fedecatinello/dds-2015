package dds.javatar.app.domain.receta.busqueda;

import java.util.List;

import dds.javatar.app.domain.receta.Receta;

public interface PostProcesamiento {

	public void procesar(List<Receta> recetasXusuario);
	
}
