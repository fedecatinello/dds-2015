package dds.javatar.app.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import queComemos.entrega3.dominio.Dificultad;
import dds.javatar.app.domain.receta.Receta;
import dds.javatar.app.domain.receta.builder.RecetaBuilder;
import dds.javatar.app.domain.receta.busqueda.Busqueda;
import dds.javatar.app.domain.usuario.Rutina;
import dds.javatar.app.domain.usuario.Rutina.TipoRutina;
import dds.javatar.app.domain.usuario.Usuario;
import dds.javatar.app.util.exception.RecetaException;

public class TestFactory {

	/**
	 * subclasificar esto
	 * */
	private TestFactory() {

	}

	public static Usuario crearUsuarioBasicoValido() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.YEAR, -1);

		return new Usuario.UsuarioBuilder()
			.nombre("Mariano")
			.credenciales("mariano", "mariano")
			.fechaNacimiento(calendar.getTime())
			.sexo(Usuario.Sexo.MASCULINO)
			.peso(new BigDecimal(70))
			.altura(new BigDecimal(1.77))
			.rutina(new Rutina(TipoRutina.FUERTE, 20))
			.build();
	}

	public static Usuario crearUsuarioBasicoValidoDiferente() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.YEAR, -1);

		return new Usuario.UsuarioBuilder()
			.nombre("Pedro")
			.credenciales("Pedro", "Pedro")
			.fechaNacimiento(calendar.getTime())
			.sexo(Usuario.Sexo.FEMENINO)
			.peso(new BigDecimal(70))
			.altura(new BigDecimal(1.77))
			.rutina(new Rutina(TipoRutina.LEVE, 20))
			.build();
	}

	public static Usuario crearUsuarioHombre() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.YEAR, -1);

		return new Usuario.UsuarioBuilder()
			.nombre("DonJuan")
			.credenciales("donjuan", "donjuan")
			.fechaNacimiento(calendar.getTime())
			.sexo(Usuario.Sexo.MASCULINO)
			.peso(new BigDecimal(70))
			.altura(new BigDecimal(1.77))
			.rutina(new Rutina(TipoRutina.FUERTE, 20))
			.build();
	}

	public static Usuario crearUsuarioMujer() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.YEAR, -1);

		return new Usuario.UsuarioBuilder()
			.nombre("DonJuan")
			.credenciales("donjuan", "donjuan")
			.fechaNacimiento(calendar.getTime())
			.sexo(Usuario.Sexo.FEMENINO)
			.peso(new BigDecimal(70))
			.altura(new BigDecimal(1.77))
			.rutina(new Rutina(TipoRutina.FUERTE, 20))
			.build();
	}

	public static Usuario crearUsuarioConSobrepeso() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.YEAR, -1);

		return new Usuario.UsuarioBuilder()
			.nombre("DonPedro")
			.credenciales("donpredo", "donpredo")
			.fechaNacimiento(calendar.getTime())
			.sexo(Usuario.Sexo.MASCULINO)
			.peso(new BigDecimal(130))
			.altura(new BigDecimal(1.60))
			.rutina(new Rutina(TipoRutina.NADA, 20))
			.build();
	}

	public static Busqueda crearBusquedaDificilPollo() {
		return new Busqueda.BusquedaBuilder().nombre("Pollo").dificultad(Dificultad.DIFICIL).palabrasClave(new ArrayList<String>()).build();
	}

	public static Busqueda crearBusquedaDificilFideos() {
		return new Busqueda.BusquedaBuilder().nombre("fideos").dificultad(Dificultad.DIFICIL).palabrasClave(new ArrayList<String>()).build();
	}

	public static Busqueda crearBusquedaDificilMollejas() {
		return new Busqueda.BusquedaBuilder().nombre("Mollejas al verdeo").dificultad(Dificultad.DIFICIL).palabrasClave(new ArrayList<String>()).build();
	}

	public static Busqueda crearBusquedaDificilMatambre() {
		return new Busqueda.BusquedaBuilder()
			.nombre("Matambre tiernizado de cerdo con papas noisette")
			.dificultad(Dificultad.DIFICIL)
			.palabrasClave(new ArrayList<String>())
			.build();
	}

	public static Busqueda crearBusquedaFacilFideos() {
		return new Busqueda.BusquedaBuilder().nombre("fideos").dificultad(Dificultad.FACIL).palabrasClave(new ArrayList<String>()).build();
	}

	public static Busqueda crearBusquedaMediaPollo() {
		return new Busqueda.BusquedaBuilder().nombre("Pollo").dificultad(Dificultad.MEDIANA).palabrasClave(new ArrayList<String>()).build();
	}

	public static Receta crearRecetaPrivadaSimple() {
		return new RecetaBuilder("Ravioles").totalCalorias(310).inventadaPor("DonJuan").agregarIngrediente("Tomatess", new BigDecimal(300)).buildReceta();
	}

	public static Receta crearRecetaPublicaSimple() {
		return new RecetaBuilder("Canelones").totalCalorias(310).agregarIngrediente("Tomatess", new BigDecimal(300)).buildReceta();
	}

	public static Receta crearRecetaPrivadaSimpleConMuchasCalorias() {
		return new RecetaBuilder("RaviolesVerdura")
			.totalCalorias(900)
			.agregarIngrediente("Harina", new BigDecimal(300))
			.agregarIngrediente("Agua", new BigDecimal(70))
			.agregarIngrediente("Verdura", new BigDecimal(100))
			.inventadaPor("Juan Carlos")
			.buildReceta();

	}

	public static Receta crearRecetaPublicaSimpleConMuchasCalorias() {
		return new RecetaBuilder("RaviolesRicota")
			.totalCalorias(900)
			.agregarIngrediente("Harina", new BigDecimal(300))
			.agregarIngrediente("Agua", new BigDecimal(70))
			.agregarIngrediente("Verdura", new BigDecimal(100))
			.buildReceta();

	}

	public static Receta crearRecetaPrivadaCompuesta() throws RecetaException {

		Receta recetaPrivadaSimplePure = new RecetaBuilder("Pure")
			.totalCalorias(350)
			.agregarIngrediente("Manteca", new BigDecimal(300))
			.agregarIngrediente("Papa", new BigDecimal(300))
			.inventadaPor("Pepe")
			.buildReceta();
		Receta recetaPrivadaSimplePollo = new RecetaBuilder("Pollo")
			.totalCalorias(220)
			.agregarIngrediente("pollo", new BigDecimal(280))
			.inventadaPor("Juan")
			.buildReceta();
		Receta recetaPrivadaSimpleCondimentos = new RecetaBuilder("Condimentos")
			.totalCalorias(120)
			.agregarIngrediente("Oregano", new BigDecimal(20))
			.inventadaPor("Gabriel")
			.buildReceta();

		return new RecetaBuilder("pollo con pure")
			.agregarSubReceta(recetaPrivadaSimplePollo)
			.agregarSubReceta(recetaPrivadaSimpleCondimentos)
			.agregarSubReceta(recetaPrivadaSimplePure)
			.inventadaPor("Juana")
			.buildReceta();

	}

	public static void crearListaRecetasParaUsuarioSize30(Usuario user) throws RecetaException {
		Receta recetaPrivadaSimpleHipertenso;
		Receta recetaPublicaSimple, recetaPublicaSimple2, recetaPublicaSimple3;
		for (int i = 0; i < 10; i++) {
			recetaPublicaSimple = crearRecetaPublicaSimpleConMuchasCalorias();
			recetaPublicaSimple2 = crearRecetaPublicaSimple();
			recetaPublicaSimple3 = crearRecetaPublicaSimple();
			recetaPrivadaSimpleHipertenso = crearRecetaNoAptaParaHipertensos();

			user.agregarReceta(recetaPublicaSimple);
			user.agregarReceta(recetaPublicaSimple2);
			user.agregarReceta(recetaPublicaSimple3);
			user.agregarReceta(recetaPrivadaSimpleHipertenso);
		}

	}

	public static void crearListaRecetasParaUsuarioSize101(Usuario user) throws RecetaException {
		Receta recetaPrivadaCompuesta;
		Receta recetaPrivadaSimple;
		for (int i = 0; i < 50; i++) {
			recetaPrivadaCompuesta = crearRecetaPrivadaCompuesta();
			recetaPrivadaSimple = crearRecetaPrivadaSimple();

			user.agregarReceta(recetaPrivadaSimple);
			user.agregarReceta(recetaPrivadaCompuesta);
		}

		recetaPrivadaSimple = crearRecetaPrivadaSimple();
		user.agregarReceta(recetaPrivadaSimple);
	}

	public static void crearListaRecetasParaUsuarioSize3(Usuario user) throws RecetaException {
		Receta recetaPrivadaCompuesta;
		Receta recetaPrivadaSimple;
		Receta recetaPrivadaSimpleHipertenso;
		recetaPrivadaCompuesta = crearRecetaPrivadaCompuesta();
		recetaPrivadaSimple = crearRecetaPrivadaSimple();
		recetaPrivadaSimpleHipertenso = crearRecetaNoAptaParaHipertensos();

		user.agregarReceta(recetaPrivadaSimple);

		user.agregarReceta(recetaPrivadaCompuesta);

		user.agregarReceta(recetaPrivadaSimpleHipertenso);

	}

	public static Receta crearRecetaNoAptaParaHipertensos() {
		return new RecetaBuilder("Pizza")
			.totalCalorias(350)
			.agregarIngrediente("sal", new BigDecimal(300))
			.agregarIngrediente("Agua", new BigDecimal(70))
			.agregarIngrediente("Harina", new BigDecimal(100))
			.inventadaPor("Pepito")
			.buildReceta();
	}

}
