package dds.javatar.app.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dds.javatar.app.dto.sistema.Administrador;
import dds.javatar.app.dto.sistema.RepositorioUsuarios;
import dds.javatar.app.dto.sistema.Solicitud;
import dds.javatar.app.dto.usuario.Usuario;

public class TestAdministrador {

	private RepositorioUsuarios repositorioUsuarios = RepositorioUsuarios
		.getInstance();
	private Administrador administrador = Administrador.getInstance();
	private Usuario usuario;

	@Test
	public void agregarUsuario() {
		this.repositorioUsuarios.add(usuario);
		assertEquals(2, this.repositorioUsuarios.allInstances().size());
	}

	@Test
	public void eliminarUsuario() {
		usuario = TestFactory.crearUsuarioBasicoValido();
		this.repositorioUsuarios.add(usuario);
		assertEquals(3, this.repositorioUsuarios.allInstances().size());
		this.repositorioUsuarios.delete(usuario);
		assertEquals(2, this.repositorioUsuarios.allInstances().size());

	}

	@Test
	public void updateUsuario() {
		this.repositorioUsuarios.updateUsuario(usuario);
		assertEquals(2, this.repositorioUsuarios.allInstances().size());
	}

	@Test
	public void getUsuario() {
		List<Usuario> listaUsuarios = new ArrayList<Usuario>();
		this.repositorioUsuarios.setObjects(listaUsuarios);
		usuario = TestFactory.crearUsuarioBasicoValido();
		usuario = TestFactory.crearUsuarioBasicoValido();
		this.repositorioUsuarios.add(usuario);
		assertTrue(usuario.getNombre().equals(
				this.repositorioUsuarios
					.searchByName(usuario)
						.get(0)
						.getNombre()));
	}

	@Test
	public void listUsuario() {
		this.repositorioUsuarios.add(usuario);
		Usuario otherUsr = TestFactory.crearUsuarioBasicoValidoDiferente();
		this.repositorioUsuarios.add(otherUsr);
		assertEquals(4, this.repositorioUsuarios.allInstances().size());
	}

	@Test
	public void solicitudAceptadaPorSistema() {
		List<Usuario> listaUsuarios = new ArrayList<Usuario>();
		this.repositorioUsuarios.setObjects(listaUsuarios);
		usuario = TestFactory.crearUsuarioBasicoValido();
		Solicitud solicitud = new Solicitud();
		this.administrador.aceptar(solicitud);
		assertEquals("ACEPTADA", this.repositorioUsuarios
			.allInstances()
				.get(0)
				.getEstadoSolicitud()
				.toString());
	}

	@Test
	public void solicitudRechazadaPorSistema() {
		Solicitud solicitud = new Solicitud();
		this.administrador.rechazar(solicitud);
		assertEquals("RECHAZADA", this.repositorioUsuarios
			.allInstances()
				.get(0)
				.getEstadoSolicitud()
				.toString());
	}
}
