package com.ojas.obs.psa.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ojas.obs.psa.facade.ProjectInfoFacade;
import com.ojas.obs.psa.facadeimpl.ProjectInfoFacadeImpl;
import com.ojas.obs.psa.model.ProjectInfo;
import com.ojas.obs.psa.model.ProjectRatecard;
import com.ojas.obs.psa.model.ProjectResourceMapping;
import com.ojas.obs.psa.request.ProjectInfoRequest;
import com.ojas.obs.psa.response.ErrorResponse;
import com.ojas.obs.psa.response.ProjectInfoResponse;

public class ProjectInfoControllerTest {
	

	@InjectMocks
	ProjectInfoController unitController;
	
	@Mock
	ProjectInfoFacade infoFacade;

	@Spy
	ProjectInfoRequest projectInfoRequest;
	@Spy
	ErrorResponse errorResponse = new ErrorResponse();
	@Spy
	ProjectInfoResponse projectInfoResponse = new ProjectInfoResponse();
	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
	@Spy
	ResponseEntity<Object> conflict = new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
	@Spy
	ResponseEntity<Object> successResponse = new ResponseEntity<>(projectInfoResponse, HttpStatus.OK);
	@Spy
	ProjectInfo projectInfo;
	@Spy
	ProjectRatecard projectRatecard;
	@Spy
	ProjectResourceMapping projectResourceMapping;
	@Before
	public void init() throws Exception {
		unitController = new ProjectInfoController();
		infoFacade = mock(ProjectInfoFacadeImpl.class);
		setCollaborator(unitController, "infoFacade", infoFacade);
	}
	public void setCollaborator(Object object, String name, Object service) throws Exception {
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);
}
	
	
	public ProjectInfo getProjectInfo() {
	
		
	
		Set<String> taskset= new HashSet<>();
		taskset.add("Obs Psa Tasks");
	
		 ProjectInfo projectInfo = new ProjectInfo();
		 
		 
		
		 
		 projectInfo.setContractId(1);
		 projectInfo.setCustomerId(2);
		 projectInfo.setDeliveryLocation("10");
		 projectInfo.setProjectId(52);
		 projectInfo.setProjectType("Customer");
		 projectInfo.setProjectDescription("good");
		 projectInfo.setLocationType("5");
		 projectInfo.setProjectName("obs");
		 projectInfo.setProjectRatecard(projectRatecard);
		 projectInfo.setProjectResourceMapping(projectResourceMapping);
		 projectInfo.setStartDate("2019-12-18");
		 projectInfo.setServicecategory("3");
		 projectInfo.setFinanceStatus("good");
		 projectInfo.setBuHead("18162");
		 projectInfo.setBuStatus("approve");
		 projectInfo.setSbuHead("18167");
		 projectInfo.setCreatedBy("ajay");
		 projectInfo.setUpdatedBy("ravi");
		 projectInfo.setComment("good");
		 projectInfo.setTasks(taskset);
		
		 projectInfo.setEndDate("2019-12-31");
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
		Set<String> taskset= new HashSet<>();
		taskset.add("Obs Psa Tasks1");
		ProjectResourceMapping projectResourceMapping = new ProjectResourceMapping();
		projectResourceMapping.setProjectManager("ajay");
		//projectResourceMapping.setResources(taskset);
		//projectResourceMapping.setResourceCount(6);
		projectResourceMapping.setResourceMappingId(52);
		projectResourceMapping.setTechLead("suri");
		projectResourceMapping.setTechStack(taskset);
		 return projectResourceMapping;
		}
	//requstObjNullTest
	@Test
	public void projctInfoNullTest() throws SQLException, IOException {
		ProjectInfoRequest request=new  ProjectInfoRequest();
		request.setProjectInfo(null);
		request.setRateCard(null);
		request.setResourceMap(null);
		request.setTransactionType("delete");
		request.setTransactionType("statusUpdate");
		when(infoFacade.setProjectInfo(request)).thenReturn(failureResponse);
		ResponseEntity<Object> setBus = unitController.setProjectInfo(request);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	
}

	@Test//requset null
	public void requestNullTest() throws SQLException, IOException {
		ProjectInfoRequest pro=new ProjectInfoRequest();
		pro.setTransactionType("");
		when(infoFacade.setProjectInfo(pro)).thenReturn(failureResponse);
		ResponseEntity<Object> setBus = unitController.setProjectInfo(pro);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}

	@Test//saveTest
	public void saveTest() throws Exception {

		ProjectInfoRequest projectInfoRequest = new ProjectInfoRequest();
		projectInfoRequest.setProjectInfo(getProjectInfo());
		projectInfoRequest.setRateCard(projectRateCardList());
		projectInfoRequest.setResourceMap(getProjectResouce());
		projectInfoRequest.setTransactionType("save");
		projectInfoRequest.setTransactionType("update");
	 when(infoFacade.setProjectInfo(projectInfoRequest)).thenReturn(successResponse);
	 ResponseEntity<Object> setBus = unitController.setProjectInfo(projectInfoRequest);
	 HttpStatus unitCode = setBus.getStatusCode();
	 assertEquals(HttpStatus.OK, unitCode);
	}
	
	@Test//saveNegativeTest
	public void saveNegativeTest() throws Exception {

		ProjectInfoRequest projectInfoRequest = new ProjectInfoRequest();
		ProjectInfo pro =new ProjectInfo();
		pro.setProjectId(1);
		pro.setProjectName("keep");
		projectInfoRequest.setProjectInfo(pro);
		ProjectRatecard prc =new ProjectRatecard();
		prc.setRatecardId(9);
		prc.setBillingType("java");
	    projectInfoRequest.setRateCard(prc);
		projectInfoRequest.setResourceMap(getProjectResouce());
	    projectInfoRequest.setTransactionType("save");
		projectInfoRequest.setTransactionType("update");
	 when(infoFacade.setProjectInfo(projectInfoRequest)).thenReturn(failureResponse);
	 ResponseEntity<Object> setBus = unitController.setProjectInfo(projectInfoRequest);
	 HttpStatus unitCode = setBus.getStatusCode();
	 assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode); 
	}
	
	@Test//saveNegativeprc
	public void saveNegativeTestPrc() throws Exception {

		ProjectInfoRequest projectInfoRequest = new ProjectInfoRequest();
		
		ProjectRatecard prc =new ProjectRatecard();
		prc.setRatecardId(9);
		prc.setBillingType("java");
	    projectInfoRequest.setRateCard(prc);
	    ProjectResourceMapping prm= new ProjectResourceMapping();
	    prm.setProjectManager("ajay");
	    prm.setTechLead("suri");
	    projectInfoRequest.setResourceMap(prm);
		projectInfoRequest.setProjectInfo(getProjectInfo());
	    projectInfoRequest.setTransactionType("save");
		projectInfoRequest.setTransactionType("update");
	 when(infoFacade.setProjectInfo(projectInfoRequest)).thenReturn(failureResponse);
	 ResponseEntity<Object> setBus = unitController.setProjectInfo(projectInfoRequest);
	 HttpStatus unitCode = setBus.getStatusCode();
	 assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	@Test//saveNegativeprm
	public void saveNegativeTestPrm() throws Exception {

		ProjectInfoRequest projectInfoRequest = new ProjectInfoRequest();
	    
	    ProjectResourceMapping prm= new ProjectResourceMapping();
	    prm.setProjectManager("ajay");
	    prm.setTechLead("suri");
	    projectInfoRequest.setResourceMap(prm);
		projectInfoRequest.setProjectInfo(getProjectInfo());
		  projectInfoRequest.setRateCard(projectRateCardList());
	    projectInfoRequest.setTransactionType("save");
		projectInfoRequest.setTransactionType("update");
	 when(infoFacade.setProjectInfo(projectInfoRequest)).thenReturn(failureResponse);
	 ResponseEntity<Object> setBus = unitController.setProjectInfo(projectInfoRequest);
	 HttpStatus unitCode = setBus.getStatusCode();
	 assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	
	@Test
	public void nullTest() throws SQLException, IOException {
		
		ProjectInfoRequest projectInfoRequest = new ProjectInfoRequest();
		projectInfoRequest.setTransactionType("delete");
		projectInfoRequest.setTransactionType("STATUSUPDATE");
		ProjectInfo pro = new ProjectInfo();
		pro.setProjectId(null);
		pro.setUpdatedBy(null);
		projectInfoRequest.setProjectInfo(pro);
		when(infoFacade.setProjectInfo(projectInfoRequest)).thenReturn(failureResponse);
		 ResponseEntity<Object> setBus = unitController.setProjectInfo(projectInfoRequest);
		 HttpStatus unitCode = setBus.getStatusCode();
		 assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	
	}
	@Test//exceptionTest
	public void setExceptionTest() throws Exception {

		ProjectInfoRequest projectInfoRequest = new ProjectInfoRequest();
		projectInfoRequest.setProjectInfo(getProjectInfo());
		projectInfoRequest.setRateCard(projectRateCardList());
		projectInfoRequest.setResourceMap(getProjectResouce());
		projectInfoRequest.setTransactionType("save");
		projectInfoRequest.setTransactionType("update");
	 when(infoFacade.setProjectInfo(projectInfoRequest)).thenThrow(new RuntimeException());
	 ResponseEntity<Object> setBus = unitController.setProjectInfo(projectInfoRequest);
	 HttpStatus unitCode = setBus.getStatusCode();
	 assertEquals(HttpStatus.CONFLICT, unitCode);
	}
	
	//get
	@Test
	public void getRequestNullTest() throws SQLException {
	 ProjectInfoRequest projectInfoRequest = new ProjectInfoRequest();
	
	 projectInfoRequest.setTransactionType(null);
	 projectInfoRequest.setTransactionType("");
	 projectInfoRequest.setProjectInfo(null);
	  when(infoFacade.getProjectInfo(projectInfoRequest)).thenReturn(failureResponse);
	 ResponseEntity<Object> businessUnit = unitController.getProjectInfo(projectInfoRequest);
	 HttpStatus status = businessUnit.getStatusCode();
	 assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}
	
	@Test
	public void projObjNullTest() throws SQLException {
		 ProjectInfoRequest  pr= new ProjectInfoRequest();
		 
		pr.setProjectInfo(null);
		pr.setRateCard(projectRateCardList());
		pr.setResourceMap(getProjectResouce());
		pr.setTransactionType("getbyId");
	  when(infoFacade.getProjectInfo(pr)).thenReturn(failureResponse);
	 ResponseEntity<Object> businessUnit = unitController.getProjectInfo(pr);
	 HttpStatus status = businessUnit.getStatusCode();
	 assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}
	@Test
	public void transationTypeNullTest() throws SQLException {
	 ProjectInfoRequest  pr= new ProjectInfoRequest();
	 ProjectInfo pro=new ProjectInfo();
	 pr.setTransactionType("getByProId");
	 pro.setProjectId(null);
	 pr.setTransactionType("getByCustId");
	 pro.setCustomerId(null);
	 pr.setTransactionType("getByContId");
	 pro.setContractId(null); 
	 pr.setProjectInfo(pro);
	  when(infoFacade.getProjectInfo(pr)).thenReturn(failureResponse);
	 ResponseEntity<Object> businessUnit = unitController.getProjectInfo(pr);
	 HttpStatus status = businessUnit.getStatusCode();
	 assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}
	@Test
	public void getExceptionTest() throws SQLException {
		ProjectInfoRequest  pr= new ProjectInfoRequest();
		pr.setProjectInfo(getProjectInfo());
		pr.setRateCard(projectRateCardList());
		pr.setResourceMap(getProjectResouce());
		pr.setTransactionType("getbyId");
		 pr.setTransactionType("getByProId");
		 pr.setTransactionType("getByCustId");
		 pr.setTransactionType("getByContId");
	  when(infoFacade.getProjectInfo(pr)).thenThrow(new RuntimeException());
	 ResponseEntity<Object> businessUnit = unitController.getProjectInfo(pr);
	 HttpStatus status = businessUnit.getStatusCode();
	 assertEquals(HttpStatus.CONFLICT, status);
	}
	@Test
	public void getTest() throws SQLException {
		ProjectInfoRequest  pr= new ProjectInfoRequest();
		pr.setProjectInfo(getProjectInfo());
		pr.setRateCard(projectRateCardList());
		pr.setResourceMap(getProjectResouce());
		pr.setTransactionType("getbyId");
		 pr.setTransactionType("getByProId");
		 pr.setTransactionType("getByCustId");
		 pr.setTransactionType("getByContId");
	  when(infoFacade.getProjectInfo(pr)).thenReturn(successResponse);
	 ResponseEntity<Object> businessUnit = unitController.getProjectInfo(pr);
	 HttpStatus status = businessUnit.getStatusCode();
	 assertEquals(HttpStatus.OK, status);
	}
	
}
