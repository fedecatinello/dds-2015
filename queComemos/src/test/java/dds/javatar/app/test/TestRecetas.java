package dds.javatar.app.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import dds.javatar.app.dto.receta.RecetaCompuesta;
import dds.javatar.app.dto.receta.RecetaPrivada;
import dds.javatar.app.dto.receta.RecetaPublica;
import dds.javatar.app.dto.receta.RecetaSimple;
import dds.javatar.app.dto.receta.TipoReceta;
import dds.javatar.app.dto.usuario.CondicionPreexistente;
import dds.javatar.app.dto.usuario.Diabetico;
import dds.javatar.app.dto.usuario.Hipertenso;
import dds.javatar.app.dto.usuario.Rutina;
import dds.javatar.app.dto.usuario.Rutina.TipoRutina;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.dto.usuario.Vegano;
import dds.javatar.app.util.BusinessException;

public class TestRecetas {

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
	private RecetaSimple crearRecetaSimple(){
		TipoReceta recetaPrivada = new RecetaPrivada(usuario);
		RecetaSimple ravioles = new RecetaSimple(350, recetaPrivada);
		ravioles.setNombre("Ravioles");
		ravioles.agregarIngrediente("Harina", new BigDecimal(300));
		ravioles.agregarIngrediente("Agua", new BigDecimal(70));
		ravioles.agregarIngrediente("Verdura", new BigDecimal(100));
		return ravioles;
	}
	private RecetaCompuesta crearRecetaCompuesta(){
		TipoReceta recetaPrivada = new RecetaPrivada(usuario);
		TipoReceta recetaPrivada2 = new RecetaPrivada(usuario);
		TipoReceta recetaPublica = new RecetaPublica();

		RecetaSimple condimentos = new RecetaSimple(120, recetaPublica);
		RecetaSimple pure  = new RecetaSimple(350, recetaPrivada);
		RecetaCompuesta polloConPure  = new RecetaCompuesta(350, recetaPrivada2);

		condimentos.setNombre("Condimentos");
		condimentos.agregarIngrediente("Oregano",new BigDecimal(20));

		pure.setNombre("Pure");
		pure.agregarIngrediente("Manteca", new BigDecimal(300));
		pure.agregarIngrediente("Papa", new BigDecimal(300));

		polloConPure.agregarIngrediente("pollo", new BigDecimal(300));
		polloConPure.setNombre("PolloConPure");
		polloConPure.agregarSubReceta(pure);
		polloConPure.agregarSubReceta(condimentos);

		return polloConPure;
	}

	
	// Punto 3: Hacer que un usuario agregue una receta
	
	@Test
	public void testAgregarRecetaSimple() throws BusinessException {
		RecetaSimple unaRecetaSimple=crearRecetaSimple();

		this.usuario.agregarReceta(unaRecetaSimple);
	}
	@Test(expected = BusinessException.class)
	public void testAgregarRecetaSimpleSinIngredientes() throws BusinessException {
		TipoReceta rPublica = new RecetaPublica();
		RecetaSimple receta = new RecetaSimple(350, rPublica);
		this.usuario.agregarReceta(receta);
	}

	@Test(expected = BusinessException.class)
	public void testAgregarRecetaMenorAlRangoDeCalorias() throws BusinessException {
		TipoReceta rPublica = new RecetaPublica();
		RecetaSimple receta = new RecetaSimple(350, rPublica);
		receta.setCalorias(2);
		this.usuario.agregarReceta(receta);
	}

	@Test(expected = BusinessException.class)
	public void testAgregarRecetaMayorAlRangoDeCalorias() throws BusinessException {
		TipoReceta rPublica = new RecetaPublica();
		RecetaSimple receta = new RecetaSimple(350, rPublica);
		receta.setCalorias(99999);
		this.usuario.agregarReceta(receta);
	}

