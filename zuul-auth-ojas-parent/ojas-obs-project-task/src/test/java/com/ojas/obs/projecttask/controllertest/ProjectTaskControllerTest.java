package com.ojas.obs.projecttask.controllertest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ojas.obs.projecttask.controller.ProjectTaskController;
import com.ojas.obs.projecttask.facade.ProjectTaskFacade;
import com.ojas.obs.projecttask.facadeimpl.ProjectTaskFacadeImpl;
import com.ojas.obs.projecttask.model.ProjectTask;
import com.ojas.obs.projecttask.request.ProjectTaskRequest;
import com.ojas.obs.projecttask.response.ErrorResponse;
import com.ojas.obs.projecttask.response.ProjectTaskResponse;

public class ProjectTaskControllerTest {
	@InjectMocks
	ProjectTaskController projecttaskcontroller;

	@Mock
	ProjectTaskFacadeImpl projecttaskfacadeImpl;

	@Spy
	ProjectTaskRequest projecttaskreq;

	@Spy
	ErrorResponse errorresponse;

	@Spy
	ProjectTaskResponse projecttaskresponse;

	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorresponse, HttpStatus.UNPROCESSABLE_ENTITY);

	@Spy
	ResponseEntity<Object> conflict = new ResponseEntity<>(errorresponse, HttpStatus.CONFLICT);

	@Spy
	ResponseEntity<Object> successResponse = new ResponseEntity<>(projecttaskresponse, HttpStatus.OK);

	@Spy
	ProjectTask projecttask;

	@Before
	public void init() throws Exception {
		projecttaskcontroller = new ProjectTaskController();
		projecttaskfacadeImpl = mock(ProjectTaskFacadeImpl.class);
		setCollaborator(projecttaskcontroller, "projecttaskfacadeImpl", projecttaskfacadeImpl);
	}

	private void setCollaborator(Object object, String name, Object service) throws Exception {
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);
	}

	public List<ProjectTask> getProjectTaskList() {
		List<ProjectTask> tasklist = new ArrayList<ProjectTask>();

		ProjectTask task1 = new ProjectTask();
		task1.setProjecttaskId(1);
		task1.setProjecttask("clientodc");

		ProjectTask task = new ProjectTask();
		task.setProjecttaskId(2);
		task.setProjecttask("ojasodc");

		tasklist.add(task1);
		tasklist.add(task);
		return tasklist;
	}
	
	@Test
	public void projecttaskRequestNullTest() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	ProjectTaskRequest projecttaskrequest=new ProjectTaskRequest();
    	
    	projecttaskrequest.setProjecttasklist(this.getProjectTaskList());
    	projecttaskrequest.setTransactionType(null);
		when(projecttaskfacadeImpl.saveProjectTask(projecttaskrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = projecttaskcontroller.saveProjectTask(projecttaskrequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	
	@Test
	public void servicecategoryRequestsaveTest() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	ProjectTaskRequest projecttaskrequest=new ProjectTaskRequest();
    	
    	projecttaskrequest.setProjecttasklist(this.getProjectTaskList());
    	projecttaskrequest.setTransactionType("save");
    	
		when(projecttaskfacadeImpl.saveProjectTask(projecttaskrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = projecttaskcontroller.saveProjectTask(projecttaskrequest, request, response);
		HttpStatus status = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void servicecategoryRequestupdateTest() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	ProjectTaskRequest projecttaskrequest=new ProjectTaskRequest();
    	projecttaskrequest.setProjecttasklist(this.getProjectTaskList());
    	projecttaskrequest.setTransactionType("update");
		when(projecttaskfacadeImpl.saveProjectTask(projecttaskrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setBus = projecttaskcontroller.saveProjectTask(projecttaskrequest, request, response);
		HttpStatus statusCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	public void servicecategoryRequestdeleteTest() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	ProjectTaskRequest projecttaskrequest=new ProjectTaskRequest();
    	projecttaskrequest.setProjecttasklist(this.getProjectTaskList());
    	projecttaskrequest.setTransactionType("delete");
		when(projecttaskfacadeImpl.saveProjectTask(projecttaskrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setBus = projecttaskcontroller.saveProjectTask(projecttaskrequest, request, response);
		HttpStatus statusCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	public void setDuplicateKeyExceptionTest() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	ProjectTaskRequest projecttaskrequest=new ProjectTaskRequest();
    		
    	ProjectTask task1 = new ProjectTask();
    	task1.setProjecttask("any cato");
    	task1.setStatus(true);
    	
    	List<ProjectTask> tasklist = new ArrayList<ProjectTask>();
    	tasklist.add(task1);
    	projecttaskrequest.setProjecttasklist(tasklist);
    	projecttaskrequest.setTransactionType("save");	
    	
    	when(projecttaskfacadeImpl.saveProjectTask(projecttaskrequest)).thenThrow(new DuplicateKeyException(null,new Throwable()));   
		ResponseEntity<Object> setBus = projecttaskcontroller.saveProjectTask(projecttaskrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}
	
	@Test
	public void setExceptionTest() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	ProjectTaskRequest projecttaskrequest=new ProjectTaskRequest();
    		
    	ProjectTask task1 = new ProjectTask();
    	task1.setProjecttask("any cato");
    	task1.setStatus(false);
    	
    	List<ProjectTask> tasklist  = new ArrayList<ProjectTask>();
    	tasklist.add(task1);
    	projecttaskrequest.setProjecttasklist(tasklist);
    	projecttaskrequest.setTransactionType("save");	
    	when(projecttaskfacadeImpl.saveProjectTask(projecttaskrequest)).thenThrow(RuntimeException.class);
    
		ResponseEntity<Object> setBus = projecttaskcontroller.saveProjectTask(projecttaskrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}
	
	@Test
	public void setsavesucces() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	ProjectTaskRequest projecttaskrequest=new ProjectTaskRequest();
    		
    	ProjectTask task1 = new ProjectTask();
    	task1.setProjecttask("data");
    	task1.setStatus(false);
    	
    	List<ProjectTask>tasklist  = new ArrayList<ProjectTask>();
    	tasklist.add(task1);
    	projecttaskrequest.setProjecttasklist(tasklist);
    	projecttaskrequest.setTransactionType("save");		
    	when(projecttaskfacadeImpl.saveProjectTask(projecttaskrequest)).thenReturn(successResponse);
    
		ResponseEntity<Object> setBus = projecttaskcontroller.saveProjectTask(projecttaskrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.OK, unitCode);
	}
	
	@Test
	public void setupdatesucces() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	ProjectTaskRequest projecttaskrequest=new ProjectTaskRequest();
		
    	ProjectTask task1 = new ProjectTask();
    	task1.setProjecttask("data");
    	task1.setStatus(false);
    	
    	List<ProjectTask> tasklist = new ArrayList<ProjectTask>();
    	tasklist.add(task1);
    	projecttaskrequest.setProjecttasklist(tasklist);
    	projecttaskrequest.setTransactionType("update");	
    	when(projecttaskfacadeImpl.saveProjectTask(projecttaskrequest)).thenReturn(successResponse);
    
		ResponseEntity<Object> setBus = projecttaskcontroller.saveProjectTask(projecttaskrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.OK, unitCode);
	}
	
	@Test
	public void setdeletesucces() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	ProjectTaskRequest projecttaskrequest=new ProjectTaskRequest();
    		
    	ProjectTask task1 = new ProjectTask();
    	task1.setProjecttask("ss");
    	task1.setStatus(false);
    	
    	List<ProjectTask> tasklist  = new ArrayList<ProjectTask>();
    	tasklist.add(task1);
    	projecttaskrequest.setProjecttasklist(tasklist);
    	projecttaskrequest.setTransactionType("delete");	
    	when(projecttaskfacadeImpl.saveProjectTask(projecttaskrequest)).thenReturn(successResponse);
    
		ResponseEntity<Object> setBus = projecttaskcontroller.saveProjectTask(projecttaskrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.OK, unitCode);
	}
	//getTestcases
	
		@Test
		public void getTransactionEmpty() throws SQLException {
			HttpServletRequest request = null;
	    	HttpServletResponse response = null;
	    	ProjectTaskRequest projecttaskrequest=new ProjectTaskRequest();
	    	
	    	projecttaskrequest.setProjecttasklist(this.getProjectTaskList());
	    	projecttaskrequest.setTransactionType(null);
			when(projecttaskfacadeImpl.saveProjectTask(projecttaskrequest)).thenReturn(failureResponse);
			ResponseEntity<Object> setservice = projecttaskcontroller.getProjectTask(projecttaskrequest, request, response);
			HttpStatus unitCode = setservice.getStatusCode();
			assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
		}
		
		
		@Test
		public void getByIdsuccesscheck() throws SQLException {
			HttpServletRequest request = null;
	    	HttpServletResponse response = null;
	    	ProjectTaskRequest projecttaskrequest=new ProjectTaskRequest();
	    
	    	projecttaskrequest.setProjecttasklist(this.getProjectTaskList());
	    	projecttaskrequest.setTransactionType("getById");
			
	    	projecttaskrequest.getProjecttasklist().get(0).getProjecttaskId();
			
			when(projecttaskfacadeImpl.getProjectTask(projecttaskrequest)).thenReturn(successResponse);
			ResponseEntity<Object> setservice = projecttaskcontroller.getProjectTask(projecttaskrequest, request, response);
			HttpStatus unitCode = setservice.getStatusCode();
			assertEquals(HttpStatus.OK, unitCode);
		}
		@Test
		public void getByIdcheck() throws SQLException {
			HttpServletRequest request = null;
	    	HttpServletResponse response = null;
	    	ProjectTaskRequest projecttaskrequest=new ProjectTaskRequest();
	    
	    	projecttaskrequest.setProjecttasklist(this.getProjectTaskList());
	    	projecttaskrequest.setTransactionType("getById");
			
	    	projecttaskrequest.getProjecttasklist().get(0).setProjecttaskId(null);
			
			when(projecttaskfacadeImpl.getProjectTask(projecttaskrequest)).thenReturn(failureResponse);
			ResponseEntity<Object> setservice = projecttaskcontroller.getProjectTask(projecttaskrequest, request, response);
			HttpStatus unitCode = setservice.getStatusCode();
			assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
		}
		
		
		@Test
		public void getExceptionTest() throws Exception {
			HttpServletRequest request = null;
	    	HttpServletResponse response = null;
	    	ProjectTaskRequest projecttaskrequest=new ProjectTaskRequest();
	  
	    	projecttaskrequest.setProjecttasklist(this.getProjectTaskList());
	    	projecttaskrequest.setTransactionType("getAll");	
	    	when(projecttaskfacadeImpl.getProjectTask(projecttaskrequest)).thenThrow(RuntimeException.class);
	    
			ResponseEntity<Object> setBus = projecttaskcontroller.getProjectTask(projecttaskrequest, request, response);
			HttpStatus unitCode = setBus.getStatusCode();
			assertEquals(HttpStatus.CONFLICT, unitCode);
		}
		
	}

