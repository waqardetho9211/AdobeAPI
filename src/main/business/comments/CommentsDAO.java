package main.business.comments;

import java.util.List;

public interface CommentsDAO {
	public void insertComment(Comment commentBO);
	public List<Comment> getAllComments();

}
