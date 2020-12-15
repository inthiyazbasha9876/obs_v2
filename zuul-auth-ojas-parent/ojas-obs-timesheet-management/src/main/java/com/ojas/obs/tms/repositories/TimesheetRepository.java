
package com.ojas.obs.tms.repositories;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ojas.obs.tms.model.Record;
import com.ojas.obs.tms.model.TimeSheet;

public interface TimesheetRepository extends JpaRepository<TimeSheet, Integer> {

	public List<TimeSheet> findByEmployeeIdAndTimeSheetStartDate(String employeeId, String startDate);

	@Query(name = "Schedules")
	public List<Record> getHours(String projId, Date from, Date to);

	@Query("SELECT filePath FROM TimeSheet WHERE timeSheetID = :timeSheetID")
	String getFilePath(@Param("timeSheetID") Integer timeSheetID);

	@Query("FROM TimeSheet WHERE employee_id = :employeeId")
	public List<TimeSheet> findByEmployeeId(@Param("employeeId") String employeeId);

	@Query("SELECT new TimeSheet(t.employeeId, t.timeSheetStartDate, ts.submissionState) from TimeSheet t, TimesheetStatus ts where t.employeeId= ?1 and t.timeSheetID = ts.sheet")
	public List<TimeSheet> findByEmployeeIdAndSubmissionState(String employeeId);
	@Query(name="GetByProject")
	public List<Record> getByProjectId(String proId, String empId, Date start, Date end);
}
