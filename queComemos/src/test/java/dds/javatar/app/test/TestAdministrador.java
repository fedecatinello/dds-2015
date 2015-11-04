package dds.javatar.app.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import dds.javatar.app.domain.sistema.Administrador;
import dds.javatar.app.domain.sistema.RepositorioUsuarios;
import dds.javatar.app.domain.sistema.Solicitud;
import dds.javatar.app.domain.usuario.Usuario;

public class TestAdministrador {

	private RepositorioUsuarios repositorioUsuarios = RepositorioUsuarios.getInstance();
	private Administrador administrador = Administrador.getInstance();
	private Usuario usuario = TestFactory.crearUsuarioBasicoValido();

	@Test
	public void agregarUsuario() {
		this.usuario = TestFactory.crearUsuarioBasicoValido();
		this.repositorioUsuarios.add(this.usuario);
		assertEquals(7, this.repositorioUsuarios.getAll().size());
	}

	@Test
	public void eliminarUsuario() {
		this.usuario = TestFactory.crearUsuarioBasicoValido();
		this.repositorioUsuarios.add(this.usuario);
		assertEquals(7, this.repositorioUsuarios.getAll().size());
		this.repositorioUsuarios.delete(this.usuario);
		assertEquals(6, this.repositorioUsuarios.getAll().size());
	}

	@Test
	public void updateUsuario() {
		this.repositorioUsuarios.update(this.usuario);
		assertEquals(7, this.repositorioUsuarios.getAll().size());
	}

	@Test
	public void getUsuario() {
		// List<Usuario> listaUsuarios = new ArrayList<Usuario>();
		// this.repositorioUsuarios.add(listaUsuarios);
		this.usuario = TestFactory.crearUsuarioBasicoValido();
		this.repositorioUsuarios.add(this.usuario);
		assertTrue(this.usuario.getNombre().equals(this.repositorioUsuarios.searchByName(this.usuario).get(0).getNombre()));
	}

	@Test
	public void listUsuario() {
		this.repositorioUsuarios.add(this.usuario);
		Usuario otherUsr = TestFactory.crearUsuarioBasicoValidoDiferente();
		this.repositorioUsuarios.add(otherUsr);
		assertEquals(7, this.repositorioUsuarios.getAll().size());
	}

}
