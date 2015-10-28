package dds.javatar.app.db;

import static com.mongodb.client.model.Filters.eq;

import java.util.HashSet;
import java.util.Set;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

public abstract class DBContentProvider<T> {
		
		protected String collectionName;  
	
		/** Singleton that represent the DB client **/
		private static MongoClient clientInstance = new MongoClient( "localhost" , 27017 );
		
		public static MongoClient getClient() {
			if (clientInstance == null) {
				clientInstance = new MongoClient();

			}
			return clientInstance;
		}
	
		/** Get the instance of the database **/
		public MongoDatabase getDB() {
			
			return clientInstance.getDatabase("DDS");
		}
		
		/** Get a particular collection **/
		public MongoCollection<Document> getCollection() {
			
			return getDB().getCollection(this.collectionName);			
		}
		
		/** To be applied by subclasses **/
		public abstract Document create(T t);
		
		public abstract Document createFilter(T t);
		
		public abstract T map(Document bson);
				
		/** Basic CRUD **/
		public void insert(T t) {
			Document bson = create(t);
			this.getCollection().insertOne(bson);
		}
		
		public Set<T> findAll() {
			
			Set<Document> allDocuments = new HashSet<Document>(); 
			
			Set<T> resultItems = new HashSet<T>();
			
			MongoCursor<Document> cursor = this.getCollection().find().iterator();
			try {
				while (cursor.hasNext()) {
					allDocuments.add(cursor.next());
				}
			} finally {
				cursor.close();
			}
			
			/** Map all items to its entity type **/
			allDocuments.forEach(doc -> {
						T t = map(doc);
						resultItems.add(t);
			});
			
			return resultItems;

		}
		
		public Document findByName(String value) {
			
			return this.getCollection().find(eq("name", value)).first();
		}
		
		public Document findByUsername(String value) {
			
			return this.getCollection().find(eq("username", value)).first();
		}
		
		public void update(T t, String key, Object newValue) {
			
			Document query = createFilter(t);
			this.getCollection().updateOne(query, new Document("$set", new Document(key, newValue)));
		}
		
		public void delete(T t) {
			
			Document query = create(t);
			this.getCollection().deleteOne(query);
		}
}
