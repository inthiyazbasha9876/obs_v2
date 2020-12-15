 package com.ojas.obs.dao;

import static com.ojas.obs.constants.UserConstants.GETCOSTCENTER;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.ojas.obs.model.CostCenter;
import com.ojas.obs.model.CostCenterRequest;
import com.ojas.obs.utility.ErrorResponse;

@RunWith(MockitoJUnitRunner.Silent.class)
public class CostCenterDaoImplTest {

	

	@Mock
	private CostCenterImpl costCenterImpl;

	@Spy
	CostCenterRequest costCenterRequest;

	@Spy
	ErrorResponse error = new ErrorResponse();

	@Mock
	DataSource dataSource;

	@Mock
	Connection connection;

	@Mock
	JdbcTemplate jdbcTemplate;

	@Spy
	List<CostCenter> costCenterList = new ArrayList<CostCenter>();

	@Before
	public void init() {
		costCenterImpl = new CostCenterImpl();
		// costCenterDao=mock(CostCenterDao.class);
		jdbcTemplate = mock(JdbcTemplate.class);
		dataSource = mock(DataSource.class);
		connection = mock(Connection.class);
		this.setCollaborator(costCenterImpl, "jdbcTemplate", jdbcTemplate);

	}

	public void setCollaborator(Object object, String name, Object collab)

	{
		Field field;
		try {
			field = object.getClass().getDeclaredField(name);
			field.setAccessible(true);
			field.set(object, collab);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("exception occured");
		}

	}

	@Test
	public void getDataSource1() throws Exception {
		Mockito.lenient().when(jdbcTemplate.getDataSource()).thenReturn(dataSource);
		assertEquals(dataSource, jdbcTemplate.getDataSource());
	}

	
	public List<CostCenter> getCostCenter() {
		ArrayList<CostCenter> arrayList = new ArrayList<>();
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
	//----------------save test case-------------------
	@Test
	public void saveTest() throws Exception {
		costCenterRequest.setCostCenter(getCostCenter());
		costCenterRequest.setTransactionType("save");
		int[] count= {1,2};
		Mockito.lenient().when(jdbcTemplate.batchUpdate(Mockito.anyString(), Mockito.anyList())).thenReturn(count);
	boolean save = costCenterImpl.save(costCenterRequest);
		assertEquals(true, save);

	}
	
	
	
	//------------------update test case---------------
	
	@Test
	public void updateTest() throws Exception {
		costCenterRequest.setCostCenter(getCostCenter());
		costCenterRequest.setTransactionType("update");
		int[] count= {1,2};
		Mockito.lenient().when(jdbcTemplate.batchUpdate(Mockito.anyString(), Mockito.anyList())).thenReturn(count);
	boolean save = costCenterImpl.updateCenter(getCostCenter());
		assertEquals(true, save);

	}
	

	
	
	

	
	
	public List<CostCenter> getAllCostCenter() {
		CostCenter costCenter = new CostCenter();

		ArrayList<CostCenter> arrayList = new ArrayList<>();
		arrayList.add(costCenter);
		return arrayList;
	}

	public CostCenterRequest getCostCenterRequest() {
		CostCenterRequest costCenterRequest1 = new CostCenterRequest();
		costCenterRequest1.setCostCenter(this.getAllCostCenter());
		
		costCenterRequest1.setTransactionType("get");
		return costCenterRequest1;
	}

	@Test
	public void testGetAllCostCenter() throws Exception {
		
		
		int count=4;
		Mockito.lenient().when(jdbcTemplate.queryForObject("select count(*) from obs_costcenter", Integer.class)).thenReturn(count);
	int allCostCenterCount = costCenterImpl.getAllCostCenterCount();
		assertEquals(4,allCostCenterCount);

			}
	
	
	@Test
	public void testGetCostCenterCount() throws Exception {
		
		
		List<CostCenter> list = new ArrayList<CostCenter>();
		Mockito.lenient().when(jdbcTemplate.query(GETCOSTCENTER, new BeanPropertyRowMapper<>(CostCenter.class))).thenReturn(list);
		List<CostCenter> costCenterList1 = costCenterImpl.getAllCostCenter(getCostCenterRequest());
		assertEquals(list, costCenterList1);

			}

}
