package com.ojas.daotest;

import static org.junit.Assert.assertEquals;
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
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.ojas.obs.dao.BankDetailsDAO;
import com.ojas.obs.daoimpl.BankDetailsDaoImpl;
import com.ojas.obs.model.BankDetails;
import com.ojas.obs.request.BankDetailsRequest;

public class BankDetailsDaoTest {

	@InjectMocks
	BankDetailsDaoImpl bankDaoImpl;
	@Mock
	BankDetailsDAO bankDao;
	@Mock
	JdbcTemplate jdbcTemplate;
	@Spy
	BankDetails detail;
	@Spy
	BankDetailsRequest bankDetailsRequest;

	int[] count = { 1 };
	int[] noCount = {};

	@Before
	public void init() throws Exception {
		bankDaoImpl = new BankDetailsDaoImpl();
		jdbcTemplate = mock(JdbcTemplate.class);
		setCollaborator(bankDaoImpl, "jdbcTemplate", jdbcTemplate);
	}

	public void setCollaborator(Object object, String name, Object service) throws Exception {
		Field field;
		field = object.getClass().getDeclaredField(name);
		field.setAccessible(true);
		field.set(object, service);
	}
	
	List<BankDetails> getBankList() {
		List<BankDetails> bankList = new ArrayList<BankDetails>();
		detail = new BankDetails();
		detail.setBank_name("HDFC");
		detail.setBank_account_no("123456");
		detail.setBank_account_status("Active");
		detail.setBank_branch("Hyd");
		detail.setBank_city("Hyderabad");
		detail.setBank_ifsc_code("ABC123");
		detail.setEmployee_id("123");
		detail.setFlag(true);
		detail.setId(1);
		detail.setIs_active(true);
		bankList.add(detail);
		return bankList;
	}

	@Test
	public void saveTest() throws SQLException {
		BankDetailsRequest bankRequest = new BankDetailsRequest();
		bankRequest.setBankDetails(getBankList());
		when(jdbcTemplate.batchUpdate(anyString(), Mockito.anyList())).thenReturn(count);
		boolean status = bankDaoImpl.saveEmployeeBankDetails(bankRequest);
		assertEquals(true, status);
	}
	
	@Test
	public void saveNegative() throws SQLException {
		BankDetailsRequest bankRequest = new BankDetailsRequest();
		bankRequest.setBankDetails(getBankList());
		when(jdbcTemplate.batchUpdate(anyString(), Mockito.anyList())).thenReturn(noCount);
		boolean status = bankDaoImpl.saveEmployeeBankDetails(bankRequest);
		assertEquals(false, status);
	}
	
	@Test
	public void updateTest() throws SQLException {
		BankDetailsRequest bankRequest = new BankDetailsRequest();
		bankRequest.setBankDetails(getBankList());
		when(jdbcTemplate.batchUpdate(anyString(), Mockito.anyList())).thenReturn(count);
		boolean status = bankDaoImpl.updateEmployeeBankDetails(bankRequest);
		assertEquals(true, status);
	}
	
	@Test
	public void updateNegative() throws SQLException {
		BankDetailsRequest bankRequest = new BankDetailsRequest();
		bankRequest.setBankDetails(getBankList());
		when(jdbcTemplate.batchUpdate(anyString(), Mockito.anyList())).thenReturn(noCount);
		boolean status = bankDaoImpl.updateEmployeeBankDetails(bankRequest);
		assertEquals(false, status);
	}
	
	@Test
	public void deleteTest() throws SQLException {
		BankDetailsRequest bankRequest = new BankDetailsRequest();
		bankRequest.setBankDetails(getBankList());
		when(jdbcTemplate.batchUpdate(anyString(), Mockito.anyList())).thenReturn(count);
		boolean status = bankDaoImpl.deleteEmployeeBankDetails(bankRequest);
		assertEquals(true, status);
	}
	
	@Test
	public void deleteNegative() throws SQLException {
		BankDetailsRequest bankRequest = new BankDetailsRequest();
		bankRequest.setBankDetails(getBankList());
		when(jdbcTemplate.batchUpdate(anyString(), Mockito.anyList())).thenReturn(noCount);
		boolean status = bankDaoImpl.deleteEmployeeBankDetails(bankRequest);
		assertEquals(false, status);
	}
	
	@Test
	public void getByIdTest() throws SQLException {
		bankDetailsRequest = new BankDetailsRequest();
		bankDetailsRequest.setBankDetails(getBankList());
		bankDetailsRequest.setTransactionType("getall");
		when(jdbcTemplate.query("", new BeanPropertyRowMapper<BankDetails>(BankDetails.class))).thenReturn(getBankList());
		List<BankDetails> list = bankDaoImpl.getAllBankDetails(bankDetailsRequest);
		boolean b = list.isEmpty();
		assertEquals(true, b);
	}
	@Test
	public void getByEmpIdTest() throws SQLException {
		bankDetailsRequest = new BankDetailsRequest();
		List<BankDetails> bankList = getBankList();
		bankList.get(0).setEmployee_id(null);
		bankDetailsRequest.setBankDetails(bankList);
		bankDetailsRequest.setTransactionType("getall");
		when(jdbcTemplate.query("", new BeanPropertyRowMapper<BankDetails>(BankDetails.class))).thenReturn(getBankList());
		List<BankDetails> list = bankDaoImpl.getAllBankDetails(bankDetailsRequest);
		boolean b = list.isEmpty();
		assertEquals(true, b);
	}
	
	@Test
	public void getAllTest() throws SQLException {
		bankDetailsRequest = new BankDetailsRequest();
		List<BankDetails> bankList = new ArrayList<BankDetails>();
		bankDetailsRequest.setBankDetails(bankList);
		bankDetailsRequest.setTransactionType("getall");
		when(jdbcTemplate.query("", new BeanPropertyRowMapper<BankDetails>(BankDetails.class))).thenReturn(getBankList());
		List<BankDetails> list = bankDaoImpl.getAllBankDetails(bankDetailsRequest);
		boolean b = list.isEmpty();
		assertEquals(true, b);
	}
	
	@Test
	public void getAllTestNegative() throws SQLException {
		bankDetailsRequest = new BankDetailsRequest();
		List<BankDetails> bankList = new ArrayList<BankDetails>();
		BankDetails bank = new BankDetails();
		bank.setId(null);
		bankList.add(bank);
		bankDetailsRequest.setBankDetails(bankList);
		bankDetailsRequest.setTransactionType("getll");
		when(jdbcTemplate.query("", new BeanPropertyRowMapper<BankDetails>(BankDetails.class))).thenReturn(getBankList());
		List<BankDetails> list = bankDaoImpl.getAllBankDetails(bankDetailsRequest);
		boolean b = list.isEmpty();
		assertEquals(true, b);
	}
}
