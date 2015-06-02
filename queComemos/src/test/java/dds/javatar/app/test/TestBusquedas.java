package dds.javatar.app.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.receta.busqueda.Alfabeticamente;
import dds.javatar.app.dto.receta.busqueda.Busqueda;
import dds.javatar.app.dto.receta.busqueda.Calorias;
import dds.javatar.app.dto.receta.busqueda.Criterio;
import dds.javatar.app.dto.receta.busqueda.Ordenamiento;
import dds.javatar.app.dto.receta.busqueda.PostProcesamiento;
import dds.javatar.app.dto.receta.busqueda.PrimerosDiez;
import dds.javatar.app.dto.receta.busqueda.ResultadosPares;
import dds.javatar.app.dto.receta.filtro.Filtro;
import dds.javatar.app.dto.receta.filtro.FiltroCondiciones;
import dds.javatar.app.dto.receta.filtro.FiltroPrecio;
import dds.javatar.app.dto.sistema.RepositorioRecetas;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.util.exception.FilterException;
import dds.javatar.app.util.exception.RecetaException;

public class TestBusquedas extends TestGeneralAbstract{

	private Usuario usuario;
	
	
	@Before
	public void initialize() throws RecetaException {
		this.usuario = this.crearUsuarioBasicoValido();
		crearListaRecetasParaUsuarioSize30(this.usuario);
	}
	

	
	/* Tests de Filtros */ 
	
	@Test
	public void testBuscarRecetasSinFiltro() throws FilterException {
		RepositorioRecetas.getInstance().eliminarTodasLasRecetas();
		Busqueda busqueda = new Busqueda();
		List<Receta> listaRecetas = RepositorioRecetas.getInstance().realizarBusquedaPara(busqueda, usuario);
		assertEquals(30 , listaRecetas.size());
	}
	
	@Test
	public void testFiltrarRecetasConExcesoDeCalorias() throws FilterException {
		RepositorioRecetas.getInstance().eliminarTodasLasRecetas();
		Busqueda busqueda = new Busqueda(); 
		List<Filtro> filtros = new ArrayList<Filtro>();
		filtros.add(new FiltroCondiciones());
		busqueda.setFiltros(filtros);
		
		Usuario sobrepesado = crearUsuarioConSobrepeso();
		List<Receta> listaRecetas = RepositorioRecetas.getInstance().realizarBusquedaPara(busqueda, sobrepesado);
		assertEquals(0, listaRecetas.size());
	}
	
	@Test
	public void testFiltrarRecetasConIngredientesCaros() throws FilterException, RecetaException {
		RepositorioRecetas.getInstance().eliminarTodasLasRecetas();
		Usuario usuarioPedro = this.crearUsuarioBasicoValido();
		crearListaRecetasParaUsuarioSize30(usuarioPedro);
		
		Busqueda busqueda = new Busqueda(); 
		List<Filtro> filtros = new ArrayList<Filtro>();
		FiltroPrecio filtroPrecio = new FiltroPrecio();
		List<String> ingredientesCaros = new ArrayList<String>();
		ingredientesCaros.add("Harina");
		ingredientesCaros.add("sal");
		ingredientesCaros.add("Ravioles");
		filtroPrecio.setIngredientesCaros(ingredientesCaros);
		filtros.add(filtroPrecio);
		busqueda.setFiltros(filtros); 
		List<Receta> listaRecetas = RepositorioRecetas.getInstance().realizarBusquedaPara(busqueda, usuarioPedro);	
		assertEquals(10 , listaRecetas.size());		
	}
	

	
	/* Tests de Busquedas */ 
	
	@Test
	public void testProcesarDiezPrimeros() throws FilterException {
		Busqueda busqueda = new Busqueda();
		PostProcesamiento primerosDiez = new PrimerosDiez();
		busqueda.setPostProcesamiento(primerosDiez);
		
		List<Filtro> filtros = new ArrayList<Filtro>();
		busqueda.setFiltros(filtros);
		
		List<Receta> listaRecetas = RepositorioRecetas.getInstance().realizarBusquedaPara(busqueda, usuario);
		assertEquals(10, listaRecetas.size());
	}
	
	@Test
	public void cantidadRecetasGeneradas(){
		assertEquals(30,this.usuario.getRecetas().size());
	}
	
	
	@Test
	public void testProcesarSoloPares() throws FilterException {
		Busqueda busqueda = new Busqueda();
		PostProcesamiento soloPares = new ResultadosPares();
		busqueda.setPostProcesamiento(soloPares);
		
		List<Receta> listaRecetas = RepositorioRecetas.getInstance().realizarBusquedaPara(busqueda, usuario);
		assertEquals(15, listaRecetas.size());
	}
	
	//revisar: no tiene sentido el assert
	@Test
	public void testProcesarOrdenAlfabetico() throws FilterException {
		RepositorioRecetas.getInstance().eliminarTodasLasRecetas();
		Busqueda busqueda = new Busqueda();
		Criterio alfabetico = new Alfabeticamente();
		Ordenamiento orden = new Ordenamiento(alfabetico);
		busqueda.setPostProcesamiento(orden);
		
		List<Filtro> filtros = new ArrayList<Filtro>();
		busqueda.setFiltros(filtros);
		
		List<Receta> listaRecetas = RepositorioRecetas.getInstance().realizarBusquedaPara(busqueda, usuario);
		assertEquals(30, listaRecetas.size());
	}
	
	//revisar: no tiene sentido el assert
	@Test
	public void testProcesarOrdenCalorias() throws FilterException {
		RepositorioRecetas.getInstance().eliminarTodasLasRecetas();
		Busqueda busqueda = new Busqueda();
		Criterio calorias = new Calorias();
		Ordenamiento orden = new Ordenamiento(calorias);
		busqueda.setPostProcesamiento(orden);
		
		List<Filtro> filtros = new ArrayList<Filtro>();
		busqueda.setFiltros(filtros);
		
		List<Receta> listaRecetas = RepositorioRecetas.getInstance().realizarBusquedaPara(busqueda, usuario);
		assertEquals(30, listaRecetas.size());
	}
	
	
	/* Tests de Busquedas + Filtros */ 

	/*
	 * agregar tests de filtros + postprocesamiento
	 * combinar 2 filtros y 1 postprocesamiento de orden
	 * ej: filtro de ingredientes caros y exceso de calorias
	 * 		y un post procesamiento de ordenar por calorias
	 */
}
