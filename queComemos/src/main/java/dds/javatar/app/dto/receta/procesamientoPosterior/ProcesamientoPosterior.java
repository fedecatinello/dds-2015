package dds.javatar.app.dto.receta.procesamientoPosterior;

import java.util.List;

import dds.javatar.app.dto.receta.Receta;

public interface ProcesamientoPosterior {

	public List<Receta> aplicarProcPost(List<Receta> listaRecetas);
	
}
