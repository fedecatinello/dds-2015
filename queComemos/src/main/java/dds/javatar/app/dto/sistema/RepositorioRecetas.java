package dds.javatar.app.dto.sistema;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import dds.javatar.app.dto.grupodeusuarios.GrupoDeUsuarios;
import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.receta.busqueda.Buscador;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.util.exception.BusinessException;
import dds.javatar.app.util.exception.FilterException;

public class RepositorioRecetas implements InterfazRepositorioRecetas {

	private List<Receta> recetaConocidas;

	protected RepositorioRecetas() {
		this.recetaConocidas = new ArrayList<Receta>();
	}

	private static RepositorioRecetas instance;

	public static RepositorioRecetas getInstance() {
		if (instance == null) {
			instance = new RepositorioRecetas();
		}
		return instance;
	}

	/** Getter & Setter **/

	@Override
	public void agregar(Receta receta) {
		this.recetaConocidas.add(receta);
	}

	@Override
	public void quitar(Receta receta) throws BusinessException {
		this.recetaConocidas.remove(receta);
	}

	/** Metodos **/

	@Override
	public List<Receta> listarTodas() {
		return this.recetaConocidas;
	}

	public void eliminarTodasLasRecetas() {
		this.recetaConocidas.clear();
	}

	public List<Receta> recetasQueConoceEl(Usuario usuario) {

		List<Receta> recetasQueConoceLista;
		Set<Receta> recetasQueConoceSet = new LinkedHashSet<Receta>(
				this.recetaConocidas);
		recetasQueConoceSet.addAll(usuario.getRecetas());

		if (!usuario.getGruposAlQuePertenece().isEmpty()
				|| usuario.getGruposAlQuePertenece() != null) {
			for (GrupoDeUsuarios grupo : usuario.getGruposAlQuePertenece()) {
				for (Usuario miembroDelGrupo : grupo.getUsuarios()) {
					recetasQueConoceSet.addAll(miembroDelGrupo.getRecetas());
				}
			}
		}
		recetasQueConoceLista = new ArrayList<Receta>(recetasQueConoceSet);
		return recetasQueConoceLista;
	}

	public List<Receta> realizarBusquedaPara(Buscador buscador, Usuario usuario)
			throws FilterException {

		List<Receta> recetasXusuario = this.recetasQueConoceEl(usuario);
		buscador.filtrar(usuario, recetasXusuario);
		buscador.postProcesar(recetasXusuario);
		return recetasXusuario;
	}

}
