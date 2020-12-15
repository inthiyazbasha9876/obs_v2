package com.ojas.obs.daoTest;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static com.ojas.obs.constants.DependentDetailsContants.GETALLDEPENDENTDETAILS;
import static com.ojas.obs.constants.DependentDetailsContants.GETDEPENDENTDETAILSBYID;
import static com.ojas.obs.constants.DependentDetailsContants.GETID;
import static com.ojas.obs.constants.DependentDetailsContants.UPDATEDEPENDENTDETAILS;


import java.lang.reflect.Field;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.jdbc.core.JdbcTemplate;


import static org.mockito.ArgumentMatchers.anyListOf;
import static org.mockito.ArgumentMatchers.anyString;

import com.ojas.obs.daoImpl.DependentDetailsDaoImpl;
import com.ojas.obs.model.DependentDetails;
import com.ojas.obs.request.DependentDetailsRequest;
import com.ojas.obs.response.DependentDetailsResponse;
import com.ojas.obs.service.RowMap;


@RunWith(MockitoJUnitRunner.Silent.class)
public class DaoTest {
	
	@InjectMocks
	private DependentDetailsDaoImpl dependentDetailsDaoImpl;
	
	
	@Mock
	JdbcTemplate jdbcTemplate;
	@Spy
	Error err = new Error();

	@Spy
	ResponseEntity<Object> responseEntity = new ResponseEntity<>(err, HttpStatus.UNPROCESSABLE_ENTITY);

	@Spy
	ResponseEntity<Object> successEntity = new ResponseEntity<>(HttpStatus.OK);

	@Spy
	DependentDetailsRequest dependentDetailsRequest;
	
	@Spy
	List<DependentDetails> listDeptDetRequest = new ArrayList<DependentDetails>();
	
	@Spy
	DependentDetailsResponse dependentDetailsResponse = new DependentDetailsResponse();
	
	@Spy
	List<DependentDetails> list= new ArrayList<DependentDetails>();
	
	
	@Before
	public void beforeTest() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		dependentDetailsDaoImpl = new DependentDetailsDaoImpl();
		jdbcTemplate = mock(JdbcTemplate.class);
		setCollabarator(dependentDetailsDaoImpl, "jdbcTemplate", jdbcTemplate);
	}

	public void setCollabarator(Object object, String name, Object collabarator) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field;
			field = object.getClass().getDeclaredField(name);
			field.setAccessible(true);
			field.set(object, collabarator);
	}
	
	public DependentDetailsRequest dependentDetailsRequest() {

		DependentDetails depDetails = new DependentDetails();
		Date date = new Date();
		depDetails.setId(1);
		depDetails.setDependent_Name("fjdh");
		depDetails.setRelation("fij");
		//depDetails.setDate_Of_Birth(new java.sql.Date(date.getTime()));
		depDetails.setDate_Of_Birth("2019-12-18");
		depDetails.setEmployee_Id("emp00");
		depDetails.setCreated_By("jg");
		depDetails.setCreated_Date(new Timestamp(2010, 10, 10, 10, 10, 10, 10));
		
		listDeptDetRequest.add(depDetails);
		dependentDetailsRequest = new DependentDetailsRequest();
		dependentDetailsResponse = new DependentDetailsResponse();	
		

		dependentDetailsRequest.setDependentDetails(listDeptDetRequest);
		return dependentDetailsRequest;
	}
	
	@Test
	public void saveDependentDetailsTest() throws SQLException {
		dependentDetailsRequest = dependentDetailsRequest();
		dependentDetailsRequest.setTransactionType("save");
	//	Object[] object= new Object[] {};
		when(jdbcTemplate.update(anyString(),anyListOf(Object.class))).thenReturn(0);
		int n= dependentDetailsDaoImpl.saveDependentDetails(dependentDetailsRequest);
		assertEquals(0, n);
		
	}
	@Test
	public void updateDependentDetailsTest() throws SQLException {
		dependentDetailsRequest.setTransactionType("update");
		//Object[] object = new Object[] {};
		when(jdbcTemplate.update(anyString(),anyListOf(Object.class))).thenReturn(0);
		int n = dependentDetailsDaoImpl.updateDependentDetails(dependentDetailsRequest());
		assertEquals(0, n);
	
	}
	
	@Test
	public void deleteTest() throws SQLException {
		dependentDetailsRequest.setTransactionType("delete");
		//Object[] object = new Object[] {};
		when(jdbcTemplate.update(anyString(),anyListOf(Object.class))).thenReturn(0);
		int n = dependentDetailsDaoImpl.deleteDependentDetails(dependentDetailsRequest());
		assertEquals(0, n);
	}
	@Test
	public void getAllTest() throws SQLException {
		dependentDetailsRequest.setTransactionType("getall");
		List<DependentDetails> list = new ArrayList<>();
		when(jdbcTemplate.query(GETALLDEPENDENTDETAILS, new RowMap())).thenReturn(list);
		List<DependentDetails> depList= dependentDetailsDaoImpl.getAll(dependentDetailsRequest());
		assertEquals(list, depList);
	}
	
	
	@Test
	 public void getByIdTest() throws SQLException {
		List<DependentDetails> list = new ArrayList<>();
		 when(jdbcTemplate.query(GETDEPENDENTDETAILSBYID,new RowMap())).thenReturn(listDeptDetRequest);
		 List<DependentDetails> depList = dependentDetailsDaoImpl.getById(2);
		 assertEquals(list,depList);

	  }

	@Test
	public void getByEmPId() throws SQLException {
		/*
		 * DependentDetails depDetails = new DependentDetails();
		 * 
		 * depDetails.setEmployee_Id("emp00"); listDeptDetRequest.add(depDetails);
		 * 
		 * dependentDetailsRequest = new DependentDetailsRequest();
		 * dependentDetailsRequest.setDependentDetails(listDeptDetRequest);
		 */
		//List<DependentDetails> list = new ArrayList<>();
		 when(jdbcTemplate.query(GETID,new RowMap())).thenReturn(listDeptDetRequest);
		 List<DependentDetails> depList = dependentDetailsDaoImpl.getByEmpId("em12");
	}
}

