package dds.javatar.app.dto.tareasPendientes;

import java.util.List;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.usuario.Usuario;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;



public class LogMuchosResultados implements TareaPendiente {
	

	private Usuario usuario;
	private List<Receta>  listaDeResultados;
	private static final Logger LOG =Logger.getLogger(LogMuchosResultados.class);
	public LogMuchosResultados(Usuario usuario, List<Receta>  listaDeResultados) {
		this.usuario = usuario;
		this.listaDeResultados = listaDeResultados;
	}
@Override
	public void execute() {
		PropertyConfigurator.configure(this.getClass().getResource(
			    "/lib/log4j.properties"));
		if (listaDeResultados.size()>100) {
			LOG.info("Consulta de: "+ usuario.getNombre() +" devuelve mas de 100 resultados.(" + String.valueOf(listaDeResultados.size())  + " resultados)");	
		}
		
	}
}

