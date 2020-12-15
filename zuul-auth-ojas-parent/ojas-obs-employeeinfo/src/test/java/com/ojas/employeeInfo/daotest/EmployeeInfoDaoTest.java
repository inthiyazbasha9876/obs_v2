package com.ojas.employeeInfo.daotest;

import static com.ojas.obs.constants.UserConstants.GETEMPDETAILS;
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
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ojas.obs.dao.EmployeeInfoDao;
import com.ojas.obs.daoImpl.EmployeeDaoImpl;
import com.ojas.obs.model.EmployeeInfo;
import com.ojas.obs.request.EmployeeInfoRequest;

public class EmployeeInfoDaoTest {

	@InjectMocks
	EmployeeDaoImpl empDaoImpl;
	@Mock
	EmployeeInfoDao empInfoDao;
	@Mock
	JdbcTemplate jdbcTemplate;
	@Spy
	EmployeeInfo empInfo;
	@Spy
	EmployeeInfoRequest empInfoRequest;
	@Mock
	Environment env;
	@Mock
	private PasswordEncoder pwdEncode;

	int[] count = { 1 };
	int[] noCount = {};

	@Before
	public void init() throws Exception {
		empDaoImpl = new EmployeeDaoImpl();
		jdbcTemplate = mock(JdbcTemplate.class);
		env = mock(Environment.class);
		pwdEncode = mock(PasswordEncoder.class);
		setCollaborator(empDaoImpl, "jdbcTemplate", jdbcTemplate);
		setCollaborator(empDaoImpl, "env", env);
		setCollaborator(empDaoImpl, "passwordEncode", pwdEncode);
	}

	public void setCollaborator(Object object, String name, Object service) throws Exception {
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);
	}

	public List<EmployeeInfo> getEmpInfoList() {
		List<EmployeeInfo> employeeInfos = new ArrayList<EmployeeInfo>();
		EmployeeInfo employeeInfo = new EmployeeInfo();
		employeeInfo.setFirstname("abc");
		employeeInfo.setMiddlename("def");
		employeeInfo.setLastname("ghi");
		employeeInfo.setStatus("Bench");
		employeeInfo.setGender("Male");
		employeeInfo.setDob("2019-04-09");
		//employeeInfo.setStatusDate("2019-07-15");
		employeeInfo.setEmployeeId("123");
		employeeInfos.add(employeeInfo);
		return employeeInfos;
	}

	@Test
	public void saveTest() throws SQLException {
		EmployeeInfoRequest empInfoRequest = new EmployeeInfoRequest();
		empInfoRequest.setEmployeeInfo(getEmpInfoList());
		when(env.getProperty(anyString())).thenReturn("123");
		when(pwdEncode.encode(anyString())).thenReturn("");
		when(jdbcTemplate.batchUpdate(anyString(), Mockito.anyList())).thenReturn(count);
		boolean status = empDaoImpl.saveEmployeeInfo(empInfoRequest);
		assertEquals(true, status);
	}

	@Test
	public void updateTest() throws SQLException {
		EmployeeInfoRequest empInfoRequest = new EmployeeInfoRequest();
		empInfoRequest.setEmployeeInfo(getEmpInfoList());
		when(jdbcTemplate.batchUpdate(anyString(), Mockito.anyList())).thenReturn(count);
		boolean status = empDaoImpl.updateEmployeeInfo(empInfoRequest);
		assertEquals(true, status);
	}

	@Test
	public void deleteTest() throws SQLException {
		EmployeeInfoRequest empInfoRequest = new EmployeeInfoRequest();
		empInfoRequest.setEmployeeInfo(getEmpInfoList());
		when(jdbcTemplate.batchUpdate(anyString(), Mockito.anyList())).thenReturn(count);
		boolean status = empDaoImpl.deleteEmployeeInfo(empInfoRequest);
		assertEquals(true, status);
	}

	@Test
	public void getByIdTest() throws SQLException {
		EmployeeInfoRequest empInfoRequest = new EmployeeInfoRequest();
		empInfoRequest.setEmployeeInfo(getEmpInfoList());
		when(jdbcTemplate.query("", new BeanPropertyRowMapper<>(EmployeeInfo.class))).thenReturn(getEmpInfoList());
		List<EmployeeInfo> list = empDaoImpl.getById(empInfoRequest);
		boolean b = list.isEmpty();
		assertEquals(true, b);
	}
	
	@Test
	public void getByEmpIdTest() throws SQLException {
		EmployeeInfoRequest empInfoRequest = new EmployeeInfoRequest();
		List<EmployeeInfo> empInfoList = getEmpInfoList();
		empInfoList.get(0).setId(1);
		empInfoRequest.setEmployeeInfo(empInfoList);
		when(jdbcTemplate.query("", new BeanPropertyRowMapper<>(EmployeeInfo.class))).thenReturn(getEmpInfoList());
		List<EmployeeInfo> list = empDaoImpl.getById(empInfoRequest);
		boolean b = list.isEmpty();
		assertEquals(true, b);
	}

	@Test
	public void getAllEmployeeDetailsTest() throws SQLException {
		EmployeeInfoRequest empInfoRequest = new EmployeeInfoRequest();
		empInfoRequest.setEmployeeInfo(getEmpInfoList());
		when(jdbcTemplate.query(GETEMPDETAILS, new BeanPropertyRowMapper<>(EmployeeInfo.class)))
				.thenReturn(getEmpInfoList());
		List<EmployeeInfo> list = empDaoImpl.getAllEmployeeDetails(empInfoRequest);
		boolean b = list.isEmpty();
		assertEquals(true, b);
	}
}
