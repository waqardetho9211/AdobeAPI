package main.business.resources;

import java.util.List;

public interface ResourceDAO {
	public void insertResource(Resource resource);
	public List<Resource> getAllResources();
	public Resource getAResource(String name);
	public List<Resource> getAllResourcePath(String path);
}
