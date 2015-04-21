package dds.javatar.app.test;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import dds.javatar.app.dto.Celiaco;
import dds.javatar.app.dto.Diabetico;
import dds.javatar.app.dto.Hipertenso;
import dds.javatar.app.dto.Receta;
import dds.javatar.app.dto.Rutina;
import dds.javatar.app.dto.Rutina.TipoRutina;
import dds.javatar.app.dto.Usuario;
import dds.javatar.app.dto.Vegano;
import dds.javatar.app.util.BusinessException;

public class TestUsuario {

	private MathContext mc;

	@Before
	public void initialize() {
		this.mc = new MathContext(MathContext.DECIMAL32.getPrecision(), RoundingMode.HALF_DOWN);
	}

	private void assertIMC(Usuario usuario, double expectedValue) {
		BigDecimal expected = new BigDecimal(expectedValue, mc);
		assertEquals(expected.doubleValue(), usuario.getIMC(mc.getPrecision()).doubleValue(), 0.1);
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

	@Test(expected = BusinessException.class)
	public void testFechaNacimientoPosterior() throws BusinessException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.YEAR, 1);
		Date fechaPosterior = calendar.getTime();

		Usuario usuario = this.crearUsuarioBasicoValido();
		usuario.setFechaNacimiento(fechaPosterior);

