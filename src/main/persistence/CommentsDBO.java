package main.persistence;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

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

	// ToDo Fix twice initialization of MongoClient
	private Properties properties = getApplicationProperties();
	private MongoClientURI clientURI = new MongoClientURI(properties.get("application.uri").toString());

	private MongoClient mongoClient = new MongoClient(clientURI);
	private MongoDatabase mongoDatabase = mongoClient.getDatabase(properties.get("application.db").toString()); 

	public CommentsDBO() {

		final CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
				fromProviders(PojoCodecProvider.builder().automatic(true).build()));
		mongoClient = new MongoClient(properties.get("application.uri").toString(),
				MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build());

		mongoDatabase = mongoDatabase.withCodecRegistry(pojoCodecRegistry);
		collection = mongoDatabase.getCollection("Comment", Comment.class);
		
		//ToDo print info logs
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
		final Block<Comment> commentBlock = new Block<Comment>() {
		    @Override
		    public void apply(final Comment comment) {
		    	result.add(comment);
		    }
		};
		collection.find().forEach(commentBlock);
		return result;
	}
	
	private Properties getApplicationProperties() {
		// Boilerplate code ToDo create a universal properties loader
		final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		final InputStream input = classLoader.getResourceAsStream("./main/resources/conf/application.properties");
		// ...
		final Properties properties = new Properties();
		try {
			properties.load(input);
		} catch (IOException e) {
			e.printStackTrace();
			// ToDo print logs
		}
		return properties;
	}

}
