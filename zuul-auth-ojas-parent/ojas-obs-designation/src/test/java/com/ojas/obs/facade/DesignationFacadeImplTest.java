package com.ojas.obs.facade;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyObject;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.ArgumentMatchers.anyInt;
//import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.SpringBootConfiguration;
//import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ojas.main.OjasObsDesignationApplicationTests;
import com.ojas.obs.model.Designation;
import com.ojas.obs.dao.DesignationDao;
import com.ojas.obs.request.DesignationRequest;
//import com.ojas.obs.response.DesignationResponse;
import com.ojas.obs.utility.ErrorResponse;

@SpringBootConfiguration 
public class DesignationFacadeImplTest extends OjasObsDesignationApplicationTests {

	@Mock
	DesignationDao designationDao;

	@InjectMocks
	DesignationFacadeImpl designationFacadeImpl;
	@Spy
	ErrorResponse errorResponse = new ErrorResponse();
	@Spy
	ResponseEntity<Object> sucessResponse = new ResponseEntity<>(errorResponse, HttpStatus.OK);
	
	

	@Before
	public void init() {
		designationFacadeImpl = new DesignationFacadeImpl();
		designationDao = mock(DesignationDao.class);
		setCollabarator(designationFacadeImpl, "designationDao", designationDao);

	}

	public void setCollabarator(Object object, String name, Object collabarator) {
		Field field;
		try {
			field = object.getClass().getDeclaredField(name);
			field.setAccessible(true);
			field.set(object, collabarator);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DesignationRequest designationRequest() {

		/*
		 * Designation designation = new Designation();
		 * designation.setDesignation("javaaa"); List<Designation> designationList=new
		 * ArrayList<Designation>(); designationList.add(designation);
		 */

		DesignationRequest des = new DesignationRequest();
		des.setDesignation(getModel());
		des.setTransactionType("save");
		
		return des;
	}

	public List<Designation> getModel() {
		Designation designation = new Designation();
		List<Designation> list = new ArrayList<Designation>();
		designation.setDesignation("Java dev");
		designation.setId(145);
		list.add(designation);
		return list;
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testSetDesignation() throws SQLException {
		DesignationRequest designationRequest = new DesignationRequest();
		designationRequest.setTransactionType("");
		when(designationDao.saveDesignation(anyObject())).thenReturn(true);
		designationFacadeImpl.setDesignation(designationRequest);
		//HttpStatus status = saveDesignation.getStatusCode();
		//assertEquals(HttpStatus.OK, status);

	}

	@SuppressWarnings("deprecation")
	@Test
	public void testSetDesignationTransave() throws SQLException {
		// SeparationTypeRequest separationTypeRequest = new SeparationTypeRequest();
		// separationTypeRequest.setTransactionType("save");
		when(designationDao.saveDesignation(anyObject())).thenReturn(true);
		ResponseEntity<Object> saveDesignation = designationFacadeImpl.setDesignation(designationRequest());
		HttpStatus status = saveDesignation.getStatusCode();
		assertEquals(HttpStatus.OK, status);

	}

	@SuppressWarnings("deprecation")
	@Test
	public void testSetDesignationTranNotsave() throws SQLException {
		// SeparationTypeRequest separationTypeRequest = new SeparationTypeRequest();
		// separationTypeRequest.setTransactionType("save");
		when(designationDao.saveDesignation(anyObject())).thenReturn(false);
		ResponseEntity<Object> saveSeparationType = designationFacadeImpl.setDesignation(designationRequest());
		HttpStatus status = saveSeparationType.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);

	}

	@SuppressWarnings("deprecation") 
	@Test
	public void testSetDesignationTransUp() throws SQLException {
		DesignationRequest designationRequest = new DesignationRequest();
		designationRequest.setTransactionType("update");
		when(designationDao.updateDesignation(anyObject())).thenReturn(true);
		ResponseEntity<Object> updateDesignation = designationFacadeImpl.setDesignation(designationRequest);
		HttpStatus status = updateDesignation.getStatusCode();
		assertEquals(HttpStatus.OK, status);

	} 

	@SuppressWarnings("deprecation")
	@Test
	public void testSetDesignationTransNotUp() throws SQLException {
		DesignationRequest designationRequest = new DesignationRequest();
		designationRequest.setTransactionType("update");
		when(designationDao.updateDesignation(anyObject())).thenReturn(false);
		ResponseEntity<Object> updateDesignation = designationFacadeImpl.setDesignation(designationRequest);
		HttpStatus status = updateDesignation.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);

	}
	
	@Test
	public void testGetDesignation() throws SQLException { 
		
		DesignationRequest designationRequest = new DesignationRequest();
		List<Designation> list = new ArrayList<Designation>();
		Designation designation = new Designation();
		designation.setDesignation("ab");
		designation.setId(1);  
		list.add(designation);
		designationRequest.setTransactionType("/getbyid");
		when(designationDao.getById(designationRequest)).thenThrow(new RuntimeException());
		
		//when(designationDao.getAllDesignationCount()).thenReturn(count);
		designationFacadeImpl.getDesignation(designationRequest);
		
		
	}
	
	 	
	
	@Test
	public void testGetDesignationListEmpty() throws SQLException { 

		DesignationRequest designationRequest = new DesignationRequest(); 
		List<Designation> designationlist = Collections.emptyList();
		designationRequest.setDesignation(designationlist);
		designationRequest.setTransactionType("getall");
		when(designationDao.getAll(designationRequest())).thenReturn(designationlist);
		ResponseEntity<Object> getDesignation = designationFacadeImpl.getDesignation(designationRequest);
		HttpStatus status = getDesignation.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);

	} 
	 
