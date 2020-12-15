package com.obs.lms.facade;

import java.io.IOException;

import org.springframework.http.ResponseEntity;

import com.obs.lms.request.LmsRequest;

public interface LmsFacade {
	public ResponseEntity<Object> setLms(LmsRequest lmsreq) throws IOException;
	public ResponseEntity<Object> getLms(LmsRequest lmsreq) throws IOException;

}
