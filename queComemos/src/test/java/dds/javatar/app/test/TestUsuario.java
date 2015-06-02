package dds.javatar.app.test;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import dds.javatar.app.dto.usuario.Rutina;
import dds.javatar.app.dto.usuario.Rutina.TipoRutina;
import dds.javatar.app.dto.usuario.condiciones.Diabetico;
import dds.javatar.app.dto.usuario.condiciones.Hipertenso;
import dds.javatar.app.dto.usuario.condiciones.Vegano;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.util.exception.UsuarioException;

public class TestUsuario {

	private MathContext mc;
	private Usuario usuario;

	@Before
	public void initialize() {
		this.mc = new MathContext(MathContext.DECIMAL32.getPrecision(), RoundingMode.HALF_DOWN);
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

	// Punto 1: validación de usuario
	@Test
	public void testUsuarioValido() throws UsuarioException {
		this.usuario.validar();
	}

	@Test(expected = UsuarioException.class)
	public void testFechaNacimientoPosterior() throws UsuarioException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.YEAR, 1);
		Date fechaPosterior = calendar.getTime();
		this.usuario.setFechaNacimiento(fechaPosterior);

		this.usuario.validar();
	}

	@Test
	public void testFechaNacimientoAnterior() throws UsuarioException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.YEAR, -1);
		Date fechaAnterior = calendar.getTime();

		this.usuario.setFechaNacimiento(fechaAnterior);

		this.usuario.validar();
	}

	@Test(expected = UsuarioException.class)
	public void testUsuarioSinFechaNacimiento() throws UsuarioException {
		this.usuario.setFechaNacimiento(null);
		this.usuario.validar();
	}

	@Test(expected = UsuarioException.class)
	public void testUsuarioSinNombre() throws UsuarioException {
		this.usuario.setNombre(null);
		this.usuario.validar();
	}

	@Test(expected = UsuarioException.class)
	public void testUsuarioSinPeso() throws UsuarioException {
		this.usuario.setPeso(null);
		this.usuario.validar();
	}

	@Test(expected = UsuarioException.class)
	public void testUsuarioSinAltura() throws UsuarioException {
		this.usuario.setAltura(null);
		this.usuario.validar();
	}

	@Test(expected = UsuarioException.class)
	public void testUsuarioSinRutina() throws UsuarioException {
		this.usuario.setRutina(null);
		this.usuario.validar();
	}

	@Test(expected = UsuarioException.class)
	public void testUsuarioConNombreMenorACuatroCaracters() throws UsuarioException {
		this.usuario.setNombre("Nom");
		this.usuario.validar();
	}

	@Test(expected = UsuarioException.class)
	public void testUsuarioConNombreIgualACuatroCaracteres() throws UsuarioException {
		this.usuario.setNombre("Nomb");
		this.usuario.validar();
	}

	@Test(expected = UsuarioException.class)
	public void testVeganoConPreferenciaInvalida() throws UsuarioException {
		Vegano vegano = new Vegano();

		this.usuario.agregarPreferenciaAlimenticia("pollo");
		this.usuario.agregarCondicionPreexistente(vegano);

		this.usuario.validar();
	}

	@Test(expected = UsuarioException.class)
	public void testDiabeticoSinSexo() throws UsuarioException {
		Diabetico diabetico = new Diabetico();

		this.usuario.setSexo(null);
		this.usuario.agregarCondicionPreexistente(diabetico);

		this.usuario.validar();
	}

	@Test
	public void testDiabeticoConSexo() throws UsuarioException {
		Diabetico diabetico = new Diabetico();

		this.usuario.agregarCondicionPreexistente(diabetico);
		this.usuario.agregarPreferenciaAlimenticia("pollo");

		this.usuario.validar();
	}

	@Test(expected = UsuarioException.class)
	public void testHipertensoSinPreferencia() throws UsuarioException {
		Hipertenso hipertenso = new Hipertenso();

		this.usuario.agregarCondicionPreexistente(hipertenso);

		this.usuario.validar();
	}

	@Test(expected = UsuarioException.class)
	public void testDiabeticoSinPreferencia() throws UsuarioException {
		Diabetico diabetico = new Diabetico();

		this.usuario.agregarCondicionPreexistente(diabetico);

		this.usuario.validar();
	}

	public void testHipertensoConPreferencia() throws UsuarioException {
		Hipertenso hipertenso = new Hipertenso();

		this.usuario.agregarCondicionPreexistente(hipertenso);
		this.usuario.agregarPreferenciaAlimenticia("pollo");

		this.usuario.validar();
	}

	@Test
	public void testDiabeticoConPreferencia() throws UsuarioException {
		Diabetico diabetico = new Diabetico();

		this.usuario.agregarCondicionPreexistente(diabetico);
		this.usuario.agregarPreferenciaAlimenticia("pollo");

		this.usuario.validar();
	}

	@Test(expected = UsuarioException.class)
	public void testVeganoConpreferenciaPollo() throws UsuarioException {
		Vegano vegano = new Vegano();

		this.usuario.agregarCondicionPreexistente(vegano);
		this.usuario.agregarPreferenciaAlimenticia("pollo");

		this.usuario.validar();
	}

	@Test(expected = UsuarioException.class)
	public void testVeganoConpreferenciaCarne() throws UsuarioException {
		Vegano vegano = new Vegano();

		this.usuario.agregarCondicionPreexistente(vegano);
		this.usuario.agregarPreferenciaAlimenticia("carne");

		this.usuario.validar();
	}

	@Test(expected = UsuarioException.class)
	public void testVeganoConpreferenciaChori() throws UsuarioException {
		Vegano vegano = new Vegano();

		this.usuario.agregarCondicionPreexistente(vegano);
		this.usuario.agregarPreferenciaAlimenticia("chori");

		this.usuario.validar();
	}

	@Test(expected = UsuarioException.class)
	public void testVeganoConpreferenciaChivito() throws UsuarioException {
		Vegano vegano = new Vegano();

		this.usuario.agregarCondicionPreexistente(vegano);
		this.usuario.agregarPreferenciaAlimenticia("chivito");

		this.usuario.validar();
	}

	@Test
	public void testVeganoConpreferenciaDiferenteAChivitoCarnePolloChori() throws UsuarioException {
		Vegano vegano = new Vegano();

		this.usuario.agregarCondicionPreexistente(vegano);
		this.usuario.agregarPreferenciaAlimenticia("lechuga");

		this.usuario.validar();
	}
	

	// Punto 2: averiguar el índice de masa corporal o IMC de un usuario
	private void assertIMC(Usuario usuario, double expectedValue) {
		BigDecimal expected = new BigDecimal(expectedValue, this.mc);
		assertEquals(expected.doubleValue(), usuario.getIMC(this.mc.getPrecision()).doubleValue(), 0.1);
	}

	@Test
	public final void testPabloGomez() {
		Usuario usuario = new Usuario(new BigDecimal(1.75), new BigDecimal(65.0));
		this.assertIMC(usuario, 21.2244898);
	}

	@Test
	public void testMelinaMacko() {
		Usuario meli = new Usuario();
		BigDecimal altura = new BigDecimal(1.47);
		BigDecimal peso = new BigDecimal(42);
		meli.setAltura(altura);
		meli.setPeso(peso);
		this.assertIMC(meli, 19.43635);
	}

	@Test
	public final void testFedericoCatinello() {
		Usuario fede = new Usuario(new BigDecimal(1.72), new BigDecimal(75));
		this.assertIMC(fede, 25.35154137);
	}

	@Test
	public final void testElianaLuguerosSinatra() {
		Usuario eliana = new Usuario(new BigDecimal(1.66), new BigDecimal(62));
		this.assertIMC(eliana, 22.49964);
	}

	@Test
	public final void testNicolasGarcia() {
		Usuario nico = new Usuario(new BigDecimal(1.79), new BigDecimal(81));
		this.assertIMC(nico, 25.28010);
	}

	@Test
	public void testCalculoIMC() {
		Usuario usuarioEsperado = new Usuario(new BigDecimal(1.75), new BigDecimal(65.0));

		this.usuario.setAltura(new BigDecimal(1.75));
		this.usuario.setPeso(new BigDecimal(65.0));
		BigDecimal usuarioObtenido = this.usuario.getIMC(10);
		this.assertIMC(usuarioEsperado, usuarioObtenido.doubleValue());

	}

}
