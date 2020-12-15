package com.ojas.obs.facade;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
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

import com.ojas.obs.OjasObsKyeApplicationTests;
import com.ojas.obs.dao.KyeDao;
import com.ojas.obs.model.KYE;
import com.ojas.obs.request.KYERequest;
import com.ojas.obs.utility.ErrorResponse;

public class KyeFacadeImplTest extends OjasObsKyeApplicationTests {

//	@Mock
//	KyeDao kyeDao;
//
//	@InjectMocks
//	KyeFacadeImpl kyeFacadeimpl;
//
//	@Spy
//	ErrorResponse err = new ErrorResponse();
//
//	@Spy
//	ResponseEntity<Object> successEntity = new ResponseEntity<>(err, HttpStatus.OK);
//
//	@Spy
//	KYERequest kyeRequest;
//
//	@Spy
//	KYE mockKYE;
//
//	public List<KYE> getList() {
//		List<KYE> list = new ArrayList<>();
//		mockKYE = new KYE();
//		mockKYE.setkYE_Type("type1");
//		mockKYE.setkYE_address("HYD");
//		mockKYE.setCreated_by("18334");
//		mockKYE.setEmployee_Id("18334");
//		mockKYE.setPassport_address("Hyd");
//		mockKYE.setPassport_date_of_Issue("2015-01-25");
//		mockKYE.setPassport_date_of_expiry("2015-01-26");
//		mockKYE.setPassport_no("12345");
//		mockKYE.setPlace_of_issue("HYD");
//		mockKYE.setUan("6789");
//		mockKYE.setFlag(false);
//		mockKYE.setUpdated_by("18334");
//		mockKYE.setCreated_date("2019-04-02 17:04:14.0");
//		mockKYE.setUpdated_date("2019-04-02 17:04:14.0");
//		mockKYE.setPassport_img("fgjfjgfdjgj");
//		mockKYE.setPan_img("dhfjhddddruiir");
//		mockKYE.setAadhar_img("dvshgdhsgyef");
//		mockKYE.setAadhar_address("bvhhffyhejdjhf");
//		mockKYE.setPan_number("sfsgdgdgfhfjggk");
//		mockKYE.setAadhar_number("nbnbjgjgjgufj");
//		mockKYE.setPassport_status(true);
//		mockKYE.setAadhar_status(true);
//		mockKYE.setPan_status(true);
//		list.add(mockKYE);
//		return list;
//	}
//
//	public KYERequest getRequest() {
//		kyeRequest = new KYERequest();
//		List<KYE> list = new ArrayList<KYE>();
//		mockKYE = new KYE();
//		mockKYE.setkYE_Type("type1");
//		mockKYE.setkYE_address("HYD");
//		mockKYE.setCreated_by("18334");
//		mockKYE.setEmployee_Id("18334");
//		mockKYE.setPassport_address("Hyd");
//		mockKYE.setPassport_date_of_Issue("2015-01-25");
//		mockKYE.setPassport_date_of_expiry("2015-01-26");
//		mockKYE.setPassport_no("12345");
//		mockKYE.setPlace_of_issue("HYD");
//		mockKYE.setUan("6789");
//		mockKYE.setPassport_img("fgjfjgfdjgj");
//		mockKYE.setPan_img("dhfjhddddruiir");
//		mockKYE.setAadhar_img("dvshgdhsgyef");
//		mockKYE.setAadhar_address("bvhhffyhejdjhf");
//		mockKYE.setPan_number("sfsgdgdgfhfjggk");
//		mockKYE.setAadhar_number("nbnbjgjgjgufj");
//		mockKYE.setPassport_status(true);
//		mockKYE.setAadhar_status(true);
//		mockKYE.setPan_status(true);
//		list.add(mockKYE);
//		kyeRequest.setKye(list);
//		return kyeRequest;
//	}
//
//	public KYERequest getAllRequest() {
//		kyeRequest = new KYERequest();
//		List<KYE> list = new ArrayList<KYE>();
//		kyeRequest.setKye(list);
//		kyeRequest.setTransactionType("getAll");
//		return kyeRequest;
//	}
//
//	public void setCollabarator(Object object, String name, Object collabarator)
//			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
//		Field field;
//		field = object.getClass().getDeclaredField(name);
//		field.setAccessible(true);
//		field.set(object, collabarator);
//	}
//
//	@Before
//	public void setup()
//			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
//		kyeFacadeimpl = new KyeFacadeImpl();
//		kyeDao = mock(KyeDao.class);
//		setCollabarator(kyeFacadeimpl, "kyeDao", kyeDao);
//	}
//
//	@Test
//	public void testSetKYESave() throws Exception {
//		kyeRequest.setTransactionType("save");
//		when(kyeDao.saveKYE(kyeRequest)).thenReturn(true);
//		ResponseEntity<Object> setKYE = kyeFacadeimpl.setKYE(kyeRequest);
//		HttpStatus statusCode = setKYE.getStatusCode();
//		assertEquals(HttpStatus.OK, statusCode);
//	}
//
//	@Test
//	public void testSetKYEUpdate() throws SQLException {
//		kyeRequest.setTransactionType("update");
//		when(kyeDao.updateKYE(kyeRequest)).thenReturn(true);
//		ResponseEntity<Object> setKYE = kyeFacadeimpl.setKYE(kyeRequest);
//		HttpStatus statusCode = setKYE.getStatusCode();
//		assertEquals(HttpStatus.OK, statusCode);
//	}
//
//	@Test
//	public void testSetKYEDelete() throws SQLException {
//		kyeRequest.setTransactionType("delete");
//		when(kyeDao.deleteKYE(kyeRequest)).thenReturn(true);
//		ResponseEntity<Object> setKYE = kyeFacadeimpl.setKYE(kyeRequest);
//		HttpStatus statusCode = setKYE.getStatusCode();
//		assertEquals(HttpStatus.OK, statusCode);
//	}
//
//	@Test
//	public void testSetKYENullSave() throws Exception {
//		kyeRequest.setTransactionType("save");
//		when(kyeDao.saveKYE(kyeRequest)).thenReturn(false);
//		ResponseEntity<Object> setKYE = kyeFacadeimpl.setKYE(kyeRequest);
//		HttpStatus statusCode = setKYE.getStatusCode();
//		assertNotEquals(HttpStatus.OK, statusCode);
//	}
//
//	@Test
//	public void testSetKYENullUpdate() throws SQLException {
//		kyeRequest.setTransactionType("update");
//		when(kyeDao.updateKYE(kyeRequest)).thenReturn(false);
//		ResponseEntity<Object> setKYE = kyeFacadeimpl.setKYE(kyeRequest);
//		HttpStatus statusCode = setKYE.getStatusCode();
//		assertNotEquals(HttpStatus.OK, statusCode);
//	}
//
//	@Test
//	public void testSetKYENullDelete() throws SQLException {
//		kyeRequest.setTransactionType("delete");
//		when(kyeDao.deleteKYE(kyeRequest)).thenReturn(false);
//		ResponseEntity<Object> setKYE = kyeFacadeimpl.setKYE(kyeRequest);
//		HttpStatus statusCode = setKYE.getStatusCode();
//		assertNotEquals(HttpStatus.OK, statusCode);
//	}
//
//	@Test
//	public void testGetKYE() throws SQLException {
//		List<KYE> mockList = new ArrayList<>();
//		KYE mockKYE = mock(KYE.class);
//		mockList.add(mockKYE);
//		when(kyeDao.getAllKYE(any())).thenReturn(mockList);
//		ResponseEntity<Object> kye2 = kyeFacadeimpl.getKYE(getAllRequest());
//		HttpStatus statusCode = kye2.getStatusCode();
//		assertEquals(HttpStatus.OK, statusCode);
//	}
//
//	@Test
//	public void testGetKYENull() throws SQLException {
//		when(kyeDao.getAllKYE(getRequest())).thenReturn(null);
//		when(kyeDao.getCountPerPage(getList(), 1, 2)).thenReturn(null);
//		ResponseEntity<Object> kye2 = kyeFacadeimpl.getKYE(getAllRequest());
//		HttpStatus statusCode = kye2.getStatusCode();
//		assertNotEquals(HttpStatus.OK, statusCode);
//	}
//	
//
//	@Test
//	public void testGetKYECatchBlock() throws SQLException {
//		when(kyeDao.getAllKYE(getRequest())).thenReturn(null);
//		ResponseEntity<Object> kye2 = kyeFacadeimpl.getKYE(null);
//		HttpStatus statusCode = kye2.getStatusCode();
//		assertNotEquals(HttpStatus.OK, statusCode);
//	}
//
//	@Test
//	public void testGetKYEPageCount() throws SQLException {
//		List<KYE> mockList = new ArrayList<>();
//		KYE mockKYE = mock(KYE.class);
//		mockList.add(mockKYE);
//		KYERequest kyeRequest = new KYERequest();
//		kyeRequest.setTransactionType("getAll");
//		when(kyeDao.getAllKYE(any())).thenReturn(mockList);
//		ResponseEntity<Object> kye2 = kyeFacadeimpl.getKYE(kyeRequest);
//		HttpStatus statusCode = kye2.getStatusCode();
//		assertEquals(HttpStatus.OK, statusCode);
//	}
// @Test
// public void TestExceptionSave() throws Exception {
//	 
//	 
//	 when(kyeDao.saveKYE(kyeRequest)).thenThrow(new RuntimeException());
//	 ResponseEntity<Object> respEntity = kyeFacadeimpl.setKYE(kyeRequest);
//	 HttpStatus statusCode =respEntity.getStatusCode();
//	 assertEquals(HttpStatus.CONFLICT, statusCode);
// }
}
