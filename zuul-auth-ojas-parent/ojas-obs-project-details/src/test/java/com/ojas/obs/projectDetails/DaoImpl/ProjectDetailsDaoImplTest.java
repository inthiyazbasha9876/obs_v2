package com.ojas.obs.projectDetails.DaoImpl;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import com.ojas.obs.projectDetails.Dao.ProjectDetailsDao;
import com.ojas.obs.projectDetails.constants.PropsReaderUtil;
import com.ojas.obs.projectDetails.model.ProjectDetails;
import com.ojas.obs.projectDetails.request.ProjectDetailsRequest;
import com.ojas.obs.projectDetails.rowmapper.ProjectDetailsRowMappers;


@RunWith(MockitoJUnitRunner.Silent.class)
public class ProjectDetailsDaoImplTest {
	@Mock
	ProjectDetailsDao projectDetailsDao;
	@Mock
	ProjectDetails projectDetails2;
	@InjectMocks
	private ProjectDetailsDaoImpl projectDetailsDaoImpl;

	@Mock
	DataSource dataSource;

	@Mock
	JdbcTemplate jdbcTemplate;

	@Mock
	private Environment environment;

	@Mock
	PropsReaderUtil propsReaderUtil;

	@Spy
	Error err = new Error();
	@Spy
	ResponseEntity<Object> responseEntity = new ResponseEntity<Object>(err, HttpStatus.UNPROCESSABLE_ENTITY);
	@Spy
	ResponseEntity<Object> successEntity = new ResponseEntity<Object>(err, HttpStatus.OK);
	@Spy
	List<ProjectDetails> employeeExperienceDetailsList = new ArrayList<ProjectDetails>();
	@Spy
	ProjectDetailsRequest projectDetailsRequest;
	@Spy
	ProjectDetails projectDetails;
	int[] count = { 1 };
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	@Mock
	BeanPropertyRowMapper<ProjectDetails> beanPropertyRowMapper;
	@Mock
	Connection con;

