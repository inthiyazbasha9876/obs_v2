package com.ojas.obs.controller;

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
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ojas.obs.facade.SkillFacade;
import com.ojas.obs.facade.SkillFacadeImpl;
import com.ojas.obs.model.ErrorResponse;
import com.ojas.obs.model.Skill;
import com.ojas.obs.request.SkillRequest;
import com.ojas.obs.response.SkillResponse;


public class SkillControllerTest {

	@InjectMocks
	private SkillController skillController;

	@Mock
	SkillFacade skillFacade;

	@Spy
	ErrorResponse errorResponse = new ErrorResponse();

	@Spy
	ResponseEntity<Object> responseEntity = new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);

	@Spy
	ResponseEntity<Object> successEntity = new ResponseEntity<>(HttpStatus.OK);

	@Spy
	SkillRequest skillRequest = new SkillRequest();

	@Spy
	SkillResponse skillResponse = new SkillResponse();

	@Before
	public void beforeTest() throws Exception {
		skillController = new SkillController();
		skillFacade = mock(SkillFacadeImpl.class);
		setCollaborator(skillController, "skillFacade", skillFacade);
	}

	private void setCollaborator(Object object, String name, Object service) throws Exception 
	{ 
		Field field;
		field = object.getClass().getDeclaredField(name); 
		field.setAccessible(true);
		field.set(object, service); 
	}

	public List<Skill> getSkill() {
		ArrayList<Skill> arrayList = new ArrayList<Skill>();
		Skill skill = new Skill();
		skill.setId(1);
		// skill.setSkill_id(123);
		skill.setSkill_name("java");
		Skill skill1 = new Skill();
		skill1.setId(2);
		// skill1.setSkill_id(124);
		skill1.setSkill_name("c");

		arrayList.add(skill);
		arrayList.add(skill1);
		return arrayList;
	}

	// ----------nullTransaction---------
	@Test
	public void nullTransaction() throws SQLException {
		skillRequest.setListOfSkill(this.getSkill());
		String s = null;
		skillRequest.setTransactionType(s);
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		Mockito.lenient().when(skillFacade.setSkillInfo(skillRequest)).thenReturn(successEntity);

		ResponseEntity<Object> setSkill = skillController.setSkill(skillRequest, request, response);
		HttpStatus statusCode = setSkill.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	// ---------nullRequestTest------------
	@Test
	public void nullRequest() throws SQLException {
		skillRequest = null;
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		Mockito.lenient().when(skillFacade.setSkillInfo(skillRequest)).thenReturn(successEntity);

		ResponseEntity<Object> setSkill = skillController.setSkill(skillRequest, request, response);
		HttpStatus statusCode = setSkill.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	// -------set method-------------

	@Test
	public void setSkillTest() throws Exception {
		skillRequest.setListOfSkill(this.getSkill());
		skillRequest.setTransactionType("save");
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		Mockito.lenient().when(skillFacade.setSkillInfo(skillRequest)).thenReturn(successEntity);

		ResponseEntity<Object> setSkill = skillController.setSkill(skillRequest, request, response);
		HttpStatus statusCode = setSkill.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	// ---------Duplicate Exception case------------

	@Test
	public void setDuplicateExceptionTest() throws Exception {

		skillRequest.setListOfSkill(this.getSkill());
		skillRequest.setTransactionType("save");
		HttpServletRequest request = null;
		HttpServletResponse response = null;

		Mockito.lenient().when(skillFacade.setSkillInfo(skillRequest)).thenThrow(DuplicateKeyException.class);

		ResponseEntity<Object> setSkill = skillController.setSkill(skillRequest, request, response);
		HttpStatus statusCode = setSkill.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	@Test
	public void setExceptionTest() throws Exception {

		skillRequest.setListOfSkill(this.getSkill());
		skillRequest.setTransactionType("save");
		HttpServletRequest request = null;
		HttpServletResponse response = null;

		Mockito.lenient().when(skillFacade.setSkillInfo(skillRequest)).thenThrow(new RuntimeException());

		ResponseEntity<Object> setSkill = skillController.setSkill(skillRequest, request, response);
		HttpStatus statusCode = setSkill.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	// ---------SQL Exception case for set method------------

	@Test
	public void setSQLExceptionTest() throws Exception {

		skillRequest.setListOfSkill(this.getSkill());
		skillRequest.setTransactionType("save");
		HttpServletRequest request = null;
		HttpServletResponse response = null;

		Mockito.lenient().when(skillFacade.setSkillInfo(skillRequest)).thenThrow(SQLException.class);

		ResponseEntity<Object> setSkill = skillController.setSkill(skillRequest, request, response);
		HttpStatus statusCode = setSkill.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	// -------get method-------------

	@Test
	public void getSkillTest() throws Exception {
		// skillRequest.setListOfSkill(this.getSkillEmpty());
		skillRequest.setTransactionType("getAll");
		HttpServletRequest request = null;
		HttpServletResponse response = null;
		Mockito.lenient().when(skillFacade.getSkillInfo(skillRequest)).thenReturn(successEntity);

		ResponseEntity<Object> setSkill = skillController.getSkillInfo(skillRequest, request, response);
		HttpStatus statusCode = setSkill.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	// ---------SQL Exception case for get method------------

	@Test
	public void getSQLExceptionTest() throws Exception {

		skillRequest.setListOfSkill(this.getSkill());
		skillRequest.setTransactionType("save");
		HttpServletRequest request = null;
		HttpServletResponse response = null;

		Mockito.lenient().when(skillFacade.getSkillInfo(skillRequest)).thenThrow(SQLException.class);

		ResponseEntity<Object> getSkill = skillController.getSkillInfo(skillRequest, request, response);
		HttpStatus statusCode = getSkill.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	// ---------nullRequest-------------

	@Test
	public void nullRequestGet() throws SQLException {
		skillRequest = null;
		HttpServletRequest request = null;
		HttpServletResponse response = null;

		Mockito.lenient().when(skillFacade.getSkillInfo(skillRequest)).thenReturn(successEntity);

		ResponseEntity<Object> setSkill = skillController.getSkillInfo(skillRequest, request, response);
		HttpStatus statusCode = setSkill.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}

	// ------------------- nullTransaction------------------------

	@Test
	public void nullTransactionGet() throws SQLException {
		String s = null;
		skillRequest.setTransactionType(s);
		HttpServletRequest request = null;
		HttpServletResponse response = null;

		Mockito.lenient().when(skillFacade.getSkillInfo(skillRequest)).thenReturn(successEntity);

		ResponseEntity<Object> setSkill = skillController.getSkillInfo(skillRequest, request, response);
		HttpStatus statusCode = setSkill.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	
	@Test
	public void getExceptionTest() throws Exception {

		skillRequest.setListOfSkill(this.getSkill());
		skillRequest.setTransactionType("save");
		HttpServletRequest request = null;
		HttpServletResponse response = null;

		Mockito.lenient().when(skillFacade.getSkillInfo(skillRequest)).thenThrow(new RuntimeException());

		ResponseEntity<Object> setSkill = skillController.getSkillInfo(skillRequest, request, response);
		HttpStatus statusCode = setSkill.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
}
