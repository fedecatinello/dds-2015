package dds.javatar.app.dto.usuario.monitoreo;

import queComemos.entrega3.dominio.Dificultad;
import dds.javatar.app.dto.usuario.Usuario;

public interface ConsultaObserver {

	
	// TODO: cambiar los parametros a un objeto.
	void notificarConsulta(Usuario usuario, String nombre, Dificultad dificultad);
	
}
