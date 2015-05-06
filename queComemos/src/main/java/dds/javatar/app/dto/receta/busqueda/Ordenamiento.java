package dds.javatar.app.dto.receta.busqueda;

import java.util.List;

public class Ordenamiento implements PostProcesamiento{

	private List<Criterio> criterios;
	
	@Override
	public void procesar(Busqueda busqueda) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	public List<Criterio> getCriterios() {
		return criterios;
	}

	public void setCriterios(List<Criterio> criterios) {
		this.criterios = criterios;
	}

}
