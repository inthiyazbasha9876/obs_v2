/*
 * package com.ojas.obs.facade;
 * 
 * import static org.junit.Assert.assertEquals; import static
 * org.mockito.Mockito.mock; import static org.mockito.Mockito.when;
 * 
 * import java.lang.reflect.Field; import java.sql.SQLException; import
 * java.util.ArrayList; import java.util.List;
 * 
 * import org.junit.Before; import org.junit.Test; import
 * org.junit.runner.RunWith; import org.mockito.InjectMocks; import
 * org.mockito.Mock; import org.mockito.Mockito; import org.mockito.Spy; import
 * org.mockito.junit.MockitoJUnitRunner; import
 * org.springframework.http.HttpStatus; import
 * org.springframework.http.ResponseEntity;
 * 
 * import com.ojas.obs.dao.EmployeeSkillDao; import
 * com.ojas.obs.model.EmployeeSkillInfo; import
 * com.ojas.obs.model.EmployeeSkillInfoRequest; import
 * com.ojas.obs.utility.ErrorResponse;
 * 
 * @RunWith(MockitoJUnitRunner.class) public class EmployeeSkillFacadeImplTest {
 * 
 * @InjectMocks private EmployeeSkillFacadeImpl employeeSkillFacadeImpl;
 * 
 * @Mock private EmployeeSkillDao employeeSkillDao;
 * 
 * @Spy EmployeeSkillInfoRequest employeeSkillInfoRequest;
 * 
 * @Spy ErrorResponse error = new ErrorResponse();
 * 
 * @Spy ResponseEntity<Object> objEntity = new ResponseEntity<Object>(error,
 * HttpStatus.UNPROCESSABLE_ENTITY);
 * 
 * @Spy ResponseEntity<Object> sucessResponse = new
 * ResponseEntity<Object>(error, HttpStatus.OK);
 * 
 * @Spy List<EmployeeSkillInfo> empSkillList = new
 * ArrayList<EmployeeSkillInfo>();
 * 
 * @Before public void init() { employeeSkillFacadeImpl = new
 * EmployeeSkillFacadeImpl(); employeeSkillDao = mock(EmployeeSkillDao.class);
 * setCollabarator(employeeSkillFacadeImpl, "employeeSkillDao",
 * employeeSkillDao); }
 * 
 * public void setCollabarator(Object object, String name, Object collabarator)
 * { Field field; try { field = object.getClass().getDeclaredField(name);
 * field.setAccessible(true); field.set(object, collabarator); } catch
 * (Exception e) { e.printStackTrace(); } }
 * 
 * public List<EmployeeSkillInfo> getList() { List<EmployeeSkillInfo> list = new
 * ArrayList<>(); EmployeeSkillInfo employeeSkillInfo = new EmployeeSkillInfo();
 * employeeSkillInfo.setId(1); employeeSkillInfo.setSkill_id("Java");
 * employeeSkillInfo.setLevel_id(1); employeeSkillInfo.setEmployee_id("18290");
 * employeeSkillInfo.setCreated_by("1"); employeeSkillInfo.setUpdate_by(null);
 * employeeSkillInfo.setFlag(false); employeeSkillInfo.setFlag(false);
 * employeeSkillInfo.setCreated_date(null);
 * employeeSkillInfo.setUpdated_date(null); list.add(employeeSkillInfo); return
 * list; }
 * 
 * public EmployeeSkillInfoRequest employeeSkillInfoRequest() { //
 * employeeSkillInfoRequest = new EmployeeSkillInfoRequest(); EmployeeSkillInfo
 * employeeSkillInfo = new EmployeeSkillInfo(); List<EmployeeSkillInfo>
 * employeeSkillInfoList = new ArrayList<EmployeeSkillInfo>();
 * employeeSkillInfo.setId(1); employeeSkillInfo.setSkill_id("Java");
 * employeeSkillInfo.setLevel_id(1); employeeSkillInfo.setEmployee_id("18290");
 * employeeSkillInfo.setCreated_by("1"); employeeSkillInfo.setUpdate_by(null);
 * employeeSkillInfo.setFlag(false);
 * employeeSkillInfoList.add(employeeSkillInfo);
 * employeeSkillInfoRequest.setSkillInfoModel(employeeSkillInfoList);
 * employeeSkillInfoRequest.setTransactionType("save");
 * employeeSkillInfoRequest.setSessionId("1234");
 * 
 * return employeeSkillInfoRequest; }
 * 
 * public EmployeeSkillInfoRequest getAllRequest() { //employeeSkillInfoRequest=
 * new EmployeeSkillInfoRequest(); List<EmployeeSkillInfo> list = new
 * ArrayList<EmployeeSkillInfo>();
 * employeeSkillInfoRequest.setSkillInfoModel(list);
 * employeeSkillInfoRequest.setTransactionType("getAll"); return
 * employeeSkillInfoRequest; }
 * 
 * @Test public void setEmployeeSkillInfoTest() throws SQLException {
 * 
 * when(employeeSkillDao.saveEmployeeSkillInfo(employeeSkillInfoRequest())).
 * thenReturn(0); ResponseEntity<Object> saveResponseEntity =
 * employeeSkillFacadeImpl .setEmployeeSkillInfo(employeeSkillInfoRequest()); //
 * HttpStatus statusCode = saveResponseEntity.getStatusCode();
 * assertEquals(HttpStatus.OK, saveResponseEntity.getStatusCode());
 * 
 * }
 * 
 * @Test public void getEmployeeSkillInfoTest() throws SQLException {
 * when(employeeSkillDao.showEmployeeSkillInfo(employeeSkillInfoRequest())).
 * thenReturn(getList()); ResponseEntity<Object> skillDetails =
 * employeeSkillFacadeImpl.getEmployeeSkillInfo(getAllRequest()); HttpStatus
 * statusCode = skillDetails.getStatusCode(); assertEquals(HttpStatus.OK,
 * statusCode); }
 * 
 * @Test public void setEmployeeSkillInfoTestUpdate() throws SQLException {
 * EmployeeSkillInfo employeeSkillInfo = new EmployeeSkillInfo();
 * List<EmployeeSkillInfo> employeeSkillInfoList = new
 * ArrayList<EmployeeSkillInfo>(); employeeSkillInfo.setId(1);
 * employeeSkillInfo.setSkill_id("Java"); employeeSkillInfo.setLevel_id(1);
 * employeeSkillInfo.setEmployee_id("18290");
 * employeeSkillInfo.setCreated_by(null); employeeSkillInfo.setUpdate_by("2");
 * employeeSkillInfo.setFlag(false);
 * employeeSkillInfoList.add(employeeSkillInfo);
 * employeeSkillInfoRequest.setSkillInfoModel(employeeSkillInfoList);
 * employeeSkillInfoRequest.setTransactionType("update");
 * employeeSkillInfoRequest.setSessionId("1234");
 * 
 * when(employeeSkillDao.updateEmployeeSkillInfo(employeeSkillInfoRequest)).
 * thenReturn(0); ResponseEntity<Object> updateResponseEntity =
 * employeeSkillFacadeImpl .setEmployeeSkillInfo(employeeSkillInfoRequest); //
 * 
 * assertEquals(HttpStatus.OK, updateResponseEntity.getStatusCode());
 * 
 * }
 * 
 * // ------------------getById method--------------------
 * 
 * @Test public void testGetById() throws SQLException { List<EmployeeSkillInfo>
 * list = new ArrayList<>(); EmployeeSkillInfo empSkill = new
 * EmployeeSkillInfo(); empSkill.setId(1); empSkillList.add(empSkill);
 * EmployeeSkillInfoRequest employeeSkillInfoRequest = new
 * EmployeeSkillInfoRequest();
 * employeeSkillInfoRequest.setTransactionType("getbyid");
 * employeeSkillInfoRequest.setSkillInfoModel(list);
 * Mockito.lenient().when(employeeSkillDao.getById(employeeSkillInfoRequest));
 * ResponseEntity<Object> saveStatus =
 * employeeSkillFacadeImpl.getEmployeeSkillInfo(employeeSkillInfoRequest);
 * HttpStatus statusCode = saveStatus.getStatusCode();
 * assertEquals(HttpStatus.OK, statusCode); }
 * 
 * }
 */