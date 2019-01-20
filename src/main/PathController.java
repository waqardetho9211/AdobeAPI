package main;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import main.business.Resource;
import main.business.ResourceTypes;
import main.persistence.ResourceDAO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Path("/path")
public class PathController {		
	@GET	
	@Produces(MediaType.APPLICATION_JSON)
	public List<Resource> getResource(@QueryParam("path") final String path) {
		ResourceDAO dao = new ResourceDAO();
		List<Resource> resources = dao.getModel();
		List<Resource> foundResources = new ArrayList<Resource>(); 
		for (Resource resource : resources) {
	        if (resource.getPath().equalsIgnoreCase(path)) {
	        	foundResources.add(resource);
	        }
	    }
		if(foundResources.size() > 0) {
			return foundResources;
		}
		return Arrays.asList(new Resource("Unknown", ResourceTypes.Unknown, path, "unknown"));
	}	
}
