package main.business.resources;

import java.util.ArrayList;
import java.util.List;

public class ResourceDAO {
	List<Resource> resources = new ArrayList<Resource>();

	public ResourceDAO() {
		Resource resource1 = new Resource("content.pdf", ResourceTypes.File, "main/documents", "root/user1");
		Resource resource2 = new Resource("index.html", ResourceTypes.File, "main", "root/user1");
		Resource resource3 = new Resource("documents", ResourceTypes.Directory, "main", "root/user1");
		Resource resource4 = new Resource("exams.pdf", ResourceTypes.File, "main/documents", "root");
		resources.add(resource1);
		resources.add(resource2);
		resources.add(resource3);
		resources.add(resource4);
	}

	public List<Resource> getModel() {
		return resources;
	} 

}
