package com.obs.businessunit.facadetest;

import static org.junit.Assert.assertEquals;
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
import org.mockito.Spy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.obs.businessunit.dao.BusinessUnitDao;
import com.obs.businessunit.daoimpl.BusinessUnitDaoImpl;
import com.obs.businessunit.error.ErrorResponse;
import com.obs.businessunit.facade.BusinessUnitFacade;
import com.obs.businessunit.facadeimpl.BusinessUnitFacadeImpl;
import com.obs.businessunit.model.BusinessUnit;
import com.obs.businessunit.request.BusinessUnitRequest;
import com.obs.businessunit.response.BusinessUnitResponse;

public class BusinessUnitFacadeImplTest {
	@InjectMocks
	BusinessUnitFacadeImpl businessUnitFacadeImpl;
	@Mock
	BusinessUnitDao businessUnitDao;
	@Spy
	BusinessUnitRequest businessUnitRequest=new BusinessUnitRequest();
	@Spy
	BusinessUnitResponse businessUnitResponse;
	@Spy
	
	@Mock
	BusinessUnitFacade businessUnitFacade;
	
	@Mock
	BusinessUnitDaoImpl businessUnitDaoImpl;
	
	@Spy
	BusinessUnit businessUnit=new BusinessUnit();
     @Spy
	ErrorResponse errorResponse;
     @Spy
 	ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
 	@Spy
 	ResponseEntity<Object> conflict = new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
 	@Spy
 	ResponseEntity<Object> successResponse = new ResponseEntity<>(businessUnitResponse, HttpStatus.OK);

	@Before
	public void init() throws Exception {
		businessUnitFacadeImpl = new BusinessUnitFacadeImpl();
		businessUnitDaoImpl = mock(BusinessUnitDaoImpl.class);
		setCollaborator(businessUnitFacadeImpl, "businessUnitDao", businessUnitDaoImpl);
	}

	public void setCollaborator(Object object, String name, Object service) throws Exception {
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);
	}
	@Test
	public void testSave() throws SQLException {
		businessUnitRequest.setTransactionType("save");
		when(businessUnitDaoImpl.saveBusinessUnit(businessUnitRequest)).thenReturn(true);
		ResponseEntity<Object> saveStatus = businessUnitFacadeImpl.setBusinessUnit(businessUnitRequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}
	@Test
	public void testSaveNegative() throws SQLException {
		businessUnitRequest.setTransactionType("save");
		when(businessUnitDaoImpl.saveBusinessUnit(businessUnitRequest)).thenReturn(false);
		ResponseEntity<Object> saveStatus = businessUnitFacadeImpl.setBusinessUnit(businessUnitRequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	@Test
	public void testUpdate() throws SQLException {
		businessUnitRequest.setTransactionType("update");
		when(businessUnitDaoImpl.updateBusinessUnit(businessUnitRequest)).thenReturn(true);
		ResponseEntity<Object> saveStatus = businessUnitFacadeImpl.setBusinessUnit(businessUnitRequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}
	@Test
	public void testUpdateNegative() throws SQLException {
		businessUnitRequest.setTransactionType("update");
		when(businessUnitDaoImpl.updateBusinessUnit(businessUnitRequest)).thenReturn(false);
		ResponseEntity<Object> saveStatus = businessUnitFacadeImpl.setBusinessUnit(businessUnitRequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	@Test
	public void testDeleteNegative() throws SQLException {
		businessUnitRequest.setTransactionType("delete");
		when(businessUnitDaoImpl.deleteBusinessUnit(businessUnitRequest)).thenReturn(false);
		ResponseEntity<Object> saveStatus = businessUnitFacadeImpl.setBusinessUnit(businessUnitRequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	@Test
	public void testDelete() throws SQLException {
		businessUnitRequest.setTransactionType("delete");
		when(businessUnitDaoImpl.deleteBusinessUnit(businessUnitRequest)).thenReturn(true);
		ResponseEntity<Object> saveStatus = businessUnitFacadeImpl.setBusinessUnit(businessUnitRequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}
	@Test
	public void testGetAll() throws SQLException {
		List<BusinessUnit> businessUnit1 = new ArrayList<BusinessUnit>();
		businessUnit.setId(1);
		businessUnit1.add(businessUnit);
		businessUnitRequest.setBusinessUnit(businessUnit1);
		businessUnitRequest.setTransactionType("getall");
		when(businessUnitDaoImpl.getAllBussinessDetails(businessUnitRequest)).thenReturn(businessUnit1);
		ResponseEntity<Object> saveStatus = businessUnitFacadeImpl.getBusinessUnit(businessUnitRequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}
	@Test
	public void testGetAllNegative() throws SQLException {
		List<BusinessUnit> businessUnit1 = new ArrayList<BusinessUnit>();
		businessUnit1=null;
		businessUnitRequest.setBusinessUnit(businessUnit1);
		businessUnitRequest.setTransactionType("getall");
		when(businessUnitDaoImpl.getAllBussinessDetails(businessUnitRequest)).thenReturn(businessUnit1);
		ResponseEntity<Object> saveStatus = businessUnitFacadeImpl.getBusinessUnit(businessUnitRequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}
	@Test
	public void testGetByid() throws SQLException {
		List<BusinessUnit> businessUnit1 = new ArrayList<BusinessUnit>();
		businessUnit.setId(1);
		businessUnit1.add(businessUnit);
		businessUnitRequest.setBusinessUnit(businessUnit1);
		businessUnitRequest.setTransactionType("getbyid");
		when(businessUnitDaoImpl.getAllBussinessDetails(businessUnitRequest)).thenReturn(businessUnit1);
		ResponseEntity<Object> saveStatus = businessUnitFacadeImpl.getBusinessUnit(businessUnitRequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}
	@Test
	public void testGetnegative() throws SQLException {
		List<BusinessUnit> businessUnit1 = new ArrayList<BusinessUnit>();
		businessUnit1=null;
		businessUnitRequest.setBusinessUnit(businessUnit1);
		businessUnitRequest.setTransactionType("getbyid");
		when(businessUnitDaoImpl.getById(businessUnitRequest)).thenReturn(businessUnit1);
		ResponseEntity<Object> saveStatus = businessUnitFacadeImpl.getBusinessUnit(businessUnitRequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}
	@Test
	public void testGetBuIdNegative() throws SQLException {
		List<String> businessUnit1 = new ArrayList<String>();
		businessUnitRequest.setTransactionType("getallbuId");
		when(businessUnitDaoImpl.getBuHeads(businessUnitRequest)).thenReturn(businessUnit1);
		ResponseEntity<Object> saveStatus = businessUnitFacadeImpl.getBusinessUnit(businessUnitRequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}
	@Test
	public void testGetBuId() throws SQLException {
		List<String> businessUnit1 = new ArrayList<String>();
		businessUnit1.add("19210");
		businessUnitRequest.setTransactionType("getallbuId");
		when(businessUnitDaoImpl.getBuHeads(businessUnitRequest)).thenReturn(businessUnit1);
		ResponseEntity<Object> saveStatus = businessUnitFacadeImpl.getBusinessUnit(businessUnitRequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	
	
}
