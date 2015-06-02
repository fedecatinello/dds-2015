package dds.javatar.app.dto.usuario.monitoreo;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import queComemos.entrega3.dominio.Dificultad;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.dto.usuario.Usuario.Sexo;

public class MonitorConsultas {

	private Map<String, Integer> consultasPorNombre;
	private Map<String, Integer> consultasPorNombreDeHombres;
	private Map<String, Integer> consultasPorNombreDeMujeres;
	private Map<Integer, Integer> consultasPorHora;
	private Integer countDificlesPorVegano;

	public MonitorConsultas() {
		this.consultasPorNombre = new HashMap<String, Integer>();
		this.consultasPorNombreDeHombres = new HashMap<String, Integer>();
		this.consultasPorNombreDeMujeres = new HashMap<String, Integer>();
		this.consultasPorHora = new HashMap<Integer, Integer>();
		this.countDificlesPorVegano = 0;
	}

	public void actualizarInformacionDeConsultas(Usuario usuario, String nombre, Dificultad dificultad) {
		// Por nombre
		this.sumarConsultaPorNombre(this.consultasPorNombre, nombre);

		// Por nombre y sexo
		if (Sexo.FEMENINO.equals(usuario.getSexo())) {
			this.sumarConsultaPorNombre(this.consultasPorNombreDeMujeres, nombre);
		} else if (Sexo.MASCULINO.equals(usuario.getSexo())) {
			this.sumarConsultaPorNombre(this.consultasPorNombreDeHombres, nombre);
		}

		// Por hora
		this.sumarConsultaEnHoraActual();

		// Veganos
		if (usuario.esVegano() && Dificultad.DIFICIL.equals(dificultad)) {
			this.countDificlesPorVegano++;
		}
	}

	public String getNombreMasConsultado() {
		return this.getNombreMasConsultado(this.consultasPorNombre);
	}

	public String getNombreMasConsultadoPorHombres() {
		return this.getNombreMasConsultado(this.consultasPorNombreDeHombres);
	}

	public String getNombreMasConsultadoPorMujeres() {
		return this.getNombreMasConsultado(this.consultasPorNombreDeMujeres);
	}

	public Map<Integer, Integer> getConsultasPorHora() {
		return this.consultasPorHora;
	}

	public Integer getCantidadDeConsultasDificilesPorVeganos() {
		return this.countDificlesPorVegano;
	}

	/* Private */

	private void sumarConsultaEnHoraActual() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int currentHours = calendar.get(Calendar.HOUR_OF_DAY);

		Integer currentValue = this.consultasPorHora.get(currentHours);
		if (currentValue == null) {
			currentValue = 0;
		}

		this.consultasPorHora.put(currentHours, currentValue + 1);
	}

	private String getNombreMasConsultado(Map<String, Integer> consultas) {
		String maxKey = null;
		Integer maxValue = 0;

		for (String nombre : consultas.keySet()) {
			if (consultas.get(nombre) > maxValue) {
				maxValue = consultas.get(nombre);
				maxKey = nombre;
			}
		}

		return maxKey;
	}

	private void sumarConsultaPorNombre(Map<String, Integer> consultas, String nombre) {
		Integer currentValue = consultas.get(nombre);
		if (currentValue == null) {
			currentValue = 0;
		}

		consultas.put(nombre, currentValue + 1);
	}

}
