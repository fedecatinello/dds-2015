package dds.javatar.app.dto.receta.busqueda;

import java.util.List;

import dds.javatar.app.dto.receta.Receta;

public interface Criterio {

	public List<Receta> ascendente(List<Receta> recetasXusuario);

}
