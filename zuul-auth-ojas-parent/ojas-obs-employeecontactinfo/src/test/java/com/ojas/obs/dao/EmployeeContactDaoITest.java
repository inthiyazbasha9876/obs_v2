package com.ojas.obs.dao;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;

import com.ojas.obs.daoimpl.EmployeeContactDaoImpl;
import com.ojas.obs.model.EmployeeContactInfo;
import com.ojas.obs.requests.EmployeeContactInfoRequest;
import com.ojas.obs.response.ErrorResponse;

@RunWith(MockitoJUnitRunner.Silent.class)
public class EmployeeContactDaoITest {

	@Mock
	private EmployeeContactDaoImpl employeeContactDaoImpl;

	@Spy
	EmployeeContactInfoRequest employeeContactInfoRequest = new EmployeeContactInfoRequest();

	@Spy
	ErrorResponse error = new ErrorResponse();

	@Mock
	DataSource dataSource;

	@Mock
	Connection connection;

	@Mock
	JdbcTemplate jdbcTemplate;

	@Before
	public void beforeTest() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

		employeeContactDaoImpl = new EmployeeContactDaoImpl();

		jdbcTemplate = mock(JdbcTemplate.class);
		setCollaborator(employeeContactDaoImpl, "jdbcTemplate", jdbcTemplate);

	}

	private void setCollaborator(Object object, String name, Object service) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

		Field field;
		

			field = object.getClass().getDeclaredField(name);
			field.setAccessible(true);
			field.set(object, service);

		} 

	@Test
	public void getDataSource1() throws Exception {
		Mockito.lenient().when(jdbcTemplate.getDataSource()).thenReturn(dataSource);
		assertEquals(dataSource, jdbcTemplate.getDataSource());
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
		employeeContactInfo.setCurrentState("AP");
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

	// ----------------save test case-------------------

	@Test
	public void saveEmployeeContactInfo() throws SQLException {

		employeeContactInfoRequest.setEmpInfo(getEmployeeContactInfo());
		employeeContactInfoRequest.setTransactionType("save");
		int[] count = { 1, 2 };
		Mockito.lenient().when(jdbcTemplate.batchUpdate(Mockito.anyString(), Mockito.anyList())).thenReturn(count);
		boolean save = employeeContactDaoImpl.saveEmployeeContactInfo(employeeContactInfoRequest);
		assertEquals(true, save);

	}
	
	@Test
	public void saveEmployeeContactInfoFalse() throws SQLException {

		employeeContactInfoRequest.setEmpInfo(getEmployeeContactInfo());
		employeeContactInfoRequest.setTransactionType("save");
		int[] count = {};
		Mockito.lenient().when(jdbcTemplate.batchUpdate(Mockito.anyString(), Mockito.anyList())).thenReturn(count);
		boolean save = employeeContactDaoImpl.saveEmployeeContactInfo(employeeContactInfoRequest);
		assertEquals(false, save);

	}
	// ----------------update test case-------------------
	@Test
	public void updateEmployeeContactInfo() throws SQLException {

		employeeContactInfoRequest.setEmpInfo(getEmployeeContactInfo());
		employeeContactInfoRequest.setTransactionType("update");
		int[] count = { 1, 2 };
		Mockito.lenient().when(jdbcTemplate.batchUpdate(Mockito.anyString(), Mockito.anyList())).thenReturn(count);
		boolean update = employeeContactDaoImpl.updateEmployeeContactInfo(employeeContactInfoRequest);
		assertEquals(true, update);

	}
	
	@Test
	public void updateEmployeeContactInfoFalse() throws SQLException {

		employeeContactInfoRequest.setEmpInfo(getEmployeeContactInfo());
		employeeContactInfoRequest.setTransactionType("update");
		int[] count = {};
		Mockito.lenient().when(jdbcTemplate.batchUpdate(Mockito.anyString(), Mockito.anyList())).thenReturn(count);
		boolean update = employeeContactDaoImpl.updateEmployeeContactInfo(employeeContactInfoRequest);
		assertEquals(true, update);

	}

	// ----------------delete test case-------------------

	
	
	@Test
	public void deleteEmployeeContactInfo() throws SQLException {

		employeeContactInfoRequest.setEmpInfo(getEmployeeContactInfo());
		employeeContactInfoRequest.setTransactionType("delete");
		int[] count = { 1, 2 };
		Mockito.lenient().when(jdbcTemplate.batchUpdate(Mockito.anyString(), Mockito.anyList())).thenReturn(count);
		boolean delete = employeeContactDaoImpl.deleteEmployeeContactInfo(employeeContactInfoRequest);
		assertEquals(true, delete);

	}
	
	@Test
	public void deleteEmployeeContactInfoFalse() throws SQLException {

		employeeContactInfoRequest.setEmpInfo(getEmployeeContactInfo());
		employeeContactInfoRequest.setTransactionType("delete");
		int[] count = {};
		Mockito.lenient().when(jdbcTemplate.batchUpdate(Mockito.anyString(), Mockito.anyList())).thenReturn(count);
		boolean delete = employeeContactDaoImpl.deleteEmployeeContactInfo(employeeContactInfoRequest);
		assertEquals(false, delete);

	}
		
	
	
	// ----------------GetAll test case-------------------

	@Test
	public void getAllEmployeeContactInfo() throws SQLException {

		employeeContactInfoRequest.setEmpInfo(getEmployeeContactInfo());
		employeeContactInfoRequest.setTransactionType("getAll");
		int[] count = { 1, 2 };
		Mockito.lenient().when(jdbcTemplate.batchUpdate(Mockito.anyString(), Mockito.anyList())).thenReturn(count);
		List<EmployeeContactInfo> allList = employeeContactDaoImpl.getAllContacctDetails();
		assertEquals(allList, allList);

	}

	// ----------------GetById test case-------------------

	@Test
	public void getByIdEmployeeContactInfo() throws SQLException {

		employeeContactInfoRequest.setEmpInfo(getEmployeeContactInfo());
		// employeeContactInfoRequest.setTransactionType("getAll");
		int[] count = { 1, 2 };
		Mockito.lenient().when(jdbcTemplate.batchUpdate(Mockito.anyString(), Mockito.anyList())).thenReturn(count);
		List<EmployeeContactInfo> getEmpId = employeeContactDaoImpl
				.showEmployeeContactInfoByEmpId(employeeContactInfoRequest);
		assertEquals(getEmpId, getEmpId);

	}

	@Test
	public void getByIdEmployeeContactInfo1() throws SQLException {

		employeeContactInfoRequest.setEmpInfo(getEmployeeContactInfo());

		employeeContactInfoRequest.getEmpInfo().get(0).setId(1);
		int[] count = { 1, 2 };
		Mockito.lenient().when(jdbcTemplate.batchUpdate(Mockito.anyString(), Mockito.anyList())).thenReturn(count);
		List<EmployeeContactInfo> getById = employeeContactDaoImpl
				.showEmployeeContactInfoById(employeeContactInfoRequest);
		assertEquals(getById, getById);

	}

}
