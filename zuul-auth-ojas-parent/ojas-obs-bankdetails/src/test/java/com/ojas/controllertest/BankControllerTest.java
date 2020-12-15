package com.ojas.controllertest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

import com.ojas.obs.controller.BankDetailsController;
import com.ojas.obs.error.ErrorResponse;
import com.ojas.obs.facade.BankDetailsFacade;
import com.ojas.obs.model.BankDetails;
import com.ojas.obs.request.BankDetailsRequest;
import com.ojas.obs.response.BankDetailsResponse;

public class BankControllerTest {

	@InjectMocks
	BankDetailsController bankController;
	@Mock
	BankDetailsFacade bankFacade;
	@Spy
	BankDetailsRequest bankRequest;
	@Spy
	BankDetailsResponse bankResponse;
	@Spy
	ErrorResponse errorResponse;
	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
	@Spy
	ResponseEntity<Object> conflict = new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
	@Spy
	ResponseEntity<Object> successResponse = new ResponseEntity<>(bankResponse, HttpStatus.OK);
	@Spy
	BankDetails bankDetails;

	@Before
	public void init() throws Exception {
		bankController = new BankDetailsController();
		bankFacade = mock(BankDetailsFacade.class);
		setCollaborator(bankController, "bankDetailsFacade", bankFacade);
	}

