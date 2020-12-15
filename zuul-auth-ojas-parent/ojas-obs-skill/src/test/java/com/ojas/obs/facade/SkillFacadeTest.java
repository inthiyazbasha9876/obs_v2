
package com.ojas.obs.facade;

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
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ojas.obs.dao.SkillDao;
import com.ojas.obs.model.ErrorResponse;
import com.ojas.obs.model.Skill;
import com.ojas.obs.request.SkillRequest;

public class SkillFacadeTest {

	@Mock
	private SkillDao skillDao;

	@InjectMocks
	private SkillFacadeImpl skillFacadeImpl;

	@Spy
	SkillRequest skillRequest;

	@Spy
	ErrorResponse error = new ErrorResponse();

	@Spy
	ResponseEntity<Object> objEntity = new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);

	@Spy
	ResponseEntity<Object> sucessResponse = new ResponseEntity<Object>(error, HttpStatus.OK);

	@Spy
	List<Skill> skillList = new ArrayList<Skill>();

	@Before
	public void init() throws Exception {
		skillFacadeImpl = new SkillFacadeImpl();
		skillDao = mock(SkillDao.class);
		setCollaborator(skillFacadeImpl, "skillDao", skillDao);
	}
	private void setCollaborator(Object object, String name, Object service) throws Exception 
	{ 
		Field field;
		field = object.getClass().getDeclaredField(name); 
		field.setAccessible(true);
		field.set(object, service); 
	}


	public List<Skill> getSkill() {
		ArrayList<Skill> arrayList = new ArrayList<>();
		Skill skill = new Skill();
		skill.setId(1);
		skill.setSkill_name("java");
		Skill skill1 = new Skill();
		skill1.setId(2);
		skill1.setSkill_name("c");
		arrayList.add(skill);
		arrayList.add(skill1);
		return arrayList;
	}

	/*public SkillRequest skillRequest() {
		Skill skill = new Skill();
		List<Skill> skillList = new ArrayList<Skill>();
		skill.setId(1);
		skill.setSkill_name("Java");
		skillList.add(skill);
		skillRequest.setListOfSkill(skillList);
		// skillRequest.setTransactionType("save");
		return skillRequest;
	}

	public SkillRequest getSkillRequest() {

		List<Skill> list = new ArrayList<Skill>();
		skillRequest.setListOfSkill(list);
		skillRequest.setTransactionType("getAll");
		return skillRequest;
	}*/

	// -------set save method-------------

	@Test
	public void setSkillForSaveTest() throws SQLException {
		SkillRequest skillRequest2 = new SkillRequest();
		skillRequest2.setListOfSkill(this.getSkill());
		skillRequest2.setTransactionType("save");
		when(skillDao.saveSkillInfo(skillRequest2)).thenReturn(1);
		ResponseEntity<Object> saveResponseEntity = skillFacadeImpl.setSkillInfo(skillRequest2);
		assertEquals(HttpStatus.OK, saveResponseEntity.getStatusCode());
	}
	
	@Test
	public void setSkillDeleteTest() throws SQLException {
		SkillRequest skillRequest2 = new SkillRequest();
		skillRequest2.setListOfSkill(this.getSkill());
		skillRequest2.setTransactionType("delete");
		when(skillDao.deleteSkill(skillRequest2)).thenReturn(true);
		ResponseEntity<Object> saveResponseEntity = skillFacadeImpl.setSkillInfo(skillRequest2);
		assertEquals(HttpStatus.OK, saveResponseEntity.getStatusCode());
	}
	@Test
	public void setSkillDeleteNegativeTest() throws SQLException {
		SkillRequest skillRequest2 = new SkillRequest();
		skillRequest2.setListOfSkill(this.getSkill());
		skillRequest2.setTransactionType("delete");
		when(skillDao.deleteSkill(skillRequest2)).thenReturn(false);
		ResponseEntity<Object> saveResponseEntity = skillFacadeImpl.setSkillInfo(skillRequest2);
		assertEquals(HttpStatus.CONFLICT, saveResponseEntity.getStatusCode());
	}
	
	
	

	// ------------get method-------------------

	@Test
	public void getSkillTest() throws SQLException {

		SkillRequest skillRequest = new SkillRequest();
		List<Skill> list = new ArrayList<>();
		skillRequest.setListOfSkill(list);
		skillRequest.setTransactionType("getAll");
		when(skillDao.showSkillInfo(skillRequest)).thenReturn(skillList);
		ResponseEntity<Object> getSkill = skillFacadeImpl.getSkillInfo(skillRequest);
		HttpStatus statusCode = getSkill.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);

	}

	// ------------------getById method--------------------

	@Test
	public void testGetById() throws SQLException {
		List<Skill> list = new ArrayList<>();
		Skill skill = new Skill();
		skill.setId(1);
		skillList.add(skill);
		SkillRequest skillRequest = new SkillRequest();
		skillRequest.setTransactionType("getbyid");
		skillRequest.setListOfSkill(list);
		when(skillDao.getById(skillRequest)).thenReturn(skillList);
		ResponseEntity<Object> saveStatus = skillFacadeImpl.getSkillInfo(skillRequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	// --------------------------for update ----------------------------

	@Test
	public void setSkillForUpdateTest() throws SQLException {
		SkillRequest skillRequest2 = new SkillRequest();
		skillRequest2.setListOfSkill(this.getSkill());
		skillRequest2.setTransactionType("update");
		when(skillDao.saveSkillInfo(skillRequest2)).thenReturn(1);
		ResponseEntity<Object> saveResponseEntity = skillFacadeImpl.setSkillInfo(skillRequest2);
		assertEquals(HttpStatus.OK, saveResponseEntity.getStatusCode());
	}

}
