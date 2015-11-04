package dds.javatar.app.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dds.javatar.app.domain.receta.Receta;
import dds.javatar.app.domain.receta.busqueda.Buscador;
import dds.javatar.app.domain.receta.busqueda.Busqueda;
import dds.javatar.app.domain.sistema.Administrador;
import dds.javatar.app.domain.usuario.Usuario;
import dds.javatar.app.util.exception.FilterException;

public class TestFavoritas {

	protected Usuario user;
	protected Buscador buscador;

	@Before
	public void initialize() {
		this.user = new Usuario.UsuarioBuilder().nombre("Usuarin Testero").credenciales("testero", "testero").build();
		this.buscador = new Buscador();
	}

	@After
	public void limpiar() {
		List<Receta> faveadas = this.user.getFavoritos();
		this.user.getFavoritos().removeAll(faveadas);
	}

	@Test
	public void testQuiereAgregarAFavoritas() throws FilterException {

		List<String> palabrasClave = new ArrayList<String>();
		palabrasClave.add("whisky");
		Busqueda busqueda = new Busqueda.BusquedaBuilder().palabrasClave(palabrasClave).build();

		this.user.habilitarOpcionFavearTodas();
		List<Receta> resultadoBusqueda = this.buscador.realizarBusquedaPara(this.user, busqueda);
		Administrador.getInstance().realizarTareasPendientes();

		/* La búsqueda devuelve una */
		assertEquals(1, resultadoBusqueda.size());

		/* Se favea esa sola */
		assertEquals(1, this.user.getFavoritos().size());
	}

	@Test
	public void testNoQuiereAgregarAFavoritas() throws FilterException {

		Busqueda unaBusqueda = new Busqueda.BusquedaBuilder().nombre("ensalada").palabrasClave(new ArrayList<String>()).build();

		this.user.desHabilitarOpcionFavearTodas();
		List<Receta> resultadoBusqueda = this.buscador.realizarBusquedaPara(this.user, unaBusqueda);
		Administrador.getInstance().realizarTareasPendientes();

		/* La búsqueda devuelve todas las ensaladas */
		assertEquals(3, resultadoBusqueda.size());

		/* pero no favea ninguna porque no modificó esta opción en su perfil */
		assertEquals(0, this.user.getFavoritos().size());
	}

	@Test
	public void testNoAgregaRepetidas() throws FilterException {

		List<String> palabrasClave = new ArrayList<String>();
		palabrasClave.add("helado de chocolate");
		Busqueda busqueda = new Busqueda.BusquedaBuilder().palabrasClave(palabrasClave).build();

		this.user.habilitarOpcionFavearTodas();
		List<Receta> resultadoBusqueda = this.buscador.realizarBusquedaPara(this.user, busqueda);
		Administrador.getInstance().realizarTareasPendientes();

		/* La búsqueda devuelve una */
		assertEquals(1, resultadoBusqueda.size());

		/* Se favea esa sola */
		assertEquals(1, this.user.getFavoritos().size());

		Administrador.getInstance().realizarTareasPendientes();

		/* No se vuelve a agregar porque ya estaba */
		assertEquals(1, this.user.getFavoritos().size());
	}
}
