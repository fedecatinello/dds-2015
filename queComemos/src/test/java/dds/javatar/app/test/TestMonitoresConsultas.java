package dds.javatar.app.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dds.javatar.app.dto.receta.busqueda.adapter.BusquedaAdapter;
import dds.javatar.app.dto.usuario.Rutina;
import dds.javatar.app.dto.usuario.Rutina.TipoRutina;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.dto.usuario.Usuario.Sexo;
import dds.javatar.app.dto.usuario.condiciones.Vegano;
import dds.javatar.app.dto.usuario.monitoreo.MonitorMasConsultadas;
import dds.javatar.app.dto.usuario.monitoreo.MonitorMasConsultadasPorSexo;
import dds.javatar.app.dto.usuario.monitoreo.MonitorPorHora;
import dds.javatar.app.dto.usuario.monitoreo.MonitorVeganos;

public class TestMonitoresConsultas {

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

	// Punto 3: Monitores
	@Test
	public void testConsultasPorHora() {
		MonitorPorHora observer = new MonitorPorHora();
		BusquedaAdapter.getInstance().addObserver(observer);

		this.usuario.consultarRecetasExternas("Pollo", "D", new ArrayList<String>());
		this.usuario.consultarRecetasExternas("Pollo", "D", new ArrayList<String>());
		this.usuario.consultarRecetasExternas("Pollo", "D", new ArrayList<String>());

		Assert.assertEquals(Integer.valueOf(3), observer.getConsultasPorHora().get(this.horaActual()));
	}

	@Test
	public void testConsultasPorHoraAgregadoDespues() {
		this.usuario.consultarRecetasExternas("Pollo", "D", new ArrayList<String>());
		this.usuario.consultarRecetasExternas("Pollo", "D", new ArrayList<String>());

		MonitorPorHora observer = new MonitorPorHora();
		BusquedaAdapter.getInstance().addObserver(observer);

		this.usuario.consultarRecetasExternas("Pollo", "D", new ArrayList<String>());
		this.usuario.consultarRecetasExternas("Pollo", "D", new ArrayList<String>());

		Assert.assertEquals(Integer.valueOf(2), observer.getConsultasPorHora().get(this.horaActual()));
	}

	@Test
	public void testRecetasMasConsultada() {
		MonitorMasConsultadas observer = new MonitorMasConsultadas();
		BusquedaAdapter.getInstance().addObserver(observer);

		this.usuario.consultarRecetasExternas("Pollo", "D", new ArrayList<String>());
		this.usuario.consultarRecetasExternas("Pollo", "D", new ArrayList<String>());
		this.usuario.consultarRecetasExternas("Fideos", "D", new ArrayList<String>());
		this.usuario.consultarRecetasExternas("Pollo", "D", new ArrayList<String>());
		this.usuario.consultarRecetasExternas("Fideos", "D", new ArrayList<String>());

		Assert.assertEquals("Pollo", observer.getNombreMasConsultado());
	}

	@Test
	public void testRecetasMasConsultadaAgregadoDespues() {
		this.usuario.consultarRecetasExternas("Pollo", "D", new ArrayList<String>());
		this.usuario.consultarRecetasExternas("Pollo", "D", new ArrayList<String>());

		MonitorMasConsultadas observer = new MonitorMasConsultadas();
		BusquedaAdapter.getInstance().addObserver(observer);

		this.usuario.consultarRecetasExternas("Fideos", "D", new ArrayList<String>());
		this.usuario.consultarRecetasExternas("Pollo", "D", new ArrayList<String>());
		this.usuario.consultarRecetasExternas("Fideos", "D", new ArrayList<String>());

		Assert.assertEquals("Fideos", observer.getNombreMasConsultado());
	}

