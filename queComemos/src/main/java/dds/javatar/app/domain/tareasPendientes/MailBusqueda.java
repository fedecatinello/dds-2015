package dds.javatar.app.domain.tareasPendientes;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dds.javatar.app.domain.receta.Receta;
import dds.javatar.app.domain.receta.busqueda.Busqueda;
import dds.javatar.app.domain.tareasPendientes.mail.MailSender;
import dds.javatar.app.domain.tareasPendientes.mail.MailSenderImpl;
import dds.javatar.app.domain.usuario.Usuario;

public class MailBusqueda implements TareaPendiente {

	private static final Set<String> usuariosAEnviar = new HashSet<String>(Arrays.asList("jcontardo"));
	private static final String SEND_TO = "ddsutn@gmail.com";
	
	private static MailSender mailSender;

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
		if (mailSender == null) {
			// Sets default
			MailBusqueda.mailSender = new MailSenderImpl();
		}
		
		for (String usuarioAEnviar : usuariosAEnviar) {
			if (usuarioAEnviar.equals(this.usuario.getNombre())) {
				StringBuilder mensaje = new StringBuilder();
				mensaje.append("Parametros de busqueda:\n");
				mensaje.append("\t Nombre: " + this.busqueda.nombre() + "\n");
				mensaje.append("\t Dificultad: " + this.busqueda.nombre() + "\n");
				mensaje.append("\t Palabras clave:\n");
				for (String palabraClave : this.busqueda.palabrasClave()) {
					mensaje.append("\t\t " + palabraClave + "\n");
				}
				mensaje.append("Cantidad de resultados: " + this.resultados.size());

				MailBusqueda.mailSender.send(SEND_TO, mensaje.toString());
			}
		}
	}

	public static MailSender getMailSender() {
		return mailSender;
	}

	public static void setMailSender(MailSender mailSender) {
		MailBusqueda.mailSender = mailSender;
	}

}
