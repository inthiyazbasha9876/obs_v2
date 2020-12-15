package com.ojas.obs.Facade;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.*;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ojas.obs.config.OjasObsOnlineGpaServiceBootApplication;
import com.ojas.obs.dao.GpaPlanDao;
import com.ojas.obs.facade.GpaFacade;
import com.ojas.obs.model.GpaPlan;
import com.ojas.obs.request.GpaRequest;
import com.ojas.obs.response.GpaResponse;

@SpringBootConfiguration
public class GpaFacadeTest extends OjasObsOnlineGpaServiceBootApplication {

	@InjectMocks
	private GpaFacade gpaFacade;

	@Mock
	GpaPlanDao gpaPlanDao;

	@Spy
	Error err = new Error();

	@Spy
	ResponseEntity<Object> responseEntity = new ResponseEntity<>(err, HttpStatus.UNPROCESSABLE_ENTITY);

	@Spy
	ResponseEntity<Object> successEntity = new ResponseEntity<>(HttpStatus.OK);

	@Spy
	List<GpaPlan> listGpaRequest = new ArrayList<GpaPlan>();

	@Spy
	GpaResponse gpaResponse = new GpaResponse();

	@Spy
	List<GpaPlan> resp = new ArrayList<>();

	@Spy
	GpaRequest gpaRequest;

