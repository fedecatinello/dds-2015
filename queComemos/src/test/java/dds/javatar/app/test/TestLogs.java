package dds.javatar.app.test;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;
import org.junit.Test;

import dds.javatar.app.dto.receta.busqueda.Buscador;
import dds.javatar.app.dto.sistema.Administrador;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.util.exception.FilterException;
import dds.javatar.app.util.exception.RecetaException;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;



class TestAppender extends AppenderSkeleton {
    private final List<LoggingEvent> log = new ArrayList<LoggingEvent>();

    @Override
    public boolean requiresLayout() {
        return false;
    }

    @Override
    protected void append(final LoggingEvent loggingEvent) {
        log.add(loggingEvent);
    }

    @Override
    public void close() {
    }

    public List<LoggingEvent> getLog() {
        return new ArrayList<LoggingEvent>(log);
    }
}

public class TestLogs {
	
	Usuario usuario1, usuario2;

	@Test
	public void logueaTresConsultas() throws FilterException, RecetaException {

		TestFactory.crearListaRecetasParaUsuarioSize101(usuario1);
		TestFactory.crearListaRecetasParaUsuarioSize101(usuario2);
		Buscador buscador = new Buscador();
		buscador.realizarBusquedaPara(usuario1);
		buscador.realizarBusquedaPara(usuario1);
		buscador.realizarBusquedaPara(usuario2);
		Administrador.getInstance().realizarTareasPendientes();
		
		  final TestAppender appender = new TestAppender();
	        final Logger logger = Logger.getRootLogger();
	        logger.addAppender(appender);
	        try {
	            Logger.getLogger(TestLogs.class).info("Test");
	        }
	        finally {
	            logger.removeAppender(appender);
	        }

	        final List<LoggingEvent> log = appender.getLog();
	        final LoggingEvent firstLogEntry = log.get(0);
	        assertThat(firstLogEntry.getLevel(), is(Level.INFO));
	        assertThat((String) firstLogEntry.getMessage(), is("Test"));
	        assertThat(firstLogEntry.getLoggerName(), is("MyTest"));
	}

	@Test
	public void noLogueaConsultas() throws FilterException, RecetaException {
		TestFactory.crearListaRecetasParaUsuarioSize3(usuario1);
		TestFactory.crearListaRecetasParaUsuarioSize3(usuario2);
		Buscador buscador = new Buscador();
		buscador.realizarBusquedaPara(usuario1);
		buscador.realizarBusquedaPara(usuario1);
		buscador.realizarBusquedaPara(usuario2);
		Administrador.getInstance().realizarTareasPendientes();
		
		final TestAppender appender = new TestAppender();
        final Logger logger = Logger.getRootLogger();
        logger.addAppender(appender);
        try {
            Logger.getLogger(TestLogs.class).info("Test");
        }
        finally {
            logger.removeAppender(appender);
        }

        final List<LoggingEvent> log = appender.getLog();
        final LoggingEvent firstLogEntry = log.get(0);
        assertThat(firstLogEntry.getLevel(), is(Level.INFO));
        assertThat((String) firstLogEntry.getMessage(), is("Test"));
        assertThat(firstLogEntry.getLoggerName(), is("MyTest"));

	}

}
