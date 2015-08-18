package dds.javatar.app.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import queComemos.entrega3.dominio.Dificultad;
import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.receta.RecetaPrivadaSimple;
import dds.javatar.app.dto.receta.RecetaPublica;
import dds.javatar.app.dto.receta.builder.RecetaBuilder;
import dds.javatar.app.dto.receta.busqueda.Busqueda;
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
		this.usuario = TestFactory.crearUsuarioBasicoValido();

	}
/**CODIGO ANTERIOR
	public RecetaPrivadaSimple crearRecetaPrivadaSimple() {
		RecetaPrivadaSimple ravioles = new RecetaPrivadaSimple(350);
		ravioles.setNombre("Ravioles");
		ravioles.agregarIngrediente("Harina", new BigDecimal(300));
		ravioles.agregarIngrediente("Agua", new BigDecimal(70));
		ravioles.agregarIngrediente("Verdura", new BigDecimal(100));
		return ravioles;
	}*/
	
	public Receta crearRecetaPrivadaSimple() {
		return new RecetaBuilder("Ravioles")
					.totalCalorias(350)
					.agregarIngrediente("Harina", new BigDecimal(300))
					.agregarIngrediente("Agua", new BigDecimal(70))
					.agregarIngrediente("Verdura", new BigDecimal(100))
					.inventadaPor("Santi")
					.buildReceta();
	}


