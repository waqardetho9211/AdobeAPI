package main;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
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
	public Response getResources() throws NoSuchAlgorithmException {
		ResourceDBO dao = new ResourceDBO();
		List<Resource> resources = dao.getAllResources();
		
		MessageDigest digest = MessageDigest.getInstance("MD5");
        byte[] hash = digest.digest(resources.toString().getBytes(StandardCharsets.UTF_8));
        String hex = DatatypeConverter.printHexBinary(hash);
        EntityTag etag = new EntityTag(hex);
        
        return Response.ok(resources).tag(etag).build(); 
	}

}
