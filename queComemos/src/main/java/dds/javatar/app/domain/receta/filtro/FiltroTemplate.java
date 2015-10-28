package dds.javatar.app.domain.receta.filtro;

import java.util.ArrayList;
import java.util.List;

import dds.javatar.app.domain.receta.Receta;
import dds.javatar.app.domain.usuario.Usuario;
import dds.javatar.app.util.exception.FilterException;

public abstract class FiltroTemplate implements Filtro {

	public void filtrarBusqueda(Usuario usuarioBusqueda, List<Receta> recetasUsuario) throws FilterException {
		List<Receta> listaAux = new ArrayList<Receta>(recetasUsuario);
		for (int i = 0; i < listaAux.size(); i++) {
			if (validator(usuarioBusqueda, listaAux.get(i))) {
				recetasUsuario.remove(listaAux.get(i));
			}
		}
	}
}
