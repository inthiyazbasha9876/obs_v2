
package com.ojas.obs.ControllerTest;

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
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ojas.obs.controller.GenderController;
import com.ojas.obs.facade.GenderFacadeImpl;
import com.ojas.obs.model.ErrorResponse;
import com.ojas.obs.model.Genders;
import com.ojas.obs.request.GenderRequest;
import com.ojas.obs.response.GenderResponse;

public class GendersControllerTest {

	@InjectMocks
	GenderController genderController;

	@Mock
	GenderFacadeImpl genderFacade;

	@Spy
	ErrorResponse errorResponse = new ErrorResponse();

	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);

	@Spy
	ResponseEntity<Object> conflict = new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);

	@Spy
	ResponseEntity<Object> sucessResponse = new ResponseEntity<>(errorResponse, HttpStatus.OK);

	@Spy
	GenderRequest genderRequest = new GenderRequest();

	@Spy
	GenderResponse genderResponse = new GenderResponse();

	@Before
	public void init() {
		genderController = new GenderController();
		genderFacade = mock(GenderFacadeImpl.class);
		setCollaborator(genderController, "genderFacadeImpl", genderFacade);
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

	public List<Genders> getModel() {
		List<Genders> list = new ArrayList<Genders>();
		Genders model = new Genders();
		model.setGender("male");
		list.add(model);
		return list;
	} //

	@Test
	public void requestObjectNullCheck() throws SQLException {
		genderRequest = new GenderRequest();
		genderRequest.setGender(getModel());
		genderRequest.setTransactionType("save");
		when(genderFacade.setGender(genderRequest)).thenReturn(sucessResponse);
		ResponseEntity<Object> contResponse = genderController.setGenders(genderRequest);
		HttpStatus statusCode = contResponse.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	//
	@Test
	public void requestObjectNullPosiCheck() throws SQLException {
	genderRequest=null;
	when(genderFacade.setGender(genderRequest)).thenReturn(failureResponse);
    ResponseEntity<Object> contResponse =
    genderController.setGenders(genderRequest); HttpStatus statusCode =
    contResponse.getStatusCode(); assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
  }

	//
	@Test 
	public void genderFieldsNullCheck() throws SQLException {
		genderRequest=new GenderRequest();
		genderRequest.setGender(getModel());
	    genderRequest.getGender().get(0).setGender(null);
	    genderRequest.setTransactionType("save");
	    when(genderFacade.setGender(genderRequest)).thenReturn(failureResponse);
	    ResponseEntity<Object> contResponse =
	     genderController.setGenders(genderRequest); HttpStatus statusCode =contResponse.getStatusCode(); assertEquals(HttpStatus.UNPROCESSABLE_ENTITY,statusCode);
     } 
	//
	@Test 
	public void transactionTypeNullCheck() throws SQLException {
		genderRequest = new GenderRequest();
		genderRequest.setGender(getModel());
		genderRequest.setTransactionType(null);
		when(genderFacade.setGender(genderRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> contResponse = genderController.setGenders(genderRequest);
		HttpStatus statusCode = contResponse.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	} //
	
	@Test 
	public void transactionTypeUpadteNullCheck() throws SQLException {
		genderRequest = new GenderRequest();
		genderRequest.setGender(getModel());
		genderRequest.setTransactionType("update");
		when(genderFacade.setGender(genderRequest)).thenReturn(failureResponse);
		ResponseEntity<Object> contResponse = genderController.setGenders(genderRequest);
		HttpStatus statusCode = contResponse.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	} 
	@Test 
	public void genderSQLExcpTest() throws SQLException {
	genderRequest=new GenderRequest();genderRequest.setGender(getModel());
    genderRequest.getGender().get(0).setId(123);
    genderRequest.setTransactionType("update"); 
    try {
       when(genderFacade.setGender(genderRequest)).thenThrow(SQLException.class); }
  catch (Exception e) { } 
  ResponseEntity<Object> contResponse =
  genderController.setGenders(genderRequest); HttpStatus statusCode =
  contResponse.getStatusCode(); assertEquals(HttpStatus.CONFLICT, statusCode);
  }
	@Test 
	public void genderDuplicateExcpTest() throws SQLException {
	genderRequest=new GenderRequest();genderRequest.setGender(getModel());
    genderRequest.getGender().get(0).setId(123);
    genderRequest.setTransactionType("update"); 
    try {
       when(genderFacade.setGender(genderRequest)).thenThrow(new DuplicateKeyException(null, new Throwable())); 
    }catch (Exception e) { } 
  ResponseEntity<Object> contResponse =
  genderController.setGenders(genderRequest); HttpStatus statusCode =
  contResponse.getStatusCode(); assertEquals(HttpStatus.CONFLICT, statusCode);
  }

	 @Test 
   public void dplicateKeySQLExcpTest() throws SQLException {
			genderRequest=new GenderRequest();genderRequest.setGender(getModel());
		   genderRequest.getGender().get(0).setId(123);
		  genderRequest.setTransactionType("update");
		  try { 
			  Throwable cause= new Throwable();
			  when(genderFacade.setGender(genderRequest)).thenThrow(new DuplicateKeyException(null, cause)); 
		  } catch (Exception e) { }
		  	ResponseEntity<Object>contResponse = genderController.setGenders(genderRequest);
		  	HttpStatus statusCode = contResponse.getStatusCode(); assertEquals(HttpStatus.CONFLICT,statusCode);
		  }

		@Test
		public void ExceptionTest() throws SQLException {
			genderRequest = new GenderRequest();
			genderRequest.setGender(getModel());
			genderRequest.getGender().get(0).setId(123);
			genderRequest.setTransactionType("update");
			try {
				when(genderFacade.setGender(genderRequest)).thenThrow(new RuntimeException());

			} catch (Exception e) {
				e.printStackTrace();
			}
			ResponseEntity<Object> contResponse = genderController.setGenders(genderRequest);
			HttpStatus statusCode = contResponse.getStatusCode();
			assertEquals(HttpStatus.CONFLICT, statusCode);
		}
	// 
	@Test 
	public void requestObjectNullCheckForGet() throws SQLException {
	 genderRequest=null;
     when(genderFacade.setGender(genderRequest)).thenReturn(failureResponse);
     ResponseEntity<Object> contResponse = genderController.getGenders(genderRequest);
     HttpStatus statusCode=contResponse.getStatusCode(); assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode); 
  } //
	@Test 
	public void requestObjectNullCheckPosForGet() throws SQLException {
	 genderRequest=new GenderRequest();
	 genderRequest.setGender(getModel());
	 genderRequest.setTransactionType("getall");
     when(genderFacade.getGender(genderRequest)).thenReturn(sucessResponse);
     ResponseEntity<Object> contResponse = genderController.getGenders(genderRequest);
     HttpStatus statusCode=contResponse.getStatusCode(); 
     assertEquals(HttpStatus.OK, statusCode); 
  } 
	
	@Test 
	public void genderSQLExcpTestForGet() throws SQLException {
		genderRequest = new GenderRequest();
		genderRequest.setGender(getModel());
		genderRequest.getGender().get(0).setId(123);
		genderRequest.setTransactionType("update");
		try {
			when(genderFacade.getGender(genderRequest)).thenThrow(SQLException.class);
		} catch (Exception e) { }
		ResponseEntity<Object> contResponse = genderController.getGenders(genderRequest);
		HttpStatus statusCode = contResponse.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}


}
