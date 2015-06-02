package dds.javatar.app.dto.usuario.monitoreo;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import queComemos.entrega3.dominio.Dificultad;
import dds.javatar.app.dto.usuario.Usuario;

public class MonitorPorHora implements ConsultaObserver {

	
	private Map<Integer, Integer> consultasPorHora;
	
	public MonitorPorHora() {
		this.consultasPorHora = new HashMap<Integer, Integer>();
	}
	
	@Override
	public void notificarConsulta(Usuario usuario, String nombre, Dificultad dificultad) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int currentHours = calendar.get(Calendar.HOUR_OF_DAY);
		
		this.sumarUnoPara(currentHours);
	}
	
	public Map<Integer, Integer> getConsultasPorHora() {
		return this.consultasPorHora;
	}
	
	private void sumarUnoPara(Integer hour) {
		this.consultasPorHora.put(hour, this.consultasPorHora.get(hour) + 1);
	}

}
