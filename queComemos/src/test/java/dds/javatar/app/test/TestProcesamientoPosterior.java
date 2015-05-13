package dds.javatar.app.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.receta.busqueda.BuscarTodas;
import dds.javatar.app.dto.receta.busqueda.Busqueda;
import dds.javatar.app.dto.receta.procesamientoPosterior.ProcPostNulo;
import dds.javatar.app.dto.receta.procesamientoPosterior.ProcPostOrdenarPorAlfabeto;
import dds.javatar.app.dto.receta.procesamientoPosterior.ProcPostOrdenarPorCalorias;
import dds.javatar.app.dto.receta.procesamientoPosterior.ProcPostPrimerosDiez;
import dds.javatar.app.dto.receta.procesamientoPosterior.ProcPostResultadosPares;
import dds.javatar.app.dto.receta.procesamientoPosterior.ProcesamientoPosterior;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.util.BusinessException;

public class TestProcesamientoPosterior extends TestGeneralAbstract {

	private Usuario usuario;

	@Before
	public void initialize() throws BusinessException {
		this.usuario = this.crearUsuarioBasicoValido();
		crearListaRecetasParaUsuarioSize20(this.usuario);		
	}


	@Test
	public void testTomarLosPrimerosDiez() throws BusinessException {
		ProcesamientoPosterior procPostDameLosDiez = new ProcPostPrimerosDiez(new ProcPostNulo());
		Busqueda buscador = new BuscarTodas();
		List<Receta> listaRecetas= procPostDameLosDiez.aplicarProcPost(buscador.ObtenerRecetas(usuario));		
		assertEquals(10 , listaRecetas.size());
	}

	@Test
	public void testTomarLosPares() throws BusinessException {
		ProcesamientoPosterior procPostDameLosPares = new ProcPostResultadosPares(new ProcPostNulo());
		Busqueda buscador = new BuscarTodas();
		List<Receta> listaRecetas= procPostDameLosPares.aplicarProcPost(buscador.ObtenerRecetas(usuario));		
		assertEquals(10 , listaRecetas.size());
	}

	@Test
	public void testTomarLosPrimerosDiezYqueSeanPares() throws BusinessException {
		ProcesamientoPosterior procPostDameLosDiezYqueSeanPares = new ProcPostResultadosPares(new ProcPostPrimerosDiez(new ProcPostNulo()));	
		Busqueda buscador = new BuscarTodas();
		List<Receta> listaRecetas= procPostDameLosDiezYqueSeanPares.aplicarProcPost(buscador.ObtenerRecetas(usuario));
		assertEquals(5 , listaRecetas.size());
	}

	@Test
	public void testOrdenarPorCalorias() throws BusinessException {
		ProcesamientoPosterior procPostOrdenarPorCalorias = new ProcPostOrdenarPorCalorias(new ProcPostNulo());	
		Busqueda buscador = new BuscarTodas();
		List<Receta> listaRecetas= procPostOrdenarPorCalorias.aplicarProcPost(buscador.ObtenerRecetas(usuario));
		assertEquals(20 , listaRecetas.size());
	}

	@Test(expected = NullPointerException.class)
	public void testOrdenarPorAlfabetoConNombreNulo() throws NullPointerException, BusinessException {
		Usuario usuario2 = this.crearUsuarioBasicoValido();
		crearListaRecetasParaUsuarioSize20ConNombresNulos(usuario2);	
		ProcesamientoPosterior procPostOrdenarPorAlfabeto = new ProcPostOrdenarPorAlfabeto(new ProcPostNulo());	
		Busqueda buscador = new BuscarTodas();
		List<Receta> listaRecetas= procPostOrdenarPorAlfabeto.aplicarProcPost(buscador.ObtenerRecetas(usuario2));
		assertEquals(20 , listaRecetas.size());
	}

	@Test
	public void testOrdenarPorAlfabeto() throws BusinessException {
		ProcesamientoPosterior procPostOrdenarPorAlfabeto = new ProcPostOrdenarPorAlfabeto(new ProcPostNulo());	
		Busqueda buscador = new BuscarTodas();
		List<Receta> listaRecetas= procPostOrdenarPorAlfabeto.aplicarProcPost(buscador.ObtenerRecetas(usuario));
		assertEquals(20 , listaRecetas.size());
	}


}
