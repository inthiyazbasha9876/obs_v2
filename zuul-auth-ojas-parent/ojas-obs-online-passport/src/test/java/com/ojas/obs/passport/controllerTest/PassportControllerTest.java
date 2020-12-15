
package com.ojas.obs.passport.controllerTest;

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
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ojas.obs.passport.Request.PassportRequest;
import com.ojas.obs.passport.Response.PassportResponse;
import com.ojas.obs.passport.controller.PassportController;
import com.ojas.obs.passport.facadeImpl.PassportFacadeImpl;
import com.ojas.obs.passport.model.ErrorResponse;
import com.ojas.obs.passport.model.Passport;



@RunWith(MockitoJUnitRunner.Silent.class)
public class PassportControllerTest {

	@InjectMocks
	private PassportController passportController;

	@Mock
	PassportFacadeImpl passportFacadeImpl;

	@Mock
	DuplicateKeyException duplicateKeyException;

	@Spy
	ErrorResponse errorResponse = new ErrorResponse();

	@Spy
	ResponseEntity<Object> responseEntity = new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);

	@Spy
	ResponseEntity<Object> successEntity = new ResponseEntity<>(HttpStatus.OK);

	@Spy
	PassportRequest passportRequest2 = new PassportRequest();

	@Spy
	PassportResponse passportResponse = new PassportResponse();

	@Spy
	List<PassportResponse> passList = new ArrayList<PassportResponse>();

	@Before
	public void beforeTest() {
		passportController = new PassportController();
		passportFacadeImpl = mock(PassportFacadeImpl.class);
		duplicateKeyException = mock(DuplicateKeyException.class);
		setCollaborator(passportController, "passportFacadeImpl", passportFacadeImpl);
	}

	private void setCollaborator(Object object, String name, Object service) {

		Field field;
		try {
			field = object.getClass().getDeclaredField(name);
			field.setAccessible(true);

			field.set(object, service);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public PassportRequest passportRequest() {

		passportRequest2 = new PassportRequest();
		passportRequest2.setPassportList(this.getPassport());
		/*
		 * passportRequest2.setPageNo(1); passportRequest2.setPageSize(2);
		 * passportRequest2.setSessionId("1234");
		 */
		return passportRequest2;
	}

	public List<Passport> getPassport() {
		Passport passport = new Passport();
		passport.setId(1);
		passport.setCenterName("centername1");
		passport.setCenterName("centername1"); // passport.setCreatedBy(1234);
		// passport.setUpdatedBy(1234); //passport.setFlag(true);

		Passport passport2 = new Passport();
		passport2.setId(1);
		passport2.setCenterName("centername1"); // passport2.setCreatedBy(1234);
		// passport2.setUpdatedBy(1234); //passport2.setFlag(true);

		List<Passport> list = new ArrayList<Passport>();
		list.add(passport);
		list.add(passport2);
		return list;
	}

	@Test
	public void setTest() {
		PassportRequest passportRequest2 = new PassportRequest();
		passportRequest2.setPassportList(this.getPassport());
		/*
		 * passportRequest2.setPageNo(1); passportRequest2.setPageSize(2);
		 * passportRequest2.setSessionId("1234");
		 */

		passportRequest2.setTransaactionType("save");
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		try {
			when(passportFacadeImpl.setPassport(passportRequest2)).thenReturn(successEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ResponseEntity<Object> setpassport = passportController.setPassport(passportRequest2, request, response);
		HttpStatus statusCode = setpassport.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void setNullTest() {
		PassportRequest passportRequest2 = new PassportRequest();
		// passportRequest2.setPassportList(this.getPassport());
		/*
		 * passportRequest2.setPageNo(1); passportRequest2.setPageSize(2);
		 * passportRequest2.setSessionId("1234");
		 */

		passportRequest2.setTransaactionType("save");
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		try {
			when(passportFacadeImpl.setPassport(passportRequest2)).thenReturn(successEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ResponseEntity<Object> setpassport = passportController.setPassport(passportRequest2, request, response);
		HttpStatus statusCode = setpassport.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	// Null Transaction

	@Test
	public void setTestForNullTransaction() {
		passportRequest2 = new PassportRequest();
		passportRequest2.setPassportList(this.getPassport());
		/*
		 * passportRequest2.setPageNo(1); passportRequest2.setPageSize(2);
		 * passportRequest2.setSessionId("1234");
		 */

		passportRequest2.setTransaactionType(null);
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		try {
			when(passportFacadeImpl.setPassport(passportRequest2)).thenReturn(successEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ResponseEntity<Object> setpassport = passportController.setPassport(passportRequest2, request, response);
		HttpStatus statusCode = setpassport.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setCenterNameNullTest() {
		passportRequest2 = new PassportRequest();
		passportRequest2.setPassportList(this.getPassport());
		passportRequest2.getPassportList().get(1).setCenterName(null);
		/*
		 * passportRequest2.setPageNo(1); passportRequest2.setPageSize(2);
		 * passportRequest2.setSessionId("1234");
		 */

		passportRequest2.setTransaactionType("save");
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		try {
			when(passportFacadeImpl.setPassport(passportRequest2)).thenReturn(successEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ResponseEntity<Object> setpassport = passportController.setPassport(passportRequest2, request, response);
		HttpStatus statusCode = setpassport.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	// UPDATE Null Center Name

	@Test
	public void setupdCenterNameNullTest() {
		passportRequest2 = new PassportRequest();
		passportRequest2.setPassportList(this.getPassport());
		passportRequest2.getPassportList().get(1).setCenterName(null);
		/*
		 * passportRequest2.setPageNo(1); passportRequest2.setPageSize(2);
		 * passportRequest2.setSessionId("1234");
		 */

		passportRequest2.setTransaactionType("update");
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		try {
			when(passportFacadeImpl.setPassport(passportRequest2)).thenReturn(successEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ResponseEntity<Object> setpassport = passportController.setPassport(passportRequest2, request, response);
		HttpStatus statusCode = setpassport.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	/*
	 * @Test public void setupdCenterExceptionTest() throws SQLException {
	 * PassportRequest passportRequest2 = new PassportRequest();
	 * passportRequest2.setPassportList(this.getPassport());
	 * 
	 * passportRequest2.setPageNo(1); passportRequest2.setPageSize(2);
	 * passportRequest2.setSessionId("1234");
	 * 
	 * 
	 * passportRequest2.setTransaactionType("save"); HttpServletRequest request =
	 * null; HttpServletResponse response = null;
	 * 
	 * when(passportFacadeImpl.setPassport(passportRequest2)).thenThrow(Exception.
	 * class);
	 * 
	 * ResponseEntity<Object> setpassport =
	 * passportController.setPassport(passportRequest2, request, response);
	 * HttpStatus statusCode = setpassport.getStatusCode();
	 * assertEquals(HttpStatus.CONFLICT, statusCode); }
	 */
	@Test
	public void getupdCenterExceptionTest() throws SQLException {
		PassportRequest passportRequest2 = new PassportRequest();
		passportRequest2.setPassportList(this.getPassport());
		/*
		 * passportRequest2.setPageNo(1); passportRequest2.setPageSize(2);
		 * passportRequest2.setSessionId("1234");
		 */

		passportRequest2.setTransaactionType("save");
		HttpServletRequest request = null;
		HttpServletResponse response = null;

		when(passportFacadeImpl.getPassport(passportRequest2)).thenThrow(SQLException.class);

		ResponseEntity<Object> setpassport = passportController.getPaasport(passportRequest2, request, response);
		HttpStatus statusCode = setpassport.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	public List<Passport> getPassport1() {
		List<Passport> list = new ArrayList<Passport>();
		return list;
	}

	// test cases for get

	@Test
	public void getTest() throws Exception {
		passportRequest2 = new PassportRequest();
		passportRequest2.setPassportList(this.getPassport1());
		/*
		 * passportRequest2.setPageNo(1); passportRequest2.setPageSize(2);
		 * passportRequest2.setSessionId("1234");
		 */

		passportRequest2.setTransaactionType("getAll");
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		try {
			when(passportFacadeImpl.getPassport(passportRequest2)).thenReturn(successEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ResponseEntity<Object> getpassport = passportController.getPaasport(passportRequest2, request, response);
		assertEquals(HttpStatus.OK, getpassport.getStatusCode());
	}

	@Test
	public void getNullTest() throws Exception {
		passportRequest2 = new PassportRequest(); // passportRequest2.setPassportList(this.getPassport1());
		/*
		 * passportRequest2.setPageNo(1); passportRequest2.setPageSize(2);
		 * passportRequest2.setSessionId("1234");
		 */

		passportRequest2.setTransaactionType("getAll");
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		try {
			when(passportFacadeImpl.getPassport(passportRequest2)).thenReturn(successEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ResponseEntity<Object> getpassport = passportController.getPaasport(passportRequest2, request, response);
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, getpassport.getStatusCode());
	}

	@Test
	public void getRequestNullTest() throws Exception {
		passportRequest2 = null;
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		try {
			when(passportFacadeImpl.getPassport(passportRequest2)).thenReturn(successEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ResponseEntity<Object> getpassport = passportController.getPaasport(passportRequest2, request, response);
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, getpassport.getStatusCode());
	}

	@Test
	public void getTransactionTypeNullTest() throws Exception {
		passportRequest2 = new PassportRequest();
		passportRequest2.setPassportList(this.getPassport1());
		/*
		 * passportRequest2.setPageNo(1); passportRequest2.setPageSize(2);
		 * passportRequest2.setSessionId("1234");
		 */
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		try {
			when(passportFacadeImpl.getPassport(passportRequest2)).thenReturn(successEntity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ResponseEntity<Object> getpassport = passportController.getPaasport(passportRequest2, request, response);
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, getpassport.getStatusCode());
	}

	
	  @Test public void DuplExcpTest() throws DuplicateKeyException {
	  passportRequest2 = this.passportRequest();
	  passportRequest2.setPassportList(this.getPassport());
	  passportRequest2.getPassportList().get(0).setCenterName("centername");
	  passportRequest2.getPassportList().get(1).setCenterName("centername");
	  passportRequest2.setTransaactionType("save"); HttpServletRequest request
	  =null; HttpServletResponse response = null; try { Throwable cause = new
	  Throwable();
	  when(passportFacadeImpl.setPassport(passportRequest2)).thenThrow(new
	  DuplicateKeyException(null, cause)); } catch (Exception e) { // TODO
	  e.printStackTrace(); } ResponseEntity<Object> setPassport =
	  passportController.setPassport(passportRequest2, request, response);
	  System.out.println(setPassport); HttpStatus statusCode =
	  setPassport.getStatusCode(); System.out.println(statusCode);
	  assertEquals(HttpStatus.CONFLICT, setPassport.getStatusCode()); }
	 

	@Test
	public void PassportSQLExcpTest() throws SQLException {
		passportRequest2 = this.passportRequest();
		passportRequest2.setTransaactionType("save");
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		try {
			when(passportFacadeImpl.setPassport(passportRequest2)).thenThrow(SQLException.class);
		} catch (Exception e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResponseEntity<Object> setPassport = passportController.setPassport(passportRequest2, request, response);
		System.out.println(setPassport);
		HttpStatus statusCode = setPassport.getStatusCode();
		System.out.println(statusCode);
		assertEquals(HttpStatus.CONFLICT, setPassport.getStatusCode());
	}

	
	/*
	 * @Test public void PassportExcpTest() { passportRequest2=new
	 * PassportRequest(); passportRequest2.setPassportList(this.getPassport());
	 * passportRequest2.setTransaactionType("save"); HttpServletRequest request =
	 * null; HttpServletResponse response = null; try {
	 * when(passportFacadeImpl.setPassport(passportRequest2)).thenThrow(Exception.
	 * class); } catch (Exception e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } System.out.println(passportRequest2+"jjj");
	 * ResponseEntity<Object> setPassport =null; try {
	 * 
	 * 
	 * setPassport = passportController.setPassport(passportRequest2, request,
	 * response); System.out.println(setPassport+"jjjjj"); }catch (Exception e) { //
	 * // TODO Auto-generated
	 * 
	 * e.printStackTrace(); }
	 * 
	 * 
	 * HttpStatus statusCode = setPassport.getStatusCode();
	 * System.out.println(statusCode); assertEquals(HttpStatus.CONFLICT,
	 * setPassport.getStatusCode()); }
	 */
	 
	
	  @Test public void setExceptionPassport() throws SQLException {
	  HttpServletRequest request = null; HttpServletResponse response = null;
	  passportRequest2 = this.passportRequest();
	  passportRequest2.setTransaactionType("delete");
	  when(passportFacadeImpl.setPassport(passportRequest2)).thenThrow(new
	  RuntimeException()); ResponseEntity<Object> setPassport =
	  passportController.setPassport(passportRequest2, request, response);
	  HttpStatus statusCode = setPassport.getStatusCode();
	  
	  assertEquals(HttpStatus.CONFLICT, statusCode);
	  
	  }
	 
	
	  @Test public void setExceptionPassportForGet() throws SQLException {
	  HttpServletRequest request = null; HttpServletResponse response = null;
	  passportRequest2 = this.passportRequest();
	  passportRequest2.setTransaactionType("delete");
	  when(passportFacadeImpl.getPassport(passportRequest2)).thenThrow(new
	  RuntimeException()); ResponseEntity<Object> setPassport =
	  passportController.getPaasport(passportRequest2, request, response);
	  HttpStatus statusCode = setPassport.getStatusCode();
	  
	  assertEquals(HttpStatus.CONFLICT, statusCode);
	  
	  }
	 
}
