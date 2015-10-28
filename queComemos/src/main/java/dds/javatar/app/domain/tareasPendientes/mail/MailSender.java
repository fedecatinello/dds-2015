package dds.javatar.app.domain.tareasPendientes.mail;

public interface MailSender {

	public void send(String to, String message);
	
}