	@Test
	public void testRecetaHipertensoNoAcepta() {
		Hipertenso hipertenso = new Hipertenso();
		this.usuario.agregarCondicionPreexistente(hipertenso);

		RecetaSimple receta = new RecetaSimple(350);
		receta.agregarIngrediente("sal", new BigDecimal(50));

		Set<CondicionPreexistente> noAcepta = new HashSet<CondicionPreexistente>();
		noAcepta.add(hipertenso);
		assertEquals(noAcepta , this.usuario.condicionesQueNoAcepta(this.usuario, receta));;

	}

	@Test
	public void testRecetaVeganoNoAcepta() {
		Vegano veggie = new Vegano();
		this.usuario.agregarCondicionPreexistente(veggie);

		RecetaSimple receta = new RecetaSimple(350);
		receta.agregarIngrediente("chori", new BigDecimal(120));
		//	this.usuario.validarSiAceptaReceta(receta);

		assertFalse(this.usuario.validarSiAceptaReceta(receta));
	}

	@Test
	public void testRecetaDiabeticoNoAcepta()  {
		Diabetico diabetico = new Diabetico();
		this.usuario.agregarCondicionPreexistente(diabetico);

		RecetaSimple receta = new RecetaSimple(150);
		receta.agregarIngrediente("azucar", new BigDecimal(120));

		Set<CondicionPreexistente> noAcepta = new HashSet<CondicionPreexistente>();
		noAcepta.add(diabetico);
		assertEquals(noAcepta , this.usuario.condicionesQueNoAcepta(this.usuario, receta));

	}

	@Test
	public void testRecetaHipertensoAcepta()  {
		Hipertenso hipertenso = new Hipertenso();
		this.usuario.agregarCondicionPreexistente(hipertenso);

		RecetaSimple receta = new RecetaSimple(350);
		receta.agregarIngrediente("arroz", new BigDecimal(200));

		Set<CondicionPreexistente> noAcepta = new HashSet<CondicionPreexistente>();
		assertEquals(noAcepta , this.usuario.condicionesQueNoAcepta(this.usuario, receta));

	}

	@Test
	public void testRecetaVeganoAcepta() throws BusinessException {

		Vegano veggie = new Vegano();
		this.usuario.agregarCondicionPreexistente(veggie);

		RecetaSimple receta = new RecetaSimple(350);
		receta.agregarIngrediente("tomate", new BigDecimal(80));
		this.usuario.validarSiAceptaReceta(receta);
	}

	@Test
	public void testRecetaDiabeticoAceptaAzucar()  {
		Diabetico diabetico = new Diabetico();
		this.usuario.agregarCondicionPreexistente(diabetico);

		RecetaSimple receta = new RecetaSimple(150);
		receta.agregarIngrediente("azucar", new BigDecimal(50));

		Set<CondicionPreexistente> noAcepta = new HashSet<CondicionPreexistente>();
		assertEquals(noAcepta , this.usuario.condicionesQueNoAcepta(this.usuario, receta));
	}

	@Test
	public void testVerRecetaPropia() throws BusinessException {
		TipoReceta recetaPublica = new RecetaPublica();
		RecetaSimple receta = new RecetaSimple(150, recetaPublica);
		receta.agregarIngrediente("pollo", new BigDecimal(100));
		this.usuario.agregarReceta(receta);
		this.usuario.verReceta(receta);
	}


	// Punto 4: Saber si un usuario puede ver a una receta dada
	@Test
	public void testVerRecetaPublica() throws BusinessException {

		TipoReceta publica = new RecetaPublica();
		RecetaSimple receta = new RecetaSimple(150, publica);
		receta.agregarIngrediente("pollo", new BigDecimal(100));

		this.usuario.verReceta(receta);
	}

	@Test(expected = BusinessException.class)
	public void testVerRecetaAjena() throws BusinessException {
		Usuario usuarioQueQuiereVer = this.crearUsuarioBasicoValido();
		Usuario usuarioQueAutoreo = this.crearUsuarioBasicoValido();

		TipoReceta privada = new RecetaPrivada(usuarioQueAutoreo);
		RecetaSimple receta = new RecetaSimple(150, privada);
		receta.agregarIngrediente("pollo", new BigDecimal(100));
		usuarioQueAutoreo.agregarReceta(receta);

		usuarioQueQuiereVer.verReceta(receta);
	}

