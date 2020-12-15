package com.ojas.obs.facadeTest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ojas.obs.facade.BudgetFacade;
import com.ojas.obs.facadeimpl.BudgetFacadeImpl;
import com.ojas.obs.model.Budget;
import com.ojas.obs.repositories.BudgetRepository;
import com.ojas.obs.request.BudgetRequest;
import com.ojas.obs.response.BudgetResponse;
import com.ojas.obs.response.ErrorResponse;

public class BudgetFacadeImplTest {
	@InjectMocks
	BudgetFacadeImpl budgetfacadeimpl;

	@Mock
	BudgetRepository budgetRepo;

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
		budgetfacadeimpl = new BudgetFacadeImpl();
		budgetRepo = mock(BudgetRepository.class);
		setCollaborator(budgetfacadeimpl, "budgetRepo", budgetRepo);
	}

	private void setCollaborator(Object object, String name, Object service) throws Exception {
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);
	}

	public List<Budget> getBudgetList() {
		List<Budget> budgetlist = new ArrayList<Budget>();
		Budget datalist = new Budget();
		datalist.setId(1);
		Budget budget1 = new Budget();
		budget1.setId(2);
		budgetlist.add(datalist);
		budgetlist.add(budget1);
		return budgetlist;
	}

	@Test
	public void testSaveError() throws SQLException {

		BudgetRequest budgetreq = new BudgetRequest();

		budgetreq.setTransactionType("save");

		budgetreq.setBudgetList(this.getBudgetList());

		Budget budget2 = new Budget();

		when(budgetRepo.save(budget2)).thenReturn(budget2);

		ResponseEntity<Object> saveStatus = budgetfacadeimpl.saveDetails(budgetreq);

		HttpStatus statusCode = saveStatus.getStatusCode();

		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void testSavesuccescheck() throws SQLException {

		BudgetRequest budgetreq = new BudgetRequest();
		List<Budget> budget = new ArrayList<Budget>();
		Budget bug = new Budget();
		bug.setId(5);
		bug.setBudget((long) 42681241);
		bug.setStatus(true);
		budget.add(bug);
		budgetreq.setTransactionType("save");
		budgetreq.setBudgetList(budget);
		when(budgetRepo.saveAll(budget)).thenReturn(budget);
		ResponseEntity<Object> saveStatus = budgetfacadeimpl.saveDetails(budgetreq);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void testupdatesuccesscheck() throws SQLException {

		BudgetRequest budgetreq = new BudgetRequest();

		budgetreq.setTransactionType("update");

		budgetreq.setBudgetList(this.getBudgetList());

		Budget budget2 = new Budget();
		budget2.setId(1);
		List<Budget> budget = new ArrayList<Budget>();
		budget.add(budget2);
		budgetreq.setBudgetList(budget);

		Integer id = budgetreq.getBudgetList().get(0).getId();

		when(budgetRepo.findById(id)).thenReturn(Optional.of(budget2));

		ResponseEntity<Object> saveStatus = budgetfacadeimpl.saveDetails(budgetreq);

		HttpStatus statusCode = saveStatus.getStatusCode();

		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void testupdateErrorcheck() throws SQLException {

		BudgetRequest budgetreq = new BudgetRequest();

		budgetreq.setTransactionType("update");

		budgetreq.setBudgetList(this.getBudgetList());

		Budget budget2 = new Budget();
		
		budget2.setId(null);
		
		List<Budget> budget = new ArrayList<Budget>();
		budget.add(budget2);
		budgetreq.setBudgetList(budget);

		Integer id = budgetreq.getBudgetList().get(0).getId();

		when(budgetRepo.findById(id)).thenReturn(Optional.of(budget2));

		ResponseEntity<Object> saveStatus = budgetfacadeimpl.saveDetails(budgetreq);

		HttpStatus statusCode = saveStatus.getStatusCode();

		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void testdeletesuccesscheck() throws SQLException {

		BudgetRequest budgetreq = new BudgetRequest();

		budgetreq.setTransactionType("delete");

		budgetreq.setBudgetList(this.getBudgetList());

		Budget budget2 = new Budget();
		
		budget2.setId(1);
		

		List<Budget> budget = new ArrayList<Budget>();
		budget.add(budget2);
		budgetreq.setBudgetList(budget);

		Integer id = budgetreq.getBudgetList().get(0).getId();

		when(budgetRepo.getOne(id)).thenReturn(budget2);

		budget2.setStatus(budget2.getStatus() == null);

		when(budgetRepo.save(budget2)).thenReturn(budget2);

		ResponseEntity<Object> saveStatus = budgetfacadeimpl.saveDetails(budgetreq);

		HttpStatus statusCode = saveStatus.getStatusCode();

		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void testdeleteErrorcheck() throws SQLException {

		BudgetRequest budgetreq = new BudgetRequest();
		budgetreq.setTransactionType("delete");
		List<Budget> budget = new ArrayList<Budget>();
		Budget budget2 = new Budget();
		budget2.setId(null);
		budget2.setStatus(false);
		budget.add(budget2);
		budgetreq.setBudgetList(budget);
		Integer id = budgetreq.getBudgetList().get(0).getId();
		when(budgetRepo.getOne(id)).thenReturn(budget2);
		when(budgetRepo.save(budget2)).thenReturn(budget2);
		ResponseEntity<Object> saveStatus = budgetfacadeimpl.saveDetails(budgetreq);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void TestElseError() throws SQLException {

		BudgetRequest budgetreq = new BudgetRequest();

		budgetreq.setTransactionType("ss");

		budgetreq.setBudgetList(this.getBudgetList());

		Budget budget2 = new Budget();

		when(budgetRepo.save(budget2)).thenReturn(budget2);

		ResponseEntity<Object> saveStatus = budgetfacadeimpl.saveDetails(budgetreq);

		HttpStatus statusCode = saveStatus.getStatusCode();

		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void getAllSuccess() throws SQLException {

		BudgetRequest budgetreq = new BudgetRequest();
		budgetreq.setTransactionType("delete");
		List<Budget> budget = new ArrayList<Budget>();
		Budget budget2 = new Budget();
		budget2.setId(null);
		budget2.setStatus(false);
		budget.add(budget2);
		budgetreq.setBudgetList(budget);
		when(budgetRepo.findAll()).thenReturn(budget);

		ResponseEntity<Object> saveStatus = budgetfacadeimpl.getDetails(budgetreq);

		HttpStatus statusCode = saveStatus.getStatusCode();

		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void getAllError() throws SQLException {
		BudgetRequest budgetreq = new BudgetRequest();
		List<Budget> budget = new ArrayList<Budget>();
		Budget budget2 = new Budget();
		budget2.setId(null);
		budget2.setStatus(false);
		budget2.setBudget(null);
		budget.isEmpty();
		budgetreq.setTransactionType("getAll");
		budgetreq.setBudgetList(budget);
		when(budgetRepo.findAll()).thenReturn(budget);
		ResponseEntity<Object> saveStatus = budgetfacadeimpl.getDetails(budgetreq);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void getAllTest() throws SQLException {
		BudgetRequest budgetreq = new BudgetRequest();
		List<Budget> budget = new ArrayList<Budget>();
		Budget budget2 = new Budget();
		budget2.setId(null);
		budget2.setStatus(false);
		budget2.setBudget(null);
		budget.add(budget2);
		budgetreq.setTransactionType("getAll");
		budgetreq.setBudgetList(budget);
		when(budgetRepo.findAll()).thenReturn(budget);
		ResponseEntity<Object> saveStatus = budgetfacadeimpl.getDetails(budgetreq);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void getByIdError() throws SQLException {

		BudgetRequest budgetreq = new BudgetRequest();

		budgetreq.setBudgetList(this.getBudgetList());

		Budget budget2 = new Budget();
		budget2.setId(1);

		List<Budget> budget = new ArrayList<Budget>();
		budget.add(budget2);
		budgetreq.setBudgetList(budget);

		budgetreq.setTransactionType("getById");
		Integer id = budget.get(0).getId();

		when(budgetRepo.findAll()).thenReturn(budget);

		ResponseEntity<Object> saveStatus = budgetfacadeimpl.getDetails(budgetreq);

		HttpStatus statusCode = saveStatus.getStatusCode();

		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void getByIdSuccess() throws SQLException {

		BudgetRequest budgetreq = new BudgetRequest();

		budgetreq.setBudgetList(this.getBudgetList());

		Budget budget2 = new Budget();
		budget2.setId(1);

		List<Budget> budget = new ArrayList<Budget>();
		budget.add(budget2);
		budgetreq.setBudgetList(budget);

		budgetreq.setTransactionType("getById");
		Integer id = budget.get(0).getId();

		when(budgetRepo.findById(id)).thenReturn(Optional.of(budget2));
		budget.add(budget2);

		ResponseEntity<Object> saveStatus = budgetfacadeimpl.getDetails(budgetreq);

		HttpStatus statusCode = saveStatus.getStatusCode();

		assertEquals(HttpStatus.OK, statusCode);

	}
}
