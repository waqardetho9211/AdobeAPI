package test.business.resource;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import main.business.resources.Resource;

public class ResourceDAOTest {

	private MongoCollection<Resource> collection;
	Properties properties = getApplicationProperties();
	private MongoClientURI clientURI = new MongoClientURI(properties.get("application.uri").toString());
	private MongoClient mongoClient = new MongoClient(clientURI);
	private MongoDatabase mongoDatabase = mongoClient.getDatabase(properties.get("application.db").toString());

	@Before
	public void loadConfigurations() {
		loadDatabase();
	}

	@After
	public void deleteAllDataCreatedInDB() {
		// ToDo implement this
		System.out.println(collection);
	}

	@Test
	public void shouldInsertResource() {
		// ToDo implement this
	}
	
	@Test
	public void shouldGetAllResources() {
		// ToDo implement this
		assertEquals("", "");
	}

	private void loadDatabase() {
		final CodecRegistry pojoCodecRegistry = fromRegistries(MongoClient.getDefaultCodecRegistry(),
				fromProviders(PojoCodecProvider.builder().automatic(true).build()));
		mongoClient = new MongoClient(properties.get("application.uri").toString(),
				MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build());

		mongoDatabase = mongoDatabase.withCodecRegistry(pojoCodecRegistry);
		collection = mongoDatabase.getCollection("Resource", Resource.class);

		// ToDo print info logs
		System.out.println("Database Connected");

	}

	private Properties getApplicationProperties() {
		// Boilerplate code 
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream input = classLoader.getResourceAsStream("./main/resources/conf/application.properties");
		// ...
		Properties properties = new Properties();
		try {
			properties.load(input);
		} catch (IOException e) {
			e.printStackTrace();
			// ToDo print logs
		}
		return properties;
	}

}
