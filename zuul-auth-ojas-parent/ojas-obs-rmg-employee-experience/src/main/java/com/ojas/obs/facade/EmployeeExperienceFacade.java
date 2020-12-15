package com.ojas.obs.facade;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ojas.obs.request.EmployeeExperienceRequest;

@Service
public interface EmployeeExperienceFacade {
	public ResponseEntity<Object> setEmpExp(EmployeeExperienceRequest empExpReq);

	public ResponseEntity<Object> getEmpExp(EmployeeExperienceRequest empExpReq);

}
