package main;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.commons.io.FileUtils;

import main.business.comments.Comment;
import main.business.comments.CommentsDAO;
import main.persistence.CommentsDBO;

@Singleton
@Path("/comment")
public class CommentsController {

	private DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	private Date lastModified = new Date();

	@GET
	@Produces(MediaType.TEXT_HTML)
	public Response getComments(@Context Request request) throws IOException {

		ResponseBuilder builder = request.evaluatePreconditions(lastModified);
		if (builder != null) {
			return builder.build();
		}

		CommentsDAO commentsDBO = new CommentsDBO();
		List<Comment> comments = commentsDBO.getAllComments();
		Collections.sort(comments);

		String htmlString = createHtmlString(comments);
		return Response.ok(htmlString).lastModified(lastModified).build();
	}

	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response addAComment(@FormParam("name") String name, @FormParam("comment") String comment,
			@Context Request response) throws IOException {

		Comment commentBO = new Comment(name, comment);
		CommentsDAO commentsDBO = new CommentsDBO();
		commentsDBO.insertComment(commentBO);

		lastModified = new Date();

		List<Comment> comments = commentsDBO.getAllComments();
		Collections.sort(comments);

		String htmlString = createHtmlString(comments);
		return Response.ok(htmlString).lastModified(lastModified).build();
	}

	private String createHtmlString(List<Comment> comments) throws IOException {
		URL url = getClass().getResource("./resources/comments.html");
		File file = new File(url.getPath());
		String htmlString = FileUtils.readFileToString(file);

		StringBuilder listBuilder = new StringBuilder();

		for (Comment comment : comments) {
			listBuilder.append(
					"<li class='list-group-item'> <b>" + comment.getUser() + "</b> says: <i>" + comment.getComment()
							+ "</i> <p><small>" + comment.getTimeStamp().format(dateTimeFormat) + "</small></p></li>");
		}
		htmlString = htmlString.replace("$targetList", listBuilder);

		return htmlString;
	}

}
