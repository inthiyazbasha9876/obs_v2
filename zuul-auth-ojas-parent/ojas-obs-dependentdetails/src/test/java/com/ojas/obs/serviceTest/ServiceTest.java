package com.ojas.obs.serviceTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ojas.obs.dao.DependentDetailsDao;
import com.ojas.obs.daoImpl.DependentDetailsDaoImpl;
import com.ojas.obs.model.DependentDetails;
import com.ojas.obs.request.DependentDetailsRequest;
import com.ojas.obs.response.DependentDetailsResponse;
import com.ojas.obs.serviceImpl.DependentDetailsServiceImpl;
import static org.mockito.Matchers.*;

@SpringBootConfiguration
public class ServiceTest {

	@InjectMocks
	DependentDetailsServiceImpl dependentDetailsServiceImpl;

	@Mock
	DependentDetailsDao dependentDetailsDaoImpl;

	@Spy
	Error err = new Error();

	@Spy
	ResponseEntity<Object> responseEntity = new ResponseEntity<>(err, HttpStatus.UNPROCESSABLE_ENTITY);

	@Spy
	ResponseEntity<Object> successEntity = new ResponseEntity<>(HttpStatus.OK);

	@Spy
	DependentDetailsRequest dependentDetailsRequest;

	@Spy
	DependentDetailsResponse dependentDetailsResponseObj = new DependentDetailsResponse();

	@Spy
	List<DependentDetailsResponse> resp = new ArrayList<DependentDetailsResponse>();

	@Spy
	List<DependentDetails> listDeptDetRequest = new ArrayList<DependentDetails>();

	String str = "success";

