package com.ojas.obs.psa.facadeimpl;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.activation.DataSource;

import org.apache.naming.factory.SendMailFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.ojas.obs.psa.controller.ProjectInfoController;
import com.ojas.obs.psa.model.ProjectInfo;
import com.ojas.obs.psa.model.ProjectRatecard;
import com.ojas.obs.psa.model.ProjectResourceMapping;
import com.ojas.obs.psa.repositories.ProjectInfoRepository;
import com.ojas.obs.psa.request.ProjectInfoRequest;
import com.ojas.obs.psa.response.ErrorResponse;
import com.ojas.obs.psa.response.ProjectInfoResponse;

public class ProjectInfoFacadeImplTest {

	@Mock
	private ProjectInfoController unitController;
	
	@Mock
	private DataSource dataSource;
	
	@Mock
	private DriverManagerDataSource driverManagerDataSource;
	
	@Mock
	private PreparedStatement stmt;
	
	@Mock
	private SendMailFactory sendMail;
	
	@Mock
	private Connection c;

	@InjectMocks
	@Spy
	private ProjectInfoFacadeImpl infoFacade;
	
	@Mock
	private ProjectInfoRepository projectRepo;
	
	@Mock
	private JdbcTemplate jdbcTemplate;
	
	@Mock
	private Environment env;
	
	@Spy
	private ProjectInfoRequest projectInfoRequest;
	
	@Spy
	private ErrorResponse errorResponse = new ErrorResponse();
	
	@Spy
	private ProjectInfoResponse projectInfoResponse = new ProjectInfoResponse();
	
	@Spy
	private ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
	
	@Spy
	private ResponseEntity<Object> conflict = new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
	
	@Spy
	private ResponseEntity<Object> successResponse = new ResponseEntity<>(projectInfoResponse, HttpStatus.OK);
	
	@Spy
	private ProjectInfo projectInfo;
	
	@Spy
	private ProjectRatecard projectRatecard;
	
	@Spy
	private ProjectResourceMapping projectResourceMapping;

	@Before
	public void init() throws Exception {
		projectRepo = mock(ProjectInfoRepository.class);
		driverManagerDataSource = mock(DriverManagerDataSource.class);
		MockitoAnnotations.initMocks(this);
		//setCollaborator(infoFacade, "driverManagerDataSource", driverManagerDataSource);
	}
	
