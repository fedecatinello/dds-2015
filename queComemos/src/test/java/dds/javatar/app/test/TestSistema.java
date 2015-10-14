package dds.javatar.app.test;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import dds.javatar.app.dto.grupodeusuarios.GrupoDeUsuarios;
import dds.javatar.app.dto.receta.Componente;
import dds.javatar.app.dto.receta.PreferenciaGrupo;
import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.receta.builder.RecetaBuilder;
import dds.javatar.app.dto.receta.busqueda.Buscador;
import dds.javatar.app.dto.sistema.Administrador;
import dds.javatar.app.dto.sistema.RepositorioRecetas;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.dto.usuario.condiciones.Hipertenso;
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

	public Receta crearRecetaPrivadaSimple(Usuario autor) {
		return new RecetaBuilder("Ravioles")
			.totalCalorias(350)
			.agregarIngrediente("Harina", new BigDecimal(300))
			.agregarIngrediente("Agua", new BigDecimal(70))
			.agregarIngrediente("Verdura", new BigDecimal(100))
			.inventadaPor(autor.getNombre())
			.buildReceta();
	}

	private Receta crearRecetaPublicaSimpleRica(Usuario autor) {
		return new RecetaBuilder("Ñoquis")
			.totalCalorias(new Integer(400))
			.agregarIngrediente("Harina", new BigDecimal(300))
			.agregarIngrediente("Agua", new BigDecimal(70))
			.agregarIngrediente("Papa", new BigDecimal(100))
			.inventadaPor(autor.getNombre())
			.buildReceta();
		
	}

	private Receta crearRecetaNoAptaParaHipertensos() {
		return new RecetaBuilder("Pizza")
			.totalCalorias(new Integer(470))
			.agregarIngrediente("sal", new BigDecimal(300))
			.agregarIngrediente("agua", new BigDecimal(70))
			.agregarIngrediente("harina", new BigDecimal(100))
			.buildReceta();
	}

	private GrupoDeUsuarios crearGrupoDeUsuarios() throws BusinessException {
		GrupoDeUsuarios grupo = new GrupoDeUsuarios();
		grupo.setNombre("Amigos del club");

		List<PreferenciaGrupo> preferenciasAlimenticias = new ArrayList<PreferenciaGrupo>();
		Componente ravioles = new Componente("ravioles", new BigDecimal(0));
		Componente papa = new Componente("papa", new BigDecimal(0));
		
		PreferenciaGrupo prefRaviol = new PreferenciaGrupo(grupo,ravioles,true);
		PreferenciaGrupo prefPapa = new PreferenciaGrupo(grupo,papa,true);
		
		preferenciasAlimenticias.add(prefRaviol);
		preferenciasAlimenticias.add(prefPapa);

		grupo.setPreferenciasAlimenticias(preferenciasAlimenticias);

		return grupo;

	}

	@Test
	public void unaRecetaQueLeGustaPuedeSugerirseAUnUsuario() throws BusinessException {
		this.usuario.agregarAlimentoQueLeDisgusta("pollo");
		this.administrador.sugerir(crearRecetaPublicaSimpleRica(this.usuario), TestFactory.crearUsuarioBasicoValido());

	}

	@Test(expected = BusinessException.class)
	public void unaRecetaQueNoLeGustaNoPuedeSugerirseAUnUsuario() throws BusinessException {
		sistema.eliminarTodasLasRecetas();
		this.usuario = TestFactory.crearUsuarioBasicoValido();
		this.usuario.agregarAlimentoQueLeDisgusta("Harina");
		administrador.sugerir(crearRecetaPublicaSimpleRica(this.usuario), this.usuario);

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
		usuario.agregarReceta(crearRecetaPublicaSimpleRica(usuario));
		Usuario usuarioQueSeAgrega = TestFactory.crearUsuarioBasicoValido();
		usuarioQueSeAgrega.agregarReceta(crearRecetaPrivadaSimple(usuarioQueSeAgrega));
		grupo.agregarUsuario(usuarioQueSeAgrega);

		assertEquals(2, usuario.getRecetas().size() + usuarioQueSeAgrega.getRecetas().size());
	}

	@Test
	public void recetaQueNoTieneQueConocerPOrqueNoCompartenGrupo() throws BusinessException, RecetaException {
		sistema.eliminarTodasLasRecetas();
		GrupoDeUsuarios grupo = this.crearGrupoDeUsuarios();
		Usuario usuario = TestFactory.crearUsuarioBasicoValido();
		grupo.agregarUsuario(usuario);

		usuario.agregarReceta(crearRecetaPublicaSimpleRica(usuario));
		Usuario usuarioQueSeAgrega = TestFactory.crearUsuarioBasicoValido();
		usuarioQueSeAgrega.agregarReceta(crearRecetaPrivadaSimple(usuarioQueSeAgrega));

		assertEquals(1, buscador.recetasQueConoceEl(usuario).size());
	}

	@Test
	public void recetasQueConoce() {
		Usuario usuario = TestFactory.crearUsuarioBasicoValido();
		crearRecetaPublicaSimpleRica(usuario);
		crearRecetaPublicaSimpleRica(usuario);
		assertEquals(sistema.listarTodas(), buscador.recetasQueConoceEl(usuario));
	}

	@Test
	public void laRecetaContienePalabraClaveDePreferenciaDelGrupoYEsAptaParaTodosLosIntegrantes() throws BusinessException, RecetaException {
		sistema.eliminarTodasLasRecetas();
		GrupoDeUsuarios grupo = this.crearGrupoDeUsuarios();
		Usuario usuario = TestFactory.crearUsuarioBasicoValido();
		grupo.agregarUsuario(usuario);
		Usuario usuarioQueSeAgrega = TestFactory.crearUsuarioBasicoValido();
		usuarioQueSeAgrega.agregarReceta(crearRecetaPrivadaSimple(usuarioQueSeAgrega));

	}

	@Test(expected = BusinessException.class)
	public void laRecetaNOContienePalabraClaveDePreferenciaDelGrupoYEsAptaParaTodosLosIntegrantes() throws BusinessException, RecetaException {
		sistema.eliminarTodasLasRecetas();
		GrupoDeUsuarios grupo = this.crearGrupoDeUsuarios();
		Usuario usuario = TestFactory.crearUsuarioBasicoValido();
		grupo.agregarUsuario(usuario);
		Usuario usuarioQueSeAgrega = TestFactory.crearUsuarioBasicoValido();
		usuarioQueSeAgrega.agregarReceta(crearRecetaPrivadaSimple(usuarioQueSeAgrega));
		administrador.sugerir(crearRecetaPrivadaSimple(usuario), grupo);
	}
}
