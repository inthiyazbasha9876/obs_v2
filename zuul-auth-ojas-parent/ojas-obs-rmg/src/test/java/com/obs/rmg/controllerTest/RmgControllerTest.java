package com.obs.rmg.controllerTest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.obs.rmg.rmgcontroller.RmgController;
import com.obs.rmg.rmgfacade.RmgFacade;
import com.obs.rmg.rmgmodel.ProjectList;
import com.obs.rmg.rmgmodel.RMG;
import com.obs.rmg.rmgmodel.RmgGeneric;
import com.obs.rmg.rmgmodel.RmgSpecific;
import com.obs.rmg.rmgmodel.SkillsList;
import com.obs.rmg.rmgrequest.RmgRequest;
import com.obs.rmg.rmgresponse.ErrorResponse;
import com.obs.rmg.rmgresponse.RmgResponse;



public class RmgControllerTest 
{
	@InjectMocks
	RmgController rmgcontroller;
	@Mock
	RmgFacade rmgFacade;
	@Spy
	RmgGeneric rg =new RmgGeneric();
	@Spy
	RMG rmg=new RMG();
	@Spy
	SkillsList sk= new SkillsList();
	@Spy
	RmgRequest rmgrequest=new RmgRequest();
	@Spy
	RmgSpecific rs=new RmgSpecific();
	@Spy
	ErrorResponse errorresponse;
	@Spy
	RmgResponse  rmgresponse;
	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorresponse, HttpStatus.UNPROCESSABLE_ENTITY);
	@Spy
	ResponseEntity<Object> conflict = new ResponseEntity<>(errorresponse, HttpStatus.CONFLICT);
	@Spy
	ResponseEntity<Object> successResponse = new ResponseEntity<>(rmgresponse, HttpStatus.OK);
	@Before
	public void init() throws Exception {
		rmgcontroller=new RmgController();
		rmgFacade = mock(RmgFacade.class);
		setCollaborator(rmgcontroller, "rmgFacade", rmgFacade);
	}
	private void setCollaborator(Object object, String name, Object service) throws Exception {
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);
	}
	public RMG getRMGList() {
		RMG rmgdatalist = new RMG();
		rmgdatalist.setBookingId(0);
		rmgdatalist.setResourceType("specific");
		rmgdatalist.setFlag(null);
		rmgdatalist.setMessage("data");
		rmgdatalist.setStatus("accept");
		return rmgdatalist;
	}
	@Test
	public void rmgListRequestNullTest() throws SQLException, IOException{
		rmgrequest=null;
		HttpServletRequest request = null;
    	HttpServletResponse response = null;	
		when(rmgFacade.setRmg(rmgrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = rmgcontroller.setCustomerContactInfo(rmgrequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	@Test
	public void rmgTransationTypeNullTest() throws SQLException, IOException{
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	rmgrequest.setTransactiontype(null);
		when(rmgFacade.setRmg(rmgrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = rmgcontroller.setCustomerContactInfo(rmgrequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	@Test
	public void rmgTransationTypeEmptyTest() throws SQLException, IOException{
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	rmgrequest.setRmgInfo(rmg);
    	rmgrequest.setTransactiontype(null);
		when(rmgFacade.setRmg(rmgrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = rmgcontroller.setCustomerContactInfo(rmgrequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	@Test
	public void rmginfoRequestspecificsaveFailTest() throws SQLException, IOException{
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	rmgrequest.setTransactiontype("save");
    	rmg.setResourceType("specific");
    	rmgrequest.setRmgInfo(rmg);
    	Set<RmgSpecific> l=new HashSet<RmgSpecific>();
    	rs.setBillRate(0.0f);rs.setEmployeeId(null);rs.setStartDate(null);rs.setEndDate(null);l.add(rs);
    	rmgrequest.setRmgSpecificList(l);
		when(rmgFacade.setRmg(rmgrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = rmgcontroller.setCustomerContactInfo(rmgrequest, request, response);	
		HttpStatus status = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}
	@Test
	public void rmginfoRequestspecificsaveTest() throws SQLException, IOException{
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	rmgrequest.setTransactiontype("save");
    	rmg.setResourceType("specific");
    	rmgrequest.setRmgInfo(rmg);
    	Set<RmgSpecific> l=new HashSet<RmgSpecific>();
    	rs.setBillRate(0.5f);rs.setEmployeeId("125");rs.setStartDate("20-12-2015");rs.setEndDate("20-12-2015");l.add(rs);
    	rmgrequest.setRmgSpecificList(l);
		when(rmgFacade.setRmg(rmgrequest)).thenReturn(successResponse);
		ResponseEntity<Object> setservice = rmgcontroller.setCustomerContactInfo(rmgrequest, request, response);	
		HttpStatus status = setservice.getStatusCode();
		assertEquals(HttpStatus.OK, status);
	}
	@Test
	public void rmginfoRequesGenarictsaveFailTest() throws SQLException, IOException{
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	rmgrequest.setTransactiontype("save");
    	rmg.setResourceType("generic");
    	rmgrequest.setRmgInfo(rmg);
    	Set<RmgGeneric> ls=new HashSet<RmgGeneric>();
       rg.setResourceSkills(null);rg.setBillRate(0.0f); rg.setStartDate(null);rg.setEndDate(null);
      rg.setResourceExperience(null); rg.setResourceCount(0);ls.add(rg);
       rmgrequest.setRmgGenericList(ls);
		when(rmgFacade.setRmg(rmgrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = rmgcontroller.setCustomerContactInfo(rmgrequest, request, response);	
		HttpStatus status = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}
	@Test
	public void rmginfoRequesGenarictsaveTest() throws SQLException, IOException{
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	rmgrequest.setTransactiontype("save");
    	rmg.setResourceType("generic");
    	rmgrequest.setRmgInfo(rmg);
    	Set<RmgGeneric> ls=new HashSet<RmgGeneric>();	
      rg.setBillRate(82.6f);rg.setEndDate("22-10-1995");rg.setStartDate("22-10-1995");
       rmgrequest.setRmgGenericList(ls);
		when(rmgFacade.setRmg(rmgrequest)).thenReturn(successResponse);
		ResponseEntity<Object> setservice = rmgcontroller.setCustomerContactInfo(rmgrequest, request, response);	
		HttpStatus status = setservice.getStatusCode();
		assertEquals(HttpStatus.OK, status);
	}
	@Test
	public void rmginfoRequestupdateSpecifiTest() throws SQLException, IOException{
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	rmgrequest.setTransactiontype("update");
    	rmg.setResourceType("specific");rmg.setBookingId(0);rmg.setStatus("pending");
    	rmgrequest.setRmgInfo(rmg);
		when(rmgFacade.setRmg(rmgrequest)).thenReturn(successResponse);
		ResponseEntity<Object> setservice = rmgcontroller.setCustomerContactInfo(rmgrequest, request, response);
		HttpStatus status = setservice.getStatusCode();
		assertEquals(HttpStatus.OK, status);
	}
	@Test
	public void rmginfoRequestupdateSpecificFailTest() throws SQLException, IOException{
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	rmgrequest.setTransactiontype("update");
    	rmg.setResourceType("specific");rmg.setBookingId(0);rmg.setStatus(null);
    	rmgrequest.setRmgInfo(rmg);
		when(rmgFacade.setRmg(rmgrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = rmgcontroller.setCustomerContactInfo(rmgrequest, request, response);
		HttpStatus status = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}
	@Test
	public void rmginfoRequestupdateGenericFailTest() throws SQLException, IOException{
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	rmgrequest.setTransactiontype("update");
    	rmg.setResourceType("generic");rmg.setBookingId(0);rmg.setStatus(null);
    	rmgrequest.setRmgInfo(rmg);
		when(rmgFacade.setRmg(rmgrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = rmgcontroller.setCustomerContactInfo(rmgrequest, request, response);
		HttpStatus status = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}
	@Test
	public void rmginfoRequestdeleteTest() throws SQLException, IOException	{
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	rmgrequest.setTransactiontype("delete");
    	rmg.setBookingId(10);rmg.setFlag(true);
    	rmgrequest.setRmgInfo(rmg);
		when(rmgFacade.setRmg(rmgrequest)).thenReturn(successResponse);
		ResponseEntity<Object> setservice = rmgcontroller.setCustomerContactInfo(rmgrequest, request, response);
		HttpStatus status = setservice.getStatusCode();
		assertEquals(HttpStatus.OK, status);
	}
	@Test
	public void rmginfoRequestdeleteFailTest() throws SQLException, IOException{
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	rmgrequest.setTransactiontype("delete");
        rmg.setBookingId(0);rmg.setFlag(null);
        rmgrequest.setRmgInfo(rmg);
		when(rmgFacade.setRmg(rmgrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = rmgcontroller.setCustomerContactInfo(rmgrequest, request, response);
		HttpStatus status = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}
	@Test
	public void setDuplicateKeyExceptionTest() throws Exception {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	rmg.setBookingId(1);
    	rmg.setFlag(true);
    	rmgrequest.setRmgInfo(rmg);
    	rmgrequest.setTransactiontype("jj");	
    	when(rmgFacade.setRmg(rmgrequest)).thenThrow(new DuplicateKeyException(null,new Throwable()));   
		ResponseEntity<Object> setBus = rmgcontroller.setCustomerContactInfo(rmgrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}
	
	@Test
	public void setExceptionTest() throws Exception	{
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	rmg.setBookingId(1);
    	rmg.setFlag(false);
    	rmgrequest.setRmgInfo(rmg);
    	rmgrequest.setTransactiontype("jj");	
    	when(rmgFacade.setRmg(rmgrequest)).thenThrow(RuntimeException.class);
		ResponseEntity<Object> setBus = rmgcontroller.setCustomerContactInfo(rmgrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}
	
	//gettestcases
	
	@Test
	public void getException() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	rmgrequest.setRmgInfo(this.getRMGList());
    	rmgrequest.setTransactiontype("getAll");	
    	when(rmgFacade.getRmg(rmgrequest)).thenThrow(RuntimeException.class);
		ResponseEntity<Object> setBus = rmgcontroller.getCustomerContactInfoDetails(rmgrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}
	
	@Test
	public void gettransactiontyprnull() throws SQLException 	{
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	rmgrequest.setTransactiontype(null);
		when(rmgFacade.setRmg(rmgrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = rmgcontroller.getCustomerContactInfoDetails(rmgrequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	@Test
	public void gettransactiontypeEmpty() throws SQLException 	{
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	rmgrequest.setTransactiontype("");
		when(rmgFacade.setRmg(rmgrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = rmgcontroller.getCustomerContactInfoDetails(rmgrequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	
	@Test
	public void gettransactiontypegetbyidFail() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	rmgrequest.setTransactiontype("getById");
    	rmg.setBookingId(0);
    	rmgrequest.setRmgInfo(rmg);
		when(rmgFacade.setRmg(rmgrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = rmgcontroller.getCustomerContactInfoDetails(rmgrequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	@Test
	public void gettransactiontypegetbyid() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	rmgrequest.setTransactiontype("getById");
    	rmg.setBookingId(10);
    	rmgrequest.setRmgInfo(rmg);
		when(rmgFacade.getRmg(rmgrequest)).thenReturn(successResponse);
		ResponseEntity<Object> setservice = rmgcontroller.getCustomerContactInfoDetails(rmgrequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.OK, unitCode);
	}
	
	@Test
	public void gettransactiontypegetskillsFail() throws SQLException {
	    HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	rmgrequest.setSkilllist(null);
    	rmgrequest.setTransactiontype("getskills");
		when(rmgFacade.getRmg(rmgrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = rmgcontroller.getCustomerContactInfoDetails(rmgrequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	@Test
	public void getProjectsFail() throws SQLException {
	    HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	ProjectList pl=new ProjectList();
    	pl=null;
    	rmgrequest.setTransactiontype("getprojects");
    	rmgrequest.setProjectlist(pl);
		when(rmgFacade.getRmg(rmgrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = rmgcontroller.getCustomerContactInfoDetails(rmgrequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}
	@Test
	public void getProjects() throws SQLException {
	    HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	ProjectList pl=new ProjectList();
    	rmgrequest.setTransactiontype("getprojects");
    	rmgrequest.setProjectlist(pl);
		when(rmgFacade.getRmg(rmgrequest)).thenReturn(successResponse);
		ResponseEntity<Object> setservice = rmgcontroller.getCustomerContactInfoDetails(rmgrequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.OK, unitCode);
	}
	@Test
	public void gettransactiontypegetskills() throws SQLException {
	    HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	List<String> lo=new ArrayList<String>();
    	SkillsList sko=new SkillsList();
    	sko.setResourceSkills(lo);
    	sko.setResourceExperience(2.2);
    	lo.add("java");
    	rmgrequest.setTransactiontype("getskills");
    	
    	rmgrequest.setSkilllist(sko);
		when(rmgFacade.getRmg(rmgrequest)).thenReturn(successResponse);
		ResponseEntity<Object> setservice = rmgcontroller.getCustomerContactInfoDetails(rmgrequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.OK, unitCode);
	}
	
	@Test
	public void gettransactiontypegetallsuccess() throws SQLException {
		HttpServletRequest request = null;
    	HttpServletResponse response = null;
    	rmgrequest.setRmgInfo(this.getRMGList());
    	rmgrequest.setTransactiontype("jj");
		when(rmgFacade.getRmg(rmgrequest)).thenReturn(successResponse);
		ResponseEntity<Object> setservice = rmgcontroller.getCustomerContactInfoDetails(rmgrequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.OK, unitCode);
	}
	
}