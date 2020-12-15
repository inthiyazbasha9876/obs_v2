package com.ojas.obs.tms.repositoriesimpl;

import org.springframework.beans.factory.annotation.Autowired;

import com.ojas.obs.tms.model.TimesheetStatus;
import com.ojas.obs.tms.repositories.StatusRepository;

public class StatusRepositoryImpl {
	
	@Autowired
	private StatusRepository statusRepo;
	public Boolean updateStatus(TimesheetStatus status) {
		Boolean updated = false;
		TimesheetStatus updatedStatus = null;
		TimesheetStatus fetchedStatus = statusRepo.getByStatusId(status.getStatusId());
		if(fetchedStatus != null) {
		fetchedStatus.setSubmissionState(status.getSubmissionState());
		fetchedStatus.setApproverId(status.getApproverId());
		fetchedStatus.setComment(status.getComment());
		updatedStatus = statusRepo.save(fetchedStatus);
		}
		if(updatedStatus != null) {
			updated = true;
		}
		return updated;
	}
}
