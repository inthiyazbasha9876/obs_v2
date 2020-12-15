package com.ojas.obs.constants;

public class DependentDetailsContants {

	// ...........................Constants for DependentDetails.................//

	public static final String SAVE = "save";
	public static final String UPDATE = "update";
	public static final String DELETE = "delete";
	public static final String GETALL = "getAll";
	public static final String GETBYID = "getById";

	public static final String INSERTDEPENDENTDETAILS = "insert into obs_dependent_details (dependent_name, relation, date_of_birth, employee_id, created_by, created_date, updated_by, updated_date, flag) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
	public static final String UPDATEDEPENDENTDETAILS = "update obs_dependent_details set dependent_name=?, relation=?, date_of_birth=?, employee_id=?, updated_by=?, updated_date=? where id = ?";
	public static final String DELETEDEPENDENTDETAILS = "update obs_dependent_details set updated_by=?, updated_date=?, flag=? where id = ?";
	public static final String GETALLDEPENDENTDETAILS = "select * from obs_dependent_details where flag = '1'";
	public static final String GETDEPENDENTDETAILSBYID = "select * from obs_dependent_details where id = ?";
	public static final String GETID = "select * from obs_dependent_details where employee_id = ?";
	
}
