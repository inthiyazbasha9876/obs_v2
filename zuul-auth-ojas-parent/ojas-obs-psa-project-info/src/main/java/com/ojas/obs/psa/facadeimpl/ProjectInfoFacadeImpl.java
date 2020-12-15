package com.ojas.obs.psa.facadeimpl;

import static com.ojas.obs.psa.constants.UtilConstants.ADDMILESTONE;
import static com.ojas.obs.psa.constants.UtilConstants.DELETE;
import static com.ojas.obs.psa.constants.UtilConstants.FETCHED;
import static com.ojas.obs.psa.constants.UtilConstants.GETBYCONTID;
import static com.ojas.obs.psa.constants.UtilConstants.GETBYCUSTID;
import static com.ojas.obs.psa.constants.UtilConstants.GETBYPROID;
import static com.ojas.obs.psa.constants.UtilConstants.NORECORDS;
import static com.ojas.obs.psa.constants.UtilConstants.NOTEXISTS;
import static com.ojas.obs.psa.constants.UtilConstants.SAVE;
import static com.ojas.obs.psa.constants.UtilConstants.STATUSUPDATE;
import static com.ojas.obs.psa.constants.UtilConstants.UPDATE;
import static com.ojas.obs.psa.constants.UtilConstants.UPDATEMILESTONE;
import static com.ojas.obs.psa.constants.UtilConstants.VALIDATEPROJECTNAME;
import static com.ojas.obs.psa.constants.UtilConstants.ALREADYEXISTS;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
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

import com.ojas.obs.psa.facade.ProjectInfoFacade;
import com.ojas.obs.psa.model.EmpInfo;
import com.ojas.obs.psa.model.Milestone;
import com.ojas.obs.psa.model.ProjectInfo;
import com.ojas.obs.psa.model.ProjectRatecard;
import com.ojas.obs.psa.repositories.MilestoneRepository;
import com.ojas.obs.psa.repositories.ProjectInfoRepository;
import com.ojas.obs.psa.request.ProjectInfoRequest;
import com.ojas.obs.psa.response.ProjectInfoResponse;

@Service
public class ProjectInfoFacadeImpl implements ProjectInfoFacade {
	@Autowired
	private ProjectInfoRepository projectRepo;
	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private Environment env;
	@Autowired
	private MilestoneRepository milestoneRepo;

	private static final String SQL = "SELECT officialEmail,employee_id,(SELECT CONCAT(COALESCE(obs_employeeinfo.firstname,''), ' ', COALESCE(obs_employeeinfo.middlename,''), ' ', COALESCE(obs_employeeinfo.lastname,'')))  as empName FROM ojas_obs.obs_employeeinfo where employee_id in(";
	Logger logger = Logger.getLogger(ProjectInfoFacadeImpl.class);
	private static final String NOTE = " \n\n\n\nNote: This is an auto-generated mail from OBS. Please do not reply.";

