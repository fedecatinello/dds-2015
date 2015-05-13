package dds.javatar.app.dto.receta.busqueda;

import java.util.List;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.sistema.Sistema;
import dds.javatar.app.dto.usuario.Usuario;

public class BuscarTodas implements Busqueda {

	@Override
	public List<Receta> obtenerRecetasFiltradas(Usuario usuario) {
		return Sistema.getInstance().recetasQueConoceEl(usuario);
	}

}
