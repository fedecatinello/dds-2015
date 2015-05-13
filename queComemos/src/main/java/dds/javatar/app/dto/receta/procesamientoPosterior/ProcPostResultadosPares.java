package dds.javatar.app.dto.receta.procesamientoPosterior;

import java.util.ArrayList;
import java.util.List;

import dds.javatar.app.dto.receta.Receta;

public class ProcPostResultadosPares extends ProcesamientoPosteriorDecorator {

	public ProcPostResultadosPares(ProcesamientoPosterior procPosterior) {
		super(procPosterior);
	}
	
	@Override
	public List<Receta> aplicarProcPost(List<Receta> listaRecetas) {
		listaRecetas=this.procesamientoPosterior.aplicarProcPost(listaRecetas);
		List<Receta> listaRecetasAux = new ArrayList<Receta>();
		
		for (int i = 0; i < listaRecetas.size(); i=i+2) {
			listaRecetasAux.add(listaRecetas.get(i));
		}
	
		return listaRecetasAux;
	
	}

}