/**	CODIGO ANTERIOR
 * public RecetaPrivadaCompuesta crearRecetaPrivadaCompuesta()
			throws RecetaException{
=======
	public RecetaPrivadaCompuesta crearRecetaPrivadaCompuesta() throws RecetaException, RecetaException {
>>>>>>> refs/remotes/origin/master

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
	}*/
	
	public Receta crearRecetaPrivadaCompuesta()
			throws RecetaException{
		
		 Receta recetaPrivadaSimplePure = new RecetaBuilder("Pure")
		 						.totalCalorias(350)
		 						.agregarIngrediente("Manteca", new BigDecimal(300))
		 						.agregarIngrediente("Papa", new BigDecimal(300))
		 						.inventadaPor("Joni")
		 						.buildReceta();
		 Receta recetaPrivadaSimplePollo = new RecetaBuilder("Pollo")
		 						.totalCalorias(220)
		 						.agregarIngrediente("pollo", new BigDecimal(280))
		 						.inventadaPor("Mati")
		 						.buildReceta();
		 Receta recetaPrivadaSimpleCondimentos = new RecetaBuilder("Condimentos")
		 						.totalCalorias(120)
		 						.agregarIngrediente("Oregano", new BigDecimal(20))
		 						.inventadaPor("Mariano")
		 						.buildReceta();
		
		 return new RecetaBuilder("pollo con pure")
		 						.agregarSubReceta(recetaPrivadaSimplePollo)
		 						.agregarSubReceta(recetaPrivadaSimpleCondimentos)
		 						.agregarSubReceta(recetaPrivadaSimplePure)
		 						.inventadaPor("Donato")
		 						.buildReceta();
	}

	// Entrega 1 - Punto 3: Hacer que un usuario agregue una receta

	@Test
	public void testAgregarRecetaSimple() throws RecetaException {
		Receta unaRecetaSimple = this.crearRecetaPrivadaSimple();

		this.usuario.agregarReceta(unaRecetaSimple);
	}

	@Test(expected = RecetaException.class)
	public void testAgregarRecetaSimpleSinIngredientes() throws RecetaException {
		Receta receta = new RecetaBuilder("papas fritas")
							.totalCalorias(350)
							.inventadaPor("Lean")
							.buildReceta();
		
		this.usuario.agregarReceta(receta);
	}

	@Test(expected = RecetaException.class)
	public void testAgregarRecetaMenorAlRangoDeCalorias() throws RecetaException {
		Receta unaRecetaSimple = this.crearRecetaPrivadaSimple();
		unaRecetaSimple.setCalorias(2);
		this.usuario.agregarReceta(unaRecetaSimple);
	}

	@Test(expected = RecetaException.class)
	public void testAgregarRecetaMayorAlRangoDeCalorias() throws RecetaException {
		Receta unaRecetaSimple = this.crearRecetaPrivadaSimple();
		unaRecetaSimple.setCalorias(99999);
		this.usuario.agregarReceta(unaRecetaSimple);
	}

	@Test
	public void testRecetaHipertensoNoAcepta() {
		Hipertenso hipertenso = new Hipertenso();
		this.usuario.agregarCondicionPreexistente(hipertenso);

		Receta receta = new RecetaBuilder("papas fritas")
					.totalCalorias(350)
					.agregarIngrediente("sal", new BigDecimal(50))
					.inventadaPor("Cami")
					.buildReceta();
		Set<CondicionPreexistente> noAcepta = new HashSet<CondicionPreexistente>();
		noAcepta.add(hipertenso);
		assertEquals(noAcepta, this.usuario.condicionesQueNoAcepta(this.usuario, receta));
		;
	}

	@Test
	public void testRecetaVeganoNoAcepta() {
		Vegano veggie = new Vegano();
		this.usuario.agregarCondicionPreexistente(veggie);

		Receta receta = new RecetaBuilder("papas fritas")
					.totalCalorias(350)
					.agregarIngrediente("chori", new BigDecimal(120))
					.inventadaPor("Nico")
					.buildReceta();
				
		assertFalse(this.usuario.validarSiAceptaReceta(receta));
	}

	@Test
	public void testRecetaDiabeticoNoAcepta() {
		Diabetico diabetico = new Diabetico();
		this.usuario.agregarCondicionPreexistente(diabetico);

		Receta receta = new RecetaBuilder("chocolate")
					.totalCalorias(250)
					.agregarIngrediente("azucar", new BigDecimal(120))
					.inventadaPor("Pablo")
					.buildReceta();

		Set<CondicionPreexistente> noAcepta = new HashSet<CondicionPreexistente>();
		noAcepta.add(diabetico);
		assertEquals(noAcepta, this.usuario.condicionesQueNoAcepta(this.usuario, receta));

	}

	@Test
	public void testRecetaHipertensoAcepta() {
		Hipertenso hipertenso = new Hipertenso();
		this.usuario.agregarCondicionPreexistente(hipertenso);

		Receta receta = new RecetaBuilder("paella")
					.totalCalorias(350)
					.agregarIngrediente("arroz", new BigDecimal(200))
					.inventadaPor("Ale")
					.buildReceta();

		Set<CondicionPreexistente> noAcepta = new HashSet<CondicionPreexistente>();
		assertEquals(noAcepta, this.usuario.condicionesQueNoAcepta(this.usuario, receta));

	}

	@Test
	public void testRecetaVeganoAcepta() throws RecetaException {

		Vegano veggie = new Vegano();
		this.usuario.agregarCondicionPreexistente(veggie);

		Receta receta = new RecetaBuilder("paella")
					.totalCalorias(350)
					.agregarIngrediente("tomate", new BigDecimal(80))
					.inventadaPor("Carlos")
					.buildReceta();
		
		this.usuario.validarSiAceptaReceta(receta);
	}

	@Test
	public void testRecetaDiabeticoAceptaAzucar() {
		Diabetico diabetico = new Diabetico();
		this.usuario.agregarCondicionPreexistente(diabetico);

		Receta receta = new RecetaBuilder("chocolate")
					.totalCalorias(150)
					.agregarIngrediente("azucar", new BigDecimal(50))
					.inventadaPor("Jose")
					.buildReceta();

		Set<CondicionPreexistente> noAcepta = new HashSet<CondicionPreexistente>();
		assertEquals(noAcepta, this.usuario.condicionesQueNoAcepta(this.usuario, receta));
	}

	@Test
	public void testVerRecetaPropia() throws RecetaException, UsuarioException {
		Receta receta = new RecetaBuilder("Paella")
					.totalCalorias(new Integer(350))
					.agregarIngrediente("pollo", new BigDecimal(100))
					.inventadaPor(this.usuario.getNombre())
					.buildReceta();
		this.usuario.agregarReceta(receta);
		this.usuario.puedeVerReceta(receta);
	}

	// Entrega 1 - Punto 4: Saber si un usuario puede ver a una receta dada
	@Test
	public void testVerRecetaPublica() throws RecetaException, UsuarioException {
		
		Receta receta = new RecetaBuilder("Pollo con pure")
				.totalCalorias(150)
				.agregarIngrediente("pollo", new BigDecimal(100))
				.buildReceta();
		
		this.usuario.agregarReceta(receta);
		this.usuario.puedeVerReceta(receta);
	}

	@Test(expected = UsuarioException.class)
	public void testVerRecetaAjena() throws RecetaException, UsuarioException {
		Usuario usuarioQueQuiereVer = TestFactory.crearUsuarioBasicoValido();
		Usuario userOwner = TestFactory.crearUsuarioBasicoValido();

		Receta receta = new RecetaBuilder("paella")
				.totalCalorias(50)
				.agregarIngrediente("pollo", new BigDecimal(100))
				.inventadaPor(userOwner.getNombre())
				.buildReceta();
		userOwner.agregarReceta(receta);

		usuarioQueQuiereVer.puedeVerReceta(receta);
	}

	// Entrega 1 - Punto4: Saber si un usuario puede modificar una receta dada
	@Test
	public void testPuedeModificarRecetaPublica() throws RecetaException, UsuarioException {
		Receta receta = new RecetaBuilder("Pollo con pure")
					.totalCalorias(150)
					.agregarIngrediente("pollo", new BigDecimal(100))
					.buildReceta();
		
		this.usuario.agregarReceta(receta);
		this.usuario.puedeModificarReceta(receta);
	}

	@Test(expected = UsuarioException.class)
	public void testNoPuedeModificarReceta() throws RecetaException, UsuarioException {

		Usuario usuarioQueQuiereModificar = TestFactory.crearUsuarioBasicoValido();
		Usuario userOwner = TestFactory.crearUsuarioBasicoValido();

		Receta receta = new RecetaBuilder("paella")
					.totalCalorias(150)
					.agregarIngrediente("pollo", new BigDecimal(100))
					.inventadaPor("OtraPersona")
					.buildReceta();
		
		userOwner.agregarReceta(receta);
		usuarioQueQuiereModificar.modificarNombreDeReceta(receta, "unNombreReCopado");
	}

	@Test
	public void testPuedeModificarRecetaPropia() throws RecetaException, UsuarioException {

		Receta receta = new RecetaBuilder("paella")
					.totalCalorias(150)
					.agregarIngrediente("pollo", new BigDecimal(100))
					.inventadaPor(this.usuario.getNombre())
					.buildReceta();
		
		this.usuario.agregarReceta(receta);
		this.usuario.validarModificarReceta(receta);
	}

	// Entrega 1 - Punto 4: Modificar una receta dada, respetando la validación
	// del item
	// anterior
	@Test
	public void testModificarRecetaPropia() throws RecetaException,
			CloneNotSupportedException, UsuarioException {
		Receta receta1 = new RecetaBuilder("Nombre original")
					.totalCalorias(350)
					.inventadaPor(this.usuario.getNombre())
					.agregarIngrediente("pollo", new BigDecimal(100))
					.buildReceta();
	

		this.usuario.agregarReceta(receta1);

		this.usuario.validarModificarReceta(receta1);
		this.usuario.modificarNombreDeReceta(receta1, "Nuevo nombre");
		assertEquals(receta1.getNombre(), "Nuevo nombre");
	}

	@Test
	public void testModificarRecetaPublica() throws RecetaException, CloneNotSupportedException, UsuarioException {
		
		Receta receta = new RecetaBuilder("Pollo con pure")
					.totalCalorias(150)
					.agregarIngrediente("pollo", new BigDecimal(100))
					.buildReceta();
		
		this.usuario.agregarReceta(receta);
		this.usuario.modificarNombreDeReceta(receta, "Paella");

		// La receta original sigue con el mismo nombre
		assertEquals(receta.getNombre(), "Pollo con pure");
	}

	@Test(expected = UsuarioException.class)
	public void testModificarRecetaAjena() throws RecetaException, CloneNotSupportedException, UsuarioException {

		Usuario usuarioOwner = TestFactory.crearUsuarioBasicoValido();
		Receta receta = new RecetaBuilder("paella")
					.totalCalorias(150)
					.agregarIngrediente("papa", new BigDecimal(100))
					.inventadaPor("OtraPersona")
					.buildReceta();
		
		usuarioOwner.agregarReceta(receta);

		this.usuario.modificarNombreDeReceta(receta, "Nuevo nombre");
	}

	@Test
	public void testClonarReceta() throws RecetaException, CloneNotSupportedException {
		RecetaPublica receta = (RecetaPublica) new RecetaBuilder("Papas españolas")
					.totalCalorias(new Integer(150))
					.agregarIngrediente("papa", new BigDecimal(100))
					.buildReceta();
		
		RecetaPrivadaSimple recetaClonada = (RecetaPrivadaSimple) receta.clonarme("Usuario");
		recetaClonada.agregarIngrediente("papa", new BigDecimal(150));

		assertEquals(receta.getIngredientes().get("papa"), new BigDecimal(100));
		assertEquals(recetaClonada.getIngredientes().get("papa"), new BigDecimal(150));
	}

	// Entrega 1 - Punto 5: Poder construir una receta con sub-recetas.

	@Test
	public void testAgregarRecetaCompuesta() throws RecetaException {
		Receta unaRecetaCompuesta = this.crearRecetaPrivadaCompuesta();
		this.usuario.agregarReceta(unaRecetaCompuesta);

	}

	@Test(expected = RecetaException.class)
	public void testAgregarRecetaCompuestaSinSubrecetas() throws RecetaException {
		
		Receta polloConPure = new RecetaBuilder("PolloConPure")
						.inventadaPor("German")
						.buildReceta();
		this.usuario.agregarReceta(polloConPure);
	}

	@Test(expected = RecetaException.class)
	public void testAgregarRecetaCompuestaConSubrecetaSinIngredientes()
			throws RecetaException {
		  Receta pollo = new RecetaBuilder("pollo")
		  			.totalCalorias(120)
		  			.inventadaPor("Santi")
		  			.agregarIngrediente("Alas y pechugas", new BigDecimal(20))
		  			.buildReceta();
		 Receta pure = new RecetaBuilder("pure")
		 			.totalCalorias(350)
		 			.inventadaPor("Joni")
		 			.buildReceta();
		 Receta polloConPure = new RecetaBuilder("PolloConPure")
		 			.agregarSubReceta(pollo)
		 			.agregarSubReceta(pure)
		 			.inventadaPor("Pepe")
		 			.buildReceta();

		this.usuario.agregarReceta(polloConPure);
	}

	@Test
	public void testAgregaRecetaConSubrecetaPropia() throws RecetaException {
		Receta pure = new RecetaBuilder("Pure")
					.totalCalorias(350)
					.agregarIngrediente("Manteca", new BigDecimal(300))
					.agregarIngrediente("Papa", new BigDecimal(300))
					.inventadaPor("Mariano")
					.buildReceta();
		
		Receta pollo= new RecetaBuilder("Pollo")
					.totalCalorias(120)
					.agregarIngrediente("Alas y pechugas", new BigDecimal(20))
					.inventadaPor("Colo")
					.buildReceta();
		
		Receta polloConPure = new RecetaBuilder("PolloConPure")
					.agregarSubReceta(pure)
					.agregarSubReceta(pollo)
					.inventadaPor("Santi")
					.buildReceta();
		
		this.usuario.agregarReceta(polloConPure);

	}

	// Entrega 2 - Punto 3: Marcar una receta como favorita.

	@Test
	public void testMarcarUnaRecetaComoFavorita() throws RecetaException {

		Receta receta = new RecetaBuilder("Pollo a la mostaza")
					.totalCalorias(80)
					.agregarIngrediente("pollo", new BigDecimal(80))
					.inventadaPor("Fede")
					.buildReceta();
		
		this.usuario.marcarFavorita(receta);

		assertEquals(1, this.usuario.getFavoritos().size());

	}

	// Entrega 3 - Punto 2: Nuevo origen para las recetas

	@Test
	public void testConsultarRecetaExternaPorNombre() throws RecetaException {

		List<Receta> recetasEncontradas = new ArrayList<Receta>();

		Busqueda unaBusqueda = new Busqueda.BusquedaBuilder().nombre("ensalada").dificultad(null).palabrasClave(new ArrayList<String>()).build();
		recetasEncontradas = this.usuario.consultarRecetasExternas(unaBusqueda);

		assertEquals(3, recetasEncontradas.size());
	}

	@Test
	public void testConsultarRecetaExternaPorNombreQueNoExiste() throws RecetaException {

		List<Receta> recetasEncontradas = new ArrayList<Receta>();

		Busqueda unaBusqueda = new Busqueda.BusquedaBuilder().nombre("omelette").dificultad(null).palabrasClave(new ArrayList<String>()).build();

		recetasEncontradas = this.usuario.consultarRecetasExternas(unaBusqueda);

		assertEquals(0, recetasEncontradas.size());
	}

	@Test
	public void testConsultarRecetaExternaConDificultadFacil() throws RecetaException {

		List<Receta> recetasEncontradas = new ArrayList<Receta>();
		Busqueda unaBusqueda = new Busqueda.BusquedaBuilder().dificultad(Dificultad.FACIL).palabrasClave(new ArrayList<String>()).build();

		recetasEncontradas = this.usuario.consultarRecetasExternas(unaBusqueda);

		assertEquals(6, recetasEncontradas.size());
	}

	@Test
	public void testConsultarRecetaExternaConDificultadMediana() throws RecetaException {

		List<Receta> recetasEncontradas = new ArrayList<Receta>();
		Busqueda unaBusqueda = new Busqueda.BusquedaBuilder().dificultad(Dificultad.MEDIANA).palabrasClave(new ArrayList<String>()).build();

		recetasEncontradas = this.usuario.consultarRecetasExternas(unaBusqueda);

		assertEquals(4, recetasEncontradas.size());
	}

	@Test
	public void testConsultarRecetaExternaConDificultadDificil() throws RecetaException {

		List<Receta> recetasEncontradas = new ArrayList<Receta>();
		Busqueda unaBusqueda = new Busqueda.BusquedaBuilder().dificultad(Dificultad.DIFICIL).palabrasClave(new ArrayList<String>()).build();

		recetasEncontradas = this.usuario.consultarRecetasExternas(unaBusqueda);

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
		Busqueda unaBusqueda = new Busqueda.BusquedaBuilder().palabrasClave(palabrasClaves).build();

		recetasEncontradas = this.usuario.consultarRecetasExternas(unaBusqueda);

		assertEquals(8, recetasEncontradas.size());
	}

	@Test
	public void testConsultarRecetasPorTresCampos() throws RecetaException {

		List<String> palabrasClaves = new ArrayList<String>();
		palabrasClaves.add("helado de chocolate");
		palabrasClaves.add("helado de frutilla");

		List<Receta> recetasEncontradas = new ArrayList<Receta>();
		Busqueda unaBusqueda = new Busqueda.BusquedaBuilder().nombre("cassatta").dificultad(Dificultad.FACIL).palabrasClave(palabrasClaves).build();

		recetasEncontradas = this.usuario.consultarRecetasExternas(unaBusqueda);

		assertEquals(1, recetasEncontradas.size());
	}

	@Test
	public void testConsultarRecetaCarnicera() throws RecetaException {

		List<String> palabrasClaves = new ArrayList<String>();
		palabrasClaves.add("bife angosto");
		palabrasClaves.add("tomillo");

		List<Receta> recetasEncontradas = new ArrayList<Receta>();
		Busqueda unaBusqueda = new Busqueda.BusquedaBuilder().nombre("churrasco a la sal").dificultad(Dificultad.FACIL).palabrasClave(palabrasClaves).build();

		recetasEncontradas = this.usuario.consultarRecetasExternas(unaBusqueda);

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
		Busqueda unaBusqueda = new Busqueda.BusquedaBuilder().palabrasClave(palabrasClaves).build();

		recetasEncontradas = this.usuario.consultarRecetasExternas(unaBusqueda);

		assertEquals(7, recetasEncontradas.size());
	}

	@Test
	public void testConsultarRecetasDulces() throws RecetaException {

		List<String> palabrasClaves = new ArrayList<String>();
		palabrasClaves.add("leche");
		palabrasClaves.add("azucar");
		palabrasClaves.add("helado de chocolate");

		List<Receta> recetasEncontradas = new ArrayList<Receta>();

		Busqueda unaBusqueda = new Busqueda.BusquedaBuilder().palabrasClave(palabrasClaves).build();

		recetasEncontradas = this.usuario.consultarRecetasExternas(unaBusqueda);

		assertEquals(2, recetasEncontradas.size());
	}

	@Test
	public void testConsultarRecetasDeMar() throws RecetaException {
		List<String> palabrasClaves = new ArrayList<String>();
		palabrasClaves.add("salmon");
		palabrasClaves.add("mejillones");

		List<Receta> recetasEncontradas = new ArrayList<Receta>();

		Busqueda unaBusqueda = new Busqueda.BusquedaBuilder().palabrasClave(palabrasClaves).build();

		recetasEncontradas = this.usuario.consultarRecetasExternas(unaBusqueda);

		assertEquals(3, recetasEncontradas.size());
	}
}