	public void setCollaborator(Object object, String name, Object service) throws Exception {
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);
	}

	public ProjectInfo getProjectInfo() {
		Set<String> taskset = new HashSet<>();
		taskset.add("Obs Psa Tasks");
		ProjectInfo projectInfo = new ProjectInfo();
		//Date date = new Date(0);
		projectInfo.setContractId(1);
		projectInfo.setCustomerId(2);
		projectInfo.setDeliveryLocation("10");
		projectInfo.setProjectId(52);
		projectInfo.setProjectType("Customer");
		projectInfo.setProjectDescription("good");
		projectInfo.setLocationType("5");
		projectInfo.setProjectName("obs");
		projectInfo.setProjectRatecard(projectRatecard);
		projectInfo.setBuStatus("good");
		projectInfo.setFinanceStatus("salary");
		projectInfo.setProjectResourceMapping(projectResourceMapping);
		projectInfo.setStartDate("2019-12-01");
		projectInfo.setServicecategory("3");
		projectInfo.setBuHead("18162");
		projectInfo.setSbuHead("18167");
		projectInfo.setCreatedBy("ajay");
		projectInfo.setUpdatedBy("ravi");
		projectInfo.setComment("good");
		projectInfo.setTasks(taskset);
		projectInfo.setEndDate("2019-12-30");
		return projectInfo;
	}

	public ProjectRatecard projectRateCardList() {
		ProjectRatecard projectRatecard = new ProjectRatecard();
		projectRatecard.setBillingType("java");
		projectRatecard.setRatecardId(5);
		projectRatecard.setRateType("8");
		projectRatecard.setServiceType("7");
		projectRatecard.setProjectValue(20.3);
		return projectRatecard;
	}

	public ProjectResourceMapping getProjectResouce() {
		Set<String> taskset = new HashSet<>();
		taskset.add("Obs Psa Tasks1");
		ProjectResourceMapping projectResourceMapping = new ProjectResourceMapping();
		projectResourceMapping.setProjectManager("ajay");
		//projectResourceMapping.setResourceCount(6);
		projectResourceMapping.setResourceMappingId(52);
		projectResourceMapping.setTechLead("suri");
		projectResourceMapping.setTechStack(taskset);
		return projectResourceMapping;
	}

	@Test
	public void testSave() throws SQLException, IOException {
		Set<String> protask = new HashSet<String>();
		List<String> emails = new ArrayList<>();
		emails.add(env.getProperty("ajay@gmail.com"));
		emails.add(env.getProperty("ajay@gmail.com"));
		protask.add("psa projectInfo");
		//Date date = new Date(0);
		ProjectInfoRequest projectInfoRequest = new ProjectInfoRequest();
		projectInfoRequest.setTransactionType("save");
		projectInfoRequest.setProjectInfo(getProjectInfo());
		projectInfoRequest.setRateCard(projectRateCardList());
		projectInfoRequest.setResourceMap(getProjectResouce());
		ProjectInfo pro = new ProjectInfo();
		pro.setProjectId(5);
		pro.setProjectName("obs");
		pro.setProjectDescription("good");
		pro.setStartDate("2019-12-01");
		pro.setEndDate("2019-12-30");
		pro.setCustomerId(4);
		pro.setContractId(8);
		pro.setServicecategory("verygood");
		pro.setDeliveryLocation("hyd");
		pro.setProjectType("Customer");
		pro.setSbuHead("18167");
		pro.setBuHead("18162");
		pro.setCreatedBy("jay");
		pro.setUpdatedBy("java");
		pro.setComment("nice");
		pro.setTasks(protask);
		pro.setLocationType("ban");
		pro.setProjectRatecard(projectRateCardList());
		pro.setProjectResourceMapping(getProjectResouce());
		projectInfoRequest.setProjectInfo(pro);
		ProjectRatecard prc = new ProjectRatecard();
		prc.setProject(getProjectInfo());
		projectInfoRequest.setRateCard(prc);
		ProjectResourceMapping prm = new ProjectResourceMapping();
		prm.setProject(getProjectInfo());
		projectInfoRequest.setResourceMap(prm);
		List<String> projectInf = new ArrayList<String>();
		projectInf.add("18162");
		projectInf.add("18167");
		emails.addAll(projectInf);
		
		when(projectRepo.save(pro)).thenReturn(pro);
		when(c.prepareStatement(any(String.class))).thenReturn(stmt);
		when(driverManagerDataSource.getConnection()).thenReturn(c);
		doReturn(true).when(infoFacade).sendMail(any(), anyString());	
		ResponseEntity<Object> saveStatus = infoFacade.setProjectInfo(projectInfoRequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	//@Test
	public void testUpdate() throws SQLException, IOException {
		ProjectInfoRequest projectInfoRequest = new ProjectInfoRequest();
		projectInfoRequest.setTransactionType("update");
		projectInfoRequest.setProjectInfo(getProjectInfo());
		projectInfoRequest.setRateCard(projectRateCardList());
		projectInfoRequest.setResourceMap(getProjectResouce());
		ProjectInfo pro = new ProjectInfo();
		pro.setProjectId(5);
		pro.setProjectRatecard(projectRateCardList());
		projectInfoRequest.setProjectInfo(pro);
		ProjectRatecard prc = new ProjectRatecard();
		prc.setProject(getProjectInfo());
		projectInfoRequest.setRateCard(prc);
		ProjectResourceMapping prm = new ProjectResourceMapping();
		prm.setProject(getProjectInfo());
		projectInfoRequest.setResourceMap(prm);
		int id = projectInfoRequest.getProjectInfo().getProjectId();
		when(projectRepo.existsById(id)).thenReturn(true);
		when(projectRepo.save(pro)).thenReturn(pro);
		ResponseEntity<Object> saveStatus = infoFacade.setProjectInfo(projectInfoRequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	@Test
	public void testUpdateNull() throws SQLException, IOException {
		ProjectInfoRequest projectInfoRequest = new ProjectInfoRequest();
		projectInfoRequest.setTransactionType("update");
		ProjectInfo pro = new ProjectInfo();
		pro.setProjectId(5);
		projectInfoRequest.setProjectInfo(pro);
		int id = projectInfoRequest.getProjectInfo().getProjectId();
		when(projectRepo.existsById(id)).thenReturn(false);
		ResponseEntity<Object> saveStatus = infoFacade.setProjectInfo(projectInfoRequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	

	@Test
	public void getByProIdNoProjects() throws SQLException {
		ProjectInfoRequest projectInfoRequest = new ProjectInfoRequest();
		projectInfoRequest.setProjectInfo(getProjectInfo());
		projectInfoRequest.setTransactionType("getByProId");
		ProjectInfo p=new ProjectInfo();
		when( projectRepo.getOne(projectInfoRequest.getProjectInfo().getProjectId())).thenReturn(p);
		ResponseEntity<Object> saveStatus = infoFacade.getProjectInfo(projectInfoRequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void testGetByCustomerId() throws SQLException {
		projectInfoRequest = new ProjectInfoRequest();
		List<ProjectInfo> pro = new ArrayList<ProjectInfo>();
		ProjectInfo ProjectInfo = new ProjectInfo();
		ProjectInfo.setCustomerId(5);
		pro.add(ProjectInfo);
		projectInfoRequest.setProjectInfo(ProjectInfo);
		projectInfoRequest.setTransactionType("getByCustId");
		int id = projectInfoRequest.getProjectInfo().getCustomerId();
		when(projectRepo.getByCustomerId(id)).thenReturn(pro);
		
		ResponseEntity<Object> saveStatus = infoFacade.getProjectInfo(projectInfoRequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void testNOProjectsCustomerId() throws SQLException {
		projectInfoRequest = new ProjectInfoRequest();
		List<ProjectInfo> pro = new ArrayList<ProjectInfo>();
		ProjectInfo ProjectInfo = new ProjectInfo();
		ProjectInfo.setCustomerId(5);
		pro.add(ProjectInfo);
		projectInfoRequest.setProjectInfo(ProjectInfo);
		projectInfoRequest.setTransactionType("getByCustId");
		int id = projectInfoRequest.getProjectInfo().getCustomerId();
		when(projectRepo.getByContractId(id)).thenReturn(pro);
		
		ResponseEntity<Object> saveStatus = infoFacade.getProjectInfo(projectInfoRequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void testGetByContractId() throws SQLException {
		projectInfoRequest = new ProjectInfoRequest();
		List<ProjectInfo> pro = new ArrayList<ProjectInfo>();
		ProjectInfo ProjectInfo = new ProjectInfo();
		ProjectInfo.setContractId(6);
		pro.add(ProjectInfo);
		projectInfoRequest.setProjectInfo(ProjectInfo);
		projectInfoRequest.setTransactionType("getByContId");
		int id = projectInfoRequest.getProjectInfo().getContractId();
		when(projectRepo.getByContractId(id)).thenReturn(pro);
		
		ResponseEntity<Object> saveStatus = infoFacade.getProjectInfo(projectInfoRequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void deleteTest() throws SQLException, IOException {
		projectInfoRequest = new ProjectInfoRequest();
		projectInfoRequest.setTransactionType("delete");
		List<ProjectInfo> pro = new ArrayList<ProjectInfo>();
		ProjectInfo ProjectInfo = new ProjectInfo();
		ProjectInfo.setProjectId(5);
		pro.add(ProjectInfo);
		projectInfoRequest.setProjectInfo(ProjectInfo);
		int id = projectInfoRequest.getProjectInfo().getProjectId();
		when(projectRepo.getOne(id)).thenReturn(ProjectInfo);
		when(projectRepo.save(ProjectInfo)).thenReturn(ProjectInfo);
		
		ResponseEntity<Object> saveStatus = infoFacade.setProjectInfo(projectInfoRequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void deleteTestProjectNull() throws SQLException, IOException {
		projectInfoRequest = new ProjectInfoRequest();
		projectInfoRequest.setTransactionType("delete");
		List<ProjectInfo> pro = new ArrayList<ProjectInfo>();
		ProjectInfo projectInfo = new ProjectInfo();
		projectInfo.setProjectId(5);
		pro.add(projectInfo);
		projectInfoRequest.setProjectInfo(projectInfo);
		int id = projectInfoRequest.getProjectInfo().getProjectId();
		when(projectRepo.getOne(id)).thenReturn(projectInfo);
		List<ProjectInfo> pro1 = new ArrayList<ProjectInfo>();
		ProjectInfo projectInfo1 = new ProjectInfo();
		projectInfo1.setProjectId(null);
		pro1.add(projectInfo1);
		when(projectRepo.save(projectInfo)).thenReturn(projectInfo1);
		ResponseEntity<Object> saveStatus = infoFacade.setProjectInfo(projectInfoRequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void statusTestNull() throws SQLException, IOException {
		projectInfoRequest = new ProjectInfoRequest();
		projectInfoRequest.setTransactionType("statusUpdate");
		List<ProjectInfo> pro = new ArrayList<ProjectInfo>();
		ProjectInfo projectInfo = new ProjectInfo();
		projectInfo.setProjectId(5);
		projectInfo.setBuHead("1845");
		projectInfo.setSbuHead("1856");
		projectInfo.setProjectResourceMapping(getProjectResouce());
		pro.add(projectInfo);
		projectInfoRequest.setProjectInfo(projectInfo);
		int id = projectInfoRequest.getProjectInfo().getProjectId();
		when(projectRepo.getOne(id)).thenReturn(projectInfo);
		List<ProjectInfo> pro1 = new ArrayList<ProjectInfo>();
		ProjectInfo projectInfo1 = new ProjectInfo();
		projectInfo1.setProjectId(null);
		pro1.add(projectInfo1);
		when(projectRepo.save(projectInfo)).thenReturn(projectInfo1);
		ResponseEntity<Object> saveStatus = infoFacade.setProjectInfo(projectInfoRequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);

	}
	@Test
	public void statusTestl() throws SQLException, IOException {
		projectInfoRequest = new ProjectInfoRequest();
		projectInfoRequest.setTransactionType("statusUpdate");
		List<ProjectInfo> pro = new ArrayList<ProjectInfo>();
		ProjectInfo projectInfo = new ProjectInfo();
		projectInfo.setProjectId(5);
		projectInfo.setBuStatus("good");
      	projectInfo.setComment("nice");
		projectInfo.setProjectResourceMapping(getProjectResouce());
		pro.add(projectInfo);
		projectInfoRequest.setProjectInfo(projectInfo);
		int id = projectInfoRequest.getProjectInfo().getProjectId();
		when(projectRepo.getOne(id)).thenReturn(projectInfo);
		when(projectRepo.save(projectInfo)).thenReturn(projectInfo);
		List<String> projectInf = new ArrayList<String>();
		projectInf.add("18162");
		projectInf.add("18167");
	    when(c.prepareStatement(any(String.class))).thenReturn(stmt);
		when(driverManagerDataSource.getConnection()).thenReturn(c);
		doReturn(true).when(infoFacade).sendMail(any(), anyString());
		ResponseEntity<Object> saveStatus = infoFacade.setProjectInfo(projectInfoRequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);

	}
	
	

	@Test
	public void noProjectGetByContractId() throws SQLException {
		projectInfoRequest = new ProjectInfoRequest();
		List<ProjectInfo> pro = new ArrayList<ProjectInfo>();
		ProjectInfo ProjectInfo = new ProjectInfo();
		ProjectInfo.setContractId(6);
		pro.add(ProjectInfo);
		projectInfoRequest.setProjectInfo(ProjectInfo);
		projectInfoRequest.setTransactionType("getByContId");
		int id = projectInfoRequest.getProjectInfo().getContractId();
		when(projectRepo.getByCustomerId(id)).thenReturn(pro);
		ResponseEntity<Object> saveStatus = infoFacade.getProjectInfo(projectInfoRequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void testGetByProjectId() throws SQLException {
		projectInfoRequest = new ProjectInfoRequest();
		List<ProjectInfo> pro = new ArrayList<ProjectInfo>();
		ProjectInfo ProjectInfo = new ProjectInfo();
		ProjectInfo.setProjectId(5);
		pro.add(ProjectInfo);
		projectInfoRequest.setProjectInfo(ProjectInfo);
		projectInfoRequest.setTransactionType("getByProId");
		int id = projectInfoRequest.getProjectInfo().getProjectId();
		when(projectRepo.getOne(id)).thenReturn(ProjectInfo);
		ResponseEntity<Object> saveStatus = infoFacade.getProjectInfo(projectInfoRequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void testGetAll() throws SQLException {
		projectInfoRequest = new ProjectInfoRequest();
		List<ProjectInfo> pro = new ArrayList<ProjectInfo>();
		ProjectInfo ProjectInfo = new ProjectInfo();
		ProjectInfo.setProjectId(5);
		pro.add(ProjectInfo);
		projectInfoRequest.setProjectInfo(ProjectInfo);
		projectInfoRequest.setTransactionType("getAll");
		when(projectRepo.findAll()).thenReturn(pro);
		ResponseEntity<Object> saveStatus = infoFacade.getProjectInfo(projectInfoRequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void testGetAllNoProjects() throws SQLException {
		projectInfoRequest = new ProjectInfoRequest();
		List<ProjectInfo> pro = new ArrayList<ProjectInfo>();
		ProjectInfo ProjectInfo = new ProjectInfo();
		ProjectInfo.setProjectId(5);
		pro.add(ProjectInfo);
		projectInfoRequest.setProjectInfo(ProjectInfo);
		projectInfoRequest.setTransactionType("getAll");
		int id = projectInfoRequest.getProjectInfo().getProjectId();
		when(projectRepo.getOne(id)).thenReturn(ProjectInfo);
		ResponseEntity<Object> saveStatus = infoFacade.getProjectInfo(projectInfoRequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void testGetAllRecords() throws SQLException {
		projectInfoRequest = new ProjectInfoRequest();
		List<ProjectInfo> pro = new ArrayList<ProjectInfo>();
		ProjectInfo projectInfo = new ProjectInfo();
		projectInfo.setProjectId(56);
		pro.add(projectInfo);
		projectInfoRequest.setProjectInfo(projectInfo);
		projectInfoRequest.setTransactionType("getAll");
		when(projectRepo.getAllProjects()).thenReturn(pro);
		ResponseEntity<Object> saveStatus = infoFacade.getProjectInfo(projectInfoRequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

}
