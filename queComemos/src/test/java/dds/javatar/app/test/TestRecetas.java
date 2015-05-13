package dds.javatar.app.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import dds.javatar.app.dto.receta.RecetaPrivadaCompuesta;
import dds.javatar.app.dto.receta.RecetaPrivadaSimple;
import dds.javatar.app.dto.receta.RecetaPublicaSimple;
import dds.javatar.app.dto.usuario.CondicionPreexistente;
import dds.javatar.app.dto.usuario.Diabetico;
import dds.javatar.app.dto.usuario.Hipertenso;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.dto.usuario.Vegano;
import dds.javatar.app.util.BusinessException;

public class TestRecetas extends TestGeneralAbstract {

	private Usuario usuario;

	@Before
	public void initialize() {
		this.usuario = this.crearUsuarioBasicoValido();
	}


	// Punto 3: Hacer que un usuario agregue una receta

	@Test
	public void testAgregarRecetaSimple() throws BusinessException {
		RecetaPrivadaSimple unaRecetaSimple=crearRecetaPrivadaSimple();

		this.usuario.agregarReceta(unaRecetaSimple);
	}
	@Test(expected = BusinessException.class)
	public void testAgregarRecetaSimpleSinIngredientes() throws BusinessException {
		RecetaPrivadaSimple receta = new RecetaPrivadaSimple(350);
		this.usuario.agregarReceta(receta);
	}

	@Test(expected = BusinessException.class)
	public void testAgregarRecetaMenorAlRangoDeCalorias() throws BusinessException {
		RecetaPrivadaSimple unaRecetaSimple=crearRecetaPrivadaSimple();
		unaRecetaSimple.setCalorias(2);
		this.usuario.agregarReceta(unaRecetaSimple);
	}

	@Test(expected = BusinessException.class)
	public void testAgregarRecetaMayorAlRangoDeCalorias() throws BusinessException {
		RecetaPrivadaSimple unaRecetaSimple=crearRecetaPrivadaSimple();
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
		assertEquals(noAcepta , this.usuario.condicionesQueNoAcepta(this.usuario, receta));
	}

	@Test
	public void testRecetaVeganoNoAcepta() {
		Vegano veggie = new Vegano();
		this.usuario.agregarCondicionPreexistente(veggie);

		RecetaPrivadaSimple receta = new RecetaPrivadaSimple(350);
		receta.agregarIngrediente("chori", new BigDecimal(120));
		//	this.usuario.validarSiAceptaReceta(receta);

		assertFalse(this.usuario.validarSiAceptaReceta(receta));
	}

	@Test
	public void testRecetaDiabeticoNoAcepta()  {
		Diabetico diabetico = new Diabetico();
		this.usuario.agregarCondicionPreexistente(diabetico);

		RecetaPrivadaSimple receta = new RecetaPrivadaSimple(150);
		receta.agregarIngrediente("azucar", new BigDecimal(120));

		Set<CondicionPreexistente> noAcepta = new HashSet<CondicionPreexistente>();
		noAcepta.add(diabetico);
		assertEquals(noAcepta , this.usuario.condicionesQueNoAcepta(this.usuario, receta));

	}

	@Test
	public void testRecetaHipertensoAcepta()  {
		Hipertenso hipertenso = new Hipertenso();
		this.usuario.agregarCondicionPreexistente(hipertenso);

		RecetaPrivadaSimple receta = new RecetaPrivadaSimple(350);
		receta.agregarIngrediente("arroz", new BigDecimal(200));

		Set<CondicionPreexistente> noAcepta = new HashSet<CondicionPreexistente>();
		assertEquals(noAcepta , this.usuario.condicionesQueNoAcepta(this.usuario, receta));

	}

	@Test
	public void testRecetaVeganoAcepta() throws BusinessException {

		Vegano veggie = new Vegano();
		this.usuario.agregarCondicionPreexistente(veggie);

		RecetaPrivadaSimple receta = new RecetaPrivadaSimple(350);
		receta.agregarIngrediente("tomate", new BigDecimal(80));
		this.usuario.validarSiAceptaReceta(receta);
	}

