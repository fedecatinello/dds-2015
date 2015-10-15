package dds.javatar.app.dto.sistema;

import java.util.HashSet;
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

	@Override
	public void saveOrUpdate(Receta receta) {
		for (Componente comp : receta.getIngredientes()) {
			RepositorioComponentes.getInstance().saveOrUpdate(comp);
		}
		for (Componente comp : receta.getCondimentos()) {
			RepositorioComponentes.getInstance().saveOrUpdate(comp);
		}
		super.saveOrUpdate(receta);
	}

	public Set<String> getAllIngredientes() {
		Set<String> ingredientes = new HashSet<String>();

		for (Receta receta : this.getAll()) {
			ingredientes.addAll(this.getComponentesByNombre(receta.getIngredientes()));
		}
		return ingredientes;
	}

	public Set<String> getComponentesByNombre(Set<Componente> componentes) {
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

	public void eliminarTodasLasRecetas() {
	}

}
