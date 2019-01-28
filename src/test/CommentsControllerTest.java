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

		final ClientConfig config = new ClientConfig();
		final Client client = ClientBuilder.newClient(config);
		final WebTarget target = client.target(getBaseURI());

		final Form form = new Form();
		form.param("name", "Test Person");
		form.param("comment", "This is a comment on the wall");

		final Response response = target.path("rest").path("comment").request()
				.post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED), Response.class);
		assertEquals(response.getStatus(), 200);
	}

	@Test
	void shouldReturnComments() {

		final ClientConfig config = new ClientConfig();
		final Client client = ClientBuilder.newClient(config);
		final WebTarget target = client.target(getBaseURI());
		
		final String nameOfTestUser = "Test Person";
		final String commentOfTestUser = "This is a test comment";

		final Form form = new Form();
		form.param("name", nameOfTestUser);
		form.param("comment", commentOfTestUser);

		final Response response = target.path("rest").path("comment").request()
				.post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED), Response.class);

		final String htmlString = response.readEntity(String.class);
		assertThat(htmlString, containsString(nameOfTestUser));
		assertThat(htmlString, containsString(commentOfTestUser));

	}
	@Test
	void shouldGetComments() {
		final ClientConfig config = new ClientConfig();
		final Client client = ClientBuilder.newClient(config);
		final WebTarget target = client.target(getBaseURI());
		
		final Response response = target.path("rest").path("comment").request()
				.get(Response.class);

		final String htmlString = response.readEntity(String.class);
		assertNotEquals( "", htmlString );

	}
	@Test
	void shouldGetLastModifiedFromHTTPHeader() {
		final ClientConfig config = new ClientConfig();
		final Client client = ClientBuilder.newClient(config);
		final WebTarget target = client.target(getBaseURI());
		
		final Response response = target.path("rest").path("comment").request()
				.get(Response.class);
		
		final String lastModified = response.getHeaderString("Last-Modified");
		assertNotEquals(lastModified, "");

	}
	
	@Test
	void LastModifiedShoudBeTheSame() {
		final ClientConfig config = new ClientConfig();
		final Client client = ClientBuilder.newClient(config);
		final WebTarget target = client.target(getBaseURI());
		
		final Response response1 = target.path("rest").path("comment").request()
				.get(Response.class);
		
		final Response response2 = target.path("rest").path("comment").request()
				.get(Response.class);
		
		final String lastModified1 = response1.getHeaderString("Last-Modified");
		final String lastModified2 = response2.getHeaderString("Last-Modified");
		
		assertEquals(lastModified1, lastModified2);
	}
	
	@Test
	void ShouldReturnResponseAsNotModified() {
		
		 // If-Modified-Since
		final ClientConfig config = new ClientConfig();
		final Client client = ClientBuilder.newClient(config);
		final WebTarget target = client.target(getBaseURI());
		
		final Response response1 = target.path("rest").path("comment").request()
				.get(Response.class);
		
		final String lastModified1 = response1.getHeaderString("Last-Modified");
		
		final Response response2 = target.path("rest").path("comment").request().header("if-modified-since",lastModified1)
				.get(Response.class);
		assertEquals(response2.getStatus(), 304);
	}

	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8080/AdobeAPI/").build();
	}

}
