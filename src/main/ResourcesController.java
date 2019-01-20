package main;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.servlet.ServletContainer;

import main.business.Resource;
import main.business.ResourceTypes;
import main.persistence.ResourceDAO;

import java.util.List;

import javax.websocket.server.ServerContainer;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

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
