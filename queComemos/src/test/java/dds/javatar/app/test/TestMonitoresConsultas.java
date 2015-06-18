package dds.javatar.app.test;

import java.util.Calendar;
import java.util.Date;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dds.javatar.app.dto.receta.busqueda.Busqueda;
import dds.javatar.app.dto.receta.busqueda.adapter.BusquedaAdapter;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.dto.usuario.condiciones.Vegano;
import dds.javatar.app.dto.usuario.monitoreo.MonitorMasConsultadas;
import dds.javatar.app.dto.usuario.monitoreo.MonitorMasConsultadasPorSexo;
import dds.javatar.app.dto.usuario.monitoreo.MonitorPorHora;
import dds.javatar.app.dto.usuario.monitoreo.MonitorVeganos;

public class TestMonitoresConsultas {

	private Usuario usuario, hombre, mujer, usuarioVegano;
	private Busqueda unaBusquedaDificilPollo, unaBusquedaDificilFideos,
			unaBusquedaDificilMollejas, unaBusquedaDificilMatambre;
	private Busqueda unaBusquedaFacilFideos, unaBusquedaMediaPollo;

	@Before
	public void initialize() {
		this.usuario = TestFactory.crearUsuarioBasicoValido();
		this.hombre = TestFactory.crearUsuarioHombre();
		this.mujer = TestFactory.crearUsuarioMujer();

		this.usuarioVegano = TestFactory.crearUsuarioBasicoValido();
		this.usuarioVegano.agregarCondicionPreexistente(new Vegano());		
		
		this.unaBusquedaDificilPollo = TestFactory.crearBusquedaDificilPollo();
		this.unaBusquedaDificilFideos = TestFactory.crearBusquedaDificilFideos();
		this.unaBusquedaDificilMollejas = TestFactory.crearBusquedaDificilMollejas();			
		this.unaBusquedaDificilMatambre = TestFactory.crearBusquedaDificilMatambre();
		this.unaBusquedaFacilFideos = TestFactory.crearBusquedaFacilFideos();
		this.unaBusquedaMediaPollo = TestFactory.crearBusquedaMediaPollo();	

	}

	// Punto 3: Monitores
	@Test
	public void testConsultasPorHora() {
		MonitorPorHora observer = new MonitorPorHora();
		BusquedaAdapter.getInstance().addObserver(observer);

		this.usuario.consultarRecetasExternas(unaBusquedaDificilPollo);
		this.usuario.consultarRecetasExternas(unaBusquedaDificilPollo);
		this.usuario.consultarRecetasExternas(unaBusquedaDificilPollo);

		Assert.assertEquals(3,
				observer
					.getConsultasPorHora()
						.get(this.horaActual())
						.intValue());
	}

	@Test
	public void testConsultasPorHoraAgregadoDespues() {
		this.usuario.consultarRecetasExternas(unaBusquedaDificilPollo);
		this.usuario.consultarRecetasExternas(unaBusquedaDificilPollo);

		MonitorPorHora observer = new MonitorPorHora();
		BusquedaAdapter.getInstance().addObserver(observer);

		this.usuario.consultarRecetasExternas(unaBusquedaDificilPollo);
		this.usuario.consultarRecetasExternas(unaBusquedaDificilPollo);

		Assert.assertEquals(2,
				observer
					.getConsultasPorHora()
						.get(this.horaActual())
						.intValue());
	}

	@Test
	public void testRecetasMasConsultada() {
		MonitorMasConsultadas observer = new MonitorMasConsultadas();
		BusquedaAdapter.getInstance().addObserver(observer);

		this.usuario.consultarRecetasExternas(unaBusquedaDificilPollo);
		this.usuario.consultarRecetasExternas(unaBusquedaDificilPollo);
		this.usuario.consultarRecetasExternas(unaBusquedaDificilFideos);
		this.usuario.consultarRecetasExternas(unaBusquedaDificilPollo);
		this.usuario.consultarRecetasExternas(unaBusquedaDificilFideos);

		Assert.assertEquals("Pollo", observer.getNombreMasConsultado());
	}

