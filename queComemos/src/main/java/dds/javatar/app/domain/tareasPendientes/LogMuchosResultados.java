package dds.javatar.app.domain.tareasPendientes;

import java.util.List;

import org.apache.log4j.Logger;

import dds.javatar.app.domain.receta.Receta;
import dds.javatar.app.domain.usuario.Usuario;

public class LogMuchosResultados implements TareaPendiente {

	private Usuario usuario;
	private List<Receta> listaDeResultados;
	private static final Logger LOG = Logger.getLogger(LogMuchosResultados.class);

	public LogMuchosResultados(Usuario usuario, List<Receta> listaDeResultados) {
		this.usuario = usuario;
		this.listaDeResultados = listaDeResultados;
	}

	@Override
	public void execute() {
		if (listaDeResultados.size() > 100) {
			LOG.info("Consulta de: " + usuario.getNombre()
				.toString() + " devuelve mas de 100 resultados.(" + String.valueOf(listaDeResultados.size()) + " resultados)");
		}

	}
}
