package com.obs.lms.facade;

import static com.obs.lms.constants.Constants.DELETE;
import static com.obs.lms.constants.Constants.REJECTED;
import static com.obs.lms.constants.Constants.APPROVED;
import static com.obs.lms.constants.Constants.WITHDRAWN;
import static com.obs.lms.constants.Constants.PENDING;

import static com.obs.lms.constants.Constants.GETALL;
import static com.obs.lms.constants.Constants.GETALLLEAVEBAL;
import static com.obs.lms.constants.Constants.GETAllLEAVEINFO;
import static com.obs.lms.constants.Constants.GETBYID;
import static com.obs.lms.constants.Constants.GETBYMANAGER;
import static com.obs.lms.constants.Constants.GETFILE;
import static com.obs.lms.constants.Constants.NORECORDS;
import static com.obs.lms.constants.Constants.SAVE;
import static com.obs.lms.constants.Constants.SUCCESS;
import static com.obs.lms.constants.Constants.UPDATESTATUS;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.obs.lms.model.EmpInfo;
import com.obs.lms.model.LeaveBalance;
import com.obs.lms.model.LeaveInfo;
import com.obs.lms.repository.LeaveBalanceRepo;
import com.obs.lms.repository.LeaveInfoRepository;
import com.obs.lms.request.LmsRequest;
import com.obs.lms.response.LmsResponse;

@Service
public class LmsFacadeImpl implements LmsFacade {

	@Autowired
	private LeaveInfoRepository lmsRepo;
	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private Environment env;
	@Autowired
	private LeaveBalanceRepo leaveBalanceRepo;

	Logger logger = Logger.getLogger(this.getClass());
	private static final String SQL = "SELECT officialEmail,employee_id,(SELECT CONCAT(obs_employeeinfo.firstname,' ',obs_employeeinfo.middlename,' ',obs_employeeinfo.lastname))  as empName FROM ojas_obs.obs_employeeinfo where employee_Id in (select reportingManager from ojas_obs.obs_employeeinfo where employee_Id=? union select employee_Id from ojas_obs.obs_employeeinfo where employee_Id= ?)";

	@Override
	public ResponseEntity<Object> setLms(LmsRequest lmsreq) throws IOException {
		LmsResponse response = new LmsResponse();
		LeaveInfo leave = lmsreq.getLeaveInfo();

		if (lmsreq.getTransationType().equalsIgnoreCase(SAVE)) {
			uploadFile(leave);
			LeaveInfo save = lmsRepo.save(leave);
			statusUpdate(lmsreq);
			if (save.getId() != null) {
				sendMail(leave);
				logger.info("Leave saved successfully!");
				response.setMessage("Leave saved successfully!");
				response.setStatusCode("200");
				return new ResponseEntity<>(response, HttpStatus.OK);
			}

		}
		if (lmsreq.getTransationType().equalsIgnoreCase(UPDATESTATUS)) {
			LeaveInfo leave1 = lmsRepo.getOne(lmsreq.getLeaveInfo().getId());
			leave1.setStatus(lmsreq.getLeaveInfo().getStatus());
			leave1.setComment(lmsreq.getLeaveInfo().getComment());
			leave1.setUpdatedBy(lmsreq.getLeaveInfo().getUpdatedBy());
			leave1.setUpdatedOn(lmsreq.getLeaveInfo().getUpdatedOn());
			if (leave1.getId() != null && leave1.getStatus() != null) {
				if (leave1.getStatus().equalsIgnoreCase(REJECTED) || leave1.getStatus().equalsIgnoreCase(WITHDRAWN)) {
					statusRejectedwithdrawn(lmsreq);
				}
				if(leave1.getApplyType().equalsIgnoreCase("LeaveGrant")) {
                    statusUpdate(lmsreq);
                }
				LeaveInfo save2 = lmsRepo.save(leave1);

				if (save2.getId() != null) {
					sendMail(leave1);
					logger.info("Leave has been " + save2.getStatus());
					response.setMessage("Leave has been " + save2.getStatus());
					response.setMessage(SUCCESS);
					response.setStatusCode("200");
					return new ResponseEntity<>(response, HttpStatus.OK);
				}

			}
		}

		if (lmsreq.getTransationType().equalsIgnoreCase(DELETE)) {
			LeaveInfo leave2 = lmsRepo.getOne(lmsreq.getLeaveInfo().getId());
			if (leave2.getId() != null) {
				leave2.setFlag(lmsreq.getLeaveInfo().getFlag());
				LeaveInfo info = lmsRepo.save(leave2);
				if (info.getId() != null) {
					sendMail(leave2);
					logger.info("Leave deleted successfully!");
					response.setMessage("Leave deleted successfully!");
					response.setStatusCode("200");
					return new ResponseEntity<>(response, HttpStatus.OK);

				}

			}

		}

		logger.error("Failed to process request");
		response.setMessage("Failed to process the request!");
		response.setStatusCode("409");
		return new ResponseEntity<>(response, HttpStatus.CONFLICT);
	}

