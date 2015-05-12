package dds.javatar.app.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.receta.busqueda.BuscarTodas;
import dds.javatar.app.dto.receta.busqueda.Busqueda;
import dds.javatar.app.dto.receta.busqueda.FiltroCarosEnPreparacion;
import dds.javatar.app.dto.receta.busqueda.FiltroExcesoCalorias;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.util.BusinessException;

public class TestFiltrosYbusquedas extends TestGeneralAbstract {
	
	private Usuario usuario;
//	private List<Receta> listaRecetas;
	
	@Before
	public void initialize() throws BusinessException {
		this.usuario = this.crearUsuarioBasicoValido();
		crearListaRecetasParaUsuarioSize20(this.usuario);		
	}
	
	
	
	@Test
	public void testBuscarRecetasSinFiltro() throws BusinessException {
		Busqueda buscador = new BuscarTodas();
		List<Receta> listaRecetas=buscador.ObtenerRecetas(usuario);
		assertEquals(20 , listaRecetas.size());
	}
	
	@Test
	public void testBuscarRecetasSinExcesoDeCalorias() throws BusinessException {
		Busqueda buscador = new FiltroExcesoCalorias(new BuscarTodas()); // 10 de 20 recetas tienen 690 de calorias
		List<Receta> listaRecetas=buscador.ObtenerRecetas(usuario);
		assertEquals(10 , listaRecetas.size());
	}
	
	@Test
	public void testBuscarRecetasSinIngredientesCaros() throws BusinessException {
		Busqueda buscador = new FiltroCarosEnPreparacion(new BuscarTodas()); // 10 de 20 recetas tienen 690 de calorias
		List<Receta> listaRecetas=buscador.ObtenerRecetas(usuario);
		assertEquals(20 , listaRecetas.size());
	}

}
