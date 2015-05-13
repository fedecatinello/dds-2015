package dds.javatar.app.dto.receta.procesamientoPosterior;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import dds.javatar.app.dto.receta.Receta;

public class ProcPostOrdenarPorAlfabeto extends ProcesamientoPosteriorDecorator {

	public ProcPostOrdenarPorAlfabeto(ProcesamientoPosterior procPosterior) {
		super(procPosterior);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Receta> aplicarProcPost(List<Receta> listaRecetas)	{
		listaRecetas=this.procesamientoPosterior.aplicarProcPost(listaRecetas);		
		Collections.sort(listaRecetas, new Comparator<Receta>() {
			 @Override
		        public int compare(Receta receta1, Receta receta2) {
				 return receta1.getNombre().compareTo( receta2.getNombre());
			 }
		});		
		return listaRecetas;
	
	}
	
}
