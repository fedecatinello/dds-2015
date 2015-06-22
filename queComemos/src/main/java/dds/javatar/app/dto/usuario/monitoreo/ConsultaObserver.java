package dds.javatar.app.dto.usuario.monitoreo;

import dds.javatar.app.dto.receta.busqueda.Busqueda;
import dds.javatar.app.dto.usuario.Usuario;

public interface ConsultaObserver {

	
	// TODO: cambiar los parametros a un objeto.
	void notificarConsulta(Usuario usuario, Busqueda busqueda);
	
}
