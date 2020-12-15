package com.ojas.obs.ControllerTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.*;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpServerErrorException;

//import com.ojas.demo.OjasObsOnlineGpaServiceBootApplicationTests;
import com.ojas.obs.config.OjasObsOnlineGpaServiceBootApplication;
import com.ojas.obs.controller.GpaController;
import com.ojas.obs.facade.GpaFacade;
import com.ojas.obs.model.GpaPlan;
import com.ojas.obs.request.GpaRequest;
import com.ojas.obs.response.GpaResponse;

@SpringBootConfiguration
public class GpaControllerTest extends OjasObsOnlineGpaServiceBootApplication{

	@InjectMocks
	private GpaController gpaController;

	@Mock
	GpaFacade gpaFacade;
	
	Connection connection = mock(Connection.class);
	@Mock
	DataSource dataSource;

	@Spy
	Error err = new Error();

	@Spy
	ResponseEntity<Object> responseEntity = new ResponseEntity<>(err, HttpStatus.UNPROCESSABLE_ENTITY);

	@Spy
	ResponseEntity<Object> successEntity = new ResponseEntity<>(HttpStatus.OK);

	@Spy
	GpaResponse gpaResponse = new GpaResponse();

	@Spy
	List<GpaPlan> listGpaRequest = new ArrayList<GpaPlan>();

	@Spy
	List<GpaResponse> resp = new ArrayList<GpaResponse>();

	@Spy
	GpaRequest gpaRequest;

	@Before
	public void beforeTest() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		gpaController = new GpaController();
		dataSource = mock(DataSource.class);
		
