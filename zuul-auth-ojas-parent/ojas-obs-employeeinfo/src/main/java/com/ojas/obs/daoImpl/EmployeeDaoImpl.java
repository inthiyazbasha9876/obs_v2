package com.ojas.obs.daoImpl;

import static com.ojas.obs.constants.UserConstants.DELETEEMPINFO;
import static com.ojas.obs.constants.UserConstants.GETEMPBYEMPID;
import static com.ojas.obs.constants.UserConstants.GETEMPBYID;
import static com.ojas.obs.constants.UserConstants.GETEMPDETAILS;
import static com.ojas.obs.constants.UserConstants.GETMAILID;
import static com.ojas.obs.constants.UserConstants.SAVEEMPINFO;
import static com.ojas.obs.constants.UserConstants.SAVERMGEMPINFO;
import static com.ojas.obs.constants.UserConstants.SAVEEMPLOGININFO;
import static com.ojas.obs.constants.UserConstants.SAVEEMPROLE;
import static com.ojas.obs.constants.UserConstants.UPDATEEMPINFO;
import static com.ojas.obs.constants.UserConstants.GETMNGRMAIL;
import static com.ojas.obs.constants.UserConstants.GETALLREPORTIES;
import static com.ojas.obs.constants.UserConstants.UPLOADPIC;
import static com.ojas.obs.constants.UserConstants.GETEMPINFOS;
import static com.ojas.obs.constants.UserConstants.STATUSUPDATE;
import static com.ojas.obs.constants.UserConstants.ADDEMP;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ojas.obs.dao.EmployeeInfoDao;
import com.ojas.obs.model.EmployeeInfo;
import com.ojas.obs.model.EmployeeSkills;
import com.ojas.obs.request.EmployeeInfoRequest;

