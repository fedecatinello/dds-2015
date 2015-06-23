package dds.javatar.app.dto.tareasPendientes;

import dds.javatar.app.dto.usuario.Usuario;

public class LogMuchosResultados implements TareaPendiente {
	
	private Usuario usuario;

	public LogMuchosResultados(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public void execute() {
		
		// Falta determinar si hago el control de +100 aca o en el buscador
		
		//hagoElLogConElFramework, agarre el log4j 2

	}

}
