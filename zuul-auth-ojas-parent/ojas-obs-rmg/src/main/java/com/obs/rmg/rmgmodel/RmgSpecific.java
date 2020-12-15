package com.obs.rmg.rmgmodel;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table
public class RmgSpecific {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int specificId;
	
	@Column
	private String startDate;
	
	@Column
	private String endDate;
	
	@Column
	private String employeeId;
	
	@Column
	private float billRate;
	
	@Column
	private String reason;
	
	@Column
	private String specificStatus;
	
	@Transient
	private String specificEmployeeStatus;
	
	@Column
	private String alternateemployeeId;
	
	@Column
	private Boolean flag;
	
	@Column
	private Double hoursOfAllocation;
	
	@Column
	@ElementCollection
	private List<String> weekOffDays;
	
	
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler","rmgspecific"})
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.ALL }, mappedBy = "rmgspecific")
    private Set<RMG> rmg = new HashSet<>();
	
	@OneToMany(mappedBy = "rmgspecific", cascade = CascadeType.ALL)
	@JsonIgnoreProperties("rmgspecific")
    private Set<ProjectTasks> projectTasks=new HashSet<ProjectTasks>();
	
	

	public int getSpecificId() {
		return specificId;
	}

	public void setSpecificId(int specificId) {
		this.specificId = specificId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}
  public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public float getBillRate() {
		return billRate;
	}
	public void setBillRate(float billRate) {
		this.billRate = billRate;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Set<RMG> getRmg() {
		return rmg;
	}
	public void setRmg(Set<RMG> rmg) {
		this.rmg = rmg;
	}

	public String getSpecificStatus() {
		return specificStatus;
	}

	public void setSpecificStatus(String specificStatus) {
		this.specificStatus = specificStatus;
	}

	public String getAlternateemployeeId() {
		return alternateemployeeId;
	}

	public void setAlternateemployeeId(String alternateemployeeId) {
		this.alternateemployeeId = alternateemployeeId;
	}

	public String getSpecificEmployeeStatus() {
		return specificEmployeeStatus;
	}

	public void setSpecificEmployeeStatus(String specificEmployeeStatus) {
		this.specificEmployeeStatus = specificEmployeeStatus;
	}

	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) {
		this.flag = flag;
	}

	public Double getHoursOfAllocation() {
		return hoursOfAllocation;
	}

	public void setHoursOfAllocation(Double hoursOfAllocation) {
		this.hoursOfAllocation = hoursOfAllocation;
	}

	public Set<ProjectTasks> getProjectTasks() {
		return projectTasks;
	}

	public void setProjectTasks(Set<ProjectTasks> projectTasks) {
		this.projectTasks = projectTasks;
	}

	public List<String> getWeekOffDays() {
		return weekOffDays;
	}

	public void setWeekOffDays(List<String> weekOffDays) {
		this.weekOffDays = weekOffDays;
	}


}
