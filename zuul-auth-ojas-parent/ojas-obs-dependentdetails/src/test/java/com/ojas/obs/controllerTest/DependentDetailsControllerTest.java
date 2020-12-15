
package com.ojas.obs.controllerTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.*;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ojas.obs.controller.DependentDetailsController;
import com.ojas.obs.model.DependentDetails;
import com.ojas.obs.request.DependentDetailsRequest;
import com.ojas.obs.response.DependentDetailsResponse;
import com.ojas.obs.service.DependentDetailsService;
import com.ojas.obs.serviceImpl.DependentDetailsServiceImpl;

import net.bytebuddy.implementation.bytecode.Throw;

@SpringBootConfiguration
public class DependentDetailsControllerTest {

	@InjectMocks
	private DependentDetailsController dependentDetailsController;

	@Mock
	DependentDetailsService dependentDetailsServiceImpl;

	@Spy
	Error err = new Error();

	@Spy
	ResponseEntity<Object> responseEntity = new ResponseEntity<>(err, HttpStatus.UNPROCESSABLE_ENTITY);

	@Spy
	ResponseEntity<Object> successEntity = new ResponseEntity<>(HttpStatus.OK);

	@Spy
	DependentDetailsResponse dependentDetailsResponse = new DependentDetailsResponse();

	@Spy
	List<DependentDetailsResponse> resp = new ArrayList<DependentDetailsResponse>();

	@Spy
	DependentDetailsRequest dependentDetailsRequest;

	@Spy
	List<DependentDetails> listDeptDetRequest = new ArrayList<DependentDetails>();

