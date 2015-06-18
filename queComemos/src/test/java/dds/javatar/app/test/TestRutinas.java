package dds.javatar.app.test;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import dds.javatar.app.dto.usuario.Rutina.TipoRutina;
import dds.javatar.app.dto.usuario.condiciones.Celiaco;
import dds.javatar.app.dto.usuario.condiciones.Diabetico;
import dds.javatar.app.dto.usuario.condiciones.Hipertenso;
import dds.javatar.app.dto.usuario.condiciones.Vegano;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.util.exception.BusinessException;

public class TestRutinas {

	private Usuario usuarioComun, usuarioSobrePeso;

	@Before
	public void initialize() {
		this.usuarioComun = TestFactory.crearUsuarioBasicoValido();
		this.usuarioSobrePeso = TestFactory.crearUsuarioConSobrepeso();		
	}


	
	// Punto 2: averiguar si un usuario sigue una rutina saludable.
	@Test
	public void testUsuarioConRutinaSaludableFuegaDelRangoDelIMC() throws BusinessException {
		assertEquals(this.usuarioSobrePeso.sigueRutinaSaludable(), Boolean.FALSE);
	}

	@Test
	public void testUsuarioConRutinaSaludableConCondicionesPreexistentes() throws BusinessException {
		Hipertenso hipertenso = new Hipertenso();

		this.usuarioComun.agregarCondicionPreexistente(hipertenso);

		assertEquals(this.usuarioComun.sigueRutinaSaludable(), Boolean.FALSE);
	}

	@Test
	public void testUsuarioConRutinaSaludable() throws BusinessException {
		assertEquals(this.usuarioComun.sigueRutinaSaludable(), Boolean.TRUE);
	}

	@Test
	public void testVeganoConPreferenciaFrutaTieneRutinaSaludable() throws BusinessException {
		Vegano vegano = new Vegano();

		this.usuarioComun.agregarCondicionPreexistente(vegano);
		this.usuarioComun.agregarPreferenciaAlimenticia("fruta");

		assertEquals(this.usuarioComun.sigueRutinaSaludable(), Boolean.TRUE);
	}

	@Test
	public void testHipertensoConRutinaActivaIntensiva() throws BusinessException {
		Hipertenso hipertenso = new Hipertenso();

		this.usuarioComun.agregarCondicionPreexistente(hipertenso);
		this.usuarioComun.getRutina().setTipo(TipoRutina.INTENSIVO);
		this.usuarioComun.getRutina().setDuracion(40);

		assertEquals(this.usuarioComun.sigueRutinaSaludable(), Boolean.TRUE);
	}

	@Test
	public void testHipertensoConRutinaSedentariaLeve() throws BusinessException {
		Hipertenso hipertenso = new Hipertenso();

		this.usuarioComun.agregarCondicionPreexistente(hipertenso);
		this.usuarioComun.getRutina().setTipo(TipoRutina.LEVE);
		this.usuarioComun.getRutina().setDuracion(15);

		assertEquals(this.usuarioComun.sigueRutinaSaludable(), Boolean.FALSE);
	}

	@Test
	public void testDiabeticoConRutinaSedentariaLeveYSobrepeso() throws BusinessException {
		Diabetico diabetico = new Diabetico();

		this.usuarioSobrePeso.agregarCondicionPreexistente(diabetico);
		this.usuarioSobrePeso.getRutina().setTipo(TipoRutina.LEVE);
		this.usuarioSobrePeso.getRutina().setDuracion(15);

		assertEquals(this.usuarioSobrePeso.sigueRutinaSaludable(), Boolean.FALSE);
	}

	@Test
	public void testDiabeticoConRutinaActiva() throws BusinessException {
		Diabetico diabetico = new Diabetico();

		this.usuarioComun.agregarCondicionPreexistente(diabetico);
		this.usuarioComun.getRutina().setTipo(TipoRutina.FUERTE);
		this.usuarioComun.getRutina().setDuracion(0);

		assertEquals(this.usuarioComun.sigueRutinaSaludable(), Boolean.TRUE);
	}

	@Test
	public void testDiabeticoPesaMasde70() throws BusinessException {
		Diabetico diabetico = new Diabetico();

		this.usuarioSobrePeso.agregarCondicionPreexistente(diabetico);

		assertEquals(this.usuarioSobrePeso.sigueRutinaSaludable(), Boolean.FALSE);
	}

	@Test
	public void testDiabeticoPesaMenosde70() throws BusinessException {
		Diabetico diabetico = new Diabetico();

		this.usuarioComun.agregarCondicionPreexistente(diabetico);

		assertEquals(this.usuarioComun.sigueRutinaSaludable(), Boolean.TRUE);
	}

	@Test
	public void testCeliacoSaludable() throws BusinessException {
		Celiaco celiaco = new Celiaco();
		this.usuarioComun.agregarCondicionPreexistente(celiaco);

		assertEquals(this.usuarioComun.sigueRutinaSaludable(), Boolean.TRUE);
	}

}
