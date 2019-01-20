package test;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.jersey.client.ClientConfig;
import org.junit.jupiter.api.Test;

import main.business.Resource;

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
	void shouldReturnResources() {

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
	
	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8080/AdobeAPI/").build();
	}

}
