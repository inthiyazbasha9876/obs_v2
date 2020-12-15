package com.ojas.obs.daoTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.ojas.obs.dao.EmployeeBUDaoImpl;
import com.ojas.obs.error.ErrorResponse;
import com.ojas.obs.model.EmployeeBUDetails;
import com.ojas.obs.request.EmployeeBUDetailsRequest;
import com.ojas.obs.response.EmployeeBUDeatailsResponse;

public class DaoTest {

	@Mock
	JdbcTemplate jdbcTemplate;

	@InjectMocks
	private EmployeeBUDaoImpl buDaoImpl;

	@Spy
	ErrorResponse errorResponse = new ErrorResponse();

	@Spy
	EmployeeBUDetailsRequest buRequest;

	@Spy
	EmployeeBUDeatailsResponse buResponse;

	@Spy
	EmployeeBUDetails bu;

	@Spy
	List<EmployeeBUDetails> buList;

	@Spy
	List<EmployeeBUDetails> busList = new ArrayList<EmployeeBUDetails>();

	int[] count = { 1 };
	int[] zeroCount = {};

	@Before
	public void init() {
		buDaoImpl = new EmployeeBUDaoImpl();
		busList.add(bu);
		jdbcTemplate = mock(JdbcTemplate.class);
		setCollaborator(buDaoImpl, "jdbcTemplate", jdbcTemplate);
	}

	private void setCollaborator(Object object, String name, Object service) {

		Field field;
		try {
			field = object.getClass().getDeclaredField(name);
			field.setAccessible(true);

			field.set(object, service);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public EmployeeBUDetailsRequest buRequest() {
		buRequest = new EmployeeBUDetailsRequest();
		EmployeeBUDetails bu1 = new EmployeeBUDetails();
		bu1.setId(1);
		bu1.setStatus("admin");
		bu1.setEmployeeId("abc");
		bu1.setFlag(true);
		bu1.setSbu(3);
		bu1.setCreatedby("mnb");
		bu1.setCreateddate(new Timestamp(21101998));
		EmployeeBUDetails bu2 = new EmployeeBUDetails();
		bu1.setId(2);
		bu1.setStatus("admihn");
		bu1.setEmployeeId("abggc");
		bu1.setFlag(true);
		bu1.setSbu(2);
		bu1.setCreatedby("mnggb");
		bu1.setCreateddate(new Timestamp(19101998));
		List<EmployeeBUDetails> buList = new ArrayList<>();
		buList.add(bu1);
		buList.add(bu2);
		buRequest.setEmployeeBUDeatils(buList);
		return buRequest;
	}

	@Test
	public void saveSuccess() throws SQLException {
		buRequest = buRequest();

		when(jdbcTemplate.batchUpdate(anyString(), Mockito.anyList())).thenReturn(count);

		boolean save = buDaoImpl.saveEmployeebu(buRequest);
		assertNotEquals(true, save);
	}

	@Test
	public void saveFail() throws SQLException {
		buRequest = buRequest();
		when(jdbcTemplate.batchUpdate(anyString(), Mockito.anyList())).thenReturn(zeroCount);

		boolean save = buDaoImpl.saveEmployeebu(buRequest);
		assertEquals(false, save);
	}

	@Test
	public void updateSuccess() throws SQLException {
		buRequest = buRequest();
		when(jdbcTemplate.batchUpdate(anyString(), Mockito.anyList())).thenReturn(count);

		boolean update = buDaoImpl.updateEmployeebu(buRequest);
		assertNotEquals(true, update);
	}

	@Test
	public void deleteSuccess() throws SQLException {
		buRequest = buRequest();
		when(jdbcTemplate.batchUpdate(anyString(), Mockito.anyList())).thenReturn(count);

		boolean delete = buDaoImpl.deleteEmployeeRecord(1);
		assertNotEquals(true, delete);
	}
	@Test
	public void deleteFail() throws SQLException {
		buRequest = buRequest();
		when(jdbcTemplate.batchUpdate(anyString(), Mockito.anyList())).thenReturn(zeroCount);

		boolean delete = buDaoImpl.deleteEmployeeRecord(1);
		assertEquals(false, delete);
	}

	@Test
	public void getAll() throws SQLException {

		buRequest = buRequest();
		String GETALLRECORDS = "select * from obs_budetails";
		when(jdbcTemplate.query(GETALLRECORDS, new BeanPropertyRowMapper<EmployeeBUDetails>(EmployeeBUDetails.class)))
				.thenReturn(buList);

		List<EmployeeBUDetails> getAll = buDaoImpl.getAllEmployeebu(buRequest);
		boolean status = getAll.isEmpty();

		assertEquals(true, status);
	}

	@Test
	public void getById() throws SQLException {

		buRequest = buRequest();
		String GETALLRECORDS = "select * from obs_budetails where id =?";
		when(jdbcTemplate.query(GETALLRECORDS, new BeanPropertyRowMapper<EmployeeBUDetails>(EmployeeBUDetails.class)))
				.thenReturn(buList);

		List<EmployeeBUDetails> getAll = buDaoImpl.getById(buRequest);
		boolean status = getAll.isEmpty();

		assertEquals(true, status);
	}

	@Test
	public void getByEmpId() throws SQLException {

		buRequest = buRequest();
		String GETALLRECORDS = "select * from obs_budetails where employeeId =?";
		when(jdbcTemplate.query(GETALLRECORDS, new BeanPropertyRowMapper<EmployeeBUDetails>(EmployeeBUDetails.class)))
				.thenReturn(buList);

		List<EmployeeBUDetails> getAll = buDaoImpl.getByEmpId(buRequest);
		boolean status = getAll.isEmpty();

		assertEquals(true, status);
	}
}
