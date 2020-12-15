package com.obs.businessunit.controllerTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.obs.businessunit.controller.BusinessUnitController;
import com.obs.businessunit.error.ErrorResponse;
import com.obs.businessunit.facade.BusinessUnitFacade;
import com.obs.businessunit.facadeimpl.BusinessUnitFacadeImpl;
import com.obs.businessunit.model.BusinessUnit;
import com.obs.businessunit.request.BusinessUnitRequest;
import com.obs.businessunit.response.BusinessUnitResponse;

public class BusinessUnitControllerTest {
	@InjectMocks
	BusinessUnitController unitController;
	@Mock
	BusinessUnitFacade businessUnitFacade;
	@Spy
	BusinessUnitRequest businessUnitsRequest=new BusinessUnitRequest();
	@Spy
	ErrorResponse errorResponse = new ErrorResponse();
	@Spy
	BusinessUnitResponse businessUnitResponse = new BusinessUnitResponse();
	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
	@Spy
	ResponseEntity<Object> conflict = new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
	@Spy
	ResponseEntity<Object> successResponse = new ResponseEntity<>(businessUnitResponse, HttpStatus.OK);
	@Spy
	BusinessUnit businessUnit = new BusinessUnit();

	@Before
	public void init() throws Exception {
		unitController = new BusinessUnitController();
		businessUnitFacade = mock(BusinessUnitFacadeImpl.class);
		setCollaborator(unitController, "businessUnitFacade", businessUnitFacade);
	}

