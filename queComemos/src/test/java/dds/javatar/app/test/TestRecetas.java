package dds.javatar.app.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.receta.RecetaPrivada;
import dds.javatar.app.dto.receta.RecetaPublica;
import dds.javatar.app.dto.receta.TipoReceta;
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

	// Punto 3: Hacer que un usuario agregue una receta
	@Test
	public void testAgregarReceta() throws BusinessException {
		TipoReceta recetaPrivada = new RecetaPrivada(usuario);
		Receta ravioles = new Receta(350, recetaPrivada);
		ravioles.agregarIngrediente("Harina", new BigDecimal(300));
		ravioles.agregarIngrediente("Agua", new BigDecimal(70));
		ravioles.agregarIngrediente("Verdura", new BigDecimal(100));

		this.usuario.agregarReceta(ravioles);
	}

	@Test(expected = BusinessException.class)
	public void testAgregarRecetaSinIngredientes() throws BusinessException {
		TipoReceta rPublica = new RecetaPublica();
		Receta receta = new Receta(350, rPublica);
		this.usuario.agregarReceta(receta);

	}

	@Test(expected = BusinessException.class)
	public void testAgregarRecetaMenorAlRangoDeCalorias() throws BusinessException {
		TipoReceta rPublica = new RecetaPublica();
		Receta receta = new Receta(350, rPublica);
		receta.setCalorias(2);
		this.usuario.agregarReceta(receta);
	}

	@Test(expected = BusinessException.class)
	public void testAgregarRecetaMayorAlRangoDeCalorias() throws BusinessException {
		TipoReceta rPublica = new RecetaPublica();
		Receta receta = new Receta(350, rPublica);
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
		TipoReceta recetaPublica = new RecetaPublica();
		Receta receta = new Receta(150, recetaPublica);
		receta.agregarIngrediente("pollo", new BigDecimal(100));
		this.usuario.agregarReceta(receta);
		this.usuario.verReceta(receta);
	}


	// Punto 4: Saber si un usuario puede ver a una receta dada
	@Test
	public void testVerRecetaPublica() throws BusinessException {
		
		TipoReceta publica = new RecetaPublica();
		Receta receta = new Receta(150, publica);
		receta.agregarIngrediente("pollo", new BigDecimal(100));

		this.usuario.verReceta(receta);
	}

	@Test(expected = BusinessException.class)
	public void testVerRecetaAjena() throws BusinessException {
		Usuario usuarioQueQuiereVer = this.crearUsuarioBasicoValido();
		Usuario usuarioQueAutoreo = this.crearUsuarioBasicoValido();
		
		TipoReceta privada = new RecetaPrivada(usuarioQueAutoreo);
		Receta receta = new Receta(150, privada);
		receta.agregarIngrediente("pollo", new BigDecimal(100));
		usuarioQueAutoreo.agregarReceta(receta);

		usuarioQueQuiereVer.verReceta(receta);
	}

	// Punto4: Saber si un usuario puede modificar una receta dada
	@Test
	public void testPuedeModificarRecetaPublica() throws BusinessException {

		TipoReceta publica = new RecetaPublica();
		Receta receta = new Receta(150, publica);
		receta.agregarIngrediente("pollo", new BigDecimal(100));

		this.usuario.puedeModificarReceta(receta);
	}

	@Test
	public void testNoPuedeModificarReceta() throws BusinessException {

		Usuario usuarioQueQuiereModificar = this.crearUsuarioBasicoValido();
		Usuario usuarioQueAutoreo = this.crearUsuarioBasicoValido();
		
		TipoReceta privada = new RecetaPrivada(usuarioQueAutoreo);
		Receta receta = new Receta(150, privada);
		receta.agregarIngrediente("pollo", new BigDecimal(100));
		usuarioQueAutoreo.agregarReceta(receta);

		assertFalse(usuarioQueQuiereModificar.puedeModificarReceta(receta));
	}

	@Test
	public void testPuedeModificarRecetaPropia() throws BusinessException {
	
		TipoReceta privada = new RecetaPrivada(this.usuario);
		Receta receta = new Receta(150, privada);
		receta.agregarIngrediente("pollo", new BigDecimal(100));
		this.usuario.agregarReceta(receta);
		this.usuario.validarModificarReceta(receta);
	}

	// Punto 4: Modificar una receta dada, respetando la validación del item anterior
	@Test
	public void testModificarRecetaPropia() throws BusinessException, CloneNotSupportedException {
		
		TipoReceta privada = new RecetaPrivada(this.usuario);
		Receta receta1 = new Receta(150, privada);
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
		Receta receta = new Receta(150, publica);
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
		Receta receta = new Receta(150, privada);
		receta.agregarIngrediente("papa", new BigDecimal(100));
		usuarioOwner.agregarReceta(receta);

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
}
//
//	// Punto 5: Poder construir una receta con sub-recetas.
//	@Test
//	public void testAgregaRecetaConSubrecetaPropia() throws BusinessException {
//		Receta recetaPure = new Receta(150);
//		recetaPure.agregarIngrediente("papa", new BigDecimal(100));
//		this.usuario.agregarReceta(recetaPure);
//		Receta recetaPollo = new Receta(350);
//		recetaPollo.agregarIngrediente("pollo", new BigDecimal(100));
//
//		recetaPollo.agregarSubReceta(recetaPure);
//		this.usuario.puedeAgregarSubRecetas(recetaPollo.getSubRecetas());
//	}
//
//	@Test(expected = BusinessException.class)
//	public void testAgregaRecetaConSubrecetaAjena() throws BusinessException {
//		Usuario usuarioOwner = this.crearUsuarioBasicoValido();
//
//		Receta recetaPure = new Receta(150);
//		recetaPure.agregarIngrediente("papa", new BigDecimal(100));
//		usuarioOwner.agregarReceta(recetaPure);
//		Receta recetaPollo = new Receta(350);
//		recetaPollo.agregarIngrediente("pollo", new BigDecimal(100));
//
//		recetaPollo.agregarSubReceta(recetaPure);
//		this.usuario.puedeAgregarSubRecetas(recetaPollo.getSubRecetas());
//	}
//
//	@Test
//	public void testAgregaRecetaConSubrecetaPublica() throws BusinessException {
//		Receta recetaPure = new Receta(150);
//		recetaPure.agregarIngrediente("papa", new BigDecimal(100));
//		Receta recetaPollo = new Receta(350);
//		recetaPollo.agregarIngrediente("pollo", new BigDecimal(100));
//
//		recetaPollo.agregarSubReceta(recetaPure);
//		this.usuario.puedeAgregarSubRecetas(recetaPollo.getSubRecetas());
//
//	}
//
//	@Test
//	public void testAgregaRecetaConSubrecetaVacia() throws BusinessException {
//		// TODO: ?
//	}
//
//}
