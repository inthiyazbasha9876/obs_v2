package com.obs.rmg.rmgmodel;



public class ProjectList {
	
	private String empId;
	private String projectId;
	private String startDate;
	private String endDate;
	private Double hoursOfAllocation;
	private int bookingId;
	private int specificId;
	private String projectName;
	//private int genericId;
	

	public int getBookingId() {
		return bookingId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public int getSpecificId() {
		return specificId;
	}

	public void setSpecificId(int specificId) {
		this.specificId = specificId;
	}



	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
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

	public Double getHoursOfAllocation() {
		return hoursOfAllocation;
	}

	public void setHoursOfAllocation(Double hoursOfAllocation) {
		this.hoursOfAllocation = hoursOfAllocation;
	}


}