	@Test
	public void testConsultasPorSexo() {
		Usuario hombre = this.crearUsuarioBasicoValido();
		hombre.setSexo(Sexo.MASCULINO);
		Usuario mujer = this.crearUsuarioBasicoValido();
		mujer.setSexo(Sexo.FEMENINO);

		MonitorMasConsultadasPorSexo observer = new MonitorMasConsultadasPorSexo();
		BusquedaAdapter.getInstance().addObserver(observer);

		hombre.consultarRecetasExternas("Mollejas al verdeo", "D", new ArrayList<String>());
		hombre.consultarRecetasExternas("Mollejas al verdeo", "D", new ArrayList<String>());
		hombre.consultarRecetasExternas("Fideos", "D", new ArrayList<String>());
		hombre.consultarRecetasExternas("Pollo", "D", new ArrayList<String>());
		hombre.consultarRecetasExternas("Mollejas al verdeo", "D", new ArrayList<String>());

		mujer.consultarRecetasExternas("Pollo", "D", new ArrayList<String>());
		mujer.consultarRecetasExternas("Matambre tiernizado de cerdo con papas noisette", "D", new ArrayList<String>());
		mujer.consultarRecetasExternas("Fideos", "D", new ArrayList<String>());
		mujer.consultarRecetasExternas("Matambre tiernizado de cerdo con papas noisette", "D", new ArrayList<String>());
		mujer.consultarRecetasExternas("Matambre tiernizado de cerdo con papas noisette", "D", new ArrayList<String>());

		Assert.assertEquals("Mollejas al verdeo", observer.getNombreMasConsultadoPorHombres());
		Assert.assertEquals("Matambre tiernizado de cerdo con papas noisette", observer.getNombreMasConsultadoPorMujeres());
	}

	@Test
	public void testConsultasPorSexoAgregadoDespues() {
		Usuario hombre = this.crearUsuarioBasicoValido();
		hombre.setSexo(Sexo.MASCULINO);
		Usuario mujer = this.crearUsuarioBasicoValido();
		mujer.setSexo(Sexo.FEMENINO);

		hombre.consultarRecetasExternas("Mollejas al verdeo", "D", new ArrayList<String>());
		hombre.consultarRecetasExternas("Mollejas al verdeo", "D", new ArrayList<String>());

		MonitorMasConsultadasPorSexo observer = new MonitorMasConsultadasPorSexo();
		BusquedaAdapter.getInstance().addObserver(observer);
		hombre.consultarRecetasExternas("Fideos", "D", new ArrayList<String>());
		hombre.consultarRecetasExternas("Fideos", "D", new ArrayList<String>());
		hombre.consultarRecetasExternas("Pollo", "D", new ArrayList<String>());
		hombre.consultarRecetasExternas("Mollejas al verdeo", "D", new ArrayList<String>());

		mujer.consultarRecetasExternas("Pollo", "D", new ArrayList<String>());
		mujer.consultarRecetasExternas("Matambre tiernizado de cerdo con papas noisette", "D", new ArrayList<String>());
		mujer.consultarRecetasExternas("Fideos", "D", new ArrayList<String>());
		mujer.consultarRecetasExternas("Matambre tiernizado de cerdo con papas noisette", "D", new ArrayList<String>());
		mujer.consultarRecetasExternas("Matambre tiernizado de cerdo con papas noisette", "D", new ArrayList<String>());

		Assert.assertEquals("Fideos", observer.getNombreMasConsultadoPorHombres());
		Assert.assertEquals("Matambre tiernizado de cerdo con papas noisette", observer.getNombreMasConsultadoPorMujeres());
	}

	@Test
	public void testConsultasDicilesVeganos() {
		Usuario usuarioVegano = this.crearUsuarioBasicoValido();
		usuarioVegano.agregarCondicionPreexistente(new Vegano());

		MonitorVeganos observer = new MonitorVeganos();
		BusquedaAdapter.getInstance().addObserver(observer);

		usuarioVegano.consultarRecetasExternas("Mollejas al verdeo", "D", new ArrayList<String>());
		usuarioVegano.consultarRecetasExternas("Mollejas al verdeo", "D", new ArrayList<String>());
		usuarioVegano.consultarRecetasExternas("Fideos", "F", new ArrayList<String>());
		usuarioVegano.consultarRecetasExternas("Fideos", "F", new ArrayList<String>());
		usuarioVegano.consultarRecetasExternas("Pollo", "M", new ArrayList<String>());
		usuarioVegano.consultarRecetasExternas("Mollejas al verdeo", "D", new ArrayList<String>());
		usuarioVegano.consultarRecetasExternas("Pollo", "M", new ArrayList<String>());
		usuarioVegano.consultarRecetasExternas("Matambre tiernizado de cerdo con papas noisette", "D", new ArrayList<String>());
		usuarioVegano.consultarRecetasExternas("Fideos", "F", new ArrayList<String>());
		usuarioVegano.consultarRecetasExternas("Matambre tiernizado de cerdo con papas noisette", "D", new ArrayList<String>());
		usuarioVegano.consultarRecetasExternas("Matambre tiernizado de cerdo con papas noisette", "D", new ArrayList<String>());

		Assert.assertEquals(Integer.valueOf(6), observer.getCantidad());
	}

