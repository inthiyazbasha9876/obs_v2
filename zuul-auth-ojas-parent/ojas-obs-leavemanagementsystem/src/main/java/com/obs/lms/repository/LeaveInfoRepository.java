package com.obs.lms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.obs.lms.model.LeaveInfo;

public interface LeaveInfoRepository extends JpaRepository<LeaveInfo, Integer> {
	@Query("Select l From LeaveInfo l where l.empId= :empId order by id desc")
	public List<LeaveInfo> getByEmpId(String empId);

	@Query("SELECT filePath FROM LeaveInfo WHERE id = :id")
	String getFilePath(@Param("id") Integer id);

	@Query("Select l From LeaveInfo l where l.applyTo= :applyTo order by id desc")
	public List<LeaveInfo> getByManagerId(String applyTo);

	public List<LeaveInfo> getAllLeaveInfoByEmpId(String empId);

}