/*
 * package com.ojas.obs.dao;
 * 
 * import static com.ojas.obs.constants.UserConstants.GETALL; import static
 * com.ojas.obs.constants.UserConstants.GETBYEMPID; import static
 * com.ojas.obs.constants.UserConstants.GETBYID; import static
 * com.ojas.obs.constants.UserConstants.GETCOUNT; import static
 * org.junit.Assert.assertEquals; import static org.mockito.Mockito.mock;
 * 
 * import java.lang.reflect.Field; import java.sql.SQLException; import
 * java.util.ArrayList; import java.util.List;
 * 
 * import javax.activation.DataSource;
 * 
 * import org.junit.Before; import org.junit.Test; import
 * org.junit.runner.RunWith; import org.mockito.InjectMocks; import
 * org.mockito.Mock; import org.mockito.Mockito; import org.mockito.Spy; import
 * org.mockito.junit.MockitoJUnitRunner; import
 * org.springframework.jdbc.core.BeanPropertyRowMapper; import
 * org.springframework.jdbc.core.JdbcTemplate;
 * 
 * import com.ojas.obs.model.EmployeeSkillInfo; import
 * com.ojas.obs.model.EmployeeSkillInfoRequest;
 * 
 * @RunWith(MockitoJUnitRunner.class) public class EmployeeSkillDaoImplTest {
 * 
 * @Mock DataSource datasource;
 * 
 * @Mock JdbcTemplate jdbcTemplate;
 * 
 * @InjectMocks EmployeeSkillDaoImpl employeeSkillDaoImpl;
 * 
 * @Mock EmployeeSkillDao employeeSkillDao;
 * 
 * @Spy EmployeeSkillInfoRequest employeeSkillInfoRequest;
 * 
 * int[] arr = new int[4];
 * 
 * @Before public void setup() { employeeSkillDaoImpl = new
 * EmployeeSkillDaoImpl(); jdbcTemplate = mock(JdbcTemplate.class);
 * setCollabarator(employeeSkillDaoImpl, "jdbcTemplate", jdbcTemplate); }
 * 
 * public void setCollabarator(Object object, String name, Object collabarator)
 * { Field field; try { field = object.getClass().getDeclaredField(name);
 * field.setAccessible(true); field.set(object, collabarator); } catch
 * (Exception e) { e.printStackTrace(); } }
 * 
 * public EmployeeSkillInfoRequest employeeSkillInfoRequest() {
 * employeeSkillInfoRequest = new EmployeeSkillInfoRequest(); EmployeeSkillInfo
 * employeeSkillInfo = new EmployeeSkillInfo(); List<EmployeeSkillInfo>
 * employeeSkillInfoList = new ArrayList<EmployeeSkillInfo>();
 * employeeSkillInfo.setId(1); employeeSkillInfo.setSkill_id("Java");
 * employeeSkillInfo.setLevel_id(1); employeeSkillInfo.setEmployee_id("18290");
 * employeeSkillInfo.setCreated_by("1"); employeeSkillInfo.setUpdate_by(null);
 * employeeSkillInfo.setFlag(false);
 * employeeSkillInfoList.add(employeeSkillInfo);
 * employeeSkillInfoRequest.setSkillInfoModel(employeeSkillInfoList);
 * employeeSkillInfoRequest.setTransactionType("save");
 * employeeSkillInfoRequest.setSessionId("1234"); return
 * employeeSkillInfoRequest; }
 * 
 * public List<EmployeeSkillInfo> getEmployeeSkillInfo() { EmployeeSkillInfo
 * employeeSkillInfo = new EmployeeSkillInfo(); List<EmployeeSkillInfo>
 * employeeSkillInfoList = new ArrayList<EmployeeSkillInfo>();
 * employeeSkillInfo.setId(1); employeeSkillInfo.setSkill_id("Java");
 * employeeSkillInfo.setLevel_id(1); employeeSkillInfo.setEmployee_id("18290");
 * employeeSkillInfo.setCreated_by("1"); employeeSkillInfo.setUpdate_by(null);
 * employeeSkillInfo.setFlag(false);
 * employeeSkillInfoList.add(employeeSkillInfo); return employeeSkillInfoList; }
 * 
 * @Test public void saveEmployeeSkillInfo() throws SQLException {
 * employeeSkillInfoRequest = new EmployeeSkillInfoRequest();
 * employeeSkillInfoRequest.setSkillInfoModel(getEmployeeSkillInfo());
 * employeeSkillInfoRequest.setTransactionType("save");
 * Mockito.lenient().when(jdbcTemplate.batchUpdate(Mockito.anyString(),
 * Mockito.anyList())).thenReturn(arr); int saveSkill =
 * employeeSkillDaoImpl.saveEmployeeSkillInfo(employeeSkillInfoRequest);
 * assertEquals(1, saveSkill);
 * 
 * }
 * 
 * @Test public void updateEmployeeSkillInfo() throws SQLException {
 * Mockito.lenient().when(jdbcTemplate.batchUpdate(Mockito.anyString(),
 * Mockito.anyListOf(Object[].class))) .thenReturn(arr); int updateEmpSkill =
 * employeeSkillDaoImpl.updateEmployeeSkillInfo(employeeSkillInfoRequest());
 * assertEquals(1, updateEmpSkill);
 * 
 * }
 * 
 * @Test public void showEmployeeSkillInfo() throws SQLException {
 * List<EmployeeSkillInfo> list = new ArrayList<EmployeeSkillInfo>();
 * Mockito.lenient().when(jdbcTemplate.query(GETALL, new
 * BeanPropertyRowMapper<>(EmployeeSkillInfo.class))) .thenReturn(list);
 * List<EmployeeSkillInfo> skillList =
 * employeeSkillDaoImpl.showEmployeeSkillInfo(employeeSkillInfoRequest());
 * assertEquals(list, skillList); }
 * 
 * @Test public void getAllCount() throws SQLException {
 * Mockito.lenient().when(jdbcTemplate.queryForObject(GETCOUNT,
 * Integer.class)).thenReturn(1); int allCount =
 * employeeSkillDaoImpl.getAllCount(); assertEquals(1, allCount); }
 * 
 * @Test public void getById() throws SQLException {
 * 
 * List<EmployeeSkillInfo> list = new ArrayList<EmployeeSkillInfo>();
 * Mockito.lenient().when(jdbcTemplate.query(GETBYID, new
 * BeanPropertyRowMapper<>(EmployeeSkillInfo.class))) .thenReturn(list);
 * List<EmployeeSkillInfo> skillList =
 * employeeSkillDaoImpl.getById(employeeSkillInfoRequest); assertEquals(list,
 * skillList); }
 * 
 * @Test public void getByEmpId() throws SQLException {
 * 
 * List<EmployeeSkillInfo> list = new ArrayList<EmployeeSkillInfo>();
 * Mockito.lenient().when(jdbcTemplate.query(GETBYEMPID, new
 * BeanPropertyRowMapper<>(EmployeeSkillInfo.class))) .thenReturn(list);
 * List<EmployeeSkillInfo> skillList =
 * employeeSkillDaoImpl.getByEmpId(employeeSkillInfoRequest); assertEquals(list,
 * skillList); } }
 */