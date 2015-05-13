package dds.javatar.app.dto.receta.filtro;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.receta.busqueda.Busqueda;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.util.exception.FilterException;

public interface Filtro {

	public void filtrarBusqueda(Busqueda busqueda) throws FilterException;
	
	public boolean validator(Usuario usuario, Receta receta);
	
}
