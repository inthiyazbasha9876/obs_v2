package com.ojas.obs.dao;

import static com.ojas.obs.constants.UserConstants.COUNTRECORDS;
import static com.ojas.obs.constants.UserConstants.DELETEEDUCATION;
import static com.ojas.obs.constants.UserConstants.TOTALRECORDS;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyListOf;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.ojas.obs.daoimpl.EmployeeEducationDaoImpl;
import com.ojas.obs.model.EmployeeEducation;
import com.ojas.obs.modelrequest.EmployeeEducationRequest;
import com.ojas.obs.modelresponse.EmployeeEducationResponse;

@SpringBootConfiguration
public class EmployeeEducationDaoTest {

	@InjectMocks
	private EmployeeEducationDaoImpl employeeEducationDaoImpl;

	@Mock
	JdbcTemplate jdbcTemplate;

	@Spy
	Error err = new Error();

	@Spy
	ResponseEntity<Object> responseEntity = new ResponseEntity<>(err, HttpStatus.UNPROCESSABLE_ENTITY);

	@Spy
	ResponseEntity<Object> successEntity = new ResponseEntity<>(HttpStatus.OK);

	@Spy
	EmployeeEducationResponse employeeEducationResponse = new EmployeeEducationResponse();

	@Spy
	List<EmployeeEducationResponse> listResponse = new ArrayList<EmployeeEducationResponse>();

	@Spy
	EmployeeEducationRequest employeeEducationRequest = new EmployeeEducationRequest();

	@Spy
	List<EmployeeEducation> listReq = new ArrayList<EmployeeEducation>();

	int[] arr = new int[4];
	int[] arr1 = new int[0];

