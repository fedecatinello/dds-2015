package dds.javatar.app.dto.usuario.condiciones;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.util.exception.UsuarioException;

@Entity
@DiscriminatorValue("Diabetico")
@SequenceGenerator(name="CONDICIONES_SEQ", sequenceName="condiciones_sequence")
public class Diabetico extends UsuarioConPreferencia {

	private static final Integer MAX_PESO = 70;

	@Id
	@Column(name="condicion_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="CONDICIONES_SEQ")
	public Long id;
	  
    @ManyToMany(mappedBy="condicionesPreexistentes")
	private Set<Usuario> usuariosCon;
	
	@Override
	public void validarUsuario(Usuario usuario) throws UsuarioException {
		super.validarUsuario(usuario);

		if (usuario.getSexo() == null) {
			throw new UsuarioException("El usuario diabetico debe indicar sexo");
		}
	}

	@Override
	public Boolean usuarioSigueRutinaSaludable(Usuario usuario) {
		return (usuario.getPeso()
			.intValue() <= MAX_PESO || usuario.getRutina()
			.esActiva());
	}

	@Override
	public boolean validarReceta(Receta receta) {
		if (receta.alimentoSobrepasaCantidad("azucar", new BigDecimal(100))) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public Boolean esVegano() {
		return Boolean.FALSE;
	}

	@Override
	public String getName() {
		return "Diabetico";
	}

}