@Repository
public class EmployeeDaoImpl implements EmployeeInfoDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private PasswordEncoder passwordEncode;
	@Autowired
	private Environment env;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	Logger logger = Logger.getLogger(this.getClass());

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ojas.obs.dao.EmployeeInfoDao#saveEmployeeInfo(com.ojas.obs.request.
	 * EmployeeInfoRequest)
	 */

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean saveEmployeeInfo(EmployeeInfoRequest employeeInfoRequest) throws SQLException {
		logger.debug("inside save employee in employeeDao");
		List<Object[]> employeeInfoList = new ArrayList<>();
		List<Object[]> loginList = new ArrayList<>();
		List<Object[]> roleList = new ArrayList<>();
		List<Object[]> empIds = new ArrayList<>();
		List<Object[]> rmgdataList = new ArrayList<>();
		String fullName = null;
		for (EmployeeInfo employeeInfo : employeeInfoRequest.getEmployeeInfo()) {
			Integer role = Integer.parseInt(env.getProperty("role"));
			employeeInfo.setRole(role);
			if (employeeInfo.getMiddlename() != null) {
				fullName = employeeInfo.getFirstname() + " " + employeeInfo.getMiddlename() + "  "
						+ employeeInfo.getLastname();
			} else {
				fullName = employeeInfo.getFirstname() + "  " + employeeInfo.getLastname();
			}
			Date date = Date.valueOf(employeeInfo.getDob());
			// Date statusDate = Date.valueOf(employeeInfo.getStatusDate());
			Object[] emp = { employeeInfo.getEmployeeId() };
			empIds.add(emp);
			Object[] empinfo = { employeeInfo.getFirstname(), employeeInfo.getMiddlename(), employeeInfo.getLastname(),
					employeeInfo.getStatus(), date, employeeInfo.getGender(), employeeInfo.getTitle(),
					employeeInfo.getReportingManager(), employeeInfo.getEmployeeId(), true,
					new Timestamp(new java.util.Date().getTime()), employeeInfo.getCreatedBy(), employeeInfo.getEmail(),
					employeeInfo.getOfficialEmail(), employeeInfo.getPersonalMobileNo(), employeeInfo.getImage() };

			employeeInfoList.add(empinfo);
			Object[] empLogin = { employeeInfo.getEmployeeId(), this.passwordEncode.encode(employeeInfo.getPassword()),
					new Timestamp(new java.util.Date().getTime()), employeeInfo.getCreatedBy() };
			loginList.add(empLogin);
			Object[] empList = { employeeInfo.getEmployeeId(), employeeInfo.getRole() };
			roleList.add(empList);

			Object[] rmgList = { employeeInfo.getEmployeeId(), fullName, true, employeeInfo.getGender() };
			rmgdataList.add(rmgList);

		}

		jdbcTemplate.batchUpdate(ADDEMP, empIds);
		jdbcTemplate.batchUpdate(SAVEEMPINFO, employeeInfoList);
		jdbcTemplate.batchUpdate(SAVEEMPROLE, roleList);
		jdbcTemplate.batchUpdate(SAVEEMPLOGININFO, loginList);
		jdbcTemplate.batchUpdate(SAVERMGEMPINFO, rmgdataList);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ojas.obs.dao.EmployeeInfoDao#updateEmployeeInfo(com.ojas.obs.request.
	 * EmployeeInfoRequest)
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean updateEmployeeInfo(EmployeeInfoRequest employeeInfoRequest) throws SQLException {
		logger.debug("inside update employee in employeeDao");
		List<Object[]> inputList = new ArrayList<>();
		for (EmployeeInfo employeeInfo : employeeInfoRequest.getEmployeeInfo()) {
			Date date = Date.valueOf(employeeInfo.getDob());
			// Date statusDate = Date.valueOf(employeeInfo.getStatusDate());
			Object[] emp = { employeeInfo.getFirstname(), employeeInfo.getMiddlename(), employeeInfo.getLastname(),
					employeeInfo.getStatus(), date, employeeInfo.getGender(), employeeInfo.getTitle(),
					employeeInfo.getReportingManager(), employeeInfo.getEmployeeId(), employeeInfo.getEmail(),
					employeeInfo.getOfficialEmail(), employeeInfo.getPersonalMobileNo(), employeeInfo.getUpdatedBy(),
					new Timestamp(new java.util.Date().getTime()), employeeInfo.getId() };
			inputList.add(emp);

		}
		jdbcTemplate.batchUpdate(UPDATEEMPINFO, inputList);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ojas.obs.dao.EmployeeInfoDao#deleteEmployeeInfo(com.ojas.obs.request.
	 * EmployeeInfoRequest)
	 */
	@Override
	public boolean deleteEmployeeInfo(EmployeeInfoRequest employeeInfoRequest) throws SQLException {
		logger.debug("inside delete employee in employeeDao");
		List<Object[]> inputList = new ArrayList<>();
		EmployeeInfo employeeInfo = employeeInfoRequest.getEmployeeInfo().get(0);
		Object[] emp = { false, new Timestamp(new java.util.Date().getTime()), employeeInfo.getId() };
		inputList.add(emp);
		jdbcTemplate.batchUpdate(DELETEEMPINFO, inputList);
		return true;
	}

	@Override
	public boolean picUpload(EmployeeInfo info) throws SQLException {
		boolean uploaded = false;
		int save = jdbcTemplate.update(UPLOADPIC, info.getImage(), info.getEmployeeId());
		if (save > 0) {
			uploaded = true;
		}
		return uploaded;
	}

	@Override
	public boolean updateStatus(EmployeeInfo info) throws SQLException {
		boolean updated = false;
		int save = jdbcTemplate.update(STATUSUPDATE, info.getStatus(), info.isFlag(), info.getEmployeeId());
		if (save > 0) {
			updated = true;
		}
		return updated;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.ojas.obs.dao.EmployeeInfoDao#getAllEmployeeDetails(com.ojas.obs.request.
	 * EmployeeInfoRequest)
	 */

	@Override
	public List<EmployeeInfo> getById(EmployeeInfoRequest employeeInfoRequest) throws SQLException {

		List<EmployeeInfo> employeeinfo = null;
		EmployeeInfo employee = employeeInfoRequest.getEmployeeInfo().get(0);
		if (employee.getId() != null) {
			logger.debug("inside get by id in employeeDao with id : " + employee.getId());
			employeeinfo = jdbcTemplate.query(GETEMPBYID + employee.getId(),
					new BeanPropertyRowMapper<>(EmployeeInfo.class));
		} else {
			logger.debug("inside get by employeeId in employeeDao with employeeId : " + employee.getEmployeeId());
			employeeinfo = jdbcTemplate.query(GETEMPBYEMPID + employee.getEmployeeId(),
					new BeanPropertyRowMapper<>(EmployeeInfo.class));
		}
		return employeeinfo;
	}

	@Override
	public List<EmployeeInfo> getAllEmployeeDetails(EmployeeInfoRequest employeeInfoRequest) throws SQLException {
		logger.debug("inside get all employee details in employeeDao");
		return jdbcTemplate.query(GETEMPDETAILS, new BeanPropertyRowMapper<>(EmployeeInfo.class));
	}

	@Override
	public List<EmployeeInfo> getReporties(EmployeeInfoRequest employeeInfoRequest) throws SQLException {
		logger.debug("inside get all Reporties in employeeDao");
		EmployeeInfo employeeInfo = employeeInfoRequest.getEmployeeInfo().get(0);
		return jdbcTemplate.query(GETALLREPORTIES + employeeInfo.getReportingManager(),
				new BeanPropertyRowMapper<>(EmployeeInfo.class));
	}

	@Override
	public String getMngrMail(String empId) throws SQLException {

		return jdbcTemplate.queryForObject(GETMNGRMAIL + empId, String.class);
	}

	@Override
	public List<String> getEmails(String empId, String mngrId) throws SQLException {
		List<String> mails = jdbcTemplate.queryForList(GETMAILID, String.class, empId, mngrId);
		logger.debug("Fetched mails in Dao : " + mails.toString());
		return mails;
	}

	@Override
	public List<EmployeeInfo> getAllEmployeeInfos() throws SQLException {
		logger.debug("inside get all employee infos in employeeDao");
		return jdbcTemplate.query(GETEMPINFOS, new BeanPropertyRowMapper<>(EmployeeInfo.class));
	}

	@Override
	public List<EmployeeSkills> getSkillsByEmpInfo(List<String> employeeSkills) {
		
		List<String> result = new ArrayList<String>();

		String queryParams = getStringQueryParameter(employeeSkills);

		String queryStrs = "select distinct(s.employee_id), CONCAT(COALESCE(e.firstname,''), ' ', COALESCE(e.middlename,''), ' ', COALESCE(e.lastname,'')) as empName from  ojas_obs.obs_employeeskilldetails s, ojas_obs.obs_employeeinfo e where s.skill_id in ( "+ queryParams+ ") and s.employee_id = e.employee_id and e.flag=1";
		
	     return jdbcTemplate.query(queryStrs, new BeanPropertyRowMapper<>(EmployeeSkills.class));
	}

	private String getStringQueryParameter(List<String> list) 
	{

		String queryParams = "\"";

		Iterator<String> iter = list.iterator();
		int i = 0;

		while (iter.hasNext()) {
			if (i != list.size()) {

				queryParams = queryParams + iter.next() + "\",\"";

			} else {

				queryParams = queryParams + "\"" + iter.next();

			}
			i++;
		}
		return queryParams = queryParams.substring(0, queryParams.length() - 2);

	}

}
