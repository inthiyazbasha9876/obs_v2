package com.ojas.obs.projecttask.facadetest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ojas.obs.projecttask.facade.ProjectTaskFacade;
import com.ojas.obs.projecttask.facadeimpl.ProjectTaskFacadeImpl;
import com.ojas.obs.projecttask.model.ProjectTask;
import com.ojas.obs.projecttask.repository.ProjectTaskRepository;
import com.ojas.obs.projecttask.request.ProjectTaskRequest;
import com.ojas.obs.projecttask.response.ErrorResponse;
import com.ojas.obs.projecttask.response.ProjectTaskResponse;

public class ProjectTaskFacadeImplTest {
	@InjectMocks
	ProjectTaskFacadeImpl projecttaskfacadeimpl;
	
	@Mock
	ProjectTaskRepository projecttaskRepo;
	
	@Mock
	ProjectTaskFacade  projecttaskfacade;
	
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
	public void init() throws Exception 
	{
		projecttaskfacadeimpl=new ProjectTaskFacadeImpl();
		projecttaskRepo = mock(ProjectTaskRepository.class);
		setCollaborator(projecttaskfacadeimpl, "projecttaskRepo", projecttaskRepo);
	}
	
	private void setCollaborator(Object object, String name, Object service) throws Exception 
	{
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);	
	}
	
	public List<ProjectTask> getProjectTaskList() {
		List<ProjectTask> tasklist = new ArrayList<ProjectTask>();
		ProjectTask task = new ProjectTask();
		task.setProjecttaskId(1);
		task.setProjecttask("clientodc");
		
		
		ProjectTask task1 = new ProjectTask();
		task1.setProjecttaskId(2);
		task1.setProjecttask("ojasodc");
		
		tasklist.add(task);
		tasklist.add(task1);
		return tasklist;
	}
	
	@Test
	public void testSaveError() throws SQLException 
	{
		
		ProjectTaskRequest projecttaskreq = new ProjectTaskRequest();
		
		projecttaskreq.setTransactionType("save");
		
		projecttaskreq.setProjecttasklist(this.getProjectTaskList());
		
		ProjectTask task1 = new ProjectTask();

		when(projecttaskRepo.save(task1)).thenReturn(task1);	
		
		ResponseEntity<Object> saveStatus = projecttaskfacadeimpl.saveProjectTask(projecttaskreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	public void testSavesuccescheck() throws SQLException 
	{
		
		ProjectTaskRequest projecttaskreq = new ProjectTaskRequest();
		
		projecttaskreq.setTransactionType("save");
		
		projecttaskreq.setProjecttasklist(this.getProjectTaskList());
		
		ProjectTask task1= new ProjectTask();
		task1.setProjecttaskId(1);
		task1.setProjecttask("cat");
		
		List<ProjectTask> tasklist = new ArrayList<ProjectTask>();
		tasklist.add(task1);
		projecttaskreq.setProjecttasklist(tasklist);
		
		when(projecttaskRepo.save(task1)).thenReturn(task1);	
		
		ResponseEntity<Object> saveStatus = projecttaskfacadeimpl.saveProjectTask(projecttaskreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	@Test
	public void testupdatesuccesscheck() throws SQLException 
	{
		
		ProjectTaskRequest projecttaskreq = new ProjectTaskRequest();
		
		projecttaskreq.setTransactionType("update");
		
		projecttaskreq.setProjecttasklist(this.getProjectTaskList());
		
		ProjectTask task1 = new ProjectTask();
		task1.setProjecttaskId(1);
		task1.setProjecttask("cat");
		
		List<ProjectTask> tasklist = new ArrayList<ProjectTask>();
		tasklist.add(task1);
		projecttaskreq.setProjecttasklist(tasklist);

		Integer id=projecttaskreq.getProjecttasklist().get(0).getProjecttaskId();
		
		when(projecttaskRepo.findById(id)).thenReturn(Optional.of(task1));
			
		ResponseEntity<Object> saveStatus = projecttaskfacadeimpl.saveProjectTask(projecttaskreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	@Test
	public void testupdateErrorcheck() throws SQLException 
	{
		
		ProjectTaskRequest projecttaskreq = new ProjectTaskRequest();
		
		projecttaskreq.setTransactionType("update");
		
		projecttaskreq.setProjecttasklist(this.getProjectTaskList());
		
		ProjectTask task1 = new ProjectTask();
		task1.setProjecttaskId(null);
		task1.setProjecttask("cat");
		
		List<ProjectTask> tasklist  = new ArrayList<ProjectTask>();
		tasklist.add(task1);
		projecttaskreq.setProjecttasklist(tasklist);

		Integer id=projecttaskreq.getProjecttasklist().get(0).getProjecttaskId();
		
		when(projecttaskRepo.findById(id)).thenReturn(Optional.of(task1));
			
		ResponseEntity<Object> saveStatus = projecttaskfacadeimpl.saveProjectTask(projecttaskreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	public void testdeletesuccesscheck() throws SQLException 
	{
		
		ProjectTaskRequest projecttaskreq = new ProjectTaskRequest();
		
		projecttaskreq.setTransactionType("delete");
		
		projecttaskreq.setProjecttasklist(this.getProjectTaskList());
		
		ProjectTask task1 = new ProjectTask();	
		task1.setProjecttaskId(1);
		task1.setProjecttask("cat");
		
		List<ProjectTask> tasklist = new ArrayList<ProjectTask>();
		tasklist.add(task1);
		projecttaskreq.setProjecttasklist(tasklist);

		Integer id=projecttaskreq.getProjecttasklist().get(0).getProjecttaskId();
		
		when(projecttaskRepo.getOne(id)).thenReturn(task1);
					
		task1.setStatus(task1.getStatus()==null);
		
		when(projecttaskRepo.save(task1)).thenReturn(task1);
			
		ResponseEntity<Object> saveStatus = projecttaskfacadeimpl.saveProjectTask(projecttaskreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	@Test
	public void testdeleteErrorcheck() throws SQLException 
	{
		
		ProjectTaskRequest projecttaskreq = new ProjectTaskRequest();
		
		projecttaskreq.setTransactionType("delete");
		
		projecttaskreq.setProjecttasklist(this.getProjectTaskList());
		
		ProjectTask task1 = new ProjectTask();
		task1.setProjecttaskId(null);
		task1.setProjecttask(null);
		task1.setStatus(null);
		
		List<ProjectTask> tasklist = new ArrayList<ProjectTask>();
		tasklist.add(task1);
		projecttaskreq.setProjecttasklist(tasklist);

		Integer id=projecttaskreq.getProjecttasklist().get(0).getProjecttaskId();
		
		when(projecttaskRepo.getOne(id)).thenReturn(task1);
					
		task1.setStatus(task1.getStatus()!=null);
		
		when(projecttaskRepo.save(task1)).thenReturn(null);
			
		ResponseEntity<Object> saveStatus = projecttaskfacadeimpl.saveProjectTask(projecttaskreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	public void TestElseError() throws SQLException 
	{
		
		ProjectTaskRequest projecttaskreq = new ProjectTaskRequest();
		
		projecttaskreq.setTransactionType("ss");
		
		projecttaskreq.setProjecttasklist(this.getProjectTaskList());
		
		ProjectTask task1 = new ProjectTask();

		when(projecttaskRepo.save(task1)).thenReturn(task1);	
		
		ResponseEntity<Object> saveStatus = projecttaskfacadeimpl.saveProjectTask(projecttaskreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	//getTestcases
	
		@Test
		public void getAllSuccess() throws SQLException 
		{
			
			ProjectTaskRequest projecttaskreq = new ProjectTaskRequest();
			
			projecttaskreq.setProjecttasklist(this.getProjectTaskList());
			
			projecttaskreq.setTransactionType("getAll");
		
			ProjectTask task1= new ProjectTask();
		
			List<ProjectTask> tasklist  = new ArrayList<ProjectTask>();
			tasklist.add(task1);
			projecttaskreq.setProjecttasklist(tasklist);

			when(projecttaskRepo.findAll()).thenReturn(tasklist);	
			
			ResponseEntity<Object> saveStatus = projecttaskfacadeimpl.getProjectTask(projecttaskreq);
			
			HttpStatus statusCode = saveStatus.getStatusCode();
			
			assertEquals(HttpStatus.OK, statusCode);
		}
		
		@Test
		public void getAllError() throws SQLException 
		{
			
			ProjectTaskRequest projecttaskreq = new ProjectTaskRequest();
			
			projecttaskreq.setProjecttasklist(this.getProjectTaskList());
			
			projecttaskreq.setTransactionType("getAll");
		
			ProjectTask task1 = new ProjectTask();
			
		
			List<ProjectTask> tasklist  = new ArrayList<ProjectTask>();
			tasklist.add(task1);
			projecttaskreq.setProjecttasklist(tasklist);

			when(projecttaskRepo.findAll()).thenReturn(null);
			
			ResponseEntity<Object> saveStatus = projecttaskfacadeimpl.getProjectTask(projecttaskreq);
			
			HttpStatus statusCode = saveStatus.getStatusCode();
			
			assertEquals(HttpStatus.CONFLICT, statusCode);
		}
	
		@Test
		public void getByIdError() throws SQLException 
		{
			
			ProjectTaskRequest projecttaskreq = new ProjectTaskRequest();
			
			projecttaskreq.setProjecttasklist(this.getProjectTaskList());
		
			ProjectTask task1 = new ProjectTask();
			task1.setProjecttaskId(1);
			
			List<ProjectTask> tasklist = new ArrayList<ProjectTask>();
			tasklist.add(task1);
			projecttaskreq.setProjecttasklist(tasklist);
	    	
			projecttaskreq.setTransactionType("getById");
	    	Integer id=tasklist.get(0).getProjecttaskId();

			when(projecttaskRepo.findAll()).thenReturn(tasklist);	
			
			ResponseEntity<Object> saveStatus = projecttaskfacadeimpl.getProjectTask(projecttaskreq);
			
			HttpStatus statusCode = saveStatus.getStatusCode();
			
			assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
		}
		
		@Test
		public void getByIdSuccess() throws SQLException 
		{
			
			ProjectTaskRequest projecttaskreq = new ProjectTaskRequest();
			
			projecttaskreq.setProjecttasklist(this.getProjectTaskList());
			
			ProjectTask task = new ProjectTask();
			task.setProjecttaskId(1);
			
			List<ProjectTask> tasklist  = new ArrayList<ProjectTask>();
			tasklist.add(task);
			projecttaskreq.setProjecttasklist(tasklist);
	    	
			projecttaskreq.setTransactionType("getById");
	    	Integer id=tasklist.get(0).getProjecttaskId();
	    	
			when(projecttaskRepo.findById(id)).thenReturn(Optional.of(task));
			tasklist.add(task);
			
			ResponseEntity<Object> saveStatus = projecttaskfacadeimpl.getProjectTask(projecttaskreq);
			
			HttpStatus statusCode = saveStatus.getStatusCode();
			
			assertEquals(HttpStatus.OK, statusCode);
		}
		
		
		
	}
