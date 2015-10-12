package dds.javatar.app.dto.usuario.condiciones;

import java.util.Arrays;
import java.util.HashSet;
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
@DiscriminatorValue("Vegano")
@SequenceGenerator(name="CONDICIONES_SEQ", sequenceName="condiciones_sequence")
public class Vegano implements CondicionPreexistente {

    @Id
    @Column(name="condicion_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="CONDICIONES_SEQ")
    public Long id;
    
    @ManyToMany(mappedBy="condicionesPreexistentes")
   	private Set<Usuario> usuariosCon;
	
	private static final Set<String> alimentosProhibidos = new HashSet<String>(Arrays.asList("pollo", "carne", "chivito", "chori"));

	@Override
	public void validarUsuario(Usuario usuario) throws UsuarioException {
		for (String alimentoProhibido : alimentosProhibidos) {
			if (usuario.tienePreferenciaAlimenticia(alimentoProhibido)) {
				throw new UsuarioException(String.format("El usuario no puede tener como preferencia %s por ser vegano", alimentoProhibido));
			}
		}
	}

	@Override
	public Boolean usuarioSigueRutinaSaludable(Usuario usuario) {
		return usuario.tienePreferenciaAlimenticia("fruta");
	}

	@Override
	public boolean validarReceta(Receta receta) {
		for (String alimentoProhibido : alimentosProhibidos) {
			if (receta.contieneIngrediente(alimentoProhibido)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public Boolean esVegano() {
		return Boolean.TRUE;
	}

	@Override
	public String getName() {
		return "Vegano";
	}
}
