package com.obs.rmg.rmgfacadeimpl;

import static com.obs.rmg.rmgconstants.RmgUtilConstants.ACCEPTED;
import static com.obs.rmg.rmgconstants.RmgUtilConstants.DELETE;
import static com.obs.rmg.rmgconstants.RmgUtilConstants.GENERIC;
import static com.obs.rmg.rmgconstants.RmgUtilConstants.GETALL;
import static com.obs.rmg.rmgconstants.RmgUtilConstants.GETBYID;
import static com.obs.rmg.rmgconstants.RmgUtilConstants.GETBYPROJECTID;
import static com.obs.rmg.rmgconstants.RmgUtilConstants.GETDEPLOYEDRESOURCESBYSKILLS;
import static com.obs.rmg.rmgconstants.RmgUtilConstants.GETEMPIDBYSKILLS;
import static com.obs.rmg.rmgconstants.RmgUtilConstants.GETEMPIDBYSTATUS;
import static com.obs.rmg.rmgconstants.RmgUtilConstants.GETPROJECTSBYEMPID;
import static com.obs.rmg.rmgconstants.RmgUtilConstants.GETRESOURCESBYPROJECT;
import static com.obs.rmg.rmgconstants.RmgUtilConstants.PENDING;
import static com.obs.rmg.rmgconstants.RmgUtilConstants.PROPOSAL;
import static com.obs.rmg.rmgconstants.RmgUtilConstants.REJECTED;
import static com.obs.rmg.rmgconstants.RmgUtilConstants.RELEASE;
import static com.obs.rmg.rmgconstants.RmgUtilConstants.SAVE;
import static com.obs.rmg.rmgconstants.RmgUtilConstants.SPECIFIC;
import static com.obs.rmg.rmgconstants.RmgUtilConstants.UPDATE;
import static com.obs.rmg.rmgconstants.RmgUtilConstants.UPDATEBOOKING;
import static com.obs.rmg.rmgconstants.RmgUtilConstants.UPDATETASKS;
import static com.obs.rmg.rmgconstants.RmgUtilConstants.SAVETASKS;
import static com.obs.rmg.rmgconstants.RmgUtilConstants.GETALLWORKEDHOURS;
import static com.obs.rmg.rmgconstants.RmgUtilConstants.GETRESOURCEPROJECTTASKS;
import static com.obs.rmg.rmgconstants.RmgUtilConstants.GETALLTASKS;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.obs.rmg.rmgdao.ProjectTasksRepository;
import com.obs.rmg.rmgdao.RmgDao;
import com.obs.rmg.rmgdao.RmgGenericRepository;
import com.obs.rmg.rmgdao.RmgGenericResourceMapRepository;
import com.obs.rmg.rmgdao.RmgSpecificRepository;
import com.obs.rmg.rmgfacade.RmgFacade;
import com.obs.rmg.rmgmodel.EmpInfo;
import com.obs.rmg.rmgmodel.EmployeeProjects;
import com.obs.rmg.rmgmodel.EmployeeSkills;
import com.obs.rmg.rmgmodel.HoursList;
import com.obs.rmg.rmgmodel.ProjectList;
import com.obs.rmg.rmgmodel.ProjectTasks;
import com.obs.rmg.rmgmodel.RMG;
import com.obs.rmg.rmgmodel.ResourceProjectTasks;
import com.obs.rmg.rmgmodel.RmgEmployeeList;
import com.obs.rmg.rmgmodel.RmgGeneric;
import com.obs.rmg.rmgmodel.RmgGenericResourceMap;
import com.obs.rmg.rmgmodel.RmgSpecific;
import com.obs.rmg.rmgrequest.RmgRequest;
import com.obs.rmg.rmgresponse.DeployresourceResponse;
import com.obs.rmg.rmgresponse.EmployeeHoursResponse;
import com.obs.rmg.rmgresponse.EmployeeProjectsResponse;
import com.obs.rmg.rmgresponse.ProjectListResponse;
import com.obs.rmg.rmgresponse.RmgResponse;

@Service
public class RmgFacadeImpl implements RmgFacade {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private EntityManager manager;

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private RmgDao rmgDao;

	@Autowired
	private RmgGenericRepository rmggenrepo;

	@Autowired
	private RmgSpecificRepository rmgspecificrepo;

	@Autowired
	private RmgGenericResourceMapRepository rmggenericresourcerepo;
	
	@Autowired
	private ProjectTasksRepository projecttasksrepo;

	@Autowired
	private Environment env;

	RmgResponse response = null;
	
