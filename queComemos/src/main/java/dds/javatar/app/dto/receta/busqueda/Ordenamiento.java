package dds.javatar.app.dto.receta.busqueda;

import java.util.List;

import dds.javatar.app.dto.receta.Receta;

public class Ordenamiento implements PostProcesamiento{

	private List<Criterio> criterios;

	@Override
	public void procesar(List<Receta> recetasXusuario) {
		
		for(Criterio criterioAAplicar: criterios){
			criterioAAplicar.ascendente(recetasXusuario);
		}
	}
	
	
	public List<Criterio> getCriterios() {
		return criterios;
	}

	public void setCriterios(List<Criterio> criterios) {
		this.criterios = criterios;
	}

}
