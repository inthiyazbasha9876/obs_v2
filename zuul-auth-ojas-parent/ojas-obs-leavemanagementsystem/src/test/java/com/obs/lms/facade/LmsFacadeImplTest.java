package com.obs.lms.facade;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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
import org.springframework.mail.javamail.JavaMailSender;

import com.obs.lms.controller.LmsController;
import com.obs.lms.model.LeaveBalance;
import com.obs.lms.model.LeaveInfo;
import com.obs.lms.repository.LeaveBalanceRepo;
import com.obs.lms.repository.LeaveInfoRepository;
import com.obs.lms.request.LmsRequest;
import com.obs.lms.response.ErrorResponse;
import com.obs.lms.response.LmsResponse;

public class LmsFacadeImplTest {
	@InjectMocks
	@Spy
	private LmsFacadeImpl lmsfacadeimpl;
	@Mock
	private LmsController lmsController;

	@Mock
	private LeaveInfoRepository lmsRepo;

	@Mock
	private JavaMailSender javaMailSender;

	@Mock
	private JdbcTemplate jdbcTemplate;
	@Mock
	private DriverManagerDataSource driverManagerDataSource;

	@Mock
	private Environment env;

	@Mock
	private LeaveBalanceRepo leaveBalanceRepo;
	
	@Spy
	LmsRequest lmsRequest =new LmsRequest();
	@Spy
	private LmsResponse lmsResponse;
	@Spy
	private LeaveInfo leave;
	@Spy
	private ErrorResponse errorResponse;
	
	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
	@Spy
	ResponseEntity<Object> conflict = new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
	@Spy
	ResponseEntity<Object> successResponse = new ResponseEntity<>(lmsResponse, HttpStatus.OK);
	@Before
	public void init() throws Exception {
		MockitoAnnotations.initMocks(this);
		//setCollaborator(lmsfacadeimpl, "lmsRepo", lmsRepo);
	}

	
	public LeaveInfo getLeaveList() {
		//Date date = new Date(0);
		List<LeaveInfo> list = new ArrayList<>();
		leave.setAppliedOn("ajay");
		leave.setApplyTo("emp");
		leave.setApplyType("LeaveApply");
		leave.setAttachment("djdhjdfj");
		leave.setStatus("approved");
		leave.setCcTo("sachin");
		leave.setComment("no");
		leave.setContactDetails((long) 9885511);
		leave.setCountNumOfDays(0.5f);
		leave.setFileName("weding");
		leave.setFilePath("C:\\Users\\najay\\Desktop\\LMS_Docs\\");
		leave.setFlag(true);
		leave.setFromDate("2019-12-12");
		leave.setToDate("2019-12-12");
		leave.setId(5);
		leave.setLeaveReason("i went home");
		leave.setLeaveType("Sick leave");
		leave.setSession1("morning");
		leave.setSession2("afternoon");
		leave.setUpdatedOn("bharath");
		leave.setUpdatedBy("suresh");
		list.add(leave);
		return leave;
	}
	
	@Test
	public void updateStatustestFail() throws IOException {
		LeaveBalance lb=new LeaveBalance();
		lb.setConsumedSickLeave(2.5f);
		lmsRequest.setTransationType("updatestatus");
		lmsRequest.setLeaveInfo(getLeaveList());
		lmsRequest.setLeaveBalance(lb);
		int id = lmsRequest.getLeaveInfo().getId();
		when( lmsRepo.getOne(id)).thenReturn(leave);
		when(lmsRepo.save(leave)).thenReturn(leave);
		doReturn(false).when(lmsfacadeimpl).sendMail(leave);
		when(leaveBalanceRepo.getAllLeaveBalByEmpId(leave.getEmpId())).thenReturn(lb);
		ResponseEntity<Object> saveStatus = lmsfacadeimpl.setLms(lmsRequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);

	}

	@Test
	public void deleteTest() throws IOException {
		lmsRequest.setTransationType("delete");
		leave.setId(1);
		leave.setFlag(true);
		lmsRequest.setLeaveInfo(getLeaveList());
		int id = lmsRequest.getLeaveInfo().getId();
		when(lmsRepo.getOne(id)).thenReturn(leave);
		when(lmsRepo.save(leave)).thenReturn(leave);
		doReturn(true).when(lmsfacadeimpl).sendMail(leave);
		ResponseEntity<Object> saveStatus = lmsfacadeimpl.setLms(lmsRequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);

	}

