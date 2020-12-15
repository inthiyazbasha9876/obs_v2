package com.ojas.obs.resourcetype.model;

public class ResourceType {

	private Integer id;

	private String resourceTypeName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getResourceTypeName() {
		return resourceTypeName;
	}

	public void setResourceTypeName(String resourceTypeName) {
		this.resourceTypeName = resourceTypeName;
	}

	@Override
	public String toString() {
		return "ResourceType [id=" + id + ", resourceTypeName=" + resourceTypeName + "]";
	}

}
