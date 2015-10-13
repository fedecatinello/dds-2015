package dds.javatar.app.dto.receta.filtro;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dds.javatar.app.dto.receta.Componente;
import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.usuario.Usuario;
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
	
	public Set<String> getComponentesByNombre(List<Componente> componentes){
		Set<String> nombres = new HashSet<>();
		for(Componente componente:componentes){
			nombres.add(componente.getDescripcion());
		}
		return nombres;
	}
}
