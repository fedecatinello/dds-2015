package dds.javatar.app.test;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import dds.javatar.app.dto.usuario.Celiaco;
import dds.javatar.app.dto.usuario.Diabetico;
import dds.javatar.app.dto.usuario.Hipertenso;
import dds.javatar.app.dto.usuario.Rutina;
import dds.javatar.app.dto.usuario.Rutina.TipoRutina;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.dto.usuario.Vegano;
import dds.javatar.app.util.BusinessException;

public class TestRutinas {

	private Usuario usuario;

	@Before
	public void initialize() {
		this.usuario = this.crearUsuarioBasicoValido();
	}

	private Usuario crearUsuarioBasicoValido() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.YEAR, -1);

		Usuario usuario = new Usuario();
		usuario.setFechaNacimiento(calendar.getTime());
		usuario.setNombre("Nombre del usuario");
		usuario.setSexo(Usuario.Sexo.MASCULINO);
		usuario.setPeso(new BigDecimal(70));
		usuario.setAltura(new BigDecimal(1.77));
		usuario.setRutina(new Rutina(TipoRutina.FUERTE, 20));

		return usuario;
	}

	// Punto 2: averiguar si un usuario sigue una rutina saludable.
	@Test
	public void testUsuarioConRutinaSaludableFuegaDelRangoDelIMC() throws BusinessException {
		this.usuario.setPeso(new BigDecimal(200));

		assertEquals(this.usuario.sigueRutinaSaludable(), Boolean.FALSE);
	}

	@Test
	public void testUsuarioConRutinaSaludableConCondicionesPreexistentes() throws BusinessException {
		Hipertenso hipertenso = new Hipertenso();

		this.usuario.agregarCondicionPreexistente(hipertenso);

		assertEquals(this.usuario.sigueRutinaSaludable(), Boolean.FALSE);
	}

	@Test
	public void testUsuarioConRutinaSaludable() throws BusinessException {
		assertEquals(this.usuario.sigueRutinaSaludable(), Boolean.TRUE);
	}

	@Test
	public void testVeganoConPreferenciaFrutaTieneRutinaSaludable() throws BusinessException {
		Vegano vegano = new Vegano();

		this.usuario.agregarCondicionPreexistente(vegano);
		this.usuario.agregarPreferenciaAlimenticia("fruta");

		assertEquals(this.usuario.sigueRutinaSaludable(), Boolean.TRUE);
	}

	@Test
	public void testHipertensoConRutinaActivaIntensiva() throws BusinessException {
		Hipertenso hipertenso = new Hipertenso();

		this.usuario.agregarCondicionPreexistente(hipertenso);
		this.usuario.getRutina().setTipo(TipoRutina.INTENSIVO);
		this.usuario.getRutina().setDuracion(40);

		assertEquals(this.usuario.sigueRutinaSaludable(), Boolean.TRUE);
	}

	@Test
	public void testHipertensoConRutinaSedentariaLeve() throws BusinessException {
		Hipertenso hipertenso = new Hipertenso();

		this.usuario.agregarCondicionPreexistente(hipertenso);
		this.usuario.getRutina().setTipo(TipoRutina.LEVE);
		this.usuario.getRutina().setDuracion(15);

		assertEquals(this.usuario.sigueRutinaSaludable(), Boolean.FALSE);
	}

	@Test
	public void testDiabeticoConRutinaSedentariaLeveYSobrepeso() throws BusinessException {
		Diabetico diabetico = new Diabetico();

		this.usuario.setPeso(new BigDecimal(100));
		this.usuario.agregarCondicionPreexistente(diabetico);
		this.usuario.getRutina().setTipo(TipoRutina.LEVE);
		this.usuario.getRutina().setDuracion(15);

		assertEquals(this.usuario.sigueRutinaSaludable(), Boolean.FALSE);
	}

	@Test
	public void testDiabeticoConRutinaActiva() throws BusinessException {
		Diabetico diabetico = new Diabetico();

		this.usuario.agregarCondicionPreexistente(diabetico);
		this.usuario.getRutina().setTipo(TipoRutina.FUERTE);
		this.usuario.getRutina().setDuracion(0);

		assertEquals(this.usuario.sigueRutinaSaludable(), Boolean.TRUE);
	}

	@Test
	public void testDiabeticoPesaMasde70() throws BusinessException {
		Diabetico diabetico = new Diabetico();

		this.usuario.setRutina(new Rutina(TipoRutina.LEVE, 50));
		this.usuario.agregarCondicionPreexistente(diabetico);
		this.usuario.setPeso(new BigDecimal(85));

		assertEquals(this.usuario.sigueRutinaSaludable(), Boolean.FALSE);
	}

	@Test
	public void testDiabeticoPesaMenosde70() throws BusinessException {
		Diabetico diabetico = new Diabetico();

		this.usuario.agregarCondicionPreexistente(diabetico);
		this.usuario.setPeso(new BigDecimal(65));

		assertEquals(this.usuario.sigueRutinaSaludable(), Boolean.TRUE);
	}

	@Test
	public void testCeliacoSaludable() throws BusinessException {
		Celiaco celiaco = new Celiaco();
		this.usuario.agregarCondicionPreexistente(celiaco);

		assertEquals(this.usuario.sigueRutinaSaludable(), Boolean.TRUE);
	}

}
