package com.obs.lms.constants;

public class Constants {

	public static final String SAVE = "save";
	public static final String UPDATE = "update";
	public static final String WITHDRAWN = "withdrawn";
	public static final String REJECTED = "Rejected";
	public static final String APPROVED = "approved";
	public static final String PENDING = "pending";
	
	public static final String DELETE = "delete";
	public static final String GETBYMANAGER = "getbymanager";
	public static final String GETALL = "getAll";
	public static final String GETBYID = "getById";
	public static final String UPDATEBYLEAVEBALID = "updateleavebal";
	public static final String GETFILE = "getFile";
	public static final String FAILED = "failed";
	public static final String SUCCESS = "success";
	public static final String REQUESTOBJECTNULL = "Request object is null";
	public static final String NULLOBJECT = "Object is null";
	public static final String SET = "/set";
	public static final String GET = "/get";
	public static final String UPDATESTATUS = "updatestatus";
	public static final String GETAllLEAVEINFO = "getallleaveinfo";
	public static final String GETALLLEAVEBAL = "getallleavebal";
	public static final String TRANSATIONTYPENULL = "tarnsactionType must not be null";
	public static final String IDNULL = "Id must not be null";
	public static final String NORECORDS = "no records found";
	public static final String GETEMPLYINFO = "SELECT employee_id FROM ojas_obs.obs_employmentdetails WHERE now() >= confirmationDate";
	public static final String GETEMPLYNAMEID = "SELECT CONCAT(obs_employeeinfo.firstname,' ',obs_employeeinfo.middlename,' ',obs_employeeinfo.lastname)  FROM ojas_obs.obs_employeeinfo where employee_id = 18165;";

	private Constants() {
		super();

	}

}