		usuario.validar();
	}

	@Test
	public void testFechaNacimientoAnterior() throws BusinessException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.YEAR, -1);
		Date fechaAnterior = calendar.getTime();

		Usuario usuario = this.crearUsuarioBasicoValido();
		usuario.setFechaNacimiento(fechaAnterior);

		usuario.validar();
	}

	@Test(expected = BusinessException.class)
	public void testVeganoConPreferenciaInvalida() throws BusinessException {
		Vegano vegano = new Vegano();

		Usuario usuario = this.crearUsuarioBasicoValido();
		usuario.getPreferenciasAlimenticias().put("pollo", Boolean.TRUE);
		usuario.agregarCondicionPreexistente(vegano);

		usuario.validar();
	}

	@Test(expected = BusinessException.class)
	public void testDiabeticoSinSexo() throws BusinessException {
		Diabetico diabetico = new Diabetico();

		Usuario usuario = this.crearUsuarioBasicoValido();
		usuario.setSexo(null);
		usuario.agregarCondicionPreexistente(diabetico);

		usuario.validar();
	}

	@Test(expected = BusinessException.class)
	public void testHipertensoSinPreferencia() throws BusinessException {
		Hipertenso hipertenso = new Hipertenso();

		Usuario usuario = this.crearUsuarioBasicoValido();
		usuario.agregarCondicionPreexistente(hipertenso);
		usuario.setPreferenciasAlimenticias(new HashMap<String, Boolean>());

		usuario.validar();
	}

	@Test(expected = BusinessException.class)
	public void testDiabeticoSinPreferencia() throws BusinessException {
		Diabetico diabetico = new Diabetico();

		Usuario usuario = this.crearUsuarioBasicoValido();
		usuario.agregarCondicionPreexistente(diabetico);
		usuario.setPreferenciasAlimenticias(new HashMap<String, Boolean>());

		usuario.validar();
	}

	public void testHipertensoConPreferencia() throws BusinessException {
		Hipertenso hipertenso = new Hipertenso();

		Usuario usuario = this.crearUsuarioBasicoValido();
		usuario.agregarCondicionPreexistente(hipertenso);
		usuario.getPreferenciasAlimenticias().put("pollo", Boolean.TRUE);

		usuario.validar();
	}

	@Test
	public void testVeganoConPreferenciaFruta() throws BusinessException {
		Vegano vegano = new Vegano();

		Usuario usuario = this.crearUsuarioBasicoValido();
		usuario.agregarCondicionPreexistente(vegano);
		usuario.getPreferenciasAlimenticias().put("fruta", Boolean.TRUE);
		usuario.validarRutinaSaludable();
	}

	@Test
	public void testHipertensoConRutinaActivaIntensiva() throws BusinessException {
		Hipertenso hipertenso = new Hipertenso();

		Usuario usuario = this.crearUsuarioBasicoValido();
		usuario.agregarCondicionPreexistente(hipertenso);
		usuario.getRutina().setTipo(TipoRutina.INTENSIVO);
		usuario.getRutina().setDuracion(40);

		usuario.validarRutinaSaludable();
	}

	@Test(expected = BusinessException.class)
	public void testHipertensoConRutinaSedentariaLeve() throws BusinessException {
		Hipertenso hipertenso = new Hipertenso();

		Usuario usuario = this.crearUsuarioBasicoValido();
		usuario.agregarCondicionPreexistente(hipertenso);
		usuario.getRutina().setTipo(TipoRutina.LEVE);
		usuario.getRutina().setDuracion(15);

		usuario.validarRutinaSaludable();
	}

	@Test(expected = BusinessException.class)
	public void testDiabeticoConRutinaSedentariaLeveYSobrepeso() throws BusinessException {
		Diabetico diabetico = new Diabetico();

		Usuario usuario = this.crearUsuarioBasicoValido();
		usuario.setPeso(new BigDecimal(100));
		usuario.agregarCondicionPreexistente(diabetico);
		usuario.getRutina().setTipo(TipoRutina.LEVE);
		usuario.getRutina().setDuracion(15);

		usuario.validarRutinaSaludable();
	}

	@Test
	public void testDiabeticoConRutinaActiva() throws BusinessException {
		Diabetico diabetico = new Diabetico();

		Usuario usuario = this.crearUsuarioBasicoValido();
		usuario.agregarCondicionPreexistente(diabetico);
		usuario.getRutina().setTipo(TipoRutina.FUERTE);
		usuario.getRutina().setDuracion(0);

		usuario.validarRutinaSaludable();
	}

	@Test(expected = BusinessException.class)
	public void testDiabeticoPesaMasde70() throws BusinessException {
		Diabetico diabetico = new Diabetico();

		Usuario usuario = this.crearUsuarioBasicoValido();
		usuario.setRutina(new Rutina(TipoRutina.LEVE, 50));
		usuario.agregarCondicionPreexistente(diabetico);
		usuario.setPeso(new BigDecimal(85));

		usuario.validarRutinaSaludable();
	}

	@Test
	public void testDiabeticoPesaMenosde70() throws BusinessException {
		Diabetico diabetico = new Diabetico();

		Usuario usuario = this.crearUsuarioBasicoValido();
		usuario.agregarCondicionPreexistente(diabetico);
		usuario.setPeso(new BigDecimal(65));

		usuario.validarRutinaSaludable();
	}

	@Test
	public void testCeliacoSaludable() throws BusinessException {
		Celiaco celiaco = new Celiaco();

		Usuario usuario = this.crearUsuarioBasicoValido();
		usuario.agregarCondicionPreexistente(celiaco);

		usuario.validarRutinaSaludable();
	}

	@Test
	public void testAgregarReceta() throws BusinessException {
		Receta ravioles = new Receta(350);
		ravioles.agregarIngrediente("Harina", new BigDecimal(300));
		ravioles.agregarIngrediente("Agua", new BigDecimal(70));
		ravioles.agregarIngrediente("Verdura", new BigDecimal(100));

		Usuario usuario = crearUsuarioBasicoValido();
		usuario.agregarReceta(ravioles);
	}

	@Test(expected = BusinessException.class)
	public void testRecetaHipertensoNoAcepta() throws BusinessException {
		Usuario usuario = crearUsuarioBasicoValido();
		Hipertenso hipertenso = new Hipertenso();
		usuario.agregarCondicionPreexistente(hipertenso);

		Receta receta = new Receta(350);
		receta.agregarIngrediente("sal", new BigDecimal(50));
		usuario.validarSiAceptaReceta(receta);

	}

	@Test(expected = BusinessException.class)
	public void testRecetaVeganoNoAcepta() throws BusinessException {
		Usuario usuario = crearUsuarioBasicoValido();
		Vegano veggie = new Vegano();
		usuario.agregarCondicionPreexistente(veggie);

		Receta receta = new Receta(350);
		receta.agregarIngrediente("chori", new BigDecimal(120));
		usuario.validarSiAceptaReceta(receta);
	}

	@Test(expected = BusinessException.class)
	public void testRecetaDiabeticoNoAcepta() throws BusinessException {
		Usuario usuario = crearUsuarioBasicoValido();
		Diabetico diabetico = new Diabetico();
		usuario.agregarCondicionPreexistente(diabetico);

		Receta receta = new Receta(150);
		receta.agregarIngrediente("azucar", new BigDecimal(120));
		usuario.validarSiAceptaReceta(receta);

	}

	@Test
	public void testRecetaHipertensoAcepta() throws BusinessException {
		Usuario usuario = crearUsuarioBasicoValido();
		Hipertenso hipertenso = new Hipertenso();
		usuario.agregarCondicionPreexistente(hipertenso);

		Receta receta = new Receta(350);
		receta.agregarIngrediente("arroz", new BigDecimal(200));
		usuario.validarSiAceptaReceta(receta);

	}

	@Test
	public void testRecetaVeganoAcepta() throws BusinessException {
		Usuario usuario = crearUsuarioBasicoValido();
		Vegano veggie = new Vegano();
		usuario.agregarCondicionPreexistente(veggie);

		Receta receta = new Receta(350);
		receta.agregarIngrediente("tomate", new BigDecimal(80));
		usuario.validarSiAceptaReceta(receta);
	}

	@Test
	public void testRecetaDiabeticoAceptaAzucar() throws BusinessException {
		Usuario usuario = crearUsuarioBasicoValido();
		Diabetico diabetico = new Diabetico();
		usuario.agregarCondicionPreexistente(diabetico);

		Receta receta = new Receta(150);
		receta.agregarIngrediente("azucar", new BigDecimal(50));
		usuario.validarSiAceptaReceta(receta);
	}

	@Test(expected = BusinessException.class)
	public void testRecetaDiabeticoNoAceptaAzucar() throws BusinessException {
		Usuario usuario = crearUsuarioBasicoValido();
		Diabetico diabetico = new Diabetico();
		usuario.agregarCondicionPreexistente(diabetico);

		Receta receta = new Receta(150);
		receta.agregarIngrediente("azucar", new BigDecimal(150));
		usuario.validarSiAceptaReceta(receta);
	}
	
}
