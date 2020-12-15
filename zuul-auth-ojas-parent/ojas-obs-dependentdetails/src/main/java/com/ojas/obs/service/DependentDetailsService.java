package com.ojas.obs.service;

import java.sql.SQLException;

import com.ojas.obs.request.DependentDetailsRequest;
import com.ojas.obs.response.DependentDetailsResponse;

public interface DependentDetailsService {
	DependentDetailsResponse setDependentDetails(DependentDetailsRequest dependentDetailsRequestObject) throws SQLException;
	DependentDetailsResponse getDependentDetails(DependentDetailsRequest dependentDetailsRequestObject) throws SQLException;
}
