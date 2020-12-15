package com.ojas.obs.facade;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ojas.obs.dao.EmployeeContactDao;
import com.ojas.obs.daoimpl.EmployeeContactDaoImpl;
import com.ojas.obs.facadeimpl.EmployeeContactFacadeImpl;
import com.ojas.obs.model.EmployeeContactInfo;
import com.ojas.obs.requests.EmployeeContactInfoRequest;
import com.ojas.obs.response.EmployeeContactInfoResponse;
import com.ojas.obs.response.ErrorResponse;

@RunWith(MockitoJUnitRunner.Silent.class)
public class EmployeeContactFacadeTest {

	@InjectMocks
	private EmployeeContactFacadeImpl employeeContactFacadeImpl;

	@Mock
	private EmployeeContactDao employeeContactDao;

	@Spy
	ErrorResponse errorResponse = new ErrorResponse();

	@Spy
	ResponseEntity<Object> responseEntity = new ResponseEntity<Object>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);

	@Spy
	ResponseEntity<Object> successEntity = new ResponseEntity<Object>(HttpStatus.OK);

	@Spy
	EmployeeContactInfoRequest employeeContactInfoRequest = new EmployeeContactInfoRequest();

	@Spy
	EmployeeContactInfoResponse employeeContactInfoResponse = new EmployeeContactInfoResponse();

	@Spy
	List<EmployeeContactInfo> employeeContactInfolist = new ArrayList<>();

	@Before
	public void beforeTest()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

		employeeContactFacadeImpl = new EmployeeContactFacadeImpl();

		employeeContactDao = mock(EmployeeContactDaoImpl.class);
		// MockitoAnnotations.initMocks(This.class);
		setCollaborator(employeeContactFacadeImpl, "employeeContactDao", employeeContactDao);

	}

	private void setCollaborator(Object object, String name, Object service)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

		Field field;

		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);

	}

	public List<EmployeeContactInfo> getEmployeeContactInfo() {

		List<EmployeeContactInfo> list = new ArrayList<>();

		EmployeeContactInfo employeeContactInfo = new EmployeeContactInfo();
		Timestamp timestamp = new Timestamp(new java.util.Date().getTime());

		employeeContactInfo.setId(1);
		employeeContactInfo.setAlternateMobileNo("9876543210");
		employeeContactInfo.setCurrentAddressLine1("Raidurgam");
		employeeContactInfo.setCurrentAddressLine2("Police Line-2");
		employeeContactInfo.setCurrentCity("Hyderbad");
		employeeContactInfo.setCurrentState("TS");
		employeeContactInfo.setCurrentPin(500001);
		employeeContactInfo.setPermanentAddressLine1("Hyderbad");
		employeeContactInfo.setEmpId("19201");
		employeeContactInfo.setCreatedBy("SaiKrishna");
		employeeContactInfo.setUpdatedBy("Krishna");
		employeeContactInfo.setCreatedDate(timestamp);
		employeeContactInfo.setUpdatedDate(timestamp);
		employeeContactInfo.setFlag(true);

		list.add(employeeContactInfo);
		return list;

	}

	public List<EmployeeContactInfo> emptyEmployeeContactInfo() {
		List<EmployeeContactInfo> list = new ArrayList<>();

		EmployeeContactInfo employeeContactInfo = new EmployeeContactInfo();

		list.add(employeeContactInfo);
		return list;
	}

	// ------------save test case----------------

	@Test
	public void setEmployeeContactInfoTestSave() throws Exception {

		EmployeeContactInfoRequest employeeContactInfoRequest = new EmployeeContactInfoRequest();
		employeeContactInfoRequest.setEmpInfo(this.getEmployeeContactInfo());

		employeeContactInfoRequest.setTransactionType("save");
		Mockito.lenient().when(employeeContactDao.saveEmployeeContactInfo(employeeContactInfoRequest)).thenReturn(true);
		ResponseEntity<Object> setEmployeeContactInfo = employeeContactFacadeImpl
				.setEmployeeContactInfo(employeeContactInfoRequest);
		HttpStatus statusCode = setEmployeeContactInfo.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);

	}

	@Test
	public void setEmployeeContactInfoTestSaveFailed() throws Exception {

		EmployeeContactInfoRequest employeeContactInfoRequest = new EmployeeContactInfoRequest();
		employeeContactInfoRequest.setEmpInfo(this.getEmployeeContactInfo());

		employeeContactInfoRequest.setTransactionType("save");
		Mockito.lenient().when(employeeContactDao.saveEmployeeContactInfo(employeeContactInfoRequest))
				.thenReturn(false);
		ResponseEntity<Object> setEmployeeContactInfo = employeeContactFacadeImpl
				.setEmployeeContactInfo(employeeContactInfoRequest);
		HttpStatus statusCode = setEmployeeContactInfo.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);

	}

	// ------------update test case----------------

	@Test
	public void setEmployeeContactInfoTestUpdate() throws Exception {

		EmployeeContactInfoRequest employeeContactInfoRequest = new EmployeeContactInfoRequest();
		employeeContactInfoRequest.setEmpInfo(this.getEmployeeContactInfo());

		employeeContactInfoRequest.setTransactionType("update");
		Mockito.lenient().when(employeeContactDao.updateEmployeeContactInfo(employeeContactInfoRequest))
				.thenReturn(true);
		ResponseEntity<Object> setEmployeeContactInfo = employeeContactFacadeImpl
				.setEmployeeContactInfo(employeeContactInfoRequest);
		HttpStatus statusCode = setEmployeeContactInfo.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);

	}

	@Test
	public void setEmployeeContactInfoTestUpdateFailed() throws Exception {

		EmployeeContactInfoRequest employeeContactInfoRequest = new EmployeeContactInfoRequest();
		employeeContactInfoRequest.setEmpInfo(this.getEmployeeContactInfo());

		employeeContactInfoRequest.setTransactionType("update");
		Mockito.lenient().when(employeeContactDao.updateEmployeeContactInfo(employeeContactInfoRequest))
				.thenReturn(false);
		ResponseEntity<Object> setEmployeeContactInfo = employeeContactFacadeImpl
				.setEmployeeContactInfo(employeeContactInfoRequest);
		HttpStatus statusCode = setEmployeeContactInfo.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);

	}

	// ------------delete test case----------------

	@Test
	public void setEmployeeContactInfoTestDelete() throws Exception {

		EmployeeContactInfoRequest employeeContactInfoRequest = new EmployeeContactInfoRequest();
		employeeContactInfoRequest.setEmpInfo(this.getEmployeeContactInfo());

		employeeContactInfoRequest.setTransactionType("delete");
		Mockito.lenient().when(employeeContactDao.deleteEmployeeContactInfo(employeeContactInfoRequest))
				.thenReturn(true);
		ResponseEntity<Object> setEmployeeContactInfo = employeeContactFacadeImpl
				.setEmployeeContactInfo(employeeContactInfoRequest);
		HttpStatus statusCode = setEmployeeContactInfo.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);

	}

	@Test
	public void setEmployeeContactInfoTestDeleteFailed() throws Exception {

		EmployeeContactInfoRequest employeeContactInfoRequest = new EmployeeContactInfoRequest();
		employeeContactInfoRequest.setEmpInfo(this.getEmployeeContactInfo());

		employeeContactInfoRequest.setTransactionType("delete");
		Mockito.lenient().when(employeeContactDao.deleteEmployeeContactInfo(employeeContactInfoRequest))
				.thenReturn(false);
		ResponseEntity<Object> setEmployeeContactInfo = employeeContactFacadeImpl
				.setEmployeeContactInfo(employeeContactInfoRequest);
		HttpStatus statusCode = setEmployeeContactInfo.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);

	}

	@Test
	public void setEmployeeContactInfoTestTransactionsTypeMismatch() throws Exception {

		EmployeeContactInfoRequest employeeContactInfoRequest = new EmployeeContactInfoRequest();
		employeeContactInfoRequest.setEmpInfo(this.getEmployeeContactInfo());

		employeeContactInfoRequest.setTransactionType("deletesdfgdtg");
		Mockito.lenient().when(employeeContactDao.deleteEmployeeContactInfo(employeeContactInfoRequest))
				.thenReturn(false);
		ResponseEntity<Object> setEmployeeContactInfo = employeeContactFacadeImpl
				.setEmployeeContactInfo(employeeContactInfoRequest);
		HttpStatus statusCode = setEmployeeContactInfo.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);

	}

	
	@Test
	public void getEmployeeContactInfoTestEmpInfoIdNotNull() throws Exception {

		EmployeeContactInfoRequest employeeContactInfoRequest = new EmployeeContactInfoRequest();
		employeeContactInfoRequest.setEmpInfo(getEmployeeContactInfo());
		employeeContactInfoRequest.getEmpInfo().get(0).setId(1);
		employeeContactInfoRequest.setTransactionType("getall");

		Mockito.lenient().when(employeeContactDao.getAllContacctDetails())
				.thenReturn(employeeContactInfolist);
		ResponseEntity<Object> getEmployeeContactInfo = employeeContactFacadeImpl
				.getEmployeeContactInfo(employeeContactInfoRequest);
		HttpStatus statusCode = getEmployeeContactInfo.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);

	}

}
