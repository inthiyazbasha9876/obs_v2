package com.ojas.obs.constants;

public class Constants {
	private Constants() {
	}

	// =====================================================================
	public static final String STATES = "/obs/states";
	public static final String GET = "/get"; /// states/service/get
	public static final String SET = "/set";

	// ==================================== states
	// ==================================================
	public static final String GETSTATES = "http://localhost:8080/ojas-obs-states-service/obs/states/service/get";
	public static final String SETSTATES = "http://localhost:8080/ojas-obs-states-service/obs/states/service/set";
	public static final String FAILED = "failed";

	// ...........................UserConstants for Passport.................//

	public static final String REQUESTOBJECTNULL = "reqest object is null";
	public static final String PASSPOROTBJECTNULL = "passport object is null";
	public static final String SESSIONIDNULL = "sessionid is null";
	public static final String TRANSACTIONTYPENULL = "transactiontype is null";
	public static final String SAVE = "save";
	public static final String UPDATE = "update";
	public static final String DELETE = "delete";
	public static final String GETALL = "getAll";
	public static final String GETBYID = "getById";
	public static final String INSERT_STATES = "insert into obs_states(stateName,status) values(?,?)";
	public static final String UPDATE_STATES = "update obs_states set stateName= ?  where id= ?";

	public static final String SELECT_STATES = "SELECT * FROM ojas_obs.obs_states;";
	public static final String SELECT_STATES_BY_ID = "select * from obs_states where id= ?";
	public static final String STATESCOUNT = "select count(*) from obs_states";
	public static final String DELETE_STATES = "update obs_states set status= false  where id= ?";

}