	// Punto4: Saber si un usuario puede modificar una receta dada
	@Test
	public void testPuedeModificarRecetaPublica() throws BusinessException {

		TipoReceta publica = new RecetaPublica();
		RecetaSimple receta = new RecetaSimple(150, publica);
		receta.agregarIngrediente("pollo", new BigDecimal(100));

		this.usuario.puedeModificarReceta(receta);
	}

	@Test
	public void testNoPuedeModificarReceta() throws BusinessException {

		Usuario usuarioQueQuiereModificar = this.crearUsuarioBasicoValido();
		Usuario usuarioQueAutoreo = this.crearUsuarioBasicoValido();

		TipoReceta privada = new RecetaPrivada(usuarioQueAutoreo);
		RecetaSimple receta = new RecetaSimple(150, privada);
		receta.agregarIngrediente("pollo", new BigDecimal(100));
		usuarioQueAutoreo.agregarReceta(receta);

		assertFalse(usuarioQueQuiereModificar.puedeModificarReceta(receta));
	}

	@Test
	public void testPuedeModificarRecetaPropia() throws BusinessException {

		TipoReceta privada = new RecetaPrivada(this.usuario);
		RecetaSimple receta = new RecetaSimple(150, privada);
		receta.agregarIngrediente("pollo", new BigDecimal(100));
		this.usuario.agregarReceta(receta);
		this.usuario.validarModificarReceta(receta);
	}

	// Punto 4: Modificar una receta dada, respetando la validación del item anterior
	@Test
	public void testModificarRecetaPropia() throws BusinessException, CloneNotSupportedException {

		TipoReceta privada = new RecetaPrivada(this.usuario);
		RecetaSimple receta1 = new RecetaSimple(150, privada);
		receta1.agregarIngrediente("pollo", new BigDecimal(100));
		receta1.setNombre("Nombre original");
		this.usuario.agregarReceta(receta1);

		this.usuario.validarModificarReceta(receta1);
		this.usuario.modificarNombreDeReceta(receta1, "Nuevo nombre");
		assertEquals(receta1.getNombre(), "Nuevo nombre");
	}

	@Test
	public void testModificarRecetaPublica() throws BusinessException, CloneNotSupportedException {

		TipoReceta publica = new RecetaPublica();
		RecetaSimple receta = new RecetaSimple(150, publica);
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
		TipoReceta privada = new RecetaPrivada(usuarioOwner);
		RecetaSimple receta = new RecetaSimple(150, privada);
		receta.agregarIngrediente("papa", new BigDecimal(100));
		usuarioOwner.agregarReceta(receta);

		this.usuario.modificarNombreDeReceta(receta, "Nuevo nombre");
	}


	@Test
	public void testClonarReceta() throws BusinessException, CloneNotSupportedException {
		RecetaSimple receta = new RecetaSimple(150);
		receta.agregarIngrediente("papa", new BigDecimal(100));

		RecetaSimple recetaClonada = (RecetaSimple) receta.clone();
		recetaClonada.agregarIngrediente("papa", new BigDecimal(150));

		assertEquals(receta.getIngredientes().get("papa"), new BigDecimal(100));
		assertEquals(recetaClonada.getIngredientes().get("papa"), new BigDecimal(150));
	}

	
	// Punto 5: Poder construir una receta con sub-recetas.
	//

