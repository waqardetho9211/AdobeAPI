package test;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.jersey.client.ClientConfig;
import org.junit.jupiter.api.Test;

import main.business.resources.Resource;

class PathControllerTest {  

	@Test
	void shouldShowNoContentResponse() {

		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		WebTarget target = client.target(getBaseURI());

		final Response response = target.path("rest/path").queryParam("path", "random").request()
				.accept(MediaType.APPLICATION_JSON).get(Response.class);

		assertEquals(response.getStatus(), 204); 
	}

	@Test
	public void shouldReturnResources() {

		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		WebTarget target = client.target(getBaseURI());

		Resource aRandomResource = new Resource();
		aRandomResource.setPath("main");
		Response response = target.path("rest/path").queryParam("path", aRandomResource.getPath())
				.request().accept(MediaType.APPLICATION_JSON).get(Response.class);

		List<Resource> resources = response.readEntity(new GenericType<List<Resource>>(){}); 
		
		for (Resource resource : resources) {			
			assertEquals(resource.getPath(), aRandomResource.getPath());			
	    }
	}
	
	@Test
	public void shouldReturnNotModifiedResponseAtAPath() {
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		WebTarget target = client.target(getBaseURI());

		Resource aRandomResource = new Resource();
		aRandomResource.setPath("main");
		Response response = target.path("rest/path").queryParam("path", aRandomResource.getPath())
				.request().accept(MediaType.APPLICATION_JSON).get(Response.class);
		
		String eTag = response.getHeaderString(HttpHeaders.ETAG);

		Response response2 = target.path("rest/resources").request().header(HttpHeaders.ETAG, eTag).get(Response.class);
		// ToDo not working fix this
		assertEquals(response2.getStatus(), 304);
	}
	
	@Test
	public void shouldReturnEntityTagInResponse() {
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		WebTarget target = client.target(getBaseURI());

		Resource aRandomResource = new Resource();
		aRandomResource.setPath("main");
		Response response = target.path("rest/path").queryParam("path", aRandomResource.getPath())
				.request().accept(MediaType.APPLICATION_JSON).get(Response.class);
		
		String eTag = response.getHeaderString(HttpHeaders.ETAG);
		assertNotNull(eTag); 
	}
	
	@Test
	public void shouldHaveSameETAGInDifferentRequestAsContentNotUpdated() {
		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		WebTarget target = client.target(getBaseURI());
		Resource aRandomResource = new Resource();
		aRandomResource.setPath("main");
		
		Response response1 = target.path("rest/path").queryParam("path", aRandomResource.getPath())
				.request().accept(MediaType.APPLICATION_JSON).get(Response.class);
		Response response2 = target.path("rest/path").queryParam("path", aRandomResource.getPath())
				.request().accept(MediaType.APPLICATION_JSON).get(Response.class);

		String eTag1 = response1.getHeaderString(HttpHeaders.ETAG);
		String eTag2 = response2.getHeaderString(HttpHeaders.ETAG);

		assertEquals(eTag1, eTag2);
	}
	
	@Test
	public void shouldReturnResourcesAtAGivenPath() {
		// ToDo implement this
	}
	
	@Test
	public void shouldReturnUpdatedResourcesAtAPath() {
		// ToDo implement this
	}
	
	@Test
	public void checkHTTPCachingRequest() {
		// ToDo implement this. 
	}
	
	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8080/AdobeAPI/").build();
	}

}
