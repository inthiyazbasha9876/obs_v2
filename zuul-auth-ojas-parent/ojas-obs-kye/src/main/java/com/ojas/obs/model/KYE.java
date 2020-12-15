package com.ojas.obs.model;

/**
 * 
 * @author anusha kota
 *
 */
public class KYE {

	private int id;
	private String kYE_Type;
	private String uan;
	private String kYE_address;
	private String passport_no;
	private String passport_date_of_Issue;
	private String passport_date_of_expiry;
	private String place_of_issue;
	private String passport_address;
	private String employee_Id;
	private boolean flag;
	private String created_by;
	private String updated_by;
	private String created_date;
	private String updated_date;
	private String passport_img;
	private String pan_img;
	private String aadhar_img;
	private String aadhar_address;
	private String pan_number;
	private String aadhar_number;
	private String passport_status;
	private String aadhar_status;
	private String pan_status;

	public String getPan_number() {
		return pan_number;
	}

	public void setPan_number(String pan_number) {
		this.pan_number = pan_number;
	}

	public String getAadhar_number() {
		return aadhar_number;
	}

	public void setAadhar_number(String aadhar_number) {
		this.aadhar_number = aadhar_number;
	}

	public String getAadhar_address() {
		return aadhar_address;
	}

	public void setAadhar_address(String aadhar_address) {
		this.aadhar_address = aadhar_address;
	}

	public String getPan_img() {
		return pan_img;
	}

	public void setPan_img(String pan_img) {
		this.pan_img = pan_img;
	}

	public String getAadhar_img() {
		return aadhar_img;
	}

	public void setAadhar_img(String aadhar_img) {
		this.aadhar_img = aadhar_img;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getkYE_Type() {
		return kYE_Type;
	}

	public void setkYE_Type(String kYE_Type) {
		this.kYE_Type = kYE_Type;
	}

	public String getUan() {
		return uan;
	}

	public void setUan(String uan) {
		this.uan = uan;
	}

	public String getkYE_address() {
		return kYE_address;
	}

	public void setkYE_address(String kYE_address) {
		this.kYE_address = kYE_address;
	}

	public String getPassport_no() {
		return passport_no;
	}

	public void setPassport_no(String passport_no) {
		this.passport_no = passport_no;
	}

	public String getPassport_date_of_Issue() {
		return passport_date_of_Issue;
	}

	public void setPassport_date_of_Issue(String passport_date_of_Issue) {
		this.passport_date_of_Issue = passport_date_of_Issue;
	}

	public String getPassport_date_of_expiry() {
		return passport_date_of_expiry;
	}

	public void setPassport_date_of_expiry(String passport_date_of_expiry) {
		this.passport_date_of_expiry = passport_date_of_expiry;
	}

	public String getPlace_of_issue() {
		return place_of_issue;
	}

	public void setPlace_of_issue(String place_of_issue) {
		this.place_of_issue = place_of_issue;
	}

	public String getPassport_address() {
		return passport_address;
	}

	public void setPassport_address(String passport_address) {
		this.passport_address = passport_address;
	}

	public String getEmployee_Id() {
		return employee_Id;
	}

	public void setEmployee_Id(String employee_Id) {
		this.employee_Id = employee_Id;
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

	public String getCreated_date() {
		return created_date;
	}

	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}

	public String getUpdated_date() {
		return updated_date;
	}

	public void setUpdated_date(String updated_date) {
		this.updated_date = updated_date;
	}

	public String getPassport_img() {
		return passport_img;
	}

	public void setPassport_img(String passport_img) {
		this.passport_img = passport_img;
	}
	

	public String getPassport_status() {
		return passport_status;
	}

	public void setPassport_status(String passport_status) {
		this.passport_status = passport_status;
	}

	public String getAadhar_status() {
		return aadhar_status;
	}

	public void setAadhar_status(String aadhar_status) {
		this.aadhar_status = aadhar_status;
	}

	public String getPan_status() {
		return pan_status;
	}

	public void setPan_status(String pan_status) {
		this.pan_status = pan_status;
	}

	@Override
	public String toString() {
		return "KYE [id=" + id + ", kYE_Type=" + kYE_Type + ", uan=" + uan + ", kYE_address=" + kYE_address
				+ ", passport_no=" + passport_no + ", passport_date_of_Issue=" + passport_date_of_Issue
				+ ", passport_date_of_expiry=" + passport_date_of_expiry + ", place_of_issue=" + place_of_issue
				+ ", passport_address=" + passport_address + ", employee_Id=" + employee_Id + ", flag=" + flag
				+ ", created_by=" + created_by + ", updated_by=" + updated_by + ", created_date=" + created_date
				+ ", updated_date=" + updated_date + ", passport_img=" + passport_img + ", pan_img=" + pan_img
				+ ", aadhar_img=" + aadhar_img + ", aadhar_address=" + aadhar_address + ", pan_number=" + pan_number
				+ ", aadhar_number=" + aadhar_number + ", passport_status=" + passport_status + ", aadhar_status="
				+ aadhar_status + ", pan_status=" + pan_status + "]";
	}

	
}
