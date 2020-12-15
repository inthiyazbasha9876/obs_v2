package com.ojas.obs.constants;

/**
 * 
 * @author sdileep
 *
 */
public class UserConstants {
	private UserConstants() {
		throw new IllegalStateException("Utility class");
	}

	public static final String SAVE = "save";

	public static final String UPDATE = "update";
	public static final String PICUPDATE = "picUpdate";
	public static final String UPDATESTATUS = "updateStatus";
	public static final String DELETE = "delete";
	public static final String GETEMAILID = "getEmailId";

	public static final String FAILED = "failed";
	public static final String SQLEXCEPTION = "SQLException caught";
	public static final String EXCEPTION = "Exception caught";
	public static final String INVALIDDATA = "Data is not valid";
	public static final String SUCCESS = "Request processed successfully!";
	
	
	

	// ======EmployeeInfo Queries==============//
	public static final String SAVERMGEMPINFO = "insert into obs_rmg.rmg_employee_list (employee_id, full_name, flag, gender) values (?,?,?,?)";

	public static final String SAVEEMPINFO = "insert into obs_employeeinfo  (firstname,middlename,lastname,status,dob,gender,title,reportingManager,employee_id,flag,createdOn,createdBy,email,officialEmail,personalMobileNo,image) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	public static final String SAVEEMPLOGININFO =  "insert into obs_employee_login(employeeId,password,createdOn,createdBy) values(?,?,?,?)";
	
	public static final String SAVEEMPROLE =  "insert into user_role(employee_id,role_id) values(?,?)";
	
	public static final String ADDEMP = "Insert into obs_lms.leave_balance(emp_id) values(?)";
			
	public static final String DELETEEMPROLE ="delete from user_role where employee_id =?";
		
	public static final String UPDATEEMPINFO = "update obs_employeeinfo set firstname =?,middlename=?,lastname=? ,status =?,dob=?,gender=?, title=?, reportingManager=?,employee_id=?,email=?,officialEmail=?,personalMobileNo=?,updatedBy=?,updatedOn =? where id =?";
	public static final String UPLOADPIC = "update obs_employeeinfo set image = ? where employee_id = ?";
	public static final String STATUSUPDATE = "update obs_employeeinfo set status = ?, flag = ? where employee_id = ?";
	public static final String DELETEEMPINFO = "update ojas_obs.obs_employeeinfo set flag = ?,updatedOn = ? where id =?";
	public static final String GETEMPDETAILS = "select * from obs_employeeinfo where flag = true ";
	public static final String GETEMPINFOS = "select id,firstname,middlename,lastname,status,statusDate,dob,gender,title,reportingManager,employee_id,email,officialEmail,personalMobileNo from obs_employeeinfo where flag = true ";
	public static final String GETEMPCOUNT = "select count(*) from obs_employeeinfo";
	
	public static final String GETEMPBYID ="select * from obs_employeeinfo where flag = '1' and id= ";
	
	public static final String ROLEEMPBYID ="select role_id from user_role where  employee_id= ";
			
	public static final String GETEMPBYEMPID ="select * from obs_employeeinfo where flag = true and employee_id= ";
	public static final String GETALLREPORTIES ="select * from obs_employeeinfo where flag = true and reportingManager= ";
	
	public static final String GETROLEBYEMPID ="SELECT a.role_name,a.role_id, e.employeeId,e.firstname,e.middlename,e.lastname FROM obs_employeeinfo e, user_role u , app_role a where e.employeeId =u.employeeId and u.role_id =a.role_id and e.employeeId = ? ";
	
	public static final String GETMAILID = "select officialEmail from obs_employeeinfo where employee_id in(?, ?)";
	public static final String GETMNGRMAIL = "select officialEmail from obs_employeeinfo where employee_id="; 
}
