package main;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import main.business.Comment;
import main.persistence.CommentsDBA;

@Path("/comment")
public class CommentsController {
	
	@POST
    @Produces(MediaType.TEXT_HTML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response getResource(@FormParam("name") String name,
            @FormParam("comment") String comment,            
            @Context HttpServletResponse servletResponse) throws IOException {
		
		Comment commentBO = new Comment(name, comment);
		CommentsDBA commentsDBO = new CommentsDBA();
		List<Comment> comments = commentsDBO.insertComment(commentBO);
		
		GenericEntity<List<Comment>> generic = new GenericEntity<List<Comment>>(comments){};
        return Response.status(200).entity(generic).build();
	}

}
