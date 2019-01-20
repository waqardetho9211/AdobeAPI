package test;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;

import org.glassfish.jersey.client.ClientConfig;
import org.junit.jupiter.api.Test;

import main.business.Resource;

class ResourceControllerTest {

	@Test
	void shouldShowOKResponse() {

		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		WebTarget target = client.target(getBaseURI());

		final Response response = target.path("rest/resource").queryParam("name", "random").request()
				.accept(MediaType.APPLICATION_JSON).get(Response.class);

		assertEquals(response.getStatus(), 200);
	}

	@Test
	void shouldReturnResource() {

		ClientConfig config = new ClientConfig();
		Client client = ClientBuilder.newClient(config);
		WebTarget target = client.target(getBaseURI());

		Resource aRandomResource = new Resource();
		aRandomResource.setName("content.pdf");
		Response response = target.path("rest/resource").queryParam("name", aRandomResource.getName()).request()
				.accept(MediaType.APPLICATION_JSON).get(Response.class);

		String output = response.readEntity(String.class);
		assertThat(output, containsString("name"));
		assertThat(output, containsString("type"));
		assertThat(output, containsString("path"));
		assertThat(output, containsString("location"));

		assertThat(output, containsString("content.pdf"));

	}

	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://localhost:8080/AdobeAPI/").build();
	}

}
