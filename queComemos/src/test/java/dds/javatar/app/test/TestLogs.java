package dds.javatar.app.test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.apache.log4j.Appender;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import dds.javatar.app.dto.receta.busqueda.Buscador;
import dds.javatar.app.dto.sistema.Administrador;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.util.exception.FilterException;
import dds.javatar.app.util.exception.RecetaException;





@RunWith(MockitoJUnitRunner.class)
public class TestLogs {
	Usuario usuario1, usuario2;

	@Mock
	private Appender mockAppender;

	@Captor
	private ArgumentCaptor captorLoggingEvent;

	@Before
	public void initialize() {
		usuario1 = TestFactory.crearUsuarioBasicoValido();
		usuario2 = TestFactory.crearUsuarioBasicoValido();
		LogManager.getRootLogger().addAppender(mockAppender);

	}

	@After
	public void teardown() {
		LogManager.getRootLogger().removeAppender(mockAppender);
	}

	@Test
	public void logueaTresConsultas() throws FilterException, RecetaException {

		TestFactory.crearListaRecetasParaUsuarioSize101(usuario1);
		TestFactory.crearListaRecetasParaUsuarioSize101(usuario1);
		TestFactory.crearListaRecetasParaUsuarioSize101(usuario2);
		TestFactory.crearListaRecetasParaUsuarioSize101(usuario2);
		Buscador buscador = new Buscador();
		buscador.realizarBusquedaPara(usuario1);
		buscador.realizarBusquedaPara(usuario1);
		buscador.realizarBusquedaPara(usuario2);
		Administrador.getInstance().realizarTareasPendientes();
		

		verify(mockAppender,times(3)).doAppend(
				(LoggingEvent) captorLoggingEvent.capture());
		LoggingEvent loggingEvent = (LoggingEvent) captorLoggingEvent
				.getValue();
		assertThat(loggingEvent.getLevel(), is(Level.INFO));
		assertThat(
				loggingEvent.getRenderedMessage(),
				is("Consulta de: DonJuan devuelve mas de 100 resultados.(114 resultados)"));
	}

	@Test(expected=Exception.class)
	public void noLogueaConsultas() throws FilterException, RecetaException {
		TestFactory.crearListaRecetasParaUsuarioSize3(usuario1);
		TestFactory.crearListaRecetasParaUsuarioSize3(usuario2);
		Buscador buscador = new Buscador();
		buscador.realizarBusquedaPara(usuario1);
		buscador.realizarBusquedaPara(usuario1);
		buscador.realizarBusquedaPara(usuario2);
		Administrador.getInstance().realizarTareasPendientes();
		
		verify(mockAppender, times(0)).doAppend(
				(LoggingEvent) captorLoggingEvent.capture());
		LoggingEvent loggingEvent = (LoggingEvent) captorLoggingEvent
				.getValue();
		assertThat(loggingEvent.getLevel(), is(Level.INFO));
		assertThat(
				loggingEvent.getRenderedMessage(),
				is("Consulta de: DonJuan devuelve mas de 100 resultados.(113 resultados)"));
	}

}
