package dds.javatar.app.domain.usuario.condiciones;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

import dds.javatar.app.domain.receta.Receta;
import dds.javatar.app.domain.usuario.Usuario;
import dds.javatar.app.util.exception.UsuarioException;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = As.WRAPPER_OBJECT)
@JsonSubTypes({ @Type(name = "celiaco", value = Celiaco.class), @Type(name = "diabetico", value = Diabetico.class),
		@Type(name = "hipertenso", value = Hipertenso.class), @Type(name = "vegano", value = Vegano.class) })
public interface CondicionPreexistente {

	void validarUsuario(Usuario usuario) throws UsuarioException;

	Boolean usuarioSigueRutinaSaludable(Usuario usuario);

	boolean validarReceta(Receta receta);

	Boolean esVegano();

	public String getName();

}
