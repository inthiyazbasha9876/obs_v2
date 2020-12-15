package com.ojas.obs.facade;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ojas.obs.request.LeaveTypeRequest;
@Service
public interface LeaveTypeFacade {
	public ResponseEntity<Object> saveDetails(LeaveTypeRequest leaveTypeRequestObject);

	public ResponseEntity<Object> getDetails(LeaveTypeRequest leaveTypeRequestObject);
}
