package dds.javatar.app.domain.receta.busqueda;

import java.util.List;

import dds.javatar.app.domain.receta.Receta;

public class ResultadosPares implements PostProcesamiento {

	@Override
	public void procesar(List<Receta> recetasXusuario) {
		int cantidadDeRecetas=recetasXusuario.size();
		for(int i=cantidadDeRecetas-1; i>=0; i-=2){
			 recetasXusuario.remove(i);
		}		
	}

}
