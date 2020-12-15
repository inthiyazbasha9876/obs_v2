package com.ojas.obs.constants;

public class UserConstants {
	
	private UserConstants() {
	}

	public static final String SAVEEMPLOYEESKILLINFOSTMT = "insert into  obs_employeeskilldetails(skill_id ,level_id,employee_id ,created_by ,flag,created_date ) values(?,?,?,?,?,?)";
	public static final String GETALL = "select * from  obs_employeeskilldetails";
	public static final String GETBYID = "select * from  obs_employeeskilldetails where id =?  ";
	public static final String GETCOUNT = "select count(*) from  obs_employeeskilldetails";
	public static final String UPDATEBYID = "update obs_employeeskilldetails set employee_id = ? ,skill_id=?, level_id =?, update_by=? ,updated_date =? where id = ? ";
	public static final String GETBYEMPID = "select * from  obs_employeeskilldetails where employee_id = ? ";
}
