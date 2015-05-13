package dds.javatar.app.dto.receta.busqueda;

import java.util.List;

import dds.javatar.app.dto.receta.Receta;

public class Ordenamiento implements PostProcesamiento{

	private Criterio criterio;

	@Override
	public void procesar(List<Receta> recetasXusuario) {
		criterio.ascendente(recetasXusuario);
	}

	public Criterio getCriterio() {
		return criterio;
	}

	public void setCriterio(Criterio criterio) {
		this.criterio = criterio;
	}
	
	

}
