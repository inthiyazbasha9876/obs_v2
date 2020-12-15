package com.ojas.obs.controllerTest;

import static org.junit.Assert.assertEquals;
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

import com.ojas.obs.controller.LocationTypeController;
import com.ojas.obs.facadeimpl.LocationTypeFacadeImpl;
import com.ojas.obs.model.LocationType;
import com.ojas.obs.request.LocationTypeRequest;
import com.ojas.obs.response.ErrorResponse;
import com.ojas.obs.response.LocationTypeResponse;
 
public class LocationTypeControllerTest {  
	@InjectMocks
	LocationTypeController locationcontroller;

	@Mock
	LocationTypeFacadeImpl locationfacadeImpl;

	@Spy
	LocationTypeRequest locationreq;

	@Spy
	ErrorResponse errorresponse;

	@Spy
	LocationTypeResponse locationresponse; 

	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorresponse, HttpStatus.UNPROCESSABLE_ENTITY);

	@Spy
	ResponseEntity<Object> conflict = new ResponseEntity<>(errorresponse, HttpStatus.CONFLICT);

	@Spy
	ResponseEntity<Object> successResponse = new ResponseEntity<>(locationresponse, HttpStatus.OK);

	@Spy
	LocationType locationtype;

	@Before
	public void init() throws Exception {
		locationcontroller = new LocationTypeController();
		locationfacadeImpl = mock(LocationTypeFacadeImpl.class);
		setCollaborator(locationcontroller, "locationfacadeImpl", locationfacadeImpl);
	}

	private void setCollaborator(Object object, String name, Object service) throws Exception {
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);
	} 

	public List<LocationType> getLocationTypes() {
		List<LocationType> locationlist = new ArrayList<LocationType>();
		LocationType locationdatalist = new LocationType();
		locationdatalist.setLocationtypeId(1);
		LocationType locationdatalist1 = new LocationType();
		locationdatalist1.setLocationtypeId(2);
		locationlist.add(locationdatalist);
		locationlist.add(locationdatalist1);
		return locationlist;
	}

	@Test
	public void servicetypeRequestNullTest() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		LocationTypeRequest locationrequest = new LocationTypeRequest();
		List<LocationType> locationlist = new ArrayList<LocationType>();
		LocationType locationdatalist1 = new LocationType();
		locationdatalist1.setLocationtypeId(null);
		locationdatalist1.setLocationType(null);
		locationdatalist1.setStatus(null);
		locationlist.add(locationdatalist1);
		locationrequest.setLocationTypeList(null);
		when(locationfacadeImpl.saveDetails(locationrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = locationcontroller.saveDetails(locationrequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}

	@Test
	public void servicecategoryRequestsaveTest() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		LocationTypeRequest locationrequest = new LocationTypeRequest();
		locationrequest.setLocationTypeList(this.getLocationTypes());
		locationrequest.setTransactionType("save");
		when(locationfacadeImpl.saveDetails(locationrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = locationcontroller.saveDetails(locationrequest, request, response);
		HttpStatus status = setservice.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, status);
	}

	@Test
	public void servicecategoryRequestupdateTest() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		LocationTypeRequest locationrequest = new LocationTypeRequest();
		locationrequest.setLocationTypeList(this.getLocationTypes());
		locationrequest.setTransactionType("update");
		when(locationfacadeImpl.saveDetails(locationrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setBus = locationcontroller.saveDetails(locationrequest, request, response);
		HttpStatus statusCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void servicecategoryRequestdeleteTest() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		LocationTypeRequest locationrequest = new LocationTypeRequest();
		locationrequest.setLocationTypeList(this.getLocationTypes());
		locationrequest.setTransactionType("delete");
		when(locationfacadeImpl.saveDetails(locationrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setBus = locationcontroller.saveDetails(locationrequest, request, response);
		HttpStatus statusCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setDuplicateKeyExceptionTest() throws Exception {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		LocationTypeRequest locationrequest = new LocationTypeRequest();

		LocationType locationtype2 = new LocationType();
	
		locationtype2.setStatus(true);

		List<LocationType> locationtype = new ArrayList<LocationType>();
		locationtype.add(locationtype2);
		locationrequest.setLocationTypeList(locationtype);
		locationrequest.setTransactionType("save");

		when(locationfacadeImpl.saveDetails(locationrequest))
				.thenThrow(new DuplicateKeyException(null, new Throwable()));
		ResponseEntity<Object> setBus = locationcontroller.saveDetails(locationrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}

	@Test
	public void setExceptionTest() throws Exception {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		LocationTypeRequest locationrequest = new LocationTypeRequest();

		LocationType locationtype2 = new LocationType();
		
		locationtype2.setStatus(false);

		List<LocationType> locationtype = new ArrayList<LocationType>();
		locationtype.add(locationtype2);
		locationrequest.setLocationTypeList(locationtype);
		locationrequest.setTransactionType("save");
		when(locationfacadeImpl.saveDetails(locationrequest)).thenThrow(RuntimeException.class);

		ResponseEntity<Object> setBus = locationcontroller.saveDetails(locationrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}

	@Test
	public void setsavesucces() throws Exception {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		LocationTypeRequest locationrequest = new LocationTypeRequest();

		LocationType locationtype = new LocationType();
	
		locationtype.setStatus(false);

		List<LocationType> locationtype1 = new ArrayList<LocationType>();
		locationtype1.add(locationtype);
		locationrequest.setLocationTypeList(locationtype1);
		locationrequest.setTransactionType("save");
		when(locationfacadeImpl.saveDetails(locationrequest)).thenReturn(successResponse);

		ResponseEntity<Object> setBus = locationcontroller.saveDetails(locationrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}

	@Test
	public void setupdatesucces() throws Exception {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		LocationTypeRequest locationrequest = new LocationTypeRequest();

		LocationType locationtype2 = new LocationType();
		
		locationtype2.setStatus(false);

		List<LocationType> locationtype = new ArrayList<LocationType>();
		locationtype.add(locationtype2);
		locationrequest.setLocationTypeList(locationtype);
		locationrequest.setTransactionType("update");
		when(locationfacadeImpl.saveDetails(locationrequest)).thenReturn(successResponse);

		ResponseEntity<Object> setBus = locationcontroller.saveDetails(locationrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}

	@Test
	public void setdeletesucces() throws Exception {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		LocationTypeRequest locationrequest = new LocationTypeRequest();

		LocationType locationtype2 = new LocationType();
	
		locationtype2.setStatus(false);

		List<LocationType> locationtype = new ArrayList<LocationType>();
		locationtype.add(locationtype2);
		locationrequest.setLocationTypeList(locationtype);
		locationrequest.setTransactionType("delete");
		when(locationfacadeImpl.saveDetails(locationrequest)).thenReturn(successResponse);

		ResponseEntity<Object> setBus = locationcontroller.saveDetails(locationrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}

	// getTestcases

	@Test
	public void getTransactionEmpty() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		LocationTypeRequest locationrequest = new LocationTypeRequest();

		locationrequest.setLocationTypeList(this.getLocationTypes());
		locationrequest.setTransactionType(null);
		when(locationfacadeImpl.saveDetails(locationrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = locationcontroller.getDetails(locationrequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}

	@Test
	public void getByIdsuccesscheck() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		LocationTypeRequest locationrequest = new LocationTypeRequest();

		locationrequest.setLocationTypeList(this.getLocationTypes());
		locationrequest.setTransactionType("getById");

		locationrequest.getLocationTypeList().get(0).getLocationtypeId();

		when(locationfacadeImpl.getDetails(locationrequest)).thenReturn(successResponse);
		ResponseEntity<Object> setservice = locationcontroller.getDetails(locationrequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.OK, unitCode);
	}

	@Test
	public void getByIdcheck() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		LocationTypeRequest locationrequest = new LocationTypeRequest();

		locationrequest.setLocationTypeList(this.getLocationTypes());
		locationrequest.setTransactionType("getById");

		locationrequest.getLocationTypeList().get(0).setLocationtypeId(null);

		when(locationfacadeImpl.getDetails(locationrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = locationcontroller.getDetails(locationrequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}

	@Test
	public void getExceptionTest() throws Exception {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		LocationTypeRequest locationrequest = new LocationTypeRequest();

		locationrequest.setLocationTypeList(this.getLocationTypes());
		locationrequest.setTransactionType("getAll");
		when(locationfacadeImpl.getDetails(locationrequest)).thenThrow(RuntimeException.class);

		ResponseEntity<Object> setBus = locationcontroller.getDetails(locationrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}

}
