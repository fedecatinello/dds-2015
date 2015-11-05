package dds.javatar.app.dto.sistema;

import java.util.HashSet;
import java.util.Set;

import dds.javatar.app.dto.grupodeusuarios.GrupoDeUsuarios;
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
		for (String ingrediente : receta.getIngredientes().keySet()) {
			if (!usuario.validarSiAceptaReceta(receta) || usuario.tieneAlimentoQueLeDisguste(ingrediente)) {
				throw new BusinessException("la receta: " + receta.getNombre() + " no puede ser sugerida al usuario" + usuario.getNombre());
			}
		}
	}

	public void sugerir(Receta receta, GrupoDeUsuarios grupo) throws BusinessException {
		for (String preferencia : grupo.getPreferenciasAlimenticias().keySet()) {

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
		this.repositorioUsuarios.add(usuario);
	}

	public void rechazar(Solicitud solicitud) {
		Usuario usuario = solicitud.build();
		usuario.setEstadoSolicitud(EstadoSolicitud.RECHAZADA);
		this.repositorioUsuarios.add(usuario);
	}

	public void agregarTareaPendiente(TareaPendiente tareaPendiente) {
		this.tareasPendientes.add(tareaPendiente);
	}

	public void realizarTareasPendientes() {
		this.tareasPendientes.forEach((tareaPendiente) -> tareaPendiente.execute());
		this.tareasPendientes.clear();
	}

}