	@Test
	public void testRecetaDiabeticoAceptaAzucar()  {
		Diabetico diabetico = new Diabetico();
		this.usuario.agregarCondicionPreexistente(diabetico);

		RecetaPrivadaSimple receta = new RecetaPrivadaSimple(150);
		receta.agregarIngrediente("azucar", new BigDecimal(50));

		Set<CondicionPreexistente> noAcepta = new HashSet<CondicionPreexistente>();
		assertEquals(noAcepta , this.usuario.condicionesQueNoAcepta(this.usuario, receta));
	}

	@Test
	public void testVerRecetaPropia() throws BusinessException {
		RecetaPrivadaSimple receta = new RecetaPrivadaSimple(150);
		receta.agregarIngrediente("pollo", new BigDecimal(100));
		this.usuario.agregarReceta(receta);
		this.usuario.verReceta(receta);
	}


	// Punto 4: Saber si un usuario puede ver a una receta dada
	@Test
	public void testVerRecetaPublica() throws BusinessException {
		RecetaPublicaSimple receta = new RecetaPublicaSimple(150);
		receta.agregarIngrediente("pollo", new BigDecimal(100));
		usuario.agregarReceta(receta);
		this.usuario.verReceta(receta);
	}

	@Test(expected = BusinessException.class)
	public void testVerRecetaAjena() throws BusinessException {
		Usuario usuarioQueQuiereVer = this.crearUsuarioBasicoValido();
		Usuario userOwner = this.crearUsuarioBasicoValido();

		RecetaPrivadaSimple receta = new RecetaPrivadaSimple(150);
		receta.agregarIngrediente("pollo", new BigDecimal(100));
		userOwner.agregarReceta(receta);

		usuarioQueQuiereVer.verReceta(receta);
	}

	// Punto4: Saber si un usuario puede modificar una receta dada
	@Test
	public void testPuedeModificarRecetaPublica() throws BusinessException {
		RecetaPublicaSimple receta = new RecetaPublicaSimple(150);
		receta.agregarIngrediente("pollo", new BigDecimal(100));

		this.usuario.puedeModificarReceta(receta);
	}

	@Test
	public void testNoPuedeModificarReceta() throws BusinessException {

		Usuario usuarioQueQuiereModificar = this.crearUsuarioBasicoValido();
		Usuario userOwner = this.crearUsuarioBasicoValido();

		RecetaPrivadaSimple receta = new RecetaPrivadaSimple(150);
		receta.agregarIngrediente("pollo", new BigDecimal(100));
		userOwner.agregarReceta(receta);

		assertFalse(usuarioQueQuiereModificar.puedeModificarReceta(receta));
	}

	@Test
	public void testPuedeModificarRecetaPropia() throws BusinessException {

		RecetaPrivadaSimple receta = new RecetaPrivadaSimple(150);
		receta.agregarIngrediente("pollo", new BigDecimal(100));
		this.usuario.agregarReceta(receta);
		this.usuario.validarModificarReceta(receta);
	}

	// Punto 4: Modificar una receta dada, respetando la validación del item anterior
	@Test
	public void testModificarRecetaPropia() throws BusinessException, CloneNotSupportedException {
		RecetaPrivadaSimple receta1 = new RecetaPrivadaSimple(150);
		receta1.agregarIngrediente("pollo", new BigDecimal(100));
		receta1.setNombre("Nombre original");
		this.usuario.agregarReceta(receta1);

		this.usuario.validarModificarReceta(receta1);
		this.usuario.modificarNombreDeReceta(receta1, "Nuevo nombre");
		assertEquals(receta1.getNombre(), "Nuevo nombre");
	}

	@Test
	public void testModificarRecetaPublica() throws BusinessException, CloneNotSupportedException {
		RecetaPublicaSimple receta = new RecetaPublicaSimple(150);
		receta.agregarIngrediente("pollo", new BigDecimal(100));
		receta.setNombre("Nombre original");

		this.usuario.validarModificarReceta(receta);
		this.usuario.modificarNombreDeReceta(receta, "Nuevo nombre");

		// La receta original sigue con el mismo nombre
		assertNotSame(receta.getNombre(), "Nombre original");
	}

	@Test(expected = BusinessException.class)
	public void testModificarRecetaAjena() throws BusinessException, CloneNotSupportedException {

		Usuario usuarioOwner = this.crearUsuarioBasicoValido();
		RecetaPrivadaSimple receta = new RecetaPrivadaSimple(150);
		receta.agregarIngrediente("papa", new BigDecimal(100));
		usuarioOwner.agregarReceta(receta);

		this.usuario.modificarNombreDeReceta(receta, "Nuevo nombre");
	}


