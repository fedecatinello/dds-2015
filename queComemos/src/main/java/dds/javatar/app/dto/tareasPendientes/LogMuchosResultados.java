package dds.javatar.app.dto.tareasPendientes;

import dds.javatar.app.dto.usuario.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LogMuchosResultados implements TareaPendiente {

	private Usuario usuario;
	private static final Logger LOGGER = LoggerFactory
			.getLogger(LogMuchosResultados.class);

	public LogMuchosResultados(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public void execute() {
	
		
		LOGGER.info("La siguiente consulta devuelve mas de 100 resultados:");
		
		// Falta determinar si hago el control de +100 aca o en el buscador

		// hagoElLogConElFramework, agarre el log4j 2

	}

}
