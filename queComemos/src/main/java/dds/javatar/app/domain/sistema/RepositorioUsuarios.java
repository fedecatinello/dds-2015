package dds.javatar.app.domain.sistema;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dds.javatar.app.db.RepoDefault;
import dds.javatar.app.domain.usuario.Usuario;

public class RepositorioUsuarios extends RepoDefault<Usuario> {

	public RepositorioUsuarios() {
		super("usuarios", Usuario.class);
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

	public Usuario getByUsername(String username) {
		try {
			List<Usuario> listaUsers = this.getAll();
			return listaUsers.stream().filter(s -> s.getUser().equals(username)).map(p -> p).collect(Collectors.toList()).get(0);
		} catch (Exception e) {
			return null; // revianta cuando no encuentra nada
		}
	}

	public Usuario getByCredential(String username, String password) {
		try {
			List<Usuario> listaUsers = this.getAll();
			return listaUsers
				.stream()
				.filter(s -> s.getUser().equals(username) && s.getPassword().equals(password))
				.map(p -> p)
				.collect(Collectors.toList())
				.get(0);
		} catch (Exception e) {
			return null; // revianta cuando no encuentra nada
		}
	}

	public List<Usuario> searchByName(Usuario usuario) {
		List<Usuario> listaUsuariosConElMismoNombre = new ArrayList<Usuario>();
		for (Usuario usuarioEnSistema : this.getAll()) {
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

}
