package com.ojas.obs.facadeTest;

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

import com.ojas.obs.facade.InterviewResultFacade;
import com.ojas.obs.facadeimpl.InterviewResultFacadeImpl;
import com.ojas.obs.model.InterviewResult;
import com.ojas.obs.repositories.InterviewResultRepository;
import com.ojas.obs.request.InterviewResultRequest;
import com.ojas.obs.response.ErrorResponse;
import com.ojas.obs.response.InterviewResultResponse;

public class InterviewResultFacadeImplTest {  
	@InjectMocks
    InterviewResultFacadeImpl interviewfacadeImpl;
    
    @Mock
    InterviewResultRepository interviewModeRepo;
    
    @Mock
    InterviewResultFacade  interviewResultfacadeImpl; 
    
    @Spy
    InterviewResultRequest interviewResultreq;
    
    @Spy
    ErrorResponse errorresponse;
    
    @Spy
    InterviewResultResponse interviewResultresponse;
    
    @Spy
    ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorresponse, HttpStatus.UNPROCESSABLE_ENTITY);
    
    @Spy
    ResponseEntity<Object> conflict = new ResponseEntity<>(errorresponse, HttpStatus.CONFLICT);
    
    @Spy
    ResponseEntity<Object> successResponse = new ResponseEntity<>(interviewResultresponse, HttpStatus.OK);
    
    @Spy
    InterviewResult interviewResult;
    
    @Before
    public void init() throws Exception 
    {
    	interviewfacadeImpl=new InterviewResultFacadeImpl();
        interviewModeRepo = mock(InterviewResultRepository.class);
        setCollaborator(interviewfacadeImpl, "interviewModeRepo", interviewModeRepo);
    }
    
    private void setCollaborator(Object object, String name, Object service) throws Exception 
    {
        Field field;
        field = object.getClass().getDeclaredField(name);
        field.setAccessible(true);
        field.set(object, service);    
    }
    
    public List<InterviewResult> getInterviewResults() {
        List<InterviewResult> resultlist = new ArrayList<InterviewResult>();
        InterviewResult resultdatalist = new InterviewResult();
        resultdatalist.setInterviewresultId(1);
        resultdatalist.setInterviewResult("clientodc");
        
        
        InterviewResult resultdatalist1 = new InterviewResult();
        resultdatalist1.setInterviewresultId(2);
        resultdatalist1.setInterviewResult("ojasodc");
        
        resultlist.add(resultdatalist);
        resultlist.add(resultdatalist1);
        return resultlist;
    }
    
    @Test
    public void testSaveError() throws SQLException 
    { 
        
        InterviewResultRequest interviewresultreq = new InterviewResultRequest();
        
        interviewresultreq.setTransactionType("save");
        
        interviewresultreq.setInterviewresultList(this.getInterviewResults());
        
        InterviewResult interviewresult = new InterviewResult();

 
		List<InterviewResult> list = new ArrayList<InterviewResult>();
		list.add(interviewresult);
		interviewresultreq.setInterviewresultList(list);
 

        when(interviewModeRepo.save(interviewresult)).thenReturn(interviewresult);    
        
        ResponseEntity<Object> saveStatus = interviewfacadeImpl.saveDetails(interviewresultreq);
        
        HttpStatus statusCode = saveStatus.getStatusCode();
        
        assertEquals(HttpStatus.CONFLICT, statusCode);
        
    }
    @Test
	public void testSavesuccescheck() throws SQLException 
	{
		
    	InterviewResultRequest interviewresultreq = new InterviewResultRequest();
		
		interviewresultreq.setTransactionType("save");
		
		interviewresultreq.setInterviewresultList(this.getInterviewResults());
		
		InterviewResult interviewresult2 = new InterviewResult();
		interviewresult2.setInterviewresultId(1);
		interviewresult2.setInterviewResult("cat");
		
		List<InterviewResult> interviewresult  = new ArrayList<InterviewResult>();
		interviewresult.add(interviewresult2);
		interviewresultreq.setInterviewresultList(interviewresult);
		
		when(interviewModeRepo.save(interviewresult2)).thenReturn(interviewresult2);	
		
		ResponseEntity<Object> saveStatus = interviewfacadeImpl.saveDetails(interviewresultreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.OK, statusCode);
	}
	@Test
	public void testupdatesuccesscheck() throws SQLException 
	{
		
		InterviewResultRequest interviewresultreq = new InterviewResultRequest();
		
		interviewresultreq.setTransactionType("update");
		
		interviewresultreq.setInterviewresultList(this.getInterviewResults());
		
		InterviewResult sarstatus2 = new InterviewResult();
		sarstatus2.setInterviewresultId(1);
		sarstatus2.setInterviewResult("cat");
		
		List<InterviewResult> interviewresult  = new ArrayList<InterviewResult>();
		interviewresult.add(sarstatus2);
		interviewresultreq.setInterviewresultList(interviewresult);

		Integer id=interviewresultreq.getInterviewresultList().get(0).getInterviewresultId();
		
		when(interviewModeRepo.getOne(id)).thenReturn(sarstatus2);
			
		ResponseEntity<Object> saveStatus = interviewfacadeImpl.saveDetails(interviewresultreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	@Test
	public void testupdateErrorcheck() throws SQLException 
	{
		
		InterviewResultRequest interviewresultreq = new InterviewResultRequest();
		
		interviewresultreq.setTransactionType("update");
		
		interviewresultreq.setInterviewresultList(this.getInterviewResults());
		
		InterviewResult sarstatus2 = new InterviewResult();
		sarstatus2.setInterviewresultId(null);
		sarstatus2.setInterviewResult("cat");
		
		List<InterviewResult> interviewresult  = new ArrayList<InterviewResult>();
		interviewresult.add(sarstatus2);
		interviewresultreq.setInterviewresultList(interviewresult);

		Integer id=interviewresultreq.getInterviewresultList().get(0).getInterviewresultId();
		
		when(interviewModeRepo.getOne(id)).thenReturn(sarstatus2);
			
		ResponseEntity<Object> saveStatus = interviewfacadeImpl.saveDetails(interviewresultreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	
	@Test
	public void testdeletesuccesscheck() throws SQLException 
	{
		
		InterviewResultRequest interviewresultreq = new InterviewResultRequest();
		
		interviewresultreq.setTransactionType("delete");
		
		interviewresultreq.setInterviewresultList(this.getInterviewResults());
		
		InterviewResult sarstatus2 = new InterviewResult();	
		sarstatus2.setInterviewresultId(1);
		sarstatus2.setInterviewResult("cat");
		
		List<InterviewResult> interviewresult  = new ArrayList<InterviewResult>();
		interviewresult.add(sarstatus2);
		interviewresultreq.setInterviewresultList(interviewresult);

		Integer id=interviewresultreq.getInterviewresultList().get(0).getInterviewresultId();
		
		when(interviewModeRepo.getOne(id)).thenReturn(sarstatus2);
					
		sarstatus2.setStatus(sarstatus2.getStatus()==null);
		
		when(interviewModeRepo.save(sarstatus2)).thenReturn(sarstatus2);
			
		ResponseEntity<Object> saveStatus = interviewfacadeImpl.saveDetails(interviewresultreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	@Test
	public void testdeleteErrorcheck() throws SQLException 
	{
		
		InterviewResultRequest interviewresultreq = new InterviewResultRequest();
		
		interviewresultreq.setTransactionType("delete");
		
		interviewresultreq.setInterviewresultList(this.getInterviewResults());
		
		InterviewResult sarstatus2 = new InterviewResult();
		sarstatus2.setInterviewresultId(null);
		sarstatus2.setInterviewResult(null);
		sarstatus2.setStatus(null);
		
		List<InterviewResult> interviewresult  = new ArrayList<InterviewResult>();
		interviewresult.add(sarstatus2);
		interviewresultreq.setInterviewresultList(interviewresult);

		Integer id=interviewresultreq.getInterviewresultList().get(0).getInterviewresultId();
		
		when(interviewModeRepo.getOne(id)).thenReturn(sarstatus2);
					
		sarstatus2.setStatus(sarstatus2.getStatus()!=null);
		
		when(interviewModeRepo.save(sarstatus2)).thenReturn(sarstatus2);
			
		ResponseEntity<Object> saveStatus = interviewfacadeImpl.saveDetails(interviewresultreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	public void TestElseError() throws SQLException 
	{
		
		InterviewResultRequest interviewresultreq = new InterviewResultRequest();
		
		interviewresultreq.setTransactionType("ss");
		
		interviewresultreq.setInterviewresultList(this.getInterviewResults());
		
		InterviewResult interviewresult2 = new InterviewResult();

		when(interviewModeRepo.save(interviewresult2)).thenReturn(interviewresult2);	
		
		ResponseEntity<Object> saveStatus = interviewfacadeImpl.saveDetails(interviewresultreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	
	@Test
	public void getAllSuccess() throws SQLException 
	{
		
		InterviewResultRequest interviewresultreq = new InterviewResultRequest();
		
		interviewresultreq.setInterviewresultList(this.getInterviewResults());
		
		interviewresultreq.setTransactionType("getAll");
	
		InterviewResult servicetype2 = new InterviewResult();
	
		List<InterviewResult> interviewresult  = new ArrayList<InterviewResult>();
		interviewresult.add(servicetype2);
		interviewresultreq.setInterviewresultList(interviewresult);

		when(interviewModeRepo.findAll()).thenReturn(interviewresult);	
		
		ResponseEntity<Object> saveStatus = interviewfacadeImpl.getDetails(interviewresultreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	@Test
	public void getAllError() throws SQLException 
	{
		
		InterviewResultRequest interviewresultreq = new InterviewResultRequest();
		
		interviewresultreq.setInterviewresultList(this.getInterviewResults());
		
		interviewresultreq.setTransactionType("getAll");
	
		InterviewResult interviewresult2 = new InterviewResult();
		
	
		List<InterviewResult> interviewresult  = new ArrayList<InterviewResult>();
		interviewresult.add(interviewresult2);
		interviewresultreq.setInterviewresultList(interviewresult);

		when(interviewModeRepo.findAll()).thenReturn(interviewresult);
		
		ResponseEntity<Object> saveStatus = interviewfacadeImpl.getDetails(interviewresultreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	@Test
	public void getByIdError() throws SQLException 
	{
		
		InterviewResultRequest interviewresultreq = new InterviewResultRequest();
		
		interviewresultreq.setInterviewresultList(this.getInterviewResults());
	
		InterviewResult interviewresult2 = new InterviewResult();
		interviewresult2.setInterviewresultId(1);
		
		List<InterviewResult> interviewresult  = new ArrayList<InterviewResult>();
		interviewresult.add(interviewresult2);
		interviewresultreq.setInterviewresultList(interviewresult);
    	
		interviewresultreq.setTransactionType("getById");
    	Integer id=interviewresult.get(0).getInterviewresultId();

		when(interviewModeRepo.findAll()).thenReturn(interviewresult);	
		
		ResponseEntity<Object> saveStatus = interviewfacadeImpl.getDetails(interviewresultreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	
	@Test
	public void getByIdSuccess() throws SQLException 
	{
		
		InterviewResultRequest interviewresultreq = new InterviewResultRequest();
		
		interviewresultreq.setInterviewresultList(this.getInterviewResults());
		
		InterviewResult interviewresult2 = new InterviewResult();
		interviewresult2.setInterviewresultId(1);
		
		List<InterviewResult> interviewresult  = new ArrayList<InterviewResult>();
		interviewresult.add(interviewresult2);
		interviewresultreq.setInterviewresultList(interviewresult);
    	
		interviewresultreq.setTransactionType("getById");
    	Integer id=interviewresult.get(0).getInterviewresultId();
    	
		when(interviewModeRepo.findById(id)).thenReturn(Optional.of(interviewresult2));
		interviewresult.add(interviewresult2);
		
		ResponseEntity<Object> saveStatus = interviewfacadeImpl.getDetails(interviewresultreq);
		
		HttpStatus statusCode = saveStatus.getStatusCode();
		
		assertEquals(HttpStatus.OK, statusCode);
	
	
	 
	
	}
}
