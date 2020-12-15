package com.ojas.obs.facade;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
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
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ojas.obs.dao.SeparationTypeDao;
import com.ojas.obs.main.OjasObsOnlineSeparationTypeServiceApplicationTests;
import com.ojas.obs.model.SeparationType;
import com.ojas.obs.request.SeparationTypeRequest;
import com.ojas.obs.utility.ErrorResponse;

//@RunWith(MockitoJUnitRunner.Silent.class)
@SpringBootConfiguration
public class SeparationTypeFacadeTest extends OjasObsOnlineSeparationTypeServiceApplicationTests { 
	@Mock
	SeparationTypeDao separationTypeDao;

	@InjectMocks
	private SeparationTypeFacade separationTypeFacade;
 
	@Spy
	ErrorResponse errorResponse = new ErrorResponse();
	
	/*
	 * @Spy List<SeparationType> separationType = new ArrayList<>();
	 */
	
	
	@Before
	public void beforeTest() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

		separationTypeFacade = new SeparationTypeFacade();
		separationTypeDao = mock(SeparationTypeDao.class);
		setCollabarator(separationTypeFacade, "separationTypeDao", separationTypeDao);

	}
	
	public void setCollabarator(Object object, String name, Object collabarator) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field;
			field = object.getClass().getDeclaredField(name);
			field.setAccessible(true);
			field.set(object, collabarator);
	}
	

	public SeparationTypeRequest separationTypeRequest() {
		/*
		 * SeparationType separationType = new SeparationType();
		 * separationType.setSeparationType("javaaa");
		 * 
		 * List<SeparationType> separationTypeList=new ArrayList<SeparationType>();
		 */

		SeparationTypeRequest sep = new SeparationTypeRequest();
		sep.setTransactionType("save");
		sep.setSeparationType(getModel());
		return sep;

	}

	public List<SeparationType> getModel() {
		SeparationType separationType = new SeparationType();
		List<SeparationType> list = new ArrayList<SeparationType>();
		separationType.setSeparationType("Java dev");
		separationType.setSeparationTypeId(145);
		list.add(separationType);
		return list;
	}
 
	@Test
	public void testSetSeparationTypeTransave() throws SQLException {
		// SeparationTypeRequest separationTypeRequest = new SeparationTypeRequest();
		// separationTypeRequest.setTransactionType("save");
		when(separationTypeDao.saveSeparationType(any())).thenReturn(true);
		ResponseEntity<Object> saveSeparationType = separationTypeFacade
				.setSeparationTypeDetails(separationTypeRequest());
		HttpStatus status = saveSeparationType.getStatusCode();
		assertEquals(HttpStatus.OK, status);

	}
	
	
	@Test
	public void testSetSeparationTypeTranNotsave() throws SQLException {
		// SeparationTypeRequest separationTypeRequest = new SeparationTypeRequest();
		// separationTypeRequest.setTransactionType("save");
		when(separationTypeDao.saveSeparationType(any())).thenReturn(false);
		ResponseEntity<Object> saveSeparationType = separationTypeFacade
				.setSeparationTypeDetails(separationTypeRequest());
		HttpStatus status = saveSeparationType.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, status);

	}
	
	
	
	
	 
	
	

	@Test
	public void testSetSeparationTypeTransUp() throws SQLException {
		SeparationTypeRequest separationTypeRequest = new SeparationTypeRequest();
		separationTypeRequest.setTransactionType("update");
		when(separationTypeDao.updateSeparationType(any())).thenReturn(true);
		ResponseEntity<Object> updateSeparationType = separationTypeFacade
				.setSeparationTypeDetails(separationTypeRequest);
		HttpStatus status = updateSeparationType.getStatusCode();
		assertEquals(HttpStatus.OK, status);

	}
	
	
	
	

	@Test
	public void testSetSeparationTypeTransNotUp() throws SQLException {
		SeparationTypeRequest separationTypeRequest = new SeparationTypeRequest();
		separationTypeRequest.setTransactionType("update");
		when(separationTypeDao.updateSeparationType(any())).thenReturn(false);
		ResponseEntity<Object> updateSeparationType = separationTypeFacade
				.setSeparationTypeDetails(separationTypeRequest); 
		HttpStatus status = updateSeparationType.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, status);

	}

	
	@Test
	public void testSetSeparationTypeDuplicate() throws SQLException {
		SeparationTypeRequest separationTypeRequest = new SeparationTypeRequest();
		separationTypeRequest.setTransactionType("/update");
		Throwable cause = new Throwable();
		when(separationTypeDao.updateSeparationType(separationTypeRequest)).thenThrow(new DuplicateKeyException(null, cause));
		 separationTypeFacade.setSeparationTypeDetails(separationTypeRequest);
	

	}

	@Test
	public void testSeparationtypeCatch() throws SQLException { 
		SeparationTypeRequest separationTypeRequest = new SeparationTypeRequest();
		separationTypeRequest.setTransactionType("/update");
		Throwable ca = new Throwable();
		when(separationTypeDao.updateSeparationType(any())).thenThrow(new RuntimeException());
		separationTypeFacade.setSeparationTypeDetails(separationTypeRequest);
	

	}
	
	
	@Test
	public void testSeparationtypenotCatch() throws SQLException { 
		SeparationTypeRequest separationTypeRequest = new SeparationTypeRequest();
		separationTypeRequest.setTransactionType("");
		when(separationTypeDao.updateSeparationType(any())).thenThrow(new RuntimeException());
		separationTypeFacade.setSeparationTypeDetails(separationTypeRequest);
		

	}
	
	

	/*
	 * @Test public void testSetSeparationTypeTransDel() throws SQLException {
	 * SeparationTypeRequest separationTypeRequest = new SeparationTypeRequest();
	 * List<SeparationType> separationTypeList = getModel();
	 * separationTypeRequest.setTransactionType("/delete");
	 * separationTypeRequest.setSeparationType(separationTypeList);
	 * separationTypeRequest.setSessionId("3231");
	 * 
	 * // separationTypeRequest.setSeparationType(separationType); for
	 * (SeparationType sepa : separationTypeList) {
	 * 
	 * when(separationTypeDao.deleteSeparationType(sepa.getSeparationTypeId())).
	 * thenReturn(true); ResponseEntity<Object> deleteSeparationType =
	 * separationTypeFacade .setSeparationTypeDetails(separationTypeRequest);
	 * HttpStatus status = deleteSeparationType.getStatusCode();
	 * assertEquals(HttpStatus.OK, status); }
	 * 
	 * }
	 * 
	 * @Test public void testSetSeparationTypeTransNotDel() throws SQLException {
	 * SeparationTypeRequest separationTypeRequest = new SeparationTypeRequest();
	 * List<SeparationType> separationTypeList = getModel();
	 * separationTypeRequest.setTransactionType("/delete");
	 * separationTypeRequest.setSeparationType(separationTypeList);
	 * separationTypeRequest.setSessionId("3231");
	 * 
	 * // separationTypeRequest.setSeparationType(separationType); for
	 * (SeparationType sepa : separationTypeList) {
	 * 
	 * when(separationTypeDao.deleteSeparationType(sepa.getSeparationTypeId())).
	 * thenReturn(false); ResponseEntity<Object> deleteSeparationType =
	 * separationTypeFacade .setSeparationTypeDetails(separationTypeRequest);
	 * HttpStatus status = deleteSeparationType.getStatusCode();
	 * assertEquals(HttpStatus.CONFLICT, status); }
	 * 
	 * }
	 * 
	 * @Test public void testSeparationtypeCatch() throws SQLException {
	 * SeparationTypeRequest separationTypeRequest = new SeparationTypeRequest();
	 * List<SeparationType> separationList = getModel();
	 * //designationRequest.setTransactionType("delete");
	 * separationTypeRequest.setSeparationType(separationList);
	 * separationTypeRequest.setSessionId("3231");
	 * when(separationTypeDao.deleteSeparationType(anyInt())).thenReturn(false);
	 * ResponseEntity<Object> deleteSeparation =
	 * separationTypeFacade.setSeparationTypeDetails(separationTypeRequest);
	 * HttpStatus status = deleteSeparation.getStatusCode();
	 * assertEquals(HttpStatus.CONFLICT, status); }
	 */
	
	
	
	
	
	
	
	
	
	@Test
	public void testGetSeaparationTypeListEmpty() throws SQLException { 
		SeparationTypeRequest separationTypeRequest = new SeparationTypeRequest();
		List<SeparationType> separationTypeList = Collections.emptyList();
		separationTypeRequest.setSeparationType(separationTypeList);
		separationTypeRequest.setTransactionType("getall");
		when(separationTypeDao.getAllSeparationType()).thenReturn(separationTypeList);
		ResponseEntity<Object> getSeparation = separationTypeFacade.getSeparationType(separationTypeRequest);
		HttpStatus status=getSeparation.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, status); 
		 
	}
	
	
	
	
	
	
	  
	
		@Test
		public void testGetSeaparationTypeListnotempty() throws SQLException { 
			SeparationType separationType = new SeparationType();
			List<SeparationType> separationTypeList = new ArrayList<>();
			separationType.setSeparationType("Java dev");
			separationType.setSeparationTypeId(145);
			separationTypeList.add(separationType);
			SeparationTypeRequest separationTypeRequest = new SeparationTypeRequest();
			separationTypeRequest.setSeparationType(separationTypeList);
			separationTypeRequest.setTransactionType("getall");
			when(separationTypeDao.getAllSeparationType()).thenReturn(separationTypeList);
			ResponseEntity<Object> getSeparation = separationTypeFacade.getSeparationType(separationTypeRequest);
			HttpStatus status=getSeparation.getStatusCode();
			assertEquals(HttpStatus.OK, status); 
			 
		}
		
		
		@Test
		public void testGetSeparationGETBYID() throws SQLException { 
			SeparationTypeRequest separationTypeRequest = new SeparationTypeRequest();
			List<SeparationType> separationTypeList = new ArrayList<>();
			separationTypeRequest.setSeparationType(separationTypeList);
			 /* List<Designation> designationlist = Collections.emptyList();
			 * designationRequest.setDesignation(designationlist);
			 */
			separationTypeRequest.setTransactionType("getbyid");
			when(separationTypeDao.getById(separationTypeRequest())).thenReturn(separationTypeList);
			List<SeparationType> designationlist = Collections.emptyList();
			separationTypeRequest.setSeparationType(designationlist);
			ResponseEntity<Object> getsepararation = separationTypeFacade.getSeparationType(separationTypeRequest);
			HttpStatus status = getsepararation.getStatusCode();
			assertEquals(HttpStatus.CONFLICT, status);

		} 
		
		@Test
		public void testGetSeparationGETBYIDElse() throws SQLException { 
			SeparationTypeRequest separationTypeRequest = new SeparationTypeRequest();
			//List<SeparationType> separationTypeList = new ArrayList<>();
			//separationTypeRequest.setSeparationType(separationTypeList);
			
			
			separationTypeRequest.setTransactionType("getbyid");
			when(separationTypeDao.getById(separationTypeRequest)).thenReturn(getModel());
			//List<SeparationType> designationlist = Collections.emptyList();
			//separationTypeRequest.setSeparationType(designationlist);
			ResponseEntity<Object> getsepararation = separationTypeFacade.getSeparationType(separationTypeRequest);
			HttpStatus status = getsepararation.getStatusCode();
			assertEquals(HttpStatus.OK, status);

		} 
		 
		@Test
		public void testGetSeaparationTypeListreturn() throws SQLException { 
			SeparationTypeRequest separationTypeRequest = new SeparationTypeRequest();
			separationTypeRequest.setTransactionType("");
			ResponseEntity<Object> getSeparation = separationTypeFacade.getSeparationType(separationTypeRequest);
			HttpStatus status=getSeparation.getStatusCode();
			assertEquals(HttpStatus.OK, status); 
			 
		}
		
		
		
		


}
