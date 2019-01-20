package main.persistence;

import java.util.ArrayList;
import java.util.List;
import main.business.Resource;
import main.business.ResourceTypes;

public class ResourceDAO {
	List<Resource> resources = new ArrayList<Resource>();

	public ResourceDAO() {
		Resource resource1 = new Resource("conent.pdf", ResourceTypes.File);
		Resource resource2 = new Resource("index.html", ResourceTypes.File);
		Resource resource3 = new Resource("documents", ResourceTypes.Directory);
		resources.add(resource1);
		resources.add(resource2);
		resources.add(resource3);
	}

	public List<Resource> getModel() {
		return resources;
	}

}
