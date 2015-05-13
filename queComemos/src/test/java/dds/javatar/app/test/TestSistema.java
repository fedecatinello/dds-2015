package dds.javatar.app.test;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import dds.javatar.app.dto.grupodeusuarios.GrupoDeUsuarios;
import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.receta.RecetaPrivadaSimple;
import dds.javatar.app.dto.receta.RecetaPublicaSimple;
import dds.javatar.app.dto.sistema.Sistema;
import dds.javatar.app.dto.usuario.Hipertenso;
import dds.javatar.app.dto.usuario.Rutina;
import dds.javatar.app.dto.usuario.Rutina.TipoRutina;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.util.BusinessException;

public class TestSistema {

	private Usuario usuario;
	private Sistema sistema = Sistema.getInstance();

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
		this.sistema.sugerir(this.crearRecetaPublicaSimpleRica(), this.crearUsuarioBasicoValido());

	}

	@Test(expected = BusinessException.class)
	public void unaRecetaQueNoLeGustaNoPuedeSugerirseAUnUsuario() throws BusinessException {
		this.usuario = this.crearUsuarioBasicoValido();
		this.usuario.agregarAlimentoQueLeDisgusta("Harina");
		this.sistema.sugerir(this.crearRecetaPublicaSimpleRica(), this.usuario);

	}

	@Test(expected = BusinessException.class)
	public void unaRecetaQueNoSeaAptaParaElPerfilDelUsuarioNoSePuedeSugerir() throws BusinessException {
		Hipertenso hipertenso = new Hipertenso();

		this.usuario = this.crearUsuarioBasicoValido();
		this.usuario.agregarCondicionPreexistente(hipertenso);
		this.sistema.sugerir(this.crearRecetaNoAptaParaHipertensos(), this.usuario);

	}

	@Test
	public void recetaQueLeConocePorCompartirGrupo() throws BusinessException {
		Sistema sistema = this.sistema;
		GrupoDeUsuarios grupo = this.crearGrupoDeUsuarios();
		Usuario usuario = this.crearUsuarioBasicoValido();
		grupo.agregarUsuario(usuario);

		usuario.agregarReceta(this.crearRecetaPublicaSimpleRica());
		Usuario usuarioQueSeAgrega = this.crearUsuarioBasicoValido();
		usuarioQueSeAgrega.agregarReceta(this.crearRecetaPrivadaSimple());
		grupo.agregarUsuario(usuarioQueSeAgrega);

		assertEquals(usuario.getRecetas().size() + usuarioQueSeAgrega.getRecetas().size(), sistema.recetasQueConoceEl(usuario).size());
	}

	@Test
	public void recetaQueNoTieneQueConocerPOrqueNoCompartenGrupo() throws BusinessException {
		Sistema sistema = this.sistema;
		GrupoDeUsuarios grupo = this.crearGrupoDeUsuarios();
		Usuario usuario = this.crearUsuarioBasicoValido();
		grupo.agregarUsuario(usuario);

		usuario.agregarReceta(this.crearRecetaPublicaSimpleRica());
		Usuario usuarioQueSeAgrega = this.crearUsuarioBasicoValido();
		usuarioQueSeAgrega.agregarReceta(this.crearRecetaPrivadaSimple());

		assertEquals(usuario.getRecetas().size(), sistema.recetasQueConoceEl(usuario).size());
	}

	@Test
	public void recetasQueConoce() {
		Receta receta = this.crearRecetaPublicaSimpleRica();
		this.sistema.agregar(receta);
		assertEquals(this.sistema.listarTodas(), this.sistema.recetasQueConoceEl(this.crearUsuarioBasicoValido()));
	}

	@Test
	public void laRecetaContienePalabraClaveDePreferenciaDelGrupoYEsAptaParaTodosLosIntegrantes() throws BusinessException {
		GrupoDeUsuarios grupo = this.crearGrupoDeUsuarios();
		Usuario usuario = this.crearUsuarioBasicoValido();
		grupo.agregarUsuario(usuario);
		Usuario usuarioQueSeAgrega = this.crearUsuarioBasicoValido();
		usuarioQueSeAgrega.agregarReceta(this.crearRecetaPrivadaSimple());

	}

	@Test(expected = BusinessException.class)
	public void laRecetaNOContienePalabraClaveDePreferenciaDelGrupoYEsAptaParaTodosLosIntegrantes() throws BusinessException {
		GrupoDeUsuarios grupo = this.crearGrupoDeUsuarios();
		Usuario usuario = this.crearUsuarioBasicoValido();
		grupo.agregarUsuario(usuario);
		Usuario usuarioQueSeAgrega = this.crearUsuarioBasicoValido();
		usuarioQueSeAgrega.agregarReceta(this.crearRecetaPrivadaSimple());
		this.sistema.sugerir(this.crearRecetaPrivadaSimple(), grupo);
	}
}