	@Test
	public void testRecetasMasConsultadaAgregadoDespues() {

		this.usuario.consultarRecetasExternas(unaBusquedaDificilPollo);
		this.usuario.consultarRecetasExternas(unaBusquedaDificilPollo);

		MonitorMasConsultadas observer = new MonitorMasConsultadas();
		BusquedaAdapter.getInstance().addObserver(observer);

		this.usuario.consultarRecetasExternas(unaBusquedaDificilFideos);
		this.usuario.consultarRecetasExternas(unaBusquedaDificilPollo);
		this.usuario.consultarRecetasExternas(unaBusquedaDificilFideos);

		Assert.assertEquals("fideos", observer.getNombreMasConsultado());
	}

	@Test
	public void testConsultasPorSexo() {

		MonitorMasConsultadasPorSexo observer = new MonitorMasConsultadasPorSexo();
		BusquedaAdapter.getInstance().addObserver(observer);

		hombre.consultarRecetasExternas(unaBusquedaDificilMollejas);
		hombre.consultarRecetasExternas(unaBusquedaDificilMollejas);
		hombre.consultarRecetasExternas(unaBusquedaDificilFideos);
		hombre.consultarRecetasExternas(unaBusquedaDificilPollo);
		hombre.consultarRecetasExternas(unaBusquedaDificilMollejas);

		mujer.consultarRecetasExternas(unaBusquedaDificilPollo);
		mujer.consultarRecetasExternas(unaBusquedaDificilMatambre);
		mujer.consultarRecetasExternas(unaBusquedaDificilFideos);
		mujer.consultarRecetasExternas(unaBusquedaDificilMatambre);
		mujer.consultarRecetasExternas(unaBusquedaDificilMatambre);

		Assert.assertEquals("Mollejas al verdeo",
				observer.getNombreMasConsultadoPorHombres());
		Assert.assertEquals("Matambre tiernizado de cerdo con papas noisette",
				observer.getNombreMasConsultadoPorMujeres());
	}

	@Test
	public void testConsultasPorSexoAgregadoDespues() {

		hombre.consultarRecetasExternas(unaBusquedaDificilMollejas);
		hombre.consultarRecetasExternas(unaBusquedaDificilMollejas);

		MonitorMasConsultadasPorSexo observer = new MonitorMasConsultadasPorSexo();
		BusquedaAdapter.getInstance().addObserver(observer);
		hombre.consultarRecetasExternas(unaBusquedaDificilFideos);
		hombre.consultarRecetasExternas(unaBusquedaDificilFideos);
		hombre.consultarRecetasExternas(unaBusquedaDificilPollo);
		hombre.consultarRecetasExternas(unaBusquedaDificilMollejas);

		mujer.consultarRecetasExternas(unaBusquedaDificilPollo);
		mujer.consultarRecetasExternas(unaBusquedaDificilMatambre);
		mujer.consultarRecetasExternas(unaBusquedaDificilFideos);
		mujer.consultarRecetasExternas(unaBusquedaDificilMatambre);
		mujer.consultarRecetasExternas(unaBusquedaDificilMatambre);

		Assert.assertEquals("fideos",
				observer.getNombreMasConsultadoPorHombres());
		Assert.assertEquals("Matambre tiernizado de cerdo con papas noisette",
				observer.getNombreMasConsultadoPorMujeres());
	}

