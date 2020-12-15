package com.obs.rmg.rmgmodel;

public class EmployeeProjects
{
	private String projectId;
	private String employeeId;
	private String startDate;
	private String endDate;
	private float billRate;
	private Double hoursOfAllocation;
	private int specificId;
	private int bookingId;
	
	
	public int getSpecificId() {
		return specificId;
	}
	public void setSpecificId(int specificId) {
		this.specificId = specificId;
	}
	public int getBookingId() {
		return bookingId;
	}
	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
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
	public float getBillRate() {
		return billRate;
	}
	public void setBillRate(float billRate) {
		this.billRate = billRate;
	}
	
	public Double getHoursOfAllocation() {
		return hoursOfAllocation;
	}
	public void setHoursOfAllocation(Double hoursOfAllocation) {
		this.hoursOfAllocation = hoursOfAllocation;
	}
	
	
}
