package dds.javatar.app.domain.usuario.monitoreo;

import dds.javatar.app.domain.receta.busqueda.Busqueda;
import dds.javatar.app.domain.usuario.Usuario;

public interface ConsultaObserver {

	
	// TODO: cambiar los parametros a un objeto.
	void notificarConsulta(Usuario usuario, Busqueda busqueda);
	
	Integer cantidadConsultasReceta(String nombre);
	
}
