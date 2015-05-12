package dds.javatar.app.dto.receta.busqueda;

import java.util.List;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.sistema.Sistema;
import dds.javatar.app.dto.usuario.Usuario;

public class BuscarTodas extends Sistema implements Busqueda {

	public List<Receta> ObtenerRecetas (Usuario usuario){
		return recetasQueConoceEl(usuario);
	}
	
}
