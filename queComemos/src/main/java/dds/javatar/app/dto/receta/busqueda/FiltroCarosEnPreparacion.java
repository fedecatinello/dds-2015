package dds.javatar.app.dto.receta.busqueda;

import java.util.List;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.usuario.Usuario;

public class FiltroCarosEnPreparacion extends BusquedaDecorator {

	public FiltroCarosEnPreparacion(Busqueda busqueda) {
		super(busqueda);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Receta> ObtenerRecetas(Usuario usuario) {
		List<Receta> listaRecetas= this.busqueda.ObtenerRecetas(usuario);
		//Hago algo
		return listaRecetas;
	}




}
