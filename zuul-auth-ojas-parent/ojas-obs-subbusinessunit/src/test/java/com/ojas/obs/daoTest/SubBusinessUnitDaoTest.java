
  package com.ojas.obs.daoTest;
  
  import static org.junit.Assert.assertEquals; import static
  org.mockito.ArgumentMatchers.anyString; import static
  org.mockito.Mockito.mock; import static org.mockito.Mockito.when;
  
  import java.lang.reflect.Field; import java.sql.SQLException; import
  java.util.ArrayList; import java.util.Collections; import java.util.List;
  
  import org.junit.Before; import org.junit.Test; import
  org.mockito.InjectMocks; import org.mockito.Mock; import org.mockito.Mockito;
  import org.mockito.Spy; import
  org.springframework.jdbc.core.BeanPropertyRowMapper; import
  org.springframework.jdbc.core.JdbcTemplate;
  
  import com.ojas.obs.daoimpl.SubBusinessUnitDaoImpl; import
  com.ojas.obs.error.ErrorResponse; import com.ojas.obs.model.SubBusinessUnit;
  import com.ojas.obs.request.SubBusinessUnitRequest; import
  com.ojas.obs.response.SubBusinessUnitResponse;
  
  public class SubBusinessUnitDaoTest {
  
  @Mock JdbcTemplate jdbcTemplate;
  
  @InjectMocks private SubBusinessUnitDaoImpl subBusinessUnitDaoImpl;
  
  @Spy ErrorResponse errorResponse = new ErrorResponse();
  
  @Spy SubBusinessUnitRequest subBusinessUnitRequest;
  
  @Spy SubBusinessUnitResponse subBusinessUnitResponse;
  
  @Spy SubBusinessUnit subBusinessUnit;
  
  @Spy List<SubBusinessUnit> SubBusinessUnitList = new
  ArrayList<SubBusinessUnit>(); int[] count = { 1 }; int[] zeroCount = {};
  
  @Before public void init() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException { subBusinessUnitDaoImpl = new
  SubBusinessUnitDaoImpl(); SubBusinessUnitList.add(subBusinessUnit);
  jdbcTemplate = mock(JdbcTemplate.class);
  setCollaborator(subBusinessUnitDaoImpl, "jdbcTemplate", jdbcTemplate); }
  
  private void setCollaborator(Object object, String name, Object service) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
  
  Field field;
  field = object.getClass().getDeclaredField(name);
  field.setAccessible(true);
  
  field.set(object, service); } 
  
  public SubBusinessUnitRequest subBusinessUnitRequest() {
  subBusinessUnitRequest = new SubBusinessUnitRequest(); SubBusinessUnit
  subBusinessUnit1 = new SubBusinessUnit(); subBusinessUnit1.setId(1);
  subBusinessUnit1.setName("sunil"); subBusinessUnit1.setBusinessUnitId("123");
  //subBusinessUnit1.setCostCenterId(123); 
  SubBusinessUnit subBusinessUnit2 = new
  SubBusinessUnit(); subBusinessUnit2.setId(2);
  subBusinessUnit2.setName("sunil");
  subBusinessUnit2.setBusinessUnitId("123");
  //subBusinessUnit2.setCostCenterId(123); 
  List<SubBusinessUnit>subBusinessUnit1List = new ArrayList<>();
  subBusinessUnit1List.add(subBusinessUnit1);
  subBusinessUnit1List.add(subBusinessUnit2);
  subBusinessUnitRequest.setSubBusinessUnitModel(subBusinessUnit1List); return
  subBusinessUnitRequest; }
  
  @Test public void saveSubBusinessUnitSuccess() throws SQLException {
  subBusinessUnitRequest = subBusinessUnitRequest();
  
  when(jdbcTemplate.batchUpdate(anyString(),
  Mockito.anyList())).thenReturn(count);
  
  boolean save =
  subBusinessUnitDaoImpl.saveSubBusinessUnit(subBusinessUnitRequest);
  assertEquals(true, save); }
  
  @Test public void saveSubBusinessUnitFail() throws SQLException {
  subBusinessUnitRequest = subBusinessUnitRequest();
  
  when(jdbcTemplate.batchUpdate(anyString(),
  Mockito.anyList())).thenReturn(zeroCount);
  
  boolean save =
  subBusinessUnitDaoImpl.saveSubBusinessUnit(subBusinessUnitRequest);
  assertEquals(false, save); }
  
  @Test public void updateSubBusinessUnitSuccess() throws SQLException {
  subBusinessUnitRequest = subBusinessUnitRequest();
  
  when(jdbcTemplate.batchUpdate(anyString(),
  Mockito.anyList())).thenReturn(count);
  
  boolean save =
  subBusinessUnitDaoImpl.updateSubBusinessUnit(subBusinessUnitRequest);
  assertEquals(true, save); }
  
  @Test public void updateSubBusinessUnitFail() throws SQLException {
  subBusinessUnitRequest = subBusinessUnitRequest();
  
  when(jdbcTemplate.batchUpdate(anyString(),
  Mockito.anyList())).thenReturn(zeroCount);
  
  boolean save =
  subBusinessUnitDaoImpl.updateSubBusinessUnit(subBusinessUnitRequest);
  assertEquals(false, save); }
  
  
  @Test public void deleteSubBusinessUnitSuccess() { subBusinessUnitRequest =
  subBusinessUnitRequest();
  
  when(jdbcTemplate.batchUpdate(anyString(),
  Mockito.anyList())).thenReturn(count);
  
		  }
  
  @Test
  public void deleteSubBusinessUnitFail() { 
	  subBusinessUnitRequest = subBusinessUnitRequest();
  
  when(jdbcTemplate.batchUpdate(anyString(),
  Mockito.anyList())).thenReturn(zeroCount);
  
		
  
  }
  
  
  @Test public void getAllSubBusinessUnitDetails() throws SQLException {
  subBusinessUnitRequest = subBusinessUnitRequest(); String GETALLBUSINESSUNITS
  = "select * from obs_subbusinessunitt";
  when(jdbcTemplate.query(GETALLBUSINESSUNITS, new
  BeanPropertyRowMapper<SubBusinessUnit>(SubBusinessUnit.class))).thenReturn(
  Collections.emptyList()); SubBusinessUnitList =
  subBusinessUnitDaoImpl.getAllSubBusinessUnitDetails(); boolean flag =
  SubBusinessUnitList.isEmpty(); assertEquals(true, flag); }
  
  
  }
 