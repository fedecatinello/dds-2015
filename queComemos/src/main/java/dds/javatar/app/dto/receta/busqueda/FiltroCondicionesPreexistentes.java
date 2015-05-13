package dds.javatar.app.dto.receta.busqueda;

import java.util.Iterator;
import java.util.List;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.usuario.Usuario;

public class FiltroCondicionesPreexistentes extends FiltroDeBusqueda {

	public FiltroCondicionesPreexistentes(Busqueda busqueda) {
		super(busqueda);
	}

	@Override
	public List<Receta> obtenerRecetasFiltradas(Usuario usuario) {
		List<Receta> listaRecetas = this.busqueda.obtenerRecetasFiltradas(usuario);

		Iterator<Receta> it = listaRecetas.iterator();

		while (it.hasNext()) {
			Receta receta = it.next();
			if (!usuario.validarSiAceptaReceta(receta)) {
				it.remove();
			}
		}

		return listaRecetas;
	}

}
