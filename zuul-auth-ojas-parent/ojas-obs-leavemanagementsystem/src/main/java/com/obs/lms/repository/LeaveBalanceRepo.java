package com.obs.lms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.obs.lms.model.LeaveBalance;

public interface LeaveBalanceRepo extends JpaRepository<LeaveBalance, Integer> {
	
	public LeaveBalance getAllLeaveBalByEmpId(String empId);

	public List<LeaveBalance> findByEmpIdIn(List<String> empIds);
	
	
	

}
