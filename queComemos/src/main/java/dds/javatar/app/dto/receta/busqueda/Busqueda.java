package dds.javatar.app.dto.receta.busqueda;


import java.util.List;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.usuario.Usuario;

public interface Busqueda {
	
	public List<Receta> ObtenerRecetas (Usuario usuario);

}
