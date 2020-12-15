package com.ojas.obs.dao;


import static com.ojas.obs.dao.SeparationTypeDaoImpl.SELECTSeparationTtype;
import static com.ojas.obs.dao.SeparationTypeDaoImpl.Getbyid;
import static org.junit.Assert.assertEquals;
//import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyListOf;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
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

import com.ojas.obs.main.OjasObsOnlineSeparationTypeServiceApplicationTests;
import com.ojas.obs.model.SeparationType;
import com.ojas.obs.request.SeparationTypeRequest;

@SpringBootConfiguration
public class SeparationTypeDaoImplTest extends OjasObsOnlineSeparationTypeServiceApplicationTests { 
	 
	@Mock
	DataSource datasource;

	@Mock
	JdbcTemplate jdbcTemplate;
	
	@InjectMocks
	SeparationTypeDaoImpl separationTypeDaoImpl;
	 @Mock
	 SeparationTypeDao separationTypeDao;
	 
	 int[] arr = {1}; 
	 int[] arr1 = {};
	 
	 
	 
	 
	 
	 
	 @Before
		public void beforeTest() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
			datasource = mock(DataSource.class);
			jdbcTemplate = new JdbcTemplate(datasource);
			separationTypeDaoImpl = new SeparationTypeDaoImpl();
			jdbcTemplate = mock(JdbcTemplate.class);
			setCollabarator(separationTypeDaoImpl, "jdbcTemplate", jdbcTemplate);
		}
		
		public void setCollabarator(Object object, String name, Object collabarator) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
			Field field;
				field = object.getClass().getDeclaredField(name);
				field.setAccessible(true);
				field.set(object, collabarator);
		}

		
		@Spy
		SeparationTypeRequest separationTypeRequest;

		public SeparationTypeRequest getRequest() { 
			
			SeparationTypeRequest separationTypeRequest = new SeparationTypeRequest();
			List<SeparationType> list = new ArrayList<>();
			SeparationType separationType = new SeparationType();
			separationType.setSeparationTypeId(1);
			separationType.setSeparationType("java");
			list.add(separationType);
			separationTypeRequest.setSeparationType(list);  
			separationTypeRequest.setTransactionType("save");
			return separationTypeRequest;

		}
		@Test
		public void saveseparationTest() throws SQLException {

			when(jdbcTemplate.batchUpdate(anyString(), anyListOf(Object[].class))).thenReturn(arr);
			boolean saveSeparationType = separationTypeDaoImpl.saveSeparationType(getRequest());
			assertEquals(true, saveSeparationType); 
		}
		
		@Test
		public void saveseparationNegativeTest() throws SQLException {

			when(jdbcTemplate.batchUpdate(anyString(), anyListOf(Object[].class))).thenReturn(arr1);
			boolean saveSeparationType = separationTypeDaoImpl.saveSeparationType(getRequest());
			assertEquals(false, saveSeparationType);
		}

	/*
	 * @Test public void saveseparationCatchTest() throws SQLException {
	 * 
	 * when(jdbcTemplate.batchUpdate(anyString(),
	 * anyListOf(Object[].class))).thenThrow(new RuntimeException()); boolean
	 * saveSeparationType = separationTypeDaoImpl.saveSeparationType(getRequest());
	 * assertEquals(false, saveSeparationType); }
	 */
		@Test
		public void updateseparationTest() throws SQLException {
			when(jdbcTemplate.batchUpdate(anyString(), anyListOf(Object[].class))).thenReturn(arr);
			boolean updateSeparationType = separationTypeDaoImpl.updateSeparationType(getRequest());
			assertEquals(true,updateSeparationType);
		}
		 
		@Test
		public void updateseparationNegativeTest() throws SQLException {
			when(jdbcTemplate.batchUpdate(anyString(), anyListOf(Object[].class))).thenReturn(arr1);
			boolean updateSeparationType = separationTypeDaoImpl.updateSeparationType(getRequest());
			assertEquals(false, updateSeparationType);
		}
		 
	/*
	 * @Test public void updateseparationCatchTest() throws SQLException {
	 * when(jdbcTemplate.batchUpdate(anyString(),
	 * anyListOf(Object[].class))).thenThrow(new RuntimeException());
	 * separationTypeDaoImpl.updateSeparationType(getRequest());
	 * //assertEquals(false, updateSeparationType); }
	 */
		
		@Test
		public void getAllseparationTest() throws SQLException {
			List<SeparationType> list = new ArrayList<SeparationType>();
			
			when(jdbcTemplate.query(SELECTSeparationTtype , new BeanPropertyRowMapper<>(SeparationType.class))).thenReturn(list);
			List<SeparationType> separationtypeList = separationTypeDaoImpl.getAllSeparationType();
			assertEquals(list, separationtypeList);
		}
		
		
		
		@Test
		public void getById() throws SQLException {
			 List<SeparationType> list = getRequest().getSeparationType();
			
			when(jdbcTemplate.query(Getbyid, new BeanPropertyRowMapper<>(SeparationType.class))).thenReturn(list);
			List<SeparationType> dlist = separationTypeDaoImpl.getById(getRequest());

			boolean b = dlist.isEmpty();
			
			assertEquals(true, b);
			
		}
		
	/*
	 * @Test public void deleteSeparationTypeCheck() throws SQLException { int count
	 * = 1; when(jdbcTemplate.update(anyString(), anyInt())).thenReturn(count);
	 * boolean status = separationTypeDaoImpl.deleteSeparationType(1);
	 * assertEquals(true, status); }
	 * 
	 * @Test public void deleteSeparationTypeNullCheck() throws SQLException { int
	 * count = 0; when(jdbcTemplate.update(anyString(),
	 * anyInt())).thenReturn(count); boolean status =
	 * separationTypeDaoImpl.deleteSeparationType(1); assertEquals(false, status); }
	 */



}
