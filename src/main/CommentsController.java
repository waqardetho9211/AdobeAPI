package main;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.io.FileUtils;

import main.business.Comment;
import main.persistence.CommentsDBA;

@Path("/comment")
public class CommentsController {

	private DateTimeFormatter dateTimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	@POST
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String getResource(@FormParam("name") String name, @FormParam("comment") String comment,
			@Context HttpServletResponse servletResponse) throws IOException {

		Comment commentBO = new Comment(name, comment);
		CommentsDBA commentsDBO = new CommentsDBA();
		commentsDBO.insertComment(commentBO);

		List<Comment> comments = commentsDBO.getAllComments();
		Collections.sort(comments);

		String htmlString = createHtmlString(comments);
		return htmlString;
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
			System.out.println(comment.getTimeStamp());
		}
		htmlString = htmlString.replace("$targetList", listBuilder);

		return htmlString;
	}

}
