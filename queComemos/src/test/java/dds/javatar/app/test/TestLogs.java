package dds.javatar.app.test;

import org.junit.Before;
import org.junit.Test;

import dds.javatar.app.dto.receta.busqueda.Buscador;
import dds.javatar.app.dto.sistema.Administrador;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.util.exception.FilterException;
import dds.javatar.app.util.exception.RecetaException;

public class TestLogs {
	Usuario usuario1, usuario2;

	@Before
	public void initialize() {
		usuario1 = TestFactory.crearUsuarioBasicoValido();
		usuario2 = TestFactory.crearUsuarioBasicoValido();		
	}

	@Test
	public void logueaTresConsultas() throws FilterException, RecetaException{
	
		TestFactory.crearListaRecetasParaUsuarioSize101(usuario1);
		TestFactory.crearListaRecetasParaUsuarioSize101(usuario2);
		Buscador buscador = new Buscador();
		buscador.realizarBusquedaPara(usuario1);
		buscador.realizarBusquedaPara(usuario1);
		buscador.realizarBusquedaPara(usuario2);
		Administrador.getInstance().realizarTareasPendientes();
		// TODO assert
	}
	
	@Test
	public void noLogueaConsultas() throws FilterException, RecetaException{
		TestFactory.crearListaRecetasParaUsuarioSize3(usuario1);
		TestFactory.crearListaRecetasParaUsuarioSize3(usuario2);
		Buscador buscador = new Buscador();
		buscador.realizarBusquedaPara(usuario1);
		buscador.realizarBusquedaPara(usuario1);
		buscador.realizarBusquedaPara(usuario2);
		Administrador.getInstance().realizarTareasPendientes();
		// TODO assert
	}
	

}
