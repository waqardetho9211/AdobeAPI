package main;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.xml.bind.DatatypeConverter;

import main.business.resources.Resource;
import main.persistence.ResourceDBO;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Path("/resources")
public class ResourcesController {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getResources(@Context Request request) throws NoSuchAlgorithmException {
		final ResourceDBO dao = new ResourceDBO();
		final List<Resource> resources = dao.getAllResources();

		final MessageDigest digest = MessageDigest.getInstance("MD5");
		final byte[] hash = digest.digest(resources.toString().getBytes(StandardCharsets.UTF_8));
		final String hex = DatatypeConverter.printHexBinary(hash);
		final EntityTag etag = new EntityTag(hex);

		final ResponseBuilder builder = request.evaluatePreconditions(etag);
		if (builder != null) {
			return builder.build();
		}

		return Response.ok(resources).tag(etag).build();
	}

}
