package com.ojas.obs.model;

/**
 * 
 * @author mpraneethguptha
 *
 */
public class EmployeeEducation {

	private Integer id;
	private String educationType;
	private boolean status;

	public String getEducationType() {
		return educationType;
	}

	public void setEducationType(String educationType) {
		this.educationType = educationType;
	}

	public Integer getId() {
		return id;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((educationType == null) ? 0 : educationType.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + (status ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		Boolean b =false;
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmployeeEducation other = (EmployeeEducation) obj;
		if (educationType == null) {
			if (other.educationType != null)
				return false;
		} else if (!educationType.equals(other.educationType)) {
			return false;}
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id)) {
			return false;}
		if (status != other.status) {
			b=true;
		}
			return b;
	}

	@Override
	public String toString() {
		return "EmployeeEducation [id=" + id + ", educationType=" + educationType + ", status=" + status + "]";
	}

}
