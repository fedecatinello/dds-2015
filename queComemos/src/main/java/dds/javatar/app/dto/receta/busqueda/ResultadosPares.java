package dds.javatar.app.dto.receta.busqueda;

import java.util.List;

import dds.javatar.app.dto.receta.Receta;

public class ResultadosPares implements PostProcesamiento {

	@Override
	public void procesar(List<Receta> recetasXusuario) {

		int cantidadDeRecetas=recetasXusuario.size();
		for(int i=1; i<=cantidadDeRecetas-1; i+=2){
			 recetasXusuario.remove(i);
		}		
	}

}
