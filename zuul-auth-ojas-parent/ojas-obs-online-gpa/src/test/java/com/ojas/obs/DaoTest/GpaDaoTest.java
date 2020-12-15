package com.ojas.obs.DaoTest;

import static com.ojas.obs.constants.GpaServiceConstants.GETBYID;
import static com.ojas.obs.constants.GpaServiceConstants.GETGPACOUNT;
import static com.ojas.obs.constants.GpaServiceConstants.GETGPAPLAN;
import static org.hamcrest.CoreMatchers.both;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyListOf;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

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
import org.springframework.stereotype.Controller;


import com.ojas.obs.config.OjasObsOnlineGpaServiceBootApplication;
import com.ojas.obs.dao.GpaPlanDaoImpl;
import com.ojas.obs.model.GpaPlan;
import com.ojas.obs.request.GpaRequest;
import com.ojas.obs.response.GpaResponse;

@SpringBootConfiguration
public class GpaDaoTest extends OjasObsOnlineGpaServiceBootApplication{

	@InjectMocks
	private GpaPlanDaoImpl gpaPlanDaoImpl;
	
	@Mock
	DataSource datasource;

	@Mock
	JdbcTemplate jdbcTemplate;
	
	Connection connection = mock(Connection.class);
	
	@Spy
	Error err= new Error();	

	@Spy
	ResponseEntity<Object> responseEntity = new ResponseEntity<>(err, HttpStatus.UNPROCESSABLE_ENTITY);

	@Spy
	ResponseEntity<Object> successEntity = new ResponseEntity<>(HttpStatus.OK);

	@Spy
	List<GpaPlan> listGpaRequest = new ArrayList<GpaPlan>();
	
	@Spy
	GpaResponse gpaResponse= new GpaResponse();

	@Spy
	List<GpaResponse> resp = new ArrayList<GpaResponse>();

	@Spy
	GpaRequest gpaRequest;
	
	int[] arr=new int[4];
	int[] arr1=new int[0];
	
