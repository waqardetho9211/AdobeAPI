package main.business.comments;

import java.time.LocalDateTime;

public class Comment implements Comparable<Comment>{
	
	private String user;
	private String comment;
	private LocalDateTime timeStamp;
	
	/*
	 * Constructor created to support mongo driver to convert from/to pojo to mongo document
	 */
	public Comment() {
		
	}
	
	public Comment(final String user, final String comment) {
		super();
		this.user = user;
		this.comment = comment;
		this.timeStamp = LocalDateTime.now();
	}
	public String getUser() {
		return user;
	}
	
	public void setUser(final String user) {
		this.user = user;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(final String comment) {
		this.comment = comment;
	} 
	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(final LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}
	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Comment other = (Comment) obj;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public int compareTo(final Comment comment) {
		return getTimeStamp().compareTo(comment.getTimeStamp());
	}
	
}