	@Test
	public void testGetDesignationListNull() throws SQLException {
		//List<Designation> designationDetails = null;
		DesignationRequest designationRequest = new DesignationRequest(); 
		List<Designation> designationlist = null;
		designationRequest.setDesignation(designationlist);
		designationRequest.setTransactionType("getall");

		when(designationDao.getAll(designationRequest())).thenReturn(designationlist);

		// List<SeparationType> seplist = separationTypeDao.getAllSeparationType();
		// seplist.add(separationTypeDao.getAllSeparationType();
	
		ResponseEntity<Object> getDesignation = designationFacadeImpl.getDesignation(designationRequest);
		HttpStatus status = getDesignation.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);

	}  
	

	@Test
	public void testGetDesignationCountListNotEmpty() throws SQLException { 
		int count=1;
		DesignationRequest designationRequest = new DesignationRequest();
		List<Designation> list = new ArrayList<Designation>();
		Designation designation = new Designation();
		designation.setDesignation("ab");
		designation.setId(1); 
		list.add(designation);
		designationRequest.setTransactionType("getall");
		when(designationDao.getAll(designationRequest)).thenReturn(list);
		when(designationDao.getAllDesignationCount()).thenReturn(count);
		//when(designationDao.getAllDesignationCount()).thenReturn(count);
		ResponseEntity<Object> getDesignation = designationFacadeImpl.getDesignation(designationRequest);
		HttpStatus status = getDesignation.getStatusCode();
		assertEquals(HttpStatus.OK, status);
	}
 

	@Test
	public void testGetDesignationGETBYID() throws SQLException { 
		//DesignationResponse designationResponse = new DesignationResponse();
		
		  DesignationRequest designationRequest = new DesignationRequest();
		  List<Designation> list = new ArrayList<Designation>();
		  designationRequest.setDesignation(list);
		 /* List<Designation> designationlist = Collections.emptyList();
		 * designationRequest.setDesignation(designationlist);
		 */
		designationRequest.setTransactionType("getbyid");
		when(designationDao.getById(designationRequest())).thenReturn(list);
		List<Designation> designationlist = Collections.emptyList();
		 designationRequest.setDesignation(designationlist);
		ResponseEntity<Object> getDesignation = designationFacadeImpl.getDesignation(designationRequest);
		HttpStatus status = getDesignation.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, status);

	} 
	
	@Test
	public void testGetDesignationnotGETBYID() throws SQLException { 
		
		DesignationRequest designationRequest = new DesignationRequest();
		List<Designation> list = new ArrayList<Designation>();
		Designation designation = new Designation();
		designation.setDesignation("ab");
		designation.setId(1);  
		list.add(designation);
		designationRequest.setTransactionType("getbyid");
		when(designationDao.getById(designationRequest)).thenReturn(list);
		
		//when(designationDao.getAllDesignationCount()).thenReturn(count);
		ResponseEntity<Object> getDesignation = designationFacadeImpl.getDesignation(designationRequest);
		HttpStatus status = getDesignation.getStatusCode();
		assertEquals(HttpStatus.OK, status);
	}
	
	
	
	@Test
	public void testgetDesignationGetAll() throws SQLException { 
		// SeparationTypeRequest separationTypeRequest = new SeparationTypeRequest();
		// separationTypeRequest.setTransactionType("save");
		DesignationRequest designationRequest = new DesignationRequest();
		designationRequest.setTransactionType("getall");
		designationRequest.setDesignation(getModel());
		when(designationDao.getAll(designationRequest())).thenReturn(getModel());
		ResponseEntity<Object> getDesignation = designationFacadeImpl.getDesignation(designationRequest());
		HttpStatus status = getDesignation.getStatusCode();
		assertEquals(HttpStatus.OK, status);
 
	}
	
	
	/*
	 * @SuppressWarnings("deprecation")
	 * 
	 * @Test public void testSetDesignationTransDuplicate() throws SQLException {
	 * DesignationRequest designationRequest = new DesignationRequest();
	 * designationRequest.setTransactionType("update"); Throwable cause = new
	 * Throwable();
	 * when(designationDao.updateDesignation(anyObject())).thenThrow(new
	 * DuplicateKeyException(null, cause)); ResponseEntity<Object> updateDesignation
	 * = designationFacadeImpl.setDesignation(designationRequest); HttpStatus status
	 * = updateDesignation.getStatusCode(); assertEquals(HttpStatus.CONFLICT,
	 * status);
	 * 
	 * }
	 * 
	 * 
	 * 
	 * 
	 * @Test public void testSetDesignationCatch() throws SQLException {
	 * DesignationRequest designationRequest = new DesignationRequest();
	 * List<Designation> designationList = getModel(); //
	 * designationRequest.setTransactionType("delete");
	 * designationRequest.setDesignation(designationList); Throwable cause = new
	 * Throwable();
	 * when(designationDao.updateDesignation(designationRequest())).thenThrow(new
	 * DuplicateKeyException(null, cause)); ResponseEntity<Object> updateDesignation
	 * = designationFacadeImpl.setDesignation(designationRequest); HttpStatus status
	 * = updateDesignation.getStatusCode(); assertEquals(HttpStatus.CONFLICT,
	 * status); }
	 */
	
}
