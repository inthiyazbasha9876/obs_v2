/*
 * package com.ojas.obs.emp_edu.daoImpl; import static
 * org.junit.Assert.assertEquals; import static org.mockito.Mockito.mock; import
 * static org.mockito.Mockito.when;
 * 
 * import java.lang.reflect.Field; import java.sql.SQLException; import
 * java.util.ArrayList; import java.util.List;
 * 
 * import org.junit.Before; import org.junit.Test; import
 * org.mockito.InjectMocks; import org.mockito.Mock; import org.mockito.Mockito;
 * import org.mockito.Spy; import org.springframework.http.HttpStatus; import
 * org.springframework.http.ResponseEntity; import
 * org.springframework.jdbc.core.JdbcTemplate;
 * 
 * import com.ojas.obs.emp_edu.daoimpl.EmployeeEducationDetailsDaoImpl; import
 * com.ojas.obs.emp_edu.model.EmployeeEducationDetails; import
 * com.ojas.obs.emp_edu.model.EmployeeEducationDetailsRequest; import
 * com.ojas.obs.emp_edu.model.EmployeeEducationResponse; import
 * com.ojas.obs.emp_edu.model.ErrorResponse; public class
 * EmployeeEducationDetailsDaoImplTest {
 * 
 * @InjectMocks EmployeeEducationDetailsDaoImpl employeeEducationDetailsDaoImpl;
 * 
 * @Mock JdbcTemplate jdbcTemplate;
 * 
 * @Spy ErrorResponse errorResponse = new ErrorResponse();
 * 
 * @Spy EmployeeEducationDetailsRequest empEduRequest;
 * 
 * @Spy EmployeeEducationResponse empEduResponse;
 * 
 * @Spy ResponseEntity<Object> failureResponse = new
 * ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
 * 
 * @Spy ResponseEntity<Object> conflict = new
 * ResponseEntity<>(errorResponse,HttpStatus.CONFLICT);
 * 
 * @Spy ResponseEntity<Object> sucessResponse = new
 * ResponseEntity<>(errorResponse, HttpStatus.OK);
 * 
 * @Before public void init() throws Exception { employeeEducationDetailsDaoImpl
 * = new EmployeeEducationDetailsDaoImpl(); jdbcTemplate =
 * mock(JdbcTemplate.class); setCollaborator(employeeEducationDetailsDaoImpl,
 * "jdbctemplate", jdbcTemplate); }
 * 
 * public void setCollaborator(Object object, String name, Object service)
 * throws Exception { Field field; field =
 * object.getClass().getDeclaredField(name); field.setAccessible(true);
 * field.set(object, service); } public List<EmployeeEducationDetails>
 * getEmployeeEducation() { EmployeeEducationDetails employeeEducation = new
 * EmployeeEducationDetails(); employeeEducation.setId(1);
 * employeeEducation.setEmployeeId("123");
 * employeeEducation.setQualification(2);
 * employeeEducation.setYear_of_passing("2016");
 * employeeEducation.setPercentage_marks("99");
 * employeeEducation.setInstitution_name("ojas");
 * employeeEducation.setCreatedBy("1234");
 * employeeEducation.setUpdatedBy("1234"); employeeEducation.setFlag(true);
 * 
 * List<EmployeeEducationDetails> list = new
 * ArrayList<EmployeeEducationDetails>(); list.add(employeeEducation); return
 * list; } //@Test public void saveTest() throws SQLException { empEduRequest =
 * new EmployeeEducationDetailsRequest();
 * empEduRequest.setEmployeeEducationDetailsList(getEmployeeEducation());
 * empEduRequest.setTransaactionType("save"); int[] count= {1};
 * 
 * when(jdbcTemplate.batchUpdate(Mockito.anyString(),Mockito.anyList())).
 * thenReturn(count);
 * count=employeeEducationDetailsDaoImpl.saveEmployeeEducationDetails(
 * empEduRequest); int status=count.length; assertEquals(1,status );
 * 
 * }
 * 
 * }
 */