package dds.javatar.app.dto.sistema;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import dds.javatar.app.dto.receta.PreferenciaUsuario;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.dto.usuario.condiciones.CondicionPreexistente;

public class RepositorioUsuarios extends RepoDefault<Usuario> {

	@Override
	public List<Usuario> allInstances() {
		Session session = sessionFactory.openSession();
		try {
			return session.createCriteria(Usuario.class).list();
		} finally {
			session.close();
		}
	}

	private static RepositorioUsuarios instance;

	public static RepositorioUsuarios getInstance() {
		if (instance == null) {
			instance = new RepositorioUsuarios();
		}
		return instance;
	}

	public Usuario get(Usuario Usuario) {
		return this.searchByName(Usuario).get(0);
	}

	/* las búsquedas no tienen criterio */

	@Override
	public void saveOrUpdate(Usuario usuario) {
		for (PreferenciaUsuario pref : usuario.getPreferenciasAlimenticias()) {
			RepositorioComponentes.getInstance().saveOrUpdate(pref.getComponente());
		}
		for (CondicionPreexistente cond : usuario.getCondicionesPreexistentes()) {
			RepositorioCondiciones.getInstance().saveOrUpdate(cond);
		}
		super.saveOrUpdate(usuario);
	}

	public Usuario getByUsername(String username) {
		try {
			List<Usuario> listaUsers = this.allInstances();
			return listaUsers.stream().filter(s -> s.getUser().equals(username)).map(p -> p).collect(Collectors.toList()).get(0);
		} catch (Exception e) {
			return null; // revianta cuando no encuentra nada
		}
	}

	public Usuario getByCredential(String username, String password) {
		try {
			List<Usuario> listaUsers = this.getAll();
			return listaUsers.stream().filter(s -> s.getUser().equals(username) && s.getPassword().equals(password)).collect(Collectors.toList()).get(0);
		} catch (Exception e) {
			return null; // revianta cuando no encuentra nada
		}
	}

	public List<Usuario> searchByName(Usuario usuario) {
		List<Usuario> listaUsuariosConElMismoNombre = new ArrayList<Usuario>();
		for (Usuario usuarioEnSistema : this.allInstances()) {
			if (usuarioEnSistema.getNombre().equals(usuario.getNombre())) {
				listaUsuariosConElMismoNombre.add(usuarioEnSistema);
			}
		}
		return listaUsuariosConElMismoNombre;
	}

	public List<Usuario> list(Usuario usuario) {
		List<Usuario> listaUsuariosConElMismoNombreyCondicionesPreexistentes = this.searchByName(usuario);
		listaUsuariosConElMismoNombreyCondicionesPreexistentes.forEach(usuarioConElMismoNombre -> this.addToUsersList(usuarioConElMismoNombre, usuario,
				listaUsuariosConElMismoNombreyCondicionesPreexistentes));
		return listaUsuariosConElMismoNombreyCondicionesPreexistentes;
	}

	public boolean matchConditions(Usuario usuarioConMismoNombre, Usuario usuarioBaseAComparar) {

		return (usuarioConMismoNombre.getCondicionesPreexistentes().containsAll(usuarioBaseAComparar.getCondicionesPreexistentes()))
				&& (usuarioConMismoNombre.getCondicionesPreexistentes().size() == usuarioBaseAComparar.getCondicionesPreexistentes().size());
	}

	public void addToUsersList(Usuario usuarioConMismoNombre, Usuario usuarioAComparar, List<Usuario> lista) {

		if (this.matchConditions(usuarioConMismoNombre, usuarioAComparar)) {
			lista.add(usuarioConMismoNombre);
		}
	}

	/* para que no rompa TestAdministrador: setObject viene de CollectionBasedHome */
	public void setObjects(List<Usuario> listaUsuarios) {
		for (Usuario usuario : listaUsuarios) {
			this.saveOrUpdate(usuario);
		}
	}

	@Override
	protected Class<Usuario> getEntityType() {
		return Usuario.class;
	}

	@Override
	protected void addCriteriaToSearchByExample(Criteria criteria, Usuario usuario) {
		if (usuario.getNombre() != null) {
			criteria.add(Restrictions.eq("nombre", usuario.getNombre()));
		}
	}
}
