package dds.javatar.app.dto.sistema;

import java.util.HashSet;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import dds.javatar.app.dto.receta.Componente;
import dds.javatar.app.dto.usuario.condiciones.Celiaco;
import dds.javatar.app.dto.usuario.condiciones.CondicionPreexistente;
import dds.javatar.app.dto.usuario.condiciones.Diabetico;
import dds.javatar.app.dto.usuario.condiciones.Hipertenso;
import dds.javatar.app.dto.usuario.condiciones.Vegano;

public class RepositorioComponentes extends RepoDefault<Componente> {

	private HashSet<CondicionPreexistente> crearIngredientes() {
		HashSet<CondicionPreexistente> condicionesAll = new HashSet<CondicionPreexistente>();
		Vegano vegano = new Vegano();
		Hipertenso hipertenso = new Hipertenso();
		Diabetico diabetico = new Diabetico();
		Celiaco celiaco = new Celiaco();

		//
		condicionesAll.add(vegano);
		condicionesAll.add(hipertenso);
		condicionesAll.add(diabetico);
		condicionesAll.add(celiaco);
		return condicionesAll;
	}

	private static RepositorioComponentes instance;

	public static RepositorioComponentes getInstance() {
		if (instance == null) {
			instance = new RepositorioComponentes();
		}
		return instance;
	}

	@Override
	protected Class<Componente> getEntityType() {
		return Componente.class;
	}

	@Override
	protected void addCriteriaToSearchByExample(Criteria criteria, Componente componente) {
		if (componente.getDescripcion() != null) {
			criteria.add(Restrictions.eq("descripcion", componente.getDescripcion()));
		}
	}
}