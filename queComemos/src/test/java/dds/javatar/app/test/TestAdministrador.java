package dds.javatar.app.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dds.javatar.app.dto.sistema.Administrador;
import dds.javatar.app.dto.sistema.RepositorioUsuarios;
import dds.javatar.app.dto.sistema.Solicitud;
import dds.javatar.app.dto.usuario.Rutina;
import dds.javatar.app.dto.usuario.Rutina.TipoRutina;
import dds.javatar.app.dto.usuario.Usuario;

public class TestAdministrador {

	private RepositorioUsuarios repositorioUsuarios = RepositorioUsuarios.getInstance();
	private Administrador administrador = Administrador.getInstance();

	private Usuario crearUsuarioBasicoValido() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.YEAR, -1);

		Usuario usuario = new Usuario();
		usuario.setFechaNacimiento(calendar.getTime());
		usuario.setNombre("Nombre del usuario");
		usuario.setSexo(Usuario.Sexo.MASCULINO);
		usuario.setPeso(new BigDecimal(70));
		usuario.setAltura(new BigDecimal(1.77));
		usuario.setRutina(new Rutina(TipoRutina.FUERTE, 20));

		return usuario;
	}

	private Usuario crearUsuarioBasicoValidoDiferente() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.YEAR, -1);

		Usuario usuario = new Usuario();
		usuario.setFechaNacimiento(calendar.getTime());
		usuario.setNombre("Nombre");
		usuario.setSexo(Usuario.Sexo.FEMENINO);
		usuario.setPeso(new BigDecimal(20));
		usuario.setAltura(new BigDecimal(1.77));
		usuario.setRutina(new Rutina(TipoRutina.LEVE, 20));

		return usuario;
	}

	@Before
	public void init() {
		List<Usuario> listaUsuarios = new ArrayList<Usuario>();
		this.repositorioUsuarios.setObjects(listaUsuarios);
	}

	@Test
	public void agregarUsuario() {
		Usuario usuario = this.crearUsuarioBasicoValido();
		this.repositorioUsuarios.add(usuario);
		assertEquals(1, this.repositorioUsuarios.allInstances().size());
	}

	@Test
	public void eliminarUsuario() {
		Usuario usuario = this.crearUsuarioBasicoValido();
		this.repositorioUsuarios.add(usuario);
		assertEquals(1, this.repositorioUsuarios.allInstances().size());
		this.repositorioUsuarios.delete(usuario);
		assertEquals(0, this.repositorioUsuarios.allInstances().size());

	}

	@Test
	public void updateUsuario() {
		Usuario usuario = this.crearUsuarioBasicoValido();
		this.repositorioUsuarios.updateUsuario(usuario);
		assertEquals(1, this.repositorioUsuarios.allInstances().size());
	}

	@Test
	public void getUsuario() {
		Usuario usuario = this.crearUsuarioBasicoValido();
		this.repositorioUsuarios.add(usuario);
		assertTrue(usuario.getNombre().equals(this.repositorioUsuarios.searchByName(usuario).get(0).getNombre()));
	}

	@Test
	public void listUsuario() {
		Usuario usuario = this.crearUsuarioBasicoValido();
		this.repositorioUsuarios.add(usuario);
		Usuario otherUsr = this.crearUsuarioBasicoValidoDiferente();
		this.repositorioUsuarios.add(otherUsr);
		assertEquals(2, this.repositorioUsuarios.allInstances().size());
	}

	@Test
	public void solicitudAceptadaPorSistema() {
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