	public void setCollaborator(Object object, String name, Object service) throws Exception {
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);
	}

	List<BankDetails> getBankList() {
		List<BankDetails> bankList = new ArrayList<BankDetails>();
		BankDetails detail = new BankDetails();
		detail.setBank_name("HDFC");
		detail.setBank_account_no("123456");
		detail.setBank_account_status("Active");
		detail.setBank_branch("Hyd");
		detail.setBank_city("Hyderabad");
		detail.setBank_ifsc_code("ABC123");
		detail.setEmployee_id("123");
		detail.setFlag(true);
		detail.setId(1);
		detail.setIs_active(true);
		bankList.add(detail);
		return bankList;
	}

	@Test
	public void setListNull() {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		bankRequest = new BankDetailsRequest();
		bankRequest.setBankDetails(null);
		bankRequest.setTransactionType("save");
		// bankRequest.setBankDetails(getBankList());
		ResponseEntity<Object> setBank = bankController.setBankDetails(bankRequest, request, response);
		HttpStatus statusCode = setBank.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setListEmpty() {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		bankRequest = new BankDetailsRequest();
		bankRequest.setTransactionType("save");
		// bankRequest.setBankDetails(getBankList());
		ResponseEntity<Object> setBank = bankController.setBankDetails(bankRequest, request, response);
		HttpStatus statusCode = setBank.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setBankSave() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		bankRequest = new BankDetailsRequest();
		bankRequest.setBankDetails(getBankList());
		bankRequest.setTransactionType("save");
		bankRequest.setBankDetails(getBankList());
		when(bankFacade.setBankDetails(bankRequest)).thenReturn(successResponse);
		ResponseEntity<Object> setBank = bankController.setBankDetails(bankRequest, request, response);
		HttpStatus statusCode = setBank.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void setBankSaveAccNoNull() {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		bankRequest = new BankDetailsRequest();
		List<BankDetails> detailsList = getBankList();
		detailsList.get(0).setBank_account_no(null);
		bankRequest.setBankDetails(detailsList);
		bankRequest.setTransactionType("save");
		ResponseEntity<Object> setBank = bankController.setBankDetails(bankRequest, request, response);
		HttpStatus statusCode = setBank.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setBankSaveAccNoEmpty() {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		bankRequest = new BankDetailsRequest();
		List<BankDetails> detailsList = getBankList();
		detailsList.get(0).setBank_account_no("");
		bankRequest.setBankDetails(detailsList);
		bankRequest.setTransactionType("save");
		ResponseEntity<Object> setBank = bankController.setBankDetails(bankRequest, request, response);
		HttpStatus statusCode = setBank.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setBankSaveBankNameNull() {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		bankRequest = new BankDetailsRequest();
		List<BankDetails> detailsList = getBankList();
		detailsList.get(0).setBank_name(null);
		bankRequest.setBankDetails(detailsList);
		bankRequest.setTransactionType("save");
		ResponseEntity<Object> setBank = bankController.setBankDetails(bankRequest, request, response);
		HttpStatus statusCode = setBank.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setBankSaveBankNameEmpty() {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		bankRequest = new BankDetailsRequest();
		List<BankDetails> detailsList = getBankList();
		detailsList.get(0).setBank_name("");
		bankRequest.setBankDetails(detailsList);
		bankRequest.setTransactionType("save");
		ResponseEntity<Object> setBank = bankController.setBankDetails(bankRequest, request, response);
		HttpStatus statusCode = setBank.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setBankSaveBankCityNull() {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		bankRequest = new BankDetailsRequest();
		List<BankDetails> detailsList = getBankList();
		detailsList.get(0).setBank_city(null);
		bankRequest.setBankDetails(detailsList);
		bankRequest.setTransactionType("save");
		ResponseEntity<Object> setBank = bankController.setBankDetails(bankRequest, request, response);
		HttpStatus statusCode = setBank.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setBankSaveBankCityEmpty() {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		bankRequest = new BankDetailsRequest();
		List<BankDetails> detailsList = getBankList();
		detailsList.get(0).setBank_city("");
		bankRequest.setBankDetails(detailsList);
		bankRequest.setTransactionType("save");
		ResponseEntity<Object> setBank = bankController.setBankDetails(bankRequest, request, response);
		HttpStatus statusCode = setBank.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setBankSaveBankBranchNull() {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		bankRequest = new BankDetailsRequest();
		List<BankDetails> detailsList = getBankList();
		detailsList.get(0).setBank_branch(null);
		bankRequest.setBankDetails(detailsList);
		bankRequest.setTransactionType("save");
		ResponseEntity<Object> setBank = bankController.setBankDetails(bankRequest, request, response);
		HttpStatus statusCode = setBank.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setBankSaveBankBranchEmpty() {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		bankRequest = new BankDetailsRequest();
		List<BankDetails> detailsList = getBankList();
		detailsList.get(0).setBank_branch("");
		bankRequest.setBankDetails(detailsList);
		bankRequest.setTransactionType("save");
		ResponseEntity<Object> setBank = bankController.setBankDetails(bankRequest, request, response);
		HttpStatus statusCode = setBank.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setBankSaveBankIfscNull() {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		bankRequest = new BankDetailsRequest();
		List<BankDetails> detailsList = getBankList();
		detailsList.get(0).setBank_ifsc_code(null);
		bankRequest.setBankDetails(detailsList);
		bankRequest.setTransactionType("save");
		ResponseEntity<Object> setBank = bankController.setBankDetails(bankRequest, request, response);
		HttpStatus statusCode = setBank.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setBankSaveBankIfscEmpty() {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		bankRequest = new BankDetailsRequest();
		List<BankDetails> detailsList = getBankList();
		detailsList.get(0).setBank_ifsc_code("");
		bankRequest.setBankDetails(detailsList);
		bankRequest.setTransactionType("save");
		ResponseEntity<Object> setBank = bankController.setBankDetails(bankRequest, request, response);
		HttpStatus statusCode = setBank.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setBankSaveBankStatusNull() {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		bankRequest = new BankDetailsRequest();
		List<BankDetails> detailsList = getBankList();
		detailsList.get(0).setBank_account_status(null);
		bankRequest.setBankDetails(detailsList);
		bankRequest.setTransactionType("save");
		ResponseEntity<Object> setBank = bankController.setBankDetails(bankRequest, request, response);
		HttpStatus statusCode = setBank.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setBankSaveBankStatusEmpty() {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		bankRequest = new BankDetailsRequest();
		List<BankDetails> detailsList = getBankList();
		detailsList.get(0).setBank_account_status("");
		bankRequest.setBankDetails(detailsList);
		bankRequest.setTransactionType("save");
		ResponseEntity<Object> setBank = bankController.setBankDetails(bankRequest, request, response);
		HttpStatus statusCode = setBank.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setBankSaveEmpIdNull() {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		bankRequest = new BankDetailsRequest();
		List<BankDetails> detailsList = getBankList();
		detailsList.get(0).setEmployee_id(null);
		bankRequest.setBankDetails(detailsList);
		bankRequest.setTransactionType("save");
		ResponseEntity<Object> setBank = bankController.setBankDetails(bankRequest, request, response);
		HttpStatus statusCode = setBank.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setBankUpdateIdNull() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		bankRequest = new BankDetailsRequest();
		List<BankDetails> detailsList = getBankList();
		detailsList.get(0).setEmployee_id(null);
		bankRequest.setBankDetails(detailsList);
		bankRequest.setTransactionType("update");
		// when(bankFacade.setBankDetails(bankRequest)).thenReturn(successResponse);
		ResponseEntity<Object> setBank = bankController.setBankDetails(bankRequest, request, response);
		HttpStatus statusCode = setBank.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setBankDeleteIdNull() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		bankRequest = new BankDetailsRequest();
		List<BankDetails> detailsList = getBankList();
		detailsList.get(0).setEmployee_id(null);
		bankRequest.setBankDetails(detailsList);
		bankRequest.setTransactionType("delete");
		// when(bankFacade.setBankDetails(bankRequest)).thenReturn(successResponse);
		ResponseEntity<Object> setBank = bankController.setBankDetails(bankRequest, request, response);
		HttpStatus statusCode = setBank.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setBankDeleteIdEmpty() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		bankRequest = new BankDetailsRequest();
		List<BankDetails> detailsList = getBankList();
		detailsList.get(0).setEmployee_id("");
		bankRequest.setBankDetails(detailsList);
		bankRequest.setTransactionType("delete");
		// when(bankFacade.setBankDetails(bankRequest)).thenReturn(successResponse);
		ResponseEntity<Object> setBank = bankController.setBankDetails(bankRequest, request, response);
		HttpStatus statusCode = setBank.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	@Test
	public void setBankUpdate() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		bankRequest = new BankDetailsRequest();
		bankRequest.setBankDetails(getBankList());
		bankRequest.setTransactionType("update");
		bankRequest.setBankDetails(getBankList());
		when(bankFacade.setBankDetails(bankRequest)).thenReturn(successResponse);
		ResponseEntity<Object> setBank = bankController.setBankDetails(bankRequest, request, response);
		HttpStatus statusCode = setBank.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void setBankDupException() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		bankRequest = new BankDetailsRequest();
		bankRequest.setBankDetails(getBankList());
		bankRequest.setTransactionType("update");
		Throwable cause = new Throwable();
		when(bankFacade.setBankDetails(bankRequest)).thenThrow(new DuplicateKeyException(null, cause));
		ResponseEntity<Object> setBank = bankController.setBankDetails(bankRequest, request, response);
		HttpStatus statusCode = setBank.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void setBankSQLException() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		bankRequest = new BankDetailsRequest();
		bankRequest.setBankDetails(getBankList());
		bankRequest.setTransactionType("update");
		when(bankFacade.setBankDetails(bankRequest)).thenThrow(new SQLException());
		ResponseEntity<Object> setBank = bankController.setBankDetails(bankRequest, request, response);
		HttpStatus statusCode = setBank.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void setBankException() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		bankRequest = new BankDetailsRequest();
		bankRequest.setBankDetails(getBankList());
		bankRequest.setTransactionType("update");
		when(bankFacade.setBankDetails(bankRequest)).thenThrow(new RuntimeException());
		ResponseEntity<Object> setBank = bankController.setBankDetails(bankRequest, request, response);
		HttpStatus statusCode = setBank.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void getTypeNull() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null; bankRequest = new BankDetailsRequest();
		bankRequest.setBankDetails(null); bankRequest.setTransactionType("get");
		// bankRequest.setBankDetails(getBankList());
		ResponseEntity<Object> setBank = bankController.getBankDetails(bankRequest, request, response); 
		HttpStatus statusCode = setBank.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode); 
	  }
	
	@Test
	public void getBank() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null; bankRequest = new BankDetailsRequest();
		bankRequest.setBankDetails(null); bankRequest.setTransactionType("getAll");
		// bankRequest.setBankDetails(getBankList());
		when(bankFacade.getBankDetails(bankRequest)).thenReturn(successResponse);
		ResponseEntity<Object> setBank = bankController.getBankDetails(bankRequest, request, response); 
		HttpStatus statusCode = setBank.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode); 
	  }
	
	@Test
	public void getBankSQLException() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		bankRequest = new BankDetailsRequest();
		bankRequest.setBankDetails(getBankList());
		bankRequest.setTransactionType("getall");
		when(bankFacade.getBankDetails(bankRequest)).thenThrow(new SQLException());
		ResponseEntity<Object> setBank = bankController.getBankDetails(bankRequest, request, response);
		HttpStatus statusCode = setBank.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void getBankException() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		bankRequest = new BankDetailsRequest();
		bankRequest.setBankDetails(getBankList());
		bankRequest.setTransactionType("getall");
		when(bankFacade.getBankDetails(bankRequest)).thenThrow(new RuntimeException());
		ResponseEntity<Object> setBank = bankController.getBankDetails(bankRequest, request, response);
		HttpStatus statusCode = setBank.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
}
