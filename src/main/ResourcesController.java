package main;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import main.business.Resource;
import main.persistence.ResourceDAO;

import java.util.List;

@Path("/resources")
public class ResourcesController {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Resource> isAliveJSON() {
		ResourceDAO dao = new ResourceDAO();
		List<Resource> resources = dao.getModel();
		return resources;
	}

}
