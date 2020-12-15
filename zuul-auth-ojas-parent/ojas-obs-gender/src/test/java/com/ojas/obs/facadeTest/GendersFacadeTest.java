
  package com.ojas.obs.facadeTest;

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

import com.ojas.obs.dao.GenderDAOImpl;
import com.ojas.obs.facade.GenderFacadeImpl;
import com.ojas.obs.model.ErrorResponse;
import com.ojas.obs.model.Genders;
import com.ojas.obs.request.GenderRequest;
import com.ojas.obs.response.GenderResponse;

  public class GendersFacadeTest {
  
  @InjectMocks GenderFacadeImpl genderFacadeImpl;
  
  @Mock GenderDAOImpl genderDAOImpl;
  
  @Spy ErrorResponse errorResponse = new ErrorResponse();
  
  @Spy ResponseEntity<Object> failureResponse = new
  ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
  
  @Spy ResponseEntity<Object> conflict = new
  ResponseEntity<>(errorResponse,HttpStatus.CONFLICT);
  
  @Spy ResponseEntity<Object> sucessResponse = new
  ResponseEntity<>(errorResponse, HttpStatus.OK);
  
  @Spy GenderRequest genderRequest = new GenderRequest();
  
  @Spy GenderResponse genderResponse = new GenderResponse();
  
  
  @Before
  public void init() { 
	  genderFacadeImpl= new GenderFacadeImpl();
  genderDAOImpl= mock(GenderDAOImpl.class);
  setCollaborator(genderFacadeImpl,"genderDAOImpl",genderDAOImpl); } private
  void setCollaborator(Object object, String name, Object service) { Field
  field; try { field = object.getClass().getDeclaredField(name);
  field.setAccessible(true); field.set(object, service); } catch (Exception e)
  { e.printStackTrace(); } } public List<Genders> getModel() { List<Genders>
  list = new ArrayList<Genders>(); Genders model= new Genders();
  model.setGender("male"); list.add(model); return list; 
  }
  
  @Test 
  public void saveTest() throws SQLException { 
	  genderRequest = new
  GenderRequest(); genderRequest.setGender(getModel());
  genderRequest.setTransactionType("save");
  when(genderDAOImpl.saveGender(genderRequest)).thenReturn(true);
  when(genderDAOImpl.getAllCount(genderRequest)).thenReturn(4);
  ResponseEntity<Object> saveGender=genderFacadeImpl.setGender(genderRequest);
  HttpStatus statusCode = saveGender.getStatusCode();
  assertEquals(HttpStatus.OK, statusCode);
  
  } @Test public void saveNegativeTest() throws SQLException { 
	  genderRequest
  = new GenderRequest(); genderRequest.setGender(getModel());
  genderRequest.setTransactionType("save");
  when(genderDAOImpl.saveGender(genderRequest)).thenReturn(false);
  when(genderDAOImpl.getAllCount(genderRequest)).thenReturn(4);
  ResponseEntity<Object> saveGender=genderFacadeImpl.setGender(genderRequest);
  HttpStatus statusCode = saveGender.getStatusCode();
  assertEquals(HttpStatus.CONFLICT, statusCode);
  
  } @Test public void updateTest() throws SQLException { 
genderRequest = new
  GenderRequest(); genderRequest.setGender(getModel());
  genderRequest.setTransactionType("update");
  when(genderDAOImpl.updateGender(genderRequest)).thenReturn(true);
  when(genderDAOImpl.getAllCount(genderRequest)).thenReturn(4);
  ResponseEntity<Object>
  updateGender=genderFacadeImpl.setGender(genderRequest); HttpStatus statusCode
  = updateGender.getStatusCode(); assertEquals(HttpStatus.OK, statusCode);
  
  } //
  @Test 
  public void updateNegativeTest() throws SQLException {
  genderRequest = new GenderRequest(); genderRequest.setGender(getModel());
  genderRequest.setTransactionType("update");
  when(genderDAOImpl.updateGender(genderRequest)).thenReturn(false);
  when(genderDAOImpl.getAllCount(genderRequest)).thenReturn(4);
  ResponseEntity<Object>
  updateGender=genderFacadeImpl.setGender(genderRequest); HttpStatus statusCode
  = updateGender.getStatusCode(); assertEquals(HttpStatus.CONFLICT,
  statusCode);
  
  }
  @Test public void transactionTypeTest() throws SQLException {
	  genderRequest =
  new GenderRequest(); genderRequest.setGender(getModel());
  genderRequest.setTransactionType("dfdhfgh");
  when(genderDAOImpl.saveGender(genderRequest)).thenReturn(false);
  when(genderDAOImpl.getAllCount(genderRequest)).thenReturn(4);
  ResponseEntity<Object>
  deleteGender=genderFacadeImpl.setGender(genderRequest); HttpStatus statusCode
  = deleteGender.getStatusCode(); assertEquals(HttpStatus.CONFLICT,
  statusCode);
  
  }
  
  @Test 
  public void getAllTest() throws SQLException { 
	  genderRequest = new
  GenderRequest(); genderRequest.setGender(getModel());
  genderRequest.setTransactionType("dfdhfgh");
  when(genderDAOImpl.getAll(genderRequest)).thenReturn(getModel());
  when(genderDAOImpl.getAllCount(genderRequest)).thenReturn(4);
  ResponseEntity<Object>
  getAllGender=genderFacadeImpl.getGender(genderRequest); HttpStatus statusCode
  = getAllGender.getStatusCode(); assertEquals(HttpStatus.OK,statusCode );
  
  } //
  @Test 
  public void getByIdTest() throws SQLException { 
	  genderRequest = new
  GenderRequest(); genderRequest.setGender(getModel());
  genderRequest.setTransactionType("dfdhfgh");
  genderRequest.getGender().get(0).setId(1);
  when(genderDAOImpl.getAll(genderRequest)).thenReturn(getModel());
  when(genderDAOImpl.getAllCount(genderRequest)).thenReturn(4);
  ResponseEntity<Object>
  getAllGender=genderFacadeImpl.getGender(genderRequest); HttpStatus statusCode
  = getAllGender.getStatusCode(); assertEquals(HttpStatus.OK,statusCode );
  
  } 
  @Test 
  public void getAllForNullListTest() throws SQLException {
  genderRequest = new GenderRequest(); genderRequest.setGender(getModel());
  genderRequest.setTransactionType("dfdhfgh"); 
  when(genderDAOImpl.getAll(genderRequest)).thenReturn(null);
  when(genderDAOImpl.getAllCount(genderRequest)).thenReturn(4);
  ResponseEntity<Object>
  getAllGender=genderFacadeImpl.getGender(genderRequest); HttpStatus statusCode
  = getAllGender.getStatusCode(); assertEquals(HttpStatus.OK,statusCode );
  
  } //
  
  
  }
 