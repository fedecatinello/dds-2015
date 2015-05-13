package dds.javatar.app.test;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.receta.RecetaPrivadaSimple;
import dds.javatar.app.dto.receta.busqueda.BuscarTodas;
import dds.javatar.app.dto.receta.busqueda.Busqueda;
import dds.javatar.app.dto.receta.busqueda.FiltroCarosEnPreparacion;
import dds.javatar.app.dto.receta.busqueda.FiltroExcesoCalorias;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.util.BusinessException;

public class TestFiltrosYBusquedas extends TestGeneralAbstract {

	private Usuario usuario;

	// private List<Receta> listaRecetas;

	@Before
	public void initialize() throws BusinessException {
		this.usuario = this.crearUsuarioBasicoValido();
		this.crearListaRecetasParaUsuarioSize20(this.usuario);
	}

	@Test
	public void testBuscarRecetasSinFiltro() throws BusinessException {
		Busqueda buscador = new BuscarTodas();
		List<Receta> listaRecetas = buscador.obtenerRecetasFiltradas(this.usuario);
		assertEquals(20, listaRecetas.size());
	}

	@Test
	public void testBuscarRecetasSinExcesoDeCalorias() throws BusinessException {
		Busqueda buscador = new FiltroExcesoCalorias(new BuscarTodas());

		this.usuario.setPeso(new BigDecimal(200)); // sobrepeso

		RecetaPrivadaSimple recetaConMuchasCalorias = this.crearRecetaPrivadaSimple();
		recetaConMuchasCalorias.agregarIngrediente("salmon", new BigDecimal(100));
		recetaConMuchasCalorias.setCalorias(1500);
		recetaConMuchasCalorias.setNombre("AAA");
		this.usuario.agregarReceta(recetaConMuchasCalorias);

		RecetaPrivadaSimple recetaConPocasCalorias = this.crearRecetaPrivadaSimple();
		recetaConPocasCalorias.agregarIngrediente("pollo", new BigDecimal(150));
		recetaConPocasCalorias.setCalorias(100);
		this.usuario.agregarReceta(recetaConPocasCalorias);

		RecetaPrivadaSimple recetaConCaloriasJustas = this.crearRecetaPrivadaSimple();
		recetaConCaloriasJustas.agregarIngrediente("pollo", new BigDecimal(150));
		recetaConCaloriasJustas.setCalorias(500);
		this.usuario.agregarReceta(recetaConCaloriasJustas);

		List<Receta> listaRecetas = buscador.obtenerRecetasFiltradas(this.usuario);

		Assert.assertFalse(listaRecetas.contains(recetaConMuchasCalorias));
		Assert.assertTrue(listaRecetas.contains(recetaConPocasCalorias));
		Assert.assertTrue(listaRecetas.contains(recetaConCaloriasJustas));
	}

	@Test
	public void testBuscarRecetasSinIngredientesCaros() throws BusinessException {
		Busqueda buscador = new FiltroCarosEnPreparacion(new BuscarTodas());

		RecetaPrivadaSimple recetaCara = this.crearRecetaPrivadaSimple();
		recetaCara.agregarIngrediente("salmon", new BigDecimal(100));
		this.usuario.agregarReceta(recetaCara);
		RecetaPrivadaSimple recetaNoCara = this.crearRecetaPrivadaSimple();
		recetaNoCara.agregarIngrediente("pollo", new BigDecimal(150));
		this.usuario.agregarReceta(recetaNoCara);

		List<Receta> listaRecetas = buscador.obtenerRecetasFiltradas(this.usuario);
		Assert.assertFalse(listaRecetas.contains(recetaCara));
		Assert.assertTrue(listaRecetas.contains(recetaNoCara));
	}

}
