package main.business;

public class Resource {
	private String name;
	private ResourceTypes type; 
	
	public Resource(String name, ResourceTypes type) {
		super();
		this.name = name;
		this.type = type;
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
