package com.ojas.obs.projecttechstack.facadeTest;


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

import com.ojas.obs.projecttechstack.facade.ProjectTechStackFacade;
import com.ojas.obs.projecttechstack.facadeimpl.ProjectTechStackFacadeImpl;
import com.ojas.obs.projecttechstack.model.ProjectTechStack;
import com.ojas.obs.projecttechstack.repository.ProjectTechStackRepository;
import com.ojas.obs.projecttechstack.request.ProjectTechStackRequest;
import com.ojas.obs.projecttechstack.response.ErrorResponse;
import com.ojas.obs.projecttechstack.response.ProjectTechStackResponse;

public class ProjectTechStackFacadeImplTest {
	
@InjectMocks
ProjectTechStackFacadeImpl projectTechStackFacadeImpl;

@Mock
ProjectTechStackRepository projectTechStackRepo;

@Mock
ProjectTechStackFacade projectTechStackFacade;

@Spy
ProjectTechStackRequest projectTechStackReq;

@Spy
ProjectTechStackResponse projectTechStackResponse;

@Spy
ErrorResponse errorresponse;


@Spy
ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorresponse, HttpStatus.UNPROCESSABLE_ENTITY);

@Spy
ResponseEntity<Object> conflict = new ResponseEntity<>(errorresponse, HttpStatus.CONFLICT);

@Spy
ResponseEntity<Object> successResponse = new ResponseEntity<>(projectTechStackResponse, HttpStatus.OK);

@Spy
ProjectTechStack projectTechStack;

@Before
public void init() throws Exception 
{
	projectTechStackFacadeImpl=new ProjectTechStackFacadeImpl();
	projectTechStackRepo = mock(ProjectTechStackRepository.class);
	setCollaborator(projectTechStackFacadeImpl, "projectTechStackRepo", projectTechStackRepo);
}

private void setCollaborator(Object object, String name, Object service) throws Exception 
{
	Field field;
	field = object.getClass().getDeclaredField(name);
	field.setAccessible(true);
	field.set(object, service);
}

public List<ProjectTechStack> getProjectTechStack() {
	List<ProjectTechStack> projectTechStackList = new ArrayList<ProjectTechStack>();
	ProjectTechStack projectTechStackList1 = new ProjectTechStack();
	projectTechStackList1.setId(1);
	projectTechStackList1.setTechnology("cTA");
	
	
	ProjectTechStack projectTechStackList2 = new ProjectTechStack();
	projectTechStackList2.setId(1);
	projectTechStackList2.setTechnology("KK");
	
	projectTechStackList.add(projectTechStackList1);
	projectTechStackList.add(projectTechStackList2);
	return projectTechStackList;
}

@Test
public void testSaveError() throws SQLException 
{
	
	ProjectTechStackRequest req = new ProjectTechStackRequest();
	
	req.setTransactionType("save");
	
	req.setProjectTechStackList(this.getProjectTechStack());
	
	ProjectTechStack projectTechStack1 = new ProjectTechStack();

	when(projectTechStackRepo.save(projectTechStack1)).thenReturn(projectTechStack1);	
	
	ResponseEntity<Object> saveStatus = projectTechStackFacadeImpl.saveProjectTechStack(req);
	
	HttpStatus statusCode = saveStatus.getStatusCode();
	
	assertEquals(HttpStatus.CONFLICT, statusCode);
}

@Test
public void testSavesuccescheck() throws SQLException 
{
	
ProjectTechStackRequest req = new ProjectTechStackRequest();
	
	req.setTransactionType("save");
	req.setProjectTechStackList(this.getProjectTechStack());
	
	ProjectTechStack projectTechStack1 = new ProjectTechStack();
	projectTechStack1.setId(1);
	projectTechStack1.setTechnology("cat");
	
	List<ProjectTechStack> projectTechStackList  = new ArrayList<ProjectTechStack>();
	projectTechStackList.add(projectTechStack1);
	req.setProjectTechStackList(projectTechStackList);
	
	when(projectTechStackRepo.save(projectTechStack1)).thenReturn(projectTechStack1);	
	
	ResponseEntity<Object> saveStatus = projectTechStackFacadeImpl.saveProjectTechStack(req);
	
	HttpStatus statusCode = saveStatus.getStatusCode();
	
	assertEquals(HttpStatus.CONFLICT, statusCode);
}

@Test
public void testupdatesuccesscheck() throws SQLException 
{
	
    ProjectTechStackRequest req = new ProjectTechStackRequest();
	
	req.setTransactionType("update");
	req.setProjectTechStackList(this.getProjectTechStack());
	
	

	ProjectTechStack projectTechStack1 = new ProjectTechStack();
	projectTechStack1.setId(1);
	projectTechStack1.setTechnology("cat");
	
	List<ProjectTechStack> projectTechStackList  = new ArrayList<ProjectTechStack>();
	projectTechStackList.add(projectTechStack1);
	req.setProjectTechStackList(projectTechStackList);
	

	Integer id=req.getProjectTechStackList().get(0).getId();
	
	when(projectTechStackRepo.findById(id)).thenReturn(Optional.of(projectTechStack1));
		
	ResponseEntity<Object> saveStatus = projectTechStackFacadeImpl.saveProjectTechStack(req);
	
	HttpStatus statusCode = saveStatus.getStatusCode();
	
	assertEquals(HttpStatus.OK, statusCode);
}

