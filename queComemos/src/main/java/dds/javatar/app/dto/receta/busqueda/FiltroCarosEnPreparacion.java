package dds.javatar.app.dto.receta.busqueda;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.usuario.Usuario;

public class FiltroCarosEnPreparacion extends FiltroDeBusqueda {

	private static final Set<String> ingredientesCaros = new HashSet<String>(Arrays.asList("lechon", "lomo", "salmon", "alcaparras"));

	public FiltroCarosEnPreparacion(Busqueda busqueda) {
		super(busqueda);
	}

	@Override
	public List<Receta> obtenerRecetasFiltradas(Usuario usuario) {
		List<Receta> listaRecetas = this.busqueda.obtenerRecetasFiltradas(usuario);

		Iterator<Receta> it = listaRecetas.iterator();

		while (it.hasNext()) {
			Receta receta = it.next();
			for (String ingredienteCaro : ingredientesCaros) {
				if (receta.contieneIngrediente(ingredienteCaro)) {
					it.remove();
				}
			}
		}

		return listaRecetas;
	}

}
