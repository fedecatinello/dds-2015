package dds.javatar.app.test;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.usuario.Celiaco;
import dds.javatar.app.dto.usuario.Diabetico;
import dds.javatar.app.dto.usuario.Hipertenso;
import dds.javatar.app.dto.usuario.Rutina;
import dds.javatar.app.dto.usuario.Rutina.TipoRutina;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.dto.usuario.Vegano;
import dds.javatar.app.util.BusinessException;

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
	public void testUsuarioValido() throws BusinessException {
		this.usuario.validar();
	}

	@Test(expected = BusinessException.class)
	public void testFechaNacimientoPosterior() throws BusinessException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.YEAR, 1);
		Date fechaPosterior = calendar.getTime();
		this.usuario.setFechaNacimiento(fechaPosterior);

		this.usuario.validar();
	}

	@Test
	public void testFechaNacimientoAnterior() throws BusinessException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.YEAR, -1);
		Date fechaAnterior = calendar.getTime();

		this.usuario.setFechaNacimiento(fechaAnterior);

		this.usuario.validar();
	}

	@Test(expected = BusinessException.class)
	public void testUsuarioSinFechaNacimiento() throws BusinessException {
		this.usuario.setFechaNacimiento(null);
		this.usuario.validar();
	}

	@Test(expected = BusinessException.class)
	public void testUsuarioSinNombre() throws BusinessException {
		this.usuario.setNombre(null);
		this.usuario.validar();
	}

	@Test(expected = BusinessException.class)
	public void testUsuarioSinPeso() throws BusinessException {
		this.usuario.setPeso(null);
		this.usuario.validar();
	}

	@Test(expected = BusinessException.class)
	public void testUsuarioSinAltura() throws BusinessException {
		this.usuario.setAltura(null);
		this.usuario.validar();
	}

	@Test(expected = BusinessException.class)
	public void testUsuarioSinRutina() throws BusinessException {
		this.usuario.setRutina(null);
		this.usuario.validar();
	}

	@Test(expected = BusinessException.class)
	public void testUsuarioConNombreMenorACuatroCaracters() throws BusinessException {
		this.usuario.setNombre("Nom");
		this.usuario.validar();
	}

	@Test(expected = BusinessException.class)
	public void testUsuarioConNombreIgualACuatroCaracteres() throws BusinessException {
		this.usuario.setNombre("Nomb");
		this.usuario.validar();
	}

	@Test(expected = BusinessException.class)
	public void testVeganoConPreferenciaInvalida() throws BusinessException {
		Vegano vegano = new Vegano();

		this.usuario.agregarPreferenciaAlimenticia("pollo");
		this.usuario.agregarCondicionPreexistente(vegano);

		this.usuario.validar();
	}

	@Test(expected = BusinessException.class)
	public void testDiabeticoSinSexo() throws BusinessException {
		Diabetico diabetico = new Diabetico();

		this.usuario.setSexo(null);
		this.usuario.agregarCondicionPreexistente(diabetico);

		this.usuario.validar();
	}

	@Test
	public void testDiabeticoConSexo() throws BusinessException {
		Diabetico diabetico = new Diabetico();

		this.usuario.agregarCondicionPreexistente(diabetico);
		this.usuario.agregarPreferenciaAlimenticia("pollo");

		this.usuario.validar();
	}

	@Test(expected = BusinessException.class)
	public void testHipertensoSinPreferencia() throws BusinessException {
		Hipertenso hipertenso = new Hipertenso();

		this.usuario.agregarCondicionPreexistente(hipertenso);

		this.usuario.validar();
	}

	@Test(expected = BusinessException.class)
	public void testDiabeticoSinPreferencia() throws BusinessException {
		Diabetico diabetico = new Diabetico();

		this.usuario.agregarCondicionPreexistente(diabetico);

		this.usuario.validar();
	}

	public void testHipertensoConPreferencia() throws BusinessException {
		Hipertenso hipertenso = new Hipertenso();

		this.usuario.agregarCondicionPreexistente(hipertenso);
		this.usuario.agregarPreferenciaAlimenticia("pollo");

		this.usuario.validar();
	}

	@Test
	public void testDiabeticoConPreferencia() throws BusinessException {
		Diabetico diabetico = new Diabetico();

		this.usuario.agregarCondicionPreexistente(diabetico);
		this.usuario.agregarPreferenciaAlimenticia("pollo");

		this.usuario.validar();
	}

	@Test(expected = BusinessException.class)
	public void testVeganoConpreferenciaPollo() throws BusinessException {
		Vegano vegano = new Vegano();

		this.usuario.agregarCondicionPreexistente(vegano);
		this.usuario.agregarPreferenciaAlimenticia("pollo");

		this.usuario.validar();
	}

	@Test(expected = BusinessException.class)
	public void testVeganoConpreferenciaCarne() throws BusinessException {
		Vegano vegano = new Vegano();

		this.usuario.agregarCondicionPreexistente(vegano);
		this.usuario.agregarPreferenciaAlimenticia("carne");

		this.usuario.validar();
	}

	@Test(expected = BusinessException.class)
	public void testVeganoConpreferenciaChori() throws BusinessException {
		Vegano vegano = new Vegano();

		this.usuario.agregarCondicionPreexistente(vegano);
		this.usuario.agregarPreferenciaAlimenticia("chori");

		this.usuario.validar();
	}

	@Test(expected = BusinessException.class)
	public void testVeganoConpreferenciaChivito() throws BusinessException {
		Vegano vegano = new Vegano();

		this.usuario.agregarCondicionPreexistente(vegano);
		this.usuario.agregarPreferenciaAlimenticia("chivito");

		this.usuario.validar();
	}

	@Test
	public void testVeganoConpreferenciaDiferenteAChivitoCarnePolloChori() throws BusinessException {
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

	// Punto 3: Hacer que un usuario agregue una receta
	@Test
	public void testAgregarReceta() throws BusinessException {
		Receta ravioles = new Receta(350);
		ravioles.agregarIngrediente("Harina", new BigDecimal(300));
		ravioles.agregarIngrediente("Agua", new BigDecimal(70));
		ravioles.agregarIngrediente("Verdura", new BigDecimal(100));

		this.usuario.agregarReceta(ravioles);
	}

	@Test(expected = BusinessException.class)
	public void testAgregarRecetaSinIngredientes() throws BusinessException {
		Receta receta = new Receta(350);
		this.usuario.agregarReceta(receta);

	}

	@Test(expected = BusinessException.class)
	public void testAgregarRecetaMenorAlRangoDeCalorias() throws BusinessException {
		Receta receta = new Receta(350);
		receta.setCalorias(2);
		this.usuario.agregarReceta(receta);
	}

	@Test(expected = BusinessException.class)
	public void testAgregarRecetaMayorAlRangoDeCalorias() throws BusinessException {
		Receta receta = new Receta(350);
		receta.setCalorias(99999);
		this.usuario.agregarReceta(receta);
	}

	@Test(expected = BusinessException.class)
	public void testRecetaHipertensoNoAcepta() throws BusinessException {
		Hipertenso hipertenso = new Hipertenso();
		this.usuario.agregarCondicionPreexistente(hipertenso);

		Receta receta = new Receta(350);
		receta.agregarIngrediente("sal", new BigDecimal(50));
		this.usuario.validarSiAceptaReceta(receta);

	}

	@Test(expected = BusinessException.class)
	public void testRecetaVeganoNoAcepta() throws BusinessException {
		Vegano veggie = new Vegano();
		this.usuario.agregarCondicionPreexistente(veggie);

		Receta receta = new Receta(350);
		receta.agregarIngrediente("chori", new BigDecimal(120));
		this.usuario.validarSiAceptaReceta(receta);
	}

	@Test(expected = BusinessException.class)
	public void testRecetaDiabeticoNoAcepta() throws BusinessException {
		Diabetico diabetico = new Diabetico();
		this.usuario.agregarCondicionPreexistente(diabetico);

		Receta receta = new Receta(150);
		receta.agregarIngrediente("azucar", new BigDecimal(120));
		this.usuario.validarSiAceptaReceta(receta);

	}

	@Test
	public void testRecetaHipertensoAcepta() throws BusinessException {
		Hipertenso hipertenso = new Hipertenso();
		this.usuario.agregarCondicionPreexistente(hipertenso);

		Receta receta = new Receta(350);
		receta.agregarIngrediente("arroz", new BigDecimal(200));
		this.usuario.validarSiAceptaReceta(receta);

	}

	@Test
	public void testRecetaVeganoAcepta() throws BusinessException {

		Vegano veggie = new Vegano();
		this.usuario.agregarCondicionPreexistente(veggie);

		Receta receta = new Receta(350);
		receta.agregarIngrediente("tomate", new BigDecimal(80));
		this.usuario.validarSiAceptaReceta(receta);
	}

	@Test
	public void testRecetaDiabeticoAceptaAzucar() throws BusinessException {
		Diabetico diabetico = new Diabetico();
		this.usuario.agregarCondicionPreexistente(diabetico);

		Receta receta = new Receta(150);
		receta.agregarIngrediente("azucar", new BigDecimal(50));
		this.usuario.validarSiAceptaReceta(receta);
	}

	@Test
	public void testVerRecetaPropia() throws BusinessException {
		Receta receta = new Receta(150);
		receta.agregarIngrediente("pollo", new BigDecimal(100));
		this.usuario.agregarReceta(receta);
		this.usuario.validarVerReceta(receta);
	}

	// Punto 4: Saber si un usuario puede ver a una receta dada
	@Test
	public void testVerRecetaPublica() throws BusinessException {
		Receta receta = new Receta(150);
		receta.agregarIngrediente("pollo", new BigDecimal(100));

		this.usuario.validarVerReceta(receta);
	}

	@Test(expected = BusinessException.class)
	public void testVerRecetaAjena() throws BusinessException {
		Usuario usuarioQueQuiereVer = this.crearUsuarioBasicoValido();
		Usuario usuarioQueAutoreo = this.crearUsuarioBasicoValido();
		Receta receta = new Receta(150);
		receta.agregarIngrediente("pollo", new BigDecimal(100));
		usuarioQueAutoreo.agregarReceta(receta);

		usuarioQueQuiereVer.validarVerReceta(receta);
	}

	// Punto4: Saber si un usuario puede modificar una receta dada
	@Test
	public void testPuedeModificarRecetaPublica() throws BusinessException {

		Receta receta = new Receta(150);
		receta.agregarIngrediente("pollo", new BigDecimal(100));

		this.usuario.validarModificarReceta(receta);
	}

	@Test(expected = BusinessException.class)
	public void testNoPuedeModificarReceta() throws BusinessException {
		Usuario usuarioQueQuiereModificar = this.crearUsuarioBasicoValido();
		Usuario usuarioQueAutoreo = this.crearUsuarioBasicoValido();
		Receta receta = new Receta(150);
		receta.agregarIngrediente("pollo", new BigDecimal(100));
		usuarioQueAutoreo.agregarReceta(receta);

		usuarioQueQuiereModificar.validarModificarReceta(receta);
	}

	@Test
	public void testPuedeModificarRecetaPropia() throws BusinessException {
		Receta receta = new Receta(150);
		receta.agregarIngrediente("pollo", new BigDecimal(100));
		this.usuario.agregarReceta(receta);
		this.usuario.validarModificarReceta(receta);
	}

	// Punto 4: Modificar una receta dada, respetando la validación del item anterior
	@Test
	public void testModificarRecetaPropia() throws BusinessException, CloneNotSupportedException {
		Receta receta = new Receta(150);
		receta.agregarIngrediente("pollo", new BigDecimal(100));
		receta.setNombre("Nombre original");
		this.usuario.agregarReceta(receta);

		this.usuario.validarModificarReceta(receta);
		this.usuario.modificarNombreDeReceta(receta, "Nuevo nombre");
		assertEquals(receta.getNombre(), "Nuevo nombre");
	}

	@Test
	public void testModificarRecetaPublica() throws BusinessException, CloneNotSupportedException {
		Receta receta = new Receta(150);
		receta.agregarIngrediente("pollo", new BigDecimal(100));
		receta.setNombre("Nombre original");
		
		this.usuario.validarModificarReceta(receta);
		this.usuario.modificarNombreDeReceta(receta, "Nuevo nombre");
		
		// La receta original sigue con el mismo nombre
		assertEquals(receta.getNombre(), "Nombre original");
	}

	@Test(expected = BusinessException.class)
	public void testModificarRecetaAjena() throws BusinessException, CloneNotSupportedException {

		Usuario usuarioOwner = this.crearUsuarioBasicoValido();
		Receta receta = new Receta(150);
		receta.agregarIngrediente("papa", new BigDecimal(100));
		usuarioOwner.agregarReceta(receta);

		this.usuario.validarModificarReceta(receta);
		this.usuario.modificarNombreDeReceta(receta, "Nuevo nombre");
	}

	@Test
	public void testClonarReceta() throws BusinessException, CloneNotSupportedException {
		Receta receta = new Receta(150);
		receta.agregarIngrediente("papa", new BigDecimal(100));

		Receta recetaClonada = receta.clone();
		recetaClonada.agregarIngrediente("papa", new BigDecimal(150));

		assertEquals(receta.getIngredientes().get("papa"), new BigDecimal(100));
		assertEquals(recetaClonada.getIngredientes().get("papa"), new BigDecimal(150));
	}

	// Punto 5: Poder construir una receta con sub-recetas.
	@Test
	public void testAgregaRecetaConSubrecetaPropia() throws BusinessException {
		Receta recetaPure = new Receta(150);
		recetaPure.agregarIngrediente("papa", new BigDecimal(100));
		this.usuario.agregarReceta(recetaPure);
		Receta recetaPollo = new Receta(350);
		recetaPollo.agregarIngrediente("pollo", new BigDecimal(100));

		recetaPollo.agregarSubReceta(recetaPure);
		this.usuario.puedeAgregarSubRecetas(recetaPollo.getSubRecetas());
	}

	@Test(expected = BusinessException.class)
	public void testAgregaRecetaConSubrecetaAjena() throws BusinessException {
		Usuario usuarioOwner = this.crearUsuarioBasicoValido();

		Receta recetaPure = new Receta(150);
		recetaPure.agregarIngrediente("papa", new BigDecimal(100));
		usuarioOwner.agregarReceta(recetaPure);
		Receta recetaPollo = new Receta(350);
		recetaPollo.agregarIngrediente("pollo", new BigDecimal(100));

		recetaPollo.agregarSubReceta(recetaPure);
		this.usuario.puedeAgregarSubRecetas(recetaPollo.getSubRecetas());
	}

	@Test
	public void testAgregaRecetaConSubrecetaPublica() throws BusinessException {
		Receta recetaPure = new Receta(150);
		recetaPure.agregarIngrediente("papa", new BigDecimal(100));
		Receta recetaPollo = new Receta(350);
		recetaPollo.agregarIngrediente("pollo", new BigDecimal(100));

		recetaPollo.agregarSubReceta(recetaPure);
		this.usuario.puedeAgregarSubRecetas(recetaPollo.getSubRecetas());

	}

	@Test
	public void testAgregaRecetaConSubrecetaVacia() throws BusinessException {
		// TODO: ?
	}

}
