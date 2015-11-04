package dds.javatar.app.db;

import java.net.UnknownHostException;
import java.util.List;

import com.despegar.integration.mongo.connector.MongoCollection;
import com.despegar.integration.mongo.connector.MongoCollectionFactory;
import com.despegar.integration.mongo.connector.MongoDBConnection;
import com.despegar.integration.mongo.entities.GenericIdentifiableEntity;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class RepoDefault<T extends GenericIdentifiableEntity<String>> {

	protected MongoCollection<T> collection;

	public RepoDefault(String collectionName, Class<T> t) {
		try {
			MongoDBConnection connection = new MongoDBConnection("utn-dds", "localhost:27017");

			ObjectMapper mongoMapper = new ObjectMapper();
			mongoMapper.setVisibilityChecker(mongoMapper
				.getSerializationConfig()
				.getDefaultVisibilityChecker()
				.withFieldVisibility(Visibility.ANY)
				.withGetterVisibility(Visibility.NONE)
				.withSetterVisibility(Visibility.NONE)
				.withCreatorVisibility(Visibility.NONE));
			MongoCollectionFactory factory = new MongoCollectionFactory(connection);
			factory.setMapper(mongoMapper);
			this.collection = factory.buildMongoCollection(collectionName, t);
		} catch (UnknownHostException e) {
			throw new RuntimeException(e);
		}
	}

	/*
	 * public List<T> searchByExample(T t) { Session session = sessionFactory.openSession(); try { Criteria criteria =
	 * session.createCriteria(this.getEntityType()); this.addCriteriaToSearchByExample(criteria, t); return criteria.list(); }
	 * catch (HibernateException e) { throw new RuntimeException(e); } finally { session.close(); } }
	 */
	public List<T> getAll() {
		return this.collection.find();
	}

	public void saveOrUpdate(T t) {
		this.collection.save(t);
	}

	public void delete(T t) {
		this.collection.remove(t.getId());
	}

	public void deleteAll() {
		this.collection.removeAll();
	}

	public void update(T t) {
		this.saveOrUpdate(t);
	}

	public void add(T t) {
		this.saveOrUpdate(t);
	}
}
