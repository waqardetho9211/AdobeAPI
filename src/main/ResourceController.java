package main;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import main.business.resources.Resource;
import main.business.resources.ResourceDAO;
import main.business.resources.ResourceTypes;

import java.util.List;

@Path("/resource")
public class ResourceController {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Resource getResource(@QueryParam("name") final String name) {
		ResourceDAO dao = new ResourceDAO();
		List<Resource> resources = dao.getModel();
		for (Resource resource : resources) {
			if (resource.getName().equalsIgnoreCase(name)) {
				return resource;
			}
		}
		return new Resource(name, ResourceTypes.Unknown, "unknown", "unknown");
	}
}
