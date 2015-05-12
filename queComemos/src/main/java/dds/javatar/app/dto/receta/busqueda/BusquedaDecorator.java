package dds.javatar.app.dto.receta.busqueda;

import java.util.List;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.usuario.Usuario;

public abstract class BusquedaDecorator implements Busqueda{

	protected Busqueda busqueda;
	
	 public BusquedaDecorator(Busqueda busqueda){
	        this.busqueda=busqueda;
	    }
	 
		public List<Receta> ObtenerRecetas (Usuario usuario){
			return this.busqueda.ObtenerRecetas(usuario);
		}
	 
}
