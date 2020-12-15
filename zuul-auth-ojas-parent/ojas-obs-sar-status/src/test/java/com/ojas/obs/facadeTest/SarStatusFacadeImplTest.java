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

import com.ojas.obs.facade.SarStatusFacade;
import com.ojas.obs.facadeimpl.SarStatusFacadeImpl;
import com.ojas.obs.model.SarStatus;
import com.ojas.obs.repositories.SarStatusRepository;
import com.ojas.obs.request.SarStatusRequest;
import com.ojas.obs.response.ErrorResponse;
import com.ojas.obs.response.SarStatusResponse;

public class SarStatusFacadeImplTest {
	@InjectMocks
	SarStatusFacadeImpl sarfacadeimpl;

	@Mock
	SarStatusRepository sarStatusRepo;

	@Mock
	SarStatusFacade sarfacadeImpl;

	@Spy
	SarStatusRequest sarStatusreq;
	@Spy
	ErrorResponse errorresponse;

	@Spy
	SarStatusResponse sarStatusresponse;

	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorresponse, HttpStatus.UNPROCESSABLE_ENTITY);

	@Spy
	ResponseEntity<Object> conflict = new ResponseEntity<>(errorresponse, HttpStatus.CONFLICT);

	@Spy
	ResponseEntity<Object> successResponse = new ResponseEntity<>(sarStatusresponse, HttpStatus.OK);

	@Spy
	SarStatus sarstatus;

	@Before
	public void init() throws Exception {
		sarfacadeimpl = new SarStatusFacadeImpl();
		sarStatusRepo = mock(SarStatusRepository.class);
		setCollaborator(sarfacadeimpl, "sarStatusRepo", sarStatusRepo);
	}

	private void setCollaborator(Object object, String name, Object service) throws Exception {
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);
	}

	public List<SarStatus> getSarStatus() {
		List<SarStatus> statuslist = new ArrayList<SarStatus>();
		SarStatus statusdatalist = new SarStatus();
		statusdatalist.setSarstatusId(1);
		SarStatus statusdatalist1 = new SarStatus();
		statusdatalist1.setSarstatusId(2);
		statuslist.add(statusdatalist);
		statuslist.add(statusdatalist1);
		return statuslist;
	}

	@Test
	public void testSaveError() throws SQLException {

		SarStatusRequest sarstatusreq = new SarStatusRequest();

		sarstatusreq.setTransactionType("sa");

		sarstatusreq.setSarstatusList(this.getSarStatus());

		SarStatus sarstatus2 = new SarStatus();

		when(sarStatusRepo.save(sarstatus2)).thenReturn(sarstatus2);

		ResponseEntity<Object> saveStatus = sarfacadeimpl.saveDetails(sarstatusreq);

		HttpStatus statusCode = saveStatus.getStatusCode();

		assertEquals(HttpStatus.OK, statusCode);
	}

	
	  @Test
	  public void testSavesuccescheck() throws SQLException {
	  
	  SarStatusRequest sarstatusreq = new SarStatusRequest();
	  
	  sarstatusreq.setTransactionType("save");
	  sarstatusreq.setSarstatusList(this.getSarStatus()); 
	  
	  SarStatus status = new SarStatus();
	  status.setSarstatusId(5); 
	  status.setSarStatus("Hyd");
	  //status.setStatus(true); 
	 
	  List<SarStatus>sarstatus = new ArrayList<SarStatus>();
	  sarstatus.add(status);
	  sarstatusreq.setSarstatusList(sarstatus);
	  
	  when(sarStatusRepo.save(status)).thenReturn(status);
	  ResponseEntity<Object> saveStatus = sarfacadeimpl.saveDetails(sarstatusreq);
	  HttpStatus statusCode = saveStatus.getStatusCode();
	  assertEquals(HttpStatus.OK, statusCode); }
	  
	

	@Test
	public void testupdatesuccesscheck() throws SQLException {

		SarStatusRequest sarstatusreq = new SarStatusRequest();

		sarstatusreq.setTransactionType("update");

		sarstatusreq.setSarstatusList(this.getSarStatus());

		SarStatus sarstatus2 = new SarStatus();
		sarstatus2.setSarstatusId(1);
		sarstatus2.setSarStatus("cat");

		List<SarStatus> sarstatus = new ArrayList<SarStatus>();
		sarstatus.add(sarstatus2);
		sarstatusreq.setSarstatusList(sarstatus);

		Integer id = sarstatusreq.getSarstatusList().get(0).getSarstatusId();

		when(sarStatusRepo.getOne(id)).thenReturn(sarstatus2);

		ResponseEntity<Object> saveStatus = sarfacadeimpl.saveDetails(sarstatusreq);

		HttpStatus statusCode = saveStatus.getStatusCode();

		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void testupdateErrorcheck() throws SQLException {

		SarStatusRequest sarstatusreq = new SarStatusRequest();

		sarstatusreq.setTransactionType("update");

		sarstatusreq.setSarstatusList(this.getSarStatus());

		SarStatus sarstatus2 = new SarStatus();
		sarstatus2.setSarstatusId(null);
		sarstatus2.setSarStatus("cat");

		List<SarStatus> sarstatus = new ArrayList<SarStatus>();
		sarstatus.add(sarstatus2);
		sarstatusreq.setSarstatusList(sarstatus);

		Integer id = sarstatusreq.getSarstatusList().get(0).getSarstatusId();

		when(sarStatusRepo.getOne(id)).thenReturn(sarstatus2);
		ResponseEntity<Object> saveStatus = sarfacadeimpl.saveDetails(sarstatusreq);

		HttpStatus statusCode = saveStatus.getStatusCode();

		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void testdeletesuccesscheck() throws SQLException {

		SarStatusRequest sarstatusreq = new SarStatusRequest();

		sarstatusreq.setTransactionType("delete");

		sarstatusreq.setSarstatusList(this.getSarStatus());

		SarStatus sarstatus2 = new SarStatus();
		sarstatus2.setSarstatusId(1);
		// servicetype2.setServiceType("cat");

		List<SarStatus> sarstatus = new ArrayList<SarStatus>();
		sarstatus.add(sarstatus2);
		sarstatusreq.setSarstatusList(sarstatus);

		Integer id = sarstatusreq.getSarstatusList().get(0).getSarstatusId();

		when(sarStatusRepo.getOne(id)).thenReturn(sarstatus2);

		sarstatus2.setStatus(sarstatus2.getStatus() == null);

		when(sarStatusRepo.save(sarstatus2)).thenReturn(sarstatus2);

		ResponseEntity<Object> saveStatus = sarfacadeimpl.saveDetails(sarstatusreq);

		HttpStatus statusCode = saveStatus.getStatusCode();

		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void testdeleteErrorcheck() throws SQLException {

		SarStatusRequest sarstatusreq = new SarStatusRequest();
		sarstatusreq.setTransactionType("delete");
		List<SarStatus> sarstatus = new ArrayList<SarStatus>();
		SarStatus sarstatus2 = new SarStatus();
		sarstatus2.setSarstatusId(null);
		sarstatus2.setStatus(false);
		sarstatus.add(sarstatus2);
		sarstatusreq.setSarstatusList(sarstatus);
		Integer id = sarstatusreq.getSarstatusList().get(0).getSarstatusId();
		when(sarStatusRepo.getOne(id)).thenReturn(sarstatus2);
		when(sarStatusRepo.save(sarstatus2)).thenReturn(sarstatus2);
		ResponseEntity<Object> saveStatus = sarfacadeimpl.saveDetails(sarstatusreq);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void TestElseError() throws SQLException {

		SarStatusRequest sarstatusreq = new SarStatusRequest();

		sarstatusreq.setTransactionType("ss");

		sarstatusreq.setSarstatusList(this.getSarStatus());

		SarStatus sarstatus2 = new SarStatus();

		when(sarStatusRepo.save(sarstatus2)).thenReturn(sarstatus2);

		ResponseEntity<Object> saveStatus = sarfacadeimpl.saveDetails(sarstatusreq);

		HttpStatus statusCode = saveStatus.getStatusCode();

		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void getAllSuccess() throws SQLException {

		SarStatusRequest sarstatusreq = new SarStatusRequest();
		sarstatusreq.setTransactionType("delete");
		List<SarStatus> sarstatus = new ArrayList<SarStatus>();
		SarStatus sarstatus2 = new SarStatus();
		sarstatus2.setSarstatusId(null);
		sarstatus2.setStatus(false);
		sarstatus.add(sarstatus2);
		sarstatusreq.setSarstatusList(sarstatus);
		when(sarStatusRepo.findAll()).thenReturn(sarstatus);

		ResponseEntity<Object> saveStatus = sarfacadeimpl.getDetails(sarstatusreq);

		HttpStatus statusCode = saveStatus.getStatusCode();

		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void getAllError() throws SQLException {
		SarStatusRequest sarstatusreq = new SarStatusRequest();
		List<SarStatus> sarstatus = new ArrayList<SarStatus>();
		SarStatus sarstatus2 = new SarStatus();
		sarstatus2.setSarstatusId(null);
		sarstatus2.setStatus(false);
		sarstatus2.setSarStatus(null);
		sarstatus.isEmpty();
		sarstatusreq.setTransactionType("getAll");
		sarstatusreq.setSarstatusList(sarstatus);
		when(sarStatusRepo.findAll()).thenReturn(sarstatus);
		ResponseEntity<Object> saveStatus = sarfacadeimpl.getDetails(sarstatusreq);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void getAllTest() throws SQLException {
		SarStatusRequest sarstatusreq = new SarStatusRequest();
		List<SarStatus> sarstatus = new ArrayList<SarStatus>();
		SarStatus sarstatus2 = new SarStatus();
		sarstatus2.setSarStatus(null);
		sarstatus2.setStatus(false);
		sarstatus2.setSarStatus(null);
		sarstatus.add(sarstatus2);
		sarstatusreq.setTransactionType("getAll");
		sarstatusreq.setSarstatusList(sarstatus);
		when(sarStatusRepo.findAll()).thenReturn(sarstatus);
		ResponseEntity<Object> saveStatus = sarfacadeimpl.getDetails(sarstatusreq);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void getByIdError() throws SQLException {

		SarStatusRequest sarstatusreq = new SarStatusRequest();

		sarstatusreq.setSarstatusList(this.getSarStatus());

		SarStatus sarstatus2 = new SarStatus();
		sarstatus2.setSarstatusId(1);

		List<SarStatus> sarstatus = new ArrayList<SarStatus>();
		sarstatus.add(sarstatus2);
		sarstatusreq.setSarstatusList(sarstatus);

		sarstatusreq.setTransactionType("getById");
		Integer id = sarstatus.get(0).getSarstatusId();

		when(sarStatusRepo.findAll()).thenReturn(sarstatus);

		ResponseEntity<Object> saveStatus = sarfacadeimpl.getDetails(sarstatusreq);

		HttpStatus statusCode = saveStatus.getStatusCode();

		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void getByIdSuccess() throws SQLException {

		SarStatusRequest sarstatusreq = new SarStatusRequest();

		sarstatusreq.setSarstatusList(this.getSarStatus());

		SarStatus sarstatus2 = new SarStatus();
		sarstatus2.setSarstatusId(1);

		List<SarStatus> sarstatus = new ArrayList<SarStatus>();
		sarstatus.add(sarstatus2);
		sarstatusreq.setSarstatusList(sarstatus);

		sarstatusreq.setTransactionType("getById");
		Integer id = sarstatus.get(0).getSarstatusId();

		when(sarStatusRepo.findById(id)).thenReturn(Optional.of(sarstatus2));
		sarstatus.add(sarstatus2);

		ResponseEntity<Object> saveStatus = sarfacadeimpl.getDetails(sarstatusreq);

		HttpStatus statusCode = saveStatus.getStatusCode();

		assertEquals(HttpStatus.OK, statusCode);

	}
}