	@Before
	public void beforeTest() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		dependentDetailsController = new DependentDetailsController();
		dependentDetailsServiceImpl = mock(DependentDetailsServiceImpl.class);
		setCollabarator(dependentDetailsController, "dependentDetailsServiceImpl", dependentDetailsServiceImpl);
	}
	/*
	 * @Before public void beforeTest1() { dependentDetailsController = new
	 * DependentDetailsController(); dependentDetailsServiceImpl =
	 * mock(DependentDetailsServiceImpl.class);
	 * setCollabarator(dependentDetailsController, "dependentDetailsServiceImpl1",
	 * dependentDetailsServiceImpl); }
	 */

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
		depDetails.setCreated_By("jg");
		depDetails.setCreated_Date(new Timestamp(2010, 10, 10, 10, 10, 10, 10));

		listDeptDetRequest.add(depDetails);

		dependentDetailsRequest = new DependentDetailsRequest();
		dependentDetailsResponse = new DependentDetailsResponse();

		dependentDetailsRequest.setTransactionType("save");
	
		dependentDetailsRequest.setDependentDetails(listDeptDetRequest);
		return dependentDetailsRequest;
	}

	public DependentDetailsRequest dependentDetailsRequest() {

		DependentDetails depDetails = new DependentDetails();
		Date date = new Date();
		depDetails.setId(1);
		depDetails.setDependent_Name("fjdh");
		depDetails.setRelation("fij");
		depDetails.setDate_Of_Birth("2019-12-18");
		depDetails.setEmployee_Id("emp00");
		depDetails.setCreated_By("difjd");
		depDetails.setCreated_Date(new Timestamp(2010, 10, 10, 10, 10, 10, 10));
		
		listDeptDetRequest.add(depDetails);
		dependentDetailsRequest = new DependentDetailsRequest();
		dependentDetailsResponse = new DependentDetailsResponse();	
		
		dependentDetailsRequest.setTransactionType("getAll");
	
		dependentDetailsRequest.setDependentDetails(listDeptDetRequest);
		return dependentDetailsRequest;
	}
	

	@Test
	public void setDependentDetails() throws SQLException {
		HttpServletRequest httpServletRequest = null;
		HttpServletResponse httpServletResponse = null;
		when(dependentDetailsServiceImpl.setDependentDetails(dependentDetailsRequest()))
				.thenReturn(dependentDetailsResponse);
		ResponseEntity<Object> setResponseEntity = dependentDetailsController
				.setDependentDetails(dependentDetailsRequest(), httpServletRequest, httpServletResponse);
		HttpStatus httpStatus = setResponseEntity.getStatusCode();
		assertEquals(HttpStatus.OK, httpStatus);
	}



	@Test
	public void setDependentDetailsNullReq() throws SQLException {
		HttpServletRequest httpServletRequest = null;
		HttpServletResponse httpServletResponse = null;
		dependentDetailsRequest = null;
		when(dependentDetailsServiceImpl.setDependentDetails(dependentDetailsRequest))
				.thenReturn(dependentDetailsResponse);
		ResponseEntity<Object> setResponseEntity = dependentDetailsController
				.setDependentDetails(dependentDetailsRequest, httpServletRequest, httpServletResponse);
		HttpStatus httpStatus = setResponseEntity.getStatusCode();
		assertNotEquals(HttpStatus.OK, httpStatus);
	}

	
	
	@Test
	public void setDependentDetailsNullDeptDetails() throws SQLException {
		HttpServletRequest httpServletRequest = null;
		HttpServletResponse httpServletResponse = null;
		dependentDetailsRequest = dependentDetailsRequest();
		dependentDetailsRequest.setDependentDetails(null); 
		when(dependentDetailsServiceImpl.setDependentDetails(dependentDetailsRequest)).thenReturn(dependentDetailsResponse);
		ResponseEntity<Object> setResponseEntity = dependentDetailsController.setDependentDetails(dependentDetailsRequest, httpServletRequest, httpServletResponse);
		HttpStatus httpStatus = setResponseEntity.getStatusCode();
		assertNotEquals(HttpStatus.OK, httpStatus);
	}
	
	@Test
	public void setDependentDetailsNullTranType() throws SQLException {
		HttpServletRequest httpServletRequest = null;
		HttpServletResponse httpServletResponse = null;
		dependentDetailsRequest = dependentDetailsRequest();
		dependentDetailsRequest.setTransactionType(null); 
		when(dependentDetailsServiceImpl.setDependentDetails(dependentDetailsRequest)).thenReturn(dependentDetailsResponse);
		ResponseEntity<Object> setResponseEntity = dependentDetailsController.setDependentDetails(dependentDetailsRequest, httpServletRequest, httpServletResponse);
		HttpStatus httpStatus = setResponseEntity.getStatusCode();
		assertNotEquals(HttpStatus.OK, httpStatus);
	}
	
	
	
	@Test 
	public void setSQLExceptionTest() throws SQLException {
		HttpServletRequest httpServletRequest = null;
		HttpServletResponse httpServletResponse = null;
		when(dependentDetailsServiceImpl.setDependentDetails(anyObject())).thenThrow(new SQLException(null,new Throwable()));
		ResponseEntity<Object> setResponseEntity= dependentDetailsController.setDependentDetails(dependentDetailsRequest(), httpServletRequest, httpServletResponse);
		HttpStatus httpStatus = setResponseEntity.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, httpStatus);
	}

	@Test
	public void setExceptionTest() throws SQLException {
		HttpServletRequest httpServletRequest = null;
		HttpServletResponse httpServletResponse=null;
		when(dependentDetailsServiceImpl.setDependentDetails(anyObject())).thenThrow(new DuplicateKeyException(null, new Throwable()));
		ResponseEntity<Object> setResponseEntity = dependentDetailsController.setDependentDetails(dependentDetailsRequest(), httpServletRequest, httpServletResponse); 
	
		HttpStatus httpStatus= setResponseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, httpStatus);
	}
	

	
	//////////////////////////////////////////Get method///////////////////////////////////////////////////////////
	@Test
	public void getDepTest() throws SQLException {
		HttpServletRequest httpServletRequest = null;
		HttpServletResponse httpServletResponse = null;
		when(dependentDetailsServiceImpl.getDependentDetails(dependentDetailsRequest))
				.thenReturn(dependentDetailsResponse);
		ResponseEntity<Object> setResponseEntity = dependentDetailsController.getDependentDetails(dependentDetailsRequest(), httpServletRequest, httpServletResponse);
		HttpStatus httpStatus = setResponseEntity.getStatusCode();
		assertEquals(HttpStatus.OK, httpStatus);
	}
	@Test
	public void getDepNull() throws SQLException {
		HttpServletRequest httpServletRequest = null;
		HttpServletResponse httpServletResponse = null;
		when(dependentDetailsServiceImpl.getDependentDetails(dependentDetailsRequest))
				.thenReturn(dependentDetailsResponse);
		ResponseEntity<Object> setResponseEntity = dependentDetailsController
				.setDependentDetails(dependentDetailsRequestNull(), httpServletRequest, httpServletResponse);
		HttpStatus httpStatus = setResponseEntity.getStatusCode();
		assertEquals(HttpStatus.OK, httpStatus);
	}
	
	
	
	
	@Test
	public void getDeptNullReq() throws SQLException 
	{
		HttpServletRequest httpServletRequest = null;
		HttpServletResponse httpServletResponse= null;
		dependentDetailsRequest = null;
		when(dependentDetailsServiceImpl.getDependentDetails(anyObject())).thenReturn(dependentDetailsResponse);
		ResponseEntity<Object> setResponseEntity = dependentDetailsController.getDependentDetails(dependentDetailsRequest, httpServletRequest, httpServletResponse);
		HttpStatus httpStatus = setResponseEntity.getStatusCode();
		assertNotEquals(HttpStatus.OK, httpStatus);
	}
	
	
	
	
	
	@Test
	public void getDeptNullDeptDetails() throws SQLException {
		HttpServletRequest httpServletRequest = null;
		HttpServletResponse httpServletResponse = null;
		dependentDetailsRequest = dependentDetailsRequest();
		dependentDetailsRequest.setDependentDetails(null);
		when(dependentDetailsServiceImpl.getDependentDetails(anyObject())).thenReturn(dependentDetailsResponse);
		ResponseEntity<Object> setresponseEntity = dependentDetailsController.getDependentDetails(dependentDetailsRequest, httpServletRequest, httpServletResponse);
		HttpStatus httpStatus = setresponseEntity.getStatusCode();
		assertNotEquals(HttpStatus.OK, httpStatus);
		
	}
	
	@Test
	public void getTransTypeNull() throws SQLException {
		HttpServletRequest httpServletRequest=null;
		HttpServletResponse httpServletResponse=null;
		dependentDetailsRequest = dependentDetailsRequest();
		dependentDetailsRequest.setTransactionType(null);
		when(dependentDetailsServiceImpl.getDependentDetails(anyObject())).thenReturn(dependentDetailsResponse);
		ResponseEntity<Object> setResponseEntity = dependentDetailsController.getDependentDetails(dependentDetailsRequest, httpServletRequest, httpServletResponse);
		HttpStatus httpStatus = setResponseEntity.getStatusCode();
		assertNotEquals(HttpStatus.OK, httpStatus);
	}
	
	
	
	
	
	
	
	
	/////////get Exception test///////////////
	@Test
	public void getDepExceptionTest() throws SQLException {
		dependentDetailsRequest = dependentDetailsRequest();
		dependentDetailsRequest.setTransactionType("getall");
		HttpServletRequest httpServletRequest = null;
		HttpServletResponse httpServletResponse=null;
	
		when(dependentDetailsServiceImpl.getDependentDetails(anyObject())).thenThrow(new DuplicateKeyException(null, new Throwable()));
		ResponseEntity<Object> setResponseEntity = dependentDetailsController.getDependentDetails(dependentDetailsRequest, httpServletRequest, httpServletResponse);
		HttpStatus httpStatus = setResponseEntity.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, httpStatus);

    }
	
	@Test
	public void getSQlExceptionTest() throws SQLException {
		dependentDetailsRequest = dependentDetailsRequest();
		dependentDetailsRequest.setTransactionType("getall");
		HttpServletRequest httpServletRequest = null;
		HttpServletResponse httpServletResponse=null;
		//when(dependentDetailsServiceImpl.getDependentDetails(anyObject())).thenThrow(new SQLException());
		when(dependentDetailsServiceImpl.getDependentDetails(anyObject())).thenThrow(new SQLException(null,new Throwable()));
		ResponseEntity<Object> setResponseEntity = dependentDetailsController.getDependentDetails(dependentDetailsRequest, httpServletRequest, httpServletResponse);
		HttpStatus httpStatus = setResponseEntity.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, httpStatus);

    }
	
}
