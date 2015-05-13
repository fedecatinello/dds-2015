package dds.javatar.app.dto.receta.busqueda;

import java.util.List;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.usuario.Usuario;

public abstract class FiltroDeBusqueda implements Busqueda {

	protected Busqueda busqueda;

	public FiltroDeBusqueda(Busqueda busqueda) {
		this.busqueda = busqueda;
	}

	@Override
	public List<Receta> obtenerRecetasFiltradas(Usuario usuario) {
		return this.busqueda.obtenerRecetasFiltradas(usuario);
	}

}
