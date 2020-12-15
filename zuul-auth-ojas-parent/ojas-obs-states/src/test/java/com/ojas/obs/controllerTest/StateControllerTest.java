
  package com.ojas.obs.controllerTest;
  
  import static org.junit.Assert.assertEquals; import static
  org.mockito.Mockito.mock; import static org.mockito.Mockito.when;
  
  import java.lang.reflect.Field; import java.sql.SQLException; import
  java.util.ArrayList; import java.util.List;
  
  import javax.servlet.http.HttpServletRequest; import
  javax.servlet.http.HttpServletResponse;
  
  import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks; import
  org.mockito.Mock; import org.mockito.Spy;
import org.springframework.dao.DuplicateKeyException;
import
  org.springframework.http.HttpStatus; import
  org.springframework.http.ResponseEntity;
  
  import com.ojas.obs.controller.StatesController; import
  com.ojas.obs.facade.StatesFacade; import
  com.ojas.obs.facade.StatesFacadeImpl; import
  com.ojas.obs.model.ErrorResponse; import com.ojas.obs.model.States; import
  com.ojas.obs.request.StatesRequest; import
  com.ojas.obs.response.StatesResponse;
  
  public class StateControllerTest {
  
  @Mock StatesFacade statesFacade;
  
  @Mock StatesFacadeImpl statesFacadeImpl;
  
  @InjectMocks StatesController statesController;
  
  @Spy ErrorResponse errorResponse = new ErrorResponse();
  
  @Spy ResponseEntity<Object> failureResponse = new
  ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
  
  @Spy ResponseEntity<Object> conflict = new
  ResponseEntity<>(errorResponse,HttpStatus.CONFLICT);
  
  @Spy ResponseEntity<Object> sucessResponse = new
  ResponseEntity<>(errorResponse, HttpStatus.OK);
  
  @Spy StatesRequest statesRequest = new StatesRequest();
  
  @Spy StatesResponse statesResponse = new StatesResponse();
  
  
  @Before public void init() { statesController = new StatesController();
  statesFacadeImpl=mock(StatesFacadeImpl.class);
  setCollaborator(statesController,"statesFacade",statesFacadeImpl); }
  
  private void setCollaborator(Object object, String name, Object service) {
  
  Field field; try { field = object.getClass().getDeclaredField(name);
  field.setAccessible(true);
  
  field.set(object, service); } catch (Exception e) {
  
  e.printStackTrace(); } }
  
  public List<States> getStates() {
  List<States> statesList = new
  ArrayList<States>(); States states = new States(); states.setId(1);
  states.setStateName("Uttarakhand"); statesList.add(states); return
  statesList; 
  } 
   @Test
  public void requestObjectNullCheck() { StatesRequest
  request= null; HttpServletRequest request1 = null; HttpServletResponse
  response = null; try {
  when(statesFacadeImpl.setStates(request)).thenReturn(new
  ResponseEntity<Object>(request, HttpStatus.UNPROCESSABLE_ENTITY));
  ResponseEntity<Object> setStates =
  statesController.setStates(request,request1,response); HttpStatus statusCode
  = setStates.getStatusCode();
  assertEquals(HttpStatus.UNPROCESSABLE_ENTITY,statusCode); } catch (Exception
  e) {} } 
   
 
  
  @Test
  public void transactionTypeNullCheck() { StatesRequest request= new
  StatesRequest();
  request.setTransactionType(null); HttpServletRequest request1 = null;
  HttpServletResponse response = null; try {
  when(statesFacadeImpl.setStates(request)).thenReturn(failureResponse);
  ResponseEntity<Object> setStates =
  statesController.setStates(request,request1,response); HttpStatus statusCode
  = setStates.getStatusCode();
  assertEquals(HttpStatus.UNPROCESSABLE_ENTITY,statusCode); } catch (Exception
  e) {}
  
  } 
  @Test 
  public void modelFieldsCheck() { statesRequest= new
  StatesRequest();  statesRequest.setStates(getStates());
  statesRequest.getStates().get(0).setStateName(null);
  statesRequest.setTransactionType("save"); HttpServletRequest request1 = null;
  HttpServletResponse response = null; try {
  when(statesFacadeImpl.setStates(statesRequest)).thenReturn(new
  ResponseEntity<Object>(statesRequest, HttpStatus.UNPROCESSABLE_ENTITY));
  ResponseEntity<Object> setStates =
  statesController.setStates(statesRequest,request1,response); HttpStatus
  statusCode = setStates.getStatusCode();
  assertEquals(HttpStatus.UNPROCESSABLE_ENTITY,statusCode); } catch (Exception
  e) {} } 
  @Test 
  public void idNullCheck() { statesRequest= new
  StatesRequest();  statesRequest.setStates(getStates());
  statesRequest.getStates().get(0).setId(0);
  statesRequest.setTransactionType("update"); HttpServletRequest request1 =
  null; HttpServletResponse response = null; try {
  when(statesFacadeImpl.setStates(statesRequest)).thenReturn(new
  ResponseEntity<Object>(statesRequest, HttpStatus.UNPROCESSABLE_ENTITY));
  ResponseEntity<Object> setStates =
  statesController.setStates(statesRequest,request1,response); HttpStatus
  statusCode = setStates.getStatusCode();
  assertEquals(HttpStatus.UNPROCESSABLE_ENTITY,statusCode); } catch (Exception
  e) {} }
  
  
  @Test 
  public void statesSQLExcpTest() { 
  statesRequest= new StatesRequest(); 
  statesRequest.setStates(getStates());
  statesRequest.setTransactionType("save"); 
  HttpServletRequest request1 = null;
  HttpServletResponse response = null; 
  try {
  when(statesFacadeImpl.setStates(statesRequest)).thenThrow(SQLException.class ); 
  ResponseEntity<Object> contResponse =
  statesController.setStates(statesRequest,request1,response); HttpStatus
  statusCode = contResponse.getStatusCode(); assertEquals(HttpStatus.CONFLICT,
  statusCode); } catch (Exception e) { // TODO Auto-generated catch block
  e.printStackTrace(); }
  
  }
  
  @Test 
  public void statesDuplicateKeyExcpTest() { 
  statesRequest= new StatesRequest(); 
  statesRequest.setStates(getStates());
  statesRequest.setTransactionType("save"); 
  HttpServletRequest request1 = null;
  HttpServletResponse response = null; 
  try {
  when(statesFacadeImpl.setStates(statesRequest)).thenThrow(new DuplicateKeyException(null,new Throwable()) ); 
  ResponseEntity<Object> contResponse =
  statesController.setStates(statesRequest,request1,response); HttpStatus
  statusCode = contResponse.getStatusCode(); assertEquals(HttpStatus.CONFLICT,
  statusCode); } catch (Exception e) { }
  
  }
  @Test 
  public void statesExcpTest() { 
  statesRequest= new StatesRequest(); 
  statesRequest.setStates(getStates());
  statesRequest.setTransactionType("save"); 
  HttpServletRequest request1 = null;
  HttpServletResponse response = null; 
  try {
  when(statesFacadeImpl.setStates(statesRequest)).thenThrow(new NullPointerException()); 
  ResponseEntity<Object> contResponse =
  statesController.setStates(statesRequest,request1,response); HttpStatus
  statusCode = contResponse.getStatusCode(); assertEquals(HttpStatus.CONFLICT,
  statusCode); } catch (Exception e) { }
  
  }
  
  
  
  @Test 
  public void requestObjectForGetNullCheck() { StatesRequest request=
  null; try { when(statesFacadeImpl.getStates(request)).thenReturn(new
  ResponseEntity<Object>(request, HttpStatus.UNPROCESSABLE_ENTITY));
  ResponseEntity<Object> setStates = statesController.getStates(request);
  HttpStatus statusCode = setStates.getStatusCode();
  assertEquals(HttpStatus.UNPROCESSABLE_ENTITY,statusCode); } catch (Exception
  e) {} }
  
 
  @Test
  public void statesSQLExcpTestOFrGet() { 
  statesRequest= new StatesRequest();
  statesRequest.setStates(getStates());
  statesRequest.setTransactionType("save"); try {
  when(statesFacadeImpl.getStates(statesRequest)).thenThrow(SQLException.class) ;
  ResponseEntity<Object> contResponse =
  statesController.getStates(statesRequest); HttpStatus statusCode =
  contResponse.getStatusCode(); assertEquals(HttpStatus.CONFLICT, statusCode);
  } catch (SQLException e) { e.printStackTrace(); }
  
  }
  @Test 
  public void statesExcpForGetTest() { 
  statesRequest= new StatesRequest(); 
  statesRequest.setStates(getStates());
  statesRequest.setTransactionType("save");
  try {
  when(statesFacadeImpl.getStates(statesRequest)).thenThrow(new NullPointerException()); 
  ResponseEntity<Object> contResponse =
  statesController.getStates(statesRequest); 
  HttpStatus  statusCode = contResponse.getStatusCode(); assertEquals(HttpStatus.CONFLICT,
  statusCode); 
  } catch (Exception e) { 
	  
  }
  
  }
  
  
  }
 