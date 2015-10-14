package dds.javatar.app.dto.sistema;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

import dds.javatar.app.dto.grupodeusuarios.GrupoDeUsuarios;
import dds.javatar.app.dto.receta.Componente;
import dds.javatar.app.dto.receta.Paso;
import dds.javatar.app.dto.receta.PreferenciaGrupo;
import dds.javatar.app.dto.receta.PreferenciaUsuario;
import dds.javatar.app.dto.receta.Receta;
import dds.javatar.app.dto.usuario.Usuario;
import dds.javatar.app.dto.usuario.condiciones.Celiaco;
import dds.javatar.app.dto.usuario.condiciones.CondicionPreexistente;
import dds.javatar.app.dto.usuario.condiciones.Diabetico;
import dds.javatar.app.dto.usuario.condiciones.Hipertenso;
import dds.javatar.app.dto.usuario.condiciones.Vegano;

@SuppressWarnings("unchecked")
public abstract class RepoDefault<T> {

	protected static final SessionFactory sessionFactory = new AnnotationConfiguration()
		.configure()
		.addAnnotatedClass(Receta.class)
		.addAnnotatedClass(Usuario.class)
		.addAnnotatedClass(CondicionPreexistente.class)
		.addAnnotatedClass(Celiaco.class)
		.addAnnotatedClass(Diabetico.class)
		.addAnnotatedClass(Hipertenso.class)
		.addAnnotatedClass(Vegano.class)
		.addAnnotatedClass(Componente.class)
		.addAnnotatedClass(GrupoDeUsuarios.class)
		.addAnnotatedClass(Paso.class)
		.addAnnotatedClass(PreferenciaGrupo.class)
		.addAnnotatedClass(PreferenciaUsuario.class)
		.buildSessionFactory();

	protected Session openSession() {
		return sessionFactory.openSession();
	}

	public List<T> allInstances() {
		Session session = sessionFactory.openSession();
		try {
			return session.createCriteria(this.getEntityType()).list();
		} finally {
			session.close();
		}
	}

	protected abstract Class<T> getEntityType();

	protected abstract void addCriteriaToSearchByExample(Criteria criteria, T t);

	public List<T> searchByExample(T t) {
		Session session = sessionFactory.openSession();
		try {
			Criteria criteria = session.createCriteria(this.getEntityType());
			this.addCriteriaToSearchByExample(criteria, t);
			return criteria.list();
		} catch (HibernateException e) {
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
	}

	public List<T> getAll() {
		Session session = sessionFactory.openSession();
		try {
			Criteria criteria = session.createCriteria(this.getEntityType());
			return criteria.list();
		} catch (HibernateException e) {
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
	}

	public void saveOrUpdate(T t) {
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			session.saveOrUpdate(t);
			session.getTransaction().commit();
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
	}

	public void delete(T t) {
		Session session = sessionFactory.openSession();
		try {
			session.beginTransaction();
			session.delete(t);
			session.getTransaction().commit();
		} catch (HibernateException ex) {
			session.getTransaction().rollback();
			throw new RuntimeException(ex);
		} finally {
			session.close();
		}
	}

	public void update(T t) {
		this.saveOrUpdate(t);
	}

	public void add(T t) {
		this.saveOrUpdate(t);
	}
}
