
package com.ojas.obs.passport.facadeTest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ojas.obs.passport.Request.PassportRequest;
import com.ojas.obs.passport.Response.PassportResponse;
import com.ojas.obs.passport.dao.PassportDao;
import com.ojas.obs.passport.daoImpl.PassportDaoImpl;
import com.ojas.obs.passport.facadeImpl.PassportFacadeImpl;
import com.ojas.obs.passport.model.ErrorResponse;
import com.ojas.obs.passport.model.Passport;



@RunWith(MockitoJUnitRunner.Silent.class)
public class PassportFacadeTest {

	@InjectMocks
	private PassportFacadeImpl passportFacadeImpl;

	@Mock
	PassportDao passportDao;

	@Mock
	PassportDaoImpl passportDaoImpl;

	@Spy
	ErrorResponse errorResponse = new ErrorResponse();

	@Spy
	ResponseEntity<Object> responseEntity = new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);

	@Spy
	ResponseEntity<Object> successEntity = new ResponseEntity<>(HttpStatus.OK);

	@Spy
	PassportRequest passportRequest21 = new PassportRequest();

	@Spy
	PassportResponse passportResponse = new PassportResponse();

	@Spy
	List<PassportResponse> passList = new ArrayList<PassportResponse>();

	@Spy
	List<Passport> passportList = new ArrayList<Passport>();

	@Before
	public void beforeTest() {
		passportFacadeImpl = new PassportFacadeImpl();
		passportDaoImpl = mock(PassportDaoImpl.class);
		setCollaborator(passportFacadeImpl, "passportDaoImpl", passportDaoImpl);
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

		passportRequest21 = new PassportRequest();
		passportRequest21.setPassportList(this.getPassport());
	
		return passportRequest21;
	}

	public List<Passport> getPassport() {
		Passport passport = new Passport();
		passport.setId(1);
		passport.setCenterName("centername1");
		// passport.setCreatedBy(1234); //passport.setUpdatedBy(1234);
		// passport.setFlag(true); 
		Passport passport2 = new Passport();
		passport2.setId(1);
		passport2.setCenterName("centername1");
		// passport2.setCreatedBy(1234); //passport2.setUpdatedBy(1234);
		// passport2.setFlag(true);

		List<Passport> list = new ArrayList<Passport>();
		list.add(passport);
		list.add(passport2);
		return list;
	} // save

	@Test public void setSaveTest() {
	passportRequest21 = new
  PassportRequest(); //
	passportRequest21.setPassportList(this.getPassport());
	//passportRequest2.setPageNo(1);passportRequest2.setPageSize(2);passportRequest2.setSessionId("1234");

	passportRequest21.setTransaactionType("save");try

	{
		when(passportDaoImpl.savePassport(passportRequest21)).thenReturn(true);
		when(passportDaoImpl.getcountPassport(passportRequest21)).thenReturn(2);
		ResponseEntity<Object> setPassport = passportFacadeImpl.setPassport(passportRequest21);
		HttpStatus statusCode = setPassport.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}catch(
	Exception e)
	{
		e.printStackTrace();
	}

	} 
	// save Null //
	@Test
	//ok
	public void setSaveFalseTest() { passportRequest21
	=new PassportRequest();passportRequest21.setPassportList(this.getPassport());
	//passportRequest2.setPageNo(1);passportRequest2.setPageSize(2);passportRequest2.setSessionId("1234");

	 passportRequest21.setTransaactionType("save");
	try {
	when(passportDaoImpl.savePassport(passportRequest21)).thenReturn(false);when(passportDaoImpl.getcountPassport(passportRequest21)).thenReturn(2);
	ResponseEntity<Object> setPassport = passportFacadeImpl.setPassport(passportRequest21);
	HttpStatus statusCode = setPassport.getStatusCode();

	assertEquals(HttpStatus.UNPROCESSABLE_ENTITY,
  statusCode); }catch(

	Exception e)
	{
		e.printStackTrace();
	}

	} // update

	@Test
	public void setUpdateTest() {
		PassportRequest passportRequest2 =
	new PassportRequest();passportRequest2.setPassportList(this.getPassport());
	passportRequest2.setTransaactionType("update");try{when(passportDaoImpl.updatePassport(passportRequest2)).thenReturn(true);when(passportDaoImpl.getcountPassport(passportRequest2)).thenReturn(2);
	ResponseEntity<Object> setPassport =
  passportFacadeImpl.setPassport(passportRequest2);
	HttpStatus statusCode =
  setPassport.getStatusCode();

	assertEquals(HttpStatus.OK, statusCode); }catch(

	Exception e)
	{
		e.printStackTrace();
	}

	} 
	// Update Null //
	/*
	 * @Test public void setUpdatefalseTest() { PassportRequest passportRequest2 =
	 * new PassportRequest(); passportRequest2.setPassportList(this.getPassport());
	 * 
	 * // passportRequest2.setTransaactionType("update"); try {
	 * when(passportDaoImpl.updatePassport(passportRequest2)).thenReturn(false);
	 * when(passportDaoImpl.getcountPassport(passportRequest2)).thenReturn(2);
	 * ResponseEntity<Object> setPassport =
	 * passportFacadeImpl.setPassport(passportRequest2); HttpStatus statusCode =
	 * setPassport.getStatusCode(); assertEquals(HttpStatus.UNPROCESSABLE_ENTITY,
	 * statusCode); }catch(
	 * 
	 * Exception e) { e.printStackTrace(); } }
	 */

	@Test
	public void setUpdateNullIDTest() { 
		passportRequest21 = new
  PassportRequest(); 
		passportRequest21.setPassportList(this.getPassport());
  List<Passport> passport = this.getPassport();
  passport.get(0).setId(null);
  passportRequest21.setTransaactionType("update"); try {
  when(passportDaoImpl.updatePassport(passportRequest21)).thenReturn(false);
  when(passportDaoImpl.getcountPassport(passportRequest21)).thenReturn(2);
  ResponseEntity<Object> setPassport =
  passportFacadeImpl.setPassport(passportRequest21); HttpStatus statusCode =
  setPassport.getStatusCode(); assertEquals(HttpStatus.UNPROCESSABLE_ENTITY,
  statusCode); } catch (Exception e) { e.printStackTrace(); }
  
  } // Delete

	/*
	 * @Test public void setDeleteTrueTest() { passportRequest21 = new
	 * PassportRequest(); passportRequest21.setPassportList(this.getPassport());
	 * passportRequest21.setTransaactionType("delete"); try {
	 * when(passportDaoImpl.deletePassport(passportRequest21)).thenReturn(true);
	 * when(passportDaoImpl.getcountPassport(passportRequest21)).thenReturn(2);
	 * ResponseEntity<Object> setPassport =
	 * passportFacadeImpl.setPassport(passportRequest21); HttpStatus statusCode =
	 * setPassport.getStatusCode(); assertEquals(HttpStatus.OK, statusCode); } catch
	 * (Exception e) { e.printStackTrace(); }
	 * 
	 * }
	 */ // Delete Null ID

	/*
	 * @Test public void setDeleteIdNullTest() { passportRequest21 = new
	 * PassportRequest();
	 * 
	 * List<Passport> passport = this.getPassport(); passport.get(0).setId(null);
	 * 
	 * passportRequest21.setPassportList(passport);
	 * 
	 * passportRequest21.setPassportList(getPassport());
	 * passportRequest21.getPassportList().get(0).setId(null);
	 * passportRequest21.setTransaactionType("delete"); try {
	 * when(passportDaoImpl.deletePassport(passportRequest21)).thenReturn(false);
	 * when(passportDaoImpl.getcountPassport(passportRequest21)).thenReturn(2);
	 * ResponseEntity<Object> setPassport =
	 * passportFacadeImpl.setPassport(passportRequest21); HttpStatus statusCode =
	 * setPassport.getStatusCode(); assertEquals(HttpStatus.UNPROCESSABLE_ENTITY,
	 * statusCode); } catch (Exception e) { e.printStackTrace(); }
	 * 
	 * }
	 */

	// Null transaction

	/*
	 * @Test public void setTransactionNullTest() throws SQLException {
	 * PassportRequest passportRequest2 = new PassportRequest(); List<Passport>
	 * passport = this.getPassport(); passportRequest2.setPassportList(passport);
	 * passportRequest2.setTransaactionType(null);
	 * 
	 * when(passportDaoImpl.savePassport(passportRequest2)).thenReturn(false); //
	 * when(passportDaoImpl.getcountPassport(passportRequest2)).thenReturn(2);
	 * ResponseEntity<Object> setPassport =
	 * passportFacadeImpl.setPassport(passportRequest2); HttpStatus statusCode =
	 * setPassport.getStatusCode(); assertEquals(HttpStatus.UNPROCESSABLE_ENTITY,
	 * statusCode);
	 * 
	 * 
	 * }
	 */

	@Test
	public void getPassportForNoRecordTest() throws SQLException {
		passportRequest21 = new PassportRequest();
		List<Passport> passportlist = new ArrayList<Passport>();
		passportRequest21.setPassportList(getPassport());
	
		passportRequest21.setTransaactionType("getAll");

		when(passportDaoImpl.getAll(passportRequest21)).thenReturn(passportlist);
		when(passportDaoImpl.getcountPassport(passportRequest21)).thenReturn(2);
		ResponseEntity<Object> getPassport = passportFacadeImpl.getPassport(passportRequest21);
		HttpStatus statusCode = getPassport.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);

	}

	@Test
	public void getPassportForRecordTest() {
		passportRequest21 = new PassportRequest();
		passportRequest21.setPassportList(getPassport());
	
		passportRequest21.setTransaactionType("getAll");
		try {
			when(passportDaoImpl.getAll(passportRequest21)).thenReturn(getPassport());
			when(passportDaoImpl.getcountPassport(passportRequest21)).thenReturn(2);
			ResponseEntity<Object> getPassport = passportFacadeImpl.getPassport(passportRequest21);
			HttpStatus statusCode = getPassport.getStatusCode();
			assertEquals(HttpStatus.OK, statusCode);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void getPassportNoPeginationTest() {
		passportRequest21 = new PassportRequest();
		passportRequest21.setPassportList(getPassport());
		
		passportRequest21.setTransaactionType("getAll");
		try {
			when(passportDaoImpl.getAll(passportRequest21)).thenReturn(getPassport());
			when(passportDaoImpl.getcountPassport(passportRequest21)).thenReturn(2);
			ResponseEntity<Object> getPassport = passportFacadeImpl.getPassport(passportRequest21);
			HttpStatus statusCode = getPassport.getStatusCode();
			assertEquals(HttpStatus.OK, statusCode);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void getPassportEmptyTest() {
		passportRequest21 = new PassportRequest();
		passportRequest21.setPassportList(null);
	
		passportRequest21.setTransaactionType("getAll");
		try {
			when(passportDaoImpl.getAll(passportRequest21)).thenReturn(passportList);
			// when(passportDaoImpl.getcountPassport(passportRequest2)).thenReturn(2);
			ResponseEntity<Object> setPassport = passportFacadeImpl.getPassport(passportRequest21);
			HttpStatus statusCode = setPassport.getStatusCode();
			assertEquals(HttpStatus.CONFLICT, statusCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// error //
	

}
