package dds.javatar.app.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dds.javatar.app.domain.receta.Receta;
import dds.javatar.app.domain.receta.busqueda.Alfabeticamente;
import dds.javatar.app.domain.receta.busqueda.Buscador;
import dds.javatar.app.domain.receta.busqueda.Calorias;
import dds.javatar.app.domain.receta.busqueda.Criterio;
import dds.javatar.app.domain.receta.busqueda.Ordenamiento;
import dds.javatar.app.domain.receta.busqueda.PostProcesamiento;
import dds.javatar.app.domain.receta.busqueda.PrimerosDiez;
import dds.javatar.app.domain.receta.busqueda.ResultadosPares;
import dds.javatar.app.domain.receta.filtro.Filtro;
import dds.javatar.app.domain.receta.filtro.FiltroCondiciones;
import dds.javatar.app.domain.receta.filtro.FiltroPrecio;
import dds.javatar.app.domain.usuario.Usuario;
import dds.javatar.app.util.exception.FilterException;
import dds.javatar.app.util.exception.RecetaException;

public class TestBusquedas {

	private Usuario usuario;

	@Before
	public void initialize() throws RecetaException {
		this.usuario = TestFactory.crearUsuarioBasicoValido();
		TestFactory.crearListaRecetasParaUsuarioSize30(this.usuario);
	}

	/* Tests de Filtros */

	@Test
	public void testBuscarRecetasSinFiltro() throws FilterException {
		Buscador buscador = new Buscador();
		List<Receta> listaRecetas = buscador.realizarBusquedaPara(usuario);
		assertEquals(14, listaRecetas.size()); // Son 2 locales + 12 externas
	}

	@Test
	public void testFiltrarRecetasConExcesoDeCalorias() throws FilterException {
		Buscador buscador = new Buscador();
		List<Filtro> filtros = new ArrayList<Filtro>();
		filtros.add(new FiltroCondiciones());
		buscador.setFiltros(filtros);

		Usuario userSobrepesado = TestFactory.crearUsuarioConSobrepeso();
		List<Receta> listaRecetas = buscador.realizarBusquedaPara(userSobrepesado);
		assertEquals(0, listaRecetas.size());
	}

	@Test
	public void testFiltrarRecetasConIngredientesCaros() throws FilterException, RecetaException {
		Usuario usuarioPedro = TestFactory.crearUsuarioBasicoValido();
		TestFactory.crearListaRecetasParaUsuarioSize30(usuarioPedro);

		Buscador buscador = new Buscador();
		List<Filtro> filtros = new ArrayList<Filtro>();
		FiltroPrecio filtroPrecio = new FiltroPrecio();
		List<String> ingredientesCaros = new ArrayList<String>();
		ingredientesCaros.add("Harina");
		ingredientesCaros.add("sal");
		ingredientesCaros.add("Ravioles");
		filtroPrecio.setIngredientesCaros(ingredientesCaros);
		filtros.add(filtroPrecio);
		buscador.setFiltros(filtros);
		List<Receta> listaRecetas = buscador.realizarBusquedaPara(usuarioPedro);
		assertEquals(14, listaRecetas.size()); // Son 2 locales + 12 externas
	}

	/* Tests de Busquedas */

	@Test
	public void testProcesarDiezPrimeros() throws FilterException {
		Buscador buscador = new Buscador();
		PostProcesamiento primerosDiez = new PrimerosDiez();
		buscador.setPostProcesamiento(primerosDiez);

		List<Filtro> filtros = new ArrayList<Filtro>();
		buscador.setFiltros(filtros);

		List<Receta> listaRecetas = buscador.realizarBusquedaPara(usuario);
		assertEquals(10, listaRecetas.size());
	}

	@Test
	public void cantidadRecetasGeneradas() {
		assertEquals(3, this.usuario.getRecetas().size());
	}

	@Test
	public void testProcesarSoloPares() throws FilterException {
		Buscador buscador = new Buscador();
		PostProcesamiento soloPares = new ResultadosPares();
		buscador.setPostProcesamiento(soloPares);

		List<Receta> listaRecetas = buscador.realizarBusquedaPara(usuario);
		assertEquals(7, listaRecetas.size()); // Son 2 locales + 12 externas =
												// 14 /2 =7
	}

	// revisar: no tiene sentido el assert
	@Test
	public void testProcesarOrdenAlfabetico() throws FilterException {
		Buscador buscador = new Buscador();
		Criterio alfabetico = new Alfabeticamente();
		Ordenamiento orden = new Ordenamiento(alfabetico);
		buscador.setPostProcesamiento(orden);

		List<Filtro> filtros = new ArrayList<Filtro>();
		buscador.setFiltros(filtros);

		List<Receta> listaRecetas = buscador.realizarBusquedaPara(usuario);
		assertEquals(14, listaRecetas.size()); // Son 2 locales + 12 externas
	}

	// revisar: no tiene sentido el assert
	@Test
	public void testProcesarOrdenCalorias() throws FilterException {
		Buscador buscador = new Buscador();
		Criterio calorias = new Calorias();
		Ordenamiento orden = new Ordenamiento(calorias);
		buscador.setPostProcesamiento(orden);

		List<Filtro> filtros = new ArrayList<Filtro>();
		buscador.setFiltros(filtros);

		List<Receta> listaRecetas = buscador.realizarBusquedaPara(usuario);
		assertEquals(14, listaRecetas.size()); // Son 2 locales + 12 externas
	}

}
