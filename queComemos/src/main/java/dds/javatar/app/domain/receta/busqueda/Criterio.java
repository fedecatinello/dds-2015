package dds.javatar.app.domain.receta.busqueda;

import java.util.List;

import dds.javatar.app.domain.receta.Receta;

public interface Criterio {

	public void ascendente(List<Receta> recetasXusuario);

}
