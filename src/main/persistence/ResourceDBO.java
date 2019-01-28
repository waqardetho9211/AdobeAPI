package main.persistence;

import static com.mongodb.client.model.Filters.eq;
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

import main.business.resources.Resource;
import main.business.resources.ResourceDAO;


public class ResourceDBO implements ResourceDAO {
	
	private MongoCollection<Resource> collection;
	
	// ToDo Fix twice initialization of MongoClient
	private Properties properties = getApplicationProperties();
	private MongoClientURI clientURI = new MongoClientURI(properties.get("application.uri").toString());

	private MongoClient mongoClient = new MongoClient(clientURI);
	private MongoDatabase mongoDatabase = mongoClient.getDatabase(properties.get("application.db").toString());

	public ResourceDBO() {
		final CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
				fromProviders(PojoCodecProvider.builder().automatic(true).build()));
		mongoClient = new MongoClient(properties.get("application.uri").toString(),
				MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build());

		mongoDatabase = mongoDatabase.withCodecRegistry(pojoCodecRegistry);
		collection = mongoDatabase.getCollection("Resource", Resource.class);
		
		//ToDo print info logs
		System.out.println("Database Connected");
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public List<Resource> getAllResourcePath(final String path) {
		final List<Resource> result = new ArrayList<Resource>();
		final Block<Resource> resourceBlock = new Block<Resource>() {
		    @Override
		    public void apply(final Resource resource) {
		    	result.add(resource);
		    }
		};
		collection.find(eq("path", path)).forEach(resourceBlock);
		return result;
	}

	@Override
	public void insertResource(final Resource resource) {
		collection.insertOne(new Resource(resource.getName(), resource.getType(), resource.getPath(), resource.getLocation()));
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<Resource> getAllResources() {
		final List<Resource> result = new ArrayList<Resource>();
		final Block<Resource> resourceBlock = new Block<Resource>() {
		    @Override
		    public void apply(final Resource resource) {
		    	result.add(resource);
		    }
		};
		collection.find().forEach(resourceBlock);
		return result;
	}

	@Override
	public Resource getAResource(final String name) {
		final Resource resource = collection.find(eq("name", name)).first();
		return resource;
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
