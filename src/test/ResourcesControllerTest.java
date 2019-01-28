package test;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.junit.jupiter.api.Test;

class ResourcesControllerTest {

	@Test
	public void shouldShowOKResponse() {
		final ClientConfig config = new ClientConfig();

		final Client client = ClientBuilder.newClient(config);

		final WebTarget target = client.target(getBaseURI());

		final Response response = target.path("rest").path("resources").request().accept(MediaType.APPLICATION_JSON)
				.get(Response.class);

		assertEquals(response.getStatus(), 200);
	}

	@Test
	public void shouldReturnEntityTagInResponse() {
		final ClientConfig config = new ClientConfig();
		final Client client = ClientBuilder.newClient(config);
		final WebTarget target = client.target(getBaseURI());

		final Response response = target.path("rest/resources").request().get(Response.class);

		final String eTag = response.getHeaderString(HttpHeaders.ETAG);
		assertNotNull(eTag);

	}

	@Test
	public void shouldReturnNotModifiedResponse() {
		final ClientConfig config = new ClientConfig();
		final Client client = ClientBuilder.newClient(config);
		final WebTarget target = client.target(getBaseURI());

		final Response response = target.path("rest/resources").request().get(Response.class);

		final String eTag = response.getHeaderString(HttpHeaders.ETAG);

		final Response response2 = target.path("rest/resources").request().header(HttpHeaders.ETAG, eTag).get(Response.class);
		// ToDo not working fix this
		assertEquals(response2.getStatus(), 304);
	}
	
	@Test
	public void shouldHaveSameETAGInDifferentRequestAsContentNotUpdated() {
		final ClientConfig config = new ClientConfig();
		final Client client = ClientBuilder.newClient(config);
		final WebTarget target = client.target(getBaseURI());

		final Response response1 = target.path("rest/resources").request().get(Response.class);
		final Response response2 = target.path("rest/resources").request().get(Response.class);

		final String eTag1 = response1.getHeaderString(HttpHeaders.ETAG);
		final String eTag2 = response2.getHeaderString(HttpHeaders.ETAG);

		assertEquals(eTag1, eTag2);
	}

	@Test
	public void shouldReturnResources() {
		// ToDo implement this
	}
	
	@Test
	public void shouldReturnUpdatedResources() {
		// ToDo implement this
	}

	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8080/AdobeAPI/").build();
	}

}
