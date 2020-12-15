package com.obs.rmg.rmgmodel;

public class ResourceProjectTasks 
{
   private String projectId;
   private String EmployeeId;
   private int bookingId;
   private int specificId;
   private String taskName;
   private String taskStartDate;
   private String taskEndDate;
   private String taskStatus;
   private Double hoursPerDay;
   private String fullName;
   
public String getFullName() {
	return fullName;
}
public void setFullName(String fullName) {
	this.fullName = fullName;
}
public String getProjectId() {
	return projectId;
}
public void setProjectId(String projectId) {
	this.projectId = projectId;
}
public String getEmployeeId() {
	return EmployeeId;
}
public void setEmployeeId(String employeeId) {
	EmployeeId = employeeId;
}
public int getBookingId() {
	return bookingId;
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
public String getTaskName() {
	return taskName;
}
public void setTaskName(String taskName) {
	this.taskName = taskName;
}
public String getTaskStartDate() {
	return taskStartDate;
}
public void setTaskStartDate(String taskStartDate) {
	this.taskStartDate = taskStartDate;
}
public String getTaskEndDate() {
	return taskEndDate;
}
public void setTaskEndDate(String taskEndDate) {
	this.taskEndDate = taskEndDate;
}
public String getTaskStatus() {
	return taskStatus;
}
public void setTaskStatus(String taskStatus) {
	this.taskStatus = taskStatus;
}
public Double getHoursPerDay() {
	return hoursPerDay;
}
public void setHoursPerDay(Double hoursPerDay) {
	this.hoursPerDay = hoursPerDay;
}
   
}
