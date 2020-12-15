package com.ojas.obs.dao;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.activation.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;


import com.ojas.obs.model.Skill;
import com.ojas.obs.request.SkillRequest;
import static com.ojas.obs.constants.UserConstants.GETALL;
import static com.ojas.obs.constants.UserConstants.GETBYID;
import static com.ojas.obs.constants.UserConstants.GETCOUNT;
import static com.ojas.obs.constants.UserConstants.DELETESKIL;

@RunWith(MockitoJUnitRunner.class)
public class SkillDaoTest {

	@Mock
	DataSource datasource;

	@Mock
	JdbcTemplate jdbcTemplate;

	@InjectMocks
	SkillDaoImpl skillDaoImpl;

	@Mock
	SkillDao skillDao;

	@Spy
	SkillRequest skillRequest;

	int[] arr = new int[4];

	@Before
	public void setup() {
		skillDaoImpl = new SkillDaoImpl();
		jdbcTemplate = mock(JdbcTemplate.class);
		setCollabarator(skillDaoImpl, "jdbcTemplate", jdbcTemplate);
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

	/*public List<Skill> getSkill() {
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
	}*/

	public SkillRequest skillRequest() {

		Skill skill = new Skill();
		List<Skill> skillList = new ArrayList<Skill>();
		skill.setId(1);
		skill.setSkill_name("Java");
		skillList.add(skill);
		skillRequest.setListOfSkill(skillList);
		skillRequest.setTransactionType("save");
		return skillRequest;
	}

	public SkillRequest getSkillRequest() {

		List<Skill> list = new ArrayList<Skill>();
		skillRequest.setListOfSkill(list);
		skillRequest.setTransactionType("getAll");
		return skillRequest;
	}

	@Test
	public void saveEmployeeSkillInfo() throws SQLException {
		Mockito.lenient().when(jdbcTemplate.batchUpdate(Mockito.anyString(), Mockito.anyListOf(Object[].class))).thenReturn(arr);
		int saveSkill = skillDaoImpl.saveSkillInfo(skillRequest());
		assertEquals(1, saveSkill);

	}

	@Test
	public void updateEmployeeSkillInfo() throws SQLException {
		Mockito.lenient().when(jdbcTemplate.batchUpdate(Mockito.anyString(), Mockito.anyListOf(Object[].class))).thenReturn(arr);
		int saveSkill = skillDaoImpl.updateSkillInfo(skillRequest());
		assertEquals(1, saveSkill);

	}

	@Test
	public void showEmployeeSkillInfo() throws SQLException {
		List<Skill> list = new ArrayList<Skill>();
		Mockito.lenient().when(jdbcTemplate.query(GETALL, new BeanPropertyRowMapper<>(Skill.class))).thenReturn(list);
		List<Skill> skillList = skillDaoImpl.showSkillInfo(getSkillRequest());
		assertEquals(list, skillList);
	}

	@Test
	public void getAllCount() throws SQLException {
		Mockito.lenient().when(jdbcTemplate.queryForObject(GETCOUNT, Integer.class)).thenReturn(1);
		int allCount = skillDaoImpl.getAllCount();
		assertEquals(1, allCount);
	}
	
	@Test
	public void getById() throws SQLException {
		
		List<Skill> list = new ArrayList<Skill>();
		Mockito.lenient().when(jdbcTemplate.query(GETBYID,new BeanPropertyRowMapper<>(Skill.class))).thenReturn(list);
		List<Skill> skillList = skillDaoImpl.getById(getSkillRequest());
		assertEquals(list, skillList);
	}
	
	@Test
	public void deleteTest() throws SQLException {
		SkillRequest sr=new SkillRequest();
		List<Skill> list = new ArrayList<Skill>();
		Skill sk=new Skill();
		sk.setId(1);
		sk.setSkill_name("java");
		sk.setStatus(true);
		list.add(sk);
		sr.setListOfSkill(list);
		Mockito.lenient().when(jdbcTemplate.batchUpdate(Mockito.anyString(), Mockito.anyListOf(Object[].class))).thenReturn(arr);
		boolean skillList = skillDaoImpl.deleteSkill(sr);
		assertEquals(true,skillList);
	}
	
}