	public void setCollaborator(Object object, String name, Object service) throws Exception {
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);
	}

	public List<BusinessUnit> getbusUnitList() {
		List<BusinessUnit> busDetailsList = new ArrayList<BusinessUnit>();
		businessUnit.setId(1);
		businessUnit.setBusinessUnitName("ice-cream");
		businessUnit.setCostCenterId(66);
		busDetailsList.add(businessUnit);
		return busDetailsList;
	}
	//---------Request Object null test---------------
	@Test
	public void busUnitRequestNullTest() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		businessUnitsRequest = null;
		when(businessUnitFacade.setBusinessUnit(businessUnitsRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setBus = unitController.setBusinessUnit(businessUnitsRequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	//-------setException test-------------
	@Test
	public void setExceptionTest() throws Exception {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		businessUnitsRequest.setBusinessUnit(this.getbusUnitList());
		businessUnitsRequest.setTransactionType("save");
		when(businessUnitFacade.setBusinessUnit(businessUnitsRequest)).thenThrow(new RuntimeException());
		ResponseEntity<Object> setBus = unitController.setBusinessUnit(businessUnitsRequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}
	
	//-------setSQLException test-------------
	
	
	
		@Test
		public void setSQLExceptionTest() throws SQLException {
			HttpServletRequest request = null;
			HttpServletResponse response = null;
			businessUnitsRequest.setBusinessUnit(this.getbusUnitList());
			businessUnitsRequest.setTransactionType("save");
			when(businessUnitFacade.setBusinessUnit(businessUnitsRequest)).thenThrow(SQLException.class);
			ResponseEntity<Object> setBus = unitController.setBusinessUnit(businessUnitsRequest, request, response);
			HttpStatus unitCode = setBus.getStatusCode();
			assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
		}
		
		//-------setDupulicateException test-------------
		@Test
		public void setDupulicateExceptionTest() throws SQLException {
			HttpServletRequest request = null;
			HttpServletResponse response = null;
			businessUnitsRequest.setBusinessUnit(this.getbusUnitList());
			businessUnitsRequest.setTransactionType("save");
			when(businessUnitFacade.setBusinessUnit(businessUnitsRequest)).thenThrow(new DuplicateKeyException(null,new Throwable()));
			ResponseEntity<Object> setBus = unitController.setBusinessUnit(businessUnitsRequest, request, response);
			HttpStatus unitCode = setBus.getStatusCode();
			assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
		}
		
	
	@Test
	public void busUnitNullObjList() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		when(businessUnitFacade.setBusinessUnit(businessUnitsRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setBus = unitController.setBusinessUnit(businessUnitsRequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}

	@Test
	public void setBusUnitTransationTypeNullType() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		businessUnitsRequest.setBusinessUnit(getbusUnitList());
		businessUnitsRequest.setTransactionType(null);
		when(businessUnitFacade.setBusinessUnit(businessUnitsRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setBus = unitController.setBusinessUnit(businessUnitsRequest, request, response);
		HttpStatus statusCode = setBus.getStatusCode();
		assertNotEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void setBusUnitTransatonTypeEmptyTest() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		businessUnitsRequest.setBusinessUnit(getbusUnitList());
		businessUnitsRequest.setTransactionType("");
		when(businessUnitFacade.setBusinessUnit(businessUnitsRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setEmp = unitController.setBusinessUnit(businessUnitsRequest, request, response);
		HttpStatus statusCode = setEmp.getStatusCode();
		assertNotEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void setBusinessUnitSave() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		businessUnitsRequest.setBusinessUnit(this.getbusUnitList());
		businessUnitsRequest.setTransactionType("save");
		when(businessUnitFacade.setBusinessUnit(businessUnitsRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setBus = unitController.setBusinessUnit(businessUnitsRequest, request, response);
		HttpStatus statusCode = setBus.getStatusCode();
		assertNotEquals(HttpStatus.OK, statusCode);
	}


	@Test
	public void setBusinessUnitUpdateTest() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		businessUnitsRequest.setBusinessUnit(getbusUnitList());
		businessUnitsRequest.setTransactionType("update");
		when(businessUnitFacade.setBusinessUnit(businessUnitsRequest)).thenReturn(successResponse);
		ResponseEntity<Object> setBus = unitController.setBusinessUnit(businessUnitsRequest, request, response);
		HttpStatus statusCode = setBus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void setBusinessUnitUpdateIdNullCheck() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		businessUnitsRequest.setBusinessUnit(getbusUnitList());
		businessUnitsRequest.getBusinessUnit().get(0).setId(null);
		businessUnitsRequest.setTransactionType("update");
		when(businessUnitFacade.setBusinessUnit(businessUnitsRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setBus = unitController.setBusinessUnit(businessUnitsRequest, request, response);
		HttpStatus statusCode = setBus.getStatusCode();
		assertNotEquals(HttpStatus.OK, statusCode);
	}

	//----------GetAll test case--------------
	@Test
	public void testGetbusinessUnit() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		businessUnitsRequest.setTransactionType("getall");
		when(businessUnitFacade.getBusinessUnit(businessUnitsRequest)).thenReturn(successResponse);
		ResponseEntity<Object> businessUnit = unitController.getBusinessUnit(businessUnitsRequest,request,response);
		HttpStatus status = businessUnit.getStatusCode();
		assertEquals(HttpStatus.OK, status);
	}
	//---------------Request null test--------------------
	@Test
	public void getRequestNullTest() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
	    businessUnitsRequest = null;
		when(businessUnitFacade.getBusinessUnit(businessUnitsRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> businessUnit = unitController.getBusinessUnit(businessUnitsRequest,request,response);
		HttpStatus status = businessUnit.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}
	
	

	@Test
	public void getExceptionTest() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		businessUnitsRequest.setTransactionType(null);
		when(businessUnitFacade.getBusinessUnit(businessUnitsRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> businessUnit = unitController.getBusinessUnit(businessUnitsRequest,request,response);
		HttpStatus status = businessUnit.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, status);
	}
	//---------TransactionType empty Test----------------
		@Test
		public void getTransactionEmpty() throws SQLException {
			HttpServletRequest request = null;
			HttpServletResponse response = null;
			businessUnitsRequest.setTransactionType("");
			when(businessUnitFacade.getBusinessUnit(businessUnitsRequest)).thenReturn(failureResponse);
			ResponseEntity<Object> businessUnit = unitController.getBusinessUnit(businessUnitsRequest,request,response);
			HttpStatus status = businessUnit.getStatusCode();
			assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
		}
		//---------id null Test----------------
				@Test
				public void getIdNull() throws SQLException {
					HttpServletRequest request = null;
					HttpServletResponse response = null;
					ArrayList<BusinessUnit> arrayList = new ArrayList<BusinessUnit>();
					businessUnit.setId(null);
					arrayList.add(businessUnit);
					businessUnitsRequest.setBusinessUnit(arrayList);
					businessUnitsRequest.setTransactionType("getById");
					when(businessUnitFacade.getBusinessUnit(businessUnitsRequest)).thenReturn(failureResponse);
					ResponseEntity<Object> businessUnit = unitController.getBusinessUnit(businessUnitsRequest,request,response);
					HttpStatus status = businessUnit.getStatusCode();
					assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
				}
	
				//-------getSQLException test-------------
				
				@Test
				public void getSQLExceptionTest() throws SQLException {
					HttpServletRequest request = null;
					HttpServletResponse response = null;
					businessUnitsRequest.setBusinessUnit(new ArrayList<BusinessUnit>());
					businessUnitsRequest.setTransactionType("getAll");
					when(businessUnitFacade.getBusinessUnit(businessUnitsRequest)).thenThrow(SQLException.class);
					ResponseEntity<Object> setBus = unitController.getBusinessUnit(businessUnitsRequest, request, response);
					Object unitCode = setBus.getStatusCode();
					assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
				}
				
		
				
}
