package com.ojas.obs.facadeimplTest;

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

import com.ojas.obs.facade.EmployeeExperienceFacade;
import com.ojas.obs.facadeimpl.EmployeeExperienceFacadeImpl;
import com.ojas.obs.model.EmployeeExperience;

import com.ojas.obs.repository.EmployeeExperienceRepository;
import com.ojas.obs.request.EmployeeExperienceRequest;

import com.ojas.obs.response.EmployeeExperienceResponse;
import com.ojas.obs.response.ErrorResponse;

public class EmployeeExperienceFacadeImplTest {
	@InjectMocks
	EmployeeExperienceFacadeImpl empExpFacadeImpl;

	@Mock
	EmployeeExperienceRepository empExpRepo;

	@Mock
	EmployeeExperienceFacade empexpfacade;

	@Spy
	EmployeeExperienceRequest empexpreq;

	@Spy
	ErrorResponse errorresponse;

	@Spy
	EmployeeExperienceResponse empexpresponse;

	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorresponse, HttpStatus.UNPROCESSABLE_ENTITY);

	@Spy
	ResponseEntity<Object> conflict = new ResponseEntity<>(errorresponse, HttpStatus.CONFLICT);

	@Spy
	ResponseEntity<Object> successResponse = new ResponseEntity<>(empexpresponse, HttpStatus.OK);

	@Spy
	EmployeeExperience empexperience;

	@Before 
	public void init() throws Exception {
		empExpFacadeImpl = new EmployeeExperienceFacadeImpl();
		empExpRepo = mock(EmployeeExperienceRepository.class);
		setCollaborator(empExpFacadeImpl, "empExpRepo", empExpRepo);
	}

	private void setCollaborator(Object object, String name, Object service) throws Exception {
		Field field; 
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);
	}

	public List<EmployeeExperience> getEmployeeExperiences() {
		List<EmployeeExperience> explist = new ArrayList<EmployeeExperience>();
		EmployeeExperience expdatalist = new EmployeeExperience();
		expdatalist.setEmpExperienceId(1);
		EmployeeExperience expdatalist1 = new EmployeeExperience();
		expdatalist1.setEmpExperienceId(2);
		explist.add(expdatalist);
		explist.add(expdatalist1);
		return explist;
	}
	@Test
	public void testSaveError() throws SQLException {

		EmployeeExperienceRequest expreq = new EmployeeExperienceRequest();

		expreq.setTransactionType("save");

		expreq.setEmpExperienceList(this.getEmployeeExperiences());

		EmployeeExperience exp2 = new EmployeeExperience();

		when(empExpRepo.save(exp2)).thenReturn(exp2);

		ResponseEntity<Object> saveStatus = empExpFacadeImpl.setEmpExp(expreq);

		HttpStatus statusCode = saveStatus.getStatusCode();

		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void testSavesuccescheck() throws SQLException {

		EmployeeExperienceRequest expreq = new EmployeeExperienceRequest();
		List<EmployeeExperience> exp = new ArrayList<EmployeeExperience>();
		EmployeeExperience exp1 = new EmployeeExperience();
		exp1.setEmpExperienceId(5);
		exp1.setEmpExperience(1.2);
		exp1.setStatus(true);
		exp.add(exp1);		
		expreq.setTransactionType("save");
		expreq.setEmpExperienceList(exp);
		when(empExpRepo.saveAll(exp)).thenReturn(exp);
		ResponseEntity<Object> saveStatus = empExpFacadeImpl.setEmpExp(expreq);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);;
	}

	@Test
	public void testupdatesuccesscheck() throws SQLException {

		EmployeeExperienceRequest expreq = new EmployeeExperienceRequest();

		expreq.setTransactionType("update");

		expreq.setEmpExperienceList(this.getEmployeeExperiences());

		EmployeeExperience exp2 = new EmployeeExperience();
		exp2.setEmpExperienceId(1);
		List<EmployeeExperience> exp = new ArrayList<EmployeeExperience>();
		exp.add(exp2);
		expreq.setEmpExperienceList(exp);

		Integer id = expreq.getEmpExperienceList().get(0).getEmpExperienceId();

		when(empExpRepo.findById(id)).thenReturn(Optional.of(exp2));

		ResponseEntity<Object> saveStatus = empExpFacadeImpl.setEmpExp(expreq);

		HttpStatus statusCode = saveStatus.getStatusCode();

		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void testupdateErrorcheck() throws SQLException {

		EmployeeExperienceRequest expreq = new EmployeeExperienceRequest();

		expreq.setTransactionType("update");

		expreq.setEmpExperienceList(this.getEmployeeExperiences());

		EmployeeExperience exp2 = new EmployeeExperience();
		exp2.setEmpExperienceId(null);
		
		List<EmployeeExperience> exp = new ArrayList<EmployeeExperience>();
		exp.add(exp2);
		expreq.setEmpExperienceList(exp);

		Integer id = expreq.getEmpExperienceList().get(0).getEmpExperienceId();

		when(empExpRepo.findById(id)).thenReturn(Optional.of(exp2));

		ResponseEntity<Object> saveStatus = empExpFacadeImpl.setEmpExp(expreq);

		HttpStatus statusCode = saveStatus.getStatusCode();

		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void testdeletesuccesscheck() throws SQLException {

		EmployeeExperienceRequest expreq = new EmployeeExperienceRequest();

		expreq.setTransactionType("delete");

		expreq.setEmpExperienceList(this.getEmployeeExperiences());

		EmployeeExperience exp2 = new EmployeeExperience();
		exp2.setEmpExperienceId(1);
		
		List<EmployeeExperience> exp = new ArrayList<EmployeeExperience>();
		exp.add(exp2);
		expreq.setEmpExperienceList(exp);

		Integer id = expreq.getEmpExperienceList().get(0).getEmpExperienceId();

		when(empExpRepo.getOne(id)).thenReturn(exp2);

		exp2.setStatus(exp2.getStatus() == null);

		when(empExpRepo.save(exp2)).thenReturn(exp2);

		ResponseEntity<Object> saveStatus = empExpFacadeImpl.setEmpExp(expreq);

		HttpStatus statusCode = saveStatus.getStatusCode();

		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void testdeleteErrorcheck() throws SQLException {

		EmployeeExperienceRequest expreq = new EmployeeExperienceRequest();
		expreq.setTransactionType("delete");
		List<EmployeeExperience> exp = new ArrayList<EmployeeExperience>();
		EmployeeExperience exp2 = new EmployeeExperience();
		exp2.setEmpExperienceId(null);
		exp2.setStatus(false);
		exp.add(exp2);
		expreq.setEmpExperienceList(exp);
		Integer id = expreq.getEmpExperienceList().get(0).getEmpExperienceId();
		when(empExpRepo.getOne(id)).thenReturn(exp2);
		when(empExpRepo.save(exp2)).thenReturn(exp2);
		ResponseEntity<Object> saveStatus = empExpFacadeImpl.setEmpExp(expreq);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void TestElseError() throws SQLException {

		EmployeeExperienceRequest expreq = new EmployeeExperienceRequest();

		expreq.setTransactionType("ss");

		expreq.setEmpExperienceList(this.getEmployeeExperiences());

		EmployeeExperience exp2 = new EmployeeExperience();

		when(empExpRepo.save(exp2)).thenReturn(exp2);

		ResponseEntity<Object> saveStatus = empExpFacadeImpl.setEmpExp(expreq);

		HttpStatus statusCode = saveStatus.getStatusCode();

		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void getAllSuccess() throws SQLException {

		EmployeeExperienceRequest expreq = new EmployeeExperienceRequest();
		expreq.setTransactionType("delete");
		List<EmployeeExperience> exp = new ArrayList<EmployeeExperience>();
		EmployeeExperience exp2 = new EmployeeExperience();
		exp2.setEmpExperienceId(null);
		exp2.setStatus(false);
		exp.add(exp2);
		expreq.setEmpExperienceList(exp);
		when(empExpRepo.findAll()).thenReturn(exp);

		ResponseEntity<Object> saveStatus = empExpFacadeImpl.getEmpExp(expreq);

		HttpStatus statusCode = saveStatus.getStatusCode();

		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void getAllError() throws SQLException {
		EmployeeExperienceRequest expreq = new EmployeeExperienceRequest();
		List<EmployeeExperience> exp = new ArrayList<EmployeeExperience>();
		EmployeeExperience exp2 = new EmployeeExperience();
		exp2.setEmpExperienceId(null);
		exp2.setStatus(false);
		exp2.setEmpExperience(null);
		exp.isEmpty();
		expreq.setTransactionType("getAll");
		expreq.setEmpExperienceList(exp);
		when(empExpRepo.findAll()).thenReturn(exp);
		ResponseEntity<Object> saveStatus = empExpFacadeImpl.getEmpExp(expreq);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	@Test
	public void getAllTest() throws SQLException {
		EmployeeExperienceRequest expreq = new EmployeeExperienceRequest();
		List<EmployeeExperience> exp = new ArrayList<EmployeeExperience>();
		EmployeeExperience exp2 = new EmployeeExperience();
		exp2.setEmpExperience(null);
		exp2.setStatus(false);
		exp2.setEmpExperience(null);
		exp.add(exp2);
		expreq.setTransactionType("getAll");
		expreq.setEmpExperienceList(exp);
		when(empExpRepo.findAll()).thenReturn(exp);
		ResponseEntity<Object> saveStatus = empExpFacadeImpl.getEmpExp(expreq);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	@Test
	public void getByIdError() throws SQLException {

		EmployeeExperienceRequest expreq = new EmployeeExperienceRequest();

		expreq.setEmpExperienceList(this.getEmployeeExperiences());

		EmployeeExperience exp2 = new EmployeeExperience();
		exp2.setEmpExperienceId(1);

		List<EmployeeExperience> exp = new ArrayList<EmployeeExperience>();
		exp.add(exp2);
		expreq.setEmpExperienceList(exp);

		expreq.setTransactionType("getById");
		exp.get(0).getEmpExperienceId();

		when(empExpRepo.findAll()).thenReturn(exp);

		ResponseEntity<Object> saveStatus = empExpFacadeImpl.getEmpExp(expreq);

		HttpStatus statusCode = saveStatus.getStatusCode();

		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	@Test
	public void getByIdSuccess() throws SQLException {

		EmployeeExperienceRequest expreq = new EmployeeExperienceRequest();

		expreq.setEmpExperienceList(this.getEmployeeExperiences());

		EmployeeExperience exp2 = new EmployeeExperience();
		exp2.setEmpExperienceId(1);

		List<EmployeeExperience> exp = new ArrayList<EmployeeExperience>();
		exp.add(exp2);
		expreq.setEmpExperienceList(exp);

		expreq.setTransactionType("getById");
		Integer id = exp.get(0).getEmpExperienceId();

		when(empExpRepo.findById(id)).thenReturn(Optional.of(exp2));
		exp.add(exp2);

		ResponseEntity<Object> saveStatus = empExpFacadeImpl.getEmpExp(expreq);

		HttpStatus statusCode = saveStatus.getStatusCode();

		assertEquals(HttpStatus.OK, statusCode);

	}
}



