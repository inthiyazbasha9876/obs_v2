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

import com.ojas.obs.controller.GeoController;
import com.ojas.obs.facadeimpl.GeoFacadeImpl;
import com.ojas.obs.model.Geo;
import com.ojas.obs.request.GeoRequest;
import com.ojas.obs.response.ErrorResponse;
import com.ojas.obs.response.GeoResponse;

public class GeoControllerTest {       
	
	@InjectMocks 
	GeoController geocontroller;
	 
	@Mock
	GeoFacadeImpl geofacadeImpl;
	
	@Spy
	GeoRequest georeq; 
	
	@Spy
	ErrorResponse errorresponse;
	
	@Spy
	GeoResponse georesponse; 
	
	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorresponse, HttpStatus.UNPROCESSABLE_ENTITY);
	
	@Spy
	ResponseEntity<Object> conflict = new ResponseEntity<>(errorresponse, HttpStatus.CONFLICT);
	
	@Spy
	ResponseEntity<Object> successResponse = new ResponseEntity<>(georesponse, HttpStatus.OK);
	
	@Spy
	Geo geo;
	
	@Before
	public void init() throws Exception 
	{
		geocontroller=new GeoController(); 
		geofacadeImpl = mock(GeoFacadeImpl.class); 
		setCollaborator(geocontroller, "geofacadeImpl", geofacadeImpl);
	}

	private void setCollaborator(Object object, String name, Object service) throws Exception 
	{ 
		Field field;
		field = object.getClass().getDeclaredField(name); 
		field.setAccessible(true);
		field.set(object, service); 
	}
	
	public List<Geo> getGeos() {
		List<Geo> geolist = new ArrayList<Geo>();
		Geo geodatalist = new Geo();
		geodatalist.setGeoId(1);
		Geo geodatalist1 = new Geo();
		geodatalist1.setGeoId(2);
		geolist.add(geodatalist);
		geolist.add(geodatalist1);
		return geolist; 
	}
	
	@Test
	public void servicetypeRequestNullTest() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		GeoRequest georequest = new GeoRequest();
		List<Geo> geolist = new ArrayList<Geo>();
		Geo geodatalist1 = new Geo();
		geodatalist1.setGeoId(null);
		geodatalist1.setGeoname(null);
		geodatalist1.setStatus(null);
		geolist.add(geodatalist1);
		georequest.setGeoList(null);
		when(geofacadeImpl.saveDetails(georequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = geocontroller.saveDetails(georequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}

	@Test
	public void servicecategoryRequestsaveTest() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		GeoRequest georequest = new GeoRequest();
		georequest.setGeoList(this.getGeos());
		georequest.setTransactionType("save");
		when(geofacadeImpl.saveDetails(georequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = geocontroller.saveDetails(georequest, request, response);
		HttpStatus status = setservice.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, status);
	}

	@Test
	public void servicecategoryRequestupdateTest() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		GeoRequest georequest = new GeoRequest();
		georequest.setGeoList(this.getGeos());
		georequest.setTransactionType("update");
		when(geofacadeImpl.saveDetails(georequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setBus = geocontroller.saveDetails(georequest, request, response);
		HttpStatus statusCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void servicecategoryRequestdeleteTest() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		GeoRequest georequest = new GeoRequest();
		georequest.setGeoList(this.getGeos());
		georequest.setTransactionType("delete");
		when(geofacadeImpl.saveDetails(georequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setBus = geocontroller.saveDetails(georequest, request, response);
		HttpStatus statusCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setDuplicateKeyExceptionTest() throws Exception {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		GeoRequest georequest = new GeoRequest();
		Geo geo2 = new Geo();
		geo2.setStatus(true);
		List<Geo> geo = new ArrayList<Geo>();
		geo.add(geo2);
		georequest.setGeoList(geo);
		georequest.setTransactionType("save");

		when(geofacadeImpl.saveDetails(georequest))
				.thenThrow(new DuplicateKeyException(null, new Throwable()));
		ResponseEntity<Object> setBus = geocontroller.saveDetails(georequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}

	@Test
	public void setExceptionTest() throws Exception {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		GeoRequest georequest = new GeoRequest();
		Geo geo1 = new Geo();
		geo1.setStatus(false);
		List<Geo> geo2 = new ArrayList<Geo>();
		geo2.add(geo1);
		georequest.setGeoList(geo2);
		georequest.setTransactionType("save");
		when(geofacadeImpl.saveDetails(georequest)).thenThrow(RuntimeException.class);
		ResponseEntity<Object> setBus = geocontroller.saveDetails(georequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}

	@Test
	public void setsavesucces() throws Exception {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		GeoRequest georequest = new GeoRequest();
		Geo geo = new Geo();
		geo.setStatus(false);
		List<Geo> geo1 = new ArrayList<Geo>();
		geo1.add(geo);
		georequest.setGeoList(geo1);
		georequest.setTransactionType("save");
		when(geofacadeImpl.saveDetails(georequest)).thenReturn(successResponse);

		ResponseEntity<Object> setBus = geocontroller.saveDetails(georequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}

	@Test
	public void setupdatesucces() throws Exception {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		GeoRequest georequest = new GeoRequest();
		Geo geo2 = new Geo();	
		geo2.setStatus(false);
		List<Geo> geo = new ArrayList<Geo>();
		geo.add(geo2);
		georequest.setGeoList(geo);
		georequest.setTransactionType("update");
		when(geofacadeImpl.saveDetails(georequest)).thenReturn(successResponse);

		ResponseEntity<Object> setBus = geocontroller.saveDetails(georequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}

	@Test
	public void setdeletesucces() throws Exception {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		GeoRequest georequest = new GeoRequest();
		Geo geo2 = new Geo();
		geo2.setStatus(false);
		List<Geo> geo = new ArrayList<Geo>();
		geo.add(geo2);
		georequest.setGeoList(geo);
		georequest.setTransactionType("delete");
		when(geofacadeImpl.saveDetails(georequest)).thenReturn(successResponse);

		ResponseEntity<Object> setBus = geocontroller.saveDetails(georequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}

	// getTestcases

	@Test
	public void getTransactionEmpty() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		GeoRequest georequest = new GeoRequest();

		georequest.setGeoList(this.getGeos());
		georequest.setTransactionType(null);
		when(geofacadeImpl.saveDetails(georequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = geocontroller.getDetails(georequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}

	@Test
	public void getByIdsuccesscheck() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		GeoRequest georequest = new GeoRequest();

		georequest.setGeoList(this.getGeos());
		georequest.setTransactionType("getById");

		georequest.getGeoList().get(0).getGeoId();

		when(geofacadeImpl.getDetails(georequest)).thenReturn(successResponse);
		ResponseEntity<Object> setservice = geocontroller.getDetails(georequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.OK, unitCode);
	}

	@Test
	public void getByIdcheck() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		GeoRequest georequest = new GeoRequest();

		georequest.setGeoList(this.getGeos());
		georequest.setTransactionType("getById");

		georequest.getGeoList().get(0).setGeoId(null);

		when(geofacadeImpl.getDetails(georequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = geocontroller.getDetails(georequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}

	@Test
	public void getExceptionTest() throws Exception { 
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		GeoRequest georequest = new GeoRequest();

		georequest.setGeoList(this.getGeos());
		georequest.setTransactionType("getAll");
		when(geofacadeImpl.getDetails(georequest)).thenThrow(RuntimeException.class);

		ResponseEntity<Object> setBus = geocontroller.getDetails(georequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}

}
