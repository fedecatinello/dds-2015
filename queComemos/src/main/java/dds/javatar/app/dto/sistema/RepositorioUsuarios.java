package dds.javatar.app.dto.sistema;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections15.Predicate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.uqbar.commons.model.CollectionBasedHome;

import dds.javatar.app.dto.usuario.Usuario;

public class RepositorioUsuarios {

	private static final SessionFactory sessionFactory = new AnnotationConfiguration()
			.configure()
			.addAnnotatedClass(Usuario.class)
			.buildSessionFactory();

	public List<Usuario> allInstances() {
		Session session = sessionFactory.openSession();
		try {
			return (List<Usuario>) session.createCriteria(Usuario.class).list();
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

	public void add(Usuario usuario) {
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			session.save(usuario);
			session.getTransaction().commit();
		} catch (HibernateException ex) {
			session.getTransaction().rollback();
			throw new RuntimeException(ex);
		} finally {
			session.close();
		}
	}

	public void delete(Usuario usuario) {
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			session.delete(usuario);
			session.getTransaction().commit();
		} catch (HibernateException ex) {
			session.getTransaction().rollback();
			throw new RuntimeException(ex);
		} finally {
			session.close();
		}
	}

	public void updateUsuario(Usuario usuario) {
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			session.update(usuario);
			session.getTransaction().commit();
		} catch (HibernateException ex) {
			session.getTransaction().rollback();
			throw new RuntimeException(ex);
		} finally {
			session.close();
		}
	}

	public Usuario get(Usuario Usuario) {
		return this.searchByName(Usuario).get(0);
	}
	
	/* las búsquedas no tienen criterio */

	public Usuario getByUsername(String username) {
		try {
			List<Usuario> listaUsers = allInstances();
			return listaUsers.stream().filter(s -> s.getUser().equals(username)).map(p-> (Usuario) p).collect(Collectors.toList()).get(0);
		} catch (Exception e) {
			return null;	// revianta cuando no encuentra nada
		}
		}
	
	public Usuario getByCredential(String username, String password) {
		try {
			List<Usuario> listaUsers = allInstances();
			return listaUsers.stream().filter(s -> s.getUser().equals(username) && s.getPassword().equals(password)).map(p-> (Usuario) p).collect(Collectors.toList()).get(0);
		} catch (Exception e) {
			return null;	// revianta cuando no encuentra nada
		}
		}
	
	public List<Usuario> searchByName(Usuario usuario) {
		List<Usuario> listaUsuariosConElMismoNombre = new ArrayList<Usuario>();
		for (Usuario usuarioEnSistema : allInstances()) {
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


	public static SessionFactory getSessionfactory() {
		return sessionFactory;
	}
	
	/* para que no rompa TestAdministrador: setObject viene de CollectionBasedHome */
	public void setObjects(List<Usuario> listaUsuarios){
		for(Usuario usuario:listaUsuarios){
			this.add(usuario);
		}
	}
}
