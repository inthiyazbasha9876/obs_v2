package com.ojas.obs.facade;

import java.sql.SQLException;

import org.springframework.http.ResponseEntity;

import com.ojas.obs.request.ExperienceRequest;
import com.ojas.obs.response.ExperienceResponse;

public interface ExperienceFacade {

	ResponseEntity<Object> setEmployeeExperienceDetails(
			ExperienceRequest employeeExperienceDetailsRequestObject) throws SQLException;

	ResponseEntity<Object> getEmployeeExperienceDetails(
			ExperienceRequest employeeExperienceDetailsRequestObject) throws SQLException;


}
