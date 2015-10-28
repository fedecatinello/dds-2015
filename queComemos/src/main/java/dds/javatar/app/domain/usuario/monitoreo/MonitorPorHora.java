package dds.javatar.app.domain.usuario.monitoreo;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import dds.javatar.app.domain.receta.busqueda.Busqueda;
import dds.javatar.app.domain.usuario.Usuario;

public class MonitorPorHora implements ConsultaObserver {

	private Map<Integer, Integer> consultasPorHora;

	public MonitorPorHora() {
		this.consultasPorHora = new HashMap<Integer, Integer>();
	}

	@Override
	public void notificarConsulta(Usuario usuario, Busqueda busqueda) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int currentHours = calendar.get(Calendar.HOUR_OF_DAY);

		this.sumarUnoPara(currentHours);
	}

	public Map<Integer, Integer> getConsultasPorHora() {
		return this.consultasPorHora;
	}

	private void sumarUnoPara(Integer hour) {
		Integer currentValue = this.consultasPorHora.get(hour);
		if (currentValue == null) {
			currentValue = 0;
		}

		this.consultasPorHora.put(hour, currentValue + 1);
	}

	@Override
	public Integer cantidadConsultasReceta(String nombre) {
		// TODO Auto-generated method stub
		return 0;
	}

}