	@Before
	public void beforeTest()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		employeeEducationDaoImpl = new EmployeeEducationDaoImpl();
		jdbcTemplate = mock(JdbcTemplate.class);
		setCollabarator(employeeEducationDaoImpl, "jdbcTemplate", jdbcTemplate);
	}

	public void setCollabarator(Object object, String name, Object collabarator)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, collabarator);
	}

	/*
	 * public EmployeeEducationRequest employeeEducationRequest() {
	 * 
	 * EmployeeEducation employeeEducation = new EmployeeEducation();
	 * 
	 * employeeEducation.setId(1); employeeEducation.setEducationType("BTech");
	 * 
	 * listReq.add(employeeEducation);
	 * 
	 * employeeEducationRequest = new EmployeeEducationRequest();
	 * employeeEducationRequest.setTransactionType("save");
	 * employeeEducationRequest.setListEmployeeEducations(listReq); return
	 * employeeEducationRequest;
	 * 
	 * }
	 */

	@SuppressWarnings("deprecation")
	@Test
	public void saveDetails() throws SQLException {
		EmployeeEducation employeeEducation = new EmployeeEducation();
		employeeEducation.setId(1);
		employeeEducation.setEducationType("BTech");

		listReq.add(employeeEducation);

		employeeEducationRequest = new EmployeeEducationRequest();
		employeeEducationRequest.setTransactionType("save");
		employeeEducationRequest.setListEmployeeEducations(listReq);
		when(jdbcTemplate.batchUpdate(anyString(), anyListOf(Object[].class))).thenReturn(arr);
		boolean b = employeeEducationDaoImpl.saveEmployeeEducation(employeeEducationRequest);
		assertEquals(true, b);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void saveFalseDetails() throws SQLException {
		EmployeeEducation employeeEducation = new EmployeeEducation();
		employeeEducation.setId(1);
		employeeEducation.setEducationType("BTech");

		listReq.add(employeeEducation);

		employeeEducationRequest = new EmployeeEducationRequest();
		employeeEducationRequest.setTransactionType("save");
		employeeEducationRequest.setListEmployeeEducations(listReq);
		when(jdbcTemplate.batchUpdate(anyString(), anyListOf(Object[].class))).thenReturn(arr1);
		boolean b = employeeEducationDaoImpl.saveEmployeeEducation(employeeEducationRequest);
		assertEquals(false, b);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void UpdateTrueDetails() throws SQLException {
		EmployeeEducation employeeEducation = new EmployeeEducation();
		employeeEducation.setId(1);
		employeeEducation.setEducationType("BTech");

		listReq.add(employeeEducation);

		employeeEducationRequest = new EmployeeEducationRequest();
		employeeEducationRequest.setTransactionType("update");
		employeeEducationRequest.setListEmployeeEducations(listReq);
		when(jdbcTemplate.batchUpdate(anyString(), anyListOf(Object[].class))).thenReturn(arr);
		boolean b = employeeEducationDaoImpl.updateEmployeeEducation(employeeEducationRequest);
		assertEquals(true, b);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void UpdateFalseDetails() throws SQLException {
		EmployeeEducation employeeEducation = new EmployeeEducation();
		employeeEducation.setId(1);
		employeeEducation.setEducationType("BTech");

		listReq.add(employeeEducation);

		employeeEducationRequest = new EmployeeEducationRequest();
		employeeEducationRequest.setTransactionType("update");
		employeeEducationRequest.setListEmployeeEducations(listReq);
		when(jdbcTemplate.batchUpdate(anyString(), anyListOf(Object[].class))).thenReturn(arr1);
		boolean b = employeeEducationDaoImpl.updateEmployeeEducation(employeeEducationRequest);
		assertEquals(false, b);
	}

	@Test
	public void deleteTrueDetails() throws SQLException {
		int a = 1;
		when(jdbcTemplate.update(DELETEEDUCATION, a)).thenReturn(1);
		boolean b = employeeEducationDaoImpl.deleteEmployeeEducation(a);
		assertEquals(true, b);
	}

	@Test
	public void deleteFalseDetails() throws SQLException {
		int a = 1;
		when(jdbcTemplate.update(DELETEEDUCATION, a)).thenReturn(0);
		boolean b = employeeEducationDaoImpl.deleteEmployeeEducation(a);
		assertEquals(false, b);
	}

	@Test
	public void getByIdTrueDetails() throws SQLException {
		EmployeeEducation employeeEducation = new EmployeeEducation();
		employeeEducation.setId(1);
		employeeEducation.setEducationType("BTech");

		listReq.add(employeeEducation);

		List<EmployeeEducation> list = new ArrayList<EmployeeEducation>();
		employeeEducationRequest = new EmployeeEducationRequest();
		employeeEducationRequest.setTransactionType("getall");
		employeeEducationRequest.setListEmployeeEducations(listReq);
		when(jdbcTemplate.query("", new BeanPropertyRowMapper<>(EmployeeEducation.class))).thenReturn(listReq);
		List<EmployeeEducation> b = employeeEducationDaoImpl.getAllEmployeeEducation(employeeEducationRequest);
		assertEquals(list, b);
	}

	@Test
	public void getallTrueDetails() throws SQLException {
		EmployeeEducation employeeEducation = new EmployeeEducation();
		// employeeEducation.setId(1);
		employeeEducation.setEducationType("BTech");

		listReq.add(employeeEducation);

		List<EmployeeEducation> list = new ArrayList<EmployeeEducation>();
		employeeEducationRequest = new EmployeeEducationRequest();
		employeeEducationRequest.setTransactionType("getall");
		employeeEducationRequest.setListEmployeeEducations(listReq);
		when(jdbcTemplate.query(TOTALRECORDS, new BeanPropertyRowMapper<EmployeeEducation>(EmployeeEducation.class)))
				.thenReturn(listReq);
		List<EmployeeEducation> b = employeeEducationDaoImpl.getAllEmployeeEducation(employeeEducationRequest);
		assertEquals(list, b);
	}

	@Test
	public void getCountDetails() throws SQLException {
		EmployeeEducation employeeEducation = new EmployeeEducation();
		// employeeEducation.setId(1);
		employeeEducation.setEducationType("BTech");

		listReq.add(employeeEducation);

		//List<EmployeeEducation> list = new ArrayList<EmployeeEducation>();
		employeeEducationRequest = new EmployeeEducationRequest();
		employeeEducationRequest.setTransactionType("getall");
		employeeEducationRequest.setListEmployeeEducations(listReq);
		when(jdbcTemplate.queryForObject(COUNTRECORDS, Integer.class)).thenReturn(1);
		int b = employeeEducationDaoImpl.getAllRecordsCount();
		assertEquals(1, b);
	}
}
