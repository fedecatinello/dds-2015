package dds.javatar.app.dto.receta.busqueda;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import dds.javatar.app.dto.grupodeusuarios.GrupoDeUsuarios;
import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.receta.busqueda.adapter.BusquedaAdapter;
import dds.javatar.app.dto.receta.filtro.Filtro;
import dds.javatar.app.dto.sistema.Administrador;
import dds.javatar.app.dto.sistema.RepositorioRecetas;
import dds.javatar.app.dto.tareasPendientes.AgregarFavoritas;
import dds.javatar.app.dto.tareasPendientes.LogMuchosResultados;
import dds.javatar.app.dto.tareasPendientes.MailBusqueda;
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
		List<Receta> recetasEncontradas = BusquedaAdapter.getInstance().consultarRecetas(usuario, busqueda);
		return recetasEncontradas;
	}

	public List<Receta> recetasQueConoceEl(Usuario usuario) {

		List<Receta> recetasQueConoceLista = new ArrayList<Receta>();
		Set<Receta> recetasQueConoceSet = new LinkedHashSet<Receta>(RepositorioRecetas.getInstance().getAll());
		recetasQueConoceSet.addAll(usuario.getRecetas());

		if (!usuario.getGruposAlQuePertenece().isEmpty() || usuario.getGruposAlQuePertenece() != null) {
			for (GrupoDeUsuarios grupo : usuario.getGruposAlQuePertenece()) {
				for (Usuario miembroDelGrupo : grupo.getUsuarios()) {
					recetasQueConoceSet.addAll(miembroDelGrupo.getRecetas());
				}
			}
		}

		for (Receta unaReceta : recetasQueConoceSet) {
			if (unaReceta.getAutor() == null || unaReceta.getAutor().equals(usuario.getNombre())) {
				recetasQueConoceLista.add(unaReceta);
			}
		}

		return recetasQueConoceLista;
	}

	public List<Receta> realizarBusquedaPara(Usuario usuario) throws FilterException {
		Busqueda busqueda = new Busqueda.BusquedaBuilder().build();
		return this.realizarBusquedaPara(usuario, busqueda);
	}

	public List<Receta> realizarBusquedaPara(Usuario usuario, Busqueda busqueda) throws FilterException {
		List<Receta> recetasXusuario = this.recetasQueConoceEl(usuario);
		//List<Receta> recetasRepoExterno = this.buscarRecetasExternas(usuario, busqueda);
		//recetasXusuario.addAll(recetasRepoExterno);
		this.filtrar(usuario, recetasXusuario);
		this.postProcesar(recetasXusuario);

		LogMuchosResultados logMuchosResultados = new LogMuchosResultados(usuario, recetasXusuario);
		Administrador.getInstance().agregarTareaPendiente(logMuchosResultados);

		Administrador.getInstance().agregarTareaPendiente(new MailBusqueda(usuario, busqueda, recetasXusuario));

		Administrador.getInstance().agregarTareaPendiente(new AgregarFavoritas(usuario, recetasXusuario));
		return recetasXusuario;
	}

}
