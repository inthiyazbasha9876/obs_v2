package com.ojas.obs.constants;

public class RoleServiceConstants {

	public static final String SET = "/set";
	public static final String GET = "/get";
	public static final String ROLEMANAGEMENT = "/RoleManagement";
	public static final String GETALL = "getAll";
	public static final String GETBYID = "getById";
	public static final String SAVE = "save";
	public static final String UPDATE = "update";
	public static final String DELETE = "delete";
	public static final String SUCCESS = "success";
	public static final String FAILED = "failed";

	public static final String SAVEROLE = "insert into obs_rolemanagement(roleName,status) values(?,?)";
	public static final String UPDATEROLE = "update  obs_rolemanagement set  roleName = ?,status=? where id = ?";
	public static final String GETALLRECORDS = "select * from obs_rolemanagement";
	public static final String GETBYIDRECORDS = "select * from obs_rolemanagement where id = ?";
	public static final String DELETEROLE = "update ojas_obs.obs_rolemanagement  set status = false  where id = ?";
	
}
