package dds.javatar.app.dto.sistema;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections15.Predicate;
import org.uqbar.commons.model.CollectionBasedHome;

import dds.javatar.app.dto.usuario.Usuario;

public class RepositorioUsuarios extends CollectionBasedHome<Usuario> {

	private static RepositorioUsuarios instance;

	public static RepositorioUsuarios getInstance() {
		if (instance == null) {
			instance = new RepositorioUsuarios();
		}
		return instance;
	}

	public void add(Usuario usuario) {
		this.effectiveCreate(usuario);
	}

	public void remove(Usuario usuario) {
		this.effectiveDelete(usuario);
	}

	public void updateUsuario(Usuario usuario) {
		update(usuario);
	}

	public Usuario get(Usuario Usuario) {
		return this.searchByName(Usuario).get(0);
	}

	public Usuario getByUsername(String username) {
		try {
			List<Usuario> listaUsers = super.getObjects();
			return listaUsers.stream().filter(s -> s.getUser().equals(username)).map(p-> (Usuario) p).collect(Collectors.toList()).get(0);
		} catch (Exception e) {
			return null;	// revianta cuando no encuentra nada
		}
		}
	
	public Usuario getByCredential(String username, String password) {
		try {
			List<Usuario> listaUsers = super.getObjects();
			return listaUsers.stream().filter(s -> s.getUser().equals(username) && s.getPassword().equals(password)).map(p-> (Usuario) p).collect(Collectors.toList()).get(0);
		} catch (Exception e) {
			return null;	// revianta cuando no encuentra nada
		}
		}
	
	public List<Usuario> searchByName(Usuario usuario) {
		List<Usuario> listaUsuariosConElMismoNombre = new ArrayList<Usuario>();
		for (Usuario usuarioEnSistema : super.getObjects()) {
			if (usuarioEnSistema.getNombre().equals(usuario.getNombre())) {
				listaUsuariosConElMismoNombre.add(usuarioEnSistema);
			}
		}
		return listaUsuariosConElMismoNombre;
	}
	
	public List<Usuario> list(Usuario usuario) {
		List<Usuario> listaUsuariosConElMismoNombreyCondicionesPreexistentes = this.searchByName(usuario);
		listaUsuariosConElMismoNombreyCondicionesPreexistentes.forEach(usuarioConElMismoNombre -> addToUsersList(usuarioConElMismoNombre, usuario, listaUsuariosConElMismoNombreyCondicionesPreexistentes));
		return listaUsuariosConElMismoNombreyCondicionesPreexistentes;
	}

	public boolean matchConditions(Usuario usuarioConMismoNombre, Usuario usuarioBaseAComparar) {
		
		return (usuarioConMismoNombre.getCondicionesPreexistentes().containsAll(usuarioBaseAComparar.getCondicionesPreexistentes()))
		&& (usuarioConMismoNombre.getCondicionesPreexistentes().size() == usuarioBaseAComparar.getCondicionesPreexistentes().size());
	}
	
	public void addToUsersList(Usuario usuarioConMismoNombre, Usuario usuarioAComparar,  List<Usuario> lista) {
		
		if (matchConditions(usuarioConMismoNombre, usuarioAComparar)) {
			lista.add(usuarioConMismoNombre);
		}
	}
	
	@Override
	public Class<Usuario> getEntityType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario createExample() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Predicate<?> getCriterio(Usuario example) {
		// TODO Auto-generated method stub
		return null;
	}

}
