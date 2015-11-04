package dds.javatar.app.domain.sistema;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dds.javatar.app.db.RepoDefault;
import dds.javatar.app.domain.receta.Receta;
import dds.javatar.app.domain.usuario.condiciones.CondicionPreexistente;
import dds.javatar.app.util.exception.RecetaException;

public class RepositorioRecetas extends RepoDefault<Receta> {

	public RepositorioRecetas() {
		super("recetas", Receta.class);
	}

	private static RepositorioRecetas instance;

	public static RepositorioRecetas getInstance() {
		if (instance == null) {
			instance = new RepositorioRecetas();
		}
		return instance;
	}

	/** Metodos **/

	@Override
	public void saveOrUpdate(Receta receta) {
		if (receta.getNombre() == null) {
			throw new RecetaException("El nombre no puede ser nulo!");
		}
		super.saveOrUpdate(receta);
	}

	
	public Set<String> getAllIngredientes() {
		Set<String> ingredientes = new HashSet<String>();

		for (Receta receta : this.getAll()) {
			ingredientes.addAll(receta.getIngredientes().keySet());
		}
		return ingredientes;
	}

	public List<String> getCondicionesName(Set<CondicionPreexistente> condicionesPreexistentes) {
		List<String> condiciones = new ArrayList<String>();
		for (CondicionPreexistente cond : condicionesPreexistentes) {
			condiciones.add(cond.getName());
		}
		return condiciones;
	}

}
