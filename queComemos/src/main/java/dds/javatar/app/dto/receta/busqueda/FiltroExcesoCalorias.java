package dds.javatar.app.dto.receta.busqueda;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.usuario.Usuario;

public class FiltroExcesoCalorias extends BusquedaDecorator {
	
	
	public FiltroExcesoCalorias(Busqueda busqueda) {
		super(busqueda);
	}

	@Override
	public List<Receta> ObtenerRecetas(Usuario usuario) {
		List<Receta> listaRecetas= this.busqueda.ObtenerRecetas(usuario);	
		List<Receta> listaRecetasAux = new ArrayList<Receta>();
		// Tengo que usar una lista auxiliar porque sino me tira la excepcion de concurrence
		for (Iterator<Receta> iterator = listaRecetas.iterator(); iterator.hasNext();) {
			Receta receta = (Receta) iterator.next();
			if (receta.getCalorias()<500) {
				listaRecetasAux.add(receta);
			}
		}		
		return listaRecetasAux;
	}


}
