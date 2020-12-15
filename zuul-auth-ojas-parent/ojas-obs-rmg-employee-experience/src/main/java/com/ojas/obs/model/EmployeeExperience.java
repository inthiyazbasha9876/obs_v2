package com.ojas.obs.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Emp_Experience")
public class EmployeeExperience {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Integer empExperienceId;
	
	@Column(unique = true)
	private Double empExperience;
	
	@Column(columnDefinition = "tinyint default true")
	private Boolean status;

	public Integer getEmpExperienceId() {
		return empExperienceId;
	}

	public void setEmpExperienceId(Integer empExperienceId) {
		this.empExperienceId = empExperienceId;
	}

	public Double getEmpExperience() {
		return empExperience;
	}

	public void setEmpExperience(Double empExperience) {
		this.empExperience = empExperience;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "EmployeeExperience [empExperienceId=" + empExperienceId + ", empExperience=" + empExperience
				+ ", status=" + status + "]";
	}

}