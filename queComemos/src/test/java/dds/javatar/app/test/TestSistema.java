package dds.javatar.app.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import dds.javatar.app.dto.grupodeusuarios.GrupoDeUsuarios;
import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.receta.RecetaPrivadaSimple;
import dds.javatar.app.dto.receta.RecetaPublicaSimple;
import dds.javatar.app.dto.receta.busqueda.Buscador;
import dds.javatar.app.dto.sistema.Administrador;
import dds.javatar.app.dto.sistema.RepositorioRecetas;
import dds.javatar.app.dto.usuario.condiciones.Hipertenso;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.util.exception.BusinessException;
import dds.javatar.app.util.exception.RecetaException;

public class TestSistema {

	private Usuario usuario;
	private RepositorioRecetas sistema = RepositorioRecetas.getInstance();
	private Administrador administrador = Administrador.getInstance();
	private Buscador buscador;

	@Before
	public void initialize() {
		this.usuario = TestFactory.crearUsuarioBasicoValido();
		buscador = new Buscador();
	}

	private RecetaPrivadaSimple crearRecetaPrivadaSimple() {
		RecetaPrivadaSimple ravioles = new RecetaPrivadaSimple(350);
		ravioles.setNombre("Ravioles");
		ravioles.agregarIngrediente("Harina", new BigDecimal(300));
		ravioles.agregarIngrediente("Agua", new BigDecimal(70));
		ravioles.agregarIngrediente("Verdura", new BigDecimal(100));
		return ravioles;
	}

	private Receta crearRecetaPublicaSimpleRica() {
		RecetaPublicaSimple nioquis = new RecetaPublicaSimple(350);
		nioquis.setNombre("Nioquis");
		nioquis.agregarIngrediente("Harina", new BigDecimal(300));
		nioquis.agregarIngrediente("Agua", new BigDecimal(70));
		nioquis.agregarIngrediente("papa", new BigDecimal(100));
		return nioquis;
	}

	private Receta crearRecetaNoAptaParaHipertensos() {
		RecetaPublicaSimple pizza = new RecetaPublicaSimple(350);
		pizza.setNombre("Pizza");
		pizza.agregarIngrediente("sal", new BigDecimal(300));
		pizza.agregarIngrediente("Agua", new BigDecimal(70));
		pizza.agregarIngrediente("Harina", new BigDecimal(100));
		return pizza;
	}

	private GrupoDeUsuarios crearGrupoDeUsuarios() throws BusinessException {
		GrupoDeUsuarios grupo = new GrupoDeUsuarios();
		grupo.setNombre("Amigos del club");
		HashMap<String, Boolean> preferenciasAlimenticias = new HashMap<String, Boolean>();
		preferenciasAlimenticias.put("Ravioles", true);
		preferenciasAlimenticias.put("papa", true);

		grupo.setPreferenciasAlimenticias(preferenciasAlimenticias);

		return grupo;

	}

	@Test
	public void unaRecetaQueLeGustaPuedeSugerirseAUnUsuario() throws BusinessException {
		this.usuario.agregarAlimentoQueLeDisgusta("pollo");
		this.administrador.sugerir(crearRecetaPublicaSimpleRica(), TestFactory.crearUsuarioBasicoValido());

	}

	@Test(expected = BusinessException.class)
	public void unaRecetaQueNoLeGustaNoPuedeSugerirseAUnUsuario() throws BusinessException {
		sistema.eliminarTodasLasRecetas();
		this.usuario = TestFactory.crearUsuarioBasicoValido();
		this.usuario.agregarAlimentoQueLeDisgusta("Harina");
		administrador.sugerir(crearRecetaPublicaSimpleRica(), this.usuario);

	}

	@Test(expected = BusinessException.class)
	public void unaRecetaQueNoSeaAptaParaElPerfilDelUsuarioNoSePuedeSugerir() throws BusinessException {
		sistema.eliminarTodasLasRecetas();
		Hipertenso hipertenso = new Hipertenso();
		this.usuario = TestFactory.crearUsuarioBasicoValido();
		this.usuario.agregarCondicionPreexistente(hipertenso);
		this.administrador.sugerir(crearRecetaNoAptaParaHipertensos(), this.usuario);

	}

	@Test
	public void recetasQueConocePorCompartirGrupo() throws RecetaException, BusinessException {
		sistema.eliminarTodasLasRecetas();
		GrupoDeUsuarios grupo = this.crearGrupoDeUsuarios();
		Usuario usuario = TestFactory.crearUsuarioBasicoValido();
		grupo.agregarUsuario(usuario);
		usuario.agregarReceta(crearRecetaPublicaSimpleRica());
		Usuario usuarioQueSeAgrega = TestFactory.crearUsuarioBasicoValido();
		usuarioQueSeAgrega.agregarReceta(crearRecetaPrivadaSimple());
		grupo.agregarUsuario(usuarioQueSeAgrega);

		assertEquals(2, usuario.getRecetas().size() + usuarioQueSeAgrega.getRecetas().size());
	}

	@Test
	public void recetaQueNoTieneQueConocerPOrqueNoCompartenGrupo() throws BusinessException, RecetaException {
		sistema.eliminarTodasLasRecetas();
		GrupoDeUsuarios grupo = this.crearGrupoDeUsuarios();
		Usuario usuario = TestFactory.crearUsuarioBasicoValido();
		grupo.agregarUsuario(usuario);

		usuario.agregarReceta(crearRecetaPublicaSimpleRica());
		Usuario usuarioQueSeAgrega = TestFactory.crearUsuarioBasicoValido();
		usuarioQueSeAgrega.agregarReceta(crearRecetaPrivadaSimple());

		assertEquals(1, buscador.recetasQueConoceEl(usuario).size());
	}

	@Test
	public void recetasQueConoce() {
		crearRecetaPublicaSimpleRica();
		crearRecetaPublicaSimpleRica();
		assertEquals(sistema.listarTodas(), buscador.recetasQueConoceEl(TestFactory.crearUsuarioBasicoValido()));
	}

	@Test
	public void laRecetaContienePalabraClaveDePreferenciaDelGrupoYEsAptaParaTodosLosIntegrantes() throws BusinessException, RecetaException {
		sistema.eliminarTodasLasRecetas();
		GrupoDeUsuarios grupo = this.crearGrupoDeUsuarios();
		Usuario usuario = TestFactory.crearUsuarioBasicoValido();
		grupo.agregarUsuario(usuario);
		Usuario usuarioQueSeAgrega = TestFactory.crearUsuarioBasicoValido();
		usuarioQueSeAgrega.agregarReceta(crearRecetaPrivadaSimple());

	}

	@Test(expected = BusinessException.class)
	public void laRecetaNOContienePalabraClaveDePreferenciaDelGrupoYEsAptaParaTodosLosIntegrantes() throws BusinessException, RecetaException {
		sistema.eliminarTodasLasRecetas();
		GrupoDeUsuarios grupo = this.crearGrupoDeUsuarios();
		Usuario usuario = TestFactory.crearUsuarioBasicoValido();
		grupo.agregarUsuario(usuario);
		Usuario usuarioQueSeAgrega = TestFactory.crearUsuarioBasicoValido();
		usuarioQueSeAgrega.agregarReceta(crearRecetaPrivadaSimple());
		administrador.sugerir(crearRecetaPrivadaSimple(), grupo);
	}
}
