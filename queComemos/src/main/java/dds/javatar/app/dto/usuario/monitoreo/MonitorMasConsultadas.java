package dds.javatar.app.dto.usuario.monitoreo;

import java.util.HashMap;
import java.util.Map;

import queComemos.entrega3.dominio.Dificultad;
import dds.javatar.app.dto.usuario.Usuario;

public class MonitorMasConsultadas implements ConsultaObserver {

	private Map<String, Integer> consultasPorNombre;

	public MonitorMasConsultadas() {
		this.consultasPorNombre = new HashMap<String, Integer>();
	}

	@Override
	public void notificarConsulta(Usuario usuario, String nombre, Dificultad dificultad) {

		this.sumarUnoPara(nombre);
	}

	public String getNombreMasConsultado() {
		String maxKey = null;
		Integer maxValue = 0;

		for (String nombre : this.consultasPorNombre.keySet()) {
			if (this.consultasPorNombre.get(nombre) > maxValue) {
				maxKey = nombre;
			}
		}
		
		return maxKey;
	}

	private void sumarUnoPara(String nombre) {
		this.consultasPorNombre.put(nombre, this.consultasPorNombre.get(nombre) + 1);
	}

}
