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
import com.google.common.base.Charsets;
import com.google.common.io.Files;

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

		final ResponseBuilder builder = request.evaluatePreconditions(lastModified);
		if (builder != null) {
			return builder.build();
		}

		final CommentsDAO commentsDBO = new CommentsDBO();
		final List<Comment> comments = commentsDBO.getAllComments();
		Collections.sort(comments);

		final String htmlString = createHtmlString(comments);
		return Response.ok(htmlString).lastModified(lastModified).build();
	}

	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response addAComment(@FormParam("name") String name, @FormParam("comment") String comment,
			@Context Request response) throws IOException {

		final Comment commentBO = new Comment(name, comment);
		final CommentsDAO commentsDBO = new CommentsDBO();
		commentsDBO.insertComment(commentBO);

		lastModified = new Date();

		final List<Comment> comments = commentsDBO.getAllComments();
		Collections.sort(comments);

		final String htmlString = createHtmlString(comments);
		return Response.ok(htmlString).lastModified(lastModified).build();
	}

	private String createHtmlString(final List<Comment> comments) throws IOException {
		// Code not working in war file
		// final URL url = getClass().getResource("./resources/comments.html");
		// final File file = new File(url.getPath());
		// String htmlString = FileUtils.readFileToString(file);
		String htmlString = getHTMLFromText();

		final StringBuilder listBuilder = new StringBuilder();

		for (Comment comment : comments) {
			listBuilder.append( 
					"<li class='list-group-item'> <b>" + comment.getUser() + "</b> says: <i>" + comment.getComment()
							+ "</i> <p><small>" + comment.getTimeStamp().format(dateTimeFormat) + "</small></p></li>");
		}
		htmlString = htmlString.replace("$targetList", listBuilder);

		return htmlString;
	}

	private String getHTMLFromText() {
		return "<!-- html template copied from https://www.w3schools.com/bootstrap/tryit.asp?filename=trybs_list_group&stacked=h -->\r\n" + 
				"<!DOCTYPE html>\r\n" + 
				"<html lang=\"en\">\r\n" + 
				"<head>\r\n" + 
				"<title>Wall Post Anything here</title>\r\n" + 
				"<meta charset=\"utf-8\">\r\n" + 
				"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\r\n" + 
				"<link rel=\"stylesheet\"\r\n" + 
				"	href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css\">\r\n" + 
				"<script\r\n" + 
				"	src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>\r\n" + 
				"<script\r\n" + 
				"	src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js\"></script>\r\n" + 
				"</head>\r\n" + 
				"<body>\r\n" + 
				"\r\n" + 
				"	<div class=\"container\">\r\n" + 
				"		<h3>Comments Submitted on the wall</h3>\r\n" + 
				"		<ul class=\"list-group\">$targetList\r\n" + 
				"		</ul>\r\n" + 
				"	</div>\r\n" + 
				"\r\n" + 
				"</body>\r\n" + 
				"</html>";
	}


}
