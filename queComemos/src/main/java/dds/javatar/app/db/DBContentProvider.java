package dds.javatar.app.db;

import java.net.UnknownHostException;
import java.util.List;

import com.despegar.integration.mongo.connector.MongoCollection;
import com.despegar.integration.mongo.connector.MongoCollectionFactory;
import com.despegar.integration.mongo.connector.MongoDBConnection;
import com.despegar.integration.mongo.entities.GenericIdentifiableEntity;
import com.despegar.integration.mongo.query.Query;

public abstract class DBContentProvider<T extends GenericIdentifiableEntity<?>> {
			
		MongoDBConnection connection = new MongoDBConnection("utn-dds", "localhost:27017");
		
		MongoCollectionFactory factory = new MongoCollectionFactory(connection);		
		
		/** To be applied by subclasses **/
		public MongoCollection<T> buildCollection(String collectionName, Class<T> t) {
			MongoCollection<T> collection = null;
			try{
			collection = factory.buildMongoCollection(collectionName, t);
			} catch(UnknownHostException uhex){
				
			}
			return collection;
		}
		
		/** Basic CRUD **/
		public void insert(MongoCollection<T> collection, T t) {
			collection.insertIfNotPresent(t);
		}
		
		public void insertAll(MongoCollection<T> collection, List<T> t) {
			t.forEach(x -> collection.insertIfNotPresent(x));
		}
		
		public List<T> findAll(MongoCollection<T> collection) {
			return collection.find();
		}
		
		public T findByName(MongoCollection<T> collection, String value) {
			Query query = new Query().equals("name", value);
			return collection.findOne(query);
		}
		
		public T findByUsername(MongoCollection<T> collection, String value) {
			Query query = new Query().equals("name", value);
			return collection.findOne(query);
		}
		
		/** Save method overrides the object if it already exists **/
		public void update(MongoCollection<T> collection, T t) {
			collection.save(t);
		}
		
		public void deleteById(MongoCollection<T> collection, String id) {
			Query query = new Query().equals("_id", id);
			collection.remove(query);
		}
		
		public void deleteByName(MongoCollection<T> collection, String value) {
			Query query = new Query().equals("name", value);
			collection.remove(query);
		}
		
		public void deleteAll(MongoCollection<T> collection) {
			collection.removeAll();
		}
}
