package dds.javatar.app.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dds.javatar.app.domain.receta.busqueda.Buscador;
import dds.javatar.app.domain.sistema.Administrador;
import dds.javatar.app.domain.tareasPendientes.MailBusqueda;
import dds.javatar.app.domain.tareasPendientes.mail.MailSender;
import dds.javatar.app.domain.usuario.Usuario;
import dds.javatar.app.util.exception.FilterException;

public class TestMails {

	private class MailSenderTest implements MailSender {

		private int count;

		@Override
		public void send(String to, String message) {
			this.count++;
		}

		public int getCount() {
			return this.count;
		}

		public void resetCount() {
			this.count = 0;
		}

	}

	private MailSenderTest mailSenderTest;

	@Before
	public void initialize() {
		this.mailSenderTest = new MailSenderTest();
		MailBusqueda.setMailSender(this.mailSenderTest);
	}

	@Test
	public void testEnvioMail() throws FilterException {
		this.mailSenderTest.resetCount();
		Usuario usuario = new Usuario.UsuarioBuilder().nombre("jcontardo")
			.build();
		Buscador buscador = new Buscador();
		buscador.realizarBusquedaPara(usuario);
		Administrador.getInstance()
			.realizarTareasPendientes();

		Assert.assertEquals(1, this.mailSenderTest.getCount());
	}

	@Test
	public void testNoEnvioMail() throws FilterException {
		this.mailSenderTest.resetCount();
		Usuario usuario = new Usuario.UsuarioBuilder().nombre("jose")
			.build();
		Buscador buscador = new Buscador();
		buscador.realizarBusquedaPara(usuario);
		Administrador.getInstance()
			.realizarTareasPendientes();

		Assert.assertEquals(0, this.mailSenderTest.getCount());
	}

	@Test
	public void testEnvioDosMails() throws FilterException {
		this.mailSenderTest.resetCount();
		Usuario usuario = new Usuario.UsuarioBuilder().nombre("jcontardo")
			.build();
		Buscador buscador = new Buscador();
		buscador.realizarBusquedaPara(usuario);
		buscador.realizarBusquedaPara(usuario);

		Administrador.getInstance()
			.realizarTareasPendientes();
		Assert.assertEquals(2, this.mailSenderTest.getCount());
	}

}
