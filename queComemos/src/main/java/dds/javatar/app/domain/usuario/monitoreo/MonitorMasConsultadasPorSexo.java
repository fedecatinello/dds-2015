package dds.javatar.app.domain.usuario.monitoreo;

import dds.javatar.app.domain.receta.busqueda.Busqueda;
import dds.javatar.app.domain.usuario.Usuario;
import dds.javatar.app.domain.usuario.Usuario.Sexo;

public class MonitorMasConsultadasPorSexo implements ConsultaObserver {

	private MonitorMasConsultadas monitorHombres;
	private MonitorMasConsultadas monitorMujeres;

	public MonitorMasConsultadasPorSexo() {
		this.monitorHombres = new MonitorMasConsultadas();
		this.monitorMujeres = new MonitorMasConsultadas();
	}

	@Override
	public void notificarConsulta(Usuario usuario, Busqueda busqueda) {
		if (Sexo.FEMENINO.equals(usuario.getSexo())) {
			this.monitorMujeres.notificarConsulta(usuario, busqueda);
		} else if (Sexo.MASCULINO.equals(usuario.getSexo())) {
			this.monitorHombres.notificarConsulta(usuario, busqueda);
		}
		// Si no tiene el sexo definido? No pasa nada.
	}

	public String getNombreMasConsultadoPorHombres() {
		return this.monitorHombres.getNombreMasConsultado();
	}

	public String getNombreMasConsultadoPorMujeres() {
		return this.monitorMujeres.getNombreMasConsultado();
	}

	@Override
	public Integer cantidadConsultasReceta(String nombre) {
		// TODO Auto-generated method stub
		return 0;
	}
}
