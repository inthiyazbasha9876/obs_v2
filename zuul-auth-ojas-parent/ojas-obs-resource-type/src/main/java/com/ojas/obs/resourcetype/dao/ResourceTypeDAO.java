package com.ojas.obs.resourcetype.dao;

import java.sql.SQLException;
import java.util.List;

import com.ojas.obs.resourcetype.model.ResourceType;

public interface ResourceTypeDAO {
	
	  boolean saveResourceTypes(List<ResourceType> resourceTypes) throws SQLException;
	  
	  boolean updateResourceTypes(List<ResourceType> resourceTypes) throws SQLException;
	  
	  boolean deleteResourceTypes(List<ResourceType> resourceTypes) throws SQLException;
	 
	List<ResourceType> getAllResourceTypes() throws SQLException;

	List<ResourceType> getResourceTypeById(Integer id) throws SQLException;
}
