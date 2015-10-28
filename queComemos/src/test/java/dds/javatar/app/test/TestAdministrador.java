package dds.javatar.app.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import dds.javatar.app.domain.sistema.Administrador;
import dds.javatar.app.domain.sistema.RepositorioUsuarios;
import dds.javatar.app.domain.sistema.Solicitud;
import dds.javatar.app.domain.usuario.Usuario;

public class TestAdministrador {

	private RepositorioUsuarios repositorioUsuarios = RepositorioUsuarios.getInstance();
	private Administrador administrador = Administrador.getInstance();
	private Usuario usuario;

	@Test
	public void agregarUsuario() {
		this.repositorioUsuarios.add(this.usuario);
		assertEquals(2, this.repositorioUsuarios.allInstances().size());
	}

	@Test
	public void eliminarUsuario() {
		this.usuario = TestFactory.crearUsuarioBasicoValido();
		this.repositorioUsuarios.add(this.usuario);
		assertEquals(3, this.repositorioUsuarios.allInstances().size());
		this.repositorioUsuarios.delete(this.usuario);
		assertEquals(2, this.repositorioUsuarios.allInstances().size());

	}

	@Test
	public void updateUsuario() {
		this.repositorioUsuarios.updateUsuario(this.usuario);
		assertEquals(2, this.repositorioUsuarios.allInstances().size());
	}

	@Test
	public void getUsuario() {
		List<Usuario> listaUsuarios = new ArrayList<Usuario>();
		this.repositorioUsuarios.setObjects(listaUsuarios);
		this.usuario = TestFactory.crearUsuarioBasicoValido();
		this.usuario = TestFactory.crearUsuarioBasicoValido();
		this.repositorioUsuarios.add(this.usuario);
		assertTrue(this.usuario.getNombre().equals(this.repositorioUsuarios.searchByName(this.usuario).get(0).getNombre()));
	}

	@Test
	public void listUsuario() {
		this.repositorioUsuarios.add(this.usuario);
		Usuario otherUsr = TestFactory.crearUsuarioBasicoValidoDiferente();
		this.repositorioUsuarios.add(otherUsr);
		assertEquals(4, this.repositorioUsuarios.allInstances().size());
	}

	@Test
	public void solicitudAceptadaPorSistema() {
		List<Usuario> listaUsuarios = new ArrayList<Usuario>();
		this.repositorioUsuarios.setObjects(listaUsuarios);
		this.usuario = TestFactory.crearUsuarioBasicoValido();
		Solicitud solicitud = new Solicitud();
		this.administrador.aceptar(solicitud);
		assertEquals("ACEPTADA", this.repositorioUsuarios.allInstances().get(0).getEstadoSolicitud().toString());
	}

	@Test
	public void solicitudRechazadaPorSistema() {
		Solicitud solicitud = new Solicitud();
		this.administrador.rechazar(solicitud);
		assertEquals("RECHAZADA", this.repositorioUsuarios.allInstances().get(0).getEstadoSolicitud().toString());
	}
}
