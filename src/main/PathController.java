package main;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import main.business.resources.Resource;
import main.business.resources.ResourceDAO;
import main.persistence.ResourceDBO;

@Path("/path")
public class PathController {
	private List<Resource> resource;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getResource(@QueryParam("path") final String path, @Context Request request) {

		ResourceDAO resourceDAO = new ResourceDBO();
		resource = resourceDAO.getAllResourcePath(path);
		if (resource == null || resource.size() == 0) {
			return Response.noContent().build();
		}
		EntityTag etag = new EntityTag(Integer.toString(resource.hashCode()));
		ResponseBuilder builder = request.evaluatePreconditions(etag);
		if (builder != null) {
			return builder.build();
		}
		return Response.ok(resource).tag(etag).build();
	}
}
