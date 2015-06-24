package dds.javatar.app.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import queComemos.entrega3.dominio.Dificultad;
import dds.javatar.app.dto.receta.RecetaPrivadaCompuesta;
import dds.javatar.app.dto.receta.RecetaPrivadaSimple;
import dds.javatar.app.dto.receta.busqueda.Busqueda;
import dds.javatar.app.dto.usuario.Rutina;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.dto.usuario.Rutina.TipoRutina;
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

		return new Usuario.UsuarioBuilder().nombre("DonJuan").fechaNacimiento(calendar.getTime()).sexo(Usuario.Sexo.MASCULINO).peso(new BigDecimal(70))
				.altura(new BigDecimal(1.77)).rutina(new Rutina(TipoRutina.FUERTE, 20)).build();
	}

	public static Usuario crearUsuarioBasicoValidoDiferente() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.YEAR, -1);

		return new Usuario.UsuarioBuilder().nombre("Nombre").fechaNacimiento(calendar.getTime()).sexo(Usuario.Sexo.FEMENINO).peso(new BigDecimal(70))
				.altura(new BigDecimal(1.77)).rutina(new Rutina(TipoRutina.LEVE, 20)).build();
	}

	public static Usuario crearUsuarioHombre() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.YEAR, -1);

		return new Usuario.UsuarioBuilder().nombre("DonJuan").fechaNacimiento(calendar.getTime()).sexo(Usuario.Sexo.MASCULINO).peso(new BigDecimal(70))
				.altura(new BigDecimal(1.77)).rutina(new Rutina(TipoRutina.FUERTE, 20)).build();
	}

	public static Usuario crearUsuarioMujer() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.YEAR, -1);

		return new Usuario.UsuarioBuilder().nombre("DonJuan").fechaNacimiento(calendar.getTime()).sexo(Usuario.Sexo.FEMENINO).peso(new BigDecimal(70))
				.altura(new BigDecimal(1.77)).rutina(new Rutina(TipoRutina.FUERTE, 20)).build();
	}

	public static Usuario crearUsuarioConSobrepeso() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.YEAR, -1);

		return new Usuario.UsuarioBuilder().nombre("DonPedro").fechaNacimiento(calendar.getTime()).sexo(Usuario.Sexo.MASCULINO).peso(new BigDecimal(130))
				.altura(new BigDecimal(1.60)).rutina(new Rutina(TipoRutina.NADA, 20)).build();
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
		return new Busqueda.BusquedaBuilder().nombre("Matambre tiernizado de cerdo con papas noisette").dificultad(Dificultad.DIFICIL)
				.palabrasClave(new ArrayList<String>()).build();
	}

	public static Busqueda crearBusquedaFacilFideos() {
		return new Busqueda.BusquedaBuilder().nombre("fideos").dificultad(Dificultad.FACIL).palabrasClave(new ArrayList<String>()).build();
	}

	public static Busqueda crearBusquedaMediaPollo() {
		return new Busqueda.BusquedaBuilder().nombre("Pollo").dificultad(Dificultad.MEDIANA).palabrasClave(new ArrayList<String>()).build();
	}

	public static RecetaPrivadaSimple crearRecetaPrivadaSimple() {
		RecetaPrivadaSimple ravioles = new RecetaPrivadaSimple(350);
		ravioles.setNombre("Ravioles");
		ravioles.agregarIngrediente("Harina", new BigDecimal(300));
		ravioles.agregarIngrediente("Agua", new BigDecimal(70));
		ravioles.agregarIngrediente("Verdura", new BigDecimal(100));
		return ravioles;
	}

	public static RecetaPrivadaSimple crearRecetaPrivadaSimpleConMuchasCalorias() {
		RecetaPrivadaSimple ravioles = new RecetaPrivadaSimple(350);
		ravioles.setNombre("Ravioles");
		ravioles.agregarIngrediente("Harina", new BigDecimal(300));
		ravioles.agregarIngrediente("Agua", new BigDecimal(70));
		ravioles.agregarIngrediente("Verdura", new BigDecimal(100));
		ravioles.setCalorias(900);
		return ravioles;
	}

	public static RecetaPrivadaCompuesta crearRecetaPrivadaCompuesta() throws RecetaException {

		RecetaPrivadaSimple condimentos = new RecetaPrivadaSimple(120);
		RecetaPrivadaSimple pure = new RecetaPrivadaSimple(350);
		RecetaPrivadaSimple pollo = new RecetaPrivadaSimple(220);
		RecetaPrivadaCompuesta polloConPure = new RecetaPrivadaCompuesta();

		condimentos.setNombre("Condimentos");
		condimentos.agregarIngrediente("Oregano", new BigDecimal(20));

		pure.setNombre("Pure");
		pure.agregarIngrediente("Manteca", new BigDecimal(300));
		pure.agregarIngrediente("Papa", new BigDecimal(300));

		pollo.agregarIngrediente("pollo", new BigDecimal(290));
		pollo.setNombre("pollo");

		polloConPure.agregarSubReceta(pollo);
		polloConPure.agregarSubReceta(pure);
		polloConPure.agregarSubReceta(condimentos);
		polloConPure.setNombre("Pollo Con Pure");
		return polloConPure;
	}

	public static void crearListaRecetasParaUsuarioSize30(Usuario user) throws RecetaException {
		RecetaPrivadaCompuesta recetaPrivadaCompuesta;
		RecetaPrivadaSimple recetaPrivadaSimple;
		RecetaPrivadaSimple recetaPrivadaSimpleHipertenso;
		for (int i = 0; i < 10; i++) {
			recetaPrivadaCompuesta = crearRecetaPrivadaCompuesta();
			recetaPrivadaSimple = crearRecetaPrivadaSimple();
			recetaPrivadaSimpleHipertenso = crearRecetaNoAptaParaHipertensos();

			user.agregarReceta(recetaPrivadaSimple);
			user.agregarReceta(recetaPrivadaCompuesta);
			user.agregarReceta(recetaPrivadaSimpleHipertenso);
		}

	}

	public static void crearListaRecetasParaUsuarioSize3(Usuario user) throws RecetaException {
		RecetaPrivadaCompuesta recetaPrivadaCompuesta;
		RecetaPrivadaSimple recetaPrivadaSimple;
		RecetaPrivadaSimple recetaPrivadaSimpleHipertenso;
		recetaPrivadaCompuesta = crearRecetaPrivadaCompuesta();
		recetaPrivadaSimple = crearRecetaPrivadaSimple();
		recetaPrivadaSimpleHipertenso = crearRecetaNoAptaParaHipertensos();

		user.agregarReceta(recetaPrivadaSimple);

		user.agregarReceta(recetaPrivadaCompuesta);

		user.agregarReceta(recetaPrivadaSimpleHipertenso);

	}

	public static RecetaPrivadaSimple crearRecetaNoAptaParaHipertensos() {
		RecetaPrivadaSimple pizza = new RecetaPrivadaSimple(350);
		pizza.setNombre("Pizza");
		pizza.agregarIngrediente("sal", new BigDecimal(300));
		pizza.agregarIngrediente("Agua", new BigDecimal(70));
		pizza.agregarIngrediente("Harina", new BigDecimal(100));
		return pizza;
	}

}
