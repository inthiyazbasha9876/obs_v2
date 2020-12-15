package com.ojas.obs.controllerTest;

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

import com.ojas.obs.controller.BudgetController;
import com.ojas.obs.facade.BudgetFacade;
import com.ojas.obs.facadeimpl.BudgetFacadeImpl;
import com.ojas.obs.model.Budget;
import com.ojas.obs.request.BudgetRequest;
import com.ojas.obs.response.BudgetResponse;
import com.ojas.obs.response.ErrorResponse;

public class BudgetControllerTest {
	@InjectMocks
	BudgetController budgetcontroller;

	@Mock
	BudgetFacade budgetfacadeImpl;

	@Spy
	BudgetRequest budgetreq;

	@Spy
	ErrorResponse errorresponse;

	@Spy
	BudgetResponse budgetresponse;

	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorresponse, HttpStatus.UNPROCESSABLE_ENTITY);

	@Spy
	ResponseEntity<Object> conflict = new ResponseEntity<>(errorresponse, HttpStatus.CONFLICT);

	@Spy
	ResponseEntity<Object> successResponse = new ResponseEntity<>(budgetresponse, HttpStatus.OK);

	@Spy
	Budget budget;

	@Before
	public void init() throws Exception {
		budgetcontroller = new BudgetController();
		budgetfacadeImpl = mock(BudgetFacadeImpl.class);
		setCollaborator(budgetcontroller, "budgetfacadeImpl", budgetfacadeImpl);
	}

	private void setCollaborator(Object object, String name, Object service) throws Exception {
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);
	}

	public List<Budget> getBudgets() {
		List<Budget> budgetlist = new ArrayList<Budget>();
		Budget budgetdatalist = new Budget();
		budgetdatalist.setId(1);
		Budget budgetdatalist1 = new Budget();
		budgetdatalist1.setId(2);
		budgetlist.add(budgetdatalist1);
		budgetlist.add(budgetdatalist1);
		return budgetlist;
	}

	@Test
	public void servicetypeRequestNullTest() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		BudgetRequest budgetrequest = new BudgetRequest();
		List<Budget> budgetlist = new ArrayList<Budget>();
		Budget budgetdatalist1 = new Budget();
		budgetdatalist1.setId(null);
		budgetdatalist1.setBudget(null);
		budgetdatalist1.setStatus(null);
		budgetlist.add(budgetdatalist1);
		budgetrequest.setBudgetList(null);
		when(budgetfacadeImpl.saveDetails(budgetrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = budgetcontroller.saveDetails(budgetrequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}

	@Test
	public void servicecategoryRequestsaveTest() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		BudgetRequest budgetrequest = new BudgetRequest();
		budgetrequest.setBudgetList(this.getBudgets());
		budgetrequest.setTransactionType("save");
		when(budgetfacadeImpl.saveDetails(budgetrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = budgetcontroller.saveDetails(budgetrequest, request, response);
		HttpStatus status = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, status);
	}

	@Test
	public void servicecategoryRequestupdateTest() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		BudgetRequest budgetrequest = new BudgetRequest();
		budgetrequest.setBudgetList(this.getBudgets());
		budgetrequest.setTransactionType("update");
		when(budgetfacadeImpl.saveDetails(budgetrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setBus = budgetcontroller.saveDetails(budgetrequest, request, response);
		HttpStatus statusCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void servicecategoryRequestdeleteTest() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		BudgetRequest budgetrequest = new BudgetRequest();
		budgetrequest.setBudgetList(this.getBudgets());
		budgetrequest.setTransactionType("delete");
		when(budgetfacadeImpl.saveDetails(budgetrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setBus = budgetcontroller.saveDetails(budgetrequest, request, response);
		HttpStatus statusCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void setDuplicateKeyExceptionTest() throws Exception {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		BudgetRequest budgetrequest = new BudgetRequest();
		Budget budget2 = new Budget();
		budget2.setStatus(true);
		List<Budget> budget = new ArrayList<Budget>();
		budget.add(budget2);
		budgetrequest.setBudgetList(budget);
		budgetrequest.setTransactionType("save");

		when(budgetfacadeImpl.saveDetails(budgetrequest)).thenThrow(new DuplicateKeyException(null, new Throwable()));
		ResponseEntity<Object> setBus = budgetcontroller.saveDetails(budgetrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}

	@Test
	public void setExceptionTest() throws Exception {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		BudgetRequest budgetrequest = new BudgetRequest();
		Budget budget2 = new Budget();
		budget2.setStatus(false);
		List<Budget> budget = new ArrayList<Budget>();
		budget.add(budget2);
		budgetrequest.setBudgetList(budget);
		budgetrequest.setTransactionType("save");
		when(budgetfacadeImpl.saveDetails(budgetrequest)).thenThrow(RuntimeException.class);

		ResponseEntity<Object> setBus = budgetcontroller.saveDetails(budgetrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode); 
	}

	@Test
	public void setsavesucces() throws Exception {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		BudgetRequest budgetrequest = new BudgetRequest();
		Budget budget = new Budget();
		budget.setStatus(false);
		List<Budget> budget1 = new ArrayList<Budget>();
		budget1.add(budget);
		budgetrequest.setBudgetList(budget1);
		budgetrequest.setTransactionType("save");
		when(budgetfacadeImpl.saveDetails(budgetrequest)).thenReturn(successResponse);

		ResponseEntity<Object> setBus = budgetcontroller.saveDetails(budgetrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}

	@Test
	public void setupdatesucces() throws Exception {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		BudgetRequest budgetrequest = new BudgetRequest();
		Budget budget2 = new Budget();
		budget2.setStatus(false);
		List<Budget> budget = new ArrayList<Budget>();
		budget.add(budget2);
		budgetrequest.setBudgetList(budget);
		budgetrequest.setTransactionType("update");
		when(budgetfacadeImpl.saveDetails(budgetrequest)).thenReturn(successResponse);

		ResponseEntity<Object> setBus = budgetcontroller.saveDetails(budgetrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}

	@Test
	public void setdeletesucces() throws Exception {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		BudgetRequest budgetrequest = new BudgetRequest();
		Budget budget2 = new Budget();
		budget2.setStatus(false);
		List<Budget> budget = new ArrayList<Budget>();
		budget.add(budget2);
		budgetrequest.setBudgetList(budget);
		budgetrequest.setTransactionType("delete");
		when(budgetfacadeImpl.saveDetails(budgetrequest)).thenReturn(successResponse);

		ResponseEntity<Object> setBus = budgetcontroller.saveDetails(budgetrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}

	// getTestcases

	@Test
	public void getTransactionEmpty() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		BudgetRequest budgetrequest = new BudgetRequest();

		budgetrequest.setBudgetList(this.getBudgets());
		budgetrequest.setTransactionType(null);
		when(budgetfacadeImpl.saveDetails(budgetrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = budgetcontroller.getDetails(budgetrequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}

	@Test
	public void getByIdsuccesscheck() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		BudgetRequest budgetrequest = new BudgetRequest();

		budgetrequest.setBudgetList(this.getBudgets());
		budgetrequest.setTransactionType("getById");

		budgetrequest.getBudgetList().get(0).getId();

		when(budgetfacadeImpl.getDetails(budgetrequest)).thenReturn(successResponse);
		ResponseEntity<Object> setservice = budgetcontroller.getDetails(budgetrequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.OK, unitCode);
	}

	@Test
	public void getByIdcheck() throws SQLException {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		BudgetRequest budgetrequest = new BudgetRequest();

		budgetrequest.setBudgetList(this.getBudgets());
		budgetrequest.setTransactionType("getById");

		budgetrequest.getBudgetList().get(0).setId(null);

		when(budgetfacadeImpl.getDetails(budgetrequest)).thenReturn(failureResponse);
		ResponseEntity<Object> setservice = budgetcontroller.getDetails(budgetrequest, request, response);
		HttpStatus unitCode = setservice.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, unitCode);
	}

	@Test
	public void getExceptionTest() throws Exception {
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		BudgetRequest budgetrequest = new BudgetRequest();

		budgetrequest.setBudgetList(this.getBudgets());
		budgetrequest.setTransactionType("getAll");
		when(budgetfacadeImpl.getDetails(budgetrequest)).thenThrow(RuntimeException.class);

		ResponseEntity<Object> setBus = budgetcontroller.getDetails(budgetrequest, request, response);
		HttpStatus unitCode = setBus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, unitCode);
	}

}
