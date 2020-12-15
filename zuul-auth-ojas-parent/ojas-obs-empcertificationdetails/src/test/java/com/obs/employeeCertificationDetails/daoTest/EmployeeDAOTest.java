
  package com.obs.employeeCertificationDetails.daoTest;
  
  import static org.junit.Assert.assertEquals; import static
  org.mockito.Mockito.mock; import static org.mockito.Mockito.when;
  
  import java.lang.reflect.Field; import java.sql.Date; import
  java.sql.SQLException; import java.sql.Timestamp; import java.util.ArrayList;
  import java.util.List;
  
  import org.junit.Before; import org.junit.Test; import
  org.mockito.InjectMocks; import org.mockito.Mock; import org.mockito.Mockito;
  import org.mockito.Spy; import org.springframework.http.HttpStatus; import
  org.springframework.http.ResponseEntity; import
  org.springframework.jdbc.core.BeanPropertyRowMapper; import
  org.springframework.jdbc.core.JdbcTemplate; import
  com.obs.employeeCertificationDetails.daoImpl.EmployeeCertificationDAOImpl;
  import com.obs.employeeCertificationDetails.error.ErrorResponse; import
  com.obs.employeeCertificationDetails.model.CertificationDetails; import
  com.obs.employeeCertificationDetails.request.CertificationDetailsRequest;
  import
  com.obs.employeeCertificationDetails.response.CertificationDetailsResponse;
  
  public class EmployeeDAOTest {
  
  @InjectMocks EmployeeCertificationDAOImpl employeeCertificationDAOImpl;
  
  @Mock JdbcTemplate jdbcTemplate;
  
  @Spy ErrorResponse errorResponse = new ErrorResponse();
  
  @Spy ResponseEntity<Object> failureResponse = new
  ResponseEntity<>(errorResponse, HttpStatus.UNPROCESSABLE_ENTITY);
  
  @Spy ResponseEntity<Object> conflict = new
  ResponseEntity<>(errorResponse,HttpStatus.CONFLICT);
  
  @Spy ResponseEntity<Object> sucessResponse = new
  ResponseEntity<>(errorResponse, HttpStatus.OK);
  
  @Spy CertificationDetailsRequest certiRequest = new
  CertificationDetailsRequest();
  
  @Spy CertificationDetailsResponse certiResponse = new
  CertificationDetailsResponse();
  
  @Spy List<CertificationDetails> list = new ArrayList<CertificationDetails>();
  int[] count= {1,2,3}; boolean status;
  
	@Before
	public void init() {
		employeeCertificationDAOImpl = new EmployeeCertificationDAOImpl();
		jdbcTemplate = mock(JdbcTemplate.class);
		setCollaborator(employeeCertificationDAOImpl, "jdbcTemplate", jdbcTemplate);
	}

	private void setCollaborator(Object object, String name, Object service) {
		Field field;
		try {
			field = object.getClass().getDeclaredField(name);
			field.setAccessible(true);
			field.set(object, service);
		} catch (Exception e) {}
	}

  public List<CertificationDetails> getModel() {
  List<CertificationDetails> list = new ArrayList<CertificationDetails>();
  CertificationDetails model= new CertificationDetails();
  model.setCertificationName("dsgdhg"); 
  model.setCreatedBy("12345");
  model.setUpdatedBy("1221"); model.setIssuedBy("hdjhf");
  model.setEmployeeId("1212"); model.setCreatedDate(new Timestamp(112134));
 // model.setDateOfIssue(new Date(545454545) );
  model.setDateOfIssue("545454545");
  model.setFlag(true);
  list.add(model); return list; }
  
  @Test
  public void saveTest() throws SQLException { certiRequest = new
  CertificationDetailsRequest();
  certiRequest.setCertificationDetailsModel(getModel());
  certiRequest.setTransactionType("save");
  
  when(jdbcTemplate.batchUpdate(Mockito.anyString(),Mockito.anyList())).
  thenReturn(count);
  status=employeeCertificationDAOImpl.saveCertificationDetails(certiRequest);
  assertEquals(true,status );
  
  }
  
  @Test 
  public void saveNegativeTest() throws SQLException { certiRequest = new
  CertificationDetailsRequest();
  certiRequest.setCertificationDetailsModel(getModel());
  certiRequest.setTransactionType("save");
  int[] count= {0,0,0};
  when(jdbcTemplate.batchUpdate(Mockito.anyString(),Mockito.anyList())).
  thenReturn(count);
  status=employeeCertificationDAOImpl.saveCertificationDetails(certiRequest);
  assertEquals(false,status );
  
  }
  
  @Test 
  public void updateTest() throws SQLException { certiRequest = new
  CertificationDetailsRequest();
  certiRequest.setCertificationDetailsModel(getModel());
  certiRequest.setTransactionType("save");
  
  when(jdbcTemplate.batchUpdate(Mockito.anyString(),Mockito.anyList())).
  thenReturn(count);
  status=employeeCertificationDAOImpl.updateCertificationDetails(certiRequest);
  assertEquals(true,status );
  
  }
  
  @Test public void updateNegativeTest() throws SQLException { certiRequest =
  new CertificationDetailsRequest();
  certiRequest.setCertificationDetailsModel(getModel());
  certiRequest.setTransactionType("save"); 
  int[] count= {0,0,0};
  when(jdbcTemplate.batchUpdate(Mockito.anyString(),Mockito.anyList())).
  thenReturn(count);
  status=employeeCertificationDAOImpl.updateCertificationDetails(certiRequest);
  assertEquals(false,status );
  
  }
  
  @Test public void deleteTest() throws SQLException { certiRequest = new
  CertificationDetailsRequest();
  certiRequest.setCertificationDetailsModel(getModel());
  certiRequest.setTransactionType("save"); 
  //int[] count= {0,0,0};
  when(jdbcTemplate.batchUpdate(Mockito.anyString(),Mockito.anyList())).
  thenReturn(count);
  status=employeeCertificationDAOImpl.deleteCertificationDetails(certiRequest);
  assertEquals(true,status );
  
  }
  
  @Test public void deleteNegativeTest() throws SQLException { certiRequest =
  new CertificationDetailsRequest();
  certiRequest.setCertificationDetailsModel(getModel());
  certiRequest.setTransactionType("save"); 
  int[] count= {0,0,0};
  when(jdbcTemplate.batchUpdate(Mockito.anyString(),Mockito.anyList())).
  thenReturn(count);
  status=employeeCertificationDAOImpl.deleteCertificationDetails(certiRequest);
  assertEquals(false,status );
  
  }
  
  @Test public void getAllTest() throws SQLException { certiRequest = new
  CertificationDetailsRequest();
  certiRequest.setCertificationDetailsModel(getModel());
  when(jdbcTemplate.query("fghfgh",new
  BeanPropertyRowMapper<CertificationDetails>(CertificationDetails.class))).
  thenReturn(getModel());
  List<CertificationDetails>num=employeeCertificationDAOImpl.
  getAllCertificationDetails(); boolean status=num.isEmpty();
  assertEquals(true,status );
  
  }
  
  @Test 
  public void getAllCountTest() throws SQLException { certiRequest = new
  CertificationDetailsRequest();
  certiRequest.setCertificationDetailsModel(getModel());
  int count=4;
  when(jdbcTemplate.
  queryForObject("select count(*) from obs_certificationdetails where flag= true"
  ,Integer.class)).thenReturn(count); 
  int  num=employeeCertificationDAOImpl.getAllCertificationDetailsCount();
  assertEquals(4,num );
  
  }
  
  

  @Test public void getByIdTest() throws SQLException { certiRequest = new
  CertificationDetailsRequest();
  certiRequest.setCertificationDetailsModel(getModel());
  when(jdbcTemplate.query("fghfgh",new
  BeanPropertyRowMapper<CertificationDetails>(CertificationDetails.class))).
  thenReturn(getModel());
  List<CertificationDetails>num=employeeCertificationDAOImpl.getDetailById(certiRequest);
  boolean status=num.isEmpty();
  assertEquals(true,status );
  
  }
  @Test public void getByEmpIdTest() throws SQLException { certiRequest = new
		  CertificationDetailsRequest();
		  certiRequest.setCertificationDetailsModel(getModel());
		  when(jdbcTemplate.query("fghfgh",new
		  BeanPropertyRowMapper<CertificationDetails>(CertificationDetails.class))).
		  thenReturn(getModel());
		  List<CertificationDetails>num=employeeCertificationDAOImpl.getDetailByEmpId(certiRequest);
		  boolean status=num.isEmpty();
		  assertEquals(true,status );
		  
		  }
  
  
  }
 