	@Test
	public void testConsultasDificilesVeganos() {
		MonitorVeganos observer = new MonitorVeganos();
		BusquedaAdapter.getInstance().addObserver(observer);

		usuarioVegano.consultarRecetasExternas(unaBusquedaDificilMollejas);
		usuarioVegano.consultarRecetasExternas(unaBusquedaDificilMollejas);
		usuarioVegano.consultarRecetasExternas(unaBusquedaFacilFideos);
		usuarioVegano.consultarRecetasExternas(unaBusquedaFacilFideos);
		usuarioVegano.consultarRecetasExternas(unaBusquedaMediaPollo);
		usuarioVegano.consultarRecetasExternas(unaBusquedaDificilMollejas);
		usuarioVegano.consultarRecetasExternas(unaBusquedaMediaPollo);
		usuarioVegano.consultarRecetasExternas(unaBusquedaDificilMatambre);
		usuarioVegano.consultarRecetasExternas(unaBusquedaFacilFideos);
		usuarioVegano.consultarRecetasExternas(unaBusquedaDificilMatambre);
		usuarioVegano.consultarRecetasExternas(unaBusquedaDificilMatambre);

		Assert.assertEquals(6, observer.getCantidad().intValue());
	}

	@Test
	public void testTodosLosObservers() {

		MonitorVeganos monitorVeganos = new MonitorVeganos();
		MonitorMasConsultadasPorSexo monitorPorSexo = new MonitorMasConsultadasPorSexo();
		MonitorPorHora monitorPorHora = new MonitorPorHora();
		MonitorMasConsultadas monitorMasConsultados = new MonitorMasConsultadas();

		BusquedaAdapter.getInstance().addObserver(monitorVeganos);
		BusquedaAdapter.getInstance().addObserver(monitorPorSexo);
		BusquedaAdapter.getInstance().addObserver(monitorPorHora);
		BusquedaAdapter.getInstance().addObserver(monitorMasConsultados);

		hombre.consultarRecetasExternas(unaBusquedaMediaPollo);
		hombre.consultarRecetasExternas(unaBusquedaMediaPollo);
		hombre.consultarRecetasExternas(unaBusquedaFacilFideos);
		hombre.consultarRecetasExternas(unaBusquedaMediaPollo);
		hombre.consultarRecetasExternas(unaBusquedaFacilFideos);

		usuarioVegano.consultarRecetasExternas(unaBusquedaDificilMollejas);
		usuarioVegano.consultarRecetasExternas(unaBusquedaDificilMollejas);
		usuarioVegano.consultarRecetasExternas(unaBusquedaFacilFideos);
		usuarioVegano.consultarRecetasExternas(unaBusquedaFacilFideos);
		usuarioVegano.consultarRecetasExternas(unaBusquedaFacilFideos);
		usuarioVegano.consultarRecetasExternas(unaBusquedaMediaPollo);
		usuarioVegano.consultarRecetasExternas(unaBusquedaDificilMollejas);
		usuarioVegano.consultarRecetasExternas(unaBusquedaMediaPollo);
		usuarioVegano.consultarRecetasExternas(unaBusquedaDificilMatambre);
		usuarioVegano.consultarRecetasExternas(unaBusquedaDificilMatambre);
		usuarioVegano.consultarRecetasExternas(unaBusquedaDificilMatambre);

		hombre.consultarRecetasExternas(unaBusquedaDificilMollejas);
		hombre.consultarRecetasExternas(unaBusquedaDificilMollejas);
		hombre.consultarRecetasExternas(unaBusquedaFacilFideos);
		hombre.consultarRecetasExternas(unaBusquedaFacilFideos);
		hombre.consultarRecetasExternas(unaBusquedaFacilFideos);
		hombre.consultarRecetasExternas(unaBusquedaDificilMollejas);

		mujer.consultarRecetasExternas(unaBusquedaFacilFideos);
		mujer.consultarRecetasExternas(unaBusquedaDificilMatambre);
		mujer.consultarRecetasExternas(unaBusquedaFacilFideos);
		mujer.consultarRecetasExternas(unaBusquedaDificilMatambre);
		mujer.consultarRecetasExternas(unaBusquedaDificilMatambre);

		Assert.assertEquals(6, monitorVeganos.getCantidad().intValue());
		Assert.assertEquals("fideos",
				monitorPorSexo.getNombreMasConsultadoPorHombres());
		Assert.assertEquals("Matambre tiernizado de cerdo con papas noisette",
				monitorPorSexo.getNombreMasConsultadoPorMujeres());
		Assert.assertEquals(27,
				monitorPorHora
					.getConsultasPorHora()
						.get(this.horaActual())
						.intValue());
		Assert.assertEquals("fideos",
				monitorMasConsultados.getNombreMasConsultado());
	}

