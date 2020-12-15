package com.ojas.obs.interviewmode.facade;

import org.springframework.http.ResponseEntity;

import com.ojas.obs.interviewmode.request.InterviewModeRequest;

public interface InterviewModeFacade {
	public ResponseEntity<Object> saveDetails(InterviewModeRequest billingTypeRequest);

	public ResponseEntity<Object> getAllDetails(InterviewModeRequest billingTypeRequest);

}
