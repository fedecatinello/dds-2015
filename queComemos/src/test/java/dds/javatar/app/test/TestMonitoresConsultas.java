package dds.javatar.app.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import dds.javatar.app.dto.usuario.Rutina;
import dds.javatar.app.dto.usuario.Rutina.TipoRutina;
import dds.javatar.app.dto.usuario.Usuario;

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
	public void testConsultasPorhora()  {
		this.usuario.consultarReceta("Pollo", "D", new ArrayList<String>());
		this.usuario.consultarReceta("Pollo", "D", new ArrayList<String>());
		this.usuario.consultarReceta("Pollo", "D", new ArrayList<String>());
		//new RecetaAdapter();
		//new Sistema()
	//	this.usuario.validar();
	}

}
