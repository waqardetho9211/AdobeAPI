package test;

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
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;

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

		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		WebTarget target = client.target(getBaseURI());

		final Response response = target.path("rest/resource").queryParam("name", "random").request()
				.accept(MediaType.APPLICATION_JSON).get(Response.class);

		assertEquals(response.getStatus(), 204);
	}

	@Test
	void shouldShowOkResponse() {

		Resource resource = new Resource("index.html", ResourceTypes.File, "main", "root/user1");
		ResourceDAO resourceDAO = new ResourceDBO();
		resourceDAO.insertResource(resource);

		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		WebTarget target = client.target(getBaseURI());

		final Response response = target.path("rest/resource").queryParam("name", resource.getName()).request()
				.accept(MediaType.APPLICATION_JSON).get(Response.class);

		assertEquals(response.getStatus(), 200);
	}

	@Test
	void shouldReturnResource() {

		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		WebTarget target = client.target(getBaseURI());

		Resource aRandomResource = new Resource("content.pdf", ResourceTypes.File, "main/documents", "root/user1");
		ResourceDAO resourceDAO = new ResourceDBO();
		resourceDAO.insertResource(aRandomResource);
		Response response = target.path("rest/resource").queryParam("name", aRandomResource.getName()).request()
				.accept(MediaType.APPLICATION_JSON).get(Response.class);

		String output = response.readEntity(String.class);
		assertThat(output, containsString(aRandomResource.getName()));
		assertThat(output, containsString(aRandomResource.getType().toString()));
		assertThat(output, containsString(aRandomResource.getPath()));
		assertThat(output, containsString(aRandomResource.getLocation()));
	}

	@Test
	void shouldShowSuccessResponseWhenANewResource() {

		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		WebTarget target = client.target(getBaseURI());

		Form form = new Form();
		form.param("name", "documents");
		form.param("type", "Directory");
		form.param("path", "main");
		form.param("location", "root/user1");

		Response response = target.path("rest").path("resource").request()
				.post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED), Response.class);
		assertEquals(response.getStatus(), 200);
	}

	@Test
	void shouldReturnResourceWhenANewResourcePosted() {

		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		WebTarget target = client.target(getBaseURI());

		Resource resource = new Resource("exams.pdf", ResourceTypes.File, "main/documents", "root");

		Form form = new Form();
		form.param("name", resource.getName());
		form.param("type", resource.getType().toString());
		form.param("path", resource.getPath());
		form.param("location", resource.getLocation());

		Response response = target.path("rest").path("resource").request()
				.post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED), Response.class);

		Resource resourceResult = response.readEntity(Resource.class);
		assertEquals(resourceResult.getName(), resource.getName());
		assertEquals(resourceResult.getType(), resource.getType());
		assertEquals(resourceResult.getPath(), resource.getPath());
		assertEquals(resourceResult.getLocation(), resource.getLocation());
	}
	
	@Test
	void shouldGetETagFromHTTPHeader() {
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		WebTarget target = client.target(getBaseURI());
		
		Resource aRandomResource = new Resource("flex.yaml", ResourceTypes.File, "main/documents", "root/user8");
		ResourceDAO resourceDAO = new ResourceDBO();
		resourceDAO.insertResource(aRandomResource);
		
		Response response = target.path("rest/resource").queryParam("name", aRandomResource.getName()).request()
				.accept(MediaType.APPLICATION_JSON).get(Response.class);
		
		String eTag = response.getHeaderString("ETag");
		assertNotNull(eTag);
	}
	
	@Test
	void ShouldReturnNotModifiedResponse() {
		
		 // If-Modified-Since
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		WebTarget target = client.target(getBaseURI());
		
		Resource aRandomResource = new Resource("next_gen.html", ResourceTypes.File, "main/html", "root/user6");
		ResourceDAO resourceDAO = new ResourceDBO();
		resourceDAO.insertResource(aRandomResource);
		
		Response response1 = target.path("rest/resource").queryParam("name", aRandomResource.getName()).request()
				.get(Response.class);
		
		String eTag = response1.getHeaderString("ETag");
		
		Response response2 = target.path("rest/resource").queryParam("name", aRandomResource.getName()).request().header("ETag",eTag)
				.get(Response.class);
		assertEquals(response1.getStatus(), 200);
		assertEquals(response2.getStatus(), 304);
	} 

	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8080/AdobeAPI/").build();
	}

}
