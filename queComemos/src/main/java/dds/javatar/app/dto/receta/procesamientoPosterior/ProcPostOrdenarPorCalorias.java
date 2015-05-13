package dds.javatar.app.dto.receta.procesamientoPosterior;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import dds.javatar.app.dto.receta.Receta;

public class ProcPostOrdenarPorCalorias extends ProcesamientoPosteriorDecorator {

	public ProcPostOrdenarPorCalorias(ProcesamientoPosterior procPosterior) {
		super(procPosterior);
	}
	
	@Override
	public List<Receta> aplicarProcPost(List<Receta> listaRecetas) {
		listaRecetas=this.procesamientoPosterior.aplicarProcPost(listaRecetas);		
		Collections.sort(listaRecetas, new Comparator<Receta>() {
			 @Override
		        public int compare(Receta receta1, Receta receta2) {
				 return receta1.getCalorias() - receta2.getCalorias();
			 }
		});		
		return listaRecetas;
	
	}
}
