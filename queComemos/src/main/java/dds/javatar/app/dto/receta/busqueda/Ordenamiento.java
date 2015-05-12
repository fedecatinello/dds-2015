package dds.javatar.app.dto.receta.busqueda;

import java.util.List;

import dds.javatar.app.dto.receta.Receta;

public class Ordenamiento implements PostProcesamiento{

	private List<Criterio> criterios;
	
	@SuppressWarnings("null")
	@Override
	public List<Receta> procesar(List<Receta> recetasXusuario) {
		List<Receta> recetasOrdenadas = null;
		for(Criterio criterioAAplicar: criterios){
			recetasOrdenadas.addAll(criterioAAplicar.ascendente(recetasXusuario));
		}
		return recetasOrdenadas;
	}
	
	
	public List<Criterio> getCriterios() {
		return criterios;
	}

	public void setCriterios(List<Criterio> criterios) {
		this.criterios = criterios;
	}

}
