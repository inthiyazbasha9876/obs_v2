
  package com.ojas.obs.DAOTest;
  
  import static org.junit.Assert.assertEquals; import static
  org.mockito.ArgumentMatchers.anyList; import static
  org.mockito.ArgumentMatchers.anyString; import static
  org.mockito.Mockito.mock; import static org.mockito.Mockito.when;
  
  import java.lang.reflect.Field; import java.sql.SQLException; import
  java.util.ArrayList; import java.util.List;
  
  import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks; import
  org.mockito.Mock; import org.mockito.Spy; import
  org.springframework.http.HttpStatus; import
  org.springframework.http.ResponseEntity; import
  org.springframework.jdbc.core.BeanPropertyRowMapper; import
  org.springframework.jdbc.core.JdbcTemplate;
  
  import com.ojas.obs.dao.StatesDAOImpl; import
  com.ojas.obs.model.ErrorResponse; import com.ojas.obs.model.States; import
  com.ojas.obs.request.StatesRequest; import
  com.ojas.obs.response.StatesResponse;
  
  public class StatesDAOTest {
  
  @InjectMocks StatesDAOImpl statesDAOImpl;
  
  @Mock JdbcTemplate jdbcTemplate;
  
  @Spy ErrorResponse errorResponse = new ErrorResponse();
  
  @Spy ResponseEntity<Object> failureResponse = new
  ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
  
  @Spy ResponseEntity<Object> conflict = new
  ResponseEntity<>(errorResponse,HttpStatus.CONFLICT);
  
  @Spy ResponseEntity<Object> sucessResponse = new
  ResponseEntity<>(errorResponse, HttpStatus.OK);
  
  @Spy StatesRequest statesRequest = new StatesRequest();
  
  @Spy StatesResponse statesResponse = new StatesResponse();
  
  
  @Before
  public void init() {
  statesDAOImpl= new StatesDAOImpl();
  jdbcTemplate= mock(JdbcTemplate.class);
  setCollaborator(statesDAOImpl,"jdbcTemplate",jdbcTemplate); } private void
  setCollaborator(Object object, String name, Object service) { Field field;
  try { field = object.getClass().getDeclaredField(name);
  field.setAccessible(true); field.set(object, service); } catch (Exception e)
  { e.printStackTrace(); } } public List<States> getModel() { List<States> list
  = new ArrayList<States>(); States model= new States();
  model.setStateName("Maha"); list.add(model); return list; }
  
  
  //
  @Test 
  public void saveTest() throws SQLException { statesRequest = new
  StatesRequest(); statesRequest.setStates(getModel());
  int[] count= {1,2};
  when(jdbcTemplate.batchUpdate(anyString(),anyList())).thenReturn(count);
  boolean status=statesDAOImpl.saveStates(statesRequest);
  assertEquals(true,status ); } 
  //
  @Test
  public void saveNegativeTest() throws
  SQLException { statesRequest = new StatesRequest();
  statesRequest.setStates(getModel());
  int[] count= {0,0};
  when(jdbcTemplate.batchUpdate(anyString(),anyList())).thenReturn(count);
  boolean status=statesDAOImpl.saveStates(statesRequest);
  assertEquals(false,status ); 
  } 
  //
  @Test
  public void updateTest() throws SQLException { statesRequest = new StatesRequest();
  statesRequest.setStates(getModel());
  int[] count= {1,2};
  when(jdbcTemplate.batchUpdate(anyString(),anyList())).thenReturn(count);
  boolean status=statesDAOImpl.updateStates(statesRequest);
  assertEquals(true,status ); } 
  //
  @Test 
  public void updateNegativeTest() throws SQLException { 
  statesRequest = new StatesRequest();
  statesRequest.setStates(getModel()); int[] count= {0,0};
  when(jdbcTemplate.batchUpdate(anyString(),anyList())).thenReturn(count);
  boolean status=statesDAOImpl.updateStates(statesRequest);
  assertEquals(false,status );
  }
  
  //
  
  @Test 
  public void getAllTest() throws SQLException { statesRequest = new
  StatesRequest(); statesRequest.setStates(getModel());
  when(jdbcTemplate.query("fghfgh",new
  BeanPropertyRowMapper<States>(States.class))).thenReturn(getModel());
  List<States>num=statesDAOImpl.getAll(statesRequest); boolean
  status=num.isEmpty(); assertEquals(true,status );
  
  } //
  @Test
  public void getAllCountTest() throws SQLException { statesRequest =
  new StatesRequest(); statesRequest.setStates(getModel()); int num=4;
  when(jdbcTemplate.queryForObject("select count(*) from obs_states",Integer.
  class)).thenReturn(num); int count=statesDAOImpl.getAllStatesCount();
  assertEquals(4,count );
  
  } 
  
  @Test 
  public void getByIdTest() throws SQLException { statesRequest = new
  StatesRequest(); statesRequest.setStates(getModel());
  when(jdbcTemplate.query("fghfgh",new
  BeanPropertyRowMapper<States>(States.class))).thenReturn(getModel());
  List<States>num=statesDAOImpl.getStateById(statesRequest);
  boolean status=num.isEmpty(); assertEquals(true,status );
  
  } 
  
  
  
  }
 