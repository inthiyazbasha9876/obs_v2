package com.ojas.obs.model;

import java.sql.Timestamp;

/**
 * 
 * @author akrishna
 *
 */
public class BankDetails {

	private Integer id;
	private String bank_account_no;
	private String bank_name;
	private String bank_city;
	private String bank_branch;
	private String bank_ifsc_code;
	private String bank_account_status;
	private String employee_id;
	private boolean is_active;
	private String updated_by;
	private String created_by;
	private Timestamp created_date;
	private Timestamp updated_date;
	private boolean flag;

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBank_account_no() {
		return bank_account_no;
	}

	public void setBank_account_no(String bank_account_no) {
		this.bank_account_no = bank_account_no;
	}

	public String getBank_name() {
		return bank_name;
	}

	public void setBank_name(String bank_name) {
		this.bank_name = bank_name;
	}

	public String getBank_city() {
		return bank_city;
	}

	public void setBank_city(String bank_city) {
		this.bank_city = bank_city;
	}

	public String getBank_branch() {
		return bank_branch;
	}

	public void setBank_branch(String bank_branch) {
		this.bank_branch = bank_branch;
	}

	public String getBank_ifsc_code() {
		return bank_ifsc_code;
	}

	public void setBank_ifsc_code(String bank_ifsc_code) {
		this.bank_ifsc_code = bank_ifsc_code;
	}

	public String getBank_account_status() {
		return bank_account_status;
	}

	public void setBank_account_status(String bank_account_status) {
		this.bank_account_status = bank_account_status;
	}

	public String getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(String employee_id) {
		this.employee_id = employee_id;
	}

	public boolean isIs_active() {
		return is_active;
	}

	public void setIs_active(boolean is_active) {
		this.is_active = is_active;
	}

	public String getUpdated_by() {
		return updated_by;
	}

	public void setUpdated_by(String updated_by) {
		this.updated_by = updated_by;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
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

	@Override
	public String toString() {
		return "BankDetails [id=" + id + ", bank_account_no=" + bank_account_no + ", bank_name=" + bank_name
				+ ", bank_city=" + bank_city + ", bank_branch=" + bank_branch + ", bank_ifsc_code=" + bank_ifsc_code
				+ ", bank_account_status=" + bank_account_status + ", employee_id=" + employee_id + ", is_active="
				+ is_active + ", updated_by=" + updated_by + ", created_by=" + created_by + ", created_date="
				+ created_date + ", updated_date=" + updated_date + ", flag=" + flag + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bank_account_no == null) ? 0 : bank_account_no.hashCode());
		result = prime * result + ((bank_account_status == null) ? 0 : bank_account_status.hashCode());
		result = prime * result + ((bank_branch == null) ? 0 : bank_branch.hashCode());
		result = prime * result + ((bank_city == null) ? 0 : bank_city.hashCode());
		result = prime * result + ((bank_ifsc_code == null) ? 0 : bank_ifsc_code.hashCode());
		result = prime * result + ((bank_name == null) ? 0 : bank_name.hashCode());
		result = prime * result + ((created_by == null) ? 0 : created_by.hashCode());
		result = prime * result + ((created_date == null) ? 0 : created_date.hashCode());
		result = prime * result + ((employee_id == null) ? 0 : employee_id.hashCode());
		result = prime * result + (flag ? 1231 : 1237);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (is_active ? 1231 : 1237);
		result = prime * result + ((updated_by == null) ? 0 : updated_by.hashCode());
		result = prime * result + ((updated_date == null) ? 0 : updated_date.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BankDetails other = (BankDetails) obj;
		if (bank_account_no == null) {
			if (other.bank_account_no != null)
				return false;
		} else if (!bank_account_no.equals(other.bank_account_no)) {
			return false;}
		if (bank_account_status == null) {
			if (other.bank_account_status != null)
				return false;
		} else if (!bank_account_status.equals(other.bank_account_status)) {
			return false;}
		if (bank_branch == null) {
			if (other.bank_branch != null)
				return false;
		} else if (!bank_branch.equals(other.bank_branch)) {
			return false;}
		if (bank_city == null) {
			if (other.bank_city != null)
				return false;
		} else if (!bank_city.equals(other.bank_city)) {
			return false;}
		if (bank_ifsc_code == null) {
			if (other.bank_ifsc_code != null)
				return false;
		} else if (!bank_ifsc_code.equals(other.bank_ifsc_code)) {
			return false;}
		if (bank_name == null) {
			if (other.bank_name != null)
				return false;
		} else if (!bank_name.equals(other.bank_name)) {
			return false;}
		if (created_by == null) {
			if (other.created_by != null)
				return false;
		} else if (!created_by.equals(other.created_by)) {
			return false;}
		if (created_date == null) {
			if (other.created_date != null)
				return false;
		} else if (!created_date.equals(other.created_date)) {
			return false;}
		if (employee_id == null) {
			if (other.employee_id != null)
				return false;
		} else if (!employee_id.equals(other.employee_id)) {
			return false;}
		if (flag != other.flag)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id)) {
			return false;}
		if (is_active != other.is_active)
			return false;
		if (updated_by == null) {
			if (other.updated_by != null)
				return false;
		} else if (!updated_by.equals(other.updated_by)) {
			return false;}
		if (updated_date == null) {
			if (other.updated_date != null)
				return false;
		} else if (!updated_date.equals(other.updated_date)) {
			return false;}
		return true;
	}

	
}