	@Test
	public void testClonarReceta() throws BusinessException, CloneNotSupportedException {
		RecetaPublicaSimple receta = new RecetaPublicaSimple(150);
		receta.agregarIngrediente("papa", new BigDecimal(100));

		RecetaPublicaSimple recetaClonada = (RecetaPublicaSimple) receta.clone();
		recetaClonada.agregarIngrediente("papa", new BigDecimal(150));

		assertEquals(receta.getIngredientes().get("papa"), new BigDecimal(100));
		assertEquals(recetaClonada.getIngredientes().get("papa"), new BigDecimal(150));
	}


	// Punto 5: Poder construir una receta con sub-recetas.
	
	

	@Test
	public void testAgregarRecetaCompuesta() throws BusinessException{
		RecetaPrivadaCompuesta unaRecetaCompuesta = crearRecetaPrivadaCompuesta();
		this.usuario.agregarReceta(unaRecetaCompuesta);

	}
	@Test(expected = BusinessException.class)
	public void testAgregarRecetaCompuestaSinSubrecetas() throws BusinessException {
		RecetaPrivadaCompuesta polloConPure  = new RecetaPrivadaCompuesta();
		polloConPure.setNombre("PolloConPure");
		this.usuario.agregarReceta(polloConPure);
	}
	@Test(expected = BusinessException.class)
	public void testAgregarRecetaCompuestaConSubrecetaSinIngredientes() throws BusinessException {
		RecetaPrivadaSimple pollo = new RecetaPrivadaSimple(120);
		RecetaPrivadaSimple pure  = new RecetaPrivadaSimple(350);
		RecetaPrivadaCompuesta polloConPure  = new RecetaPrivadaCompuesta();

		pollo.setNombre("pollo");
		pollo.agregarIngrediente("Alas y pechugas",new BigDecimal(20));

		pure.setNombre("Pure");

		polloConPure.setNombre("PolloConPure");
		polloConPure.agregarSubReceta(pollo);
		polloConPure.agregarSubReceta(pure);
		this.usuario.agregarReceta(polloConPure);
	}

	@Test
	public void testAgregaRecetaConSubrecetaPropia() throws BusinessException {
		RecetaPrivadaSimple pure  = new RecetaPrivadaSimple(350);
		RecetaPrivadaCompuesta polloConPure  = new RecetaPrivadaCompuesta();
		RecetaPrivadaSimple pollo = new RecetaPrivadaSimple(120);

		pollo.setNombre("pollo");
		pollo.agregarIngrediente("Alas y pechugas",new BigDecimal(20));

		pure.setNombre("Pure");
		pure.agregarIngrediente("Manteca", new BigDecimal(300));
		pure.agregarIngrediente("Papa", new BigDecimal(300));

		polloConPure.setNombre("PolloConPure");
		polloConPure.agregarSubReceta(pure);
		polloConPure.agregarSubReceta(pollo);
		this.usuario.agregarReceta(polloConPure);

	}
	/*
	@Test(expected = BusinessException.class)
	public void testAgregaRecetaConSubrecetaAjena() throws BusinessException {
		RecetaPrivadaCompuesta unaRecetaCompuesta = crearRecetaPrivadaCompuesta();

		RecetaPrivadaSimple pan  = new RecetaPrivadaSimple(150);
		pan.setNombre("pan");
		pan.agregarIngrediente("harina", new BigDecimal(80));
		pan.agregarIngrediente("agua", new BigDecimal(120));

		unaRecetaCompuesta.agregarSubReceta(pan);
		this.usuario.agregarReceta(unaRecetaCompuesta);
	}
	
	
	//Aca hay q ver lo de la clonacion. Tengo una receta privada y quiero agregar una publica
	@Test
	public void testAgregaRecetaConSubrecetaPublica() throws BusinessException {
		RecetaPrivadaCompuesta unaRecetaCompuesta = crearRecetaPrivadaCompuesta();

		RecetaPublicaSimple pan  = new RecetaPublicaSimple(150);
		pan.setNombre("pan");
		pan.agregarIngrediente("harina", new BigDecimal(80));
		pan.agregarIngrediente("agua", new BigDecimal(120));

		unaRecetaCompuesta.agregarSubReceta(pan);
		this.usuario.agregarReceta(unaRecetaCompuesta);
	}
	*/
	
}