	@Test
	public void testTodosLosObserversEnDistintoOrden() {

		MonitorVeganos monitorVeganos = new MonitorVeganos();
		MonitorMasConsultadasPorSexo monitorPorSexo = new MonitorMasConsultadasPorSexo();
		MonitorPorHora monitorPorHora = new MonitorPorHora();
		MonitorMasConsultadas monitorMasConsultados = new MonitorMasConsultadas();

		BusquedaAdapter.getInstance().addObserver(monitorMasConsultados);
		BusquedaAdapter.getInstance().addObserver(monitorPorSexo);
		BusquedaAdapter.getInstance().addObserver(monitorPorHora);
		BusquedaAdapter.getInstance().addObserver(monitorVeganos);

		hombre.consultarRecetasExternas(unaBusquedaMediaPollo);
		hombre.consultarRecetasExternas(unaBusquedaMediaPollo);
		hombre.consultarRecetasExternas(unaBusquedaFacilFideos);
		hombre.consultarRecetasExternas(unaBusquedaMediaPollo);
		hombre.consultarRecetasExternas(unaBusquedaFacilFideos);

		usuarioVegano.consultarRecetasExternas(unaBusquedaDificilMollejas);
		usuarioVegano.consultarRecetasExternas(unaBusquedaDificilMollejas);
		usuarioVegano.consultarRecetasExternas(unaBusquedaFacilFideos);
		usuarioVegano.consultarRecetasExternas(unaBusquedaFacilFideos);
		usuarioVegano.consultarRecetasExternas(unaBusquedaFacilFideos);
		usuarioVegano.consultarRecetasExternas(unaBusquedaMediaPollo);
		usuarioVegano.consultarRecetasExternas(unaBusquedaDificilMollejas);
		usuarioVegano.consultarRecetasExternas(unaBusquedaMediaPollo);
		usuarioVegano.consultarRecetasExternas(unaBusquedaDificilMatambre);
		usuarioVegano.consultarRecetasExternas(unaBusquedaDificilMatambre);
		usuarioVegano.consultarRecetasExternas(unaBusquedaDificilMatambre);

		hombre.consultarRecetasExternas(unaBusquedaDificilMollejas);
		hombre.consultarRecetasExternas(unaBusquedaDificilMollejas);
		hombre.consultarRecetasExternas(unaBusquedaFacilFideos);
		hombre.consultarRecetasExternas(unaBusquedaFacilFideos);
		hombre.consultarRecetasExternas(unaBusquedaFacilFideos);
		hombre.consultarRecetasExternas(unaBusquedaDificilMollejas);

		mujer.consultarRecetasExternas(unaBusquedaFacilFideos);
		mujer.consultarRecetasExternas(unaBusquedaDificilMatambre);
		mujer.consultarRecetasExternas(unaBusquedaFacilFideos);
		mujer.consultarRecetasExternas(unaBusquedaDificilMatambre);
		mujer.consultarRecetasExternas(unaBusquedaDificilMatambre);

		Assert.assertEquals(6, monitorVeganos.getCantidad().intValue());
		Assert.assertEquals("fideos",
				monitorPorSexo.getNombreMasConsultadoPorHombres());
		Assert.assertEquals("Matambre tiernizado de cerdo con papas noisette",
				monitorPorSexo.getNombreMasConsultadoPorMujeres());
		Assert.assertEquals(27,
				monitorPorHora
					.getConsultasPorHora()
						.get(this.horaActual())
						.intValue());
		Assert.assertEquals("fideos",
				monitorMasConsultados.getNombreMasConsultado());

	}

	private Integer horaActual() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		return calendar.get(Calendar.HOUR_OF_DAY);
	}

}
