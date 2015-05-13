package dds.javatar.app.dto.receta.busqueda;

import java.util.Iterator;
import java.util.List;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.usuario.Usuario;

public class FiltroExcesoCalorias extends FiltroDeBusqueda {

	public FiltroExcesoCalorias(Busqueda busqueda) {
		super(busqueda);
	}

	@Override
	public List<Receta> obtenerRecetasFiltradas(Usuario usuario) {
		List<Receta> listaRecetas = this.busqueda.obtenerRecetasFiltradas(usuario);

		
		if (usuario.tieneSobrePeso()) {
			Iterator<Receta> it = listaRecetas.iterator();
			
			while (it.hasNext()) {
				Receta receta = it.next();
				
				/*if ("AAA".equals(receta.getNombre())) {
					if (receta.getCalorias() > 500) {
						it.remove();
					}
				}*/
				if (receta.getCalorias() > 500) {
					it.remove();
				}
			}
		}

		return listaRecetas;
	}

}
