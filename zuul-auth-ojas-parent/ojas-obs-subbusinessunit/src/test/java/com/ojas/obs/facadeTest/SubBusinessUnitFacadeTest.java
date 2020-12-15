package com.ojas.obs.facadeTest;

import static org.junit.Assert.assertEquals;
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
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ojas.obs.dao.SubBusinessUnitDao;
import com.ojas.obs.daoimpl.SubBusinessUnitDaoImpl;
import com.ojas.obs.error.ErrorResponse;
import com.ojas.obs.facadeimpl.SubBusinessUnitFacadeImpl;
import com.ojas.obs.model.SubBusinessUnit;
import com.ojas.obs.request.SubBusinessUnitRequest;
import com.ojas.obs.response.SubBusinessUnitResponse;

public class SubBusinessUnitFacadeTest {

	@Mock
	SubBusinessUnitDao subBusinessUnitDao;

	@Mock
	SubBusinessUnitDaoImpl subBusinessUnitDaoImpl;

	@InjectMocks
	private SubBusinessUnitFacadeImpl subBusinessUnitFacadeImpl;

	@Spy
	ErrorResponse errorResponse = new ErrorResponse();

	@Spy
	SubBusinessUnitRequest subBusinessUnitRequest;

	@Spy
	SubBusinessUnitResponse subBusinessUnitResponse;

	@Spy
	SubBusinessUnit subBusinessUnit;

	@Spy
	List<SubBusinessUnit> subBusinessUnitList = new ArrayList<SubBusinessUnit>();

	@Before
	public void init() {
		subBusinessUnitList.add(subBusinessUnit);
		subBusinessUnitFacadeImpl = new SubBusinessUnitFacadeImpl();
		subBusinessUnitDaoImpl = mock(SubBusinessUnitDaoImpl.class);
		setCollaborator(subBusinessUnitFacadeImpl, "subBusinessDAO", subBusinessUnitDaoImpl);
	}

