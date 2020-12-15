package com.ojas.obs.title.daoTest;

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

import com.ojas.obs.dao.TitleDaoImpl;
import com.ojas.obs.error.ErrorResponse;
import com.ojas.obs.model.Model;
import com.ojas.request.Request;
import com.ojas.response.Response;



public class DaoTest {

	@Mock
	JdbcTemplate jdbcTemplate;

	@InjectMocks
	private TitleDaoImpl titleDaoImpl;

	@Spy
	ErrorResponse errorResponse = new ErrorResponse();

	@Spy
	Request request;

	@Spy
	Response response;

	@Spy
	Model title;

	@Spy
	List<Model> List;

	@Spy
	List<Model> titleList = new ArrayList<Model>();

	int[] count = { 1 };
	int[] zeroCount = {};

	@Before
	public void init() {
		titleDaoImpl = new TitleDaoImpl();
		titleList.add(title);
		jdbcTemplate = mock(JdbcTemplate.class);
		setCollaborator(titleDaoImpl, "jdbcTemplate", jdbcTemplate);
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
	public Request request() {
		request = new Request();
		Model title = new Model();
		title.setId(1);
		title.setEmployeeId("wser");
		title.setFlag(true);
		title.setRole(23);
		title.setTitle("abcd");
		title.setCreatedby("abds");
		title.setCreatedby("mnb");
		title.setUpdatedby("323");
		title.setUpdateddate(new Timestamp(21101998));
		title.setCreateddate(new Timestamp(21101998));
		Model title2 = new Model();
		title2.setId(1);
		title2.setEmployeeId("wser");
		title2.setFlag(true);
		title2.setRole(23);
		title2.setTitle("abcd");
		title2.setCreatedby("abds");
		title2.setCreatedby("mnb");
		title2.setUpdatedby("323");
		title2.setUpdateddate(new Timestamp(21101998));
		title2.setCreateddate(new Timestamp(21101998));
		List<Model> List = new ArrayList<>();
		List.add(title2);
		List.add(title);
		request.setModel(List);
		return request;
		
	}
	
	@Test
	public void saveSuccess() throws SQLException {
		request = request();

		when(jdbcTemplate.batchUpdate(anyString(), Mockito.anyList())).thenReturn(count);

		boolean save = titleDaoImpl.saveTitle(request);
		assertNotEquals(true, save);
	}

	@Test
	public void saveFail() throws SQLException {
		request = request();
		when(jdbcTemplate.batchUpdate(anyString(), Mockito.anyList())).thenReturn(zeroCount);

		boolean save = titleDaoImpl.saveTitle(request);
		assertEquals(false, save);
	}
	@Test
	public void updateSuccess() throws SQLException {
		request = request();
		when(jdbcTemplate.batchUpdate(anyString(), Mockito.anyList())).thenReturn(count);

		boolean update = titleDaoImpl.updateTitle(request);
		assertNotEquals(true, update);
	}

	@Test
	public void updateFail() throws SQLException {
		request = request();
		when(jdbcTemplate.batchUpdate(anyString(), Mockito.anyList())).thenReturn(zeroCount);
		boolean update = titleDaoImpl.updateTitle(request);
		assertEquals(false, update);
	}
	
	@Test
	public void deleteSuccess() throws SQLException {
		request = request();
		when(jdbcTemplate.batchUpdate(anyString(), Mockito.anyList())).thenReturn(count);

		boolean delete = titleDaoImpl.deleteEmployeeRecord(1);
		assertNotEquals(true, delete);
	}

	@Test
	public void deleteFail() throws SQLException {
		request = request();
		when(jdbcTemplate.batchUpdate(anyString(), Mockito.anyList())).thenReturn(zeroCount);
		boolean delete = titleDaoImpl.deleteEmployeeRecord(1);
		assertEquals(false, delete);
	}
	
	@Test
	public void getAll() throws SQLException {

		request = request();
		String GETALLRECORDS = "select * from obs_title";
		when(jdbcTemplate.query(GETALLRECORDS, new BeanPropertyRowMapper<Model>(Model.class)))
				.thenReturn(List);

		List<Model> getAll = titleDaoImpl.getAllTitle(request);
		boolean status = getAll.isEmpty();

		assertEquals(true, status);
	}

	@Test
	public void getById() throws SQLException {

		request = request();
		String GETALLRECORDS = "select * from obs_title where id =?";
		when(jdbcTemplate.query(GETALLRECORDS, new BeanPropertyRowMapper<Model>(Model.class)))
				.thenReturn(List);

		List<Model> getAll = titleDaoImpl.getById(request);
		boolean status = getAll.isEmpty();

		assertEquals(true, status);
	}
	@Test
	public void getByEmpId() throws SQLException {

		request = request();
		String GETALLRECORDS = "select * from obs_title where employeeId =?";
		when(jdbcTemplate.query(GETALLRECORDS, new BeanPropertyRowMapper<Model>(Model.class)))
				.thenReturn(List);

		List<Model> getAll = titleDaoImpl.getByEmpId(request);
		boolean status = getAll.isEmpty();

		assertEquals(true, status);
	}
	
}
