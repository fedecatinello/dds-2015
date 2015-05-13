package dds.javatar.app.dto.receta.filtro;

import java.util.List;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.receta.busqueda.Busqueda;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.util.exception.FilterException;

public abstract class FiltroTemplate implements Filtro {

	public void filtrarBusqueda(Busqueda busqueda) throws FilterException {
		
		Usuario usuarioBusqueda = busqueda.getUsuario();
		
		List<Receta> recetasUsuario = busqueda.getRecetasXusuario();
		
		for(Receta receta : recetasUsuario){
			
			if(!validator(usuarioBusqueda,receta)){
				
				recetasUsuario.remove(receta);
				
				throw new FilterException("No se pudo aplicar el "+ this.getClass().getName() +" en la busqueda solicitada.");
			}
		}

	}

}

