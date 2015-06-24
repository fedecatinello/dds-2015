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
import dds.javatar.app.dto.receta.RecetaPrivadaCompuesta;
import dds.javatar.app.dto.receta.RecetaPrivadaSimple;
import dds.javatar.app.dto.receta.RecetaPublicaSimple;
import dds.javatar.app.dto.receta.builder.RecetaPrivadaCompuestaBuilder;
import dds.javatar.app.dto.receta.builder.RecetaPrivadaSimpleBuilder;
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
	
	public RecetaPrivadaSimple crearRecetaPrivadaSimple() {
		return new RecetaPrivadaSimpleBuilder("Ravioles").totalCalorias(350).agregarIngrediente("Harina", new BigDecimal(300)).agregarIngrediente("Agua", new BigDecimal(70)).agregarIngrediente("Verdura", new BigDecimal(100)).buildReceta();
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
	
	public RecetaPrivadaCompuesta crearRecetaPrivadaCompuesta()
			throws RecetaException{
		
		 RecetaPrivadaSimple recetaPrivadaSimplePure = new RecetaPrivadaSimpleBuilder("Pure").totalCalorias(350).agregarIngrediente("Manteca", new BigDecimal(300)).agregarIngrediente("Papa", new BigDecimal(300)).buildReceta();
		 RecetaPrivadaSimple recetaPrivadaSimplePollo = new RecetaPrivadaSimpleBuilder("Pollo").totalCalorias(220).agregarIngrediente("pollo", new BigDecimal(280)).buildReceta();
		 RecetaPrivadaSimple recetaPrivadaSimpleCondimentos = new RecetaPrivadaSimpleBuilder("Condimentos").totalCalorias(120).agregarIngrediente("Oregano", new BigDecimal(20)).buildReceta();
		
		 return new RecetaPrivadaCompuestaBuilder("pollo con pure").agregarSubReceta(recetaPrivadaSimplePollo).agregarSubReceta(recetaPrivadaSimpleCondimentos).agregarSubReceta(recetaPrivadaSimplePure).buildReceta();
	}

	// Entrega 1 - Punto 3: Hacer que un usuario agregue una receta

	@Test
	public void testAgregarRecetaSimple() throws RecetaException {
		RecetaPrivadaSimple unaRecetaSimple = this.crearRecetaPrivadaSimple();

		this.usuario.agregarReceta(unaRecetaSimple);
	}

	@Test(expected = RecetaException.class)
	public void testAgregarRecetaSimpleSinIngredientes() throws RecetaException {
		RecetaPrivadaSimple receta = new RecetaPrivadaSimpleBuilder("papas fritas").totalCalorias(350).buildReceta();
		this.usuario.agregarReceta(receta);
	}

	@Test(expected = RecetaException.class)
	public void testAgregarRecetaMenorAlRangoDeCalorias() throws RecetaException {
		RecetaPrivadaSimple unaRecetaSimple = this.crearRecetaPrivadaSimple();
		unaRecetaSimple.setCalorias(2);
		this.usuario.agregarReceta(unaRecetaSimple);
	}

	@Test(expected = RecetaException.class)
	public void testAgregarRecetaMayorAlRangoDeCalorias() throws RecetaException {
		RecetaPrivadaSimple unaRecetaSimple = this.crearRecetaPrivadaSimple();
		unaRecetaSimple.setCalorias(99999);
		this.usuario.agregarReceta(unaRecetaSimple);
	}

	@Test
	public void testRecetaHipertensoNoAcepta() {
		Hipertenso hipertenso = new Hipertenso();
		this.usuario.agregarCondicionPreexistente(hipertenso);

		RecetaPrivadaSimple receta = new RecetaPrivadaSimpleBuilder("papas fritas").totalCalorias(350).agregarIngrediente("sal", new BigDecimal(50)).buildReceta();
		Set<CondicionPreexistente> noAcepta = new HashSet<CondicionPreexistente>();
		noAcepta.add(hipertenso);
		assertEquals(noAcepta, this.usuario.condicionesQueNoAcepta(this.usuario, receta));
		;
	}

	@Test
	public void testRecetaVeganoNoAcepta() {
		Vegano veggie = new Vegano();
		this.usuario.agregarCondicionPreexistente(veggie);

		RecetaPrivadaSimple receta = new RecetaPrivadaSimpleBuilder("papas fritas").totalCalorias(350).agregarIngrediente("chori", new BigDecimal(120)).buildReceta();
				
		assertFalse(this.usuario.validarSiAceptaReceta(receta));
	}

	@Test
	public void testRecetaDiabeticoNoAcepta() {
		Diabetico diabetico = new Diabetico();
		this.usuario.agregarCondicionPreexistente(diabetico);

		RecetaPrivadaSimple receta = new RecetaPrivadaSimpleBuilder("chocolate").totalCalorias(250).agregarIngrediente("azucar", new BigDecimal(120)).buildReceta();

		Set<CondicionPreexistente> noAcepta = new HashSet<CondicionPreexistente>();
		noAcepta.add(diabetico);
		assertEquals(noAcepta, this.usuario.condicionesQueNoAcepta(this.usuario, receta));

	}

	@Test
	public void testRecetaHipertensoAcepta() {
		Hipertenso hipertenso = new Hipertenso();
		this.usuario.agregarCondicionPreexistente(hipertenso);

		RecetaPrivadaSimple receta = new RecetaPrivadaSimpleBuilder("paella").totalCalorias(350).agregarIngrediente("arroz", new BigDecimal(200)).buildReceta();

		Set<CondicionPreexistente> noAcepta = new HashSet<CondicionPreexistente>();
		assertEquals(noAcepta, this.usuario.condicionesQueNoAcepta(this.usuario, receta));

	}

	@Test
	public void testRecetaVeganoAcepta() throws RecetaException {

		Vegano veggie = new Vegano();
		this.usuario.agregarCondicionPreexistente(veggie);

		RecetaPrivadaSimple receta = new RecetaPrivadaSimpleBuilder("paella").totalCalorias(350).agregarIngrediente("tomate", new BigDecimal(80)).buildReceta();
		this.usuario.validarSiAceptaReceta(receta);
	}

	@Test
	public void testRecetaDiabeticoAceptaAzucar() {
		Diabetico diabetico = new Diabetico();
		this.usuario.agregarCondicionPreexistente(diabetico);

		RecetaPrivadaSimple receta = new RecetaPrivadaSimpleBuilder("chocolate").totalCalorias(150).agregarIngrediente("azucar", new BigDecimal(50)).buildReceta();

		Set<CondicionPreexistente> noAcepta = new HashSet<CondicionPreexistente>();
		assertEquals(noAcepta, this.usuario.condicionesQueNoAcepta(this.usuario, receta));
	}

	@Test
	public void testVerRecetaPropia() throws RecetaException, UsuarioException {
		RecetaPrivadaSimple receta = new RecetaPrivadaSimpleBuilder("paella").totalCalorias(350).agregarIngrediente("pollo", new BigDecimal(100)).buildReceta();
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
		Usuario usuarioQueQuiereVer = TestFactory.crearUsuarioBasicoValido();
		Usuario userOwner = TestFactory.crearUsuarioBasicoValido();

		RecetaPrivadaSimple receta = new RecetaPrivadaSimpleBuilder("paella").totalCalorias(50).agregarIngrediente("pollo", new BigDecimal(100)).buildReceta();
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

		Usuario usuarioQueQuiereModificar = TestFactory.crearUsuarioBasicoValido();
		Usuario userOwner = TestFactory.crearUsuarioBasicoValido();

		RecetaPrivadaSimple receta = new RecetaPrivadaSimpleBuilder("paella").totalCalorias(150).agregarIngrediente("pollo", new BigDecimal(100)).buildReceta();
		userOwner.agregarReceta(receta);
		usuarioQueQuiereModificar.modificarNombreDeReceta(receta, "unNombreReCopado");
	}

	@Test
	public void testPuedeModificarRecetaPropia() throws RecetaException, UsuarioException {

		RecetaPrivadaSimple receta = new RecetaPrivadaSimpleBuilder("paella").totalCalorias(150).agregarIngrediente("pollo", new BigDecimal(100)).buildReceta();
		this.usuario.agregarReceta(receta);
		this.usuario.validarModificarReceta(receta);
	}

	// Entrega 1 - Punto 4: Modificar una receta dada, respetando la validación
	// del item
	// anterior
	@Test
	public void testModificarRecetaPropia() throws RecetaException,
			CloneNotSupportedException, UsuarioException {
		RecetaPrivadaSimple receta1 = new RecetaPrivadaSimpleBuilder("Nombre original").totalCalorias(350).agregarIngrediente("pollo", new BigDecimal(100)).buildReceta();
	

		this.usuario.agregarReceta(receta1);

		this.usuario.validarModificarReceta(receta1);
		this.usuario.modificarNombreDeReceta(receta1, "Nuevo nombre");
		assertEquals(receta1.getNombre(), "Nuevo nombre");
	}

	@Test
	public void testModificarRecetaPublica() throws RecetaException, CloneNotSupportedException, UsuarioException {
		RecetaPublicaSimple receta = new RecetaPublicaSimple(150);
		receta.agregarIngrediente("pollo", new BigDecimal(100));
		receta.setNombre("Nombre original");
		this.usuario.agregarReceta(receta);
		this.usuario.modificarNombreDeReceta(receta, "Nuevo nombre");

		// La receta original sigue con el mismo nombre
		assertEquals(receta.getNombre(), "Nombre original");
	}

	@Test(expected = UsuarioException.class)
	public void testModificarRecetaAjena() throws RecetaException, CloneNotSupportedException, UsuarioException {

		Usuario usuarioOwner = TestFactory.crearUsuarioBasicoValido();
		RecetaPrivadaSimple receta = new RecetaPrivadaSimpleBuilder("paella").totalCalorias(150).agregarIngrediente("papa", new BigDecimal(100)).buildReceta();
		usuarioOwner.agregarReceta(receta);

		this.usuario.modificarNombreDeReceta(receta, "Nuevo nombre");
	}

	@Test
	public void testClonarReceta() throws RecetaException, CloneNotSupportedException {
		RecetaPublicaSimple receta = new RecetaPublicaSimple(150);
		receta.agregarIngrediente("papa", new BigDecimal(100));

		RecetaPrivadaSimple recetaClonada = (RecetaPrivadaSimple) receta.clonarme();
		recetaClonada.agregarIngrediente("papa", new BigDecimal(150));

		assertEquals(receta.getIngredientes().get("papa"), new BigDecimal(100));
		assertEquals(recetaClonada.getIngredientes().get("papa"), new BigDecimal(150));
	}

	// Entrega 1 - Punto 5: Poder construir una receta con sub-recetas.

	@Test
	public void testAgregarRecetaCompuesta() throws RecetaException {
		RecetaPrivadaCompuesta unaRecetaCompuesta = this.crearRecetaPrivadaCompuesta();
		this.usuario.agregarReceta(unaRecetaCompuesta);

	}

	@Test(expected = RecetaException.class)
	public void testAgregarRecetaCompuestaSinSubrecetas() throws RecetaException {
		RecetaPrivadaCompuesta polloConPure = new RecetaPrivadaCompuesta();
		polloConPure.setNombre("PolloConPure");
		this.usuario.agregarReceta(polloConPure);
	}

	@Test(expected = RecetaException.class)
	public void testAgregarRecetaCompuestaConSubrecetaSinIngredientes()
			throws RecetaException {
		  RecetaPrivadaSimple pollo = new RecetaPrivadaSimpleBuilder("pollo").totalCalorias(120).agregarIngrediente("Alas y pechugas", new BigDecimal(20)).buildReceta();
		 RecetaPrivadaSimple pure = new RecetaPrivadaSimpleBuilder("pure").totalCalorias(350).buildReceta();
		RecetaPrivadaCompuesta polloConPure = new RecetaPrivadaCompuestaBuilder("PolloConPure").agregarSubReceta(pollo).agregarSubReceta(pure).buildReceta();


		this.usuario.agregarReceta(polloConPure);
	}

	@Test
	public void testAgregaRecetaConSubrecetaPropia() throws RecetaException {
		RecetaPrivadaSimple pure = new RecetaPrivadaSimpleBuilder("Pure").totalCalorias(350).agregarIngrediente("Manteca", new BigDecimal(300)).agregarIngrediente("Papa", new BigDecimal(300)).buildReceta();
		RecetaPrivadaSimple pollo= new RecetaPrivadaSimpleBuilder("Pollo").totalCalorias(120).agregarIngrediente("Alas y pechugas", new BigDecimal(20)).buildReceta();
		RecetaPrivadaCompuesta polloConPure = new RecetaPrivadaCompuestaBuilder("PolloConPure").agregarSubReceta(pure).agregarSubReceta(pollo).buildReceta();
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
