package main.business.resources;

import java.util.List;

import main.business.comments.Comment;

public interface ResourceDAO {
	public void insertResource(Resource resource);
	public List<Resource> getAllResources();
	public Resource getAResource(String name);
	public Resource getAResourcePath(String path);
}
