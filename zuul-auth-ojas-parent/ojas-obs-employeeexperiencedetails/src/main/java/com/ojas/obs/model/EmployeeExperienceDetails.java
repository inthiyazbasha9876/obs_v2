package com.ojas.obs.model;

import java.sql.Date;
import java.sql.Timestamp;

public class EmployeeExperienceDetails {
	private Integer id;
	private String employee_id;
	private String company_name;
	private Date joining_date;
	private Date exit_date;
	private Double salary;
	private String location;
	private String is_current_company;
	private String reference_1_name;
	private String reference_1_contact;
	private String reference_2_name;
	private String reference_2_contact;
	private boolean flag;
	private String created_by;
	private String updated_by;
	private Timestamp created_date;
	private Timestamp updated_date;
	private String image;
	private double experience;
	private Boolean status;
	private String documentsstatus;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEmployee_id() {
		return employee_id;
	}
	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public Date getJoining_date() {
		return joining_date;
	}
	public void setJoining_date(Date joining_date) {
		this.joining_date = joining_date;
	}
	public Date getExit_date() {
		return exit_date;
	}
	public void setExit_date(Date exit_date) {
		this.exit_date = exit_date;
	}
	public Double getSalary() {
		return salary;
	}
	public void setSalary(Double salary) {
		this.salary = salary;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getIs_current_company() {
		return is_current_company;
	}
	public void setIs_current_company(String is_current_company) {
		this.is_current_company = is_current_company;
	}
	public String getReference_1_name() {
		return reference_1_name;
	}
	public void setReference_1_name(String reference_1_name) {
		this.reference_1_name = reference_1_name;
	}
	public String getReference_1_contact() {
		return reference_1_contact;
	}
	public void setReference_1_contact(String reference_1_contact) {
		this.reference_1_contact = reference_1_contact;
	}
	public String getReference_2_name() {
		return reference_2_name;
	}
	public void setReference_2_name(String reference_2_name) {
		this.reference_2_name = reference_2_name;
	}
	public String getReference_2_contact() {
		return reference_2_contact;
	}
	public void setReference_2_contact(String reference_2_contact) {
		this.reference_2_contact = reference_2_contact;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public String getCreated_by() {
		return created_by;
	}
	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}
	public String getUpdated_by() {
		return updated_by;
	}
	public void setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
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
	
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public double getExperience() {
		return experience;
	}
	public void setExperience(double experience) {
		this.experience = experience;
	}
	public Boolean getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public String getDocumentsstatus() {
		return documentsstatus;
	}
	public void setDocumentsstatus(String documentsstatus) {
		this.documentsstatus = documentsstatus;
	}
	
	
}
