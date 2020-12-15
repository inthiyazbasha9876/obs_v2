package com.ojas.obs.model;

import java.sql.Timestamp;

public class EmployeeSkillInfo {

	private Integer id;
	private String skill_id;
	private Integer level_id;
	private String employee_id;
	private String created_by;
	private String update_by;
	private Timestamp created_date;
	private Timestamp updated_date;
	private boolean flag;

	public Integer getId() {
		return id;
	}

	@Override
	public String toString() {
		return "EmployeeSkillInfo [id=" + id + ", skill_id=" + skill_id + ", level_id=" + level_id + ", employee_id="
				+ employee_id + ", created_by=" + created_by + ", update_by=" + update_by + ", created_date="
				+ created_date + ", updated_date=" + updated_date + ", flag=" + flag + "]";
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSkill_id() {
		return skill_id;
	}

	public void setSkill_id(String skill_id) {
		this.skill_id = skill_id;
	}

	public Integer getLevel_id() {
		return level_id;
	}

	public void setLevel_id(Integer level_id) {
		this.level_id = level_id;
	}

	public String getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public String getUpdate_by() {
		return update_by;
	}

	public void setUpdate_by(String update_by) {
		this.update_by = update_by;
	}

	public Timestamp getCreated_date() {
		return created_date;
	}

	public void setCreated_date(Timestamp created_date) {
		this.created_date = created_date;
	}

	public Timestamp getUpdated_date() {
		return updated_date;
	}

	public void setUpdated_date(Timestamp updated_date) {
		this.updated_date = updated_date;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

}
