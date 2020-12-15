package com.ojas.obs.constants;

public class UserConstants {
	private UserConstants() {
	}

	public static final String SAVESKIL = "insert into obs_skill(skill_name,status) values(?,?)";
	public static final String GETALL = "select * from obs_skill";
	public static final String GETCOUNT = "select count(*) from obs_skill";
	public static final String UPDATESKIL = "update obs_skill set skill_name = ? where id = ?";
	public static final String GETBYID = "select * from obs_skill where id = ?";
	public static final String DELETESKIL = "Update obs_skill set status = false where id = ?";

}