	private void setCollaborator(Object object, String name, Object service) {

		Field field;
		try {
			field = object.getClass().getDeclaredField(name);
			field.setAccessible(true);

			field.set(object, service);
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public SubBusinessUnitRequest subBusinessUnitRequest() {
		subBusinessUnitRequest = new SubBusinessUnitRequest();
		SubBusinessUnit subBusinessUnit1 = new SubBusinessUnit();
		subBusinessUnit1.setId(1);
		subBusinessUnit1.setName("sunil");
		subBusinessUnit1.setBusinessUnitId("123");
		//subBusinessUnit1.setCostCenterId(123);
		subBusinessUnitList = new ArrayList<>();
		subBusinessUnitList.add(subBusinessUnit1);
		subBusinessUnitRequest.setTransactionType("save");
		subBusinessUnitRequest.setSubBusinessUnitModel(subBusinessUnitList);
		return subBusinessUnitRequest;
	}

	@Test
	public void setSubBusinessUnitSaveSuccess() throws SQLException {
		subBusinessUnitRequest = subBusinessUnitRequest();
		when(subBusinessUnitDaoImpl.saveSubBusinessUnit(subBusinessUnitRequest)).thenReturn(true);
		ResponseEntity<Object> saveSBU = subBusinessUnitFacadeImpl.setSubBusinessUnit(subBusinessUnitRequest);
		HttpStatus statusCode = saveSBU.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void setSubBusinessUnitSaveFail() throws SQLException {
		subBusinessUnitRequest = subBusinessUnitRequest();
		when(subBusinessUnitDaoImpl.saveSubBusinessUnit(subBusinessUnitRequest)).thenReturn(false);
		ResponseEntity<Object> saveSBU = subBusinessUnitFacadeImpl.setSubBusinessUnit(subBusinessUnitRequest);
		HttpStatus statusCode = saveSBU.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void setSubBusinessUnitUpdateSuccess() throws SQLException {
		subBusinessUnitRequest = subBusinessUnitRequest();
		subBusinessUnitRequest.setTransactionType("update");
		when(subBusinessUnitDaoImpl.updateSubBusinessUnit(subBusinessUnitRequest)).thenReturn(true);
		ResponseEntity<Object> saveSBU = subBusinessUnitFacadeImpl.setSubBusinessUnit(subBusinessUnitRequest);
		HttpStatus statusCode = saveSBU.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void setSubBusinessUnitUpdateFail() throws SQLException {
		subBusinessUnitRequest = subBusinessUnitRequest();
		subBusinessUnitRequest.setTransactionType("update");
		when(subBusinessUnitDaoImpl.updateSubBusinessUnit(subBusinessUnitRequest)).thenReturn(false);
		ResponseEntity<Object> saveSBU = subBusinessUnitFacadeImpl.setSubBusinessUnit(subBusinessUnitRequest);
		HttpStatus statusCode = saveSBU.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	
	@Test
	public void setSubBusinessEmptyTransaction() throws SQLException {
		subBusinessUnitRequest = subBusinessUnitRequest();
		subBusinessUnitRequest.setTransactionType("");
		when(subBusinessUnitDaoImpl.updateSubBusinessUnit(subBusinessUnitRequest)).thenReturn(true);
		ResponseEntity<Object> saveSBU = subBusinessUnitFacadeImpl.setSubBusinessUnit(subBusinessUnitRequest);
		HttpStatus statusCode = saveSBU.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	/*
	 * @Test public void setSubBusinessUnitExceptionTest() throws SQLException {
	 * subBusinessUnitRequest = subBusinessUnitRequest();
	 * when(subBusinessUnitDaoImpl.saveSubBusinessUnit(subBusinessUnitRequest)).
	 * thenThrow(new RuntimeException()); ResponseEntity<Object> saveSBU =
	 * subBusinessUnitFacadeImpl.setSubBusinessUnit(subBusinessUnitRequest);
	 * HttpStatus statusCode = saveSBU.getStatusCode();
	 * assertEquals(HttpStatus.CONFLICT, statusCode); }
	 */
	/*
	 * @Test public void setSubBusinessUnitDupExceptionTest() throws SQLException {
	 * subBusinessUnitRequest = subBusinessUnitRequest(); Throwable cause = new
	 * Throwable();
	 * when(subBusinessUnitDaoImpl.saveSubBusinessUnit(subBusinessUnitRequest)).
	 * thenThrow(new DuplicateKeyException(null, cause)); ResponseEntity<Object>
	 * saveSBU =
	 * subBusinessUnitFacadeImpl.setSubBusinessUnit(subBusinessUnitRequest);
	 * HttpStatus statusCode = saveSBU.getStatusCode();
	 * assertEquals(HttpStatus.CONFLICT, statusCode); }
	 */

	@Test
	public void getSubBusinessUnitUpdateSuccess() throws SQLException {
		subBusinessUnitRequest = subBusinessUnitRequest();
		subBusinessUnitRequest.setTransactionType("getAll");
		when(subBusinessUnitDaoImpl.getAllSubBusinessUnitDetails()).thenReturn(subBusinessUnitList);
		ResponseEntity<Object> getSBU = subBusinessUnitFacadeImpl.getSubBusinessUnit(subBusinessUnitRequest);
		HttpStatus statusCode = getSBU.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void getByIdSubBusinessUnitUpdateSuccess() throws SQLException {
		subBusinessUnitRequest = subBusinessUnitRequest();
		subBusinessUnitRequest.setTransactionType("getById");
		Integer id = subBusinessUnitRequest.getSubBusinessUnitModel().get(0).getId();
		when(subBusinessUnitDaoImpl.getByIdSubBusinessUnitDetails(id)).thenReturn(subBusinessUnitList);
		ResponseEntity<Object> getSBU = subBusinessUnitFacadeImpl.getSubBusinessUnit(subBusinessUnitRequest);
		HttpStatus statusCode = getSBU.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	@Test
	public void getSubBusinessUnitListNullFail() throws SQLException {
		subBusinessUnitRequest = subBusinessUnitRequest();
		subBusinessUnitRequest.setTransactionType("getAll");
		when(subBusinessUnitDaoImpl.getAllSubBusinessUnitDetails()).thenReturn(null);
		ResponseEntity<Object> getSBU = subBusinessUnitFacadeImpl.getSubBusinessUnit(subBusinessUnitRequest);
		HttpStatus statusCode = getSBU.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void getSubBusinessUnitListEmptyFail() throws SQLException {
		subBusinessUnitRequest = subBusinessUnitRequest();
		subBusinessUnitRequest.setTransactionType("getAll");
		when(subBusinessUnitDaoImpl.getAllSubBusinessUnitDetails()).thenReturn(Collections.emptyList());
		ResponseEntity<Object> getSBU = subBusinessUnitFacadeImpl.getSubBusinessUnit(subBusinessUnitRequest);
		HttpStatus statusCode = getSBU.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	/*
	 * @Test public void getSubBusinessUnitExceptionTest() throws SQLException {
	 * subBusinessUnitRequest = subBusinessUnitRequest();
	 * subBusinessUnitRequest.setTransactionType("getAll");
	 * when(subBusinessUnitDaoImpl.getAllSubBusinessUnitDetails()).thenThrow(new
	 * RuntimeException()); ResponseEntity<Object> getSBU =
	 * subBusinessUnitFacadeImpl.getSubBusinessUnit(subBusinessUnitRequest);
	 * HttpStatus statusCode = getSBU.getStatusCode();
	 * assertEquals(HttpStatus.CONFLICT, statusCode); }
	 */

}
