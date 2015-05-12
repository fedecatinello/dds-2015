package dds.javatar.app.dto.receta.busqueda;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import dds.javatar.app.dto.sistema.Sistema;
import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.usuario.Usuario;

public class FiltroCarosEnPreparacion extends BusquedaDecorator {

	public FiltroCarosEnPreparacion(Busqueda busqueda) {
		super(busqueda);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Receta> ObtenerRecetas(Usuario usuario) {
		List<Receta> listaRecetas= this.busqueda.ObtenerRecetas(usuario);	
		List<Receta> listaRecetasAux = new ArrayList<Receta>();
		// Tengo que usar una lista auxiliar porque sino me tira la excepcion de concurrence
		for (Iterator<Receta> iterator = listaRecetas.iterator(); iterator.hasNext();) {
			Receta receta = (Receta) iterator.next();
			//Hay un tema con la visibilidad aca, donde ponemos los ingredientes caros?
			//if (Collections.disjoint(receta.getIngredientes().keySet(), getIngredientesCaros) ){				
				listaRecetasAux.add(receta);
			}
		//}		
		return listaRecetasAux;
	}




}
