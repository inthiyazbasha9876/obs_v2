package com.ojas.obs.dao;

import static com.ojas.obs.utility.Constants.GETALL_KYE;
import static com.ojas.obs.utility.Constants.GETALL_KYE_COUNT;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.ojas.obs.OjasObsKyeApplicationTests;
import com.ojas.obs.model.KYE;
import com.ojas.obs.request.KYERequest;

public class KyeDaoImplTest extends OjasObsKyeApplicationTests {

//	@Mock
//	DataSource datasource;
//
//	@Mock
//	JdbcTemplate jdbcTemplate;
//
//	@InjectMocks
//	KyeDaoImpl kyeDaoImpl;
//
//	@Mock
//	KyeDao kyeDao;
//
//	int[] arr = new int[4];
//	int[] arr1 = new int[0];
//
//	@Before
//	public void setup()
//			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
//		datasource = mock(DataSource.class);
//		jdbcTemplate = new JdbcTemplate(datasource);
//		kyeDaoImpl = new KyeDaoImpl();
//		jdbcTemplate = mock(JdbcTemplate.class);
//		setCollabarator(kyeDaoImpl, "jdbcTemplate", jdbcTemplate);
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
//	@Spy
//	KYERequest kyeRequest;
//
//	public KYERequest getRequest() {
//		kyeRequest = new KYERequest();
//		List<KYE> list = new ArrayList<KYE>();
//		KYE kye = new KYE();
//		kye.setId(1);
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

	/*
	 * @Test public void testSaveKYE() throws Exception {
	 * 
	 * when(jdbcTemplate.batchUpdate(anyString(), anyList())).thenReturn(arr);
	 * boolean saveKYE = kyeDaoImpl.saveKYE(getRequest()); assertEquals(true,
	 * saveKYE); }
	 */
//	@Test
//	public void testSaveFalseKYE() throws Exception {
//
//		when(jdbcTemplate.batchUpdate(anyString(), anyList())).thenReturn(arr1);
//		boolean saveKYE = kyeDaoImpl.saveKYE(getRequest());
//		assertEquals(false, saveKYE);
//	}


//	@Test
//	public void testUpdateKYE() throws SQLException {
//		when(jdbcTemplate.batchUpdate(anyString(), anyList())).thenReturn(arr);
//		boolean updateKYE = kyeDaoImpl.updateKYE(getRequest());
//		assertEquals(true, updateKYE);
//	}
	

//	@Test
//	public void testUpdateFalseKYE() throws SQLException {
//		when(jdbcTemplate.batchUpdate(anyString(), anyList())).thenReturn(arr1);
//		boolean updateKYE = kyeDaoImpl.updateKYE(getRequest());
//		assertEquals(false, updateKYE);
//	}
//
//	@Test
//	public void testDeleteKYE() throws SQLException {
//		when(jdbcTemplate.batchUpdate(anyString(), anyList())).thenReturn(arr);
//		boolean deleteKYE = kyeDaoImpl.deleteKYE(getRequest());
//		assertEquals(true, deleteKYE);
//	}
//	@Test
//	public void testDeleteFalseKYE() throws SQLException {
//		when(jdbcTemplate.batchUpdate(anyString(), anyList())).thenReturn(arr1);
//		boolean deleteKYE = kyeDaoImpl.deleteKYE(getRequest());
//		assertEquals(false, deleteKYE);
//	}
//
//	@Test
//	public void testGetAllKYE() throws SQLException {
//		List<KYE> list = new ArrayList<KYE>();
//		when(jdbcTemplate.query(GETALL_KYE, new BeanPropertyRowMapper<>(KYE.class))).thenReturn(list);
//		List<KYE> KYEList = kyeDaoImpl.getAllKYE(getRequest());
//		assertEquals(list, KYEList);
//	}
//	@Test
//	public void testGetAllIdKYE() throws SQLException {
//		kyeRequest = new KYERequest();
//		List<KYE> list1 = new ArrayList<KYE>();
//		KYE kye = new KYE();
//		kye.setEmployee_Id("12e");
//		list1.add(kye);
//		kyeRequest.setKye(list1);
//		kyeRequest.setTransactionType("getall");
//		
//		List<KYE> list = new ArrayList<KYE>();
//		when(jdbcTemplate.query(GETALL_KYE, new BeanPropertyRowMapper<>(KYE.class))).thenReturn(list);
//		List<KYE> KYEList = kyeDaoImpl.getAllKYE(kyeRequest);
//		assertEquals(list, KYEList);
//	}
//
//	@Test
//	public void testgetAllKYECount() throws SQLException {
//		when(jdbcTemplate.queryForObject(GETALL_KYE_COUNT, Integer.class)).thenReturn(1);
//		int allKYECount = kyeDaoImpl.getAllKYECount();
//		assertEquals(1, allKYECount);
//	}
//
//	@Test
//	public void testCountPerPage() {
//		List<KYE> mockList = new ArrayList<>();
//		KYE mockKYE = mock(KYE.class);
//		mockList.add(mockKYE);
//		List<KYE> countPerPage = kyeDaoImpl.getCountPerPage(mockList, 1, 2);
//		assertEquals(mockList, countPerPage);
//	}
}
