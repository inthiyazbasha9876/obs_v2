package com.ojas.obs.constants;

public class ResignationConstants {
	private ResignationConstants()
	{}

	
	public static final String SET = "/set";
	public static final String GET = "/get";
	
	public static final String RESIGNATION="obs/resignation";
	
	
	public static final String SAVE ="save";
	public static final String UPDATE ="update";
	public static final String UPDATESTATE = "updatestate";
	public static final String GETALL ="getall";
	public static final String GETBYID ="getbyid";
	public static final String INVALIDREQUEST ="Request is invalid";
	
	
	public static final String SAVERESIGNATION = "insert into obs_resignation(resignationType, resignationSubmittedOn, finalSettlementDate, leavingReason, leavingDate, employeeIsDeceased, dateOfDemise, emailId1,emailId2, emailId3, emailId4,employeeId,remarks,state) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	//public static final String UPDATERESIGNATION = "update obs_resignation set resignationType = ?, resignationSubmittedOn  = ?, finalSettlementDate = ?, leavingReason = ?, leavingDate = ?, employeeIsDeceased = ?, dateOfDemise = ?, emailId1 = ?,emailId2 = ?,emailId3 = ?,emailId4 = ?,employeeId= ?, remarks=?,state=? where id = ?";
	public static final String UPDATERESIGNATION = "update obs_resignation set resignationType = ?, resignationSubmittedOn  = ?, finalSettlementDate = ?, leavingDate = ?,employeeId= ?,state=? where id = ?";
	public static final String UPDATESTATERESIGNATION = "update obs_resignation set state=? where id = ?";
	public static final String GETALLRESIGNATION = "select * from obs_resignation";
	public static final String GETBY_IDRESIGNATION = " select * from obs_resignation where employeeId =";
	public static final String GETBY_EMPIDRESIGNATION = " select * from obs_resignation where (state="+"'"+ "applied" +"'" +"or state="+"'"+ "approved" +"'" +"or state="+"'"+ "declined" +"')"  + "and employeeId = ";
	public static final String GETBYROLEID="select * from user_role where role_id='2'";
	public static final String GETBYBASICINFO ="select reportingManager from obs_employeeinfo where employee_Id = ";
	
	public static final String GETMEILIDS = "select email from obs_employeeinfo where employee_Id in ";
	
	
	
	
	
}
