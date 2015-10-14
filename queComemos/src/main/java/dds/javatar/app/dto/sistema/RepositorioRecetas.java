package dds.javatar.app.dto.sistema;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import dds.javatar.app.dto.receta.Componente;
import dds.javatar.app.dto.receta.Receta;

public class RepositorioRecetas extends RepoDefault<Receta> {

	private static RepositorioRecetas instance;

	public static RepositorioRecetas getInstance() {
		if (instance == null) {
			instance = new RepositorioRecetas();
		}
		return instance;
	}

	/** Getter & Setter **/

	/** Metodos **/

	public Set<String> getAllIngredientes() {
		Set<String> ingredientes = new HashSet<String>();

		for (Receta receta : this.getAll()) {
			ingredientes.addAll(this.getComponentesByNombre(receta.getIngredientes()));
		}
		return ingredientes;
	}

	public void eliminarTodasLasRecetas() {
		// TODO ?
	}

	public Set<String> getComponentesByNombre(List<Componente> componentes) {
		return componentes.stream().map(Componente::getDescripcion).collect(Collectors.toSet());
	}

	@Override
	protected Class<Receta> getEntityType() {
		return Receta.class;
	}

	@Override
	protected void addCriteriaToSearchByExample(Criteria criteria, Receta receta) {
		if (receta.getNombre() != null) {
			criteria.add(Restrictions.eq("nombre", receta.getNombre()));
		}
	}

}
