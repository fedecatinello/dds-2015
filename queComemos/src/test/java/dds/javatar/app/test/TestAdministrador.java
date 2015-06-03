package dds.javatar.app.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dds.javatar.app.dto.sistema.Administrador;
import dds.javatar.app.dto.sistema.RepositorioRecetas;
import dds.javatar.app.dto.sistema.RepositorioUsuarios;
import dds.javatar.app.dto.sistema.Solicitud;
import dds.javatar.app.dto.usuario.Rutina;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.dto.usuario.Rutina.TipoRutina;

public class TestAdministrador {

	private Usuario usuario;
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
	public void init(){
		List<Usuario> listaUsuarios = new ArrayList<Usuario>();
		repositorioUsuarios.setObjects(listaUsuarios);
	}
	
	@Test
	public void agregarUsuario(){
		Usuario usuario = crearUsuarioBasicoValido();
		repositorioUsuarios.add(usuario);
		assertEquals(1, repositorioUsuarios.allInstances().size());
	}
	
	@Test
	public void eliminarUsuario(){
		Usuario usuario = crearUsuarioBasicoValido();
		repositorioUsuarios.add(usuario);
		assertEquals(1, repositorioUsuarios.allInstances().size());
		repositorioUsuarios.delete(usuario);
		assertEquals(0, repositorioUsuarios.allInstances().size());

	}
	
	@Test
	public void updateUsuario(){
		Usuario usuario = crearUsuarioBasicoValido();
		repositorioUsuarios.updateUsuario(usuario);
		assertEquals(1, repositorioUsuarios.allInstances().size());
	}
	
	@Test
	public void getUsuario(){
		Usuario usuario = crearUsuarioBasicoValido();
		repositorioUsuarios.add(usuario);
		assertTrue(usuario.getNombre().equals(repositorioUsuarios.searchByName(usuario).get(0).getNombre()));
	}
	
	@Test
	public void listUsuario(){
		Usuario usuario = crearUsuarioBasicoValido();
		repositorioUsuarios.add(usuario);
		Usuario otherUsr = crearUsuarioBasicoValidoDiferente();
		repositorioUsuarios.add(otherUsr);
		assertEquals(2, repositorioUsuarios.allInstances().size());
	}
	
	@Test
	public void solicitudAceptadaPorSistema(){
		Solicitud solicitud = new Solicitud();
		administrador.aceptar(solicitud);
		assertEquals("ACEPTADA", repositorioUsuarios.allInstances().get(0).getEstadoSolicitud().toString());
	}
	
	@Test
	public void solicitudRechazadaPorSistema(){
		Solicitud solicitud = new Solicitud();
		administrador.rechazar(solicitud);
		assertEquals("RECHAZADA", repositorioUsuarios.allInstances().get(0).getEstadoSolicitud().toString());
	}
}
