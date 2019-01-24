package main.persistence;

import java.util.ArrayList;
import java.util.List;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import main.business.Comment;

public class CommentsDBA {
	MongoCollection<Comment> collection;
	String uri = "mongodb+srv://admin:`dxefRRvbr36.3#UBRK\\tNh@cluster0-1ng4z.mongodb.net/admin";

	MongoClientURI clientURI = new MongoClientURI(uri);
	//MongoClient mongoClient;
	MongoClient mongoClient = new MongoClient(clientURI);
	MongoDatabase mongoDatabase = mongoClient.getDatabase("AdobeAPI");

	public CommentsDBA() {

		CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
				fromProviders(PojoCodecProvider.builder().automatic(true).build()));
		mongoClient = new MongoClient(uri,
				MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build());

		mongoDatabase = mongoDatabase.withCodecRegistry(pojoCodecRegistry);
		collection = mongoDatabase.getCollection("Comment", Comment.class);
		
		System.out.println("Database Connected");

	}

	public void insertComment(Comment commentBO) {
		collection.insertOne(new Comment(commentBO.getUser(), commentBO.getComment()));		

	}

	@SuppressWarnings("deprecation")
	public List<Comment> getAllComments() {
		List<Comment> result = new ArrayList<Comment>();
		Block<Comment> printBlock = new Block<Comment>() {
		    @Override
		    public void apply(final Comment comment) {
		    	result.add(comment);
		    }
		};

		collection.find().forEach(printBlock);
		return result;
	}

}
