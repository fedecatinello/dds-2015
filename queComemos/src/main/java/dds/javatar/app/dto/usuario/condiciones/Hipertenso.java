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

@Entity
@DiscriminatorValue("Hipertenso")
@SequenceGenerator(name="CONDICIONES_SEQ", sequenceName="condiciones_sequence")
public class Hipertenso extends UsuarioConPreferencia {

    @Id
    @Column(name="condicion_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="CONDICIONES_SEQ")
    public Long id;
    
    @ManyToMany(mappedBy="condicionesPreexistentes")
   	private Set<Usuario> usuariosCon;
	
	private static final Set<String> ingredientesProhibidos = new HashSet<String>(Arrays.asList("sal", "caldo"));

	@Override
	public Boolean usuarioSigueRutinaSaludable(Usuario usuario) {
		return usuario.getRutina()
			.esIntensiva();
	}

	@Override
	public boolean validarReceta(Receta receta) {
		for (String ingredienteProhibido : ingredientesProhibidos) {
			if (receta.contieneIngrediente(ingredienteProhibido) || receta.contieneCondimento(ingredienteProhibido)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public Boolean esVegano() {
		return Boolean.FALSE;
	}

	@Override
	public String getName() {
		return "Hipertenso";
	}
}
