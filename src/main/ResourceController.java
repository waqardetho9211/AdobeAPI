package main;

import javax.inject.Singleton;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
import main.business.resources.ResourceTypes;
import main.persistence.ResourceDBO;

@Singleton
@Path("/resource")
public class ResourceController { 
	private Resource resource;
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getResource(@QueryParam("name") final String name, @Context Request request) {
		ResourceDAO resourceDAO = new ResourceDBO();
		resource = resourceDAO.getAResource(name);
		
		if (resource == null) {
			return Response.noContent().build();
		}
		EntityTag etag = new EntityTag(Integer.toString(resource.hashCode()));
		ResponseBuilder builder = request.evaluatePreconditions(etag);
        if (builder != null) {
            return builder.build();
        }
        return Response.ok(resource).tag(etag).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response postResource(@FormParam("name") final String name, @FormParam("type") String type,
			@FormParam("path") String path, @FormParam("location") String location, @Context Request response) {
		
		//Check condition if a resource already there. Since, method is a post method.
		Resource resource = new Resource(name, ((type == null) ? ResourceTypes.Unknown : ResourceTypes.valueOf(type)), path, location);
		ResourceDAO resourceDAO = new ResourceDBO();
		resourceDAO.insertResource(resource);
		
		EntityTag etag = new EntityTag(Integer.toString(resource.hashCode()));
		
		return Response.ok(resource).tag(etag).build();
	}
}
