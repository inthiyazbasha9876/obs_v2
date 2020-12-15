package com.obs.rmg.rmgconstants;

public class RmgUtilConstants {
	public static final String SAVE = "save";
	public static final String UPDATE = "update";
	public static final String DELETE = "delete";
	public static final String RECORDUPDATE = "recordUpdate";
	public static final String GETBYID = "getById";
	public static final String GETBYPROJECTID = "getbyprojectid";
	public static final String GETBYEMPID = "getByEmpId";
	public static final String GETALL = "getAll";
	public static final String ACCEPTED_MESSAGE = "Accepted Successfully";
	public static final String DECLINE_MESSAGE = "Rejected ";
	public static final String ACCEPTED = "approved";
	public static final String REJECTED = "rejected";
	public static final String PENDING = "pending";
	public static final String PROPOSAL = "proposed";
	public static final String FIELDNULL = "field is null";
	public static final String FAILED = "failed";
	public static final String GETEMPIDBYSKILLS = "getskills";
	public static final String GETEMPIDBYSTATUS = "getempbytstatus";
	public static final String GETPROJECTSBYEMPID = "getprojects";
	public static final String EMPIDLIST_BY_SKILLID = "select employee_id from  ojas_obs_psa.obs_employeeskilldetails where  skill_id=UI";
	
	public static final String SPECIFIC = "specific";
	public static final String GENERIC = "generic";
	
	public static final String RELEASE = "release";
	public static final String GETRESOURCESBYPROJECT = "getresourcebyproject";
	public static final String GETDEPLOYEDRESOURCESBYSKILLS = "getdeployedresourcesbyskills";
	public static final String UPDATEBOOKING = "updatebooking";
	public static final String UPDATETASKS = "updatetasks";
	public static final String SAVETASKS = "savetasks";
	public static final String GETALLWORKEDHOURS = "getallworkedhours";
	public static final String GETRESOURCEPROJECTTASKS = "getresourceprojecttasks";
	public static final String GETALLTASKS = "getalltasks";
	public static final String  GETPROJECTENDDATERELEASE="update obs_rmg.rmg_specific set flag=0 where end_date = current_date()";
	public static final String GETEMAILIDS="select emp.officialEmail from ojas_obs.obs_employeeinfo emp, obs_rmg.rmg_specific sp where sp.end_date = date_add(current_date(), interval 7 day) and sp.employee_id = emp.employee_id";

}