	@Before
	public void init() throws SQLException {
		projectDetails2 = mock(ProjectDetails.class);
		projectDetailsDaoImpl = new ProjectDetailsDaoImpl();
		jdbcTemplate = mock(JdbcTemplate.class);
		setCollaborator(projectDetailsDaoImpl, "jdbcTemplate", jdbcTemplate);
		setCollaborator(projectDetailsDaoImpl, "propsReaderUtil", propsReaderUtil);
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

	public List<ProjectDetails> projectDetailsList() throws ParseException {

		List<ProjectDetails> list = new ArrayList<ProjectDetails>();

		projectDetails.setContractId(2);
		projectDetails.setRateId(3);
		//projectDetails.setStartDate(new Date(dateFormat.parse("2019-02-28").getTime()));
		projectDetails.setStartDate(new String("2019-02-28"));
		//projectDetails.setEndDate(new Date(dateFormat.parse("2019-03-28").getTime()));
		projectDetails.setEndDate(new String("2019-03-28"));
		projectDetails.setBillingId(987);
		projectDetails.setEmployeeId("877");
		projectDetails.setProjectTechStack("project");
		projectDetails.setCustomer("ojas");
		projectDetails.setLocation("hyd");
		projectDetails.setGstApplicable(true);
		projectDetails.setProjectType("running");
		projectDetails.setProjectStatus("internal");
		projectDetails.setBdmContact("emp2");
		projectDetails.setInternal(true);
		projectDetails.setFlag(true);
		projectDetails.setCreatedBy("877");

		list.add(projectDetails);

		return list;

	}

	public List<ProjectDetails> projectDetailsUpdatedList() throws ParseException {

		List<ProjectDetails> list = new ArrayList<ProjectDetails>();

		projectDetails.setContractId(2);
		projectDetails.setRateId(3);
		//projectDetails.setStartDate(new Date(dateFormat.parse("2019-02-28").getTime()));
		projectDetails.setStartDate(new String("2019-02-28"));
		//projectDetails.setEndDate(new Date(dateFormat.parse("2019-03-28").getTime()));
		projectDetails.setEndDate(new String("2019-03-28"));
		projectDetails.setBillingId(987);
		projectDetails.setEmployeeId("877");
		projectDetails.setProjectTechStack("project");
		projectDetails.setCustomer("ojas");
		projectDetails.setLocation("hyd");
		projectDetails.setGstApplicable(true);
		projectDetails.setProjectType("running");
		projectDetails.setProjectStatus("internal");
		projectDetails.setBdmContact("emp2");
		projectDetails.setInternal(true);
		projectDetails.setFlag(true);
		projectDetails.setUpdatedBy("877");
		projectDetails.setCreatedBy("877");
		projectDetails.setId(1);

		list.add(projectDetails);

		return list;
	}

	public List<ProjectDetails> projectDetailsDeleteData() {
		List<ProjectDetails> al = new ArrayList<ProjectDetails>();

		projectDetails.setUpdatedBy("67");
		;
		projectDetails.setId(2);
		al.add(projectDetails);
		return al;
	}

	public ProjectDetailsRequest projectDetailsSaveReq() throws ParseException {

		projectDetailsRequest.setProjectDetailsList(projectDetailsList());

		projectDetailsRequest.setTransactionType("save");
		return projectDetailsRequest;

	}

	public ProjectDetailsRequest projectDetailsUpdateReq() throws ParseException {

		projectDetailsRequest.setProjectDetailsList(projectDetailsUpdatedList());
		projectDetailsRequest.setTransactionType("update");
		return projectDetailsRequest;

	}

	public ProjectDetailsRequest projectDetailsDeleteReq() {

		projectDetailsRequest.setProjectDetailsList(projectDetailsDeleteData());
		projectDetailsRequest.setTransactionType("delete");
		return projectDetailsRequest;

	}

	@Transactional(rollbackFor = { Exception.class })
	@Test
	public void testSaveProjectDetails() throws SQLException, ParseException, FileNotFoundException, IOException {

		int[] count = { 1 };
		ProjectDetailsRequest req = projectDetailsSaveReq();
		when(propsReaderUtil.getValue(anyString())).thenReturn("insert_ProjectDetails");
		when(jdbcTemplate.batchUpdate(anyString(), anyList())).thenReturn(count);

		int response = projectDetailsDaoImpl.saveProjectDetails(req);
		assertEquals(1, response);
	}

	@Transactional(rollbackFor = { Exception.class })
	@Test
	public void updateProjectDetails() throws SQLException, ParseException, FileNotFoundException, IOException {

		int[] count = { 1 };
		ProjectDetailsRequest req = projectDetailsUpdateReq();
		when(propsReaderUtil.getValue(anyString())).thenReturn("update_ProjectDetails");
		when(jdbcTemplate.batchUpdate(anyString(), anyList())).thenReturn(count);
		int response = projectDetailsDaoImpl.updateProjectDetails(req);
		assertEquals(1, response);
	}

	@Transactional(rollbackFor = { Exception.class })
	@Test
	public void deleteProjectDetails() throws SQLException, ParseException, FileNotFoundException, IOException {

		ProjectDetailsRequest req = projectDetailsDeleteReq();
		when(propsReaderUtil.getValue(anyString())).thenReturn("delete_ProjectDetails");
		when(jdbcTemplate.batchUpdate(anyString(), anyList())).thenReturn(count);
		int response = projectDetailsDaoImpl.deleteProjectDetails(req);
		assertEquals(1, response);
	}

	@Test
	public void testGetAll() throws SQLException {
		List<ProjectDetails> list = new ArrayList<ProjectDetails>();

		when(jdbcTemplate.query("select * from obs_projectdetails where flag = 1", new ProjectDetailsRowMappers()))
				.thenReturn(list);

		List<ProjectDetails> all = projectDetailsDaoImpl.getAll();
		assertEquals(list, all);
	}

	@Test
	public void testGetById() throws SQLException, DataAccessException, ParseException {

		List<ProjectDetails> list = new ArrayList<ProjectDetails>();
		when(propsReaderUtil.getValue(anyString())).thenReturn("getAll_ProjectDetails");

		when(jdbcTemplate.query("select * from obs_projectdetails where id = 1", new ProjectDetailsRowMappers()))
				.thenReturn(list);
		List<ProjectDetails> getProjectDetails = projectDetailsDaoImpl.getById(1);
		assertEquals(list, getProjectDetails);
	}

}