@Test
public void testupdateErrorcheck() throws SQLException 
{
	
	ProjectTechStackRequest req = new ProjectTechStackRequest();
	
	req.setTransactionType("update");
	
	req.setProjectTechStackList(this.getProjectTechStack());
	
	ProjectTechStack projectTechStack1 = new ProjectTechStack();
	projectTechStack1.setId(null);
	projectTechStack1.setTechnology("cat");
	
	List<ProjectTechStack> projectTechStack  = new ArrayList<ProjectTechStack>();
	projectTechStack.add(projectTechStack1);
	req.setProjectTechStackList(projectTechStack);

	Integer id=req.getProjectTechStackList().get(0).getId();
	
	when(projectTechStackRepo.findById(id)).thenReturn(Optional.of(projectTechStack1));
		
	ResponseEntity<Object> saveStatus = projectTechStackFacadeImpl.saveProjectTechStack(req);
	
	HttpStatus statusCode = saveStatus.getStatusCode();
	
	assertEquals(HttpStatus.CONFLICT, statusCode);
}

@Test
public void testdeletesuccesscheck() throws SQLException 
{
	
	ProjectTechStackRequest req = new ProjectTechStackRequest();
	
	req.setTransactionType("delete");
	
	req.setProjectTechStackList(this.getProjectTechStack());
	
	ProjectTechStack projectTechStack1 = new ProjectTechStack();
	projectTechStack1.setId(1);
	projectTechStack1.setTechnology("cat");
	
	
	List<ProjectTechStack> projectTechStackList = new ArrayList<ProjectTechStack>();
	projectTechStackList .add(projectTechStack1);
	req.setProjectTechStackList(projectTechStackList);

	Integer id=req.getProjectTechStackList().get(0).getId();
	
	when(projectTechStackRepo.getOne(id)).thenReturn(projectTechStack1);
				
	projectTechStack1.setStatus(projectTechStack1.getStatus()==null);
	
	when(projectTechStackRepo.save(projectTechStack1)).thenReturn(projectTechStack1);
		
	ResponseEntity<Object> saveStatus = projectTechStackFacadeImpl.saveProjectTechStack(req);
	
	HttpStatus statusCode = saveStatus.getStatusCode();
	
	assertEquals(HttpStatus.OK, statusCode);
}

//@Test
//public void testdeleteErrorcheck() throws SQLException 
//{
//	
//	ProjectTechStackRequest req = new ProjectTechStackRequest();
//	
//	req.setTransactionType("delete");
//	
//	req.setProjectTechStackList(this.getProjectTechStack());
//	
//	ProjectTechStack projectTechStack1 = new ProjectTechStack();
//	projectTechStack1.setId(1);
//	projectTechStack1.setTechnology(null);
//	projectTechStack1.setStatus(null);
//	
//	List<ProjectTechStack> projectTechStackList  = new ArrayList<ProjectTechStack>();
//	projectTechStackList.add(projectTechStack1);
//	req.setProjectTechStackList(projectTechStackList);
//
//	Integer id=req.getProjectTechStackList().get(0).getId();
//	
//	when(projectTechStackRepo.getOne(id)).thenReturn(projectTechStack1);
//				
//		
//	when(projectTechStackRepo.save(projectTechStack1)).thenReturn(projectTechStack1);
//		
//	ResponseEntity<Object> saveStatus = projectTechStackFacadeImpl.saveProjectTechStack(req);
//	
//	HttpStatus statusCode = saveStatus.getStatusCode();
//	
//	assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
//}

@Test
public void testdeleteErrorcheck() throws SQLException {
	ProjectTechStackRequest req = new ProjectTechStackRequest();
	req.setTransactionType("delete");
	ProjectTechStackResponse res=new ProjectTechStackResponse();
	List<ProjectTechStack> projectTechStackList  = new ArrayList<ProjectTechStack>();
	ProjectTechStack ptk=new ProjectTechStack();
	ptk.setId(5);
	projectTechStackList.add(ptk);
	req.setProjectTechStackList(projectTechStackList);
	Integer id=req.getProjectTechStackList().get(0).getId();
	when(projectTechStackRepo.getOne(id)).thenReturn(ptk);
	when(projectTechStackRepo.save(ptk)).thenReturn(ptk);
	ResponseEntity<Object> saveStatus = projectTechStackFacadeImpl.saveProjectTechStack(req);
   HttpStatus statusCode = saveStatus.getStatusCode();
	assertEquals(HttpStatus.OK, statusCode);
	
	
}

