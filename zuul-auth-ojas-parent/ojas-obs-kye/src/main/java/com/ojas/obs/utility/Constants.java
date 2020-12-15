package com.ojas.obs.utility;

public class Constants {
	
	//============================================URL constants=========================================================================
	public static final String SET = "/set";
	public static final String GET = "/get";	
	public static final String KYE = "/obs/kye";
	public static final String EMAIL = "/email";
	

	//=============================================TransactionTypes======================================================================
	public static final String SAVE = "save";	
	public static final String UPDATE_AADHAR_IMG = "updateAadhar";	
	public static final String UPDATE_PAN_IMG = "updatePan";
	public static final String UPDATE_PASSPORT_IMG = "updatePassport";
	
	public static final String UPDATE_AADHAR_STATUS = "updateAadharStatus";	
	public static final String UPDATE_PAN_STATUS = "updatePanStatus";
	public static final String UPDATE_PASSPORT_STATUS = "updatePassportStatus";
	
	
	
	public static final String DELETE = "delete";	
	public static final String GETALL = "getAll";
	public static final String GETBYID = "getById";
	public static final String ACCEPT = "accept";	
	public static final String DECLINE = "decline";
	public static final String PENDING= "pending";
	
	
	
	public static final String SENDMAIL = "sendMail";
	public static final String FAILED = "failed";
	public static final String FORGOT = "forgot";
	public static final String EXCEPTION = "Exception caught!";
	public static final String MAILEXCEPTION = "MailException caught!";
	public static final String SQLEXCEPTION = "SQLException caught!";
	public static final String EMPTYDATA = "Invalid employee id!";
	public static final String MESSAGE = "KYE Document Status";
	public static final String SAVE_SUCCESS_MESSAGE="Your Documents saved successfully";
	public static final String ACCEPTED_MESSAGE="Updated successfully";
	
	//=============================================sql quries ==========================================================================
	public static final String SAVE_KYE = "Insert into obs_kye(KYE_Type ,uan ,KYE_address,passport_no,Passport_date_of_Issue,Passport_date_of_expiry,place_of_issue ,Passport_address ,employee_Id, flag, created_by, created_date,passport_img,pan_img,aadhar_img,aadhar_address,pan_number,aadhar_number,passport_status,aadhar_status,pan_status) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	public static final String UPDATE_AADHAR_KYE = "Update obs_kye set  aadhar_status=?,aadhar_img = ?,updated_by = ?, updated_date = ?, employee_id = ?  where id = ?";
	public static final String UPDATE_PAN_KYE = "Update obs_kye set  pan_status=?,pan_img = ?,updated_by = ?, updated_date = ?, employee_id = ?  where  id = ?";
	public static final String UPDATE_PASSPORT_KYE ="Update obs_kye set passport_status = ?,passport_img = ? , updated_by = ?, updated_date = ?, employee_id = ?  where id = ?";
	public static final String DELETE_KYE = "Update obs_kye set flag = ? where id = ?";
	public static final String GETALL_KYE = "Select * from obs_kye";
	public static final String GETBYEMPID_KYE="select * from obs_kye where employee_id = ?";
	public static final String GETBYID_KYE="select * from obs_kye where id = ? ";
	public static final String GETALL_KYE_COUNT = "Select count(*) from obs_kye ";
	public static final String GETMAILID = "select officialEmail from obs_employeeinfo where employee_id = ";
	public static final String UPDATE_AADHAR_KYE_STATUS = "Update obs_kye set  aadhar_status = ?, employee_id = ?  where id = ?";
	public static final String UPDATE_PAN_KYE_STATUS = "Update obs_kye set pan_status = ?, employee_id = ?  where  id = ?";
	public static final String UPDATE_PASSPORT_KYE_STATUS = "Update obs_kye set passport_status = ?, employee_id = ?  where  id = ?";
	
}

