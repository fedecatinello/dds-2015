package dds.javatar.app.dto.tareasPendientes;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.receta.busqueda.Busqueda;
import dds.javatar.app.dto.usuario.Usuario;

public class MailBusqueda implements TareaPendiente {

	private static final Set<String> usuariosAEnviar = new HashSet<String>(Arrays.asList("jcontardo"));

	private Usuario usuario;
	private Busqueda busqueda;
	private List<Receta> resultados;

	public MailBusqueda(Usuario usuario, Busqueda busqueda, List<Receta> resultados) {
		this.usuario = usuario;
		this.busqueda = busqueda;
		this.resultados = resultados;
	}

	@Override
	public void execute() {
		for (String usuarioAEnviar : usuariosAEnviar) {
			if (usuarioAEnviar.equals(usuario.getNombre())) {
				StringBuilder mensaje = new StringBuilder();
				mensaje.append("Parametros de busqueda:\n");
				mensaje.append("\t Nombre: " + this.busqueda.nombre() + "\n");
				mensaje.append("\t Dificultad: " + this.busqueda.nombre() + "\n");
				mensaje.append("\t Palabras clave:\n");
				for (String palabraClave : this.busqueda.palabrasClave()) {
					mensaje.append("\t\t " + palabraClave + "\n");
				}
				mensaje.append("Cantidad de resultados: " + resultados.size());

				System.out.println(mensaje);
				// TODO, ver bien que hacer con el mensaje, y como validarlo desde el lado de los tests.
			}
		}
	}

}
