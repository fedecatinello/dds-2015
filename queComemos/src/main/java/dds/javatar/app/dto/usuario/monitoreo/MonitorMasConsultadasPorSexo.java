package dds.javatar.app.dto.usuario.monitoreo;

import queComemos.entrega3.dominio.Dificultad;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.dto.usuario.Usuario.Sexo;

public class MonitorMasConsultadasPorSexo implements ConsultaObserver {

	private MonitorMasConsultadas monitorHombres;
	private MonitorMasConsultadas monitorMujeres;

	public MonitorMasConsultadasPorSexo() {
		this.monitorHombres = new MonitorMasConsultadas();
		this.monitorMujeres = new MonitorMasConsultadas();
	}

	@Override
	public void notificarConsulta(Usuario usuario, String nombre, Dificultad dificultad) {
		if (Sexo.FEMENINO.equals(usuario.getSexo())) {
			this.monitorMujeres.notificarConsulta(usuario, nombre, dificultad);
		} else if (Sexo.MASCULINO.equals(usuario.getSexo())) {
			this.monitorHombres.notificarConsulta(usuario, nombre, dificultad);
		}
		// Si no tiene el sexo definido? No pasa nada.
	}

	public String getNombreMasConsultadoPorHombres() {
		return this.monitorHombres.getNombreMasConsultado();
	}

	public String getNombreMasConsultadoPorMujeres() {
		return this.monitorMujeres.getNombreMasConsultado();
	}
}
