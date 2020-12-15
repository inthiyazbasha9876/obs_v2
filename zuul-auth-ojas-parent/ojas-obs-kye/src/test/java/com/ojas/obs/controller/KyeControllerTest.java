package com.ojas.obs.controller;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertNotEquals;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ojas.obs.OjasObsKyeApplicationTests;
import com.ojas.obs.facade.KyeFacade;
import com.ojas.obs.model.KYE;
import com.ojas.obs.request.KYERequest;
import com.ojas.obs.utility.ErrorResponse;
import static org.mockito.ArgumentMatchers.any;
public class KyeControllerTest extends OjasObsKyeApplicationTests {

//	private MockMvc mockMvc;

//	@Mock
//	KyeFacade kyeFacade;

	/*
	 * @Autowired private WebApplicationContext webApplicationContext;
	 */

//	@Spy
//	ErrorResponse err = new ErrorResponse();
//
//	@Spy
//	ResponseEntity<Object> successEntity = new ResponseEntity<>(err, HttpStatus.OK);
//
//	@Spy
//	KyeController kyeController = new KyeController();
//
//	@Spy
//	KYERequest kyeRequest;
//
//	public KYERequest getRequest() {
//		kyeRequest = new KYERequest();
//		List<KYE> list = new ArrayList<KYE>();
//		KYE kye = new KYE();
//		kye.setkYE_Type("type1");
//		kye.setkYE_address("HYD");
//		kye.setCreated_by("18334");
//		kye.setEmployee_Id("18334");
//		kye.setPassport_address("Hyd");
//		kye.setPassport_date_of_Issue("2015-01-25");
//		kye.setPassport_date_of_expiry("2015-01-26");
//		kye.setPassport_no("12345");
//		kye.setPlace_of_issue("HYD");
//		kye.setUan("6789");
//		kye.setPassport_img("fgjfjgfdjgj");
//		kye.setPan_img("dhfjhddddruiir");
//		kye.setAadhar_img("dvshgdhsgyef");
//		kye.setAadhar_address("bvhhffyhejdjhf");
//		kye.setPan_number("sfsgdgdgfhfjggk");
//		kye.setAadhar_number("nbnbjgjgjgufj");
//		kye.setPassport_status(true);
//		kye.setAadhar_status(true);
//		kye.setPan_status(true);
//		list.add(kye);
//		kyeRequest.setKye(list);
//		kyeRequest.setTransactionType("save");
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
//		kyeController = new KyeController();
//		kyeFacade = mock(KyeFacade.class);
//		setCollabarator(kyeController, "kyeFacade", kyeFacade);
//		// mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
//	}
//
//	@Test
//	public void testSetKye() throws IOException {
//		HttpServletRequest request = null;
//		HttpServletResponse response = null;
//
//		// mockMvc.perform(get("/kye/set")).andExpect(status().isOk());
//		when(kyeFacade.setKYE(any())).thenReturn(successEntity);
//		ResponseEntity<Object> setKye = kyeController.setKye(getRequest(), request, response);
//		HttpStatus statusCode = setKye.getStatusCode();
//		assertEquals(HttpStatus.OK, statusCode);
//	}
//
//	@Test
//	public void testGetKey() {
//		HttpServletRequest request = null;
//		HttpServletResponse response = null;
//		when(kyeFacade.getKYE(any())).thenReturn(successEntity);
//		ResponseEntity<Object> key = kyeController.getKey(getRequest(), request, response);
//		HttpStatus statusCode = key.getStatusCode();
//		assertEquals(HttpStatus.OK, statusCode);
//	}
//
//
//
//	@Test
//	public void testSetKyeFieldsNull() throws IOException {
//		HttpServletRequest request = null;
//		HttpServletResponse response = null;
//		kyeRequest = new KYERequest();
//		List<KYE> list = new ArrayList<KYE>();
//		KYE kye = new KYE();
//		// kye.setkYE_Type("type1");
//		kye.setkYE_address("HYD");
//		kye.setCreated_by("18334");
//		kye.setEmployee_Id("18334");
//		kye.setPassport_address("Hyd");
//		kye.setPassport_date_of_Issue("2015-01-25");
//		kye.setPassport_date_of_expiry("2015-01-26");
//		kye.setPassport_no("12345");
//		kye.setPlace_of_issue("HYD");
//		kye.setUan("6789");
//		kye.setPassport_img("fgjfjgfdjgj");
//		kye.setPan_img("dhfjhddddruiir");
//		kye.setAadhar_img("dvshgdhsgyef");
//		kye.setAadhar_address("bvhhffyhejdjhf");
//		kye.setPan_number("sfsgdgdgfhfjggk");
//		kye.setAadhar_number("nbnbjgjgjgufj");
//		kye.setPassport_status(true);
//		kye.setAadhar_status(true);
//		kye.setPan_status(true);
//		list.add(kye);
//		kyeRequest.setKye(list);
//		kyeRequest.setTransactionType("save");
//		when(kyeFacade.setKYE(kyeRequest)).thenReturn(successEntity);
//		ResponseEntity<Object> setKye = kyeController.setKye(kyeRequest, request, response);
//		HttpStatus statusCode = setKye.getStatusCode();
//		assertNotEquals(HttpStatus.OK, statusCode);
//	}
//
//	@Test
//	public void testSetKyeTransactionTypeNull() throws IOException {
//		HttpServletRequest request = null;
//		HttpServletResponse response = null;
//		kyeRequest = new KYERequest();
//		List<KYE> list = new ArrayList<KYE>();
//		KYE kye = new KYE();
//		kye.setkYE_Type("type1");
//		kye.setkYE_address("HYD");
//		kye.setCreated_by("18334");
//		kye.setEmployee_Id("18334");
//		kye.setPassport_address("Hyd");
//		kye.setPassport_date_of_Issue("2015-01-25");
//		kye.setPassport_date_of_expiry("2015-01-26");
//		kye.setPassport_no("12345");
//		kye.setPlace_of_issue("HYD");
//		kye.setUan("6789");
//		kye.setPassport_img("fgjfjgfdjgj");
//		kye.setPan_img("dhfjhddddruiir");
//		kye.setAadhar_img("dvshgdhsgyef");
//		kye.setAadhar_address("bvhhffyhejdjhf");
//		kye.setPan_number("sfsgdgdgfhfjggk");
//		kye.setAadhar_number("nbnbjgjgjgufj");
//		kye.setPassport_status(true);
//		kye.setAadhar_status(true);
//		kye.setPan_status(true);
//		list.add(kye);
//		kyeRequest.setKye(list);
//		kyeRequest.setTransactionType("");
//		when(kyeFacade.setKYE(kyeRequest)).thenReturn(successEntity);
//		ResponseEntity<Object> setKye = kyeController.setKye(kyeRequest, request, response);
//		HttpStatus statusCode = setKye.getStatusCode();
//		assertNotEquals(HttpStatus.OK, statusCode);
//	}
//
//	@Test
//	public void testSetKyeIdNull() throws IOException {
//		HttpServletRequest request = null;
//		HttpServletResponse response = null;
//		kyeRequest = new KYERequest();
//		List<KYE> list = new ArrayList<KYE>();
//		KYE kye = new KYE();
//		kye.setkYE_Type("type1");
//		kye.setkYE_address("HYD");
//		kye.setCreated_by("18334");
//		kye.setEmployee_Id("18334");
//		kye.setPassport_address("Hyd");
//		kye.setPassport_date_of_Issue("2015-01-25");
//		kye.setPassport_date_of_expiry("2015-01-26");
//		kye.setPassport_no("12345");
//		kye.setPlace_of_issue("HYD");
//		kye.setUan("6789");
//		kye.setPassport_img("fgjfjgfdjgj");
//		kye.setPan_img("dhfjhddddruiir");
//		kye.setAadhar_img("dvshgdhsgyef");
//		kye.setAadhar_address("bvhhffyhejdjhf");
//		kye.setPan_number("sfsgdgdgfhfjggk");
//		kye.setAadhar_number("nbnbjgjgjgufj");
//		kye.setPassport_status(true);
//		kye.setAadhar_status(true);
//		kye.setPan_status(true);
//		list.add(kye);
//		kyeRequest.setKye(list);
//		kyeRequest.setTransactionType("update");
//		when(kyeFacade.setKYE(kyeRequest)).thenReturn(successEntity);
//		ResponseEntity<Object> setKye = kyeController.setKye(kyeRequest, request, response);
//		HttpStatus statusCode = setKye.getStatusCode();
//		assertNotEquals(HttpStatus.OK, statusCode);
//	}
//
//	
//
//	@Test
//	public void testGetKeyNull() {
//		HttpServletRequest request = null;
//		HttpServletResponse response = null;
//		when(kyeFacade.getKYE(null)).thenReturn(successEntity);
//		ResponseEntity<Object> key = kyeController.getKey(null, request, response);
//		HttpStatus statusCode = key.getStatusCode();
//		assertNotEquals(HttpStatus.OK, statusCode);
//	}
//
//	@Test
//	public void testGetKeyTransactionTypeNull() {
//		HttpServletRequest request = null;
//		HttpServletResponse response = null;
//		List<KYE> kyeList = new ArrayList<KYE>();
//		kyeList.add(null);
//		kyeRequest.setTransactionType("");
//		kyeRequest.setKye(kyeList);
//		when(kyeFacade.getKYE(null)).thenReturn(successEntity);
//		ResponseEntity<Object> key = kyeController.getKey(kyeRequest, request, response);
//		HttpStatus statusCode = key.getStatusCode();
//		assertNotEquals(HttpStatus.OK, statusCode);
//	}
//
//	@Test
//	public void testGetKeyCatchBlock() {
//		HttpServletRequest request = null;
//		HttpServletResponse response = null;
//		List<KYE> kyeList = new ArrayList<KYE>();
//		KYERequest kyeRequest = new KYERequest();
//		KYE kye = new KYE();
//		kyeList.add(kye);
//		kyeRequest.setKye(kyeList);
//		when(kyeFacade.getKYE(kyeRequest)).thenReturn(successEntity);
//		ResponseEntity<Object> setKye = kyeController.getKey(kyeRequest, request, response);
//		HttpStatus statusCode = setKye.getStatusCode();
//		assertNotEquals(HttpStatus.OK, statusCode);
//	}
//	
//	
	
}