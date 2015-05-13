package dds.javatar.app.dto.receta.procesamientoPosterior;

import java.util.List;

import dds.javatar.app.dto.receta.Receta;

public class ProcPostNulo implements ProcesamientoPosterior {

	@Override
	public List<Receta> aplicarProcPost(List<Receta> listaRecetas) {
		return listaRecetas;
	}

}
