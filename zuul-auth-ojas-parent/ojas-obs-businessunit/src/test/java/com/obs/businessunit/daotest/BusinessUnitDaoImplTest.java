package com.obs.businessunit.daotest;

import static com.obs.businessunit.constants.UserConstants.GETALLBUHEAD;
import static com.obs.businessunit.constants.UserConstants.SELECT_BUSINESSUNIT;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import com.obs.businessunit.daoimpl.BusinessUnitDaoImpl;
import com.obs.businessunit.error.ErrorResponse;
import com.obs.businessunit.model.BusinessUnit;
import com.obs.businessunit.request.BusinessUnitRequest;

@RunWith(MockitoJUnitRunner.Silent.class)
public class BusinessUnitDaoImplTest {

	

	@Mock
	private BusinessUnitDaoImpl businessUnitDaoImpl;

	@Spy
	BusinessUnitRequest businessUnitRequest;

	@Spy
	ErrorResponse error = new ErrorResponse();


	
	

	@Mock
	JdbcTemplate jdbcTemplate;

	@Spy
	List<BusinessUnit> costCenterList = new ArrayList<BusinessUnit>();

	@Before
	public void init() throws Exception {
		businessUnitDaoImpl = new BusinessUnitDaoImpl();
		jdbcTemplate = mock(JdbcTemplate.class);
		setCollaborator(businessUnitDaoImpl, "jdbcTemplate", jdbcTemplate);

	}

	public void setCollaborator(Object object, String name, Object collab) throws Exception

	{
		Field field;
		
			field = object.getClass().getDeclaredField(name);
			field.setAccessible(true);
			field.set(object, collab);
		

	}


	
	public List<BusinessUnit> getBusinessUnit() {
		ArrayList<BusinessUnit> arrayList = new ArrayList<>();
		BusinessUnit businessUnit = new BusinessUnit();
		businessUnit.setBusinessUnitName("fastfood");
		businessUnit.setId(1);
		businessUnit.setCostCenterId(456);
		arrayList.add(businessUnit);
		
		return arrayList;
	}
	//----------------save test case-------------------
	@Test
	public void saveTest() throws Exception {
		businessUnitRequest.setBusinessUnit(this.getBusinessUnit());
		businessUnitRequest.setTransactionType("save");
		int[] count= {1,2};
		when(jdbcTemplate.batchUpdate(anyString(), anyList())).thenReturn(count);
	boolean save = businessUnitDaoImpl.saveBusinessUnit(businessUnitRequest);
		assertEquals(true, save);

	}
	
	//----------------update test case-------------------
		@Test
		public void updateTest() throws Exception {
			businessUnitRequest.setBusinessUnit(this.getBusinessUnit());
			businessUnitRequest.setTransactionType("update");
			int[] count= {1,2};
			when(jdbcTemplate.batchUpdate(anyString(), anyList())).thenReturn(count);
	    	boolean save = businessUnitDaoImpl.updateBusinessUnit(businessUnitRequest);
			assertEquals(true, save);

		}
		@Test
		public void deleteTest() throws Exception {
			businessUnitRequest.setBusinessUnit(this.getBusinessUnit());
			businessUnitRequest.setTransactionType("delete");
			int[] count= {1,2};
			when(jdbcTemplate.batchUpdate(anyString(), anyList())).thenReturn(count);
	    	boolean delete = businessUnitDaoImpl.deleteBusinessUnit(businessUnitRequest);
			assertEquals(true, delete);

		}
		//----------------getAll  test case-------------------
				@Test
				public void getBuTest() throws Exception {
					List<BusinessUnit> list1=new ArrayList<>();
					List<String> list=new ArrayList<>();
					when(jdbcTemplate.queryForList(GETALLBUHEAD,String.class)).thenReturn(list);
		        	List<String> allBussinessDetails = businessUnitDaoImpl.getBuHeads(businessUnitRequest);
					assertEquals(list, allBussinessDetails);

				}
				
				@Test
				public void getAllTest() throws Exception {
					List<BusinessUnit> list=new ArrayList<>();
					when(jdbcTemplate.query(SELECT_BUSINESSUNIT, new BeanPropertyRowMapper<BusinessUnit>(BusinessUnit.class))).thenReturn(list);
		        	List<BusinessUnit> allBussinessDetails = businessUnitDaoImpl.getAllBussinessDetails(businessUnitRequest);
					assertEquals(list, allBussinessDetails);

				}
				//----------------getbyid  test case-------------------
	@Test
	public void getByIdTest() throws SQLException {
		businessUnitRequest.setBusinessUnit(getBusinessUnit());
		List<BusinessUnit> list=new ArrayList<>();
		when(jdbcTemplate.query("fghfgh", new BeanPropertyRowMapper<BusinessUnit>(BusinessUnit.class))).thenReturn(list);
		List<BusinessUnit> byId = businessUnitDaoImpl.getById(businessUnitRequest);
		assertEquals(list, byId);

	}
	
	

}