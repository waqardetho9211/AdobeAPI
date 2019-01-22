package main.persistence.mongo;

import org.bson.Document;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Main {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) {
		String uri = "mongodb+srv://admin:admin@cluster0-1ng4z.mongodb.net/admin";
		MongoClientURI clientURI = new MongoClientURI(uri);
		MongoClient mongoClient = new MongoClient(clientURI);

		MongoDatabase mongoDatabase = mongoClient.getDatabase("AdobeAPI");
		MongoCollection collection = mongoDatabase.getCollection("Resource");

		System.out.println("Database Connected");
		Document document = new Document("xx", "yy");

		collection.insertOne(document);
	}
}