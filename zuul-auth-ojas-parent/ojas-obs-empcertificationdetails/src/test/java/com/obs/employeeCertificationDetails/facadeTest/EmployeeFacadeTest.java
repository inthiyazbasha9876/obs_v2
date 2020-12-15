
  package com.obs.employeeCertificationDetails.facadeTest;
  
  import static org.junit.Assert.assertEquals; import static
  org.mockito.Mockito.mock; import static org.mockito.Mockito.when; import
  java.lang.reflect.Field; import java.sql.Date; import java.sql.SQLException;
  import java.sql.Timestamp; import java.util.ArrayList; import java.util.List;
  import org.junit.Before; import org.junit.Test; import
  org.mockito.InjectMocks; import org.mockito.Mock; import org.mockito.Spy;
  import org.springframework.http.HttpStatus; import
  org.springframework.http.ResponseEntity; import
  com.obs.employeeCertificationDetails.daoImpl.EmployeeCertificationDAOImpl;
  import com.obs.employeeCertificationDetails.error.ErrorResponse; import
  com.obs.employeeCertificationDetails.facadeImpl.
  CertificationDetailsFacadeImpl; import
  com.obs.employeeCertificationDetails.model.CertificationDetails; import
  com.obs.employeeCertificationDetails.request.CertificationDetailsRequest;
  import
  com.obs.employeeCertificationDetails.response.CertificationDetailsResponse;
  
  public class EmployeeFacadeTest {
  
  @InjectMocks CertificationDetailsFacadeImpl employeeFacadeImpl;
  
  @Mock EmployeeCertificationDAOImpl employeeCertificationDAOImpl;
  
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
  
  
	@Before
	public void init() {
		employeeFacadeImpl = new CertificationDetailsFacadeImpl();
		employeeCertificationDAOImpl = mock(EmployeeCertificationDAOImpl.class);
		setCollaborator(employeeFacadeImpl, "employeeCertificationDAOImpl", employeeCertificationDAOImpl);
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

	public List<CertificationDetails> getModel() {
		List<CertificationDetails> list = new ArrayList<CertificationDetails>();
		CertificationDetails model = new CertificationDetails();
		model.setCertificationName("dsgdhg");
		model.setCreatedBy("12345");
		model.setUpdatedBy("1221");
		model.setIssuedBy("hdjhf");
		model.setEmployeeId("1212");
		model.setCreatedDate(new Timestamp(112134));
		//model.setDateOfIssue(new Date(545454545));
		model.setDateOfIssue("545454545");
		model.setFlag(true);
		list.add(model);
		return list;
	}

	@Test
	public void saveTest() throws SQLException {
		certiRequest = new CertificationDetailsRequest();
		certiRequest.setCertificationDetailsModel(getModel());
		certiRequest.setTransactionType("save");
		when(employeeCertificationDAOImpl.saveCertificationDetails(certiRequest)).thenReturn(true);
		when(employeeCertificationDAOImpl.getAllCertificationDetailsCount()).thenReturn(4);
		ResponseEntity<Object> saveCertificate = employeeFacadeImpl.setCertificationDetails(certiRequest);
		HttpStatus statusCode = saveCertificate.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);

	}

	@Test
	public void saveTestNegative() throws SQLException {
		certiRequest = new CertificationDetailsRequest();
		certiRequest.setCertificationDetailsModel(getModel());
		certiRequest.setTransactionType("save");
		when(employeeCertificationDAOImpl.saveCertificationDetails(certiRequest)).thenReturn(false);
		when(employeeCertificationDAOImpl.getAllCertificationDetailsCount()).thenReturn(4);
		ResponseEntity<Object> saveCertificate = employeeFacadeImpl.setCertificationDetails(certiRequest);
		HttpStatus statusCode = saveCertificate.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);

	}
  
	@Test
	public void updateTest() throws SQLException {
		certiRequest = new CertificationDetailsRequest();
		certiRequest.setCertificationDetailsModel(getModel());
		certiRequest.setTransactionType("update");
		when(employeeCertificationDAOImpl.updateCertificationDetails(certiRequest)).thenReturn(true);
		when(employeeCertificationDAOImpl.getAllCertificationDetailsCount()).thenReturn(4);
		ResponseEntity<Object> updateCertificate = employeeFacadeImpl.setCertificationDetails(certiRequest);
		HttpStatus statusCode = updateCertificate.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);

	}

	@Test
	public void updateTestNegative() throws SQLException {
		certiRequest = new CertificationDetailsRequest();
		certiRequest.setCertificationDetailsModel(getModel());
		certiRequest.setTransactionType("update");
		when(employeeCertificationDAOImpl.updateCertificationDetails(certiRequest)).thenReturn(false);
		when(employeeCertificationDAOImpl.getAllCertificationDetailsCount()).thenReturn(4);
		ResponseEntity<Object> updateCertificate = employeeFacadeImpl.setCertificationDetails(certiRequest);
		HttpStatus statusCode = updateCertificate.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);

	}
  
	@Test
	public void deleteTest() throws SQLException {
		certiRequest = new CertificationDetailsRequest();
		certiRequest.setCertificationDetailsModel(getModel());
		certiRequest.setTransactionType("delete");
		when(employeeCertificationDAOImpl.deleteCertificationDetails(certiRequest)).thenReturn(true);
		when(employeeCertificationDAOImpl.getAllCertificationDetailsCount()).thenReturn(4);
		ResponseEntity<Object> deleteCertificate = employeeFacadeImpl.setCertificationDetails(certiRequest);
		HttpStatus statusCode = deleteCertificate.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);

	}
  
	@Test
	public void deleteTestNegative() throws SQLException {
		certiRequest = new CertificationDetailsRequest();
		certiRequest.setCertificationDetailsModel(getModel());
		certiRequest.setTransactionType("delete");
		when(employeeCertificationDAOImpl.deleteCertificationDetails(certiRequest)).thenReturn(false);
		when(employeeCertificationDAOImpl.getAllCertificationDetailsCount()).thenReturn(4);
		ResponseEntity<Object> deleteCertificate = employeeFacadeImpl.setCertificationDetails(certiRequest);
		HttpStatus statusCode = deleteCertificate.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);

	}
  
	@Test
	public void transactionTypeNullCheckForSave() throws SQLException {
		certiRequest = new CertificationDetailsRequest();
		certiRequest.setCertificationDetailsModel(getModel());
		certiRequest.setTransactionType("");
		when(employeeCertificationDAOImpl.saveCertificationDetails(certiRequest)).thenReturn(false);
		when(employeeCertificationDAOImpl.getAllCertificationDetailsCount()).thenReturn(2);
		ResponseEntity<Object> certificate = employeeFacadeImpl.setCertificationDetails(certiRequest);
		HttpStatus statusCode = certificate.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void transactionInvalid() throws SQLException {
		certiRequest = new CertificationDetailsRequest();
		certiRequest.setCertificationDetailsModel(getModel());
		certiRequest.setTransactionType("gjhfgh");
		when(employeeCertificationDAOImpl.saveCertificationDetails(certiRequest)).thenReturn(false);
		when(employeeCertificationDAOImpl.getAllCertificationDetailsCount()).thenReturn(2);
		ResponseEntity<Object> certificate = employeeFacadeImpl.setCertificationDetails(certiRequest);
		HttpStatus statusCode = certificate.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}

	@Test
	public void getTest() throws SQLException {
		certiRequest = new CertificationDetailsRequest();
		List<CertificationDetails> certificateList = new ArrayList<CertificationDetails>();
		CertificationDetails model = new CertificationDetails();
		certificateList.add(model);
		certiRequest.setCertificationDetailsModel(getModel());
		certiRequest.setTransactionType("getall");
		when(employeeCertificationDAOImpl.getAllCertificationDetails()).thenReturn(certificateList);
		when(employeeCertificationDAOImpl.getAllCertificationDetailsCount()).thenReturn(4);
		ResponseEntity<Object> getCertificate = employeeFacadeImpl.getCertificationDetails(certiRequest);
		HttpStatus statusCode = getCertificate.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);

	}
  
	@Test
	public void getNullRecordTest() throws SQLException {
		certiRequest = new CertificationDetailsRequest();
		List<CertificationDetails> certificateList = new ArrayList<CertificationDetails>(); //
		CertificationDetails model = new CertificationDetails(); // 
		//certificateList.add(model);
		certiRequest.setCertificationDetailsModel(getModel());
		certiRequest.setTransactionType("getall");
		when(employeeCertificationDAOImpl.getAllCertificationDetails()).thenReturn(certificateList);
		when(employeeCertificationDAOImpl.getAllCertificationDetailsCount()).thenReturn(4);
		ResponseEntity<Object> getCertificate = employeeFacadeImpl.getCertificationDetails(certiRequest);
		HttpStatus statusCode = getCertificate.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);

	}

	@Test
	public void getByEmpIdNullListTest() throws SQLException {
		certiRequest = new CertificationDetailsRequest();
		List<CertificationDetails> certificateList = new ArrayList<CertificationDetails>();
		CertificationDetails cr=new CertificationDetails();
		cr.setCreatedBy("ajay");
		cr.setEmployeeId("1856");
		cr.setId(2);
		certificateList.add(cr);
		certiRequest.setCertificationDetailsModel(certificateList);
		certiRequest.setTransactionType("getbyid");
		when(employeeCertificationDAOImpl.getDetailByEmpId(certiRequest)).thenReturn(certificateList);
		ResponseEntity<Object> getCertificate = employeeFacadeImpl.getCertificationDetails(certiRequest);
		HttpStatus statusCode = getCertificate.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);

	}
	@Test
	public void getByEmpIdNotNullListTest() throws SQLException {
		certiRequest = new CertificationDetailsRequest();
		List<CertificationDetails> certificateList = new ArrayList<CertificationDetails>();
		CertificationDetails model = new CertificationDetails();
		model.setCertificationName("java");
		model.setCreatedBy("121");
		model.setEmployeeId("12345");
		model.setCreatedDate(new Timestamp(112134));
		certificateList.add(model);
		certiRequest.setCertificationDetailsModel(getModel());
		certiRequest.setTransactionType("getbyid");
		when(employeeCertificationDAOImpl.getDetailByEmpId(certiRequest)).thenReturn(certificateList);
		ResponseEntity<Object> getCertificate = employeeFacadeImpl.getCertificationDetails(certiRequest);
		HttpStatus statusCode = getCertificate.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);

	}
	
	@Test
	public void getByIdNotNullTest() throws SQLException {
		certiRequest = new CertificationDetailsRequest();
		List<CertificationDetails> certificateList = new ArrayList<CertificationDetails>();
		CertificationDetails model = new CertificationDetails();
		model.setCertificationName("java");
		model.setCreatedBy("121");
		model.setEmployeeId("12345");
		model.setCreatedDate(new Timestamp(112134));
		certificateList.add(model);
		certiRequest.setCertificationDetailsModel(getModel());
		certiRequest.getCertificationDetailsModel().get(0).setId(11);
		certiRequest.getCertificationDetailsModel().get(0).setEmployeeId(null);
		certiRequest.setTransactionType("getbyid");
		when(employeeCertificationDAOImpl.getDetailById(certiRequest)).thenReturn(certificateList);
		when(employeeCertificationDAOImpl.getAllCertificationDetailsCount()).thenReturn(4);
		ResponseEntity<Object> getCertificate = employeeFacadeImpl.getCertificationDetails(certiRequest);
		HttpStatus statusCode = getCertificate.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);

	}
  
	@Test
	public void getNotNullPeginationTest() throws SQLException {
		certiRequest = new CertificationDetailsRequest();
		List<CertificationDetails> certificateList = new ArrayList<CertificationDetails>();
		CertificationDetails model = new CertificationDetails();
		certificateList.add(model);
		certiRequest.setCertificationDetailsModel(getModel());
		certiRequest.setTransactionType("getall");
		when(employeeCertificationDAOImpl.getAllCertificationDetails()).thenReturn(certificateList);
		when(employeeCertificationDAOImpl.getAllCertificationDetailsCount()).thenReturn(4);
		ResponseEntity<Object> getCertificate = employeeFacadeImpl.getCertificationDetails(certiRequest);
		HttpStatus statusCode = getCertificate.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);

	}
  
	@Test
	public void getAllCountTest() throws SQLException {
		certiRequest = new CertificationDetailsRequest();
		List<CertificationDetails> certificateList = new ArrayList<CertificationDetails>();
		CertificationDetails model = new CertificationDetails();
		certificateList.add(model);
		certiRequest.setCertificationDetailsModel(getModel());
		certiRequest.setTransactionType("get_count");
		when(employeeCertificationDAOImpl.getAllCertificationDetailsCount()).thenReturn(4);
		ResponseEntity<Object> getCertificate = employeeFacadeImpl.getCertificationDetails(certiRequest);
		HttpStatus statusCode = getCertificate.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);

	}
  
	@Test
	public void zeroCountTest() throws SQLException {
		certiRequest = new CertificationDetailsRequest();
		List<CertificationDetails> certificateList = new ArrayList<CertificationDetails>();
		CertificationDetails model = new CertificationDetails();
		certificateList.add(model);
		certiRequest.setCertificationDetailsModel(getModel());
		certiRequest.setTransactionType("get_count");
		when(employeeCertificationDAOImpl.getAllCertificationDetailsCount()).thenReturn(0);
		ResponseEntity<Object> getCertificate = employeeFacadeImpl.getCertificationDetails(certiRequest);
		HttpStatus statusCode = getCertificate.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);

	}
  
  
  }
 