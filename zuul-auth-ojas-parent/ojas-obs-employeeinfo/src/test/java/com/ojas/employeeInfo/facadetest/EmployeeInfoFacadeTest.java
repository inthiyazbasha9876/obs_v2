package com.ojas.employeeInfo.facadetest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.ojas.obs.dao.EmployeeInfoDao;
import com.ojas.obs.daoImpl.EmployeeDaoImpl;
import com.ojas.obs.errorResponse.ErrorResponse;
import com.ojas.obs.facade.EmployeeInfoFacade;
import com.ojas.obs.facadeImpl.EmployeeInfoFacadeImpl;
import com.ojas.obs.model.EmployeeInfo;
import com.ojas.obs.request.EmployeeInfoRequest;
import com.ojas.obs.response.EmployeeInfoResponse;

public class EmployeeInfoFacadeTest {
	@InjectMocks
	private EmployeeInfoFacadeImpl employeeFacadeInfoImpl;
	@Mock
	private EmployeeInfoFacade employeeFacadeInfo;
	@Mock
	private EmployeeInfoDao employeeInfoDao;
	@Mock
	private EmployeeDaoImpl employeeInfoDaoImpl;
	@Spy
	private EmployeeInfoRequest empInfoRequest;
	@Spy
	private EmployeeInfoResponse empInfoResponse;
	@Spy
	private ErrorResponse errorResponse;
	@Spy
	private EmployeeInfo empInfo;
	@Spy
	private Environment env;
	@Mock
	private JavaMailSenderImpl javaMailSender;


	@Before
	public void init() throws Exception {
		/*
		 * employeeFacadeInfoImpl = new EmployeeInfoFacadeImpl(); employeeInfoDaoImpl =
		 * mock(EmployeeDaoImpl.class); setCollaborator(employeeFacadeInfoImpl,
		 * "employeeInfoDao", employeeInfoDaoImpl);
		 * setCollaborator(employeeFacadeInfoImpl, "env", env);
		 */
		MockitoAnnotations.initMocks(this);
	}