	@Override
	public ResponseEntity<Object> getLms(LmsRequest lmsreq) throws IOException {
		LmsResponse response = new LmsResponse();
		LeaveInfo leaveInfo = lmsreq.getLeaveInfo();
		List<LeaveInfo> arrayList = new ArrayList<>();
		if (lmsreq.getTransationType().equalsIgnoreCase(GETBYID)) {
			LeaveInfo leave = lmsRepo.getOne(leaveInfo.getId());
			arrayList.add(leave);
			response.setStatusCode("200");
			response.setMessage(SUCCESS);
			return validateResult(arrayList);
		}

		if (lmsreq.getTransationType().equalsIgnoreCase(GETAllLEAVEINFO)) {
			logger.debug("recived data into facade getbyempid ");
			arrayList = lmsRepo.getByEmpId(lmsreq.getLeaveInfo().getEmpId());
			if (!arrayList.isEmpty()) {
				response.setLeaveInfo(arrayList);
				response.setStatusCode("200");
				response.setMessage(SUCCESS);
				return new ResponseEntity<>(response, HttpStatus.OK);

			}
		}

		if (lmsreq.getTransationType().equalsIgnoreCase(GETALLLEAVEBAL)) {
			logger.debug("recived data into facade getbyempid ");
			LeaveBalance byEmpId = leaveBalanceRepo.getAllLeaveBalByEmpId(lmsreq.getLeaveBalance().getEmpId());
			if (byEmpId != null) {
				response.setLeaveBalList(byEmpId);
				response.setStatusCode("200");
				response.setMessage(SUCCESS);
				return new ResponseEntity<>(response, HttpStatus.OK);

			}
		}

		if (lmsreq.getTransationType().equalsIgnoreCase(GETBYMANAGER)) {
			logger.debug("recived data into facade getbyreportingmanagerid ");

			arrayList = lmsRepo.getByManagerId(lmsreq.getLeaveInfo().getApplyTo());

			if (!arrayList.isEmpty()) {
				response.setLeaveInfo(arrayList);
				response.setStatusCode("200");
				response.setMessage(SUCCESS);
				return new ResponseEntity<>(response, HttpStatus.OK);

			}
		}

		if (lmsreq.getTransationType().equalsIgnoreCase(GETALL)) {
			arrayList = lmsRepo.findAll();
			response.setStatusCode("200");
			response.setMessage(SUCCESS);
			return validateResult(arrayList);
		}

		if (lmsreq.getTransationType().equalsIgnoreCase(GETFILE)) {
			String filePath = null;
			filePath = lmsRepo.getFilePath(lmsreq.getLeaveInfo().getId());
			if (filePath == null) {
				response.setMessage("File not found");
				response.setStatusCode("409");
				return new ResponseEntity<>(response, HttpStatus.CONFLICT);
			}
			byte[] byteFile = Files.readAllBytes(new File(filePath).toPath());
			String file = Base64.getEncoder().encodeToString(byteFile);
			LeaveInfo info = new LeaveInfo();
			info.setAttachment(file);
			arrayList.add(info);
			response.setLeaveInfo(arrayList);
			response.setMessage(SUCCESS);
			response.setStatusCode("200");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}

		response.setMessage(NORECORDS);
		response.setStatusCode("409");
		return new ResponseEntity<>(response, HttpStatus.CONFLICT);
	}

