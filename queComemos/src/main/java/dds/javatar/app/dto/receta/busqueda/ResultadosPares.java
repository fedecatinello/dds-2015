package dds.javatar.app.dto.receta.busqueda;

import java.util.List;

import dds.javatar.app.dto.receta.Receta;

public class ResultadosPares implements PostProcesamiento {

	@Override
	public List<Receta> procesar(List<Receta> recetasXusuario) {
		List<Receta> recetasPares= null;
		for(int i=0; i<recetasXusuario.size(); i+=2){
			recetasPares.add(recetasXusuario.get(i));
		}
		return recetasPares;		
	}

}