	/*
	 * public void setCollaborator(Object object, String name, Object service)
	 * throws Exception { Field field; field =
	 * object.getClass().getDeclaredField(name); field.setAccessible(true);
	 * field.set(object, service); }
	 */
	@Test
	public void testSave() throws SQLException {
		//EmployeeInfoRequest employeeInfoRequest = new EmployeeInfoRequest();
		List<EmployeeInfo> infoList = new ArrayList<EmployeeInfo>();
		EmployeeInfo employeeInfo = new EmployeeInfo();
		employeeInfo.setEmail("abc");
		infoList.add(employeeInfo);
		empInfoRequest.setEmployeeInfo(infoList);
		empInfoRequest.setTransactionType("save");
		when(employeeInfoDao.saveEmployeeInfo(empInfoRequest)).thenReturn(true);
		when(employeeInfoDao.getMngrMail("abc")).thenReturn("abc");
		when(env.getProperty(Mockito.anyString())).thenReturn("abc");
		ResponseEntity<Object> saveStatus = employeeFacadeInfoImpl.setEmployeeInfo(empInfoRequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	/*
	 * @Test public void testSaveNegative() throws SQLException {
	 * EmployeeInfoRequest employeeInfoRequest = new EmployeeInfoRequest();
	 * employeeInfoRequest.setTransactionType("save");
	 * when(employeeInfoDao.saveEmployeeInfo(employeeInfoRequest)).thenReturn(false)
	 * ; ResponseEntity<Object> saveStatus =
	 * employeeFacadeInfoImpl.setEmployeeInfo(employeeInfoRequest); HttpStatus
	 * statusCode = saveStatus.getStatusCode(); assertEquals(HttpStatus.CONFLICT,
	 * statusCode); }
	 */

	@Test
	public void testUpdate() throws SQLException {
		EmployeeInfoRequest employeeInfoRequest = new EmployeeInfoRequest();
		employeeInfoRequest.setTransactionType("update");
		when(employeeInfoDao.updateEmployeeInfo(employeeInfoRequest)).thenReturn(true);
		ResponseEntity<Object> saveStatus = employeeFacadeInfoImpl.setEmployeeInfo(employeeInfoRequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void testUpdateNegative() throws SQLException {
		EmployeeInfoRequest employeeInfoRequest = new EmployeeInfoRequest();
		employeeInfoRequest.setTransactionType("update");
		when(employeeInfoDao.updateEmployeeInfo(employeeInfoRequest)).thenReturn(false);
		ResponseEntity<Object> saveStatus = employeeFacadeInfoImpl.setEmployeeInfo(employeeInfoRequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void testDelete() throws SQLException {
		EmployeeInfoRequest employeeInfoRequest = new EmployeeInfoRequest();
		employeeInfoRequest.setTransactionType("delete");
		when(employeeInfoDao.deleteEmployeeInfo(employeeInfoRequest)).thenReturn(true);
		ResponseEntity<Object> saveStatus = employeeFacadeInfoImpl.setEmployeeInfo(employeeInfoRequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void testDeleteNegative() throws SQLException {
		EmployeeInfoRequest employeeInfoRequest = new EmployeeInfoRequest();
		employeeInfoRequest.setTransactionType("delete");
		when(employeeInfoDao.deleteEmployeeInfo(employeeInfoRequest)).thenReturn(false);
		ResponseEntity<Object> saveStatus = employeeFacadeInfoImpl.setEmployeeInfo(employeeInfoRequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	
	@Test
	public void testSetNull() throws SQLException {
		EmployeeInfoRequest employeeInfoRequest = new EmployeeInfoRequest();
		employeeInfoRequest.setTransactionType("");
		when(employeeInfoDao.saveEmployeeInfo(employeeInfoRequest)).thenReturn(true);
		ResponseEntity<Object> saveStatus = employeeFacadeInfoImpl.setEmployeeInfo(employeeInfoRequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void testGet() throws SQLException {
		List<EmployeeInfo> employeeInfos = new ArrayList<EmployeeInfo>();
		EmployeeInfo info = new EmployeeInfo();
		info.setId(1);
		employeeInfos.add(info);
		EmployeeInfoRequest employeeInfoRequest = new EmployeeInfoRequest();
		employeeInfoRequest.setEmployeeInfo(employeeInfos);
		employeeInfoRequest.setTransactionType("getall");
		when(employeeInfoDao.getAllEmployeeDetails(employeeInfoRequest)).thenReturn(employeeInfos);
		ResponseEntity<Object> saveStatus = employeeFacadeInfoImpl.getAllEmployeeDetails(employeeInfoRequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void testGetNegative() throws SQLException {
		List<EmployeeInfo> employeeInfos = null;
		EmployeeInfoRequest employeeInfoRequest = new EmployeeInfoRequest();
		employeeInfoRequest.setEmployeeInfo(employeeInfos);
		employeeInfoRequest.setTransactionType("getall");
		when(employeeInfoDao.getAllEmployeeDetails(employeeInfoRequest)).thenReturn(employeeInfos);
		ResponseEntity<Object> saveStatus = employeeFacadeInfoImpl.getAllEmployeeDetails(employeeInfoRequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void testGetById() throws SQLException {
		List<EmployeeInfo> employeeInfos = new ArrayList<EmployeeInfo>();
		EmployeeInfo info = new EmployeeInfo();
		info.setId(1);
		employeeInfos.add(info);
		EmployeeInfoRequest employeeInfoRequest = new EmployeeInfoRequest();
		employeeInfoRequest.setEmployeeInfo(employeeInfos);
		employeeInfoRequest.setTransactionType("getbyid");
		when(employeeInfoDao.getById(employeeInfoRequest)).thenReturn(employeeInfos);
		ResponseEntity<Object> saveStatus = employeeFacadeInfoImpl.getAllEmployeeDetails(employeeInfoRequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	@Test
	public void testGetNull() throws SQLException {
		List<EmployeeInfo> employeeInfos = null;
		EmployeeInfoRequest employeeInfoRequest = new EmployeeInfoRequest();
		employeeInfoRequest.setTransactionType("");
		when(employeeInfoDao.getAllEmployeeDetails(employeeInfoRequest)).thenReturn(employeeInfos);
		ResponseEntity<Object> saveStatus = employeeFacadeInfoImpl.getAllEmployeeDetails(employeeInfoRequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
}