		gpaFacade = mock(GpaFacade.class);
		setCollabarator(gpaController, "gpaFacade", gpaFacade);

	}

	public void setCollabarator(Object object, String name, Object collabarator) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field;
			field = object.getClass().getDeclaredField(name);
			field.setAccessible(true);
			field.set(object, collabarator);
	}

	public GpaRequest gpaRequestNull() {

		// List<GpaPlan> gpaPlan = (List<GpaPlan>) new GpaPlan();
		gpaRequest = new GpaRequest();
		gpaResponse = new GpaResponse();

		GpaPlan gpaPlan = new GpaPlan();
	
		gpaPlan.setGpaPlanType("djf");
		//gpaPlan.setGpaPremium(125.2);
		gpaPlan.setTotalPremium(103.32);
	

		//gpaRequest.setSessionId("121");
		listGpaRequest.add(gpaPlan);
		gpaRequest.setGpaPlan(listGpaRequest);
		gpaRequest.setTransactionType("save");

		return gpaRequest;

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
	public void saveGpa() throws SQLException {
		gpaRequest =gpaRequest();
		gpaRequest.setTransactionType("save");
		HttpServletRequest httpServletRequest = null;
		HttpServletResponse httpServletResponse = null;
		when(gpaFacade.saveGpaPlan(gpaRequest())).thenReturn(successEntity);
		ResponseEntity<Object> setResponseEntity = gpaController.saveGpa(gpaRequest, httpServletRequest,
				httpServletResponse);
		HttpStatus httpStatus = setResponseEntity.getStatusCode();
		assertEquals(HttpStatus.OK, httpStatus);

	}
	@Test
	public void getGpaDetails() throws SQLException {

		gpaRequest =gpaRequest();
		gpaRequest.setTransactionType("getAll");
		HttpServletRequest httpServletRequest = null;
		HttpServletResponse httpServletResponse = null;
		when(gpaFacade.getAllGpaDetails(gpaRequest())).thenReturn(successEntity);
		ResponseEntity<Object> setResponseEntity = gpaController.getgpaDetails(gpaRequest, httpServletRequest,
				httpServletResponse);
		HttpStatus httpStatus = setResponseEntity.getStatusCode();
		assertEquals(HttpStatus.OK, httpStatus);
	}
	
	@Test
	public void saveGpaNull() throws SQLException {
		gpaRequest =gpaRequest();
		gpaRequest.setTransactionType("save");
		HttpServletRequest httpServletRequest = null;
		HttpServletResponse httpServletResponse = null;
		when(gpaFacade.saveGpaPlan(gpaRequestNull())).thenReturn(successEntity);
		ResponseEntity<Object> setResponseEntity = gpaController.saveGpa(gpaRequestNull(), httpServletRequest,
				httpServletResponse);
		HttpStatus httpStatus = setResponseEntity.getStatusCode();
		assertNotEquals(HttpStatus.OK, httpStatus);

	}
	@Test
	public void setGpaReqNullTest() throws SQLException {
		gpaRequest =gpaRequest();
	     gpaRequest=null;
		HttpServletRequest httpServletRequest=null;
		HttpServletResponse httpServletResponse=null;
		when(gpaFacade.saveGpaPlan(gpaRequest)).thenReturn(successEntity);
		ResponseEntity<Object> setResponseEntity = gpaController.saveGpa(gpaRequest, httpServletRequest, httpServletResponse);
		HttpStatus httpStatus= setResponseEntity.getStatusCode();
		assertEquals( HttpStatus.UNPROCESSABLE_ENTITY, httpStatus);
		
		
	}
	@Test
	public void setGpaPlanNull() throws SQLException {
		HttpServletRequest httpServletRequest=null;
		HttpServletResponse httpServletResponse=null;
	
		gpaRequest=gpaRequest();
	
		gpaRequest.setGpaPlan(null);
	
		when(gpaFacade.saveGpaPlan(gpaRequest)).thenReturn(successEntity);
		ResponseEntity<Object> setResponseEntity = gpaController.saveGpa(gpaRequest, httpServletRequest, httpServletResponse);
		HttpStatus httpStatus= setResponseEntity.getStatusCode();
		assertEquals( HttpStatus.UNPROCESSABLE_ENTITY, httpStatus);
	}
	@Test
	public void setSaveNull() throws SQLException {
		HttpServletRequest httpServletRequest = null;
		HttpServletResponse httpServletResponse=null;
		gpaRequest =gpaRequest();
		
	//	List<GpaPlan> gpaPlan = new ArrayList<GpaPlan>();
		gpaRequest.setTransactionType("save");
		GpaPlan gpaPlan = new GpaPlan();
		gpaPlan.setGpaPlanType(null);
		
		gpaPlan.setGpaPremium(null);
		gpaPlan.setTotalPremium(null);
		
		listGpaRequest.add(gpaPlan);
		gpaRequest.setGpaPlan(listGpaRequest);
		
		when(gpaFacade.saveGpaPlan(gpaRequest)).thenReturn(successEntity);
		ResponseEntity<Object> setResponseEntity = gpaController.saveGpa(gpaRequest, httpServletRequest, httpServletResponse);
		HttpStatus httpStatus= setResponseEntity.getStatusCode();
		assertEquals( HttpStatus.UNPROCESSABLE_ENTITY, httpStatus);
	}
	
	@Test
	public void setUpdateNull() throws SQLException {
		HttpServletRequest httpServletRequest = null;
		HttpServletResponse httpServletResponse=null;
		gpaRequest =gpaRequest();
		

		gpaRequest.setTransactionType("update");
		GpaPlan gpaPlan = new GpaPlan();
		
	
		
		listGpaRequest.add(gpaPlan);
		gpaRequest.setGpaPlan(listGpaRequest);
		
		when(gpaFacade.saveGpaPlan(gpaRequest)).thenReturn(successEntity);
		ResponseEntity<Object> setResponseEntity = gpaController.saveGpa(gpaRequest, httpServletRequest, httpServletResponse);
		HttpStatus httpStatus= setResponseEntity.getStatusCode();
		assertEquals( HttpStatus.UNPROCESSABLE_ENTITY, httpStatus);
	}
	
	
	///////////////////////set exception block/////////////////////////////////////
	
	@Test
	public void setExeceptionTest() throws SQLException {
	
		HttpServletRequest httpServletRequest=null;
		HttpServletResponse httpServletResponse=null;
		gpaRequest=gpaRequest();
		when(gpaFacade.saveGpaPlan(anyObject())).thenThrow(new RuntimeException());
		ResponseEntity<Object> setResponseEntity = gpaController.saveGpa(gpaRequest(), httpServletRequest, httpServletResponse);
		HttpStatus httpStatus = setResponseEntity.getStatusCode();
		assertEquals( HttpStatus.CONFLICT, httpStatus);
	}
		
	/////////////////get block////////////////////////
	
	
	@Test
	public void getGpaRequestNull() throws SQLException {
		
		gpaRequest =gpaRequest();
	     gpaRequest=null;
		HttpServletRequest httpServletRequest=null;
		HttpServletResponse httpServletResponse=null;
		when(gpaFacade.getAllGpaDetails(gpaRequest)).thenReturn(successEntity);
		
		ResponseEntity<Object> setResponseEntity = gpaController.getgpaDetails(gpaRequest, httpServletRequest, httpServletResponse);
		HttpStatus httpStatus= setResponseEntity.getStatusCode();
		assertEquals( HttpStatus.UNPROCESSABLE_ENTITY, httpStatus);

}
	
	///////////////////get Exception/////////////
	@Test
	public void getExceptionTest() throws SQLException {
		HttpServletRequest httpServletRequest=null;
		HttpServletResponse httpServletResponse=null;
		gpaRequest=gpaRequest();
		when(gpaFacade.getAllGpaDetails(anyObject())).thenThrow(new RuntimeException());
		ResponseEntity<Object> setResponseEntity = gpaController.getgpaDetails(gpaRequest, httpServletRequest, httpServletResponse);
		HttpStatus httpStatus = setResponseEntity.getStatusCode();
		assertEquals( HttpStatus.CONFLICT, httpStatus);
	}

	@Test
	public void getSQLExceprion() throws SQLException {
		HttpServletRequest httpServletRequest=null;
		HttpServletResponse httpServletResponse=null;
		gpaRequest=gpaRequest();
		when(gpaFacade.getAllGpaDetails(anyObject())).thenThrow(new SQLException());
		ResponseEntity<Object> setResponseEntity = gpaController.getgpaDetails(gpaRequest, httpServletRequest, httpServletResponse);
		HttpStatus httpStatus = setResponseEntity.getStatusCode();
		assertEquals( HttpStatus.CONFLICT, httpStatus);
	}

}


