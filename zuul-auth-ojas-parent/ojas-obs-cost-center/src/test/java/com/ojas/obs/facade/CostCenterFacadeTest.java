package com.ojas.obs.facade;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.internal.util.privilegedactions.GetClassLoader;
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

import com.ojas.obs.dao.CostCenterDao;
import com.ojas.obs.model.CostCenter;
import com.ojas.obs.model.CostCenterRequest;
import com.ojas.obs.utility.ErrorResponse;

@RunWith(MockitoJUnitRunner.Silent.class)
public class CostCenterFacadeTest {

	@Mock
	private CostCenterDao costCenterDao;

	@InjectMocks
	private CostCenterFacadeImpl costCenterFacadeImpl;

	@Spy
	CostCenterRequest costCenterRequest;

	@Spy
	ErrorResponse error = new ErrorResponse();

	@Spy
	ResponseEntity<Object> objEntity = new ResponseEntity<Object>(error, HttpStatus.UNPROCESSABLE_ENTITY);

	@Spy
	ResponseEntity<Object> sucessResponse = new ResponseEntity<Object>(error, HttpStatus.OK);

	@Spy
	List<CostCenter> costCenterList = new ArrayList<CostCenter>();

	@Before
	public void init() {
		costCenterFacadeImpl = new CostCenterFacadeImpl();
		costCenterDao = mock(CostCenterDao.class);
		setCollabarator(costCenterFacadeImpl, "costcenterDao", costCenterDao);
	}

	public void setCollabarator(Object object, String name, Object collabarator) {
		Field field;
		try {
			field = object.getClass().getDeclaredField(name);
			field.setAccessible(true);
			field.set(object, collabarator);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<CostCenter> getCostCenter() {
		List<CostCenter> arrayList = new ArrayList<>();
		CostCenter costCenter = new CostCenter();
		costCenter.setId(1);
		costCenter.setCostCenterCode(123);
		CostCenter costCenter2 = new CostCenter();
		costCenter2.setId(1);
		costCenter2.setCostCenterCode(123);
		arrayList.add(costCenter);
		arrayList.add(costCenter2);
		return arrayList;
	}
	
	//------------save test case----------------
	
	@Test
	public void setTest() throws Exception {
		CostCenterRequest costCenterRequest2 = new CostCenterRequest();
		costCenterRequest2.setCostCenter(this.getCostCenter());
		costCenterRequest2.setTransactionType("save");
		Mockito.lenient().when(costCenterDao.save(costCenterRequest2)).thenReturn(true);
		ResponseEntity<Object> saveResponseEntity = costCenterFacadeImpl.set(costCenterRequest2);
		HttpStatus statusCode = saveResponseEntity.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);

	}
	
	//---------------null Transaction test case------------
	
//	@Test
//	public void setNullTransactionTest() throws Exception {
//		CostCenterRequest costCenterRequest2 = new CostCenterRequest();
//		costCenterRequest2.setCostCenter(this.getCostCenter());
//		costCenterRequest2.setTransactionType(null);
//		Mockito.lenient().when(costCenterDao.save(costCenterRequest2)).thenReturn(true);
//		ResponseEntity<Object> saveResponseEntity = costCenterFacadeImpl.set(costCenterRequest2);
//		HttpStatus statusCode = saveResponseEntity.getStatusCode();
//		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
//
//	}
	

	

	//-----------update test--------------------
	@Test
	public void setUpdateTest() throws Exception {
		CostCenterRequest costCenterRequest2 = new CostCenterRequest();
		List<CostCenter> arrayList = new ArrayList<>();
		CostCenter costCenter = new CostCenter();
		costCenter.setId(1);
		costCenter.setCostCenterCode(123);
		CostCenter costCenter2 = new CostCenter();
		costCenter2.setId(1);
		costCenter2.setCostCenterCode(123);
		arrayList.add(costCenter);
		arrayList.add(costCenter2);
		
		costCenterRequest2.setCostCenter(arrayList);

		costCenterRequest2.setTransactionType("update");
		Mockito.lenient().when(costCenterDao.save(costCenterRequest2)).thenReturn(true);
		ResponseEntity<Object> saveResponseEntity = costCenterFacadeImpl.set(costCenterRequest2);
		HttpStatus statusCode = saveResponseEntity.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);

	}
	
	//--------------GetAll test case--------------
	@Test
	public void testGet() throws Exception {
		CostCenterRequest costCenterRequest2 = new CostCenterRequest();
		
		
		Mockito.lenient().when(costCenterDao.getAllCostCenter(costCenterRequest2)).thenReturn(costCenterList);
		ResponseEntity<Object> getResponseEntity = costCenterFacadeImpl.get(costCenterRequest);
		HttpStatus statusCode = getResponseEntity.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

	//--------------GetAllNullTest---------------
	@Test
	public void testGetNull() throws Exception {
		CostCenterRequest costCenterRequest2 = new CostCenterRequest();
		
		costCenterRequest2.setCostCenter(null);
		
		Mockito.lenient().when(costCenterDao.getAllCostCenter(costCenterRequest2)).thenReturn(null);
		ResponseEntity<Object> getResponseEntity = costCenterFacadeImpl.get(costCenterRequest);
		HttpStatus statusCode = getResponseEntity.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}

}
