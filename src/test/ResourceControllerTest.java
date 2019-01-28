package test;

import static org.hamcrest.CoreMatchers.containsString;
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

import main.business.resources.Resource;
import main.business.resources.ResourceDAO;
import main.business.resources.ResourceTypes;
import main.persistence.ResourceDBO;

class ResourceControllerTest {

	@After
	void deleteAllTheRecordsCreated() {
		// ToDo Write this method delete all records created during the test
	}

	@Test
	void shouldShowNoContentResponse() {

		final ClientConfig config = new ClientConfig();
		final Client client = ClientBuilder.newClient(config);
		final WebTarget target = client.target(getBaseURI());

		final Response response = target.path("rest/resource").queryParam("name", "random").request()
				.accept(MediaType.APPLICATION_JSON).get(Response.class);

		assertEquals(response.getStatus(), 204);
	}

	@Test
	void shouldShowOkResponse() {

		final Resource resource = new Resource("index.html", ResourceTypes.File, "main", "root/user1");
		final ResourceDAO resourceDAO = new ResourceDBO();
		resourceDAO.insertResource(resource);

		final ClientConfig config = new ClientConfig();
		final Client client = ClientBuilder.newClient(config);
		final WebTarget target = client.target(getBaseURI());

		final Response response = target.path("rest/resource").queryParam("name", resource.getName()).request()
				.accept(MediaType.APPLICATION_JSON).get(Response.class);

		assertEquals(response.getStatus(), 200); 
	}

	@Test
	void shouldReturnResource() {

		final ClientConfig config = new ClientConfig();
		final Client client = ClientBuilder.newClient(config);
		final WebTarget target = client.target(getBaseURI());

		final Resource aRandomResource = new Resource("content.pdf", ResourceTypes.File, "main/documents", "root/user1");
		final ResourceDAO resourceDAO = new ResourceDBO();
		resourceDAO.insertResource(aRandomResource);
		final Response response = target.path("rest/resource").queryParam("name", aRandomResource.getName()).request()
				.accept(MediaType.APPLICATION_JSON).get(Response.class);

		final String output = response.readEntity(String.class);
		assertThat(output, containsString(aRandomResource.getName()));
		assertThat(output, containsString(aRandomResource.getType().toString()));
		assertThat(output, containsString(aRandomResource.getPath()));
		assertThat(output, containsString(aRandomResource.getLocation()));
	}

	@Test
	void shouldShowSuccessResponseWhenANewResource() {

		final ClientConfig config = new ClientConfig();
		final Client client = ClientBuilder.newClient(config);
		final WebTarget target = client.target(getBaseURI());

		final Form form = new Form();
		form.param("name", "documents");
		form.param("type", "Directory");
		form.param("path", "main");
		form.param("location", "root/user1");

		final Response response = target.path("rest").path("resource").request()
				.post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED), Response.class);
		assertEquals(response.getStatus(), 200);
	}

	@Test
	void shouldReturnResourceWhenANewResourcePosted() {

		final ClientConfig config = new ClientConfig();
		final Client client = ClientBuilder.newClient(config);
		final WebTarget target = client.target(getBaseURI());

		final Resource resource = new Resource("exams.pdf", ResourceTypes.File, "main/documents", "root");

		final Form form = new Form();
		form.param("name", resource.getName());
		form.param("type", resource.getType().toString());
		form.param("path", resource.getPath());
		form.param("location", resource.getLocation());

		final Response response = target.path("rest").path("resource").request()
				.post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED), Response.class);

		final Resource resourceResult = response.readEntity(Resource.class);
		assertEquals(resourceResult.getName(), resource.getName());
		assertEquals(resourceResult.getType(), resource.getType());
		assertEquals(resourceResult.getPath(), resource.getPath());
		assertEquals(resourceResult.getLocation(), resource.getLocation());
	}
	
	@Test
	void shouldGetETagFromHTTPHeader() {
		final ClientConfig config = new ClientConfig();
		final Client client = ClientBuilder.newClient(config);
		final WebTarget target = client.target(getBaseURI());
		
		final Resource aRandomResource = new Resource("flex.yaml", ResourceTypes.File, "main/documents", "root/user8");
		final ResourceDAO resourceDAO = new ResourceDBO();
		resourceDAO.insertResource(aRandomResource);
		
		final Response response = target.path("rest/resource").queryParam("name", aRandomResource.getName()).request()
				.accept(MediaType.APPLICATION_JSON).get(Response.class);
		
		final String eTag = response.getHeaderString("ETag");
		assertNotNull(eTag);
	}
	
	@Test
	void ShouldReturnNotModifiedResponse() {
		
		 // If-None-match test
		final ClientConfig config = new ClientConfig();
		final Client client = ClientBuilder.newClient(config);
		final WebTarget target = client.target(getBaseURI());
		
		final Resource aRandomResource = new Resource("next_gen.html", ResourceTypes.File, "main/html", "root/user6");
		final ResourceDAO resourceDAO = new ResourceDBO();
		resourceDAO.insertResource(aRandomResource);
		
		final Response response1 = target.path("rest/resource").queryParam("name", aRandomResource.getName()).request()
				.get(Response.class);
		
		final String eTag = response1.getHeaderString("ETag");
		
		final Response response2 = target.path("rest/resource").queryParam("name", aRandomResource.getName()).request().header("ETag",eTag)
				.get(Response.class);
		assertEquals(response1.getStatus(), 200);
		// ToDo not working fix this
		assertEquals(response2.getStatus(), 304);
	} 

	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8080/AdobeAPI/").build();
	}

}