@Test
public void TestElseError() throws SQLException 
{
	
	ProjectTechStackRequest req = new ProjectTechStackRequest();
	
	req.setTransactionType("ss");
	
	req.setProjectTechStackList(this.getProjectTechStack());
	
	ProjectTechStack projectTechStack1 = new ProjectTechStack();

	when(projectTechStackRepo.save(projectTechStack1)).thenReturn(projectTechStack1);	
	
	ResponseEntity<Object> saveStatus = projectTechStackFacadeImpl.saveProjectTechStack(req);
	
	HttpStatus statusCode = saveStatus.getStatusCode();
	
	assertEquals(HttpStatus.OK, statusCode);
}

//getTestcases

	@Test
	public void getAllSuccess() throws SQLException 
	{
		
		ProjectTechStackRequest req = new ProjectTechStackRequest();
		
		req.setProjectTechStackList(this.getProjectTechStack());
		
		req.setTransactionType("getAll");
	
		ProjectTechStack projectTechStack2 = new ProjectTechStack();
	
		List<ProjectTechStack> projectTechStackList  = new ArrayList<ProjectTechStack>();
		projectTechStackList.add(projectTechStack2 );
		req.setProjectTechStackList(projectTechStackList);

		when(projectTechStackRepo.findAll()).thenReturn(projectTechStackList);	
		
		ResponseEntity<Object> saveStatus = projectTechStackFacadeImpl.getProjectTechStack(req);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.OK, statusCode);
	}
	
//	@Test
//	public void getAllError() throws SQLException 
//	{
//		
//		ProjectTechStackRequest req = new ProjectTechStackRequest();
//		
//		req.setProjectTechStackList(this.getProjectTechStack());
//		
//		req.setTransactionType("getAll");
//	
//		ProjectTechStack projectTechStack2 = new ProjectTechStack();
//		
//	
//		List<ProjectTechStack> projectTechStack  = new ArrayList<ProjectTechStack>();
//		projectTechStack.add(null);
//		req.setProjectTechStackList(projectTechStack);
//
//		when(projectTechStackRepo.findAll()).thenReturn();
//		
//		ResponseEntity<Object> saveStatus = projectTechStackFacadeImpl.getProjectTechStack(req);
//		
//		HttpStatus statusCode = saveStatus.getStatusCode();
//		
//		assertEquals(HttpStatus.CONFLICT, statusCode);
//	}
//	
	
	@Test
	public void getAllError() throws SQLException 
	{
		ProjectTechStackRequest req = new ProjectTechStackRequest();
		List<ProjectTechStack> projectTechStack  = new ArrayList<ProjectTechStack>();
		ProjectTechStack ptk =new ProjectTechStack();
		ptk.setId(null);
		ptk.setStatus(false);
		ptk.setTechnology(null);
		projectTechStack.isEmpty();
		req.setTransactionType("getAll");
		req.setProjectTechStackList(projectTechStack);
		when(projectTechStackRepo.findAll()).thenReturn(projectTechStack);
		ResponseEntity<Object> saveStatus = projectTechStackFacadeImpl.getProjectTechStack(req);		
		HttpStatus statusCode = saveStatus.getStatusCode();	
		assertEquals(HttpStatus.CONFLICT, statusCode);	
	}
		
		
	
	
	@Test
	public void getByIdError() throws SQLException 
	{
        ProjectTechStackRequest req = new ProjectTechStackRequest();
		
		req.setProjectTechStackList(this.getProjectTechStack());
	
		ProjectTechStack projectTechStack2 = new ProjectTechStack();
		projectTechStack2.setId(1);
	
		List<ProjectTechStack> projectTechStack = new ArrayList<ProjectTechStack>();
		projectTechStack .add(projectTechStack2);
		req.setProjectTechStackList(projectTechStack );
  	
		req.setTransactionType("getById");
  	Integer id=projectTechStack .get(0).getId();

		when(projectTechStackRepo.findAll()).thenReturn(projectTechStack );	
		
		ResponseEntity<Object> saveStatus = projectTechStackFacadeImpl.getProjectTechStack(req);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	public void getByIdSuccess() throws SQLException 
	{
		
		ProjectTechStackRequest req = new ProjectTechStackRequest();
		req.setProjectTechStackList(this.getProjectTechStack());
		
		ProjectTechStack projectTechStack2 = new ProjectTechStack();
		projectTechStack2.setId(1);
		
		List<ProjectTechStack> projectTechStack = new ArrayList<ProjectTechStack>();
		projectTechStack.add(projectTechStack2);
		req.setProjectTechStackList(projectTechStack);
  	
		req.setTransactionType("getById");
  	Integer id=projectTechStack.get(0).getId();
  	
		when(projectTechStackRepo.findById(id)).thenReturn(Optional.of(projectTechStack2));
		projectTechStack.add(projectTechStack2);
		
		ResponseEntity<Object> saveStatus = projectTechStackFacadeImpl.getProjectTechStack(req);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.OK, statusCode);
	}
	
		
}
