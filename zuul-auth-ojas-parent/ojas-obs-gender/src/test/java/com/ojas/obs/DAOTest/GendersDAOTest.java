package com.ojas.obs.DAOTest;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
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
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.ojas.obs.dao.GenderDAOImpl;
import com.ojas.obs.model.ErrorResponse;
import com.ojas.obs.model.Genders;
import com.ojas.obs.request.GenderRequest;
import com.ojas.obs.response.GenderResponse;

public class GendersDAOTest {
	@InjectMocks
	GenderDAOImpl genderDAOImpl;
	@Mock
	JdbcTemplate jdbcTemplate;
	@Spy
	ErrorResponse errorResponse = new ErrorResponse();
    @Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
    @Spy
	ResponseEntity<Object> 	conflict = new ResponseEntity<>(errorResponse,HttpStatus.CONFLICT);
	@Spy
	ResponseEntity<Object> sucessResponse = new ResponseEntity<>(errorResponse, HttpStatus.OK);
	@Spy
	GenderRequest genderRequest = new GenderRequest();
	@Spy
	GenderResponse genderResponse = new GenderResponse();
	
	@Before
	public void init() {
		genderDAOImpl= new GenderDAOImpl();
		jdbcTemplate= mock(JdbcTemplate.class);
		setCollaborator(genderDAOImpl,"jdbcTemplate",jdbcTemplate);
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
	public List<Genders> getModel() {
		List<Genders> list = new ArrayList<Genders>();
		Genders model= new Genders();
		list.add(model);
		return list;
	}

	@Test
	public void saveTest() throws SQLException {
		genderRequest = new GenderRequest();
		genderRequest.setGender(getModel());;
		genderRequest.setTransactionType("save");
		int[] count=  {1,2};
		when(jdbcTemplate.batchUpdate(anyString(),anyList())).thenReturn(count);
	    boolean status=genderDAOImpl.saveGender(genderRequest);
		assertEquals(true,status );

	}
	@Test
	public void saveNagativeTest() throws SQLException {
		genderRequest = new GenderRequest();
		genderRequest.setGender(getModel());;
		genderRequest.setTransactionType("save");
		int[] count=  {0,0};
		when(jdbcTemplate.batchUpdate(anyString(),anyList())).thenReturn(count);
	
		boolean status=genderDAOImpl.saveGender(genderRequest);
		assertEquals(false,status );

	}
	
	@Test
	public void updateTest() throws SQLException {
		genderRequest = new GenderRequest();
		genderRequest.setGender(getModel());;
		int[] count=  {1,2};
		when(jdbcTemplate.batchUpdate(anyString(),anyList())).thenReturn(count);
		boolean status=genderDAOImpl.updateGender(genderRequest);
		assertEquals(true,status );

	}
	@Test
	public void updateNegativeTest() throws SQLException {
		genderRequest = new GenderRequest();
		genderRequest.setGender(getModel());;
		int[] count=  {0,0};
		when(jdbcTemplate.batchUpdate(anyString(),anyList())).thenReturn(count);
		boolean status=genderDAOImpl.updateGender(genderRequest);
		assertEquals(false,status );

	}
	
	
	@Test
	public void getAllTest() throws SQLException {
		genderRequest = new GenderRequest();
		genderRequest.setGender(getModel());
		when(jdbcTemplate.query("fghfgh",new BeanPropertyRowMapper<Genders>(Genders.class))).thenReturn(getModel());
		List<Genders>num=genderDAOImpl.getAll(genderRequest);
		boolean status=num.isEmpty();
		assertEquals(true,status );

	}
	@Test
	public void getByIdTest() throws SQLException {
		genderRequest = new GenderRequest();
		genderRequest.setGender(getModel());
		when(jdbcTemplate.query("fghfgh",new BeanPropertyRowMapper<Genders>(Genders.class))).thenReturn(getModel());
		List<Genders>num=genderDAOImpl.getGenderById(genderRequest);
		boolean status=num.isEmpty();
		assertEquals(true,status );

	}
	@Test
	public void getAllCountTest() throws SQLException {
		genderRequest = new GenderRequest();
		genderRequest.setGender(getModel());
		int num=4;
		when(jdbcTemplate.queryForObject("select count(*) from obs_genders",Integer.class)).thenReturn(num);
		int count=genderDAOImpl.getAllCount(genderRequest);
		assertEquals(4,count );

	}
	
	
	/*
	 * @Test public void getAllCountPerPageTest() throws SQLException {
	 * genderRequest = new GenderRequest(); genderRequest.setGender(getModel()); int
	 * count=4; when(jdbcTemplate.
	 * queryForObject("select count(*) from obs_CertificationDetails where flag= true"
	 * ,Integer.class)).thenReturn(count);
	 * List<Genders>num=genderDAOImpl.getCountPerPage(getModel(),2,3); boolean
	 * status=num.isEmpty(); assertEquals(false,status );
	 * 
	 * }
	 */
}
