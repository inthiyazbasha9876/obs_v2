package com.ojas.obs.dao;

//import static com.ojas.obs.dao.DesignationDaoImpl.DELETEDesignation;
import static com.ojas.obs.dao.DesignationDaoImpl.SELECTDesignation;
import static com.ojas.obs.dao.DesignationDaoImpl.DESIGNATIONCOUNTDesignation;
import static com.ojas.obs.dao.DesignationDaoImpl.getbyid;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyListOf;
import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
//import static org.springframework.test.web.client.match.MockRestRequestMatchers.anything;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.Spy;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.ojas.main.OjasObsDesignationApplicationTests;
import com.ojas.obs.model.Designation;
import com.ojas.obs.request.DesignationRequest;

@SpringBootConfiguration
public class DesignationDaoTest extends OjasObsDesignationApplicationTests {

	@Mock
	DataSource datasource;

	@Mock
	JdbcTemplate jdbcTemplate;

	@InjectMocks
	DesignationDaoImpl designationDaoImpl;

	@Mock
	DesignationDao designationDao;
	//@Mock
	//Connection conn;
	Connection conn = mock(Connection.class);

	// int[] arr = new int[4];
	int[] arr = { 1 };
	int[] arr1 = {};
	

	@Before
	public void setup() {

		designationDaoImpl = new DesignationDaoImpl();
		datasource = mock(DataSource.class);
		//Connection conn = mock(Connection.class);
		jdbcTemplate = new JdbcTemplate(datasource);

		jdbcTemplate = mock(JdbcTemplate.class);
		setCollabarator(designationDaoImpl, "jdbcTemplate", jdbcTemplate);
	}

	public void setCollabarator(Object object, String name, Object collabarator) {
		Field field;
		try {
			field = object.getClass().getDeclaredField(name);
			field.setAccessible(true);
			field.set(object, collabarator);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Spy
	DesignationRequest designationRequest;

	public DesignationRequest getRequest() {

		designationRequest = new DesignationRequest();
		List<Designation> list = new ArrayList<>();
		Designation designation = new Designation();
		designation.setId(1);
		designation.setDesignation("java");
		list.add(designation);
		designationRequest.setDesignation(list);
		designationRequest.setTransactionType("save");
		return designationRequest;

	}

	
	@Test
	public void saveDesignationTest() throws SQLException {

		when(jdbcTemplate.batchUpdate(anyString(), anyListOf(Object[].class))).thenReturn(arr);
		when(jdbcTemplate.getDataSource()).thenReturn(datasource);
		when(jdbcTemplate.getDataSource().getConnection()).thenReturn(conn);
		boolean saveDesignation = designationDaoImpl.saveDesignation(getRequest());
		assertEquals(true, saveDesignation);
		
	}

	@Test
	public void saveDesignationNegativeTest() throws SQLException {

		when(jdbcTemplate.batchUpdate(anyString(), anyListOf(Object[].class))).thenReturn(arr1);
		when(jdbcTemplate.getDataSource()).thenReturn(datasource);
		when(jdbcTemplate.getDataSource().getConnection()).thenReturn(conn);
		boolean saveDesignation = designationDaoImpl.saveDesignation(getRequest());
		assertEquals(false, saveDesignation);
	}

	/*
	 * @Test public void saveDesignationCatchTest() throws SQLException {
	 * 
	 * when(jdbcTemplate.batchUpdate(anyString(),
	 * anyListOf(Object[].class))).thenThrow(new RuntimeException()); boolean
	 * saveDesignation = designationDaoImpl.saveDesignation(getRequest());
	 * assertEquals(false, saveDesignation); }
	 */

	@Test
	public void updateDesignationTest() throws SQLException {
		when(jdbcTemplate.batchUpdate(anyString(), anyListOf(Object[].class))).thenReturn(arr);
		when(jdbcTemplate.getDataSource()).thenReturn(datasource);
		when(jdbcTemplate.getDataSource().getConnection()).thenReturn(conn);
		boolean updatedesignation = designationDaoImpl.updateDesignation(getRequest());
		assertEquals(true, updatedesignation);
	}

	@Test
	public void updateDesignationNegativeTest() throws SQLException {
		when(jdbcTemplate.batchUpdate(anyString(), anyListOf(Object[].class))).thenReturn(arr1);
		when(jdbcTemplate.getDataSource()).thenReturn(datasource);
		when(jdbcTemplate.getDataSource().getConnection()).thenReturn(conn);
		boolean updatedesignation = designationDaoImpl.updateDesignation(getRequest());
		assertEquals(false, updatedesignation);
	}

	@Test
	public void updateDesignatiotCatchTest() throws SQLException {
		when(jdbcTemplate.batchUpdate(anyString(), anyListOf(Object[].class))).thenThrow(new RuntimeException());
		when(jdbcTemplate.getDataSource()).thenReturn(datasource);
		when(jdbcTemplate.getDataSource().getConnection()).thenReturn(conn);
		//designationDaoImpl.updateDesignation(getRequest());
		// assertEquals(false, updateSeparationType);
	}

	/*
	 * @Test public void deleteDesignationCheck() throws SQLException { int count =
	 * 1; when(jdbcTemplate.update(anyString(), anyInt())).thenReturn(count);
	 * boolean status = designationDaoImpl.deleteDesignation(1); assertEquals(true,
	 * status); }
	 * 
	 * @Test public void deleteDesignationNullCheck() throws SQLException { int
	 * count = 0; when(jdbcTemplate.update(anyString(),
	 * anyInt())).thenReturn(count); boolean status =
	 * designationDaoImpl.deleteDesignation(1); assertEquals(false, status); }
	 */
	@Test
	public void getAllDesignationstest() throws SQLException {
		List<Designation> list = new ArrayList<Designation>();

		when(jdbcTemplate.query(SELECTDesignation, new BeanPropertyRowMapper<>(Designation.class))).thenReturn(list);
		when(jdbcTemplate.getDataSource()).thenReturn(datasource);
		when(jdbcTemplate.getDataSource().getConnection()).thenReturn(conn);
		List<Designation> designationList = designationDaoImpl.getAll(getRequest());
		assertEquals(list, designationList);
	}

	@Test
	public void getAllcounterDesignationstest() throws SQLException {
		// List<Designation> list = new ArrayList<Designation>();
		int count1 = 0;
		when(jdbcTemplate.queryForObject(DESIGNATIONCOUNTDesignation, Integer.class)).thenReturn(count1);
		when(jdbcTemplate.getDataSource()).thenReturn(datasource);
		when(jdbcTemplate.getDataSource().getConnection()).thenReturn(conn);
		int count = designationDaoImpl.getAllDesignationCount();
		assertEquals(count1, count);
	}

	@Test
	public void getById() throws SQLException {
		 List<Designation> list = getRequest().getDesignation(); //List<Designation>
		/* insuranceList =designationRequest.getDesignation(); Designation designation =
		 * new Designation(); list.add(designation);
		 */
		//when(designationRequest.getDesignation()).thenReturn(list); 
		when(jdbcTemplate.query(getbyid, new BeanPropertyRowMapper<>(Designation.class))).thenReturn(list);
		when(jdbcTemplate.getDataSource()).thenReturn(datasource);
		when(jdbcTemplate.getDataSource().getConnection()).thenReturn(conn);
		List<Designation> dlist = designationDaoImpl.getById(getRequest());

		boolean b = dlist.isEmpty();
		
		assertEquals(true, b);
		
	}

}

/*
 * @Test public void test() {
 * 
 * }
 */
