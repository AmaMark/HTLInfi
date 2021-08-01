package at.htlinn.infidb.mongodb.client.test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.bson.BSONObject;
import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Sorts;

public class Client {

	private MongoClient mongoClient;
	private MongoCollection<Document> collection;
	
	public static void main(String[] args) {
		new Client().runSampleCode();
	}
	
	private void runSampleCode() {
		// You can specify the ConnectionString:
		// MongoClient mongoClient = MongoClients.create("mongodb://hostOne:27017");
		
		try {
			// Instantiate a MongoClient object without any parameters to connect to a MongoDB instance 
			// running on  localhost on port 27017:
			mongoClient = MongoClients.create();
			
			List<String> dbNames = getDBNames();
			System.out.println("dbNames: " + dbNames);
			
			for( String dbName: dbNames) {
				List<String> dbCollections = getDBCollections(dbName);
				System.out.println(dbName + " dbCollections: " + dbCollections);	
			}
			
			// 
			MongoDatabase db = mongoClient.getDatabase("infiG1beispiel1");
			collection = db.getCollection("persons");
			
			insertTestPerson();
			queryAll();
			
			insertNameAsJSON("Tom");
			queryAllToms();
			
		} finally {
			
		}
	}
	
	private void insertNameAsJSON(final String name) {
		// "name": "Test",
		final String insertAsJsonString = "{ \"name\": \"" + name + "\"" + ", \"friend\": \"Jerry\"}";
		final Document personDocument = Document.parse(insertAsJsonString);
		collection.insertOne(personDocument);
	}
	
	private void queryAllToms() {
		final String queryAsJsonString = "{ \"name\": \"Tom\" }";
		final Document query = Document.parse(queryAsJsonString);
		final FindIterable<Document> results = collection.find(query);
		printAll(results);
	}

	private void insertTestPerson() {
		final Document personDocument = 
				new Document("name", "Kurt")
				.append("age", "66")
				.append("bornIn", "Wien")
				.append("livesIn", "Innsbruck");
		
		collection.insertOne(personDocument);
	}
	
	private void queryAll() {
		// sort(new BasicDBObject("name",1))
		FindIterable<Document> results = collection.find().sort(Sorts.ascending("name"));		
		printAll(results);
	}
	
	private void printAll(final FindIterable<Document> elements) {
		final Consumer<Document> consumer = System.out::println;
		elements.forEach(consumer);
	}
	
	private List<String> getDBNames() {
	    MongoIterable<String> iterable = mongoClient.listDatabaseNames();
	    List<String> dbNames = iterable.into(new ArrayList<>());
	    return dbNames;
	}
	
	private List<String> getDBCollections(String DBName) {
		MongoDatabase database = mongoClient.getDatabase(DBName);
	    MongoIterable<String> iterable = database.listCollectionNames();
	    MongoCursor<String> iterator = iterable.iterator();
	    
	    List<String> dbCollections = new ArrayList<>();
	    while( iterator.hasNext() ) {
	    	dbCollections.add(iterator.next());
		}
	    return dbCollections;
	}
}