	@Test
	public void getByIdTest() throws IOException {
		List<LeaveInfo> leavelist = new ArrayList<LeaveInfo>();
		leave.setId(1);
		leavelist.add(leave);
		lmsRequest.setLeaveInfo(leave);
		lmsRequest.setTransationType("getById");
		int id = lmsRequest.getLeaveInfo().getId();
		when(lmsRepo.getOne(id)).thenReturn(leave);
		ResponseEntity<Object> saveStatus = lmsfacadeimpl.getLms(lmsRequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
		

	}
	@Test
	public void getByLeave() throws IOException {
		List<LeaveInfo> leavelist = new ArrayList<LeaveInfo>();
		LeaveInfo leave = new LeaveInfo();
		leave.setId(1);
		leave.setEmpId("2555");
		leavelist.add(leave);
		lmsRequest.setLeaveInfo(leave);
		lmsRequest.setTransationType("getallleaveinfo");
		String id = lmsRequest.getLeaveInfo().getEmpId();
		when(lmsRepo.getByEmpId(id)).thenReturn(leavelist);
		ResponseEntity<Object> saveStatus = lmsfacadeimpl.getLms(lmsRequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
		

	}


	@Test
	public void getGetbymanager() throws IOException {
		lmsRequest.setLeaveInfo(getLeaveList());
		List<LeaveInfo> leavelist = new ArrayList<LeaveInfo>();
		leave.setId(1);
		leave.setApplyTo("apply");
		leavelist.add(leave);
		lmsRequest.setTransationType("getbymanager");
		when(lmsRepo.getByManagerId(lmsRequest.getLeaveInfo().getApplyTo())).thenReturn(leavelist);
		ResponseEntity<Object> saveStatus = lmsfacadeimpl.getLms(lmsRequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void getAll() throws IOException {
		lmsRequest.setTransationType("getAll");
		List<LeaveInfo> leavelist = new ArrayList<LeaveInfo>();
		leave.setId(1);
		leave.setApplyTo("apply");
		leavelist.add(leave);
		when(lmsRepo.findAll()).thenReturn(leavelist);
		ResponseEntity<Object> saveStatus = lmsfacadeimpl.getLms(lmsRequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}
	@Test
	public void getAllNoRecords() throws IOException {
		lmsRequest.setTransationType("getAll");
		List<LeaveInfo> leavelist = new ArrayList<LeaveInfo>();
		leavelist.isEmpty();
		when(lmsRepo.findAll()).thenReturn(leavelist);
		ResponseEntity<Object> saveStatus = lmsfacadeimpl.getLms(lmsRequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}


	@Test
	public void getfile() throws IOException {
		lmsRequest.setTransationType("getFile");
		lmsRequest.setLeaveInfo(getLeaveList());
		String filePath = null;
		Integer id = lmsRequest.getLeaveInfo().getId();
		when(lmsRepo.getFilePath(id)).thenReturn(filePath);
		ResponseEntity<Object> saveStatus = lmsfacadeimpl.getLms(lmsRequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	@Test
	public void getAllLeave() throws IOException {
		lmsRequest.setLeaveInfo(leave);
		lmsRequest.setTransationType("getallleavebal"); 
		LeaveBalance ib=new LeaveBalance();
		ib.setConsumedCasualLeave(44.5f);
		ib.setConsumedCompOff(1.5f);
		ib.setConsumedMaternityLeave(2.3f);
		ib.setConsumedSickLeave(0.5f);
		ib.setEmpId("256");
		ib.setId(5);
		ib.setLossOfPay(1.8f);
		ib.setTotalCasualLeave(6.5f);
		ib.setTotalCompOff(1.6f);
		ib.setTotalMaternityLeave(3.2f);
		ib.setTotalSickLeave(2.5f);
		lmsRequest.setLeaveBalance(ib);
		String empid=lmsRequest.getLeaveBalance().getEmpId();
		when(leaveBalanceRepo.getAllLeaveBalByEmpId(empid)).thenReturn(ib);
		ResponseEntity<Object> saveStatus = lmsfacadeimpl.getLms(lmsRequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
		
	}
	@Test
	public void getByIdTestNull() throws IOException {
		List<LeaveInfo> leavelist = new ArrayList<LeaveInfo>();
		leave.setId(1);
		leavelist.add(leave);
		lmsRequest.setLeaveInfo(leave);
		lmsRequest.setTransationType("getBy");
		int id = lmsRequest.getLeaveInfo().getId();
		when(lmsRepo.getOne(id)).thenReturn(null);
		ResponseEntity<Object> saveStatus = lmsfacadeimpl.getLms(lmsRequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
		

	}
	
	
}