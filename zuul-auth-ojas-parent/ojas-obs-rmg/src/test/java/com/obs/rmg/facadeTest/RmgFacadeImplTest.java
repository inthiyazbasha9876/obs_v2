package com.obs.rmg.facadeTest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;

import com.obs.rmg.rmgdao.RmgDao;
import com.obs.rmg.rmgdao.RmgGenericRepository;
import com.obs.rmg.rmgdao.RmgGenericResourceMapRepository;
import com.obs.rmg.rmgdao.RmgSpecificRepository;
import com.obs.rmg.rmgfacadeimpl.RmgFacadeImpl;
import com.obs.rmg.rmgmodel.EmpInfo;
import com.obs.rmg.rmgmodel.RMG;
import com.obs.rmg.rmgmodel.RmgGeneric;
import com.obs.rmg.rmgmodel.RmgGenericResourceMap;
import com.obs.rmg.rmgmodel.RmgSpecific;
import com.obs.rmg.rmgmodel.SkillsList;
import com.obs.rmg.rmgrequest.RmgRequest;
import com.obs.rmg.rmgresponse.ErrorResponse;
import com.obs.rmg.rmgresponse.RmgResponse;


public class RmgFacadeImplTest 
{
	
	@InjectMocks
	RmgFacadeImpl rmgFacade;

	@Mock
	RmgDao rmgDao;
	
	@Mock
	RmgGenericRepository rmggenrepo;
	@Mock
	RmgSpecificRepository rmgspecificrepo;
	
	@Spy
	RmgRequest rmgrequest =new RmgRequest(); 
	
	@Spy
	ErrorResponse errorresponse;
	@Spy 
	RmgGeneric rg=new  RmgGeneric();
	
	@Spy
	RmgResponse  rmgresponse;
	
	@Spy
	RmgGenericResourceMap rgrm=new RmgGenericResourceMap();
	@Mock
    RmgGenericResourceMapRepository rmggenericresourcerepo;
	
	@Spy
	 Environment env;
	
	@Mock
	JdbcTemplate jd= new  JdbcTemplate();
	@Mock
	SkillsList sk=new SkillsList();
	@Spy
	ResponseEntity<Object> failureResponse = new ResponseEntity<>(errorresponse, HttpStatus.UNPROCESSABLE_ENTITY);
	@Spy
	ResponseEntity<Object> conflict = new ResponseEntity<>(errorresponse, HttpStatus.CONFLICT);
	@Spy
	ResponseEntity<Object> successResponse = new ResponseEntity<>(rmgresponse, HttpStatus.OK);
	@Spy
	RMG rmg =new RMG();
	@Spy
	RmgSpecific rm;
	@Before
	public void init() throws Exception {
		MockitoAnnotations.initMocks(this);

	}
	
	public RMG getRMGList() {
		RMG rmgdatalist = new RMG();
		rmgdatalist.setBookingId(1);
		rmgdatalist.setResourceType("specific");
		rmgdatalist.setFlag(false);
		rmgdatalist.setMessage("data");
		rmgdatalist.setStatus("accept");
		rmgdatalist.setProjectId("1001");
		return rmgdatalist;
	}
	
	public Set<RmgGeneric> genaric(){
	Set<RmgGenericResourceMap> rmggenericresourcemap = new HashSet<>();
	rmggenericresourcemap.add(rgrm);
	Set<RmgGeneric> rgs=new HashSet<>(); 
	Set<String> lod=new HashSet<String>();
	Set<RmgSpecific> rms=new HashSet<RmgSpecific>();
	Set<RMG> rmgo=new HashSet<RMG>();
	RMG  rlo=new RMG();
	rlo.setBookingId(10);
	rlo.setFlag(true);
	rlo.setMessage("good");
	rlo.setProjectId("125");
	rlo.setResourceType("Beanch");
	rlo.setRmggeneric(rgs);
	rlo.setRmgspecific(rms);
	rlo.setStatus("pending");
	lod.add("java");lod.add("sql");
	RmgGeneric rgo=new RmgGeneric();
	rgo.setBillRate(10.2f);
	rgo.setEndDate("25");
	rgo.setGenericId(5);
	rgo.setResourceCount(5);
	rgo.setResourceExperience(2.2);
	rgo.setResourceSkills(lod);
	rgo.setRmg(rmgo);
	rgs.add(rgo);
	rmgo.add(rlo);
	rgo.setRmggenericresourcemap(rmggenericresourcemap);
	return rgs;
	}
	public DataSource ds() {
		
		DataSource dataSource = mock(DataSource.class);
		
		return dataSource;
		
	}
	
