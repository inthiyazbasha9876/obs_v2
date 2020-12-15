package com.ojas.obs.facade;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ojas.obs.request.InterviewResultRequest;
@Service
public interface InterviewResultFacade {
	public ResponseEntity<Object> saveDetails(InterviewResultRequest interviewRequestObject);

	public ResponseEntity<Object> getDetails(InterviewResultRequest interviewRequestObject);
}
 