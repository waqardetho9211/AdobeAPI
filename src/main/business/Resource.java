package main.business;

public class Resource {
	private String name;
	private ResourceTypes type; 
	private String path;
	private String location;
	
	public String getPath() {
		return path;
	}
	
	public Resource() {
		
	}

	public Resource(String name, ResourceTypes type, String path, String location) {
		super();
		this.name = name;
		this.type = type;
		this.path = path;
		this.location = location;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	public String getName() {
		return name;
	}

	public ResourceTypes getType() {
		return type;
	}

	public void setType(ResourceTypes type) {
		this.type = type;
	}

	public void setName(String name) {
		this.name = name;
	}

}
