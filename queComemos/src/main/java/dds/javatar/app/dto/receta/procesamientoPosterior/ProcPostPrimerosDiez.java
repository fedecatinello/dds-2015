package dds.javatar.app.dto.receta.procesamientoPosterior;

import java.util.ArrayList;
import java.util.List;

import dds.javatar.app.dto.receta.Receta;

public class ProcPostPrimerosDiez extends ProcesamientoPosteriorDecorator {

	public ProcPostPrimerosDiez(ProcesamientoPosterior procPosterior) {
		super(procPosterior);
	}
	
	@Override
	public List<Receta> aplicarProcPost(List<Receta> listaRecetas) {
		listaRecetas=this.procesamientoPosterior.aplicarProcPost(listaRecetas);
		
		List<Receta> subItems = new ArrayList<Receta>(listaRecetas.subList(0, 10));
		return subItems;
	
	}

}
