package test;

import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.jersey.client.ClientConfig;
import org.junit.After;
import org.junit.jupiter.api.Test;
import static org.hamcrest.CoreMatchers.containsString;


class CommentsControllerTest {

	@After
	void deleteAllTheRecordsCreated() {
		//ToDo Write this method delete all records created during the test
	}
	
	@Test
	void shouldShowSuccessResponseWhenANewComment() {

		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		WebTarget target = client.target(getBaseURI());

		Form form = new Form();
		form.param("name", "Test Person");
		form.param("comment", "This is a comment on the wall");

		Response response = target.path("rest").path("comment").request()
				.post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED), Response.class);
		assertEquals(response.getStatus(), 200);
	}

	@Test
	void shouldReturnComments() {

		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		WebTarget target = client.target(getBaseURI());
		
		String nameOfTestUser = "Test Person";
		String commentOfTestUser = "This is a test comment";

		Form form = new Form();
		form.param("name", nameOfTestUser);
		form.param("comment", commentOfTestUser);

		Response response = target.path("rest").path("comment").request()
				.post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED), Response.class);

		String htmlString = response.readEntity(String.class);
		assertThat(htmlString, containsString(nameOfTestUser));
		assertThat(htmlString, containsString(commentOfTestUser));

	}
	@Test
	void shouldGetComments() {
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		WebTarget target = client.target(getBaseURI());
		
		Response response = target.path("rest").path("comment").request()
				.get(Response.class);

		String htmlString = response.readEntity(String.class);
		assertNotEquals( "", htmlString );

	}
	@Test
	void shouldGetLastModifiedFromHTTPHeader() {
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		WebTarget target = client.target(getBaseURI());
		
		Response response = target.path("rest").path("comment").request()
				.get(Response.class);
		
		String lastModified = response.getHeaderString("Last-Modified");
		assertNotEquals(lastModified, "");

	}
	
	@Test
	void LastModifiedShoudBeTheSame() {
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		WebTarget target = client.target(getBaseURI());
		
		Response response1 = target.path("rest").path("comment").request()
				.get(Response.class);
		
		Response response2 = target.path("rest").path("comment").request()
				.get(Response.class);
		
		String lastModified1 = response1.getHeaderString("Last-Modified");
		String lastModified2 = response2.getHeaderString("Last-Modified");
		
		assertEquals(lastModified1, lastModified2);
	}
	
	@Test
	void ShouldReturnResponseAsNotModified() {
		
		 // If-Modified-Since
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		WebTarget target = client.target(getBaseURI());
		
		Response response1 = target.path("rest").path("comment").request()
				.get(Response.class);
		
		String lastModified1 = response1.getHeaderString("Last-Modified");
		
		Response response2 = target.path("rest").path("comment").request().header("if-modified-since",lastModified1)
				.get(Response.class);
		assertEquals(response2.getStatus(), 304);
	}

	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8080/AdobeAPI/").build();
	}

}
