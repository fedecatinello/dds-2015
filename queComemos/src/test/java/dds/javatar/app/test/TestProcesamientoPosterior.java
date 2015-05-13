package dds.javatar.app.test;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.receta.busqueda.BuscarTodas;
import dds.javatar.app.dto.receta.busqueda.Busqueda;
import dds.javatar.app.dto.receta.procesamientoPosterior.ProcPostNulo;
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
		this.crearListaRecetasParaUsuarioSize20(this.usuario);
	}

	@Test
	public void testTomarLosPrimerosDiez() throws BusinessException {
		ProcesamientoPosterior procPostDameLosDiez = new ProcPostPrimerosDiez(new ProcPostNulo());
		Busqueda buscador = new BuscarTodas();
		List<Receta> listaRecetas = procPostDameLosDiez.aplicarProcPost(buscador.obtenerRecetasFiltradas(this.usuario));
		assertEquals(10, listaRecetas.size());
	}

	@Test
	public void testTomarLosPares() throws BusinessException {
		ProcesamientoPosterior procPostDameLosPares = new ProcPostResultadosPares(new ProcPostNulo());
		Busqueda buscador = new BuscarTodas();
		List<Receta> listaRecetas = procPostDameLosPares.aplicarProcPost(buscador.obtenerRecetasFiltradas(this.usuario));
		assertEquals(10, listaRecetas.size());
	}

}
