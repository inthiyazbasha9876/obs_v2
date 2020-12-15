package com.ojas.obs.constants;

public class Constants {
	private Constants() {
	}

	// =====================================================================
	public static final String GENDERS = "/obs/genders";
	public static final String GET = "/get";
	public static final String SET = "/set";
	// com.ojas.obs.constants.Constants.INSERT_GENDER

	// ==================================== Genders
	// ==================================================

	public static final String FAILED = "failed";

	public static final String REQUESTOBJECTNULL = "reqest object is null";
	public static final String SESSIONIDNULL = "sessionid is null";
	public static final String TRANSACTIONTYPENULL = "transactiontype is null";
	public static final String SAVE = "save";
	public static final String UPDATE = "update";
	public static final String DELETE = "delete";
	public static final String GETALL = "getAll";
	public static final String DELETE_GENDER = "update obs_genders set flag=0 where id = ?";
	public static final String INSERT_GENDER = "insert into obs_genders(gender) values(?)";
	public static final String UPDATE_GENDER = "update obs_genders set gender= ? where id= ?";
	public static final String SELECT_GENDER = "select * from obs_genders";
	public static final String GENDERCOUNT = "select count(*) from obs_genders";
	public static final String GENDER_BY_ID = "select * from obs_genders where id= ?";
}