	public Boolean sendMail(LeaveInfo leaveInfo) {

		Boolean sent = false;
		Object[] args = { leaveInfo.getEmpId(), leaveInfo.getEmpId() };
		jdbcTemplate = new JdbcTemplate(getDataSource());
		List<EmpInfo> emp = jdbcTemplate.query(SQL, args, new BeanPropertyRowMapper<EmpInfo>(EmpInfo.class));
		String[] to = emp.stream().map(x -> x.getOfficialEmail()).collect(Collectors.toList()).stream()
				.toArray(String[]::new);
		String msg = "";
		String sub = "";
		if ("Pending".equalsIgnoreCase(leaveInfo.getStatus())) {
			msg = "Hi,\n\n\t" + emp.get(0).getEmpName() + " (" + emp.get(0).getEmployeeId() + ") "
					+ env.getProperty("applied") + " " + leaveInfo.getFromDate() + " to " + leaveInfo.getToDate() + ".";
			sub = "Leave Applied";
		}
		if ("withdrawn".equalsIgnoreCase(leaveInfo.getStatus()) && !leaveInfo.getFlag()) {
			msg = env.getProperty("Withdrawn") + " " + leaveInfo.getFromDate() + " to" + leaveInfo.getToDate() + ".";
			sub = "Leave Withdrawn";
		}
		if ("Approved".equalsIgnoreCase(leaveInfo.getStatus())) {
			msg = "Hi" + " " + emp.get(0).getEmpName() + "(" + emp.get(0).getEmployeeId() + ")" + "," + "\n\n"
					+ env.getProperty("accepted") + " " + leaveInfo.getFromDate() + " to " + leaveInfo.getToDate() + " "
					+ "has been approved" + ".";

			sub = "Leave accepted";
		}
		if ("Rejected".equalsIgnoreCase(leaveInfo.getStatus())) {
			msg = "Hi" + " " + emp.get(0).getEmpName() + "(" + emp.get(0).getEmployeeId() + ")" + "," + "\n"
					+ env.getProperty("rejected") + " " + leaveInfo.getFromDate() + " to " + leaveInfo.getToDate() + " "
					+ "has been rejected" + "." + "\n" + "Reason :" + " " + "due to" + " " + leaveInfo.getComment()
					+ ".";
			System.out.println("levae :" + msg);
			sub = "Leave rejected";
		}
		if (to != null) {
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setFrom(env.getProperty("spring.mail.username"));
			mailMessage.setTo(to);
			if (null != leaveInfo.getCcTo()) {
				mailMessage.setCc(leaveInfo.getCcTo());
			}
			mailMessage.setSubject(sub);
			mailMessage.setText(msg);
			javaMailSender.send(mailMessage);
			sent = true;
		}
		return sent;
	}

	private void uploadFile(LeaveInfo leave) throws IOException {
		if (leave.getAttachment() != null && !leave.getAttachment().trim().isEmpty()) {
			byte[] bytes = Base64.getDecoder().decode(leave.getAttachment());
			String[] filenames = leave.getFileName().split("\\.");
			Path path = Paths.get(env.getProperty("file_directory") + leave.getFileName());
			Path savedPath = null;
			int count = 1;
			while (Files.exists(path)) {
				path = Paths.get(env.getProperty("file_directory") + filenames[0] + count++ + "." + filenames[1]);
			}
			savedPath = Files.write(path, bytes, StandardOpenOption.CREATE);
			leave.setFilePath(savedPath.toUri().getPath());

		}
	}

