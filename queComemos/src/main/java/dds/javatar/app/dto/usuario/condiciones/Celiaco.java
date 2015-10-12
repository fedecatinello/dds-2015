package dds.javatar.app.dto.usuario.condiciones;

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
@DiscriminatorValue("Celiaco")
@SequenceGenerator(name="CONDICIONES_SEQ", sequenceName="condiciones_sequence")
public class Celiaco implements CondicionPreexistente {

    @Id
    @Column(name="condicion_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="CONDICIONES_SEQ")
    public Long id;
	
    @ManyToMany(mappedBy="condicionesPreexistentes")
	private Set<Usuario> usuariosCon;
    
	@Override
	public Boolean usuarioSigueRutinaSaludable(Usuario usuario) {
		// Para los celiacos no hace falta que cumplan con alguna condición.
		return Boolean.TRUE;
	}

	@Override
	public void validarUsuario(Usuario usuario) throws UsuarioException {
		// TODO: no hay información al respecto, deberia solicitarse?
	}

	@Override
	public boolean validarReceta(Receta receta) {
		return true;
	}

	@Override
	public Boolean esVegano() {
		return Boolean.FALSE;
	}

	@Override
	public String getName() {
		return "Celiaco";
	}

}