	@Before
	public void beforeTest() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		datasource = mock(DataSource.class);
		jdbcTemplate = new JdbcTemplate(datasource);
		gpaPlanDaoImpl = new GpaPlanDaoImpl();
		jdbcTemplate = mock(JdbcTemplate.class);
		setCollabarator(gpaPlanDaoImpl, "jdbcTemplaeObject", jdbcTemplate);
	}
	
	public void setCollabarator(Object object, String name, Object collabarator) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field;
			field = object.getClass().getDeclaredField(name);
			field.setAccessible(true);
			field.set(object, collabarator);
	}
	
	

	
	@Test
	public void saveGpaPlanTst() throws SQLException {
		GpaRequest gpaRequest = new GpaRequest();
		GpaPlan gpaPlan = new GpaPlan();
		
		gpaPlan.setGpaPlanType("djf");
		gpaPlan.setGpaPremium(125.2);
		gpaPlan.setTotalPremium(103.32);
	
		
		listGpaRequest.add(gpaPlan);
		gpaRequest.setGpaPlan(listGpaRequest);
		gpaRequest.setTransactionType("save");

	when(jdbcTemplate.batchUpdate(anyString(), anyListOf(Object[].class))).thenReturn(arr);
	boolean saveGpa = gpaPlanDaoImpl.saveGpaPlan(gpaRequest);
	assertEquals(true,saveGpa);
	
	}
	
	@Test
	public void saveGpaPlanNeg() throws SQLException {
		GpaRequest gpaRequest = new GpaRequest();
		GpaPlan gpaPlan = new GpaPlan();
		
		gpaPlan.setGpaPlanType("djf");
		gpaPlan.setGpaPremium(125.2);
		gpaPlan.setTotalPremium(103.32);
	
		
		listGpaRequest.add(gpaPlan);
		gpaRequest.setGpaPlan(listGpaRequest);
		gpaRequest.setTransactionType("save");
		when(jdbcTemplate.batchUpdate(anyString(),anyListOf(Object[].class))).thenReturn(arr1);
		boolean saveGpa = gpaPlanDaoImpl.saveGpaPlan(gpaRequest);
		
		assertEquals(false, saveGpa);
		
	}
	/*
	 * @Test public void saveGpaPlanCatch() throws SQLException {
	 * when(jdbcTemplate.batchUpdate(anyString(),anyListOf(Object[].class))).
	 * thenThrow(new RuntimeException());
	 * 
	 * boolean saveGpa = gpaPlanDaoImpl.saveGpaPlan(gpaRequest());
	 * assertEquals(false,saveGpa);
	 * 
	 * }
	 */

	@Test
	public void updateGpaPlanTest() throws SQLException {
		GpaRequest gpaRequest = new GpaRequest();
		GpaPlan gpaPlan = new GpaPlan();
		gpaPlan.setId(1);
		gpaPlan.setGpaPlanType("djf");
		gpaPlan.setGpaPremium(125.2);
		gpaPlan.setTotalPremium(103.32);
	
		
		listGpaRequest.add(gpaPlan);
		gpaRequest.setGpaPlan(listGpaRequest);
		gpaRequest.setTransactionType("update");
		when(jdbcTemplate.batchUpdate(anyString(), anyListOf(Object[].class))).thenReturn(arr);
		boolean updategpa = gpaPlanDaoImpl.updateGpa(gpaRequest);
		assertEquals(true,updategpa);
	}
	
	@Test
	public void updateGpaPlanNeg() throws SQLException {
		GpaRequest gpaRequest = new GpaRequest();
		GpaPlan gpaPlan = new GpaPlan();
		gpaPlan.setId(1);
		gpaPlan.setGpaPlanType("djf");
		gpaPlan.setGpaPremium(125.2);
		gpaPlan.setTotalPremium(103.32);
	
		
		listGpaRequest.add(gpaPlan);
		gpaRequest.setGpaPlan(listGpaRequest);
		gpaRequest.setTransactionType("update");

		when(jdbcTemplate.batchUpdate(anyString(), anyListOf(Object[].class))).thenReturn(arr1);
		boolean updategpa = gpaPlanDaoImpl.updateGpa(gpaRequest);
		assertEquals(false,updategpa);
	}
	/*
	 * @Test public void updateGpaPlanCatch() throws SQLException {
	 * 
	 * GpaPlan plan = new GpaPlan(); gpaRequest = new GpaRequest(); plan.setId(1);
	 * listGpaRequest.add(plan); gpaRequest.setGpaPlan(listGpaRequest);
	 * 
	 * when(jdbcTemplate.batchUpdate(anyString(),
	 * anyListOf(Object[].class))).thenThrow(new RuntimeException());
	 * when(jdbcTemplate.getDataSource()).thenReturn(datasource);
	 * when(jdbcTemplate.getDataSource().getConnection()).thenReturn(connection);
	 * boolean updategpa = gpaPlanDaoImpl.updateGpa(gpaRequest);
	 * assertEquals(false,updategpa); }
	 */
	
	@Test
	public void getAllGpaPlantest() throws SQLException {
		GpaRequest gpaRequest = new GpaRequest();
		
		List<GpaPlan> list = new ArrayList<GpaPlan>();
		
		when(jdbcTemplate.query(GETGPAPLAN, new BeanPropertyRowMapper<>(GpaPlan.class))).thenReturn(list);
		List<GpaPlan> gpaList = gpaPlanDaoImpl.getAllGpaDetails(gpaRequest);
		assertEquals(list, gpaList);
	}
	@Test
	public void getAllGpaDetailsCountTest() throws SQLException {
		gpaRequest = new GpaRequest();
		when(jdbcTemplate.queryForObject(GETGPACOUNT, Integer.class)).thenReturn(1);
		int gpaList = gpaPlanDaoImpl.getAllGpaDetailsCount();
		assertEquals(1,gpaList );
		
		
	}
	
	
	@Test
	public void getAllGpaDetailsPagination() throws SQLException {
		List<GpaPlan> mockList = new ArrayList<>();
		GpaPlan mockGpa = mock(GpaPlan.class);
		mockList.add(mockGpa);
		List<GpaPlan> pageRecords = gpaPlanDaoImpl.getPageRecords(mockList, 1, 2);
		assertEquals(mockList, pageRecords);
	}

 	@Test
 	public void getByIdTest() throws SQLException {
 		List<GpaPlan> mockList = new ArrayList<>();
 		GpaPlan plan = new GpaPlan();
 		
 		gpaRequest = new GpaRequest();
 		plan.setId(1);
 		listGpaRequest.add(plan);
 		gpaRequest.setGpaPlan(listGpaRequest);
 		
 		when(jdbcTemplate.query(GETBYID, new BeanPropertyRowMapper<>(GpaPlan.class))).thenReturn(mockList);
 		when(jdbcTemplate.getDataSource()).thenReturn(datasource);
 		when(jdbcTemplate.getDataSource().getConnection()).thenReturn(connection);
 		List<GpaPlan> gpaList = gpaPlanDaoImpl.getById(gpaRequest);
 		assertEquals(mockList, gpaList);
 	}




}