	@Test
	public void testTodosLosObservers() {
		Usuario usuarioVegano = this.crearUsuarioBasicoValido();
		usuarioVegano.agregarCondicionPreexistente(new Vegano());
		Usuario hombre = this.crearUsuarioBasicoValido();
		hombre.setSexo(Sexo.MASCULINO);
		Usuario mujer = this.crearUsuarioBasicoValido();
		mujer.setSexo(Sexo.FEMENINO);

		MonitorVeganos monitorVeganos = new MonitorVeganos();
		MonitorMasConsultadasPorSexo monitorPorSexo = new MonitorMasConsultadasPorSexo();
		MonitorPorHora monitorPorHora = new MonitorPorHora();
		MonitorMasConsultadas monitorMasConsultados = new MonitorMasConsultadas();

		BusquedaAdapter.getInstance().addObserver(monitorVeganos);
		BusquedaAdapter.getInstance().addObserver(monitorPorSexo);
		BusquedaAdapter.getInstance().addObserver(monitorPorHora);
		BusquedaAdapter.getInstance().addObserver(monitorMasConsultados);

		hombre.consultarRecetasExternas("Pollo", "M", new ArrayList<String>());
		hombre.consultarRecetasExternas("Pollo", "M", new ArrayList<String>());
		hombre.consultarRecetasExternas("Fideos", "F", new ArrayList<String>());
		hombre.consultarRecetasExternas("Pollo", "M", new ArrayList<String>());
		hombre.consultarRecetasExternas("Fideos", "F", new ArrayList<String>());

		usuarioVegano.consultarRecetasExternas("Mollejas al verdeo", "D", new ArrayList<String>());
		usuarioVegano.consultarRecetasExternas("Mollejas al verdeo", "D", new ArrayList<String>());
		usuarioVegano.consultarRecetasExternas("Fideos", "F", new ArrayList<String>());
		usuarioVegano.consultarRecetasExternas("Fideos", "F", new ArrayList<String>());
		usuarioVegano.consultarRecetasExternas("Pollo", "M", new ArrayList<String>());
		usuarioVegano.consultarRecetasExternas("Mollejas al verdeo", "D", new ArrayList<String>());
		usuarioVegano.consultarRecetasExternas("Pollo", "M", new ArrayList<String>());
		usuarioVegano.consultarRecetasExternas("Matambre tiernizado de cerdo con papas noisette", "D", new ArrayList<String>());
		usuarioVegano.consultarRecetasExternas("Fideos", "F", new ArrayList<String>());
		usuarioVegano.consultarRecetasExternas("Matambre tiernizado de cerdo con papas noisette", "D", new ArrayList<String>());
		usuarioVegano.consultarRecetasExternas("Matambre tiernizado de cerdo con papas noisette", "D", new ArrayList<String>());

		hombre.consultarRecetasExternas("Mollejas al verdeo", "D", new ArrayList<String>());
		hombre.consultarRecetasExternas("Mollejas al verdeo", "D", new ArrayList<String>());
		hombre.consultarRecetasExternas("Fideos", "F", new ArrayList<String>());
		hombre.consultarRecetasExternas("Fideos", "F", new ArrayList<String>());
		hombre.consultarRecetasExternas("Pollo", "D", new ArrayList<String>());
		hombre.consultarRecetasExternas("Mollejas al verdeo", "D", new ArrayList<String>());

		mujer.consultarRecetasExternas("Pollo", "D", new ArrayList<String>());
		mujer.consultarRecetasExternas("Matambre tiernizado de cerdo con papas noisette", "D", new ArrayList<String>());
		mujer.consultarRecetasExternas("Fideos", "F", new ArrayList<String>());
		mujer.consultarRecetasExternas("Matambre tiernizado de cerdo con papas noisette", "D", new ArrayList<String>());
		mujer.consultarRecetasExternas("Matambre tiernizado de cerdo con papas noisette", "D", new ArrayList<String>());

		Assert.assertEquals(Integer.valueOf(6), monitorVeganos.getCantidad());
		Assert.assertEquals("Fideos", monitorPorSexo.getNombreMasConsultadoPorHombres());
		Assert.assertEquals("Matambre tiernizado de cerdo con papas noisette", monitorPorSexo.getNombreMasConsultadoPorMujeres());
		Assert.assertEquals(Integer.valueOf(27), monitorPorHora.getConsultasPorHora().get(this.horaActual()));
		Assert.assertEquals("Fideos", monitorMasConsultados.getNombreMasConsultado());
	}

	private Integer horaActual() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		return calendar.get(Calendar.HOUR_OF_DAY);
	}
}
