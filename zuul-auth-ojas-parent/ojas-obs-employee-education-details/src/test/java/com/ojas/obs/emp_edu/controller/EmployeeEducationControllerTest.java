/*
 * package com.ojas.obs.emp_edu.controller; import static
 * org.junit.Assert.assertEquals; import static org.mockito.Mockito.mock; import
 * static org.mockito.Mockito.when;
 * 
 * import java.lang.reflect.Field; import java.sql.SQLException; import
 * java.util.ArrayList; import java.util.List;
 * 
 * import javax.servlet.http.HttpServletRequest; import
 * javax.servlet.http.HttpServletResponse;
 * 
 * import org.junit.Before; import org.junit.Test; import
 * org.mockito.InjectMocks; import org.mockito.Mock; import org.mockito.Spy;
 * import org.springframework.http.HttpStatus; import
 * org.springframework.http.ResponseEntity;
 * 
 * import com.ojas.obs.emp_edu.controller.EmployeeEducationController; import
 * com.ojas.obs.emp_edu.facadeImpl.EmployeeEducationDetailsFacadeImpl; import
 * com.ojas.obs.emp_edu.model.EmployeeEducationDetails; import
 * com.ojas.obs.emp_edu.model.EmployeeEducationDetailsRequest; import
 * com.ojas.obs.emp_edu.model.EmployeeEducationResponse; import
 * com.ojas.obs.emp_edu.model.ErrorResponse; public class
 * EmployeeEducationControllerTest {
 * 
 * @InjectMocks private EmployeeEducationController employeeEducationController;
 * 
 * 
 * @Mock EmployeeEducationDetailsFacadeImpl employeeEducationFacadeImpl;
 * 
 * @Spy ErrorResponse errorResponse = new ErrorResponse();
 * 
 * @Spy ResponseEntity<Object> responseEntity = new
 * ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
 * 
 * @Spy ResponseEntity<Object> successEntity = new
 * ResponseEntity<>(HttpStatus.OK);
 * 
 * @Spy EmployeeEducationDetailsRequest employeeEducationRequest = new
 * EmployeeEducationDetailsRequest();
 * 
 * @Spy EmployeeEducationResponse employeeEducationResponse = new
 * EmployeeEducationResponse();
 * 
 * @Spy List<EmployeeEducationResponse> passList = new
 * ArrayList<EmployeeEducationResponse>();
 * 
 * 
 * @Before public void beforeTest() { employeeEducationController = new
 * EmployeeEducationController(); employeeEducationFacadeImpl =
 * mock(EmployeeEducationDetailsFacadeImpl.class);
 * 
 * setCollaborator(employeeEducationController, "employeeEducationFacade",
 * employeeEducationFacadeImpl); }
 * 
 * private void setCollaborator(Object object, String name, Object service) {
 * 
 * Field field; try { field = object.getClass().getDeclaredField(name);
 * field.setAccessible(true);
 * 
 * field.set(object, service); } catch (Exception e) {
 * 
 * e.printStackTrace(); } }
 * 
 * public EmployeeEducationDetailsRequest employeeEducationRequest() {
 * 
 * employeeEducationRequest = new EmployeeEducationDetailsRequest();
 * employeeEducationRequest.setEmployeeEducationDetailsList(this.
 * getEmployeeEducation()); employeeEducationRequest.setPageNo(1);
 * employeeEducationRequest.setPageSize(2); return employeeEducationRequest; }
 * 
 * public List<EmployeeEducationDetails> getEmployeeEducation() {
 * EmployeeEducationDetails employeeEducation = new EmployeeEducationDetails();
 * employeeEducation.setId(1); employeeEducation.setEmployeeId("123");
 * employeeEducation.setQualification(2);
 * employeeEducation.setYear_of_passing("2016");
 * employeeEducation.setPercentage_marks("99");
 * employeeEducation.setInstitution_name("ojas");
 * employeeEducation.setCreatedBy("1234");
 * employeeEducation.setUpdatedBy("1234"); employeeEducation.setFlag(true);
 * 
 * List<EmployeeEducationDetails> list = new
 * ArrayList<EmployeeEducationDetails>(); list.add(employeeEducation); return
 * list; } //@Test public void saveTest() { EmployeeEducationDetailsRequest
 * employeeEducationRequest2 = new EmployeeEducationDetailsRequest();
 * employeeEducationRequest2.setEmployeeEducationDetailsList(this.
 * getEmployeeEducation()); employeeEducationRequest2.setPageNo(1);
 * employeeEducationRequest2.setPageSize(2);
 * employeeEducationRequest2.setTransaactionType("save"); HttpServletRequest
 * request = null; HttpServletResponse response = null; try {
 * when(employeeEducationFacadeImpl.setEmployeeEducationDetails(
 * employeeEducationRequest2)).thenReturn(successEntity); } catch (Exception e)
 * { e.printStackTrace(); } ResponseEntity<Object> setemployeeEducation =
 * employeeEducationController.setEmployeeEducationDeatails(
 * employeeEducationRequest2, request, response); HttpStatus statusCode =
 * setemployeeEducation.getStatusCode(); assertEquals(HttpStatus.OK,
 * statusCode); } //@Test public void saveRequestNullTest() {
 * EmployeeEducationDetailsRequest employeeEducationRequest2 = null;
 * 
 * HttpServletRequest request = null; HttpServletResponse response = null; try {
 * when(employeeEducationFacadeImpl.setEmployeeEducationDetails(
 * employeeEducationRequest2)).thenReturn(successEntity); } catch (Exception e)
 * { e.printStackTrace(); } ResponseEntity<Object> setemployeeEducation =
 * employeeEducationController.setEmployeeEducationDeatails(
 * employeeEducationRequest2, request, response); HttpStatus statusCode =
 * setemployeeEducation.getStatusCode();
 * assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode); } //@Test public
 * void saveListNullTest() { HttpServletRequest request = null;
 * HttpServletResponse response = null; EmployeeEducationDetailsRequest
 * employeeEducationRequest2 = new EmployeeEducationDetailsRequest();
 * employeeEducationRequest2.setEmployeeEducationDetailsList(null);
 * employeeEducationRequest2.setPageNo(1);
 * employeeEducationRequest2.setPageSize(2);
 * employeeEducationRequest2.setTransaactionType("save"); try {
 * when(employeeEducationFacadeImpl.setEmployeeEducationDetails(
 * employeeEducationRequest2)).thenReturn(successEntity); } catch (Exception e)
 * { e.printStackTrace(); } ResponseEntity<Object> setemployeeEducation =
 * employeeEducationController.setEmployeeEducationDeatails(
 * employeeEducationRequest2, request, response); HttpStatus statusCode =
 * setemployeeEducation.getStatusCode();
 * assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode); } //@Test public
 * void savePageNoNullNullTest() { EmployeeEducationDetailsRequest
 * employeeEducationRequest2 = new EmployeeEducationDetailsRequest();
 * employeeEducationRequest2.setEmployeeEducationDetailsList(this.
 * getEmployeeEducation()); employeeEducationRequest2.setPageNo(null);
 * employeeEducationRequest2.setPageSize(2);
 * employeeEducationRequest2.setTransaactionType("save"); HttpServletRequest
 * request = null; HttpServletResponse response = null; try {
 * when(employeeEducationFacadeImpl.setEmployeeEducationDetails(
 * employeeEducationRequest2)).thenReturn(successEntity); } catch (Exception e)
 * { e.printStackTrace(); } ResponseEntity<Object> setemployeeEducation =
 * employeeEducationController.setEmployeeEducationDeatails(
 * employeeEducationRequest2, request, response); HttpStatus statusCode =
 * setemployeeEducation.getStatusCode();
 * assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode); } //@Test public
 * void savePageSizeNullTest() { EmployeeEducationDetailsRequest
 * employeeEducationRequest2 = new EmployeeEducationDetailsRequest();
 * employeeEducationRequest2.setEmployeeEducationDetailsList(this.
 * getEmployeeEducation()); employeeEducationRequest2.setPageNo(1);
 * employeeEducationRequest2.setPageSize(null);
 * employeeEducationRequest2.setTransaactionType("save"); HttpServletRequest
 * request = null; HttpServletResponse response = null; try {
 * when(employeeEducationFacadeImpl.setEmployeeEducationDetails(
 * employeeEducationRequest2)).thenReturn(successEntity); } catch (Exception e)
 * { e.printStackTrace(); } ResponseEntity<Object> setemployeeEducation =
 * employeeEducationController.setEmployeeEducationDeatails(
 * employeeEducationRequest2, request, response); HttpStatus statusCode =
 * setemployeeEducation.getStatusCode();
 * assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode); } //@Test public
 * void saveTransactionTypeNullTest() { EmployeeEducationDetailsRequest
 * employeeEducationRequest2 = new EmployeeEducationDetailsRequest();
 * employeeEducationRequest2.setEmployeeEducationDetailsList(this.
 * getEmployeeEducation()); employeeEducationRequest2.setPageNo(1);
 * employeeEducationRequest2.setPageSize(2);
 * employeeEducationRequest2.setTransaactionType(null); HttpServletRequest
 * request = null; HttpServletResponse response = null; try {
 * when(employeeEducationFacadeImpl.setEmployeeEducationDetails(
 * employeeEducationRequest2)).thenReturn(successEntity); } catch (Exception e)
 * { e.printStackTrace(); } ResponseEntity<Object> setemployeeEducation =
 * employeeEducationController.setEmployeeEducationDeatails(
 * employeeEducationRequest2, request, response); HttpStatus statusCode =
 * setemployeeEducation.getStatusCode();
 * assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode); } //@Test public
 * void saveListExceptionTest() { EmployeeEducationDetailsRequest
 * employeeEducationRequest2 = new EmployeeEducationDetailsRequest();
 * employeeEducationRequest2.setEmployeeEducationDetailsList(this.
 * getEmployeeEducation()); employeeEducationRequest2.setPageNo(1);
 * employeeEducationRequest2.setPageSize(2);
 * employeeEducationRequest2.setTransaactionType("save"); HttpServletRequest
 * request = null; HttpServletResponse response = null; try {
 * when(employeeEducationFacadeImpl.setEmployeeEducationDetails(
 * employeeEducationRequest2)).thenThrow(NullPointerException.class); } catch
 * (Exception e) { e.printStackTrace(); } ResponseEntity<Object>
 * setemployeeEducation =
 * employeeEducationController.setEmployeeEducationDeatails(
 * employeeEducationRequest2, request, response); HttpStatus statusCode =
 * setemployeeEducation.getStatusCode(); assertEquals(HttpStatus.CONFLICT,
 * statusCode); } //@Test public void saveListSQLExceptionTest() {
 * EmployeeEducationDetailsRequest employeeEducationRequest2 = new
 * EmployeeEducationDetailsRequest();
 * employeeEducationRequest2.setEmployeeEducationDetailsList(this.
 * getEmployeeEducation()); employeeEducationRequest2.setPageNo(1);
 * employeeEducationRequest2.setPageSize(2);
 * employeeEducationRequest2.setTransaactionType("save"); HttpServletRequest
 * request = null; HttpServletResponse response = null; try {
 * when(employeeEducationFacadeImpl.setEmployeeEducationDetails(
 * employeeEducationRequest2)).thenThrow(SQLException.class); } catch (Exception
 * e) { e.printStackTrace(); } ResponseEntity<Object> setemployeeEducation =
 * employeeEducationController.setEmployeeEducationDeatails(
 * employeeEducationRequest2, request, response); HttpStatus statusCode =
 * setemployeeEducation.getStatusCode(); assertEquals(HttpStatus.CONFLICT,
 * statusCode); } //get method testcases //@Test public void getTest() {
 * EmployeeEducationDetailsRequest employeeEducationRequest2 = new
 * EmployeeEducationDetailsRequest();
 * 
 * employeeEducationRequest2.setPageNo(1);
 * employeeEducationRequest2.setPageSize(2);
 * employeeEducationRequest2.setTransaactionType("getAll"); HttpServletRequest
 * request = null; HttpServletResponse response = null; try {
 * when(employeeEducationFacadeImpl.getEmployeeEducationDetails(
 * employeeEducationRequest2)).thenReturn(successEntity); } catch (Exception e)
 * { e.printStackTrace(); } ResponseEntity<Object> setemployeeEducation =
 * employeeEducationController.getEmployeeEducationDetails(
 * employeeEducationRequest2, request, response); HttpStatus statusCode =
 * setemployeeEducation.getStatusCode(); assertEquals(HttpStatus.OK,
 * statusCode); } //@Test public void getemplEduDetailsRequestObjNulllTest() {
 * EmployeeEducationDetailsRequest employeeEducationRequest2 = null;
 * HttpServletRequest request = null; HttpServletResponse response = null; try {
 * when(employeeEducationFacadeImpl.getEmployeeEducationDetails(
 * employeeEducationRequest2)).thenReturn(successEntity); } catch (Exception e)
 * { e.printStackTrace(); } ResponseEntity<Object> setemployeeEducation =
 * employeeEducationController.getEmployeeEducationDetails(
 * employeeEducationRequest2, request, response); HttpStatus statusCode =
 * setemployeeEducation.getStatusCode();
 * assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode); } //@Test public
 * void getPageNoNullTest() { EmployeeEducationDetailsRequest
 * employeeEducationRequest2 = new EmployeeEducationDetailsRequest();
 * 
 * employeeEducationRequest2.setPageNo(null);
 * employeeEducationRequest2.setPageSize(2);
 * employeeEducationRequest2.setTransaactionType("getAll"); HttpServletRequest
 * request = null; HttpServletResponse response = null; try {
 * when(employeeEducationFacadeImpl.getEmployeeEducationDetails(
 * employeeEducationRequest2)).thenReturn(successEntity); } catch (Exception e)
 * { e.printStackTrace(); } ResponseEntity<Object> setemployeeEducation =
 * employeeEducationController.getEmployeeEducationDetails(
 * employeeEducationRequest2, request, response); HttpStatus statusCode =
 * setemployeeEducation.getStatusCode();
 * assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode); } //@Test public
 * void getPageSizeNullTest() { EmployeeEducationDetailsRequest
 * employeeEducationRequest2 = new EmployeeEducationDetailsRequest();
 * 
 * employeeEducationRequest2.setPageNo(1);
 * employeeEducationRequest2.setPageSize(null);
 * employeeEducationRequest2.setTransaactionType("getAll"); HttpServletRequest
 * request = null; HttpServletResponse response = null; try {
 * when(employeeEducationFacadeImpl.getEmployeeEducationDetails(
 * employeeEducationRequest2)).thenReturn(successEntity); } catch (Exception e)
 * { e.printStackTrace(); } ResponseEntity<Object> setemployeeEducation =
 * employeeEducationController.getEmployeeEducationDetails(
 * employeeEducationRequest2, request, response); HttpStatus statusCode =
 * setemployeeEducation.getStatusCode();
 * assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode); } //@Test public
 * void getTransactionTypeNullTest() { EmployeeEducationDetailsRequest
 * employeeEducationRequest2 = new EmployeeEducationDetailsRequest();
 * 
 * employeeEducationRequest2.setPageNo(1);
 * employeeEducationRequest2.setPageSize(2);
 * employeeEducationRequest2.setTransaactionType(null); HttpServletRequest
 * request = null; HttpServletResponse response = null; try {
 * when(employeeEducationFacadeImpl.getEmployeeEducationDetails(
 * employeeEducationRequest2)).thenReturn(successEntity); } catch (Exception e)
 * { e.printStackTrace(); } ResponseEntity<Object> setemployeeEducation =
 * employeeEducationController.getEmployeeEducationDetails(
 * employeeEducationRequest2, request, response); HttpStatus statusCode =
 * setemployeeEducation.getStatusCode();
 * assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode); } //@Test public
 * void getExceptionTest() { EmployeeEducationDetailsRequest
 * employeeEducationRequest2 = new EmployeeEducationDetailsRequest();
 * 
 * employeeEducationRequest2.setPageNo(1);
 * employeeEducationRequest2.setPageSize(2);
 * employeeEducationRequest2.setTransaactionType("getAll"); HttpServletRequest
 * request = null; HttpServletResponse response = null; try {
 * when(employeeEducationFacadeImpl.getEmployeeEducationDetails(
 * employeeEducationRequest2)).thenThrow(NullPointerException.class); } catch
 * (Exception e) { e.printStackTrace(); } ResponseEntity<Object>
 * setemployeeEducation =
 * employeeEducationController.getEmployeeEducationDetails(
 * employeeEducationRequest2, request, response); HttpStatus statusCode =
 * setemployeeEducation.getStatusCode(); assertEquals(HttpStatus.CONFLICT,
 * statusCode); } //@Test public void getTransactionTest() {
 * EmployeeEducationDetailsRequest employeeEducationRequest2 = new
 * EmployeeEducationDetailsRequest();
 * 
 * employeeEducationRequest2.setPageNo(1);
 * employeeEducationRequest2.setPageSize(2);
 * employeeEducationRequest2.setTransaactionType(null); HttpServletRequest
 * request = null; HttpServletResponse response = null; try {
 * when(employeeEducationFacadeImpl.getEmployeeEducationDetails(
 * employeeEducationRequest2)).thenReturn(successEntity); } catch (Exception e)
 * { e.printStackTrace(); } ResponseEntity<Object> setemployeeEducation =
 * employeeEducationController.getEmployeeEducationDetails(
 * employeeEducationRequest2, request, response); HttpStatus statusCode =
 * setemployeeEducation.getStatusCode();
 * assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode); } //@Test public
 * void getListExceptionTest() { EmployeeEducationDetailsRequest
 * employeeEducationRequest2 = new EmployeeEducationDetailsRequest();
 * employeeEducationRequest2.setEmployeeEducationDetailsList(this.
 * getEmployeeEducation()); employeeEducationRequest2.setPageNo(1);
 * employeeEducationRequest2.setPageSize(2);
 * employeeEducationRequest2.setTransaactionType("getAll"); HttpServletRequest
 * request = null; HttpServletResponse response = null; try {
 * when(employeeEducationFacadeImpl.getEmployeeEducationDetails(
 * employeeEducationRequest2)).thenThrow(NullPointerException.class); } catch
 * (Exception e) { e.printStackTrace(); } ResponseEntity<Object>
 * setemployeeEducation =
 * employeeEducationController.getEmployeeEducationDetails(
 * employeeEducationRequest2, request, response); HttpStatus statusCode =
 * setemployeeEducation.getStatusCode(); assertEquals(HttpStatus.CONFLICT,
 * statusCode); }
 * 
 * 
 * @Test public void getListSQLExceptionTest() { EmployeeEducationDetailsRequest
 * employeeEducationRequest2 = new EmployeeEducationDetailsRequest();
 * employeeEducationRequest2.setPageNo(1);
 * employeeEducationRequest2.setPageSize(2);
 * employeeEducationRequest2.setTransaactionType("getAll"); HttpServletRequest
 * request = null; HttpServletResponse response = null; try {
 * when(employeeEducationFacadeImpl.getEmployeeEducationDetails(
 * employeeEducationRequest2)).thenThrow(SQLException.class); } catch (Exception
 * e) { e.printStackTrace(); } ResponseEntity<Object> setemployeeEducation =
 * employeeEducationController.getEmployeeEducationDetails(
 * employeeEducationRequest2, request, response); HttpStatus statusCode =
 * setemployeeEducation.getStatusCode(); assertEquals(HttpStatus.CONFLICT,
 * statusCode); }
 * 
 * }
 */