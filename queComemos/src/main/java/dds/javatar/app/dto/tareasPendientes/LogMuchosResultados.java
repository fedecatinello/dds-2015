package dds.javatar.app.dto.tareasPendientes;

import java.util.List;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.usuario.Usuario;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



public class LogMuchosResultados implements TareaPendiente {

	private Usuario usuario;
	private List<Receta>  listaDeResultados;
	private static final Log LOG = LogFactory.getLog(LogMuchosResultados.class);
	public LogMuchosResultados(Usuario usuario, List<Receta>  listaDeResultados) {
		this.usuario = usuario;
		this.listaDeResultados = listaDeResultados;
	}

	@Override
	public void execute() {
		if (listaDeResultados.size()>100) {
			LOG.info("Consulta de: "+ usuario.getNombre() +" devuelve mas de 100 resultados.(" + String.valueOf(listaDeResultados.size())  + " resultados)");	
		}
		
	}
}

