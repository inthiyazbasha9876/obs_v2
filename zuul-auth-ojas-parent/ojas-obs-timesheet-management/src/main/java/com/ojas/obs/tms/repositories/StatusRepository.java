package com.ojas.obs.tms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ojas.obs.tms.model.TimesheetStatus;

public interface StatusRepository extends JpaRepository<TimesheetStatus, Integer>{
	public TimesheetStatus getByStatusId(Integer statusId);
}
