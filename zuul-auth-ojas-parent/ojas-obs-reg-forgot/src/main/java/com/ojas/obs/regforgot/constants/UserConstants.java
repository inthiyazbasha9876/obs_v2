package com.ojas.obs.regforgot.constants;

/**
 * 
 * @author Manohar
 *
 */
public class UserConstants {

	public static final String SAVE = "save";
	public static final String UPDATE = "update";
	public static final String SENDMAIL = "sendMail";
	public static final String FAILED = "failed";
	public static final String EXCEPTION = "Exception caught!";
	public static final String MAILEXCEPTION = "MailException caught!";
	public static final String SQLEXCEPTION = "SQLException caught!";
	public static final String EMPTYDATA = "Oops! There is no request to reset password!";
	
	

	// ======EmployeeInfo Queries==============//

	public static final String SAVEEMPINFO = "insert into obs_employeeinfo  (firstname,middlename,lastname,status,dob,gender,title,employeeId,flag,createdOn,createdBy) values (?,?,?,?,?,?,?,?,?,?,?)";
	
	public static final String SAVEEMPLOGININFO =  "insert into obs_employee_login(employeeId,password,createdOn,createdBy) values(?,?,?,?)";
	
	public static final String SAVEEMPROLE =  "insert into user_role(employee_id, role_id) values(?, ?)";
	
	// ********** Forgot password QUERIES **********
	
		public static final String GETMAILID = "select officialEmail from obs_employeeinfo where employee_id = ";
		public static final String SAVEOTP = "replace into obs_forgot_password set employee_id=?, otp=?, exp_time=?";
		public static final String GETOTPDATA = "select otp, exp_time from obs_forgot_password where employee_id = ";
		public static final String UPDATEPASSWORD = "update obs_employee_login set password = ? where employeeId = ?";
		public static final String DELETEFORGOTDATA = "delete from obs_forgot_password where employee_id = ";
}
