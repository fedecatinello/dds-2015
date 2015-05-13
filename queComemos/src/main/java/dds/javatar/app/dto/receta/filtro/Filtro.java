package dds.javatar.app.dto.receta.filtro;

import java.util.List;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.util.exception.FilterException;

public interface Filtro {

	public void filtrarBusqueda(Usuario usuario, List<Receta> receta) throws FilterException;
	
	public boolean validator(Usuario usuario, Receta receta);
	
}
