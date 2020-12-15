package com.ojas.obs.constants;

public class UserConstants {
	private UserConstants() {
		
	}
	public static final String SAVE = "save";

	public static final String UPDATE = "update";

	public static final String DELETE = "delete";

	public static final String GETALL = "getAll";

	public static final String GET = "getById";
	
	public static final String EDUCATIONTYPE="/obseducationtype";
	

	public static final String INSERTEMPLOYEEEDUCATIONINFOSTMT = "insert into  ojas_obs.obs_educationtype (educationType,status) VALUES (?,?)";


	public static final String UPDATESTMT = "UPDATE  ojas_obs.obs_educationtype set educationType=? WHERE id = ? ";

	
	public static final String DELETEEDUCATION = "update ojas_obs.obs_educationtype  set status = false where id = ?";

	public static final String TOTALRECORDS = "select * from ojas_obs.obs_educationtype";

	public static final String COUNTRECORDS = "select count(*) from ojas_obs.obs_educationtype";
	
	public static final String GETBYID="select * from ojas_obs.obs_educationtype where id=?";


}
