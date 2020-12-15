package com.ojas.obs.passport.utility;

public class Constants {
	//........................URL Constants Of Passports..............................//
	public static final String BASE = "/obs";
	public static final String PASSPORTSERVICE="/obs/passportService";
	public static final String PASSPORT = "/passport";
	public static final String GET = "/get";
	public static final String SET = "/set";
	
	// ...........................UserConstants for Passport.................//

		public static final String REQUESTOBJECTNULL = "request object is null";
		public static final String PASSPOROTBJECTNULL = "passport object is null";
		public static final String SESSIONIDNULL = "sessionid is null";
		public static final String TRANSACTIONTYPENULL = "transactiontype is null";
		public static final String PASSPOROTBJECTFIELDNULL = "passport object FIELD is null";
		
		public static final String SAVE = "save";
		public static final String UPDATE = "update";
		public static final String GETALL = "getAll";
		public static final String GETBYID = "getById";
		public static final String DELETE = "delete";

		// ...........................SQL QUERIES for Passport.................//
		public static final String INSERTPASSPORTSTMT = "insert into ojas_obs.obs_passport (centerName) values (?)";
		public static final String GETTOTALSTMT = "select * from ojas_obs.obs_passport";
		public static final String UPDATESTMT = "update ojas_obs.obs_passport set obs_passport.centerName = ?  where obs_passport.id = ?";
		public static final String COUNTSTMT = "select count(*) from ojas_obs.obs_passport";
		public static final String GETTOBYID = "select * from ojas_obs.obs_passport where id = ?";
		public static final String DELETESTMT = "update ojas_obs.obs_passport set obs_passport.status = 0  where obs_passport.id = ?";
		
}
