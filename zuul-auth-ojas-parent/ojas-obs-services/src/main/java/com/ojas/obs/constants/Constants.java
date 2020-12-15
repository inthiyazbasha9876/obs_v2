package com.ojas.obs.constants;

public class Constants {
	//=====================================================================
	public static final String SERVICES = "/obs/services";
	public static final String GET = "/get"; 
	public static final String SET = "/set";
	

	// ==================================== services
	// ==================================================
	//public static final String GETSERVICES = "http://localhost:8080/ojas-obs-states-service/obs/states/service/get";
	///public static final String SETSERVICES  = "http://localhost:8080/ojas-obs-states-service/obs/states/service/set";
	public static final String FAILED = "failed";

	// ...........................UserConstants.................//

	public static final String REQUESTOBJECTNULL = "reqest object is null";
	//public static final String PASSPOROTBJECTNULL = "passport object is null";
	public static final String SESSIONIDNULL = "sessionid is null";
	public static final String TRANSACTIONTYPENULL = "transactiontype is null";
	public static final String SAVE = "save";
	public static final String UPDATE = "update";
	public static final String DELETE = "delete";
	public static final String GETALL = "getAll";
	public static final String GETBYID = "getById"; 
	public static final String INSERT_SERVICES = "insert into obs_services(serviceName,status) values(?,?)";
	public static final String UPDATE_SERVICES = "update obs_services set serviceName= ?,status=?  where id= ?";

	public static final String SELECT_SERVICES = "SELECT * FROM ojas_obs.obs_services";
	public static final String SELECT_SERVICES_BY_ID = "select * from obs_services where id= ?";
	public static final String SERVICESCOUNT = "select count(*) from obs_services";
	public static final String DELETE_SERVICES = "update obs_services set status= false  where id= ?";

}
