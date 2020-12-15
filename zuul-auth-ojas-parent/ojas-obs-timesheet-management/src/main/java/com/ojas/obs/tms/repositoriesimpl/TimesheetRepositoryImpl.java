package com.ojas.obs.tms.repositoriesimpl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ojas.obs.tms.model.Record;
import com.ojas.obs.tms.model.TimeSheet;
import com.ojas.obs.tms.repositories.TimesheetRepository;

@Transactional
public class TimesheetRepositoryImpl {
	@Autowired
	private TimesheetRepository timesheetRepository;
	private Logger logger = Logger.getLogger(TimesheetRepositoryImpl.class);

	public Boolean saveTimesheet(TimeSheet sheet) {
		boolean saved = false;
		TimeSheet submittedRecord = timesheetRepository.save(sheet);
		if (submittedRecord.getTimeSheetID() != null) {
			saved = true;
		}
		return saved;
	}

	public Boolean submitTimesheet(TimeSheet sheet) {
		boolean submitted = false;

		boolean timeSheet = timesheetRepository.existsById(sheet.getTimeSheetID());
		logger.debug("Record exists : " + timeSheet);

		if (timeSheet) {
			TimeSheet submittedRecord = timesheetRepository.save(sheet);
			if (submittedRecord.getTimeSheetID() != null) {
				submitted = true;
			}
		}
		return submitted;
	}

	public List<TimeSheet> getById(Integer id) {
		List<TimeSheet> sheet = new ArrayList<>();
		sheet.add(timesheetRepository.getOne(id));
		return sheet;
	}

	public List<TimeSheet> getByEmpId(String employeeId, String startDate) {

		return timesheetRepository.findByEmployeeIdAndTimeSheetStartDate(employeeId, startDate);
	}

	public List<TimeSheet> getByEmp(String employeeId) {

		return timesheetRepository.findByEmployeeId(employeeId);
	}

	public List<TimeSheet> getTimesheets() {
		return timesheetRepository.findAll();
	}

	public String getFile(Integer id) {
		return timesheetRepository.getFilePath(id);
	}

	public List<TimeSheet> getByEmpAndStatus(String employeeId) {
		return timesheetRepository.findByEmployeeIdAndSubmissionState(employeeId);
	}

	public List<Record> getHours(String proId, Date start, Date end) {
		return timesheetRepository.getHours(proId, start, end);
	}
	public List<Record> findByProjId(String proId, String empId, Date start, Date end) {
		return timesheetRepository.getByProjectId(proId, empId, start, end);
	}
}
