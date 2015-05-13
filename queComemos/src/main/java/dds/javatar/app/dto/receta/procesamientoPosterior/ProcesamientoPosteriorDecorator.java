package dds.javatar.app.dto.receta.procesamientoPosterior;

import java.util.List;

import dds.javatar.app.dto.receta.Receta;

public class ProcesamientoPosteriorDecorator implements ProcesamientoPosterior {

	protected ProcesamientoPosterior procesamientoPosterior;
	
	public ProcesamientoPosteriorDecorator (ProcesamientoPosterior procPosterior){
		this.procesamientoPosterior=procPosterior;
	}

	@Override
	public List<Receta> aplicarProcPost(List<Receta> listaRecetas) {
		return this.procesamientoPosterior.aplicarProcPost(listaRecetas);		
	}
	
}
