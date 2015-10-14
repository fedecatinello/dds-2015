package dds.javatar.app.dto.usuario.condiciones;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.util.exception.UsuarioException;

@Entity
@Table(name = "CondicionPreexistente")
public abstract class CondicionPreexistente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	private Long id;

	public abstract void validarUsuario(Usuario usuario) throws UsuarioException;

	public abstract Boolean usuarioSigueRutinaSaludable(Usuario usuario);

	public abstract boolean validarReceta(Receta receta);

	public abstract Boolean esVegano();

	public abstract String getName();

}
