package com.ojas.obs.emp_edu.utility;

public class Constants {
	private Constants() {
		
	}

	// =================================query constants===========================
	public static final String INSERT_STMT = "insert into ojas_obs.obs_education_details (employeeId,qualification,year_of_passing,percentage_marks,institution_name,flag,createdBy,updatedBy,createdDate,updatedDate,image,status) values (?,?,?,?,?,?,?,?,?,?,?,?)";
	public static final String DELETE_STMT = "update ojas_obs.obs_education_details set flag = 0 where id = ?;";
	public static final String UPDATE_STMT = "update ojas_obs.obs_education_details set employeeId = ?,flag = ?, updatedBy = ?, updatedDate = ?,image=?,status=? where id = ?";
	public static final String STATUS_UPDATE_STMT = "update ojas_obs.obs_education_details set employeeId = ?,flag = ?, updatedBy = ?, updatedDate = ?,status=?, comment=?  where id = ?";
	public static final String GETALL_STMT = "select * from ojas_obs.obs_education_details where flag = 1";
	
	public static final String GETMAILID = "select officialEmail from obs_employeeinfo where employee_id = ";
	
	public static final String GETBYIDQ="Select * from ojas_obs.obs_education_details where flag = 1 and id = ?";
	public static final String GETBYEMPID="Select * from ojas_obs.obs_education_details where flag = 1 and employeeId = ?";
	
	// =================================url constants===========================
		public static final String SET = "/set";
	public static final String GET = "/get";
	// =================================user constants===========================
	
	public static final String REQUEST_NULL = "Request Object is Empty";
	public static final String OBJECT_NULL = "EducationDetails Object is Empty";
	public static final String ID_NULL = "id is empty please provide the id";
	public static final String PAGE_NO_NULL = "page no is not defined";
	public static final String PAGE_SIZE_NULL = "page size is not defined";
	public static final String LIST_OBTAINED = "you got a list";
	public static final String PENDING = "pending";
	public static final String PENDING_MESSAGE = "Your documents has been uploaded successfully";
	public static final String ACCEPTED_MESSAGE = "Your documents has been accepted";
	public static final String DECLINE_MESSAGE = "Your documents has been declined";
	public static final String SAVE_SUCCESS_MESSAGE = "Employee Education Details have been saved successfully";
	public static final String UPDATE_FAILED_MESSAGE = "Employee Education Details has not updated";
	
	// =================================Transaction constants===========================
	public static final String TRANSACTIONTYPE_NULL = "transaction types is not defined";
	public static final String VALID_TRANSACTIONTYPE = "transactiontype is not valid";
	public static final String SAVE = "save";
	public static final String UPDATE = "update";
	public static final String STATUS_UPDATE = "statusupdate";
	public static final String DELETE = "delete";
	public static final String GETALL = "getAll";
	public static final String GETBYID = "getById";
	public static final String SUCCESS = "success";
	// =================================Exception constants===========================
	public static final String SQL_EXCEPTION = "sql exception occured";
	public static final String EXCEPTION = "exception occured";
}