	@Before
	public void beforeTest() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		dependentDetailsServiceImpl = new DependentDetailsServiceImpl();
		dependentDetailsDaoImpl = mock(DependentDetailsDaoImpl.class);
		setCollabarator(dependentDetailsServiceImpl, "dependentDetailsDaoImpl", dependentDetailsDaoImpl);
	}

	public void setCollabarator(Object object, String name, Object collabarator) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field;
			field = object.getClass().getDeclaredField(name);
			field.setAccessible(true);
			field.set(object, collabarator);
	}
	
	public DependentDetailsRequest dependentDetailsRequestNull() {

		DependentDetails depDetails = new DependentDetails();
		Date date = new Date();
		depDetails.setId(1);
		depDetails.setDependent_Name("fjdh");
		// depDetails.setRelation("fij");
		//depDetails.setDate_Of_Birth(new java.sql.Date(date.getTime()));
		depDetails.setDate_Of_Birth("2019-12-18");
		depDetails.setEmployee_Id("emp00");
		depDetails.setCreated_By("fkd");
		depDetails.setCreated_Date(new Timestamp(2010, 10, 10, 10, 10, 10, 10));

		listDeptDetRequest.add(depDetails);

		dependentDetailsRequest = new DependentDetailsRequest();
		

		dependentDetailsRequest.setTransactionType("getbyId");
	
		dependentDetailsRequest.setDependentDetails(listDeptDetRequest);
		return dependentDetailsRequest;
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
		depDetails.setCreated_By("djdkn");
		depDetails.setCreated_Date(new Timestamp(2010, 10, 10, 10, 10, 10, 10));
		
		listDeptDetRequest.add(depDetails);
		dependentDetailsRequest = new DependentDetailsRequest();
		
		dependentDetailsRequest.setTransactionType("save");
		
		dependentDetailsRequest.setDependentDetails(listDeptDetRequest);
		return dependentDetailsRequest;
	}

	@Test
	public void setInsertDependentDetails() throws SQLException {
		when(dependentDetailsDaoImpl.saveDependentDetails(anyObject())).thenReturn(1);
		DependentDetailsResponse setResponseEntity = dependentDetailsServiceImpl.setDependentDetails(dependentDetailsRequest());
		Object httpStatus = setResponseEntity.getStatusCode();
		assertEquals("200", httpStatus);
	}
	
	@Test
	public void setInsertDependentDetailsNull() throws SQLException {
		when(dependentDetailsDaoImpl.saveDependentDetails(anyObject())).thenReturn(1);
		DependentDetailsResponse setResponseEntity = dependentDetailsServiceImpl.setDependentDetails(dependentDetailsRequestNull());
		Object httpStatus = setResponseEntity.getStatusCode();
		assertNotEquals("200", httpStatus);
	}

	@Test
	public void setUpdateDependentDetailsTest() throws SQLException {
	
		
		dependentDetailsRequest = new DependentDetailsRequest();
		DependentDetails depDetails = new DependentDetails();
		Date date = new Date();
	//	depDetails.setId(1);
		depDetails.setDependent_Name("fjdh");
		depDetails.setRelation("fij");
		//depDetails.setDate_Of_Birth(new java.sql.Date(date.getTime()));
		depDetails.setDate_Of_Birth("2019-12-18");
		depDetails.setEmployee_Id("emp00");
		depDetails.setCreated_By("jdf");
		depDetails.setCreated_Date(new Timestamp(2010, 10, 10, 10, 10, 10, 10));
		depDetails.setId(1);
		depDetails.setUpdated_By("10");
		depDetails.setUpdated_Date(new Timestamp(2010, 10, 10, 10, 10, 10, 10));
		
		listDeptDetRequest.add(depDetails);
		
		
		//dependentDetailsRequest = dependentDetailsRequest();
		dependentDetailsRequest.setTransactionType("update");
			
				
			
				
				listDeptDetRequest.add(depDetails);
				dependentDetailsRequest.setDependentDetails(listDeptDetRequest);
		
		
		
		
		when(dependentDetailsDaoImpl.updateDependentDetails(dependentDetailsRequest)).thenReturn(1);
		DependentDetailsResponse setResponseEntity = dependentDetailsServiceImpl.setDependentDetails(dependentDetailsRequest);
		Object httpStatus = setResponseEntity.getStatusCode();
		assertEquals("200", httpStatus);

	}

	@Test
	public void setUpdateDependentDetailsTestNull() throws SQLException {
		dependentDetailsRequest= dependentDetailsRequest();
		dependentDetailsRequest.setTransactionType("update");
		when(dependentDetailsDaoImpl.updateDependentDetails(anyObject())).thenReturn(1);
		DependentDetailsResponse setResponseEntity = dependentDetailsServiceImpl.setDependentDetails(dependentDetailsRequestNull());
		Object httpStatus = setResponseEntity.getStatusCode();
		assertNotEquals("200", httpStatus);

	}

	@Test
	public void setDeleteDependentDetailsTest() throws SQLException {
		dependentDetailsRequest= dependentDetailsRequest();
		dependentDetailsRequest.setTransactionType("delete");
		when(dependentDetailsDaoImpl.deleteDependentDetails(anyObject()))
				.thenReturn(1);
		DependentDetailsResponse setResponseEntity = dependentDetailsServiceImpl
				.setDependentDetails(dependentDetailsRequest);
		Object httpStatus = setResponseEntity.getStatusCode();
		assertEquals("200", httpStatus);

	}
	@Test
	public void setDeleteDependentDetailsTestNull() throws SQLException {
		dependentDetailsRequest= dependentDetailsRequest();
		dependentDetailsRequest.setTransactionType("delete");
		when(dependentDetailsDaoImpl.deleteDependentDetails(anyObject()))
				.thenReturn(1);
		DependentDetailsResponse setResponseEntity = dependentDetailsServiceImpl
				.setDependentDetails(dependentDetailsRequestNull());
		Object httpStatus = setResponseEntity.getStatusCode();
		assertNotEquals("200", httpStatus);

	}


	@Test
	public void getDependentDetailsTest() throws SQLException {
		dependentDetailsRequest= dependentDetailsRequest();
		dependentDetailsRequest.setTransactionType("getAll");
		when(dependentDetailsDaoImpl.getAll(dependentDetailsRequest)).thenReturn(listDeptDetRequest);
		DependentDetailsResponse setResponseEntity = dependentDetailsServiceImpl.getDependentDetails(dependentDetailsRequest);
		String httpStatus = setResponseEntity.getStatusCode();
		assertEquals("200", httpStatus);

	}
	
	
	@Test
	public void getByIdTest() throws SQLException {
		dependentDetailsRequest= dependentDetailsRequest();
		
		dependentDetailsRequest.setTransactionType("getById");
		when(dependentDetailsDaoImpl.getById(1)).thenReturn(listDeptDetRequest);
		DependentDetailsResponse setResponseEntity = dependentDetailsServiceImpl.getDependentDetails(dependentDetailsRequest);
		String httpStatus = setResponseEntity.getStatusCode();
		assertEquals("200", httpStatus);
	}
	
	@Test 
	public void NullcheckTestset() throws SQLException {
		dependentDetailsRequest = dependentDetailsRequest();
		dependentDetailsRequest.setTransactionType("save");
		List<DependentDetails> dependentDetails = dependentDetailsRequest.getDependentDetails();
		DependentDetails depe= new DependentDetails();
		//dep.setId(0);
	
		depe.setId(0);
		listDeptDetRequest.add(depe);
		dependentDetailsRequest.setDependentDetails(listDeptDetRequest);
		
		when(dependentDetailsDaoImpl.saveDependentDetails(dependentDetailsRequest())).thenReturn(1);
		DependentDetailsResponse setResponseEntity = dependentDetailsServiceImpl.setDependentDetails(dependentDetailsRequest);
		String httpStatus = setResponseEntity.getStatusCode();
		assertNotEquals("200", httpStatus);
		
	}
		
		@Test
		public void NullCheckUpdateTest() throws SQLException {
	dependentDetailsRequest = dependentDetailsRequest();
	dependentDetailsRequest.setTransactionType("update");
			//List<DependentDetails> dependentDetails = dependentDetailsRequest.getDependentDetails();
			DependentDetails depe= new DependentDetails();
			
		//	depe.setDependent_Name(null);
			listDeptDetRequest.add(depe);
			dependentDetailsRequest.setDependentDetails(listDeptDetRequest);
			
			when(dependentDetailsDaoImpl.updateDependentDetails(null)).thenReturn(1);
			DependentDetailsResponse setResponseEntity = dependentDetailsServiceImpl.setDependentDetails(dependentDetailsRequest);
			String httpStatus = setResponseEntity.getStatusCode();
			assertEquals("422", httpStatus);
		}
		
	@Test
	public void NullCheckTestGet() throws SQLException {
dependentDetailsRequest = dependentDetailsRequest();
		
		List<DependentDetails> dependentDetails = dependentDetailsRequest.getDependentDetails();
		DependentDetails depe= new DependentDetails();
		//dep.setId(0);
		dependentDetailsRequest.setTransactionType("getAll");
	
		depe.setId(0);
		listDeptDetRequest.add(depe);
		dependentDetailsRequest.setDependentDetails(listDeptDetRequest);
		when(dependentDetailsDaoImpl.getAll(dependentDetailsRequest())).thenReturn(listDeptDetRequest);
		DependentDetailsResponse setResponseEntiry = dependentDetailsServiceImpl.getDependentDetails(dependentDetailsRequest);
		String httpsStatus= setResponseEntiry.getStatusCode();
		assertNotEquals("200", httpsStatus);
		
	}
	@Test 
	public void DeleteNullCheck() throws SQLException {

	
		DependentDetails depe= new DependentDetails();
		dependentDetailsRequest = dependentDetailsRequest();

		dependentDetailsRequest.setTransactionType("delete");
	
		depe.setId(0);
		listDeptDetRequest.add(depe);
		dependentDetailsRequest.setDependentDetails(listDeptDetRequest);
		
		when(dependentDetailsDaoImpl.deleteDependentDetails(dependentDetailsRequest)).thenReturn(0);
		DependentDetailsResponse setResponseEntity = dependentDetailsServiceImpl.setDependentDetails(dependentDetailsRequest);
		String httpStatus = setResponseEntity.getStatusCode();
		assertEquals("422", httpStatus);
		
	}
	
	@Test 
	public void GetAllNullCheck() throws SQLException {

		dependentDetailsRequest = dependentDetailsRequest();
		dependentDetailsRequest.setTransactionType("getAll");

		dependentDetailsRequest.setDependentDetails(listDeptDetRequest);
		when(dependentDetailsDaoImpl.getAll(null)).thenReturn(listDeptDetRequest);
		DependentDetailsResponse setResponseEntity = dependentDetailsServiceImpl.getDependentDetails(dependentDetailsRequest);
		String httpStatus = setResponseEntity.getStatusCode();
		assertEquals("200", httpStatus);
		
	}
	
	@Test
	public void getByIdNullCheck() throws SQLException {

		
		DependentDetails depe= new DependentDetails();
		dependentDetailsRequest = dependentDetailsRequest();

		dependentDetailsRequest.setTransactionType("getById");
	
		//depe.setId(0);
		listDeptDetRequest.add(depe);
		dependentDetailsRequest.setDependentDetails(listDeptDetRequest);
		
		when(dependentDetailsDaoImpl.getById(0)).thenReturn(listDeptDetRequest);
		DependentDetailsResponse setResponseEntity = dependentDetailsServiceImpl.getDependentDetails(dependentDetailsRequest);
		String httpStatus = setResponseEntity.getStatusCode();
		assertEquals("200", httpStatus);
		
	}
	@Test
	public void getByEmpIdTest() throws SQLException { 
		DependentDetails depe= new DependentDetails();
		depe.setId(1);
		depe.setEmployee_Id(null);
		DependentDetailsRequest dependentDetailsRequest = new DependentDetailsRequest();

		dependentDetailsRequest.setTransactionType("getById");
	
		//depe.setId(0);
		listDeptDetRequest.add(depe);
		dependentDetailsRequest.setDependentDetails(listDeptDetRequest);
		int n = dependentDetailsRequest.getDependentDetails().get(0).getId();
		when(dependentDetailsDaoImpl.getById(n)).thenReturn(listDeptDetRequest);
		DependentDetailsResponse setResponseEntity = dependentDetailsServiceImpl.getDependentDetails(dependentDetailsRequest);
		String httpStatus = setResponseEntity.getStatusCode();
		assertEquals("200", httpStatus);
	}
	
	
	
	
	
}
