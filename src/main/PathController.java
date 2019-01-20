package main;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import main.business.Resource;
import main.persistence.ResourceDAO;

import java.util.ArrayList;
import java.util.List;

@Path("/path")
public class PathController { 
	@GET	
	@Produces(MediaType.APPLICATION_JSON)
	public Response getResource(@QueryParam("path") final String path) {
		ResourceDAO dao = new ResourceDAO();
		List<Resource> resources = dao.getModel();
		List<Resource> foundResources = new ArrayList<Resource>(); 
		for (Resource resource : resources) {
	        if (resource.getPath().equalsIgnoreCase(path)) {
	        	foundResources.add(resource);
	        }
	    }
		if(foundResources.size() > 0) {
			GenericEntity<List<Resource>> generic = new GenericEntity<List<Resource>>(foundResources){};
	        return Response.status(200).entity(generic).build();
		}
		GenericEntity<Resource> generic = new GenericEntity<Resource>(new Resource()) {};
		return Response.status(204).entity(generic).build();
	
	}
}
