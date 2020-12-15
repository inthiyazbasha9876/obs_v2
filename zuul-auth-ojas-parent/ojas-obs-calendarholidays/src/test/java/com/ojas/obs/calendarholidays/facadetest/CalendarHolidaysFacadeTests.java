package com.ojas.obs.calendarholidays.facadetest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ojas.obs.calendarholidays.facadeimpl.CalendarHolidaysFacadeImpl;
import com.ojas.obs.calendarholidays.model.CalendarHolidays;
import com.ojas.obs.calendarholidays.repository.CalendarHolidaysRepository;
import com.ojas.obs.calendarholidays.request.CalendarHolidaysRequest;
import com.ojas.obs.calendarholidays.response.CalendarHolidaysResponse;
import com.ojas.obs.calendarholidays.response.ErrorResponse;

public class CalendarHolidaysFacadeTests {

	@InjectMocks
	CalendarHolidaysFacadeImpl holidaysfacadeImpl;

	@Mock
	CalendarHolidaysRepository holidaysRepo;

	@Spy
	CalendarHolidaysRequest holidayssreq = new CalendarHolidaysRequest();

	@Spy
	ErrorResponse errorresponse;

	@Spy
	CalendarHolidaysResponse holidaysresponse;

	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorresponse, HttpStatus.UNPROCESSABLE_ENTITY);

	@Spy
	ResponseEntity<Object> conflict = new ResponseEntity<>(errorresponse, HttpStatus.CONFLICT);

	@Spy
	ResponseEntity<Object> successResponse = new ResponseEntity<>(holidaysresponse, HttpStatus.OK);

	@Spy
	CalendarHolidays calendarholidays = new CalendarHolidays();

	@Before
	public void init() throws Exception {
		holidaysfacadeImpl = new CalendarHolidaysFacadeImpl();
		holidaysRepo = mock(CalendarHolidaysRepository.class);
		setCollaborator(holidaysfacadeImpl, "holidaysRepo", holidaysRepo);
	}

	private void setCollaborator(Object object, String name, Object service) throws Exception {
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);
	}

	public List<CalendarHolidays> getHolidaysList() {
		List<CalendarHolidays> holidayslist = new ArrayList<CalendarHolidays>();
		CalendarHolidays calendar = new CalendarHolidays();
		calendar.setHolidayId(1);
		calendar.setHolidayDate("12-12-14");
		calendar.setHolidayName("pongal");
		CalendarHolidays calendar2 = new CalendarHolidays();
		calendar.setHolidayId(2);
		calendar.setHolidayDate("13-10-14");
		calendar.setHolidayName("christmas");
		holidayslist.add(calendar);
		holidayslist.add(calendar2);
		return holidayslist;
	}

	@Test
	public void testSaveError() throws SQLException

	{
		holidayssreq.setTransactionType("save");
        holidayssreq.setHolidayslist(this.getHolidaysList());
		List<CalendarHolidays> holidaysList = new ArrayList<CalendarHolidays>();
		holidaysList.add(calendarholidays);
		holidayssreq.setHolidayslist(holidaysList);
		when(holidaysRepo.save(calendarholidays)).thenReturn(calendarholidays);
		ResponseEntity<Object> saveStatus = holidaysfacadeImpl.saveHolidays(holidayssreq);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void testSavesuccescheck() throws SQLException {

		holidayssreq.setTransactionType("save");
		holidayssreq.setHolidayslist(this.getHolidaysList());
		calendarholidays.setHolidayName("pongal");
		calendarholidays.setHolidayDate("12-12-12");
		List<CalendarHolidays> holidayslist = new ArrayList<CalendarHolidays>();
		holidayslist.add(calendarholidays);
		holidayssreq.setHolidayslist(holidayslist);
		when(holidaysRepo.save(calendarholidays)).thenReturn(calendarholidays);
		ResponseEntity<Object> saveStatus = holidaysfacadeImpl.saveHolidays(holidayssreq);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void testupdatesuccesscheck() throws SQLException {

		holidayssreq.setTransactionType("update");
		holidayssreq.setHolidayslist(this.getHolidaysList());
		calendarholidays.setHolidayName("pongal");
		calendarholidays.setHolidayDate("12-12-12");
		List<CalendarHolidays> calendarlist = new ArrayList<CalendarHolidays>();
		calendarlist.add(calendarholidays);
		holidayssreq.setHolidayslist(calendarlist);
		Integer id = holidayssreq.getHolidayslist().get(0).getHolidayId();
		when(holidaysRepo.getOne(id)).thenReturn(calendarholidays);
		ResponseEntity<Object> saveStatus = holidaysfacadeImpl.saveHolidays(holidayssreq);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void testupdateErrorcheck() throws SQLException {

		holidayssreq.setTransactionType("update");
		holidayssreq.setHolidayslist(this.getHolidaysList());
		calendarholidays.setHolidayId(null);
		calendarholidays.setHolidayName("pongal");
		List<CalendarHolidays> holidayslist = new ArrayList<CalendarHolidays>();
		holidayslist.add(calendarholidays);
		holidayssreq.setHolidayslist(holidayslist);
		Integer id = holidayssreq.getHolidayslist().get(0).getHolidayId();
		when(holidaysRepo.getOne(id)).thenReturn(calendarholidays);
		ResponseEntity<Object> saveStatus = holidaysfacadeImpl.saveHolidays(holidayssreq);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void testdeletesuccesscheck() throws SQLException {
		holidayssreq.setTransactionType("delete");
		holidayssreq.setHolidayslist(this.getHolidaysList());
		calendarholidays.setHolidayId(1);
		calendarholidays.setHolidayName("pongal");
		List<CalendarHolidays> holidayslist = new ArrayList<CalendarHolidays>();
		holidayslist.add(calendarholidays);
		holidayssreq.setHolidayslist(holidayslist);
		Integer id = holidayssreq.getHolidayslist().get(0).getHolidayId();
		when(holidaysRepo.getOne(id)).thenReturn(calendarholidays);
		calendarholidays.setStatus(calendarholidays.getStatus() == null);
		when(holidaysRepo.save(calendarholidays)).thenReturn(calendarholidays);
		ResponseEntity<Object> saveStatus = holidaysfacadeImpl.saveHolidays(holidayssreq);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void testdeleteErrorcheck() throws SQLException {

		holidayssreq.setTransactionType("delete");
		holidayssreq.setHolidayslist(this.getHolidaysList());
		calendarholidays.setHolidayId(null);
		calendarholidays.setHolidayDate(null);
		calendarholidays.setHolidayId(null);
		calendarholidays.setStatus(null);
		List<CalendarHolidays> holidayslist = new ArrayList<CalendarHolidays>();
		holidayslist.add(calendarholidays);
		holidayssreq.setHolidayslist(holidayslist);
		Integer id = holidayssreq.getHolidayslist().get(0).getHolidayId();
		when(holidaysRepo.getOne(id)).thenReturn(calendarholidays);
		calendarholidays.setStatus(calendarholidays.getStatus() != null);
		when(holidaysRepo.save(calendarholidays)).thenReturn(calendarholidays);
		ResponseEntity<Object> saveStatus = holidaysfacadeImpl.saveHolidays(holidayssreq);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void TestElseError() throws SQLException {

		holidayssreq.setTransactionType("ss");
		holidayssreq.setHolidayslist(this.getHolidaysList());
		when(holidaysRepo.save(calendarholidays)).thenReturn(calendarholidays);
		ResponseEntity<Object> saveStatus = holidaysfacadeImpl.saveHolidays(holidayssreq);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}
	// getTestcases

	@Test
	public void getAllSuccess() throws SQLException {

		holidayssreq.setTransactionType("getAll");
		List<CalendarHolidays> holidayslist = new ArrayList<CalendarHolidays>();
		holidayslist.isEmpty();
		holidayssreq.setHolidayslist(holidayslist);
		when(holidaysRepo.findAll()).thenReturn(holidayslist);
		ResponseEntity<Object> saveStatus = holidaysfacadeImpl.getHolidays(holidayssreq);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void getAllError() throws SQLException {
		holidayssreq.setHolidayslist(this.getHolidaysList());
		holidayssreq.setTransactionType("getAll");
		List<CalendarHolidays> holidayslist = new ArrayList<CalendarHolidays>();
		holidayslist.add(calendarholidays);
		holidayssreq.setHolidayslist(holidayslist);
		when(holidaysRepo.findAll()).thenReturn(holidayslist);
		ResponseEntity<Object> saveStatus = holidaysfacadeImpl.getHolidays(holidayssreq);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void getByIdError() throws SQLException {

		holidayssreq.setHolidayslist(this.getHolidaysList());
		calendarholidays.setHolidayId(1);
		List<CalendarHolidays> holidayslist = new ArrayList<CalendarHolidays>();
		holidayslist.add(calendarholidays);
		holidayssreq.setHolidayslist(holidayslist);
		holidayssreq.setTransactionType("getById");
	    holidayslist.get(0).getHolidayId();
		when(holidaysRepo.findAll()).thenReturn(holidayslist);
		ResponseEntity<Object> saveStatus = holidaysfacadeImpl.getHolidays(holidayssreq);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void getByIdSuccess() throws SQLException {

		holidayssreq.setHolidayslist(this.getHolidaysList());
		calendarholidays.setHolidayId(1);
		List<CalendarHolidays> hoilidayslist = new ArrayList<CalendarHolidays>();
		hoilidayslist.add(calendarholidays);
		holidayssreq.setHolidayslist(hoilidayslist);
		holidayssreq.setTransactionType("getById");
		Integer id = hoilidayslist.get(0).getHolidayId();
		when(holidaysRepo.findById(id)).thenReturn(Optional.of(calendarholidays));
		hoilidayslist.add(calendarholidays);
		ResponseEntity<Object> saveStatus = holidaysfacadeImpl.getHolidays(holidayssreq);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}
}