	public Set<RmgSpecific> getrmggenericList()	{
		Set<RmgSpecific> rmggenericList = new HashSet<RmgSpecific>();
		RmgSpecific rmggenericdatalist = new RmgSpecific();
		rmggenericdatalist.setEmployeeId("1002");
		rmggenericdatalist.setBillRate(10);
		rmggenericdatalist.setEndDate("2019-09-09");
		rmggenericdatalist.setStartDate("2019-09-09");	
		rmggenericList.add(rmggenericdatalist);
		return rmggenericList;
	}
	
	@Test
	public void testSave() throws SQLException, IOException	{
		rmgrequest.setTransactiontype("save");
		rmg.setResourceType("specific");
		rmgrequest.setRmgInfo(rmg);
		rmgrequest.setRmgInfo(this.getRMGList());
		rmgrequest.setRmgSpecificList(this.getrmggenericList());
		when(rmgDao.save(rmg)).thenReturn(rmg);
		ResponseEntity<Object> saveStatus = rmgFacade.setRmg(rmgrequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	@Test
	public void testGenaric() throws SQLException, IOException	{
		rmgrequest.setTransactiontype("save");
		rmg.setResourceType("generic");
		rmg.setStatus("pendi");
		rmg.setProjectId("215");
		rmgrequest.setRmgInfo(rmg);
		rmgrequest.setRmgGenericList(genaric());
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds());

		when(rmgDao.save(rmg)).thenReturn(rmg);
		EmpInfo empInfo = new EmpInfo();
		List<EmpInfo> value=new ArrayList<>();
		value.add(empInfo);
		ResponseEntity<Object> saveStatus = rmgFacade.setRmg(rmgrequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}
	@Test
	public void updatespecificTest() {
		rmgrequest.setTransactiontype("update");
		rmg.setResourceType("specific");
		rmg.setBookingId(12);
		rmg.setStatus("pendi");
		//rmg.setResourceType("generic");
		rmgrequest.setRmgInfo(rmg);
		Set<RmgSpecific>  rsw=new HashSet<RmgSpecific>();
		RmgSpecific rsc=new RmgSpecific();
		rsc.setBillRate(2.5f);rsc.setEmployeeId("125");
		rsc.setEndDate("12");rsc.setReason("no");
		rsc.setSpecificId(12);
		rsw.add(rsc);
		rmgrequest.setRmgSpecificList(rsw);
		int id=rsc.getSpecificId();
		when(rmgspecificrepo.getOne(id)).thenReturn(rsc);
		when( rmgspecificrepo.save(rsc)).thenReturn(rsc);
		when(rmgDao.getOne(rmgrequest.getRmgInfo().getBookingId())).thenReturn(rmg);
		
		ResponseEntity<Object> saveStatus = rmgFacade.setRmg(rmgrequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
		
	}
	@Test
	public void updatestGenaricTest() {
		rmgrequest.setTransactiontype("update");
		rmg.setResourceType("Generic");
		rmg.setStatus("good");
		rmgrequest.setRmgInfo(rmg);
		rg.setGenericId(45);
		rmgrequest.setRmgGenericList(genaric());
		int id=rg.getGenericId();
		when(rmggenrepo.getOne(id)).thenReturn(rg);
		when(rmggenrepo.save(rg)).thenReturn(rg);
		when(rmgDao.getOne(rmgrequest.getRmgInfo().getBookingId())).thenReturn(rmg);
		ResponseEntity<Object> saveStatus = rmgFacade.setRmg(rmgrequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	
	@Test
	public void testSuccessDelete() throws SQLException, IOException	{
		rmgrequest.setTransactiontype("delete");
		rmgrequest.setRmgInfo(this.getRMGList());
		rmgrequest.setRmgSpecificList(this.getrmggenericList());
	    RMG customer2 = new RMG();
		when(rmgDao.getOne(rmgrequest.getRmgInfo().getBookingId())).thenReturn(customer2);
		customer2.setFlag(false);
		customer2.setBookingId(1);
		 when(rmgDao.save(customer2)).thenReturn(customer2);
		ResponseEntity<Object> saveStatus = rmgFacade.setRmg(rmgrequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	@Test
	public void testErrorDelete() throws SQLException, IOException{
		rmgrequest.setTransactiontype("delete");
		rmgrequest.setRmgInfo(this.getRMGList());
		rmgrequest.setRmgSpecificList(this.getrmggenericList());
	    RMG customer2 = new RMG();
		when(rmgDao.getOne(rmgrequest.getRmgInfo().getBookingId())).thenReturn(customer2);
	    customer2.setFlag(false);
		when(rmgDao.save(rmg)).thenReturn(rmg);
		ResponseEntity<Object> saveStatus = rmgFacade.setRmg(rmgrequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	
	@Test
	public void testError() throws SQLException, IOException{
		rmgrequest.setTransactiontype("kk");
		rmgrequest.setRmgInfo(this.getRMGList());
		rmgrequest.setRmgSpecificList(this.getrmggenericList());
		when(rmgDao.save(rmg)).thenReturn(rmg);
		ResponseEntity<Object> saveStatus = rmgFacade.setRmg(rmgrequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusCode);
	}
	
	//gettestcase
	
	
	@Test
	public void getByIdError() throws SQLException, IOException, URISyntaxException 
	{
		rmgrequest.setRmgInfo(this.getRMGList());
		rmgrequest.setRmgSpecificList(this.getrmggenericList());
		rmgrequest.setTransactiontype("getById");
		RMG deliverylocation2 = new RMG();
		int id=rmgrequest.getRmgInfo().getBookingId();
		RMG customerlist = new RMG();
		when(rmgDao.getOne(id)).thenReturn(customerlist);
		ResponseEntity<Object> saveStatus = rmgFacade.getRmg(rmgrequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.CONFLICT, statusCode);
	}
	
	@Test
	public void getByIdsuccess() throws SQLException, IOException, URISyntaxException {
		rmgrequest.setRmgInfo(this.getRMGList());
		rmgrequest.setRmgSpecificList(this.getrmggenericList());
		rmgrequest.setTransactiontype("getById");
		int id=rmgrequest.getRmgInfo().getBookingId();
		RMG customerlist = new RMG();
		when(rmgDao.getOne(id)).thenReturn(customerlist);
		customerlist.setBookingId(1);
		ResponseEntity<Object> saveStatus = rmgFacade.getRmg(rmgrequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	@Test
	public void getAllSuccess() throws SQLException, IOException, URISyntaxException {
		rmgrequest.setRmgInfo(this.getRMGList());
		rmgrequest.setRmgSpecificList(this.getrmggenericList());
		rmgrequest.setTransactiontype("getAll");
		List<RMG> customerlist = new ArrayList<RMG>();
		customerlist.add(this.getRMGList());
		when(rmgDao.findAll()).thenReturn(customerlist);
		ResponseEntity<Object> saveStatus = rmgFacade.getRmg(rmgrequest);
		HttpStatus statusCode = saveStatus.getStatusCode();
		assertEquals(HttpStatus.OK, statusCode);
	}
	
	
	
}
