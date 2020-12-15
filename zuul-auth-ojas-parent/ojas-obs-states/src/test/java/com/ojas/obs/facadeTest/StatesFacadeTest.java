
  package com.ojas.obs.facadeTest;
  
  import static org.junit.Assert.assertEquals; import static
  org.mockito.Mockito.mock; import static org.mockito.Mockito.when;
  
  import java.lang.reflect.Field; import java.sql.SQLException; import
  java.util.ArrayList; import java.util.List;
  
  import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks; import
  org.mockito.Mock; import org.mockito.Spy; import
  org.springframework.http.HttpStatus; import
  org.springframework.http.ResponseEntity;
  
  import com.ojas.obs.dao.StatesDAOImpl; import
  com.ojas.obs.facade.StatesFacadeImpl; import
  com.ojas.obs.model.ErrorResponse; import com.ojas.obs.model.States; import
  com.ojas.obs.request.StatesRequest; import
  com.ojas.obs.response.StatesResponse;
  
  public class StatesFacadeTest {
  
  @InjectMocks StatesFacadeImpl statesFacadeImpl;
  
  @Mock StatesDAOImpl statesDAOImpl;
  
  @Spy ErrorResponse errorResponse = new ErrorResponse();
  
  @Spy ResponseEntity<Object> failureResponse = new
  ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
  
  @Spy ResponseEntity<Object> conflict = new
  ResponseEntity<>(errorResponse,HttpStatus.CONFLICT);
  
  @Spy ResponseEntity<Object> sucessResponse = new
  ResponseEntity<>(errorResponse, HttpStatus.OK);
  
  @Spy StatesRequest statesRequest = new StatesRequest();
  
  @Spy StatesResponse statesResponse = new StatesResponse();
  
  
  @Before public void init() { 
  statesFacadeImpl= new StatesFacadeImpl();
  statesDAOImpl= mock(StatesDAOImpl.class);
  setCollaborator(statesFacadeImpl,"statesDao",statesDAOImpl); 
  } 
  private void
   setCollaborator(Object object, String name, Object service) { 
   Field field;
	  try {
	field = object.getClass().getDeclaredField(name);
	  field.setAccessible(true);
	  field.set(object, service); 
	  } catch (Exception e)
	  { e.printStackTrace(); }
  } 
  
  
  public List<States> getModel() { List<States> list
  = new ArrayList<States>(); States model= new States();
  model.setStateName("Maha"); list.add(model); return list; }
  
  @Test 
  public void saveTest() throws SQLException {
  statesRequest = new
  StatesRequest(); statesRequest.setStates(getModel());
  statesRequest.setTransactionType("save");
  when(statesDAOImpl.saveStates(statesRequest)).thenReturn(true);
  ResponseEntity<Object> saveGender=statesFacadeImpl.setStates(statesRequest);
  HttpStatus statusCode = saveGender.getStatusCode();
  assertEquals(HttpStatus.OK, statusCode);
  
  } 
  @Test 
  public void saveNegativeTest() throws SQLException { 
  statesRequest = new StatesRequest(); 
  statesRequest.setStates(getModel());
  statesRequest.setTransactionType("save");
  when(statesDAOImpl.saveStates(statesRequest)).thenReturn(false);
  ResponseEntity<Object> saveGender=statesFacadeImpl.setStates(statesRequest);
  HttpStatus statusCode = saveGender.getStatusCode();
  assertEquals(HttpStatus.CONFLICT, statusCode);
  
  } 
  @Test
  public void updateTest() throws SQLException { 
  statesRequest = new StatesRequest(); 
  statesRequest.setStates(getModel());
  statesRequest.setTransactionType("update");
  when(statesDAOImpl.updateStates(statesRequest)).thenReturn(true);
  ResponseEntity<Object>
  updateGender=statesFacadeImpl.setStates(statesRequest); HttpStatus statusCode
  = updateGender.getStatusCode(); assertEquals(HttpStatus.OK, statusCode);
  
  } 
  @Test 
  public void updateNegativeTest() throws SQLException {
  statesRequest = new StatesRequest(); statesRequest.setStates(getModel());
  statesRequest.setTransactionType("update");
  when(statesDAOImpl.updateStates(statesRequest)).thenReturn(false);
  ResponseEntity<Object>
  updateGender=statesFacadeImpl.setStates(statesRequest); HttpStatus statusCode
  = updateGender.getStatusCode(); assertEquals(HttpStatus.CONFLICT,
  statusCode);
  
  }
  
  
  
 
  @Test 
  public void transactionTypeNullCheckTest() throws SQLException {
  statesRequest = new StatesRequest(); statesRequest.setStates(getModel());
  
  statesRequest.setTransactionType("kjk");
  when(statesDAOImpl.saveStates(statesRequest)).thenReturn(false);
  ResponseEntity<Object>
  deleteGender=statesFacadeImpl.setStates(statesRequest); HttpStatus statusCode
  = deleteGender.getStatusCode(); assertEquals(HttpStatus.CONFLICT,
  statusCode);
  
  } 

	/*
	 * @Test public void exceptionCheckTest() throws SQLException { statesRequest =
	 * new StatesRequest(); statesRequest.setStates(getModel());
	 * statesRequest.setTransactionType("save");
	 * when(statesDAOImpl.saveStates(statesRequest)).thenThrow(SQLException.class);
	 * ResponseEntity<Object> gender=statesFacadeImpl.setStates(statesRequest);
	 * HttpStatus statusCode = gender.getStatusCode();
	 * assertEquals(HttpStatus.CONFLICT, statusCode);
	 * 
	 * }
	 */
  @Test 
  public void getAllForNullListTest() throws SQLException {
  statesRequest = new StatesRequest(); statesRequest.setStates(null);
  statesRequest.setTransactionType("getall");

  when(statesDAOImpl.getAll(statesRequest)).thenReturn(null);
  ResponseEntity<Object>
  getAllGender=statesFacadeImpl.getStates(statesRequest); HttpStatus statusCode
  = getAllGender.getStatusCode(); assertEquals(HttpStatus.OK,statusCode );
  
  } 
	 
@Test 
public void getbyIDTest() throws SQLException {
statesRequest = new StatesRequest(); statesRequest.setStates(null);
statesRequest.setTransactionType("getbyid");

when(statesDAOImpl.getAll(statesRequest)).thenReturn(null);
ResponseEntity<Object>
getAllGender=statesFacadeImpl.getStates(statesRequest); HttpStatus statusCode
= getAllGender.getStatusCode(); assertEquals(HttpStatus.OK,statusCode );

} 

@Test 
public void getbyIDNotNullListTest() throws SQLException {
statesRequest = new StatesRequest(); statesRequest.setStates(null);
statesRequest.setTransactionType("getbyid");

when(statesDAOImpl.getAll(statesRequest)).thenReturn(new ArrayList<States>());
ResponseEntity<Object>
getAllGender=statesFacadeImpl.getStates(statesRequest); HttpStatus statusCode
= getAllGender.getStatusCode(); assertEquals(HttpStatus.OK,statusCode );

} 

 
  }
 