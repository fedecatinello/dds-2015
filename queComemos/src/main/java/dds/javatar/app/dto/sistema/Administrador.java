package dds.javatar.app.dto.sistema;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dds.javatar.app.dto.grupodeusuarios.GrupoDeUsuarios;
import dds.javatar.app.dto.receta.Componente;
import dds.javatar.app.dto.receta.PreferenciaGrupo;
import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.tareasPendientes.TareaPendiente;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.dto.usuario.Usuario.EstadoSolicitud;
import dds.javatar.app.util.exception.BusinessException;

public class Administrador {

	private static Administrador instance;
	private RepositorioUsuarios repositorioUsuarios = RepositorioUsuarios.getInstance();
	private Set<TareaPendiente> tareasPendientes;

	private Administrador() {
		this.tareasPendientes = new HashSet<TareaPendiente>();
	}

	public static Administrador getInstance() {
		if (instance == null) {
			instance = new Administrador();
		}
		return instance;
	}

	public void sugerir(Receta receta, Usuario usuario) throws BusinessException {
		for (String ingrediente : this.getComponentesByNombre(receta.getIngredientes())) {
			if (!usuario.validarSiAceptaReceta(receta) || usuario.tieneAlimentoQueLeDisguste(ingrediente)) {
				throw new BusinessException("la receta: " + receta.getNombre() + " no puede ser sugerida al usuario" + usuario.getNombre());
			}
		}
	}

	public void sugerir(Receta receta, GrupoDeUsuarios grupo) throws BusinessException {
		for (String preferencia : this.getComponentesByNombrePorGrupo(grupo.getPreferenciasAlimenticias())) {

			if (!receta.contieneCondimento(preferencia) || !receta.contieneIngrediente(preferencia) || !(receta.getNombre() == preferencia)) {
				throw new BusinessException("La receta:" + receta.getNombre() + " no contiene palabra clave del grupo:" + grupo.getNombre());
			}
			for (Usuario integrante : grupo.getUsuarios()) {
				integrante.validarSiAceptaReceta(receta);
			}
		}
	}

	public void aceptar(Solicitud solicitud) {
		Usuario usuario = solicitud.build();
		usuario.setEstadoSolicitud(EstadoSolicitud.ACEPTADA);
		this.repositorioUsuarios.saveOrUpdate(usuario);
	}

	public void rechazar(Solicitud solicitud) {
		Usuario usuario = solicitud.build();
		usuario.setEstadoSolicitud(EstadoSolicitud.RECHAZADA);
		this.repositorioUsuarios.saveOrUpdate(usuario);
	}

	public void agregarTareaPendiente(TareaPendiente tareaPendiente) {
		this.tareasPendientes.add(tareaPendiente);
	}

	public void realizarTareasPendientes() {
		this.tareasPendientes.forEach((tareaPendiente) -> tareaPendiente.execute());
		this.tareasPendientes.clear();
	}

	public Set<String> getComponentesByNombrePorGrupo(List<PreferenciaGrupo> preferencias){
		Set<String> nombres = new HashSet<>();
		for(PreferenciaGrupo pref: preferencias){
			nombres.add(pref.getComponente().getDescripcion());
		}
		return nombres;
	}
	
	public Set<String> getComponentesByNombre(List<Componente> componentes){
		Set<String> nombres = new HashSet<>();
		for(Componente componente: componentes){
			nombres.add(componente.getDescripcion());
		}
		return nombres;
	}
	
}
