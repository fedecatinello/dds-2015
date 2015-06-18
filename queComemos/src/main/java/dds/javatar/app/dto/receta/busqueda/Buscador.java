package dds.javatar.app.dto.receta.busqueda;

import java.util.ArrayList;
import java.util.List;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.receta.busqueda.adapter.BusquedaAdapter;
import dds.javatar.app.dto.receta.filtro.Filtro;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.util.exception.FilterException;

public class Buscador {

	private List<Filtro> filtros;

	private PostProcesamiento postProcesamiento;

	public Buscador() {
		this.filtros = new ArrayList<Filtro>();
		this.postProcesamiento = null;
	}

	public void filtrar(Usuario usuario, List<Receta> recetas) throws FilterException {
		if (!this.filtros.isEmpty()) {
			for (Filtro filtro : this.filtros) {
				filtro.filtrarBusqueda(usuario, recetas);
			}
		}
	}

	public void postProcesar(List<Receta> recetasXusuario) {
		if (this.postProcesamiento != null)
			this.postProcesamiento.procesar(recetasXusuario);
	}

	public PostProcesamiento getPostProcesamiento() {
		return this.postProcesamiento;
	}

	public void setPostProcesamiento(PostProcesamiento postProcesamiento) {
		this.postProcesamiento = postProcesamiento;
	}

	public List<Filtro> getFiltros() {
		return this.filtros;
	}

	public void setFiltros(List<Filtro> filtros) {
		this.filtros = filtros;
	}

	public List<Receta> buscarRecetasExternas(Usuario usuario, Busqueda busqueda) {
		List<Receta> recetasEncontradas = BusquedaAdapter.getInstance().consultarRecetas(usuario,busqueda);
		return recetasEncontradas;
	}

}