	@Override
	public ResponseEntity<Object> setRmg(RmgRequest rmgRequest) {

		
		int a = 0;

		if (rmgRequest.getTransactiontype().equalsIgnoreCase(SAVE)) 
		{
			response = new RmgResponse();
			RMG rmgList = rmgRequest.getRmgInfo();

			if (rmgRequest.getRmgInfo().getResourceType().equalsIgnoreCase(SPECIFIC)) 
			{

				Set<RmgSpecific> rmgSpecificList = rmgRequest.getRmgSpecificList();
				rmgList.setRmgspecific(rmgSpecificList);

			}

			if (rmgRequest.getRmgInfo().getResourceType().equalsIgnoreCase(GENERIC)) 
			{
				for (RmgGeneric gen : rmgRequest.getRmgGenericList()) {

					for (RmgGenericResourceMap rmgMap : gen.getRmggenericresourcemap()) {
						rmgMap.setRmggeneric(gen);

					}
				}
				rmgList.setRmggeneric(rmgRequest.getRmgGenericList());

			}

			   RMG save = rmgDao.save(rmgList);
			  

				//sendmail(rmgRequest);
			 

			if (save != null) 
			{
				response.setStatusCode("200");
				response.setMessage("Resources Added successfully");
				response.setRmg(rmgList);
				return new ResponseEntity<>(response, HttpStatus.OK);
			} 
			else 
			{
				response.setStatusCode("409");
				response.setMessage("Resources not Added");
				return new ResponseEntity<>(response, HttpStatus.CONFLICT);

			}

		}
		if(rmgRequest.getTransactiontype().equalsIgnoreCase(UPDATEBOOKING))
		{
			response = new RmgResponse();
			RMG rmgList = rmgRequest.getRmgInfo();
			if (rmgRequest.getRmgInfo().getResourceType().equalsIgnoreCase(SPECIFIC)) 
			{
				Set<RmgSpecific> rmgSpecificList = rmgRequest.getRmgSpecificList();
				rmgList.setRmgspecific(rmgSpecificList);
				for (RmgSpecific spec : rmgSpecificList) 
				{

					int id = spec.getSpecificId();

					RmgSpecific rm = rmgspecificrepo.getOne(id);
					rm.setWeekOffDays(spec.getWeekOffDays());

					rm.setReason(spec.getReason());
					rm.setSpecificStatus(spec.getSpecificStatus());
					rm.setAlternateemployeeId(spec.getAlternateemployeeId());
					rm.setFlag(spec.getFlag());
					rm.setHoursOfAllocation(spec.getHoursOfAllocation());
					rm.setStartDate(spec.getStartDate());
					rm.setEndDate(spec.getEndDate());
					rmgspecificrepo.save(rm);

					RMG rmg = rmgDao.getOne(rmgRequest.getRmgInfo().getBookingId());
					rmg.setResourceType(rmgRequest.getRmgInfo().getResourceType());
					rmg.setStatus(rmgRequest.getRmgInfo().getStatus());

					rmgDao.save(rmg);

					response.setRmgSpecificList(rm);
					
				}
			}
			if (rmgRequest.getRmgInfo().getResourceType().equalsIgnoreCase(GENERIC)) {
				for (RmgGeneric gen : rmgRequest.getRmgGenericList()) {
					int gid = gen.getGenericId();
					RmgGeneric rmgeneric = rmggenrepo.getOne(gid);
					rmggenrepo.save(rmgeneric);
					//response.setRmgGenericList(rmgeneric);
					for (RmgGenericResourceMap rmgMap : gen.getRmggenericresourcemap()) {
						rmgMap.setRmggeneric(gen);
						int id = gen.getGenericId();
						RmgGeneric rg = rmggenrepo.getOne(id);
						RmgGenericResourceMap rmap = new RmgGenericResourceMap();
						rmap.setWeekOffDays(rmgMap.getWeekOffDays());
						rmap.setResourcegenericId(rmgMap.getResourcegenericId());
						rmap.setEmpId(rmgMap.getEmpId());
						rmap.setStartDate(rmgMap.getStartDate());
						rmap.setEndDate(rmgMap.getEndDate());
						rmap.setFlag(rmgMap.getFlag());
						rmap.setGenericStatus(rmgMap.getGenericStatus());
						rmap.setHoursOfAllocation(rmgMap.getHoursOfAllocation());
						rmap.setRmggeneric(rg);
						rmggenericresourcerepo.save(rmap);
						//response.setRmgGenericResourceList(rmgMap);
						response.setRmgGenericResourceList(rmap);
			
					}

				}

				rmgList.setRmggeneric(rmgRequest.getRmgGenericList());
				RMG rmg = rmgDao.getOne(rmgRequest.getRmgInfo().getBookingId());
				rmg.setResourceType(rmgRequest.getRmgInfo().getResourceType());
				rmg.setStatus(rmgRequest.getRmgInfo().getStatus());
				rmgDao.save(rmg);

			}
			if (rmgList.getBookingId() != 0) {

				response.setStatusCode("200");
				response.setMessage("update Successfully");
				return new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				response.setStatusCode("409");
				response.setMessage("failed to update");
				return new ResponseEntity<>(response, HttpStatus.CONFLICT);

			}
		}
		
		if(rmgRequest.getTransactiontype().equalsIgnoreCase(SAVETASKS))
		{
			response = new RmgResponse();
			RMG rmgList = rmgRequest.getRmgInfo();
			if (rmgRequest.getRmgInfo().getResourceType().equalsIgnoreCase(SPECIFIC)) 
			{
				Set<RmgSpecific> rmgSpecificList = rmgRequest.getRmgSpecificList();
				rmgList.setRmgspecific(rmgSpecificList);
				for (RmgSpecific spec : rmgSpecificList) 
				{				
					for(ProjectTasks pt:spec.getProjectTasks())
					{
						int sid = spec.getSpecificId();
						RmgSpecific rms = rmgspecificrepo.getOne(sid);
						ProjectTasks p1=new ProjectTasks();				 
						p1.setTaskName(pt.getTaskName());
						p1.setTaskStartDate(pt.getTaskStartDate());
						p1.setTaskEndDate(pt.getTaskEndDate());
						p1.setHoursPerDay(pt.getHoursPerDay());
					    p1.setTaskStatus(pt.getTaskStatus());
					    p1.setRmgspecific(rms);
					    projecttasksrepo.save(p1);
					    response.setProjectTasks(p1);
					}				
					rmgList.setRmgspecific(rmgRequest.getRmgSpecificList());
					RMG rmg = rmgDao.getOne(rmgRequest.getRmgInfo().getBookingId());
					rmg.setResourceType(rmgRequest.getRmgInfo().getResourceType());
					rmg.setStatus(rmgRequest.getRmgInfo().getStatus());
					rmgDao.save(rmg);
					
				}
			 }
			
			if (rmgRequest.getRmgInfo().getResourceType().equalsIgnoreCase(GENERIC)) 
			{
				for (RmgGeneric gen : rmgRequest.getRmgGenericList()) 
				{
					int gid = gen.getGenericId();
					RmgGeneric rmgeneric = rmggenrepo.getOne(gid);
					rmggenrepo.save(rmgeneric);
					//response.setRmgGenericList(rmgeneric);
					
					for (RmgGenericResourceMap rmgMap : gen.getRmggenericresourcemap()) 
					{
						int rmgid = rmgMap.getResourcegenericId();
						RmgGenericResourceMap rmgenericresource = rmggenericresourcerepo.getOne(rmgid);
						rmggenericresourcerepo.save(rmgenericresource);
						response.setRmgGenericResourceList(rmgenericresource);
						
						for(ProjectTasks pt:rmgMap.getProjectTasks())
						{
							int sid = rmgMap.getResourcegenericId();
							RmgGenericResourceMap rms = rmggenericresourcerepo.getOne(sid);
							ProjectTasks p1=new ProjectTasks();				 
							p1.setTaskName(pt.getTaskName());
							p1.setTaskStartDate(pt.getTaskStartDate());
							p1.setTaskEndDate(pt.getTaskEndDate());
							p1.setHoursPerDay(pt.getHoursPerDay());
						    p1.setTaskStatus(pt.getTaskStatus());
						    p1.setRmggenericresourcemap(rms);
						    projecttasksrepo.save(p1);
						    //response.setProjectTasks(p1);
						}
						rmgList.setRmggeneric(rmgRequest.getRmgGenericList());
						RMG rmg = rmgDao.getOne(rmgRequest.getRmgInfo().getBookingId());
						rmg.setResourceType(rmgRequest.getRmgInfo().getResourceType());
						rmg.setStatus(rmgRequest.getRmgInfo().getStatus());
						rmgDao.save(rmg);
					}
				}
			}
			
			if (rmgList.getBookingId() != 0) {

				response.setStatusCode("200");
				response.setMessage("tasks Added Successfully");
				return new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				response.setStatusCode("409");
				response.setMessage("failed to update tasks");
				return new ResponseEntity<>(response, HttpStatus.CONFLICT);

			}
		}
		
		if(rmgRequest.getTransactiontype().equalsIgnoreCase(UPDATETASKS))
		{
			response = new RmgResponse();
			RMG rmgList = rmgRequest.getRmgInfo();
			if (rmgRequest.getRmgInfo().getResourceType().equalsIgnoreCase(SPECIFIC)) 
			{
				Set<RmgSpecific> rmgSpecificList = rmgRequest.getRmgSpecificList();
				rmgList.setRmgspecific(rmgSpecificList);
				for (RmgSpecific spec : rmgSpecificList) 
				{
					
					for(ProjectTasks pt:spec.getProjectTasks())
					{
						int id = spec.getSpecificId();
						RmgSpecific rm = rmgspecificrepo.getOne(id);
						ProjectTasks p1=new ProjectTasks();	
						p1.setTaskId(pt.getTaskId());
						p1.setTaskName(pt.getTaskName());
						p1.setTaskStartDate(pt.getTaskStartDate());
						p1.setTaskEndDate(pt.getTaskEndDate());
						p1.setHoursPerDay(pt.getHoursPerDay());
					    p1.setTaskStatus(pt.getTaskStatus());
					    p1.setRmgspecific(rm);
						projecttasksrepo.save(p1);
						response.setProjectTasks(p1);

					}
					
					RMG rmg = rmgDao.getOne(rmgRequest.getRmgInfo().getBookingId());
					rmg.setResourceType(rmgRequest.getRmgInfo().getResourceType());
					rmg.setStatus(rmgRequest.getRmgInfo().getStatus());

					rmgDao.save(rmg);

				}
			}
			if (rmgRequest.getRmgInfo().getResourceType().equalsIgnoreCase(GENERIC)) 
			{
				for (RmgGeneric gen : rmgRequest.getRmgGenericList()) 
				{
					int gid = gen.getGenericId();
					RmgGeneric rmgeneric = rmggenrepo.getOne(gid);
					rmggenrepo.save(rmgeneric);
					//response.setRmgGenericList(rmgeneric);
					
					for (RmgGenericResourceMap rmgMap : gen.getRmggenericresourcemap()) 
					{
						int rmgid = rmgMap.getResourcegenericId();
						RmgGenericResourceMap rmgenericresource = rmggenericresourcerepo.getOne(rmgid);
						rmggenericresourcerepo.save(rmgenericresource);
						//response.setRmgGenericResourceList(rmgenericresource);
						for(ProjectTasks pt:rmgMap.getProjectTasks())
						{
							int sid = rmgMap.getResourcegenericId();
							RmgGenericResourceMap rms = rmggenericresourcerepo.getOne(sid);
							ProjectTasks p1=new ProjectTasks();		
							p1.setTaskId(pt.getTaskId());
							p1.setTaskName(pt.getTaskName());
							p1.setTaskStartDate(pt.getTaskStartDate());
							p1.setTaskEndDate(pt.getTaskEndDate());
							p1.setHoursPerDay(pt.getHoursPerDay());
						    p1.setTaskStatus(pt.getTaskStatus());
						    p1.setRmggenericresourcemap(rms);
						    projecttasksrepo.save(p1);
						    response.setProjectTasks(p1);
						}
						rmgList.setRmggeneric(rmgRequest.getRmgGenericList());
						RMG rmg = rmgDao.getOne(rmgRequest.getRmgInfo().getBookingId());
						rmg.setResourceType(rmgRequest.getRmgInfo().getResourceType());
						rmg.setStatus(rmgRequest.getRmgInfo().getStatus());
						rmgDao.save(rmg);
					}
				}
			}
			if (rmgList.getBookingId() != 0) {

				response.setStatusCode("200");
				response.setMessage("Tasks update Successfully");
				return new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				response.setStatusCode("409");
				response.setMessage("failed to update");
				return new ResponseEntity<>(response, HttpStatus.CONFLICT);

			}
		}

		if (rmgRequest.getTransactiontype().equalsIgnoreCase(UPDATE)) 
		{
			
			response = new RmgResponse();

			RMG rmgList = rmgRequest.getRmgInfo();

			if (rmgRequest.getRmgInfo().getResourceType().equalsIgnoreCase(SPECIFIC)) 
			{

				Set<RmgSpecific> rmgSpecificList = rmgRequest.getRmgSpecificList();

				rmgList.setRmgspecific(rmgSpecificList);
				 System.out.println("rmg list" +rmgSpecificList);

				for (RmgSpecific spec : rmgSpecificList) 
				{

					int id = spec.getSpecificId();

					RmgSpecific rm = rmgspecificrepo.getOne(id);
                    rm.setWeekOffDays(spec.getWeekOffDays());
					rm.setReason(spec.getReason());
					rm.setSpecificStatus(spec.getSpecificStatus());
					rm.setAlternateemployeeId(spec.getAlternateemployeeId());
					rm.setFlag(spec.getFlag());
					rmgspecificrepo.save(rm);

					RMG rmg = rmgDao.getOne(rmgRequest.getRmgInfo().getBookingId());
					rmg.setResourceType(rmgRequest.getRmgInfo().getResourceType());
					rmg.setStatus(rmgRequest.getRmgInfo().getStatus());

					rmgDao.save(rmg);

					response.setRmgSpecificList(rm);
					
				}

			}

			if (rmgRequest.getRmgInfo().getResourceType().equalsIgnoreCase(GENERIC)) {
				for (RmgGeneric gen : rmgRequest.getRmgGenericList()) {
					int gid = gen.getGenericId();
					RmgGeneric rmgeneric = rmggenrepo.getOne(gid);
					rmggenrepo.save(rmgeneric);
					response.setRmgGenericList(rmgeneric);
					for (RmgGenericResourceMap rmgMap : gen.getRmggenericresourcemap()) {
						rmgMap.setRmggeneric(gen);
						int id = gen.getGenericId();
						RmgGeneric rg = rmggenrepo.getOne(id);
						RmgGenericResourceMap rmap = new RmgGenericResourceMap();
						rmap.setWeekOffDays(rmgMap.getWeekOffDays());
						rmap.setEmpId(rmgMap.getEmpId());
						rmap.setStartDate(rmgMap.getStartDate());
						rmap.setEndDate(rmgMap.getEndDate());
						rmap.setFlag(rmgMap.getFlag());
						rmap.setGenericStatus(ACCEPTED);
						rmap.setHoursOfAllocation(rmgMap.getHoursOfAllocation());
						rmap.setRmggeneric(rg);
						rmggenericresourcerepo.save(rmap);
						//response.setRmgGenericResourceList(rmgMap);
						
					}

				}

				rmgList.setRmggeneric(rmgRequest.getRmgGenericList());
				RMG rmg = rmgDao.getOne(rmgRequest.getRmgInfo().getBookingId());
				rmg.setResourceType(rmgRequest.getRmgInfo().getResourceType());
				rmg.setStatus(rmgRequest.getRmgInfo().getStatus());
				rmgDao.save(rmg);
			}

			//sendmailupdate(rmgRequest);

			if (rmgList.getBookingId() != 0) {

				response.setStatusCode("200");
				return new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				response.setStatusCode("409");
				response.setMessage("failed to update");
				return new ResponseEntity<>(response, HttpStatus.CONFLICT);

			}

		}
		

		if (rmgRequest.getTransactiontype().equalsIgnoreCase(DELETE)) {
			response = new RmgResponse();
			RMG rmgList = rmgRequest.getRmgInfo();

			RMG rmg = rmgDao.getOne(rmgList.getBookingId());

			rmg.setFlag(!rmg.getFlag());
			rmgDao.save(rmg);

			if (rmg.getBookingId() != 0) {
				response.setStatusCode("200");
				response.setMessage("Rmg details deleted successfully");
				return new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				response.setStatusCode("409");
				response.setMessage("failed to delete");
				return new ResponseEntity<>(response, HttpStatus.CONFLICT);

			}

		}
		
		
		if (rmgRequest.getTransactiontype().equalsIgnoreCase(RELEASE))
		{
			response = new RmgResponse();
			RMG rmgList = rmgRequest.getRmgInfo();

			
			
			if(rmgRequest.getRmgInfo().getResourceType().equalsIgnoreCase(SPECIFIC))
			{
				Set<RmgSpecific> rs=rmgRequest.getRmgSpecificList();
				
				for(RmgSpecific rmgspec:rs)
				{
					setEmpIdStatus(rmgspec.getEmployeeId(),rmgspec.getSpecificEmployeeStatus());
					RmgSpecific rm = rmgspecificrepo.getOne(rmgspec.getSpecificId());
					rm.setFlag(rmgspec.getFlag());
					rmgspecificrepo.save(rm);
					
		
				}
			
			}
			if(rmgRequest.getRmgInfo().getResourceType().equalsIgnoreCase(GENERIC))
			{
				
				
				for(RmgGeneric gen : rmgRequest.getRmgGenericList())
				{
					setEmpIdgenericStatus(gen.getRmggenericresourcemap());
					
					for (RmgGenericResourceMap rmgMap : gen.getRmggenericresourcemap()) 
					{
						
						RmgGenericResourceMap rg = rmggenericresourcerepo.getOne(rmgMap.getResourcegenericId());
						rg.setFlag(rmgMap.getFlag());
						rmggenericresourcerepo.save(rg);
					}
					
				}
			
			}
	
			if (rmgList.getBookingId() != 0) {
				response.setStatusCode("200");
				response.setMessage("Resource Released");
				return new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				response.setStatusCode("409");
				response.setMessage("Failed to Relesae Resource");
				return new ResponseEntity<>(response, HttpStatus.CONFLICT);

			}

		}


		return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	
	
	@Override
	public ResponseEntity<Object> getRmg(RmgRequest rmgRequest) {
		RmgResponse response = null;
		List<RMG> getAll = new ArrayList<>();
		RMG rmg = null;

		if (rmgRequest.getTransactiontype().equalsIgnoreCase(GETALL)) {
			getAll = rmgDao.findAll();
			if (!getAll.isEmpty()) {
				response = new RmgResponse();
				response.setRmgInfo(getAll);
				response.setMessage("success");
				response.setStatusCode("200");

				return new ResponseEntity<>(response, HttpStatus.OK);
			}
		}
		if (rmgRequest.getTransactiontype().equalsIgnoreCase(GETALLTASKS)) {
			 List<RmgSpecific> specificfindAll = rmgspecificrepo.findAllSpecific();
			 List<RmgGenericResourceMap> genericfindAll = rmggenericresourcerepo.findAllGeneric();
			if (!specificfindAll.isEmpty()) {
				response = new RmgResponse();
				response.setRmgSpecifictasks(specificfindAll);
				response.setRmgGenericResourcetasks(genericfindAll);
				response.setMessage("success");
				response.setStatusCode("200");
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
		}

		if (rmgRequest.getTransactiontype().equalsIgnoreCase(GETEMPIDBYSKILLS)) {
			List<String> getskills = getEmpIdsBySkillId(rmgRequest.getSkilllist().getResourceSkills(),
					rmgRequest.getSkilllist().getResourceExperience());
		

			if (!getskills.isEmpty()) {
				response = new RmgResponse();
				response.setEmpIdList(getskills);
				response.setMessage("success");
				response.setStatusCode("200");
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
		}
		
		if (rmgRequest.getTransactiontype().equalsIgnoreCase(GETRESOURCEPROJECTTASKS)) {
			List<ProjectList> gettaskslist = getResourceProjecttasksbyProjectId(rmgRequest.getResourceprojecttasks().getProjectId());
			if (!gettaskslist.isEmpty()) {
				ProjectListResponse res = new ProjectListResponse();
				res.setProjectlist(gettaskslist);
				res.setMessage("success");
				res.setStatusCode("200");
				return new ResponseEntity<>(res, HttpStatus.OK);
			}
			else
			{
				ProjectListResponse res = new ProjectListResponse();
				res.setProjectlist(gettaskslist);
				res.setMessage("Not records found!");
				res.setStatusCode("206");
				return new ResponseEntity<>(res, HttpStatus.OK);
			}
		}
		
		
		if (rmgRequest.getTransactiontype().equalsIgnoreCase(GETEMPIDBYSTATUS)) 
		{
			
			List<RmgEmployeeList> getEmployees = rmgDao.getEmpIdsByStatus();

			if (!getEmployees.isEmpty()) 
			{
				response = new RmgResponse();
				response.setRmgemployeelist(getEmployees);
				response.setMessage("success");
				response.setStatusCode("200");
				return new ResponseEntity<>(response, HttpStatus.OK);
			}
		}
		

		if (rmgRequest.getTransactiontype().equalsIgnoreCase(GETPROJECTSBYEMPID)) {
			List<ProjectList> getprojects = getProjectListByEmpId(rmgRequest.getProjectlist().getEmpId());

			if (!getprojects.isEmpty()) {
				ProjectListResponse res = new ProjectListResponse();
				res.setProjectlist(getprojects);
			    getprojects.stream().forEach(obj->obj.setEmpId(rmgRequest.getProjectlist().getEmpId()));
				res.setMessage("success");
				res.setStatusCode("200");
				return new ResponseEntity<>(res, HttpStatus.OK);
			}
			else
			{
				ProjectListResponse res = new ProjectListResponse();
				res.setProjectlist(getprojects);
				res.setMessage("Not allocated to any project!");
				res.setStatusCode("206");
				return new ResponseEntity<>(res, HttpStatus.OK);
			}
		}
		
		if (rmgRequest.getTransactiontype().equalsIgnoreCase(GETRESOURCESBYPROJECT)) {
			List<EmployeeProjects> getresources = getResourcesbyprojectId(rmgRequest.getEmployeeprojects().getProjectId());
			if (!getresources.isEmpty()) {
				EmployeeProjectsResponse resp = new EmployeeProjectsResponse();
				getresources.stream().forEach(obj -> obj.setProjectId(rmgRequest.getEmployeeprojects().getProjectId()));
				resp.setEmployeeprojects(getresources);
				resp.setMessage("success");
				resp.setStatusCode("200");
				return new ResponseEntity<>(resp, HttpStatus.OK);
			}
			else
			{
				EmployeeProjectsResponse resp = new EmployeeProjectsResponse();
				resp.setEmployeeprojects(getresources);
				resp.setMessage("No resources allocated to this project!");
				resp.setStatusCode("206");
				return new ResponseEntity<>(resp, HttpStatus.OK);
			}
		}
		
		if (rmgRequest.getTransactiontype().equalsIgnoreCase(GETALLWORKEDHOURS)) {
			List<HoursList> getresourceshours = getAllWorkedHoursByDeployedResources();
			if (!getresourceshours.isEmpty()) {
				EmployeeHoursResponse resp = new EmployeeHoursResponse();
				resp.setHourslist(getresourceshours);
				resp.setMessage("success");
				resp.setStatusCode("200");
				return new ResponseEntity<>(resp, HttpStatus.OK);
			}
			else
			{
				EmployeeHoursResponse resp = new EmployeeHoursResponse();
				resp.setHourslist(getresourceshours);
				resp.setMessage("No records found!");
				resp.setStatusCode("206");
				return new ResponseEntity<>(resp, HttpStatus.OK);
			}
		}
				
		
		if (rmgRequest.getTransactiontype().equalsIgnoreCase(GETDEPLOYEDRESOURCESBYSKILLS)) {
			List<EmployeeSkills> empSkills = getdeployedresourcesbyskills(rmgRequest.getEmployeeskills().getSkills());
			String q1=" SELECT  sum(totalHours) as totalExp\r\n" + 
					"FROM\r\n" + 
					"        ( \r\n" + 
					"            (select round(DATEDIFF(CURDATE(), joining_date)/365) as totalHours from ojas_obs.obs_employmentdetails where employee_id=?) \r\n" + 
					"            UNION \r\n" + 
					"            (select Sum(experience) as totalHours from ojas_obs.obs_experiencedetails where employee_id=?)\r\n" + 
					"        ) s";
			Query query1 = manager.createNativeQuery(q1);
			int count=0;
			query1.setParameter(1, empSkills.get(0).getEmployeeId());
			query1.setParameter(2, empSkills.get(0).getEmployeeId());
			Double result1 =(Double) query1.getSingleResult();
		   Double current=(result1 * 1.5)+27000;
		   for(EmployeeSkills e:empSkills )
		   {
			   empSkills.get(count).setCurrentCompanySalary(current);
			   count++;
		   }
		
		 
			
			if (!empSkills.isEmpty()) {
				DeployresourceResponse res = new DeployresourceResponse();
				res.setEmployeeskills(empSkills);
				res.setMessage("success");
				res.setStatusCode("200");
				return new ResponseEntity<>(res, HttpStatus.OK);
			}
		}
		
	
		if (rmgRequest.getTransactiontype().equalsIgnoreCase(GETBYID)) {

			rmg = rmgDao.getOne(rmgRequest.getRmgInfo().getBookingId());

			if (rmg.getBookingId() != 0) {
				response = new RmgResponse();
				response.setRmg(rmg);
				response.setMessage("success");
				response.setStatusCode("200");
				return new ResponseEntity<>(response, HttpStatus.OK);

			}
			
			
		}
		
		if (rmgRequest.getTransactiontype().equalsIgnoreCase(GETBYPROJECTID)) {

			List<RMG> rmglist = rmgDao.getByprojectId(rmgRequest.getRmgInfo().getProjectId());

			if (rmglist != null) {
				response = new RmgResponse();
				response.setRmgInfo(rmglist);
				response.setMessage("success");
				response.setStatusCode("200");
				return new ResponseEntity<>(response, HttpStatus.OK);

			}
	}
		
		
		if (getAll.isEmpty() || rmg == null) {
			response = new RmgResponse();
			response.setMessage("No records found");
			response.setStatusCode("409");
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);

	}


	private List<String> getEmpIdsBySkillId(List<String> list, Double exp) {

		List<String> result = new ArrayList<String>();

		String queryParams = getStringQueryParameter(list);

		String queryStrs = "select DISTINCT(e.employee_id) from  ojas_obs.obs_employeeskilldetails e join "
				+ "ojas_obs.obs_experiencedetails p on  e.employee_id=p.employee_id   join "
				+ " obs_rmg.rmg_employee_list ei on ei.employee_id=p.employee_id where ei.employment_status='Bench' or ei.employment_status='Lateral' and"
				+ " e.skill_id in(" + queryParams + ") and p.experience = " + exp + "";

		Query query = manager.createNativeQuery(queryStrs);

		result = query.getResultList();
		
		return result;

	}
	
	
	private String getStringQueryParameter(List<String> list) 
	{

		String queryParams = "\"";

		Iterator<String> iter = list.iterator();
		int i = 0;

		while (iter.hasNext()) {
			if (i != list.size()) {

				queryParams = queryParams + iter.next() + "\",\"";

			} else {

				queryParams = queryParams + "\"" + iter.next();

			}
			i++;
		}
		return queryParams = queryParams.substring(0, queryParams.length() - 2);

	}

	public DataSource getDataSource() 
	{
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
		driverManagerDataSource.setUrl(env.getProperty("spring.datasource.url"));
		driverManagerDataSource.setUsername(env.getProperty("spring.datasource.username"));
		driverManagerDataSource.setPassword(env.getProperty("spring.datasource.password"));
		driverManagerDataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
		return driverManagerDataSource;
	}

	private List<HoursList> getAllWorkedHoursByDeployedResources() 
	{
		List<String> result = new ArrayList<String>();

		String query = "(SELECT sp.employee_id,sp.hours_of_allocation FROM obs_psa.project_info pi join obs_rmg.rmg rm  on rm.project_id=pi.project_id\r\n" + 
				" join obs_rmg.rmg_specific_map sm  on sm.booking_id=rm.booking_id join obs_rmg.rmg_specific sp on sp.specific_id=sm.specific_id where pi.flag=1)\r\n" + 
				" union \r\n" + 
				"(SELECT gsr.emp_id,gsr.hours_of_allocation FROM obs_psa.project_info pi join obs_rmg.rmg rm  on rm.project_id=pi.project_id\r\n" + 
				" join obs_rmg.rmg_generic_map gm  on gm.booking_id=rm.booking_id join obs_rmg.rmg_generic_resources_mapping gsr on gsr.generic_id=gm.generic_id where pi.flag=1)";

		Query querys = manager.createNativeQuery(query);

		result = querys.getResultList();

		return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(HoursList.class));

	}
	
	private List<ProjectList> getResourceProjecttasksbyProjectId(String projectId) 
	{
		List<ProjectList> result = new ArrayList<ProjectList>();

		String query = "(select distinct(spc.employee_id),em.full_name,rm.booking_id,spc.specific_id,pt.task_name,pt.task_start_date,pt.task_end_date,pt.task_status,pt.hours_per_day from  obs_rmg.rmg_specific_map r\r\n" + 
				"join obs_rmg.rmg rm on r.booking_id=rm.booking_id  join obs_rmg.rmg_specific spc on spc.specific_id=r.specific_id  join obs_rmg.project_tasks pt on pt.specific_id=spc.specific_id join obs_rmg.rmg_employee_list  em on spc.employee_id=em.employee_id where  spc.flag=1 and project_id="+projectId+")\r\n" + 
				"union\r\n" + 
				"(select distinct(grm.emp_id),em.full_name,rr.booking_id,grm.resourcegeneric_id,pt.task_name,pt.task_start_date,pt.task_end_date,pt.task_status,pt.hours_per_day from obs_rmg.rmg rr join obs_rmg.rmg_generic_map gm on  gm.booking_id=rr.booking_id \r\n" + 
				"join obs_rmg.rmg_generic_resources_mapping grm  on gm.generic_id=grm.generic_id  join obs_rmg.project_tasks pt on grm.resourcegeneric_id=pt.resourcegeneric_id join obs_rmg.rmg_employee_list  em on grm.emp_id=em.employee_id where grm.flag=1 and project_id="+projectId+")";
		Query querys = manager.createNativeQuery(query);
		result = querys.getResultList();
		return jdbcTemplate.query(query,new BeanPropertyRowMapper(ResourceProjectTasks.class));

	}
	
	private List<ProjectList> getProjectListByEmpId(String emp) 
	{
		List<String> result = new ArrayList<String>();

		String query = "(select distinct(rm.project_id),pi.start_date,pi.end_date,hours_of_allocation,pi.project_name,spc.specific_id,rm.booking_id from obs_rmg.rmg_specific spc  join obs_rmg.rmg_specific_map r on spc.specific_id=r.specific_id  \r\n" + 
				"		join obs_rmg.rmg rm on r.booking_id=rm.booking_id  join obs_psa.project_info pi on pi.project_id=rm.project_id where  spc.flag=1 and employee_id="+emp+") union\r\n" + 
				"		(select distinct(rr.project_id),gr.hours_of_allocation,pi.start_date,pi.end_date,gm.generic_id,rr.booking_id,pi.project_name from obs_rmg.rmg_generic_resources_mapping gr join obs_rmg.rmg_generic_map  gm on gr.generic_id=gm.generic_id \r\n" + 
				"		join  obs_rmg.rmg rr on  gm.booking_id=rr.booking_id join obs_psa.project_info pi on pi.project_id=rr.project_id  where gr.flag=1 and emp_id="+emp+")ORDER BY project_id ASC";

		Query querys = manager.createNativeQuery(query);
		result = querys.getResultList();
		return jdbcTemplate.query(query,new BeanPropertyRowMapper(ProjectList.class));

	}
	
	private List<EmployeeProjects> getResourcesbyprojectId(String projectid) 
	{
		List<String> result = new ArrayList<String>();

		String query = "select distinct(employee_id),spc.start_date,spc.end_date,spc.bill_rate,spc.hours_of_allocation,spc.specific_id,rm.booking_id  from obs_rmg.rmg_specific spc  join obs_rmg.rmg_specific_map r on spc.specific_id=r.specific_id \r\n" + 
				"join obs_rmg.rmg rm on r.booking_id=rm.booking_id where  spc.flag=1 and project_id="+projectid+" union\r\n" + 
				"\r\n" +
				"(select distinct(emp_id),gr.start_date,gr.end_date,gg.bill_rate,gr.hours_of_allocation,rr.booking_id,gm.generic_id from obs_rmg.rmg_generic_resources_mapping gr join obs_rmg.rmg_generic_map  gm on gr.generic_id=gm.generic_id \r\n" + 
				"join  obs_rmg.rmg rr join obs_rmg.rmg_generic gg on  gm.booking_id=rr.booking_id where gr.flag=1 and project_id="+projectid+")";

		Query querys = manager.createNativeQuery(query);

		result = querys.getResultList();

		return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(EmployeeProjects.class));

	}
	
	
	private List<EmployeeSkills> getdeployedresourcesbyskills(List<String> list) {
		List<EmployeeSkills> result = new ArrayList<>();
		String queryParams = getStringQueryParameter(list);
		String queryStrs = "(select distinct(s.employee_id),pi.project_name,e.employment_status,s.skill_id,rs.bill_rate ,rs.start_date,rs.end_date,l.from_date,l.to_date,CONCAT(COALESCE(em.firstname,''), ' ', COALESCE(em.middlename,''), ' ', COALESCE(em.lastname,'')) as empName  from ojas_obs.obs_employeeskilldetails s join  obs_rmg.rmg_employee_list e on s.employee_id = e.employee_id join ojas_obs.obs_employeeinfo em on\r\n" + 
				" em.employee_id=s.employee_id  join obs_rmg.rmg_specific rs on rs.employee_id= s.employee_id  join obs_lms.leave_info l on l.emp_id=s.employee_id   join ojas_obs.obs_employmentdetails employe on employe.employee_id=s.employee_id  join obs_rmg.rmg_specific_map r on rs.specific_id=r.specific_id  \r\n" + 
				"join obs_rmg.rmg rm on r.booking_id=rm.booking_id  join obs_psa.project_info pi on pi.project_id=rm.project_id and  l.from_date between rs.start_date and rs.end_date  and  l.status= 'approved' and s.skill_id in ("+queryParams+") where   rs.flag=1 and e.employment_status LIKE 'Deployed%' )\r\n" + 
				" union\r\n" + 
				" (select distinct(s.employee_id),pi.project_name,e.employment_status,s.skill_id,gs.bill_rate ,gs.start_date,gs.end_date,l.from_date,l.to_date,CONCAT(COALESCE(em.firstname,''), ' ', COALESCE(em.middlename,''), ' ', COALESCE(em.lastname,'')) as empName from ojas_obs.obs_employeeskilldetails s join  obs_rmg.rmg_employee_list e on s.employee_id = e.employee_id join ojas_obs.obs_employeeinfo em on\r\n" + 
				" em.employee_id=s.employee_id  join obs_rmg.rmg_generic gs join  obs_rmg.rmg_generic_resources_mapping rmg on rmg.emp_id= s.employee_id join obs_lms.leave_info l on l.emp_id=s.employee_id  join ojas_obs.obs_employmentdetails employe on employe.employee_id=s.employee_id  join obs_rmg.rmg_generic_resources_mapping gr join obs_rmg.rmg_generic_map  gm on gr.generic_id=gm.generic_id \r\n" + 
				"join  obs_rmg.rmg rr on  gm.booking_id=rr.booking_id join obs_psa.project_info pi on pi.project_id=rr.project_id  and  l.from_date between gs.start_date  and gs.end_date and  l.status= 'approved' and s.skill_id in ("+queryParams+")  where  gr.flag=1 and e.employment_status LIKE 'Deployed%')";  
		Query query = manager.createNativeQuery(queryStrs);
		result = query.getResultList();		
		return jdbcTemplate.query(queryStrs, new BeanPropertyRowMapper<>(EmployeeSkills.class));
	}
	private int setEmpIdStatus(String emp,String employeestatus) 
	{
	    String query =  "update obs_rmg.rmg_employee_list set status_date=now(), employment_status= ? where employee_id= ?";    
	    int batchUpdate = jdbcTemplate.update(query, employeestatus, emp);    
		return batchUpdate;
	}
	
	private int[] setEmpIdgenericStatus(Set<RmgGenericResourceMap> rmgMap) 
	{
		List<Object[]> empObj = new ArrayList<>();
		for (RmgGenericResourceMap rmgGen : rmgMap) {
			Object[] emp = {rmgGen.getGenericEmployeeStatus(), rmgGen.getEmpId()};
			empObj.add(emp);
		}
	    String query =  "update obs_rmg.rmg_employee_list set status_date=now(), employment_status=?  where employee_id= ?"; 	    
		return jdbcTemplate.batchUpdate(query, empObj);
	}
	
	public boolean sendmail(RmgRequest rmgRequest) 
	{
		if (rmgRequest.getRmgInfo().getStatus().equalsIgnoreCase(PENDING) && rmgRequest.getRmgInfo().getStatus() != null)		
		{
			String queryStr = "select distinct( officialEmail),(employee_id),CONCAT(e.firstname,' ',e.middlename,' ',e.lastname,' ') as empName "
					+ "from ojas_obs.obs_employeeinfo e join  obs_psa.resource_mapping resm \r\n"
					+ "on resm.project_manager = e.employee_id join obs_rmg.rmg rm on  resm.project_id = rm.project_id where\r\n"
					+ " rm.project_id =" + rmgRequest.getRmgInfo().getProjectId();

			Object[] args = { rmgRequest.getRmgInfo().getProjectId() };

			jdbcTemplate = new JdbcTemplate(getDataSource());
			List<EmpInfo> emp = jdbcTemplate.query(queryStr, new BeanPropertyRowMapper<EmpInfo>(EmpInfo.class));
			String[] to = emp.stream().map(x -> x.getOfficialEmail()).collect(Collectors.toList()).stream()
					.toArray(String[]::new);

			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setFrom(env.getProperty("spring.mail.username"));
			mailMessage.setTo(to);

			mailMessage.setSubject("Welcome to Ojas Family");

			if (rmgRequest.getRmgInfo().getResourceType().equalsIgnoreCase(SPECIFIC)) 
			{
				mailMessage.setText("Specific Resource Requested By " + emp.get(0).getEmpName() + " ("
						+ emp.get(0).getEmployeeId() + ')' + '\n' + '\n'
						+ "This is Auto Generated Mail please ignore it");
			}

			if (rmgRequest.getRmgInfo().getResourceType().equalsIgnoreCase(GENERIC)) 
			{
				mailMessage.setText("Generic Resource Requested By " + emp.get(0).getEmpName() + " ("
						+ emp.get(0).getEmployeeId() + ')' + '\n' + '\n'
						+ "This is Auto Generated Mail please ignore it");
			}
			javaMailSender.send(mailMessage);
		}	
		return true;	
	}
	
	
	public boolean sendmailupdate(RmgRequest rmgRequest)
	{	
		if ((rmgRequest.getRmgInfo().getStatus().equalsIgnoreCase(PENDING)
				|| rmgRequest.getRmgInfo().getStatus().equalsIgnoreCase(REJECTED)
				|| rmgRequest.getRmgInfo().getStatus().equalsIgnoreCase(PROPOSAL))
				&& (rmgRequest.getRmgInfo().getResourceType().equalsIgnoreCase(SPECIFIC)
						&& rmgRequest.getRmgInfo().getStatus() != null
						&& rmgRequest.getRmgInfo().getBookingId() != 0))
		{

			String queryStr = "select distinct( officialEmail),(employee_id),CONCAT(e.firstname,' ',e.middlename,' ',e.lastname,' ') as empName from ojas_obs.obs_employeeinfo e join  obs_psa.resource_mapping resm \r\n"
					+ "on resm.project_manager = e.employee_id join obs_rmg.rmg rm on  resm.project_id = rm.project_id where\r\n"
					+ " rm.project_id =" + rmgRequest.getRmgInfo().getProjectId();

			Object[] args = {rmgRequest.getRmgInfo().getProjectId()};
			jdbcTemplate = new JdbcTemplate(getDataSource());
			List<EmpInfo> emp = jdbcTemplate.query(queryStr, new BeanPropertyRowMapper<EmpInfo>(EmpInfo.class));
			String[] to = emp.stream().map(x -> x.getOfficialEmail()).collect(Collectors.toList()).stream()
					.toArray(String[]::new);

			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setFrom(env.getProperty("spring.mail.username"));
			mailMessage.setTo(to);
			mailMessage.setSubject("Resource Management Group");
			if (rmgRequest.getRmgInfo().getStatus().equalsIgnoreCase(PENDING))
			{
				response.setMessage("Resource details updated successfully");
				mailMessage.setText(
						" Resource Requesting." + '\n' + '\n' + "This is Auto Generated Mail please ignore it");
			}
			if (rmgRequest.getRmgInfo().getStatus().equalsIgnoreCase(REJECTED)) 
			{
				response.setMessage("Resource Declined");
				mailMessage.setText(
						" Resource Declined." + '\n' + '\n' + "This is Auto Generated Mail please ignore it");
			}
			if (rmgRequest.getRmgInfo().getStatus().equalsIgnoreCase(PROPOSAL)) 
			{	
				response.setMessage("New Resource Proposed");
				mailMessage.setText("Proposing for New Resource" + '\n' + '\n'
						+ "This is Auto Generated Mail please ignore it");
			}
			javaMailSender.send(mailMessage);
		}
		
	
		if (rmgRequest.getRmgInfo().getStatus().equalsIgnoreCase(ACCEPTED)
				&& rmgRequest.getRmgInfo().getStatus() != null && rmgRequest.getRmgInfo().getBookingId() != 0
				&& (rmgRequest.getRmgInfo().getResourceType().equalsIgnoreCase(SPECIFIC)
						|| rmgRequest.getRmgInfo().getResourceType().equalsIgnoreCase(GENERIC))) 
		{

			String queryStr = "select distinct( officialEmail),(employee_id),CONCAT(e.firstname,' ',e.middlename,' ',e.lastname,' ') as empName from ojas_obs.obs_employeeinfo e join  obs_psa.resource_mapping resm \r\n"
					+ "on resm.project_manager = e.employee_id join obs_rmg.rmg rm on  resm.project_id = rm.project_id where\r\n"
					+ " rm.project_id =" + rmgRequest.getRmgInfo().getProjectId();

			Object[] args = { rmgRequest.getRmgInfo().getProjectId() };
			jdbcTemplate = new JdbcTemplate(getDataSource());
			List<EmpInfo> emp = jdbcTemplate.query(queryStr, new BeanPropertyRowMapper<EmpInfo>(EmpInfo.class));
			String[] to = emp.stream().map(x -> x.getOfficialEmail()).collect(Collectors.toList()).stream()
					.toArray(String[]::new);
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setFrom(env.getProperty("spring.mail.username"));
			mailMessage.setTo(to);
			if (rmgRequest.getRmgInfo().getResourceType().equalsIgnoreCase(SPECIFIC)) 
			{
				List<String> empIds = rmgRequest.getRmgSpecificList().stream().map(RmgSpecific::getEmployeeId)
						.collect(Collectors.toList());
				String queryParams = getStringQueryParameter(empIds);
				String queryStr1 = "select DISTINCT(e.officialEmail) from  ojas_obs.obs_employeeinfo e where e.employee_id in ("
						+ queryParams + ")";
				Query query = manager.createNativeQuery(queryStr1);
				List<String> emails = query.getResultList();
				String[] emailsCC = emails.toArray(new String[0]);
				mailMessage.setCc(emailsCC);		
				mailMessage.setText("Specific Resource Accepted." + '\n' + '\n' + "This is Auto Generated Mail please ignore it");		
				response.setMessage("Specific Resource Accepted");			
				Set<RmgSpecific> rmgSpecificList = rmgRequest.getRmgSpecificList();	
				for(RmgSpecific spec: rmgSpecificList)
				{
				  setEmpIdStatus(spec.getEmployeeId(),spec.getSpecificEmployeeStatus());
				}						
			}
			
			if (rmgRequest.getRmgInfo().getResourceType().equalsIgnoreCase(GENERIC))
			{

				List<String> empIds = rmgRequest.getRmgGenericList().stream()
						.flatMap(
								rrm -> rrm.getRmggenericresourcemap().stream().map(RmgGenericResourceMap::getEmpId))
					.collect(Collectors.toList());
				String queryParams = getStringQueryParameter(empIds);
				String queryStr1 = "select DISTINCT(e.officialEmail) from  ojas_obs.obs_employeeinfo e where e.employee_id in ("
						+ queryParams + ")";
				Query query = manager.createNativeQuery(queryStr1);
				List<String> emails = query.getResultList();
				String[] emailsCC = emails.toArray(new String[0]);
				mailMessage.setCc(emailsCC);			
				mailMessage.setText("Generic Resources Allocated." + '\n' + '\n' + "This is Auto Generated Mail please ignore it");		
				response.setMessage("Generic Resources Allocated");		
				Set<RmgSpecific> rmgSpecificList = rmgRequest.getRmgSpecificList();		
				for(RmgGeneric gen : rmgRequest.getRmgGenericList())
				{
					setEmpIdgenericStatus(gen.getRmggenericresourcemap());			
				}				
			}
			mailMessage.setSubject("Resource Managment Group");
			javaMailSender.send(mailMessage);
		}
		return true;
	}
	

}
