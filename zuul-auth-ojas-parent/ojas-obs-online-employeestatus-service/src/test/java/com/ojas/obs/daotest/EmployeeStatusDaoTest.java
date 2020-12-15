
package com.ojas.obs.daotest;

import static org.junit.Assert.assertEquals;
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
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.ojas.obs.dao.EmployeeStatusDao;
import com.ojas.obs.daoimpl.EmployeeStatusDaoImpl;
import com.ojas.obs.model.EmployeeStatus;
import com.ojas.obs.request.EmployeeStatusRequest;

public class EmployeeStatusDaoTest {

	@InjectMocks
	EmployeeStatusDaoImpl employeeStatusDaoImpl;

	@Mock
	EmployeeStatusDao employeeStatusDao;

	@Mock
	JdbcTemplate jdbcTemplate;

	@Spy
	EmployeeStatus employeeStatus;

	@Spy
	EmployeeStatusRequest employeeStatusRequest;
	int[] count = { 1 };
	int[] noCount = {};

	@Before
	public void init() throws Exception {
		employeeStatusDaoImpl = new EmployeeStatusDaoImpl();
		jdbcTemplate = mock(JdbcTemplate.class);
		setCollaborator(employeeStatusDaoImpl, "jdbcTemplate", jdbcTemplate);
	}

	public void setCollaborator(Object object, String name, Object service) throws Exception {
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);
	}

	public List<EmployeeStatus> getEmpStatusList() {
		List<EmployeeStatus> list = new ArrayList<EmployeeStatus>();
		EmployeeStatus status = new EmployeeStatus();
		status.setStatus("Active");
		status.setId(5);
		list.add(status);
		return list;
	}

	@Test
	public void saveTest() throws SQLException {
		EmployeeStatusRequest empstatusRequest = new EmployeeStatusRequest();
		empstatusRequest.setEmployeeStatus(getEmpStatusList());
		when(jdbcTemplate.batchUpdate(anyString(), Mockito.anyList())).thenReturn(count);
		boolean status = employeeStatusDaoImpl.saveEmployeeStatus(empstatusRequest);
		assertEquals(true, status);
	}

	@Test
	public void saveNegativeTest() throws SQLException {
		EmployeeStatusRequest empstatusRequest = new EmployeeStatusRequest();
		empstatusRequest.setEmployeeStatus(getEmpStatusList());
		when(jdbcTemplate.batchUpdate(anyString(), Mockito.anyList())).thenReturn(noCount);
		boolean status = employeeStatusDaoImpl.saveEmployeeStatus(empstatusRequest);
		assertEquals(false, status);
	}

	@Test
	public void updateTest() throws SQLException {
		EmployeeStatusRequest empstatusRequest = new EmployeeStatusRequest();
		empstatusRequest.setEmployeeStatus(getEmpStatusList());
		when(jdbcTemplate.batchUpdate(anyString(), Mockito.anyList())).thenReturn(count);
		boolean status = employeeStatusDaoImpl.updateEmployeeStatus(empstatusRequest);
		assertEquals(true, status);
	}

	@Test
	public void updateNegativeTest() throws SQLException {
		EmployeeStatusRequest empstatusRequest = new EmployeeStatusRequest();
		empstatusRequest.setEmployeeStatus(getEmpStatusList());
		when(jdbcTemplate.batchUpdate(anyString(), Mockito.anyList())).thenReturn(noCount);
		boolean status = employeeStatusDaoImpl.updateEmployeeStatus(empstatusRequest);
		assertEquals(false, status);
	}

	/*
	 * @Test public void deleteTest() { EmployeeStatusRequest empstatusRequest = new
	 * EmployeeStatusRequest();
	 * empstatusRequest.setEmployeeStatus(getEmpStatusList());
	 * when(jdbcTemplate.batchUpdate(anyString(),
	 * Mockito.anyList())).thenReturn(count); boolean status =
	 * employeeStatusDaoImpl.deleteEmployeeStatus(empstatusRequest);
	 * assertEquals(true, status); }
	 * 
	 * @Test public void deleteNegativeTest() { EmployeeStatusRequest
	 * empstatusRequest = new EmployeeStatusRequest();
	 * empstatusRequest.setEmployeeStatus(getEmpStatusList());
	 * when(jdbcTemplate.batchUpdate(anyString(),
	 * Mockito.anyList())).thenReturn(noCount); boolean status =
	 * employeeStatusDaoImpl.deleteEmployeeStatus(empstatusRequest);
	 * assertEquals(false, status); }
	 */

	@Test
	public void getAllTest() throws SQLException {

		when(jdbcTemplate.query(EmployeeStatusDaoImpl.GETTOTALSTMT,
				new BeanPropertyRowMapper<EmployeeStatus>(EmployeeStatus.class))).thenReturn(getEmpStatusList());
		List<EmployeeStatus> status = employeeStatusDaoImpl.getAllStatus();
		boolean b = status.isEmpty();
		assertEquals(true, b);
	}

	@Test
	public void getByIdTest() throws SQLException {

		when(jdbcTemplate.query(EmployeeStatusDaoImpl.GETBYIDSTMT,
				new BeanPropertyRowMapper<EmployeeStatus>(EmployeeStatus.class))).thenReturn(getEmpStatusList());
		List<EmployeeStatus> status = employeeStatusDaoImpl.getById(1);
		boolean b = status.isEmpty();
		assertEquals(true, b);
	}

}
