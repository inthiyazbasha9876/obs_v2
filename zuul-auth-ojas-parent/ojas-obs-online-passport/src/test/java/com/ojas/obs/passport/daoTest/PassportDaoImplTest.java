
package com.ojas.obs.passport.daoTest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.ojas.obs.passport.Request.PassportRequest;
import com.ojas.obs.passport.Response.PassportResponse;
import com.ojas.obs.passport.daoImpl.PassportDaoImpl;
import com.ojas.obs.passport.model.ErrorResponse;
import com.ojas.obs.passport.model.Passport;

@RunWith(MockitoJUnitRunner.Silent.class)
public class PassportDaoImplTest {

	@InjectMocks
	PassportDaoImpl passportDaoImpl;

	@Mock
	JdbcTemplate jdbcTemplate;

	@Spy
	ErrorResponse errorResponse = new ErrorResponse();

	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);

	@Spy
	ResponseEntity<Object> conflict = new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);

	@Spy
	ResponseEntity<Object> sucessResponse = new ResponseEntity<>(errorResponse, HttpStatus.OK);

	@Spy
	PassportRequest passportRequest = new PassportRequest();

	@Spy
	PassportResponse passportResponse = new PassportResponse();
	int[] update = { 1, 2, 3 };
	boolean status;

	@Before
	public void init() {
		passportDaoImpl = new PassportDaoImpl();
		jdbcTemplate = mock(JdbcTemplate.class);
		setCollaborator(passportDaoImpl, "jdbcTemplate", jdbcTemplate);
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

	public PassportRequest passportRequest() {
		passportRequest = new PassportRequest();
		passportRequest.setPassportList(this.getPassport());
		/*
		 * passportRequest.setPageNo(1); passportRequest.setPageSize(2);
		 * passportRequest.setSessionId("1234");
		 */
		return passportRequest;
	}

	public List<Passport> getPassport() {
		List<Passport> list = new ArrayList<Passport>();
		Passport passport = new Passport();
		passport.setId(1);
		passport.setCenterName("centername1"); // passport.setCreatedBy(1234); // //
		//passport.setUpdatedBy(1234); // passport.setFlag(true); 
		list.add(passport);
		return list;
	}

	@Test
	public void saveTest() throws SQLException {
		int[] count= {1,2};
		passportRequest = new PassportRequest();
		passportRequest.setPassportList(this.getPassport());
		passportRequest.setTransaactionType("save");
		//passportRequest.setSessionId("1214");
		//when(jdbcTemplate.batchUpdate(Mockito.anyString(), Mockito.anyList())).thenReturn(update);
		Mockito.lenient().when(jdbcTemplate.batchUpdate(Mockito.anyString(), Mockito.anyList())).thenReturn(count);
		status = passportDaoImpl.savePassport(passportRequest);
		assertEquals(true, status);

	}

	/*
	 * @Test public void saveNegativeTest() throws SQLException { passportRequest =
	 * new PassportRequest(); passportRequest.setPassportList(getPassport());
	 * passportRequest.setTransaactionType("save");
	 * //passportRequest.setSessionId("1214"); int[] update = { 0, 0, 0 };
	 * when(jdbcTemplate.batchUpdate(Mockito.anyString(),
	 * Mockito.anyList())).thenReturn(update); status =
	 * passportDaoImpl.savePassport(passportRequest); assertEquals(false, status); }
	 */

	@Test
	public void updateTest() throws SQLException {
		int[] count= {1,2};
		passportRequest = new PassportRequest();
		passportRequest.setPassportList(this.getPassport());
		passportRequest.setTransaactionType("update");
		//passportRequest.setSessionId("1214");
		Mockito.lenient().when(jdbcTemplate.batchUpdate(Mockito.anyString(), Mockito.anyList())).thenReturn(count);
		status = passportDaoImpl.updatePassport(passportRequest);
		assertEquals(true, status);
	}

	/*
	 * @Test public void updateNegativeTest() throws SQLException { passportRequest
	 * = new PassportRequest(); passportRequest.setPassportList(getPassport());
	 * passportRequest.setTransaactionType("update");
	 * //passportRequest.setSessionId("1214"); int[] count = { 0, 0, 0 };
	 * when(jdbcTemplate.batchUpdate(Mockito.anyString(),
	 * Mockito.anyList())).thenReturn(count); status =
	 * passportDaoImpl.updatePassport(passportRequest); assertEquals(false, status);
	 * }
	 */

	/*
	 * @Test public void deleteTest() throws SQLException { passportRequest = new
	 * PassportRequest(); passportRequest.setPassportList(getPassport());
	 * passportRequest.setTransaactionType("delete");
	 * //passportRequest.setSessionId("1214");
	 * when(jdbcTemplate.batchUpdate(Mockito.anyString(),
	 * Mockito.anyList())).thenReturn(update); status =
	 * passportDaoImpl.deletePassport(passportRequest); assertEquals(true, status);
	 * }
	 */

	/*
	 * @Test public void deleteNegativeTest() throws SQLException { passportRequest
	 * = new PassportRequest(); passportRequest.setPassportList(getPassport());
	 * passportRequest.setTransaactionType("delete");
	 * //passportRequest.setSessionId("1214"); int[] count = { 0, 0, 0 };
	 * when(jdbcTemplate.batchUpdate(Mockito.anyString(),
	 * Mockito.anyList())).thenReturn(count); status =
	 * passportDaoImpl.deletePassport(passportRequest); assertEquals(false, status);
	 * }
	 */

	@Test public void getAllTest() throws SQLException { passportRequest=new
  PassportRequest(); passportRequest.setPassportList(getPassport());
  when(jdbcTemplate.query("fghfgh",new
  BeanPropertyRowMapper<Passport>(Passport.class))).thenReturn(getPassport());
  List<Passport> num=passportDaoImpl.getAll(passportRequest); boolean
  status=num.isEmpty(); assertEquals(true,status );
  
  }

	/*
	 * @Test public void getCountTest() throws SQLException { passportRequest = new
	 * PassportRequest();passportRequest.setPassportList(getPassport()); int count =
	 * 0 ; when(jdbcTemplate.
	 * queryForObject("select count(*) from ojas_obs.obs_passport where flag=true",
	 * Integer.class)) .thenReturn(count); int num =
	 * passportDaoImpl.getcountPassport(passportRequest); assertEquals(4, num);
	 * 
	 * }
	 */

	/*
	 * @Test public void getCountPerPageTest() throws SQLException { passportRequest
	 * = new PassportRequest(); passportRequest.setPassportList(this.getPassport());
	 * int count = 4; when(jdbcTemplate.
	 * queryForObject("select count(*) from obs_CertificationDetails where flag= true"
	 * , Integer.class)).thenReturn(count); List<Passport> num =
	 * passportDaoImpl.getCountPerPage(getPassport(), 2, 3); boolean status =
	 * num.isEmpty(); assertEquals(false, status);
	 * 
	 * }
	 */

	@Test
	public void getByIdTest() throws SQLException {
		passportRequest = new PassportRequest();
		passportRequest.setPassportList(this.getPassport());
		passportRequest.getPassportList().get(0).setId(1);

		Passport count = null;
		when(jdbcTemplate.queryForObject("select * from ojas_obs.obs_passport where id = ?", Passport.class))
				.thenReturn(count);
		List<Passport> num = passportDaoImpl.getById(passportRequest);
		boolean status = num.isEmpty();
		assertEquals(true, status);

	}
}
