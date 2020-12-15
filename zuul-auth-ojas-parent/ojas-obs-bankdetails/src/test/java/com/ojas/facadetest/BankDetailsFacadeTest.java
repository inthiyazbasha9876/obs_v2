package com.ojas.facadetest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ojas.obs.dao.BankDetailsDAO;
import com.ojas.obs.daoimpl.BankDetailsDaoImpl;
import com.ojas.obs.error.ErrorResponse;
import com.ojas.obs.facade.BankDetailsFacade;
import com.ojas.obs.model.BankDetails;
import com.ojas.obs.request.BankDetailsRequest;
import com.ojas.obs.response.BankDetailsResponse;

public class BankDetailsFacadeTest {

	@InjectMocks
	BankDetailsFacade bankFacade;
	@Mock
	BankDetailsDAO bankDetailsDao;
	@Mock
	BankDetailsDaoImpl bankDetailsDaoImpl;
	@Spy
	BankDetailsRequest bankDetailsRequest;
	@Spy
	BankDetailsResponse bankDetailsResponse;
	@Spy
	ErrorResponse errorResponse;
	@Spy
	BankDetails empInfo;

	@Before
	public void init() throws Exception {
		bankFacade = new BankDetailsFacade();
		bankDetailsDaoImpl = mock(BankDetailsDaoImpl.class);
		setCollaborator(bankFacade, "bankDetailsDAO", bankDetailsDaoImpl);
	}

	public void setCollaborator(Object object, String name, Object service) throws Exception {
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);
	}
	
	@Test
	public void testSave() throws SQLException {
		bankDetailsRequest = new BankDetailsRequest();
		bankDetailsRequest.setTransactionType("save");
		when(bankDetailsDaoImpl.saveEmployeeBankDetails(bankDetailsRequest)).thenReturn(true);
		ResponseEntity<Object> saveStatus = bankFacade.setBankDetails(bankDetailsRequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	@Test
	public void testSaveNegative() throws SQLException {
		bankDetailsRequest = new BankDetailsRequest();
		bankDetailsRequest.setTransactionType("save");
		when(bankDetailsDaoImpl.saveEmployeeBankDetails(bankDetailsRequest)).thenReturn(false);
		ResponseEntity<Object> saveStatus = bankFacade.setBankDetails(bankDetailsRequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	
	@Test
	public void testUpdate() throws SQLException {
		bankDetailsRequest = new BankDetailsRequest();
		bankDetailsRequest.setTransactionType("update");
		when(bankDetailsDaoImpl.updateEmployeeBankDetails(bankDetailsRequest)).thenReturn(true);
		ResponseEntity<Object> saveStatus = bankFacade.setBankDetails(bankDetailsRequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	@Test
	public void testUpdateNegative() throws SQLException {
		bankDetailsRequest = new BankDetailsRequest();
		bankDetailsRequest.setTransactionType("update");
		when(bankDetailsDaoImpl.updateEmployeeBankDetails(bankDetailsRequest)).thenReturn(false);
		ResponseEntity<Object> saveStatus = bankFacade.setBankDetails(bankDetailsRequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	
	@Test
	public void testDelete() throws SQLException {
		bankDetailsRequest = new BankDetailsRequest();
		bankDetailsRequest.setTransactionType("delete");
		when(bankDetailsDaoImpl.deleteEmployeeBankDetails(bankDetailsRequest)).thenReturn(true);
		ResponseEntity<Object> saveStatus = bankFacade.setBankDetails(bankDetailsRequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	@Test
	public void testDeleteNegative() throws SQLException {
		bankDetailsRequest = new BankDetailsRequest();
		bankDetailsRequest.setTransactionType("delete");
		when(bankDetailsDaoImpl.deleteEmployeeBankDetails(bankDetailsRequest)).thenReturn(false);
		ResponseEntity<Object> saveStatus = bankFacade.setBankDetails(bankDetailsRequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	
	@Test
	public void testGet() throws SQLException {
		bankDetailsRequest = new BankDetailsRequest();
		List<BankDetails> list = new ArrayList<BankDetails>();
		BankDetails details = new BankDetails();
		list.add(details);
		bankDetailsRequest.setTransactionType("delete");
		when(bankDetailsDaoImpl.getAllBankDetails(bankDetailsRequest)).thenReturn(list);
		ResponseEntity<Object> saveStatus = bankFacade.getBankDetails(bankDetailsRequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	@Test
	public void testGetNull() throws SQLException {
		bankDetailsRequest = new BankDetailsRequest();
		List<BankDetails> list = null;
		bankDetailsRequest.setTransactionType("delete");
		when(bankDetailsDaoImpl.getAllBankDetails(bankDetailsRequest)).thenReturn(list);
		ResponseEntity<Object> saveStatus = bankFacade.getBankDetails(bankDetailsRequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.NOT_ACCEPTABLE, statusCode);
	}
	
	@Test
	public void testGetNegative() throws SQLException {
		bankDetailsRequest = new BankDetailsRequest();
		List<BankDetails> list = new ArrayList<BankDetails>();
		bankDetailsRequest.setTransactionType("delete");
		when(bankDetailsDaoImpl.getAllBankDetails(bankDetailsRequest)).thenReturn(list);
		ResponseEntity<Object> saveStatus = bankFacade.getBankDetails(bankDetailsRequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.NOT_ACCEPTABLE, statusCode);
	}
	
}
