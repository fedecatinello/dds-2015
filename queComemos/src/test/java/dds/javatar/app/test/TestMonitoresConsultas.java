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
import dds.javatar.app.dto.usuario.monitoreo.MonitorConsultas;

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
		BusquedaAdapter.getInstance().setMonitorConsultas(new MonitorConsultas());

		this.usuario.consultarRecetasExternas("Pollo", "D", new ArrayList<String>());
		this.usuario.consultarRecetasExternas("Pollo", "D", new ArrayList<String>());
		this.usuario.consultarRecetasExternas("Pollo", "D", new ArrayList<String>());

		Assert.assertEquals(3, BusquedaAdapter.getInstance().getMonitorConsultas().getConsultasPorHora().get(this.horaActual()).intValue());
	}

	@Test
	public void testConsultasPorHoraAgregadoDespues() {
		this.usuario.consultarRecetasExternas("Pollo", "D", new ArrayList<String>());
		this.usuario.consultarRecetasExternas("Pollo", "D", new ArrayList<String>());

		BusquedaAdapter.getInstance().setMonitorConsultas(new MonitorConsultas());

		this.usuario.consultarRecetasExternas("Pollo", "D", new ArrayList<String>());
		this.usuario.consultarRecetasExternas("Pollo", "D", new ArrayList<String>());

		Assert.assertEquals(2, BusquedaAdapter.getInstance().getMonitorConsultas().getConsultasPorHora().get(this.horaActual()).intValue());
	}

	@Test
	public void testRecetasMasConsultada() {
		BusquedaAdapter.getInstance().setMonitorConsultas(new MonitorConsultas());

		this.usuario.consultarRecetasExternas("Pollo", "D", new ArrayList<String>());
		this.usuario.consultarRecetasExternas("Pollo", "D", new ArrayList<String>());
		this.usuario.consultarRecetasExternas("Fideos", "D", new ArrayList<String>());
		this.usuario.consultarRecetasExternas("Pollo", "D", new ArrayList<String>());
		this.usuario.consultarRecetasExternas("Fideos", "D", new ArrayList<String>());

		Assert.assertEquals("Pollo", BusquedaAdapter.getInstance().getMonitorConsultas().getNombreMasConsultado());
	}

	@Test
	public void testRecetasMasConsultadaAgregadoDespues() {
		this.usuario.consultarRecetasExternas("Pollo", "D", new ArrayList<String>());
		this.usuario.consultarRecetasExternas("Pollo", "D", new ArrayList<String>());

		BusquedaAdapter.getInstance().setMonitorConsultas(new MonitorConsultas());

		this.usuario.consultarRecetasExternas("Fideos", "D", new ArrayList<String>());
		this.usuario.consultarRecetasExternas("Pollo", "D", new ArrayList<String>());
		this.usuario.consultarRecetasExternas("Fideos", "D", new ArrayList<String>());

		Assert.assertEquals("Fideos", BusquedaAdapter.getInstance().getMonitorConsultas().getNombreMasConsultado());
	}

	@Test
	public void testConsultasPorSexo() {
		Usuario hombre = this.crearUsuarioBasicoValido();
		hombre.setSexo(Sexo.MASCULINO);
		Usuario mujer = this.crearUsuarioBasicoValido();
		mujer.setSexo(Sexo.FEMENINO);

		BusquedaAdapter.getInstance().setMonitorConsultas(new MonitorConsultas());

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

		Assert.assertEquals("Mollejas al verdeo", BusquedaAdapter.getInstance().getMonitorConsultas().getNombreMasConsultadoPorHombres());
		Assert.assertEquals("Matambre tiernizado de cerdo con papas noisette", BusquedaAdapter.getInstance().getMonitorConsultas()
				.getNombreMasConsultadoPorMujeres());
	}

	@Test
	public void testConsultasPorSexoAgregadoDespues() {
		Usuario hombre = this.crearUsuarioBasicoValido();
		hombre.setSexo(Sexo.MASCULINO);
		Usuario mujer = this.crearUsuarioBasicoValido();
		mujer.setSexo(Sexo.FEMENINO);

		hombre.consultarRecetasExternas("Mollejas al verdeo", "D", new ArrayList<String>());
		hombre.consultarRecetasExternas("Mollejas al verdeo", "D", new ArrayList<String>());

		BusquedaAdapter.getInstance().setMonitorConsultas(new MonitorConsultas());

		hombre.consultarRecetasExternas("Fideos", "D", new ArrayList<String>());
		hombre.consultarRecetasExternas("Fideos", "D", new ArrayList<String>());
		hombre.consultarRecetasExternas("Pollo", "D", new ArrayList<String>());
		hombre.consultarRecetasExternas("Mollejas al verdeo", "D", new ArrayList<String>());

		mujer.consultarRecetasExternas("Pollo", "D", new ArrayList<String>());
		mujer.consultarRecetasExternas("Matambre tiernizado de cerdo con papas noisette", "D", new ArrayList<String>());
		mujer.consultarRecetasExternas("Fideos", "D", new ArrayList<String>());
		mujer.consultarRecetasExternas("Matambre tiernizado de cerdo con papas noisette", "D", new ArrayList<String>());
		mujer.consultarRecetasExternas("Matambre tiernizado de cerdo con papas noisette", "D", new ArrayList<String>());

		Assert.assertEquals("Fideos", BusquedaAdapter.getInstance().getMonitorConsultas().getNombreMasConsultadoPorHombres());
		Assert.assertEquals("Matambre tiernizado de cerdo con papas noisette", BusquedaAdapter.getInstance().getMonitorConsultas()
				.getNombreMasConsultadoPorMujeres());
	}

	@Test
	public void testConsultasDificilesVeganos() {
		Usuario usuarioVegano = this.crearUsuarioBasicoValido();
		usuarioVegano.agregarCondicionPreexistente(new Vegano());

		BusquedaAdapter.getInstance().setMonitorConsultas(new MonitorConsultas());

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

		Assert.assertEquals(6, BusquedaAdapter.getInstance().getMonitorConsultas().getCantidadDeConsultasDificilesPorVeganos().intValue());
	}

	@Test
	public void testTodosLosObservers() {
		Usuario usuarioVegano = this.crearUsuarioBasicoValido();
		usuarioVegano.agregarCondicionPreexistente(new Vegano());
		Usuario hombre = this.crearUsuarioBasicoValido();
		hombre.setSexo(Sexo.MASCULINO);
		Usuario mujer = this.crearUsuarioBasicoValido();
		mujer.setSexo(Sexo.FEMENINO);

		BusquedaAdapter.getInstance().setMonitorConsultas(new MonitorConsultas());

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

		Assert.assertEquals(6, BusquedaAdapter.getInstance().getMonitorConsultas().getCantidadDeConsultasDificilesPorVeganos().intValue());
		Assert.assertEquals("Fideos", BusquedaAdapter.getInstance().getMonitorConsultas().getNombreMasConsultadoPorHombres());
		Assert.assertEquals("Matambre tiernizado de cerdo con papas noisette", BusquedaAdapter.getInstance().getMonitorConsultas()
				.getNombreMasConsultadoPorMujeres());
		Assert.assertEquals(27, BusquedaAdapter.getInstance().getMonitorConsultas().getConsultasPorHora().get(this.horaActual()).intValue());
		Assert.assertEquals("Fideos", BusquedaAdapter.getInstance().getMonitorConsultas().getNombreMasConsultado());
	}

	@Test
	public void testTodosLosObserversEnDistintoOrden() {
		Usuario usuarioVegano = this.crearUsuarioBasicoValido();
		usuarioVegano.agregarCondicionPreexistente(new Vegano());
		Usuario hombre = this.crearUsuarioBasicoValido();
		hombre.setSexo(Sexo.MASCULINO);
		Usuario mujer = this.crearUsuarioBasicoValido();
		mujer.setSexo(Sexo.FEMENINO);

		BusquedaAdapter.getInstance().setMonitorConsultas(new MonitorConsultas());

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

		Assert.assertEquals(6, BusquedaAdapter.getInstance().getMonitorConsultas().getCantidadDeConsultasDificilesPorVeganos().intValue());
		Assert.assertEquals("Fideos", BusquedaAdapter.getInstance().getMonitorConsultas().getNombreMasConsultadoPorHombres());
		Assert.assertEquals("Matambre tiernizado de cerdo con papas noisette", BusquedaAdapter.getInstance().getMonitorConsultas()
				.getNombreMasConsultadoPorMujeres());
		Assert.assertEquals(27, BusquedaAdapter.getInstance().getMonitorConsultas().getConsultasPorHora().get(this.horaActual()).intValue());
		Assert.assertEquals("Fideos", BusquedaAdapter.getInstance().getMonitorConsultas().getNombreMasConsultado());
	}

	private Integer horaActual() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		return calendar.get(Calendar.HOUR_OF_DAY);
	}
}
