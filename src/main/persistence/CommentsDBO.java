package main.persistence;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.bson.Document;

import com.google.gson.Gson;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import main.business.Comment;

public class CommentsDBO {
	MongoCollection<Document> collection;
	private static Gson gson = new Gson();
	
	public CommentsDBO() {
		String uri = "mongodb+srv://admin:admin@cluster0-1ng4z.mongodb.net/admin";
		MongoClientURI clientURI = new MongoClientURI(uri);
		MongoClient mongoClient = new MongoClient(clientURI);

		MongoDatabase mongoDatabase = mongoClient.getDatabase("AdobeAPI");
		collection = mongoDatabase.getCollection("Comment");

		System.out.println("Database Connected");		

	}

	public List<Comment> insertComment(Comment commentBO) {
		/*
		 * Document document = new Document("UserName", commentBO.getUser());
		 * document.append("Comment", commentBO.getComment()); document.append("Date",
		 * new
		 * SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime()))
		 * ;
		 */
		
		Document document = convertToDocument(commentBO);		
		collection.insertOne(document);
		return null;
	}
	
	public static Document convertToDocument(Object rd) {
	    if (rd instanceof Document)
	        return (Document)rd;
	    String json = gson.toJson(rd);
	    Document doc = Document.parse(json); 
	    return doc;
	}
	
}
