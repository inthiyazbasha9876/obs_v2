package com.ojas.obs.calendarholidays.controllertest;

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

import com.ojas.obs.calendarholidays.controller.CalendarHolidaysController;
import com.ojas.obs.calendarholidays.facade.CalendarHolidaysFacade;
import com.ojas.obs.calendarholidays.facadeimpl.CalendarHolidaysFacadeImpl;
import com.ojas.obs.calendarholidays.model.CalendarHolidays;
import com.ojas.obs.calendarholidays.request.CalendarHolidaysRequest;
import com.ojas.obs.calendarholidays.response.CalendarHolidaysResponse;
import com.ojas.obs.calendarholidays.response.ErrorResponse;

public class CalendarHolidaysControllerTests {
	
	@InjectMocks
	CalendarHolidaysController holidaysController;
	
	@Mock
	CalendarHolidaysFacadeImpl holidaysfacadeImpl;
	
	@Mock
	CalendarHolidaysFacade holidaysfacade;
	@Spy
	CalendarHolidaysRequest holidaysrequest=new CalendarHolidaysRequest();
	
	@Spy
	CalendarHolidaysResponse holidayresponse;
	
	@Spy
	ErrorResponse errorresponse;
	
	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorresponse, HttpStatus.UNPROCESSABLE_ENTITY);
	
	@Spy
	ResponseEntity<Object> conflict = new ResponseEntity<>(errorresponse, HttpStatus.CONFLICT);
	
	@Spy
	ResponseEntity<Object> successResponse = new ResponseEntity<>(holidayresponse, HttpStatus.OK);
	
	@Spy
	CalendarHolidays holidays=new CalendarHolidays();
	
	@Before
	public void init() throws Exception 
	{
		holidaysController=new CalendarHolidaysController();
		holidaysfacadeImpl = mock(CalendarHolidaysFacadeImpl.class);
		setCollaborator(holidaysController, "holidaysfacadeImpl", holidaysfacadeImpl);
	}
	private void setCollaborator(Object object, String name, Object service) throws Exception 
	{
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);
	}
	public List<CalendarHolidays> getHolidaysList() {
		List<CalendarHolidays> holidayslist = new ArrayList<CalendarHolidays>();
		CalendarHolidays calendar = new CalendarHolidays();
		calendar.setHolidayId(1);
		calendar.setHolidayDate("12-12-2020");
		calendar.setHolidayName("new year");
		
		CalendarHolidays calendar1 = new CalendarHolidays();
		calendar1.setHolidayId(2);
		calendar1.setHolidayDate("13-01-2010");
		calendar.setHolidayName("pongal");
		holidayslist.add(calendar);
		holidayslist.add(calendar1);
		return holidayslist;
	}
	
	@Test
	public void calendarHolidaysRequestNullTest() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	
    	holidaysrequest.setHolidayslist(this.getHolidaysList());
    	holidaysrequest.setTransactionType(null);
		when(holidaysfacadeImpl.saveHolidays(holidaysrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = holidaysController.saveHolidays(holidaysrequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	
	

	@Test
	public void holidaysaveTest() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	List<CalendarHolidays>list=new ArrayList<>();
    	holidays.setHolidayName("");
    	holidays.setHolidayDate("");
    	list.add(holidays);
    	holidaysrequest.setTransactionType("save");
    	holidaysrequest.setHolidayslist(list);
		when(holidaysfacadeImpl.saveHolidays(holidaysrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = holidaysController.saveHolidays(holidaysrequest, request, response);
		HttpStatus status = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}
	
	@Test
	public void holidayrequestupdateTest() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
     	
     	holidaysrequest.setHolidayslist(this.getHolidaysList());
     	holidaysrequest.setTransactionType("update");
		when(holidaysfacadeImpl.saveHolidays(holidaysrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setBus = holidaysController.saveHolidays(holidaysrequest, request, response);
		HttpStatus statusCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	@Test
	public void holidayrequesttdeleteTest() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
   	
    	holidaysrequest.setHolidayslist(this.getHolidaysList());
    	holidaysrequest.setTransactionType("delete");
		when(holidaysfacadeImpl.saveHolidays(holidaysrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setBus = holidaysController.saveHolidays(holidaysrequest, request, response);
		HttpStatus statusCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	@Test
	public void setDuplicateKeyExceptionTest() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    		
    	holidays.setHolidayDate("12-12-12");
    	holidays.setHolidayName("any cato");
    	holidays.setStatus(true);
    	
    	List<CalendarHolidays> holidaylist  = new ArrayList<CalendarHolidays>();
    	holidaylist.add(holidays);
    	holidaysrequest.setHolidayslist(holidaylist);
    	holidaysrequest.setTransactionType("save");	
    	
    	when(holidaysfacadeImpl.saveHolidays(holidaysrequest)).thenThrow(new DuplicateKeyException(null,new Throwable()));   
		ResponseEntity<Object> setBus = holidaysController.saveHolidays(holidaysrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}
	@Test
	public void setExceptionTest() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    		
    	holidays.setHolidayDate("12-12-12");
    	holidays.setHolidayName("any cato");
    	holidays.setStatus(false);
    	
    	List<CalendarHolidays> holidaylist  = new ArrayList<CalendarHolidays>();
    	holidaylist.add(holidays);
    	holidaysrequest.setHolidayslist(holidaylist);
    	holidaysrequest.setTransactionType("save");	
    	when(holidaysfacadeImpl.saveHolidays(holidaysrequest)).thenThrow(RuntimeException.class);
    
		ResponseEntity<Object> setBus = holidaysController.saveHolidays(holidaysrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}
	@Test
	public void setsavesucces() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    		
    	holidays.setHolidayDate("12-12-12");
    	holidays.setHolidayName("any cato");
    	holidays.setStatus(false);
    	
    	List<CalendarHolidays>holidaylist  = new ArrayList<CalendarHolidays>();
    	holidaylist.add(holidays);
    	holidaysrequest.setHolidayslist(holidaylist);
    	holidaysrequest.setTransactionType("save");		
    	when(holidaysfacadeImpl.saveHolidays(holidaysrequest)).thenReturn(successResponse);
    
		ResponseEntity<Object> setBus = holidaysController.saveHolidays(holidaysrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.OK, unitCode);
	}
	@Test
	public void setupdatesucces() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
		
    	holidays.setHolidayDate("12-12-12");
    	holidays.setHolidayName("any cato");
    	holidays.setStatus(false);
    	
    	List<CalendarHolidays> holidaylist  = new ArrayList<CalendarHolidays>();
    	holidaylist.add(holidays);
    	holidaysrequest.setHolidayslist(holidaylist);
    	holidaysrequest.setTransactionType("update");	
    	when(holidaysfacadeImpl.saveHolidays(holidaysrequest)).thenReturn(successResponse);
    
		ResponseEntity<Object> setBus = holidaysController.saveHolidays(holidaysrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	@Test
	public void setdeletesucces() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
		
    		
    	CalendarHolidays holiday = new CalendarHolidays();
    	holiday.setHolidayDate("12-12-12");
    	holiday.setHolidayName("any cato");
    	holiday.setStatus(false);
    	
    	
    	List<CalendarHolidays> holidaylist  = new ArrayList<CalendarHolidays>();
    	holidaylist.add(holiday);
    	holidaysrequest.setHolidayslist(holidaylist);
    	holidaysrequest.setTransactionType("delete");	
    	when(holidaysfacadeImpl.saveHolidays(holidaysrequest)).thenReturn(successResponse);
    
		ResponseEntity<Object> setBus = holidaysController.saveHolidays(holidaysrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	//getTestcases
	
			@Test
			public void getTransactionEmpty() throws SQLException {
				HttpServletRequest request = null;
		    	HttpServletResponse response = null;
		    	holidaysrequest.setHolidayslist(this.getHolidaysList());
		    	holidaysrequest.setTransactionType(null);
				when(holidaysfacadeImpl.saveHolidays(holidaysrequest)).thenReturn(failureResponse);
				ResponseEntity<Object> setservice = holidaysController.getHolidays(holidaysrequest, request, response);
				HttpStatus unitCode = setservice.getStatusCode();
				assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
			}
			
			@Test
			public void getByIdsuccesscheck() throws SQLException {
				HttpServletRequest request = null;
		    	HttpServletResponse response = null;
		    
		    	holidaysrequest.setHolidayslist(this.getHolidaysList());
		    	holidaysrequest.setTransactionType("getById");
				
		    	holidaysrequest.getHolidayslist().get(0).getHolidayId();
				
				when(holidaysfacadeImpl.getHolidays(holidaysrequest)).thenReturn(successResponse);
				ResponseEntity<Object> setservice = holidaysController.getHolidays(holidaysrequest, request, response);
				HttpStatus unitCode = setservice.getStatusCode();
				assertEquals(HttpStatus.OK, unitCode);
			}
			@Test
			public void getByIdcheck() throws SQLException {
				HttpServletRequest request = null;
		    	HttpServletResponse response = null;
		    
		    	holidaysrequest.setHolidayslist(this.getHolidaysList());
		    	holidaysrequest.setTransactionType("getById");
				
		    	holidaysrequest.getHolidayslist().get(0).setHolidayId(null);
				
				when(holidaysfacadeImpl.getHolidays(holidaysrequest)).thenReturn(failureResponse);
				ResponseEntity<Object> setservice = holidaysController.getHolidays(holidaysrequest, request, response);
				HttpStatus unitCode = setservice.getStatusCode();
				assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
			}
			
			@Test
			public void getExceptionTest() throws Exception {
				HttpServletRequest request = null;
		    	HttpServletResponse response = null;
			    
		    	holidaysrequest.setHolidayslist(this.getHolidaysList());
		    	holidaysrequest.setTransactionType("getAll");	
		    	when(holidaysfacadeImpl.getHolidays(holidaysrequest)).thenThrow(RuntimeException.class);
		    
				ResponseEntity<Object> setBus = holidaysController.getHolidays(holidaysrequest, request, response);
				HttpStatus unitCode = setBus.getStatusCode();
				assertEquals(HttpStatus.CONFLICT, unitCode);
			}
	}
