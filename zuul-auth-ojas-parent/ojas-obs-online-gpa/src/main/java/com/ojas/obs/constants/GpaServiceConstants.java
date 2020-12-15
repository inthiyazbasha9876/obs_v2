package com.ojas.obs.constants;
/**
 * 
 * @author pnaveen
 *
 */
public class GpaServiceConstants {

	public static final String FAILED = "failed";
	
	//==================================Request==========================================================
	//public static final String GPA = "obs/gpa";
	public static final String SET = "/set";
	public static final String GET = "/get";

	
	//==================================TransactionTypes======================================================
	public static final String SAVE = "save";
	public static final String GETALLRECORDS = "getGpaDetails";
	//public static final String DELETE = "delete";
	public static final String UPDATE = "update";
 
    public static final String GETALL = "getall";
    public static final String GETID = "getbyid";

	
//	===================================DatabaseQuries========================================================
	
	public static final String INSERTGPA = "INSERT INTO obs_gpaplan(gpaplantype,gpapremium,totalpremium)"
			+ "VALUES(?,?,?)";
	//public static final String DELETEGPA = "update obs_gpaplan set  updated_on = ? where gpaplanid = ?";
	public static final String GETGPAPLAN = "select * from obs_gpaplan";
	public static final String GETGPACOUNT = "select count(*) from obs_gpaplan";
	public static final String UPDATEGPAPLAN = "update obs_gpaplan set  gpaplantype =?,gpapremium=?,totalpremium=? where id =?";
	//public static final String GETBYGPAID      ="select * from obs_gpaplan where gpaplanid =";
	public static final String GETBYID =     "select * from obs_gpaplan where id=";

}