	private ResponseEntity<Object> validateResult(List<LeaveInfo> leave) {
		LmsResponse response = new LmsResponse();
		if (leave.isEmpty()) {

			response.setMessage(NORECORDS);
			response.setStatusCode("409");
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		response.setLeaveInfo(leave);
		response.setMessage("Leave fetched successfully");
		response.setStatusCode("200");
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	private void statusUpdate(LmsRequest lmsreq) {

		LeaveInfo leave1 = lmsRepo.getOne(lmsreq.getLeaveInfo().getId());
		LeaveBalance byEmpId = leaveBalanceRepo.getAllLeaveBalByEmpId(leave1.getEmpId());
		if (lmsreq.getLeaveInfo().getStatus().equalsIgnoreCase(PENDING)
				&& leave1.getApplyType().equalsIgnoreCase("LeaveApply")) {

			if (leave1.getLeaveType().equalsIgnoreCase("Sick leave"))
				byEmpId.setConsumedSickLeave(byEmpId.getConsumedSickLeave() + leave1.getCountNumOfDays());
			if (leave1.getLeaveType().equalsIgnoreCase("Casual leave"))
				byEmpId.setConsumedCasualLeave(byEmpId.getConsumedCasualLeave() + leave1.getCountNumOfDays());
			if (leave1.getLeaveType().equalsIgnoreCase("comp off"))
				byEmpId.setConsumedCompOff(byEmpId.getConsumedCompOff() + leave1.getCountNumOfDays());
			if (leave1.getLeaveType().equalsIgnoreCase("Loss of Pay"))
				byEmpId.setLossOfPay(byEmpId.getLossOfPay() + leave1.getCountNumOfDays());
			if (leave1.getLeaveType().equalsIgnoreCase("Maternity leave"))
				byEmpId.setConsumedMaternityLeave(byEmpId.getConsumedMaternityLeave() + leave1.getCountNumOfDays());
		}

		if (leave1.getApplyType().equalsIgnoreCase("LeaveGrant")
				&& lmsreq.getLeaveInfo().getStatus().equalsIgnoreCase("approved")) {

			if (leave1.getLeaveType().equalsIgnoreCase("comp off"))
				byEmpId.setTotalCompOff(byEmpId.getTotalCompOff() + leave1.getCountNumOfDays());
			if (leave1.getLeaveType().equalsIgnoreCase("Maternity leave"))
				byEmpId.setTotalMaternityLeave(byEmpId.getTotalMaternityLeave() + leave1.getCountNumOfDays());

		}
		leaveBalanceRepo.save(byEmpId);

	}

	private void statusRejectedwithdrawn(LmsRequest lmsreq) {

		LeaveInfo leave1 = lmsRepo.getOne(lmsreq.getLeaveInfo().getId());
		LeaveBalance byEmpId = leaveBalanceRepo.getAllLeaveBalByEmpId(leave1.getEmpId());
		if ((lmsreq.getLeaveInfo().getStatus().equalsIgnoreCase(REJECTED)
				|| lmsreq.getLeaveInfo().getStatus().equalsIgnoreCase(WITHDRAWN))
				&& leave1.getApplyType().equalsIgnoreCase("LeaveApply")) {

			if (leave1.getLeaveType().equalsIgnoreCase("Sick leave"))
				byEmpId.setConsumedSickLeave(byEmpId.getConsumedSickLeave() - leave1.getCountNumOfDays());
			if (leave1.getLeaveType().equalsIgnoreCase("Casual leave"))
				byEmpId.setConsumedCasualLeave(byEmpId.getConsumedCasualLeave() - leave1.getCountNumOfDays());
			if (leave1.getLeaveType().equalsIgnoreCase("comp off"))
				byEmpId.setConsumedCompOff(byEmpId.getConsumedCompOff() - leave1.getCountNumOfDays());
			if (leave1.getLeaveType().equalsIgnoreCase("Loss of Pay"))
				byEmpId.setLossOfPay(byEmpId.getLossOfPay() - leave1.getCountNumOfDays());
			if (leave1.getLeaveType().equalsIgnoreCase("Maternity leave"))
				byEmpId.setConsumedMaternityLeave(byEmpId.getConsumedMaternityLeave() - leave1.getCountNumOfDays());
		}

		if (leave1.getApplyType().equalsIgnoreCase("LeaveGrant")
				&& (lmsreq.getLeaveInfo().getStatus().equalsIgnoreCase(REJECTED)
						|| lmsreq.getLeaveInfo().getStatus().equalsIgnoreCase(WITHDRAWN))) {

			if (leave1.getLeaveType().equalsIgnoreCase("comp off"))
				byEmpId.setTotalCompOff(byEmpId.getTotalCompOff() - leave1.getCountNumOfDays());
			if (leave1.getLeaveType().equalsIgnoreCase("Maternity leave"))
				byEmpId.setTotalMaternityLeave(byEmpId.getTotalMaternityLeave() - leave1.getCountNumOfDays());

		}
		leaveBalanceRepo.save(byEmpId);

	}

	public DataSource getDataSource() {
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
		driverManagerDataSource.setUrl(env.getProperty("db.url"));
		driverManagerDataSource.setUsername(env.getProperty("spring.datasource.username"));
		driverManagerDataSource.setPassword(env.getProperty("spring.datasource.password"));
		driverManagerDataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
		return driverManagerDataSource;
	}

}
