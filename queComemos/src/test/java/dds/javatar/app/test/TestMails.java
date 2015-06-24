package dds.javatar.app.test;

import org.junit.Before;
import org.junit.Test;

import dds.javatar.app.dto.receta.busqueda.Buscador;
import dds.javatar.app.dto.sistema.Administrador;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.util.exception.FilterException;

public class TestMails {

	@Before
	public void initialize() {
	}

	@Test
	public void testEnvioMail() throws FilterException {
		Usuario usuario = new Usuario.UsuarioBuilder().nombre("jcontardo").build();
		Buscador buscador = new Buscador();
		buscador.realizarBusquedaPara(usuario);
		Administrador.getInstance().realizarTareasPendientes();
		// TODO assert
	}

}
