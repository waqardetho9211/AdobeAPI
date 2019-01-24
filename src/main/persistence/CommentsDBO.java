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

import main.business.comments.Comment;
import main.business.comments.CommentsDAO;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class CommentsDBO implements CommentsDAO{
	MongoCollection<Comment> collection;
	String uri = "mongodb+srv://admin:`dxefRRvbr36.3#UBRK\\tNh@cluster0-1ng4z.mongodb.net/admin";

	MongoClientURI clientURI = new MongoClientURI(uri);
	//MongoClient mongoClient;
	MongoClient mongoClient = new MongoClient(clientURI);
	MongoDatabase mongoDatabase = mongoClient.getDatabase("AdobeAPI"); 

	public CommentsDBO() {

		CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
				fromProviders(PojoCodecProvider.builder().automatic(true).build()));
		mongoClient = new MongoClient(uri,
				MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build());

		mongoDatabase = mongoDatabase.withCodecRegistry(pojoCodecRegistry);
		collection = mongoDatabase.getCollection("Comment", Comment.class);
		
		System.out.println("Database Connected");

	}
	@Override
	public void insertComment(Comment commentBO) {
		collection.insertOne(new Comment(commentBO.getUser(), commentBO.getComment()));		
	}

	@Override
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
