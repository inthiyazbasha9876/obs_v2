package com.ojas.obs.constants;

public class SubBusinessUnitConstants {
	private SubBusinessUnitConstants() {}
	
	public static final String SUBBUSINESSUNIT = "/subbusinessunit";
	public static final String SET = "/set";
	public static final String GET = "/get";
	public static final String GETALL = "getAll";
	public static final String GETBYID = "getById";
	public static final String GETHEADS = "getHeads";
	public static final String SAVE = "save";
	public static final String UPDATE = "update";
	public static final String DELETE = "delete";
	public static final String FAILED = "falied";
	public static final String SUCCESS = "success";
	public static final String DATAINVALID = "data is  invalid";
	public static final String INSIDECATCHBLOCK = "inside SubBusinessUnitFacade catch block.****";
	
	
	public static final String GETSBUHEADS = "select employee_Id from ojas_obs.user_role where role_id = (select role_id from ojas_obs.app_role where role_name='ROLE_SBUHEAD')";
	public static final String SAVEBUSINESSUNIT = "Insert into obs_subbusinessunit(name,businessUnitId, sbuHead, status) VALUES (?,?,?,?)";
	public static final String UPDATEBUSINESSUNITID ="update obs_subbusinessunit set name = ?, businessUnitId = ?, sbuHead=?  where id = ?";
	public static final String DELETEBUSINESSUNITID = "update obs_subbusinessunit set status=false where id = ? ";
	
	public static final String GETALLSUBBUSINESSUNITS = "select * from obs_subbusinessunit";
	public static final String GETBYIDBUSINESSUNITS = "select * from obs_subbusinessunit where id = ?";
	

}