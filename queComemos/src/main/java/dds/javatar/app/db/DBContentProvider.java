package dds.javatar.app.db;

import java.util.HashSet;
import java.util.Set;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.*;

public class DBContentProvider {

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
		public MongoCollection<Document> getCollection(String collectionName) {
			
			return getDB().getCollection(collectionName);			
		}
		
		/** To be applied by subclasses **/
		public Document createDocument() {
			return null;
		}
		
		/** Basic CRUD **/
		public void insert(Document bson, String collectionName) {
			
			this.getCollection(collectionName).insertOne(bson);
		}
		
		public Set<Document> findAll(String collectionName) {
			
			Set<Document> allDocuments = new HashSet<Document>(); 
			
			MongoCursor<Document> cursor = this.getCollection(collectionName).find().iterator();
			try {
				while (cursor.hasNext()) {
					allDocuments.add(cursor.next());
				}
			} finally {
				cursor.close();
			}
			
			return allDocuments;

		}
		
		public Document findByName(String collectionName, String value) {
			
			return this.getCollection(collectionName).find(eq("name", value)).first();
		}
		
		public Document findByUsername(String collectionName, String value) {
			
			return this.getCollection(collectionName).find(eq("username", value)).first();
		}
		
		public void update(String collectionName, Document query, String key, Object newValue) {
			
			this.getCollection(collectionName).updateOne(query, new Document("$set", new Document(key, newValue)));
		}
		
		public void delete(String collectionName, Document query) {
			
			this.getCollection(collectionName).deleteOne(query);
		}
}
