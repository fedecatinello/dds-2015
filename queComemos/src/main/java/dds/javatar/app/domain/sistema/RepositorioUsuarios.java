package dds.javatar.app.domain.sistema;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.despegar.integration.mongo.connector.MongoCollection;

import dds.javatar.app.db.DBContentProvider;
import dds.javatar.app.domain.receta.Receta;
import dds.javatar.app.domain.usuario.Usuario;

public class RepositorioUsuarios extends DBContentProvider<Usuario> {

	private static final String collection_name = "usuarios";
	private static MongoCollection<Usuario> usuarios_collection;
	
	public static MongoCollection<Usuario> getInstance() {
		if (usuarios_collection == null) {
			RepositorioUsuarios repo = new RepositorioUsuarios(); // esta bien manejarlo asi?
			usuarios_collection = repo.buildCollection(collection_name, Usuario.class);
		}
		return usuarios_collection;
	}

	public void add(Usuario usuario) {
		this.insert(usuarios_collection, usuario);	
	}

	public void remove(Usuario usuario) {
		this.deleteByName(usuarios_collection, usuario.getNombre());
	}

	public void updateUsuario(Usuario usuario) {
		this.update(usuarios_collection, usuario);		
	}

	public Usuario get(Usuario Usuario) {
		return this.searchByName(Usuario).get(0);
	}

	public Usuario getByUsername(String username) {
		try {
			List<Usuario> listaUsers = listarUsuarios();
			return listaUsers.stream().filter(s -> s.getUser().equals(username)).map(p-> (Usuario) p).collect(Collectors.toList()).get(0);
		} catch (Exception e) {
			return null;	// revianta cuando no encuentra nada
		}
		}
	
	public Usuario getByCredential(String username, String password) {
		try {
			List<Usuario> listaUsers = listarUsuarios();
			return listaUsers.stream().filter(s -> s.getUser().equals(username) && s.getPassword().equals(password)).map(p-> (Usuario) p).collect(Collectors.toList()).get(0);
		} catch (Exception e) {
			return null;	// revianta cuando no encuentra nada
		}
		}
	
	public List<Usuario> searchByName(Usuario usuario) {
		List<Usuario> listaUsuariosConElMismoNombre = new ArrayList<Usuario>();
		for (Usuario usuarioEnSistema : listarUsuarios()) {
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
	

	public List<Usuario> listarUsuarios() {
		return this.findAll(usuarios_collection);
	}
//	
//	@Override
//	public Class<Usuario> getEntityType() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public Usuario createExample() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	protected Predicate<?> getCriterio(Usuario example) {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
