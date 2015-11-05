package dds.javatar.app.dto.usuario.monitoreo;

import java.util.HashMap;
import java.util.Map;

import dds.javatar.app.dto.receta.busqueda.Busqueda;
import dds.javatar.app.dto.usuario.Usuario;

public class MonitorMasConsultadas implements ConsultaObserver {

	private Map<String, Integer> consultasPorNombre;

	public MonitorMasConsultadas() {
		this.consultasPorNombre = new HashMap<String, Integer>();
	}

	@Override
	public void notificarConsulta(Usuario usuario, Busqueda busqueda) {
		this.sumarUnoPara(busqueda.nombre());
	}

	public String getNombreMasConsultado() {
		String maxKey = null;
		Integer maxValue = 0;

		for (String nombre : this.consultasPorNombre.keySet()) {
			if (this.consultasPorNombre.get(nombre) > maxValue) {
				maxValue = this.consultasPorNombre.get(nombre);
				maxKey = nombre;
			}
		}
		
		return maxKey;
	}

	private void sumarUnoPara(String nombre) {
		Integer currentValue = this.consultasPorNombre.get(nombre);
		if (currentValue == null) {
			currentValue = 0;
		}
		
		this.consultasPorNombre.put(nombre, currentValue + 1);
	}

	@Override
	public Integer cantidadConsultasReceta(String nombre) {
		
		Integer value = 0;
		
		if (this.consultasPorNombre.containsKey(nombre)) {
			value = this.consultasPorNombre.get(nombre);
		}
		
		return value;
	}

}