	@Test
	public void testAgregarRecetaCompuesta() throws BusinessException{
		RecetaCompuesta unaRecetaCompuesta = crearRecetaCompuesta();
		this.usuario.agregarReceta(unaRecetaCompuesta);

	}
	@Test(expected = BusinessException.class)
	public void testAgregarRecetaCompuestaSinIngredientes() throws BusinessException {
		TipoReceta recetaPrivada = new RecetaPrivada(usuario);
		TipoReceta recetaPrivada2 = new RecetaPrivada(usuario);
		RecetaSimple pure  = new RecetaSimple(350, recetaPrivada);
		RecetaCompuesta polloConPure  = new RecetaCompuesta(350, recetaPrivada2);
		pure.setNombre("Pure");
		pure.agregarIngrediente("Manteca", new BigDecimal(300));
		pure.agregarIngrediente("Papa", new BigDecimal(300));

		polloConPure.setNombre("PolloConPure");
		polloConPure.agregarSubReceta(pure);
		this.usuario.agregarReceta(polloConPure);
	}
	@Test(expected = BusinessException.class)
	public void testAgregarRecetaCompuestaConSubrecetaSinIngredientes() throws BusinessException {
		TipoReceta recetaPrivada = new RecetaPrivada(usuario);
		TipoReceta recetaPrivada2 = new RecetaPrivada(usuario);
		TipoReceta recetaPublica = new RecetaPublica();

		RecetaSimple condimentos = new RecetaSimple(120, recetaPublica);
		RecetaSimple pure  = new RecetaSimple(350, recetaPrivada);
		RecetaCompuesta polloConPure  = new RecetaCompuesta(350, recetaPrivada2);

		condimentos.setNombre("Condimentos");
		condimentos.agregarIngrediente("Oregano",new BigDecimal(20));

		pure.setNombre("Pure");

		polloConPure.agregarIngrediente("pollo", new BigDecimal(300));
		polloConPure.setNombre("PolloConPure");
		polloConPure.agregarSubReceta(condimentos);
		polloConPure.agregarSubReceta(pure);
		this.usuario.agregarReceta(polloConPure);
	}

	@Test
	public void testAgregaRecetaConSubrecetaPropia() throws BusinessException {
		TipoReceta recetaPrivada = new RecetaPrivada(usuario);
		TipoReceta recetaPrivada2 = new RecetaPrivada(usuario);

		RecetaSimple pure  = new RecetaSimple(350, recetaPrivada);
		RecetaCompuesta polloConPure  = new RecetaCompuesta(350, recetaPrivada2);

		pure.setNombre("Pure");
		pure.agregarIngrediente("Manteca", new BigDecimal(300));
		pure.agregarIngrediente("Papa", new BigDecimal(300));

		polloConPure.agregarIngrediente("pollo", new BigDecimal(300));
		polloConPure.setNombre("PolloConPure");
		polloConPure.agregarSubReceta(pure);
		this.usuario.agregarReceta(polloConPure);
		
	}
	@Test(expected = BusinessException.class)
	public void testAgregaRecetaConSubrecetaAjena() throws BusinessException {
		RecetaCompuesta unaRecetaCompuesta = crearRecetaCompuesta();
		Usuario panadero = this.crearUsuarioBasicoValido();
		TipoReceta recetaPrivadaPanadero = new RecetaPrivada(panadero);
		
		RecetaSimple pan  = new RecetaSimple(150, recetaPrivadaPanadero);
		pan.setNombre("pan");
		pan.agregarIngrediente("harina", new BigDecimal(80));
		pan.agregarIngrediente("agua", new BigDecimal(120));
		
		unaRecetaCompuesta.agregarSubReceta(pan);
		this.usuario.agregarReceta(unaRecetaCompuesta);
	}
	@Test
	public void testAgregaRecetaConSubrecetaPublica() throws BusinessException {
		RecetaCompuesta unaRecetaCompuesta = crearRecetaCompuesta();
		TipoReceta recetaPublicaPan = new RecetaPublica();
		
		RecetaSimple pan  = new RecetaSimple(150, recetaPublicaPan);
		pan.setNombre("pan");
		pan.agregarIngrediente("harina", new BigDecimal(80));
		pan.agregarIngrediente("agua", new BigDecimal(120));
		
		unaRecetaCompuesta.agregarSubReceta(pan);
		this.usuario.agregarReceta(unaRecetaCompuesta);
	}
	
}
