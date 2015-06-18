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
	private Calendar calendar;

	@Before
	public void initialize() {
		this.mc = new MathContext(MathContext.DECIMAL32.getPrecision(), RoundingMode.HALF_DOWN);
		this.usuario = TestFactory.crearUsuarioBasicoValido();
		calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.YEAR, -1);
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
		this.usuario= new Usuario.UsuarioBuilder()
		.nombre("DonJuan")
		.fechaNacimiento(fechaPosterior)
		.sexo(Usuario.Sexo.MASCULINO)
		.peso(new BigDecimal(70))
		.altura(new BigDecimal(1.77))
		.rutina(new Rutina(TipoRutina.FUERTE, 20))
		.build();
		this.usuario.validar();
	}

	@Test
	public void testFechaNacimientoAnterior() throws UsuarioException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.YEAR, -1);
		Date fechaAnterior = calendar.getTime();

		this.usuario= new Usuario.UsuarioBuilder()
		.nombre("DonJuan")
		.fechaNacimiento(fechaAnterior)
		.sexo(Usuario.Sexo.MASCULINO)
		.peso(new BigDecimal(70))
		.altura(new BigDecimal(1.77))
		.rutina(new Rutina(TipoRutina.FUERTE, 20))
		.build();
		this.usuario.validar();
	}

	@Test(expected = UsuarioException.class)
	public void testUsuarioSinFechaNacimiento() throws UsuarioException {
		this.usuario = new Usuario.UsuarioBuilder()
		.nombre("DonJuan")
		.sexo(Usuario.Sexo.MASCULINO)
		.peso(new BigDecimal(70))
		.altura(new BigDecimal(1.77))
		.rutina(new Rutina(TipoRutina.FUERTE, 20))
		.build();
		this.usuario.validar();
	}

	@Test(expected = UsuarioException.class)
	public void testUsuarioSinNombre() throws UsuarioException {
		this.usuario = new Usuario.UsuarioBuilder()
		.fechaNacimiento(calendar.getTime())
		.sexo(Usuario.Sexo.MASCULINO)
		.peso(new BigDecimal(70))
		.altura(new BigDecimal(1.77))
		.rutina(new Rutina(TipoRutina.FUERTE, 20))
		.build();
		this.usuario.validar();
	}

	@Test(expected = UsuarioException.class)
	public void testUsuarioSinPeso() throws UsuarioException {
		this.usuario = new Usuario.UsuarioBuilder()
		.nombre("DonJuan")
		.fechaNacimiento(calendar.getTime())
		.sexo(Usuario.Sexo.MASCULINO)
		.altura(new BigDecimal(1.77))
		.rutina(new Rutina(TipoRutina.FUERTE, 20))
		.build();
		this.usuario.validar();
	}

	@Test(expected = UsuarioException.class)
	public void testUsuarioSinAltura() throws UsuarioException {
		this.usuario = new Usuario.UsuarioBuilder()
		.nombre("DonJuan")
		.fechaNacimiento(calendar.getTime())
		.sexo(Usuario.Sexo.MASCULINO)
		.peso(new BigDecimal(70))
		.rutina(new Rutina(TipoRutina.FUERTE, 20))
		.build();
		this.usuario.validar();
	}

	@Test(expected = UsuarioException.class)
	public void testUsuarioSinRutina() throws UsuarioException {
		this.usuario = new Usuario.UsuarioBuilder()
		.nombre("DonJuan")
		.fechaNacimiento(calendar.getTime())
		.sexo(Usuario.Sexo.MASCULINO)
		.peso(new BigDecimal(70))
		.altura(new BigDecimal(1.77))
		.build();
		this.usuario.validar();
	}

	@Test(expected = UsuarioException.class)
	public void testUsuarioConNombreMenorACuatroCaracters() throws UsuarioException {
		this.usuario=new Usuario.UsuarioBuilder()
		.nombre("Nom")
		.fechaNacimiento(calendar.getTime())
		.sexo(Usuario.Sexo.MASCULINO)
		.peso(new BigDecimal(70))
		.altura(new BigDecimal(1.77))
		.rutina(new Rutina(TipoRutina.FUERTE, 20))
		.build();
		this.usuario.validar();
	}

	@Test(expected = UsuarioException.class)
	public void testUsuarioConNombreIgualACuatroCaracteres() throws UsuarioException {
		this.usuario=new Usuario.UsuarioBuilder()
		.nombre("Nomb")
		.fechaNacimiento(calendar.getTime())
		.sexo(Usuario.Sexo.MASCULINO)
		.peso(new BigDecimal(70))
		.altura(new BigDecimal(1.77))
		.rutina(new Rutina(TipoRutina.FUERTE, 20))
		.build();
		this.usuario.validar();
	}

	@Test(expected = UsuarioException.class)
	public void testVeganoConPreferenciaInvalida() throws UsuarioException {
		Vegano vegano = new Vegano();

		this.usuario.agregarPreferenciaAlimenticia("pollo");
		this.usuario.agregarCondicionPreexistente(vegano);

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
		Usuario usuario = new Usuario.UsuarioBuilder().altura(new BigDecimal(1.75)).peso(new BigDecimal(65)).build();
		this.assertIMC(usuario, 21.2244898);
	}

	@Test
	public void testMelinaMacko() {
		Usuario meli = new Usuario.UsuarioBuilder().altura(new BigDecimal(1.47)).peso(new BigDecimal(42)).build();
		this.assertIMC(meli, 19.43635);
	}

	@Test
	public final void testFedericoCatinello() {
		Usuario fede = new Usuario.UsuarioBuilder().altura(new BigDecimal(1.72)).peso(new BigDecimal(75)).build();
		this.assertIMC(fede, 25.35154137);
	}

	@Test
	public final void testElianaLuguerosSinatra() {
		Usuario eliana = new Usuario.UsuarioBuilder().altura(new BigDecimal(1.66)).peso(new BigDecimal(62)).build();
		this.assertIMC(eliana, 22.49964);
	}

	@Test
	public final void testNicolasGarcia() {
		Usuario nico = new Usuario.UsuarioBuilder().altura(new BigDecimal(1.79)).peso(new BigDecimal(81)).build();
		this.assertIMC(nico, 25.28010);
	}

}
