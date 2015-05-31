package dds.javatar.app.dto.receta.filtro;

import java.util.List;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.util.exception.FilterException;

public abstract class FiltroTemplate implements Filtro {

	public void filtrarBusqueda(Usuario usuarioBusqueda, List<Receta> recetasUsuario) throws FilterException {
		
		for(int i=0; i<recetasUsuario.size(); i++){			
			if(validator(usuarioBusqueda,recetasUsuario.get(i))){				
				recetasUsuario.remove(recetasUsuario.get(i));				
			}
		}
	}
}

