package com.ojas.obs.model;

import java.sql.Timestamp;
/**
 * h
 * @author uyashwanth
 *
 */
public class EmployeeBUDetails {

	private String employeeId;
	private Integer sbu;
	private boolean flag;
	private Timestamp createddate;
	private Timestamp updateddate;
	private String createdby;
	private String updatedby;
	private Integer id;
	private String status;
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public Integer getSbu() {
		return sbu;
	}
	public void setSbu(Integer sbu) {
		this.sbu = sbu;
	}
	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public Timestamp getCreateddate() {
		return createddate;
	}
	public void setCreateddate(Timestamp createddate) {
		this.createddate = createddate;
	}
	public Timestamp getUpdateddate() {
		return updateddate;
	}
	public void setUpdateddate(Timestamp updateddate) {
		this.updateddate = updateddate;
	}
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	public String getUpdatedby() {
		return updatedby;
	}
	public void setUpdatedby(String updatedby) {
		this.updatedby = updatedby;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "EmployeeBUDetails [employeeId=" + employeeId + ", sbu=" + sbu + ", flag=" + flag + ", createddate="
				+ createddate + ", updateddate=" + updateddate + ", createdby=" + createdby + ", updatedby=" + updatedby
				+ ", id=" + id + ", status=" + status + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((createdby == null) ? 0 : createdby.hashCode());
		result = prime * result + ((createddate == null) ? 0 : createddate.hashCode());
		result = prime * result + ((employeeId == null) ? 0 : employeeId.hashCode());
		result = prime * result + (flag ? 1231 : 1237);
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((sbu == null) ? 0 : sbu.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((updatedby == null) ? 0 : updatedby.hashCode());
		result = prime * result + ((updateddate == null) ? 0 : updateddate.hashCode());
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
		EmployeeBUDetails other = (EmployeeBUDetails) obj;
		if (createdby == null) {
			if (other.createdby != null)
				return false;
		} else if (!createdby.equals(other.createdby))
			return false;
		if (createddate == null) {
			if (other.createddate != null)
				return false;
		} else if (!createddate.equals(other.createddate))
			return false;
		if (employeeId == null) {
			if (other.employeeId != null)
				return false;
		} else if (!employeeId.equals(other.employeeId))
			return false;
		if (flag != other.flag)
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (sbu == null) {
			if (other.sbu != null)
				return false;
		} else if (!sbu.equals(other.sbu))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (updatedby == null) {
			if (other.updatedby != null)
				return false;
		} else if (!updatedby.equals(other.updatedby))
			return false;
		if (updateddate == null) {
			if (other.updateddate != null)
				return false;
		} else if (!updateddate.equals(other.updateddate))
			return false;
		return true;
	}



	

}
