/*
 * package com.ojas.obs.controller;
 * 
 * import static org.junit.Assert.assertEquals; import static
 * org.junit.Assert.assertNotEquals; import static org.mockito.Mockito.mock;
 * import static org.mockito.Mockito.when;
 * 
 * import java.lang.reflect.Field; import java.sql.SQLException; import
 * java.util.ArrayList; import java.util.List;
 * 
 * import javax.servlet.http.HttpServletRequest; import
 * javax.servlet.http.HttpServletResponse;
 * 
 * import org.junit.Before; import org.junit.Test; import
 * org.junit.runner.RunWith; import org.mockito.InjectMocks; import
 * org.mockito.Mock; import org.mockito.Mockito; import org.mockito.Spy; import
 * org.mockito.junit.MockitoJUnitRunner; import
 * org.springframework.http.HttpStatus; import
 * org.springframework.http.ResponseEntity;
 * 
 * import com.ojas.obs.facade.EmployeeSkillFacade; import
 * com.ojas.obs.model.EmployeeSkillInfo; import
 * com.ojas.obs.model.EmployeeSkillInfoRequest; import
 * com.ojas.obs.utility.ErrorResponse;
 * 
 * @RunWith(MockitoJUnitRunner.class) public class EmployeeSkillControllerTest {
 * 
 * @Mock private EmployeeSkillFacade employeeSkillFacade;
 * 
 * @InjectMocks private EmployeeSkillInfoController employeeSkillInfoController;
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
 * @Before public void init() throws Exception { employeeSkillInfoController =
 * new EmployeeSkillInfoController(); employeeSkillFacade =
 * mock(EmployeeSkillFacade.class); setCollabarator(employeeSkillInfoController,
 * "employeeSkillService", employeeSkillFacade); }
 * 
 * public void setCollabarator(Object object, String name, Object collabarator)
 * throws Exception { Field field;
 * 
 * field = object.getClass().getDeclaredField(name); field.setAccessible(true);
 * field.set(object, collabarator);
 * 
 * }
 * 
 * public EmployeeSkillInfoRequest employeeSkillInfoRequest() {
 * EmployeeSkillInfo employeeSkillInfo = new EmployeeSkillInfo();
 * List<EmployeeSkillInfo> employeeSkillInfoList = new
 * ArrayList<EmployeeSkillInfo>(); employeeSkillInfo.setId(1);
 * employeeSkillInfo.setSkill_id("Java"); employeeSkillInfo.setLevel_id(1);
 * employeeSkillInfo.setEmployee_id("18290");
 * employeeSkillInfo.setCreated_by("1"); employeeSkillInfo.setUpdate_by(null);
 * employeeSkillInfo.setFlag(false);
 * employeeSkillInfoList.add(employeeSkillInfo);
 * employeeSkillInfoRequest.setSkillInfoModel(employeeSkillInfoList);
 * employeeSkillInfoRequest.setTransactionType("save");
 * employeeSkillInfoRequest.setSessionId("1234");
 * 
 * return employeeSkillInfoRequest; }
 * 
 * public List<EmployeeSkillInfo> getSkillInfoList() {
 * ArrayList<EmployeeSkillInfo> arrayList = new ArrayList<>(); EmployeeSkillInfo
 * empSkill = new EmployeeSkillInfo(); empSkill.setId(1);
 * empSkill.setEmployee_id("123"); empSkill.setLevel_id(3);
 * empSkill.setSkill_id("Java");
 * 
 * 
 * 
 * arrayList.add(empSkill); return arrayList; }
 * 
 * 
 * // ------------------Null check-------------------
 * 
 * @Test public void setEmployeeSkillInfoTestNull() throws SQLException {
 * HttpServletRequest request = null; HttpServletResponse response = null;
 * List<EmployeeSkillInfo> skillList = new ArrayList<EmployeeSkillInfo>();
 * employeeSkillInfoRequest = new EmployeeSkillInfoRequest(); EmployeeSkillInfo
 * employeeSkillInfo = new EmployeeSkillInfo();
 * skillList.add(employeeSkillInfo);
 * employeeSkillInfoRequest.setSkillInfoModel(skillList);
 * Mockito.lenient().when(employeeSkillFacade.setEmployeeSkillInfo(
 * employeeSkillInfoRequest)) .thenReturn(sucessResponse);
 * ResponseEntity<Object> setSkill =
 * employeeSkillInfoController.setEmployeeSkillInfo(employeeSkillInfoRequest,
 * request, response); HttpStatus statusCode = setSkill.getStatusCode();
 * assertNotEquals(HttpStatus.OK, statusCode); }
 * 
 * @Test public void setSaveFailTest() throws Exception {
 * employeeSkillInfoRequest.setTransactionType("save"); HttpServletRequest
 * request = null; HttpServletResponse response = null;
 * when(employeeSkillFacade.setEmployeeSkillInfo(employeeSkillInfoRequest())).
 * thenThrow(SQLException.class); ResponseEntity<Object> setSkill =
 * employeeSkillInfoController.setEmployeeSkillInfo(employeeSkillInfoRequest,
 * request, response); HttpStatus statusCode = setSkill.getStatusCode();
 * assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode); }
 * 
 * // --------------------Model check-----------
 * 
 * @Test public void setExperienceTestNullCheckType1() throws SQLException {
 * HttpServletRequest request = null; HttpServletResponse response = null;
 * EmployeeSkillInfoRequest employeeSkillInfoRequest = new
 * EmployeeSkillInfoRequest(); List<EmployeeSkillInfo> listOfEmpSkill = new
 * ArrayList<EmployeeSkillInfo>();
 * employeeSkillInfoRequest.setSkillInfoModel(listOfEmpSkill);
 * employeeSkillInfoRequest.setTransactionType("save");
 * Mockito.lenient().when(employeeSkillFacade.setEmployeeSkillInfo(
 * employeeSkillInfoRequest())) .thenReturn(sucessResponse);
 * ResponseEntity<Object> setSkill =
 * employeeSkillInfoController.setEmployeeSkillInfo(employeeSkillInfoRequest,
 * request, response); HttpStatus statusCode = setSkill.getStatusCode();
 * assertNotEquals(HttpStatus.OK, statusCode); }
 * 
 * // ------------------------reqobj check--------------
 * 
 * @Test public void setEmployeeSkillInfoTestRequestObjectNull() throws
 * SQLException {
 * 
 * HttpServletRequest request = null; HttpServletResponse response = null;
 * Mockito.lenient().when(employeeSkillFacade.setEmployeeSkillInfo(
 * employeeSkillInfoRequest())) .thenReturn(sucessResponse);
 * ResponseEntity<Object> saveResponseEntity =
 * employeeSkillInfoController.setEmployeeSkillInfo(null, request, response);
 * assertEquals(HttpStatus.UNPROCESSABLE_ENTITY,
 * saveResponseEntity.getStatusCode());
 * 
 * }
 * 
 * // -----------------null check for transaction type in set method
 * 
 * @Test public void setEmployeeSkillInfoTestNullCheckTypeTranscation() throws
 * SQLException { HttpServletRequest request = null; HttpServletResponse
 * response = null; EmployeeSkillInfoRequest employeeSkillInfoRequest = new
 * EmployeeSkillInfoRequest(); employeeSkillInfoRequest.setTransactionType("");
 * Mockito.lenient().when(employeeSkillFacade.setEmployeeSkillInfo(
 * employeeSkillInfoRequest())) .thenReturn(sucessResponse);
 * ResponseEntity<Object> setSkill =
 * employeeSkillInfoController.setEmployeeSkillInfo(employeeSkillInfoRequest,
 * request, response); HttpStatus statusCode = setSkill.getStatusCode();
 * assertNotEquals(HttpStatus.OK, statusCode); }
 * 
 * 
 * @Test public void setExceptionTest1() throws SQLException {
 * 
 * HttpServletRequest request = null; HttpServletResponse response = null;
 * EmployeeSkillInfoRequest employeeSkillInfoRequest = new
 * EmployeeSkillInfoRequest(); EmployeeSkillInfo employeeSkillInfo= new
 * EmployeeSkillInfo(); employeeSkillInfo.setSkill_id("Java");
 * employeeSkillInfo.setEmployee_id("5264");
 * employeeSkillInfo.setCreated_by("ajay"); List<EmployeeSkillInfo>
 * listOfEmpSkill = new ArrayList<EmployeeSkillInfo>(); EmployeeSkillInfo
 * employeeSkillInfo1= new EmployeeSkillInfo();
 * employeeSkillInfo1.setSkill_id("Java");
 * employeeSkillInfo1.setEmployee_id("5264");
 * employeeSkillInfo1.setCreated_by("ajay");
 * listOfEmpSkill.add(employeeSkillInfo);
 * listOfEmpSkill.add(employeeSkillInfo1);
 * employeeSkillInfoRequest.setTransactionType("sav");
 * employeeSkillInfoRequest.setSkillInfoModel(listOfEmpSkill);
 * when(employeeSkillFacade.setEmployeeSkillInfo(employeeSkillInfoRequest)).
 * thenThrow((new RuntimeException())); ResponseEntity<Object> setSkill =
 * employeeSkillInfoController.setEmployeeSkillInfo(employeeSkillInfoRequest,
 * request, response); HttpStatus statusCode = setSkill.getStatusCode();
 * assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode); }
 * 
 * 
 * // ----------------------------get method---------------------------
 * 
 * @Test public void getEmployeeSkillInfoTest() throws SQLException {
 * HttpServletRequest request = null; HttpServletResponse response = null;
 * when(employeeSkillFacade.getEmployeeSkillInfo(employeeSkillInfoRequest())).
 * thenReturn(sucessResponse); ResponseEntity<Object> skillDetails =
 * employeeSkillInfoController
 * .getEmployeeSkillInfo(employeeSkillInfoRequest()); HttpStatus statusCode =
 * skillDetails.getStatusCode(); assertEquals(HttpStatus.OK, statusCode); }
 * 
 * // ---------SQL Exception case for get method------------
 * 
 * @Test public void getSQLExceptionTest() throws Exception {
 * 
 * employeeSkillInfoRequest.setSkillInfoModel(getSkillInfoList());
 * employeeSkillInfoRequest.setTransactionType("save"); HttpServletRequest
 * request = null; HttpServletResponse response = null;
 * 
 * when(employeeSkillFacade.getEmployeeSkillInfo(employeeSkillInfoRequest())).
 * thenThrow(SQLException.class);
 * 
 * ResponseEntity<Object> setSkill =
 * employeeSkillInfoController.getEmployeeSkillInfo(employeeSkillInfoRequest);
 * HttpStatus statusCode = setSkill.getStatusCode();
 * assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode); }
 * 
 * // -----------null check for transaction type in get //
 * method-------------------------
 * 
 * @Test public void getEmployeeSkillInfoTestNullCheckType() throws SQLException
 * { HttpServletRequest request = null; HttpServletResponse response = null;
 * EmployeeSkillInfoRequest employeeSkillInfoRequest = new
 * EmployeeSkillInfoRequest();
 * employeeSkillInfoRequest.setTransactionType(null);
 * when(employeeSkillFacade.getEmployeeSkillInfo(employeeSkillInfoRequest())).
 * thenReturn(sucessResponse); ResponseEntity<Object> skillDetails =
 * employeeSkillInfoController
 * .getEmployeeSkillInfo(employeeSkillInfoRequest()); HttpStatus statusCode =
 * skillDetails.getStatusCode();
 * assertNotEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
 * 
 * }
 * 
 * // ----------------------Exception in get method-------------------------
 * 
 * @Test public void getExceptionTest() throws Exception {
 * employeeSkillInfoRequest = new EmployeeSkillInfoRequest();
 * List<EmployeeSkillInfo> arrayList = new ArrayList<>();
 * employeeSkillInfoRequest.setSkillInfoModel(arrayList);
 * employeeSkillInfoRequest.setTransactionType("getAll"); HttpServletRequest
 * request = null; HttpServletResponse response = null;
 * 
 * Mockito.lenient().when(employeeSkillFacade.getEmployeeSkillInfo(
 * employeeSkillInfoRequest())) .thenThrow(new RuntimeException());
 * 
 * ResponseEntity<Object> setSkill =
 * employeeSkillInfoController.getEmployeeSkillInfo(employeeSkillInfoRequest);
 * HttpStatus statusCode = setSkill.getStatusCode();
 * assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode); }
 * 
 * // ------------------------reqobj check--------------
 * 
 * @Test public void getEmployeeSkillInfoTestRequestObjectNull() throws
 * SQLException {
 * 
 * HttpServletRequest request = null; HttpServletResponse response = null;
 * Mockito.lenient().when(employeeSkillFacade.getEmployeeSkillInfo(
 * employeeSkillInfoRequest())) .thenReturn(sucessResponse);
 * ResponseEntity<Object> saveResponseEntity =
 * employeeSkillInfoController.getEmployeeSkillInfo(null);
 * assertEquals(HttpStatus.UNPROCESSABLE_ENTITY,
 * saveResponseEntity.getStatusCode());
 * 
 * }
 * 
 * }
 */