	@Override
	public ResponseEntity<Object> setProjectInfo(ProjectInfoRequest infoRequest) throws SQLException, IOException {
		logger.debug("Incoming request : " + infoRequest);
		ProjectInfoResponse response = new ProjectInfoResponse();

		ProjectInfo project = infoRequest.getProjectInfo();

		if (infoRequest.getTransactionType().equalsIgnoreCase(SAVE)) {
			infoRequest.getResourceMap().setProject(project);
			project.setProjectResourceMapping(infoRequest.getResourceMap());
			ProjectRatecard rateCard = infoRequest.getRateCard();
			rateCard.setProject(project);
			project.setProjectRatecard(rateCard);
			for (Milestone ms : infoRequest.getMilestones()) {
				ms.setProjectInfo(project);
			}
			project.setMilestones(infoRequest.getMilestones());
			ProjectInfo save = projectRepo.save(project);
			if (save.getProjectId() != null) {
				sendMail(project, infoRequest.getTransactionType());
				List<ProjectInfo> projectList = new ArrayList<>();
				projectList.add(save);
				logger.info("Project added successfully!");
				response.setMessage("Project added successfully!");
				response.setStatusCode("200");
				response.setProjectList(projectList);
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
		}
		if (infoRequest.getTransactionType().equalsIgnoreCase(UPDATE)) {
			boolean exists = projectRepo.existsById(project.getProjectId());
			if (exists) {
				infoRequest.getResourceMap().setProject(project);
				project.setProjectResourceMapping(infoRequest.getResourceMap());
				ProjectRatecard rateCard = infoRequest.getRateCard();
				rateCard.setProject(project);
				project.setProjectRatecard(rateCard);
				for (Milestone ms : infoRequest.getMilestones()) {
					ms.setProjectInfo(project);
				}
				project.setMilestones(infoRequest.getMilestones());
				ProjectInfo update = projectRepo.save(project);
				if (update.getProjectId() != null) {
					sendMail(update, infoRequest.getTransactionType());
					List<ProjectInfo> projectList = new ArrayList<>();
					projectList.add(update);
					logger.info("Project updated successfully!");
					response.setMessage("Project updated successfully!");
					response.setStatusCode("200");
					response.setProjectList(projectList);
					return new ResponseEntity<>(response, HttpStatus.OK);
				}
			}
			response.setMessage(NOTEXISTS);
			response.setStatusCode("409");
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		if (infoRequest.getTransactionType().equalsIgnoreCase(DELETE)) {
			ProjectInfo fetched = projectRepo.getOne(project.getProjectId());
			if (fetched.getProjectId() != null) {
				fetched.setFlag(false);
				ProjectInfo delete = projectRepo.save(fetched);
				if (delete.getProjectId() != null) {
					logger.info("Project deleted successfully!");
					response.setMessage("Project deleted successfully!");
					response.setStatusCode("200");
					return new ResponseEntity<>(response, HttpStatus.OK);
				}
				response.setMessage(NOTEXISTS);
				response.setStatusCode("409");
				return new ResponseEntity<>(response, HttpStatus.CONFLICT);
			}
		}
		if (infoRequest.getTransactionType().equalsIgnoreCase(STATUSUPDATE)) {
			ProjectInfo fetched = projectRepo.getOne(project.getProjectId());
			if (fetched.getProjectId() != null
					&& (project.getBuStatus() != null && !project.getBuStatus().trim().isEmpty())) {
				fetched.setBuStatus(project.getBuStatus());
				fetched.setUpdatedBy(project.getUpdatedBy());
				fetched.setComment(project.getComment());
				ProjectInfo update = projectRepo.save(fetched);
				if (update.getProjectId() != null) {

					sendMail(fetched, infoRequest.getTransactionType());
					logger.info("Project has been " + project.getBuStatus());
					response.setMessage("Project has been " + project.getBuStatus());
					response.setStatusCode("200");
					return new ResponseEntity<>(response, HttpStatus.OK);
				}
				response.setMessage(NOTEXISTS);
				response.setStatusCode("409");
				return new ResponseEntity<>(response, HttpStatus.CONFLICT);
			}
			if (fetched.getProjectId() != null
					&& (project.getFinanceStatus() != null && !project.getFinanceStatus().trim().isEmpty())) {
				fetched.setFinanceStatus(project.getFinanceStatus());
				fetched.setComment(project.getComment());
				ProjectInfo update = projectRepo.save(fetched);
				if (update.getProjectId() != null) {
					sendMail(fetched, infoRequest.getTransactionType());
					logger.info("Project has been " + project.getFinanceStatus());
					response.setMessage("Project has been " + project.getFinanceStatus());
					response.setStatusCode("200");
					return new ResponseEntity<>(response, HttpStatus.OK);
				}
				response.setMessage(NOTEXISTS);
				response.setStatusCode("409");
				return new ResponseEntity<>(response, HttpStatus.CONFLICT);
			}
		}
		
		if (infoRequest.getTransactionType().equalsIgnoreCase(ADDMILESTONE)) {
			ProjectInfo fetched = projectRepo.getOne(project.getProjectId());
			if (fetched.getProjectId() != null) {
				for (Milestone ms : infoRequest.getMilestones()) {
					ms.setProjectInfo(project);
				}
				fetched.setMilestones(infoRequest.getMilestones());
				ProjectInfo update = projectRepo.save(fetched);
				if (update.getProjectId() != null) {
					logger.info("Project milestone added successfully!");
					response.setMessage("Project milestone added successfully!");
					response.setStatusCode("200");
					return new ResponseEntity<>(response, HttpStatus.OK);
				}
				response.setMessage(NOTEXISTS);
				response.setStatusCode("409");
				return new ResponseEntity<>(response, HttpStatus.CONFLICT);
			}
		}
		if (infoRequest.getTransactionType().equalsIgnoreCase(UPDATEMILESTONE)) {
			boolean exists = milestoneRepo.existsById(infoRequest.getMilestones().get(0).getMilestoneId());
			if (exists) {
				infoRequest.getMilestones().get(0).setProjectInfo(project);
				Milestone update = milestoneRepo.save(infoRequest.getMilestones().get(0));
				if (update.getMilestoneId() != null) {
					logger.info("Project milestone updated successfully!");
					response.setMessage("Project milestone updated successfully!");
					response.setStatusCode("200");
					return new ResponseEntity<>(response, HttpStatus.OK);
				}
				response.setMessage(NOTEXISTS);
				response.setStatusCode("409");
				return new ResponseEntity<>(response, HttpStatus.CONFLICT);
			}
		}
		logger.error("Failed to process request");
		response.setMessage("Failed to process the request!");
		response.setStatusCode("409");
		return new ResponseEntity<>(response, HttpStatus.CONFLICT);
	}

	@Override
	public ResponseEntity<Object> getProjectInfo(ProjectInfoRequest infoRequest) throws SQLException {
		List<ProjectInfo> infos = new ArrayList<>();
		ProjectInfoResponse response = new ProjectInfoResponse();
		if (infoRequest.getTransactionType().equalsIgnoreCase(GETBYCUSTID)) {
			infos = projectRepo.getByCustomerId(infoRequest.getProjectInfo().getCustomerId());
			if (!infos.isEmpty()) {
				response.setProjectList(infos);
				response.setMessage(FETCHED);
				response.setStatusCode("200");
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			response.setMessage(NORECORDS);
			response.setStatusCode("200");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		if (infoRequest.getTransactionType().equalsIgnoreCase(GETBYCONTID)) {
			infos = projectRepo.getByContractId(infoRequest.getProjectInfo().getContractId());
			if (!infos.isEmpty()) {
				response.setProjectList(infos);
				response.setMessage(FETCHED);
				response.setStatusCode("200");
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			response.setMessage(NORECORDS);
			response.setStatusCode("200");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		if (infoRequest.getTransactionType().equalsIgnoreCase(GETBYPROID)) {
			ProjectInfo info = projectRepo.getOne(infoRequest.getProjectInfo().getProjectId());

			if (info.getProjectId() != null) {
				infos.add(info);
				response.setProjectList(infos);
				response.setMessage(FETCHED);
				response.setStatusCode("200");
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			response.setMessage(NORECORDS);
			response.setStatusCode("200");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		if (infoRequest.getTransactionType().equalsIgnoreCase(VALIDATEPROJECTNAME)) {
			String proName = infoRequest.getProjectInfo().getProjectName();
			List<String> proNames = projectRepo.getAllProjectNames();

			if(validateName(proName, proNames)) {
				response.setMessage(ALREADYEXISTS);
				response.setStatusCode("200");
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
			response.setStatusCode("200");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		infos = projectRepo.getAllProjects();
		if (!infos.isEmpty()) {
			response.setProjectList(infos);
			response.setMessage(FETCHED);
			response.setStatusCode("200");
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		response.setMessage(NORECORDS);
		response.setStatusCode("200");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	boolean validateName(String curr, List<String> proNames) {
		boolean exists = false;
		for (String name : proNames) {
			if (curr.replaceAll("\\s+","").equalsIgnoreCase(name.replaceAll("\\s+",""))) {
				exists = true;
			}
		}
		return exists;
	}
	public Boolean sendMail(ProjectInfo project, String transType) {
		Boolean sent = false;
		List<EmpInfo> empList = getEmployees(project, transType);
		logger.debug("Fetched employees : " + empList);
		String subject = null;
		String body = null;
		if (transType.equalsIgnoreCase(SAVE)) {
			EmpInfo name = empList.stream().filter(emp -> emp.getEmployeeId().equalsIgnoreCase(project.getCreatedBy()))
					.collect(Collectors.toList()).get(0);
			subject = project.getProjectName() + " project has been created";
			body = "Hi,\n" + name.getEmpName() + "(" + name.getEmployeeId() + ") has created "
					+ project.getProjectName() + " project and it is pending for approval." + NOTE;
		}
		if (transType.equalsIgnoreCase(UPDATE)) {
			EmpInfo name = empList.stream().filter(emp -> emp.getEmployeeId().equalsIgnoreCase(project.getUpdatedBy()))
					.collect(Collectors.toList()).get(0);
			subject = project.getProjectName() + " project has been updated";
			body = "Hi,\n" + name.getEmpName() + "(" + name.getEmployeeId() + ") has updated "
					+ project.getProjectName() + "(" + project.getProjectId() + ")"
					+ " project and it is pending for approval." + NOTE;
		}
		if (transType.equalsIgnoreCase(STATUSUPDATE) && ("Approved".equalsIgnoreCase(project.getBuStatus())
				&& "Pending".equalsIgnoreCase(project.getFinanceStatus()))) {
			logger.debug("mail emp: " + project.getUpdatedBy());
			EmpInfo name = empList.stream().filter(emp -> emp.getEmployeeId().equalsIgnoreCase(project.getUpdatedBy()))
					.collect(Collectors.toList()).get(0);
			subject = project.getProjectName() + " project has been approved";
			body = "Hi,\n" + name.getEmpName() + "(" + name.getEmployeeId() + ") has been approved "
					+ project.getProjectName() + "(" + project.getProjectId() + ")"
					+ " project and it is pending for approval from finance." + NOTE;
		}
		if (transType.equalsIgnoreCase(STATUSUPDATE) && ("Approved".equalsIgnoreCase(project.getBuStatus())
				&& "Approved".equalsIgnoreCase(project.getFinanceStatus()))) {
			EmpInfo name = empList.stream().filter(emp -> emp.getEmployeeId().equalsIgnoreCase(project.getUpdatedBy()))
					.collect(Collectors.toList()).get(0);
			subject = project.getProjectName() + " project has been approved";
			body = "Hi,\n" + name.getEmpName() + "(" + name.getEmployeeId() + ") has been approved "
					+ project.getProjectName() + "(" + project.getProjectId() + ")" + " project." + NOTE;
		}
		if (transType.equalsIgnoreCase(STATUSUPDATE) && ("Rejected".equalsIgnoreCase(project.getBuStatus())
				|| "Rejected".equalsIgnoreCase(project.getFinanceStatus()))) {
			EmpInfo name = empList.stream().filter(emp -> emp.getEmployeeId().equalsIgnoreCase(project.getUpdatedBy()))
					.collect(Collectors.toList()).get(0);
			subject = project.getProjectName() + " project has been rejected";
			body = "Hi,\n" + name.getEmpName() + "(" + name.getEmployeeId() + ") has been rejected "
					+ project.getProjectName() + "(" + project.getProjectId() + ")" + " project. \nReason : "
					+ project.getComment() + NOTE;
		}
		logger.debug("Mail msg :" + body);
		List<String> emialIds = empList.stream().map(x -> x.getOfficialEmail()).collect(Collectors.toList());
		emialIds.add(env.getProperty("financemail"));
		String[] to = emialIds.stream().toArray(String[]::new);
		if (to != null) {
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setFrom(env.getProperty("spring.mail.username"));
			mailMessage.setTo(to);
			mailMessage.setSubject(subject);
			mailMessage.setText(body);
			javaMailSender.send(mailMessage);
			sent = true;
		}
		return sent;
	}

	List<EmpInfo> getEmployees(ProjectInfo project, String transType) {
		StringBuilder queryBuilder = new StringBuilder(SQL);
		List<String> empIds = null;
		if (transType.equalsIgnoreCase(SAVE)) {
			empIds = Arrays.asList(project.getBuHead(), project.getSbuHead(),
					project.getProjectResourceMapping().getProjectManager(),
					project.getProjectResourceMapping().getTechLead(), project.getCreatedBy());
		} else {
			empIds = Arrays.asList(project.getBuHead(), project.getSbuHead(),
					project.getProjectResourceMapping().getProjectManager(),
					project.getProjectResourceMapping().getTechLead(), project.getUpdatedBy());
		}
		Object[] ids = new Object[empIds.size()];
		for (int i = 0; i < empIds.size(); i++) {
			ids[i] = empIds.get(i);
			queryBuilder.append(" ?");
			if (i != empIds.size() - 1)
				queryBuilder.append(",");
		}
		queryBuilder.append(")");
		String query = queryBuilder.toString();
		logger.debug("Query : " + query);
		jdbcTemplate = new JdbcTemplate(getDataSource());
		return jdbcTemplate.query(query, ids, new BeanPropertyRowMapper<EmpInfo>(EmpInfo.class));
	}

	DataSource getDataSource() {
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
		driverManagerDataSource.setUrl(env.getProperty("db.url"));
		driverManagerDataSource.setUsername(env.getProperty("spring.datasource.username"));
		driverManagerDataSource.setPassword(env.getProperty("spring.datasource.password"));
		driverManagerDataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
		return driverManagerDataSource;
	}
}
