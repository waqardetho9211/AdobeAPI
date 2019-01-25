package main.persistence;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

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

import main.business.comments.Comment;
import main.business.comments.CommentsDAO;

public class CommentsDBO implements CommentsDAO{
	private MongoCollection<Comment> collection;
	private String uri = "mongodb+srv://admin:<password>@cluster0-1ng4z.mongodb.net/admin";

	// ToDo Fix twice initialization of MongoClient
	MongoClientURI clientURI = new MongoClientURI(uri); 
	MongoClient mongoClient = new MongoClient(clientURI);
	MongoDatabase mongoDatabase = mongoClient.getDatabase("AdobeAPI"); 

	public CommentsDBO() {

		final CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
				fromProviders(PojoCodecProvider.builder().automatic(true).build()));
		mongoClient = new MongoClient(uri,
				MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build());

		mongoDatabase = mongoDatabase.withCodecRegistry(pojoCodecRegistry);
		collection = mongoDatabase.getCollection("Comment", Comment.class);
		
		System.out.println("Database Connected");

	}
	@Override
	public void insertComment(final Comment commentBO) {
		collection.insertOne(new Comment(commentBO.getUser(), commentBO.getComment()));		
	}

	@Override
	@SuppressWarnings("deprecation")
	public List<Comment> getAllComments() {
		final List<Comment> result = new ArrayList<Comment>();
		final Block<Comment> printBlock = new Block<Comment>() {
		    @Override
		    public void apply(final Comment comment) {
		    	result.add(comment);
		    }
		};
		collection.find().forEach(printBlock);
		return result;
	}

}