	@Before
	public void beforeTest()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		gpaFacade = new GpaFacade();
		gpaPlanDao = mock(GpaPlanDao.class);
		setCollabarator(gpaFacade, "gpaPlanDaoImpl", gpaPlanDao);

	}

	public void setCollabarator(Object object, String name, Object collabarator)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, collabarator);
	}

	public GpaRequest gpaRequest() {

		// List<GpaPlan> gpaPlan = (List<GpaPlan>) new GpaPlan();
		gpaRequest = new GpaRequest();
		gpaResponse = new GpaResponse();

		GpaPlan gpaPlan = new GpaPlan();
		
		gpaPlan.setGpaPlanType("djf");
		gpaPlan.setGpaPremium(125.2);
		gpaPlan.setTotalPremium(103.32);

		//gpaRequest.setSessionId("121");
		listGpaRequest.add(gpaPlan);
		gpaRequest.setGpaPlan(listGpaRequest);
		gpaRequest.setTransactionType("save");

		return gpaRequest;

	}

	@Test
	public void saveGpaPlanTest() throws SQLException {

		gpaRequest = gpaRequest();
		// gpaRequest.setTransactionType("save");
		when(gpaPlanDao.saveGpaPlan(gpaRequest)).thenReturn(true);
		ResponseEntity<Object> setResponseEntity = gpaFacade.saveGpaPlan(gpaRequest);
		HttpStatus httpStatus = setResponseEntity.getStatusCode();
		assertEquals(HttpStatus.OK, httpStatus);

	}

	@Test
	public void getGpaPlanTest() throws SQLException {
		List<GpaPlan> mockList = new ArrayList<>();
		GpaPlan mockGpa = mock(GpaPlan.class);
		mockList.add(mockGpa);
		gpaRequest = gpaRequest();
		gpaRequest.setTransactionType("getAll");
		when(gpaPlanDao.getAllGpaDetails(gpaRequest)).thenReturn(mockList);
		ResponseEntity<Object> setResponseEntity = gpaFacade.getAllGpaDetails(gpaRequest);
		HttpStatus statusCode = setResponseEntity.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	/*
	 * @Test public void saveGpaPlanNull() throws SQLException {
	 * when(gpaPlanDao.saveGpaPlan(gpaRequestNull())).thenReturn(true);
	 * ResponseEntity<Object> setResponseEntity =
	 * gpaFacade.saveGpaPlan(gpaRequestNull()); HttpStatus httpStatus =
	 * setResponseEntity.getStatusCode(); assertEquals(HttpStatus.OK, httpStatus);
	 * 
	 * }
	 * 
	 * @Test public void getGpaPlanNullTest() throws SQLException {
	 * when(gpaPlanDao.getAllGpaDetails(gpaRequest())).thenReturn(listGpaRequest);
	 * ResponseEntity<Object> setResponseEntity =
	 * gpaFacade.getAllGpaDetails(gpaRequestNull()); HttpStatus httpStatus =
	 * setResponseEntity.getStatusCode(); assertEquals(HttpStatus.OK, httpStatus); }
	 */
	///////// Exception set////////////////
	@Test
	public void saveGapNull() throws SQLException {
		gpaRequest = gpaRequest();
		gpaRequest.setTransactionType("save");
		when(gpaPlanDao.saveGpaPlan(null)).thenReturn(false);
		ResponseEntity<Object> setResponseEntity = gpaFacade.saveGpaPlan(gpaRequest);
		HttpStatus httpStatus = setResponseEntity.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, httpStatus);
	}

	@Test
	public void getGpaNull() throws SQLException {
		gpaRequest = gpaRequest();
		gpaRequest.setTransactionType("update");
		when(gpaPlanDao.updateGpa(null)).thenReturn(false);
		ResponseEntity<Object> setResponseEntity = gpaFacade.saveGpaPlan(gpaRequest);
		HttpStatus httpStatus = setResponseEntity.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, httpStatus);
	}

	@Test
	public void updateGpaPlanTest() throws SQLException {

		gpaRequest = gpaRequest();
		gpaRequest.setTransactionType("update");
		when(gpaPlanDao.updateGpa(gpaRequest)).thenReturn(true);
		ResponseEntity<Object> setResponseEntity = gpaFacade.saveGpaPlan(gpaRequest);
		HttpStatus httpStatus = setResponseEntity.getStatusCode();
		assertEquals(HttpStatus.OK, httpStatus);

	}

	///////////////// set exception//////////

	@Test
	public void setExceptionDuplTest() throws SQLException {

		when(gpaPlanDao.saveGpaPlan(anyObject())).thenThrow(new DuplicateKeyException(null, new Throwable()));
		ResponseEntity<Object> setResponseEntity = gpaFacade.saveGpaPlan(gpaRequest());
		HttpStatus httpStatus = setResponseEntity.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, httpStatus);
	}
	
	@Test
	public void setSQLExceptionTest() throws SQLException 
	{
		when(gpaPlanDao.saveGpaPlan(anyObject())).thenThrow(new SQLException());
		ResponseEntity<Object> setResponseEntity = gpaFacade.saveGpaPlan(gpaRequest());
		HttpStatus httpStatus =setResponseEntity.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, httpStatus);
	}
	@Test
	public void setExceptionTest() throws SQLException 
	{
		when(gpaPlanDao.saveGpaPlan(anyObject())).thenThrow(new RuntimeException());
		ResponseEntity<Object> setResponseEntity = gpaFacade.saveGpaPlan(gpaRequest());
		HttpStatus httpStatus =setResponseEntity.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, httpStatus);
	}

	/////////////////////// get method//////////////
	@Test
	public void getGpaTest() throws SQLException {
		gpaRequest = gpaRequest();

		gpaRequest.setTransactionType("getAll");
		when(gpaPlanDao.getAllGpaDetails(null)).thenReturn(listGpaRequest);
		ResponseEntity<Object> setResponseEntity = gpaFacade.getAllGpaDetails(gpaRequest);
		HttpStatus httpStatus = setResponseEntity.getStatusCode();
		assertNotEquals(HttpStatus.OK, httpStatus);
	}

	@Test
	public void getByIdTestNull() throws SQLException {
		gpaRequest = gpaRequest();
		gpaRequest.setTransactionType("getById");
		when(gpaPlanDao.getById(null)).thenReturn(listGpaRequest);
		ResponseEntity<Object> setResponseEntity = gpaFacade.getAllGpaDetails(gpaRequest);
		HttpStatus httpStatus = setResponseEntity.getStatusCode();
		assertNotEquals(HttpStatus.OK, httpStatus);
	}

	@Test
	public void getByIdTest() throws SQLException {
		gpaRequest = gpaRequest();
		gpaRequest.setTransactionType("getById");

		List<GpaPlan> mockList = new ArrayList<>();
		GpaPlan mockGpa = mock(GpaPlan.class);
		mockList.add(mockGpa);

		when(gpaPlanDao.getById(anyObject())).thenReturn(listGpaRequest);
		ResponseEntity<Object> setResponseEntity = gpaFacade.getAllGpaDetails(gpaRequest);
		HttpStatus httpStatus = setResponseEntity.getStatusCode();
		assertEquals(HttpStatus.OK, httpStatus);
	}
	@Test
	public void getSQLException() throws SQLException {
		gpaRequest = gpaRequest();
		gpaRequest.setTransactionType("getall");

		when(gpaPlanDao.getAllGpaDetails(anyObject())).thenThrow(new SQLException());
		ResponseEntity<Object> setResponseEntity = gpaFacade.getAllGpaDetails(gpaRequest);
		HttpStatus httpStatus =setResponseEntity.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, httpStatus);
	}
	@Test
	public void getExceptionTest() throws SQLException 
	{
		when(gpaPlanDao.getAllGpaDetails(anyObject())).thenThrow(new RuntimeException());
		ResponseEntity<Object> setResponseEntity = gpaFacade.getAllGpaDetails(gpaRequest);
		HttpStatus httpStatus =setResponseEntity.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, httpStatus);
	}
	@Test
	public void getNull() throws SQLException {
		

		when(gpaPlanDao.getAllGpaDetails(anyObject())).thenThrow(new SQLException());
		ResponseEntity<Object> setResponseEntity = gpaFacade.getAllGpaDetails(gpaRequest());
		
	}
	

}
