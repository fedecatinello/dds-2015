package dds.javatar.app.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.receta.RecetaPrivadaCompuesta;
import dds.javatar.app.dto.receta.RecetaPrivadaSimple;
import dds.javatar.app.dto.receta.RecetaPublicaSimple;
import dds.javatar.app.dto.usuario.Rutina;
import dds.javatar.app.dto.usuario.Rutina.TipoRutina;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.dto.usuario.condiciones.CondicionPreexistente;
import dds.javatar.app.dto.usuario.condiciones.Diabetico;
import dds.javatar.app.dto.usuario.condiciones.Hipertenso;
import dds.javatar.app.dto.usuario.condiciones.Vegano;
import dds.javatar.app.util.exception.RecetaException;
import dds.javatar.app.util.exception.UsuarioException;

public class TestRecetas {

	private Usuario usuario;

	@Before
	public void initialize() {
		this.usuario = this.crearUsuarioBasicoValido();
	}

	public Usuario crearUsuarioBasicoValido() {
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

	public RecetaPrivadaSimple crearRecetaPrivadaSimple() {
		RecetaPrivadaSimple ravioles = new RecetaPrivadaSimple(350);
		ravioles.setNombre("Ravioles");
		ravioles.agregarIngrediente("Harina", new BigDecimal(300));
		ravioles.agregarIngrediente("Agua", new BigDecimal(70));
		ravioles.agregarIngrediente("Verdura", new BigDecimal(100));
		return ravioles;
	}

	public RecetaPrivadaCompuesta crearRecetaPrivadaCompuesta()
			throws RecetaException, RecetaException {

		RecetaPrivadaSimple condimentos = new RecetaPrivadaSimple(120);
		RecetaPrivadaSimple pure = new RecetaPrivadaSimple(350);
		RecetaPrivadaSimple pollo = new RecetaPrivadaSimple(220);
		RecetaPrivadaCompuesta polloConPure = new RecetaPrivadaCompuesta();

		condimentos.setNombre("Condimentos");
		condimentos.agregarIngrediente("Oregano", new BigDecimal(20));

		pure.setNombre("Pure");
		pure.agregarIngrediente("Manteca", new BigDecimal(300));
		pure.agregarIngrediente("Papa", new BigDecimal(300));

		pollo.agregarIngrediente("pollo", new BigDecimal(280));
		pollo.setNombre("pollo");

		polloConPure.agregarSubReceta(pollo);
		polloConPure.agregarSubReceta(pure);
		polloConPure.agregarSubReceta(condimentos);
		return polloConPure;
	}

	// Entrega 1 - Punto 3: Hacer que un usuario agregue una receta

	@Test
	public void testAgregarRecetaSimple() throws RecetaException {
		RecetaPrivadaSimple unaRecetaSimple = this.crearRecetaPrivadaSimple();

		this.usuario.agregarReceta(unaRecetaSimple);
	}

	@Test(expected = RecetaException.class)
	public void testAgregarRecetaSimpleSinIngredientes() throws RecetaException {
		RecetaPrivadaSimple receta = new RecetaPrivadaSimple(350);
		this.usuario.agregarReceta(receta);
	}

	@Test(expected = RecetaException.class)
	public void testAgregarRecetaMenorAlRangoDeCalorias()
			throws RecetaException {
		RecetaPrivadaSimple unaRecetaSimple = this.crearRecetaPrivadaSimple();
		unaRecetaSimple.setCalorias(2);
		this.usuario.agregarReceta(unaRecetaSimple);
	}

	@Test(expected = RecetaException.class)
	public void testAgregarRecetaMayorAlRangoDeCalorias()
			throws RecetaException {
		RecetaPrivadaSimple unaRecetaSimple = this.crearRecetaPrivadaSimple();
		unaRecetaSimple.setCalorias(99999);
		this.usuario.agregarReceta(unaRecetaSimple);
	}

	@Test
	public void testRecetaHipertensoNoAcepta() {
		Hipertenso hipertenso = new Hipertenso();
		this.usuario.agregarCondicionPreexistente(hipertenso);

		RecetaPrivadaSimple receta = new RecetaPrivadaSimple(350);
		receta.agregarIngrediente("sal", new BigDecimal(50));

		Set<CondicionPreexistente> noAcepta = new HashSet<CondicionPreexistente>();
		noAcepta.add(hipertenso);
		assertEquals(noAcepta,
				this.usuario.condicionesQueNoAcepta(this.usuario, receta));
		;
	}

	@Test
	public void testRecetaVeganoNoAcepta() {
		Vegano veggie = new Vegano();
		this.usuario.agregarCondicionPreexistente(veggie);

		RecetaPrivadaSimple receta = new RecetaPrivadaSimple(350);
		receta.agregarIngrediente("chori", new BigDecimal(120));
		// this.usuario.validarSiAceptaReceta(receta);

		assertFalse(this.usuario.validarSiAceptaReceta(receta));
	}

	@Test
	public void testRecetaDiabeticoNoAcepta() {
		Diabetico diabetico = new Diabetico();
		this.usuario.agregarCondicionPreexistente(diabetico);

		RecetaPrivadaSimple receta = new RecetaPrivadaSimple(150);
		receta.agregarIngrediente("azucar", new BigDecimal(120));

		Set<CondicionPreexistente> noAcepta = new HashSet<CondicionPreexistente>();
		noAcepta.add(diabetico);
		assertEquals(noAcepta,
				this.usuario.condicionesQueNoAcepta(this.usuario, receta));

	}

	@Test
	public void testRecetaHipertensoAcepta() {
		Hipertenso hipertenso = new Hipertenso();
		this.usuario.agregarCondicionPreexistente(hipertenso);

		RecetaPrivadaSimple receta = new RecetaPrivadaSimple(350);
		receta.agregarIngrediente("arroz", new BigDecimal(200));

		Set<CondicionPreexistente> noAcepta = new HashSet<CondicionPreexistente>();
		assertEquals(noAcepta,
				this.usuario.condicionesQueNoAcepta(this.usuario, receta));

	}

	@Test
	public void testRecetaVeganoAcepta() throws RecetaException {

		Vegano veggie = new Vegano();
		this.usuario.agregarCondicionPreexistente(veggie);

		RecetaPrivadaSimple receta = new RecetaPrivadaSimple(350);
		receta.agregarIngrediente("tomate", new BigDecimal(80));
		this.usuario.validarSiAceptaReceta(receta);
	}

	@Test
	public void testRecetaDiabeticoAceptaAzucar() {
		Diabetico diabetico = new Diabetico();
		this.usuario.agregarCondicionPreexistente(diabetico);

		RecetaPrivadaSimple receta = new RecetaPrivadaSimple(150);
		receta.agregarIngrediente("azucar", new BigDecimal(50));

		Set<CondicionPreexistente> noAcepta = new HashSet<CondicionPreexistente>();
		assertEquals(noAcepta,
				this.usuario.condicionesQueNoAcepta(this.usuario, receta));
	}

	@Test
	public void testVerRecetaPropia() throws RecetaException, UsuarioException {
		RecetaPrivadaSimple receta = new RecetaPrivadaSimple(150);
		receta.agregarIngrediente("pollo", new BigDecimal(100));
		this.usuario.agregarReceta(receta);
		this.usuario.puedeVerReceta(receta);
	}

	// Entrega 1 - Punto 4: Saber si un usuario puede ver a una receta dada
	@Test
	public void testVerRecetaPublica() throws RecetaException, UsuarioException {
		RecetaPublicaSimple receta = new RecetaPublicaSimple(150);
		receta.agregarIngrediente("pollo", new BigDecimal(100));
		this.usuario.agregarReceta(receta);
		this.usuario.puedeVerReceta(receta);
	}

	@Test(expected = UsuarioException.class)
	public void testVerRecetaAjena() throws RecetaException, UsuarioException {
		Usuario usuarioQueQuiereVer = this.crearUsuarioBasicoValido();
		Usuario userOwner = this.crearUsuarioBasicoValido();

		RecetaPrivadaSimple receta = new RecetaPrivadaSimple(150);
		receta.agregarIngrediente("pollo", new BigDecimal(100));
		userOwner.agregarReceta(receta);

		usuarioQueQuiereVer.puedeVerReceta(receta);
	}

	// Entrega 1 - Punto4: Saber si un usuario puede modificar una receta dada
	@Test
	public void testPuedeModificarRecetaPublica() throws RecetaException, UsuarioException {
		RecetaPublicaSimple receta = new RecetaPublicaSimple(150);
		receta.agregarIngrediente("pollo", new BigDecimal(100));
		this.usuario.agregarReceta(receta);
		this.usuario.puedeModificarReceta(receta);
	}

	@Test(expected = UsuarioException.class)
	public void testNoPuedeModificarReceta() throws RecetaException, UsuarioException {

		Usuario usuarioQueQuiereModificar = this.crearUsuarioBasicoValido();
		Usuario userOwner = this.crearUsuarioBasicoValido();

		RecetaPrivadaSimple receta = new RecetaPrivadaSimple(150);
		receta.agregarIngrediente("pollo", new BigDecimal(100));
		userOwner.agregarReceta(receta);
		usuarioQueQuiereModificar.modificarNombreDeReceta(receta, "unNombreReCopado");
	}

	@Test
	public void testPuedeModificarRecetaPropia() throws RecetaException,
			UsuarioException {

		RecetaPrivadaSimple receta = new RecetaPrivadaSimple(150);
		receta.agregarIngrediente("pollo", new BigDecimal(100));
		this.usuario.agregarReceta(receta);
		this.usuario.validarModificarReceta(receta);
	}

	// Entrega 1 - Punto 4: Modificar una receta dada, respetando la validación del item
	// anterior
	@Test
	public void testModificarRecetaPropia() throws RecetaException,
			CloneNotSupportedException, UsuarioException {
		RecetaPrivadaSimple receta1 = new RecetaPrivadaSimple(150);
		receta1.agregarIngrediente("pollo", new BigDecimal(100));
		receta1.setNombre("Nombre original");
		this.usuario.agregarReceta(receta1);

		this.usuario.validarModificarReceta(receta1);
		this.usuario.modificarNombreDeReceta(receta1, "Nuevo nombre");
		assertEquals(receta1.getNombre(), "Nuevo nombre");
	}
	
	@Test
	public void testModificarRecetaPublica() throws RecetaException,
			CloneNotSupportedException, UsuarioException {
		RecetaPublicaSimple receta = new RecetaPublicaSimple(150);
		receta.agregarIngrediente("pollo", new BigDecimal(100));
		receta.setNombre("Nombre original");
		this.usuario.agregarReceta(receta);
		this.usuario.modificarNombreDeReceta(receta, "Nuevo nombre");
		
		// La receta original sigue con el mismo nombre
		assertEquals(receta.getNombre(), "Nombre original");
	}

	@Test(expected = UsuarioException.class)
	public void testModificarRecetaAjena() throws RecetaException,
			CloneNotSupportedException, UsuarioException {

		Usuario usuarioOwner = this.crearUsuarioBasicoValido();
		RecetaPrivadaSimple receta = new RecetaPrivadaSimple(150);
		receta.agregarIngrediente("papa", new BigDecimal(100));
		usuarioOwner.agregarReceta(receta);

		this.usuario.modificarNombreDeReceta(receta, "Nuevo nombre");
	}

	@Test
	public void testClonarReceta() throws RecetaException,
			CloneNotSupportedException {
		RecetaPublicaSimple receta = new RecetaPublicaSimple(150);
		receta.agregarIngrediente("papa", new BigDecimal(100));

		RecetaPrivadaSimple recetaClonada = (RecetaPrivadaSimple) receta.clonarme();
		recetaClonada.agregarIngrediente("papa", new BigDecimal(150));

		assertEquals(receta.getIngredientes().get("papa"), new BigDecimal(100));
		assertEquals(recetaClonada.getIngredientes().get("papa"),
				new BigDecimal(150));
	}

	// Entrega 1 - Punto 5: Poder construir una receta con sub-recetas.

	@Test
	public void testAgregarRecetaCompuesta() throws RecetaException {
		RecetaPrivadaCompuesta unaRecetaCompuesta = this.crearRecetaPrivadaCompuesta();
		this.usuario.agregarReceta(unaRecetaCompuesta);

	}

	@Test(expected = RecetaException.class)
	public void testAgregarRecetaCompuestaSinSubrecetas()
			throws RecetaException {
		RecetaPrivadaCompuesta polloConPure = new RecetaPrivadaCompuesta();
		polloConPure.setNombre("PolloConPure");
		this.usuario.agregarReceta(polloConPure);
	}

	@Test(expected = RecetaException.class)
	public void testAgregarRecetaCompuestaConSubrecetaSinIngredientes()
			throws RecetaException {
		RecetaPrivadaSimple pollo = new RecetaPrivadaSimple(120);
		RecetaPrivadaSimple pure = new RecetaPrivadaSimple(350);
		RecetaPrivadaCompuesta polloConPure = new RecetaPrivadaCompuesta();

		pollo.setNombre("pollo");
		pollo.agregarIngrediente("Alas y pechugas", new BigDecimal(20));

		pure.setNombre("Pure");

		polloConPure.setNombre("PolloConPure");
		polloConPure.agregarSubReceta(pollo);
		polloConPure.agregarSubReceta(pure);
		this.usuario.agregarReceta(polloConPure);
	}

	@Test
	public void testAgregaRecetaConSubrecetaPropia() throws RecetaException {
		RecetaPrivadaSimple pure = new RecetaPrivadaSimple(350);
		RecetaPrivadaCompuesta polloConPure = new RecetaPrivadaCompuesta();
		RecetaPrivadaSimple pollo = new RecetaPrivadaSimple(120);

		pollo.setNombre("pollo");
		pollo.agregarIngrediente("Alas y pechugas", new BigDecimal(20));

		pure.setNombre("Pure");
		pure.agregarIngrediente("Manteca", new BigDecimal(300));
		pure.agregarIngrediente("Papa", new BigDecimal(300));

		polloConPure.setNombre("PolloConPure");
		polloConPure.agregarSubReceta(pure);
		polloConPure.agregarSubReceta(pollo);
		this.usuario.agregarReceta(polloConPure);

	}

	// Entrega 2 - Punto 3: Marcar una receta como favorita.
	
	@Test
	public void testMarcarUnaRecetaComoFavorita() throws RecetaException {

		RecetaPublicaSimple receta = new RecetaPublicaSimple(80);
		receta.agregarIngrediente("pollo", new BigDecimal(80));
		this.usuario.marcarFavorita(receta);

		assertEquals(1, this.usuario.getFavoritos().size());

	}
	
	
	// Entrega 3 - Punto 2: Nuevo origen para las recetas
	
	@Test
	public void testConsultarRecetaExternaPorNombre() throws RecetaException {
		
		List<String> palabrasClaves = new ArrayList<String>();
			
		List<Receta> recetasEncontradas = new ArrayList<Receta>();

		recetasEncontradas = this.usuario.consultarRecetasExternas("ensalada", null, palabrasClaves);
		
		assertEquals(3, recetasEncontradas.size());
	}
	
	@Test
	public void testConsultarRecetaExternaPorNombreQueNoExiste() throws RecetaException {
		
		List<String> palabrasClaves = new ArrayList<String>();
			
		List<Receta> recetasEncontradas = new ArrayList<Receta>();

		recetasEncontradas = this.usuario.consultarRecetasExternas("omelette", null, palabrasClaves);
		
		assertEquals(0, recetasEncontradas.size());
	}
	
	@Test
	public void testConsultarRecetaExternaConDificultadFacil() throws RecetaException {
		
		List<String> palabrasClaves = new ArrayList<String>();
			
		List<Receta> recetasEncontradas = new ArrayList<Receta>();

		recetasEncontradas = this.usuario.consultarRecetasExternas(null, "F", palabrasClaves);
		
		assertEquals(6, recetasEncontradas.size());
	}
	
	@Test
	public void testConsultarRecetaExternaConDificultadMediana() throws RecetaException {
		
		List<String> palabrasClaves = new ArrayList<String>();
			
		List<Receta> recetasEncontradas = new ArrayList<Receta>();

		recetasEncontradas = this.usuario.consultarRecetasExternas(null, "M", palabrasClaves);
		
		assertEquals(4, recetasEncontradas.size());
	}
	
	@Test
	public void testConsultarRecetaExternaConDificultadDificil() throws RecetaException {
		
		List<String> palabrasClaves = new ArrayList<String>();
			
		List<Receta> recetasEncontradas = new ArrayList<Receta>();

		recetasEncontradas = this.usuario.consultarRecetasExternas(null, "D", palabrasClaves);
		
		assertEquals(2, recetasEncontradas.size());
	}
	
	@Test
	public void testConsultarRecetaExternaPorPalabrasClaves() throws RecetaException {
		
		List<String> palabrasClaves = new ArrayList<String>();
		palabrasClaves.add("tomate");
		palabrasClaves.add("parmesano");
		palabrasClaves.add("salmon");
		palabrasClaves.add("zanahoria");
		palabrasClaves.add("acelga");
			
		List<Receta> recetasEncontradas = new ArrayList<Receta>();

		recetasEncontradas = this.usuario.consultarRecetasExternas(null, null, palabrasClaves);
		
		assertEquals(8, recetasEncontradas.size());
	}
	
	@Test
	public void testConsultarRecetasPorTresCampos() throws RecetaException {
			
		List<String> palabrasClaves = new ArrayList<String>();
		palabrasClaves.add("helado de chocolate");
		palabrasClaves.add("helado de frutilla");
			
		List<Receta> recetasEncontradas = new ArrayList<Receta>();

		recetasEncontradas = this.usuario.consultarRecetasExternas("cassatta", "F", palabrasClaves);
		
		assertEquals(1, recetasEncontradas.size());
	}
	
	@Test
	public void testConsultarRecetaCarnicera() throws RecetaException {
		
		List<String> palabrasClaves = new ArrayList<String>();
		palabrasClaves.add("bife angosto");
		palabrasClaves.add("tomillo");
			
		List<Receta> recetasEncontradas = new ArrayList<Receta>();

		recetasEncontradas = this.usuario.consultarRecetasExternas("churrasco a la sal", "F", palabrasClaves);

		assertEquals(1, recetasEncontradas.size());
	}

	@Test
	public void testConsultarRecetasconVerduras() throws RecetaException {
	
		List<String> palabrasClaves = new ArrayList<String>();
		palabrasClaves.add("lechuga");
		palabrasClaves.add("zanahoria");
		palabrasClaves.add("calabaza");
		palabrasClaves.add("papa");
		palabrasClaves.add("tomate");
		palabrasClaves.add("albahaca");
		palabrasClaves.add("acelga");
		palabrasClaves.add("palta");
			
		List<Receta> recetasEncontradas = new ArrayList<Receta>();
		
		recetasEncontradas = this.usuario.consultarRecetasExternas(null, null, palabrasClaves);

		assertEquals(7, recetasEncontradas.size());
	}
		
	@Test
	public void testConsultarRecetasDulces() throws RecetaException {
		
		List<String> palabrasClaves = new ArrayList<String>();
	 	palabrasClaves.add("leche");
	 	palabrasClaves.add("azucar");
	 	palabrasClaves.add("helado de chocolate");
			
		List<Receta> recetasEncontradas = new ArrayList<Receta>();

		recetasEncontradas = this.usuario.consultarRecetasExternas(null, null, palabrasClaves);
		
		assertEquals(2, recetasEncontradas.size());
	}
	
	
	@Test
	public void testConsultarRecetasDeMar() throws RecetaException {
		List<String> palabrasClaves = new ArrayList<String>();
	 	palabrasClaves.add("salmon");
	 	palabrasClaves.add("mejillones");
			
		List<Receta> recetasEncontradas = new ArrayList<Receta>();

		recetasEncontradas = this.usuario.consultarRecetasExternas(null, null, palabrasClaves);
		
		assertEquals(3, recetasEncontradas.size());
	}
}
