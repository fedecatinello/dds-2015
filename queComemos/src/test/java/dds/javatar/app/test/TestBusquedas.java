package dds.javatar.app.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.receta.RecetaPrivadaCompuesta;
import dds.javatar.app.dto.receta.RecetaPrivadaSimple;
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
import dds.javatar.app.dto.sistema.Sistema;
import dds.javatar.app.dto.usuario.Hipertenso;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.util.BusinessException;
import dds.javatar.app.util.exception.FilterException;

public class TestBusquedas extends TestGeneralAbstract{

	private Usuario usuario;
	
	
	@Before
	public void initialize() throws BusinessException {
		this.usuario = this.crearUsuarioBasicoValido();
		crearListaRecetasParaUsuarioSize20(this.usuario);
	}
	
	
	/* Tests de Filtros */ 
	
	@Test
	public void testBuscarRecetasSinFiltro() throws FilterException {
		Busqueda busqueda = new Busqueda();
		List<Receta> listaRecetas = Sistema.getInstance().realizarBusquedaPara(busqueda, usuario);
		assertEquals(20 , listaRecetas.size());
	}
	
	@Test
	public void testFiltrarRecetasConExcesoDeCalorias() throws FilterException {
		Busqueda busqueda = new Busqueda(); // 10 de 20 recetas tienen 690 de calorias
		List<Filtro> filtros = new ArrayList<Filtro>();
		filtros.add(new FiltroCondiciones());
		busqueda.setFiltros(filtros);
		List<Receta> listaRecetas = Sistema.getInstance().realizarBusquedaPara(busqueda, usuario);;
		assertNotEquals(20, listaRecetas.size());
	}
	
	@Test
	public void testFiltrarRecetasConIngredientesCaros() throws FilterException {
		Busqueda busqueda = new Busqueda(); // 10 de 20 recetas tienen 690 de calorias
		List<Filtro> filtros = new ArrayList<Filtro>();
		filtros.add(new FiltroPrecio());
		busqueda.setFiltros(filtros); 
		List<Receta> listaRecetas = Sistema.getInstance().realizarBusquedaPara(busqueda, usuario);;
		assertEquals(20 , listaRecetas.size());
	}
	
//	@Test (expected = FilterException.class)
//	public final void testFiltroCondicionesNoFiltra() throws FilterException{
//		
//		Filtro filtroCondiciones = new FiltroCondiciones();
//		busqueda.getUsuario().agregarCondicionPreexistente(new Hipertenso());
//		busqueda.getFiltros().add(filtroCondiciones);
//		busqueda.filtrar();
//	}
//	
//	@Test
//	public final void testFiltroCondicionesFiltra() throws FilterException{
//		
//		Filtro filtroCondiciones = new FiltroCondiciones();
//		usuario.agregarCondicionPreexistente(new Hipertenso());
//		busqueda.getFiltros().add(filtroCondiciones);
//		busqueda.filtrar();
//	}

	
	/* Tests de Busquedas */ 
	
	@Test
	public void testProcesarDiezPrimeros() throws FilterException {
		Busqueda busqueda = new Busqueda();
		PostProcesamiento primerosDiez = new PrimerosDiez();
		busqueda.setPostProcesamiento(primerosDiez);
		List<Receta> listaRecetas = Sistema.getInstance().realizarBusquedaPara(busqueda, usuario);
		assertEquals(10, listaRecetas.size());
	}
	

	@Test
	public void testProcesarSoloPares() throws FilterException {
		Busqueda busqueda = new Busqueda();
		PostProcesamiento soloPares = new ResultadosPares();
		busqueda.setPostProcesamiento(soloPares);
		List<Receta> listaRecetas = Sistema.getInstance().realizarBusquedaPara(busqueda, usuario);
		assertEquals(14, listaRecetas.size());
	}
	
	//revisar: no tiene sentido el assert
	@Test
	public void testProcesarOrdenAlfabetico() throws FilterException {
		Busqueda busqueda = new Busqueda();
		Ordenamiento orden = new Ordenamiento();
		Criterio alfabetico = new Alfabeticamente();
		
		orden.setCriterio(alfabetico);
		busqueda.setPostProcesamiento(orden);
		List<Receta> listaRecetas = Sistema.getInstance().realizarBusquedaPara(busqueda, usuario);
		assertEquals(30, listaRecetas.size());
	}
	
	//revisar: no tiene sentido el assert
	@Test
	public void testProcesarOrdenCalorias() throws FilterException {
		Busqueda busqueda = new Busqueda();
		Ordenamiento orden = new Ordenamiento();
		Criterio calorias = new Calorias();
		
		orden.setCriterio(calorias);
		busqueda.setPostProcesamiento(orden);
		List<Receta> listaRecetas = Sistema.getInstance().realizarBusquedaPara(busqueda, usuario);
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
