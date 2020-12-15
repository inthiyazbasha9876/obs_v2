package com.ojas.obs.resourcetype.facade;

import java.sql.SQLException;

import com.ojas.obs.resourcetype.model.ResourceTypeRequest;
import com.ojas.obs.resourcetype.model.ResourceTypeResponse;

public interface ResourceTypeFacade {

	ResourceTypeResponse saveResourceTypes(ResourceTypeRequest employmentDetailsRequest)
			throws SQLException;

	ResourceTypeResponse viewResourceTypes(ResourceTypeRequest resourceTypeRequest) throws SQLException;

}
