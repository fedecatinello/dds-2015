package dds.javatar.app.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.receta.RecetaPrivadaCompuesta;
import dds.javatar.app.dto.receta.RecetaPrivadaSimple;
import dds.javatar.app.dto.receta.busqueda.Busqueda;
import dds.javatar.app.dto.receta.filtro.Filtro;
import dds.javatar.app.dto.receta.filtro.FiltroCondiciones;
import dds.javatar.app.dto.receta.filtro.FiltroPrecio;
import dds.javatar.app.dto.usuario.Hipertenso;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.util.BusinessException;
import dds.javatar.app.util.exception.FilterException;

public class TestBusquedas extends TestGeneralAbstract{

	private Usuario usuario;
	private Busqueda busqueda;
	
	
	@Before
	public void initialize() throws BusinessException {
		this.usuario = this.crearUsuarioBasicoValido();
		crearListaRecetasParaUsuarioSize20(this.usuario);
	}
	
	
	@Test
	public void testBuscarRecetasSinFiltro() throws FilterException {
		Busqueda busqueda = new Busqueda();
		List<Receta> listaRecetas = busqueda.buscarPara(usuario);
		assertEquals(20 , listaRecetas.size());
	}
	
	@Test
	public void testFiltrarRecetasConExcesoDeCalorias() throws FilterException {
		Busqueda busqueda = new Busqueda(); // 10 de 20 recetas tienen 690 de calorias
		List<Filtro> filtros = new ArrayList<Filtro>();
		filtros.add(new FiltroCondiciones());
		busqueda.setFiltros(filtros);
		List<Receta> listaRecetas = busqueda.buscarPara(usuario);
		assertNotEquals(20, listaRecetas.size());
	}
	
	@Test
	public void testFiltrarRecetasConIngredientesCaros() throws FilterException {
		Busqueda busqueda = new Busqueda(); // 10 de 20 recetas tienen 690 de calorias
		List<Filtro> filtros = new ArrayList<Filtro>();
		filtros.add(new FiltroPrecio());
		busqueda.setFiltros(filtros); 
		List<Receta> listaRecetas = busqueda.buscarPara(usuario);
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

}
