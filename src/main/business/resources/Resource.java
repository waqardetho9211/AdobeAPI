package main.business.resources;

public class Resource {

	private String name;
	private ResourceTypes type;
	private String path;
	private String location;

	public Resource() {

	}

	public Resource(final String name, final ResourceTypes type, final String path, final String location) {
		super();
		this.name = name;
		this.type = type;
		this.path = path;
		this.location = location;
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(final String path) {
		this.path = path;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(final String location) {
		this.location = location;
	}

	public String getName() {
		return name;
	}

	public ResourceTypes getType() {
		return type;
	}

	public void setType(final ResourceTypes type) {
		this.type = type;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Resource other = (Resource) obj;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((location == null) ? 0 : location.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

}
