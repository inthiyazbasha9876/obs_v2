package com.ojas.obs.facadeimplTest;

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

import com.ojas.obs.facade.LeaveTypeFacade;
import com.ojas.obs.facadeimpl.LeaveTypeFacadeImpl;
import com.ojas.obs.model.LeaveType;
import com.ojas.obs.repository.LeaveTypeRepository;
import com.ojas.obs.request.LeaveTypeRequest;
import com.ojas.obs.response.ErrorResponse;
import com.ojas.obs.response.LeaveTypeResponse;

public class LeaveTypeFacadeImplTest {
	@InjectMocks
	LeaveTypeFacadeImpl leavetypefacadeImpl;

	@Mock
	LeaveTypeRepository leaveTypeRepo;

	@Mock
	LeaveTypeFacade leavetypeFacadeImpl;

	@Spy
	LeaveTypeRequest leavetypereq;

	@Spy
	ErrorResponse errorresponse;

	@Spy
	LeaveTypeResponse leavetyepresponse;

	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorresponse, HttpStatus.UNPROCESSABLE_ENTITY);

	@Spy
	ResponseEntity<Object> conflict = new ResponseEntity<>(errorresponse, HttpStatus.CONFLICT);

	@Spy
	ResponseEntity<Object> successResponse = new ResponseEntity<>(leavetyepresponse, HttpStatus.OK);

	@Spy
	LeaveType leavetyepe;

	@Before
	public void init() throws Exception {
		leavetypefacadeImpl = new LeaveTypeFacadeImpl();
		leaveTypeRepo = mock(LeaveTypeRepository.class);
		setCollaborator(leavetypefacadeImpl, "leaveTypeRepo", leaveTypeRepo);
	}

	private void setCollaborator(Object object, String name, Object service) throws Exception {
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);
	}

	public List<LeaveType> getList() {
		List<LeaveType> leavetypelist = new ArrayList<LeaveType>();
		LeaveType leavedatalist = new LeaveType();
		leavedatalist.setLeaveTypeId(1);
		LeaveType leavedatalist1 = new LeaveType();
		leavedatalist1.setLeaveTypeId(2);
		leavetypelist.add(leavedatalist);
		leavetypelist.add(leavedatalist1);
		return leavetypelist;
	}

	@Test
	public void testSaveError() throws SQLException {

		LeaveTypeRequest leavereq = new LeaveTypeRequest();

		leavereq.setTransactionType("save");

		leavereq.setLeaveTypeList(this.getList());

		LeaveType leavetype2 = new LeaveType();

		when(leaveTypeRepo.save(leavetype2)).thenReturn(leavetype2);

		ResponseEntity<Object> saveStatus = leavetypefacadeImpl.saveDetails(leavereq);

		HttpStatus statusCode = saveStatus.getStatusCode();

		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void testSavesuccescheck() throws SQLException {

		LeaveTypeRequest leavereq = new LeaveTypeRequest();
		List<LeaveType> leavetype = new ArrayList<LeaveType>();
		LeaveType leave = new LeaveType();
		leave.setLeaveTypeId(5);
		leave.setLeaveTypeName("ss");
		leave.setStatus(true);
		leavetype.add(leave);		
		leavereq.setTransactionType("save");
		leavereq.setLeaveTypeList(leavetype);
		when(leaveTypeRepo.saveAll(leavetype)).thenReturn(leavetype);
		ResponseEntity<Object> saveStatus = leavetypefacadeImpl.saveDetails(leavereq);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);;
	}

	@Test
	public void testupdatesuccesscheck() throws SQLException {

		LeaveTypeRequest leavereq = new LeaveTypeRequest();

		leavereq.setTransactionType("update");

		leavereq.setLeaveTypeList(this.getList());

		LeaveType leavetype2 = new LeaveType();
		leavetype2.setLeaveTypeId(1);
		List<LeaveType> leavetype = new ArrayList<LeaveType>();
		leavetype.add(leavetype2);
		leavereq.setLeaveTypeList(leavetype);

		Integer id = leavereq.getLeaveTypeList().get(0).getLeaveTypeId();

		when(leaveTypeRepo.findById(id)).thenReturn(Optional.of(leavetype2));

		ResponseEntity<Object> saveStatus = leavetypefacadeImpl.saveDetails(leavereq);

		HttpStatus statusCode = saveStatus.getStatusCode();

		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void testupdateErrorcheck() throws SQLException {

		LeaveTypeRequest leavereq = new LeaveTypeRequest();

		leavereq.setTransactionType("update");

		leavereq.setLeaveTypeList(this.getList());

		LeaveType leavetype2 = new LeaveType();
		leavetype2.setLeaveTypeId(null);
		
		List<LeaveType> leavetype = new ArrayList<LeaveType>();
		leavetype.add(leavetype2);
		leavereq.setLeaveTypeList(leavetype);

		Integer id = leavereq.getLeaveTypeList().get(0).getLeaveTypeId();

		when(leaveTypeRepo.findById(id)).thenReturn(Optional.of(leavetype2));

		ResponseEntity<Object> saveStatus = leavetypefacadeImpl.saveDetails(leavereq);

		HttpStatus statusCode = saveStatus.getStatusCode();

		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void testdeletesuccesscheck() throws SQLException {

		LeaveTypeRequest leavereq = new LeaveTypeRequest();

		leavereq.setTransactionType("delete");

		leavereq.setLeaveTypeList(this.getList());

		LeaveType leavetype2 = new LeaveType();
		leavetype2.setLeaveTypeId(1);
		
		List<LeaveType> leavetype = new ArrayList<LeaveType>();
		leavetype.add(leavetype2);
		leavereq.setLeaveTypeList(leavetype);

		Integer id = leavereq.getLeaveTypeList().get(0).getLeaveTypeId();

		when(leaveTypeRepo.getOne(id)).thenReturn(leavetype2);

		leavetype2.setStatus(leavetype2.getStatus() == null);

		when(leaveTypeRepo.save(leavetype2)).thenReturn(leavetype2);

		ResponseEntity<Object> saveStatus = leavetypefacadeImpl.saveDetails(leavereq);

		HttpStatus statusCode = saveStatus.getStatusCode();

		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void testdeleteErrorcheck() throws SQLException {

		LeaveTypeRequest leavereq = new LeaveTypeRequest();
		leavereq.setTransactionType("delete");
		List<LeaveType> leavetype = new ArrayList<LeaveType>();
		LeaveType leavetype2 = new LeaveType();
		leavetype2.setLeaveTypeId(null);
		leavetype2.setStatus(false);
		leavetype.add(leavetype2);
		leavereq.setLeaveTypeList(leavetype);
		Integer id = leavereq.getLeaveTypeList().get(0).getLeaveTypeId();
		when(leaveTypeRepo.getOne(id)).thenReturn(leavetype2);
		when(leaveTypeRepo.save(leavetype2)).thenReturn(leavetype2);
		ResponseEntity<Object> saveStatus = leavetypefacadeImpl.saveDetails(leavereq);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void TestElseError() throws SQLException {

		LeaveTypeRequest leavereq = new LeaveTypeRequest();

		leavereq.setTransactionType("ss");

		leavereq.setLeaveTypeList(this.getList());

		LeaveType leavetype2 = new LeaveType();

		when(leaveTypeRepo.save(leavetype2)).thenReturn(leavetype2);

		ResponseEntity<Object> saveStatus = leavetypefacadeImpl.saveDetails(leavereq);

		HttpStatus statusCode = saveStatus.getStatusCode();

		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void getAllSuccess() throws SQLException {

		LeaveTypeRequest leavereq = new LeaveTypeRequest();
		leavereq.setTransactionType("delete");
		List<LeaveType> leavetype = new ArrayList<LeaveType>();
		LeaveType leavetype2 = new LeaveType();
		leavetype2.setLeaveTypeId(null);
		leavetype2.setStatus(false);
		leavetype.add(leavetype2);
		leavereq.setLeaveTypeList(leavetype);
		when(leaveTypeRepo.findAll()).thenReturn(leavetype);

		ResponseEntity<Object> saveStatus = leavetypefacadeImpl.getDetails(leavereq);

		HttpStatus statusCode = saveStatus.getStatusCode();

		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void getAllError() throws SQLException {
		LeaveTypeRequest leavereq = new LeaveTypeRequest();
		List<LeaveType> leavetype = new ArrayList<LeaveType>();
		LeaveType leavetype2 = new LeaveType();
		leavetype2.setLeaveTypeId(null);
		leavetype2.setStatus(false);
		leavetype2.setLeaveTypeName(null);
		leavetype.isEmpty();
		leavereq.setTransactionType("getAll");
		leavereq.setLeaveTypeList(leavetype);
		when(leaveTypeRepo.findAll()).thenReturn(leavetype);
		ResponseEntity<Object> saveStatus = leavetypefacadeImpl.getDetails(leavereq);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	@Test
	public void getAllTest() throws SQLException {
		LeaveTypeRequest leavereq = new LeaveTypeRequest();
		List<LeaveType> leavetype = new ArrayList<LeaveType>();
		LeaveType leavetype2 = new LeaveType();
		leavetype2.setLeaveTypeName(null);
		leavetype2.setStatus(false);
		leavetype2.setLeaveTypeName(null);
		leavetype.add(leavetype2);
		leavereq.setTransactionType("getAll");
		leavereq.setLeaveTypeList(leavetype);
		when(leaveTypeRepo.findAll()).thenReturn(leavetype);
		ResponseEntity<Object> saveStatus = leavetypefacadeImpl.getDetails(leavereq);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void getByIdError() throws SQLException {

		LeaveTypeRequest leavereq = new LeaveTypeRequest();

		leavereq.setLeaveTypeList(this.getList());

		LeaveType leavetype2 = new LeaveType();
		leavetype2.setLeaveTypeId(1);

		List<LeaveType> leavetype = new ArrayList<LeaveType>();
		leavetype.add(leavetype2);
		leavereq.setLeaveTypeList(leavetype);

		leavereq.setTransactionType("getById");
		Integer id = leavetype.get(0).getLeaveTypeId();

		when(leaveTypeRepo.findAll()).thenReturn(leavetype);

		ResponseEntity<Object> saveStatus = leavetypefacadeImpl.getDetails(leavereq);

		HttpStatus statusCode = saveStatus.getStatusCode();

		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void getByIdSuccess() throws SQLException {

		LeaveTypeRequest leavereq = new LeaveTypeRequest();

		leavereq.setLeaveTypeList(this.getList());

		LeaveType leavetype2 = new LeaveType();
		leavetype2.setLeaveTypeId(1);

		List<LeaveType> leavetype = new ArrayList<LeaveType>();
		leavetype.add(leavetype2);
		leavereq.setLeaveTypeList(leavetype);

		leavereq.setTransactionType("getById");
		Integer id = leavetype.get(0).getLeaveTypeId();

		when(leaveTypeRepo.findById(id)).thenReturn(Optional.of(leavetype2));
		leavetype.add(leavetype2);

		ResponseEntity<Object> saveStatus = leavetypefacadeImpl.getDetails(leavereq);

		HttpStatus statusCode = saveStatus.getStatusCode();

		assertEquals(HttpStatus.OK, statusCode);

	}
}


