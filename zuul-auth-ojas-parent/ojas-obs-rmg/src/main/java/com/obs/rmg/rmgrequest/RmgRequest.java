package com.obs.rmg.rmgrequest;

import java.util.List;
import java.util.Set;

import com.obs.rmg.rmgmodel.EmployeeProjects;
import com.obs.rmg.rmgmodel.EmployeeSkills;
import com.obs.rmg.rmgmodel.HoursList;
import com.obs.rmg.rmgmodel.ProjectList;
import com.obs.rmg.rmgmodel.ProjectTasks;
import com.obs.rmg.rmgmodel.RMG;
import com.obs.rmg.rmgmodel.ResourceProjectTasks;
import com.obs.rmg.rmgmodel.RmgEmployeeList;
import com.obs.rmg.rmgmodel.RmgGeneric;
import com.obs.rmg.rmgmodel.RmgGenericResourceMap;
import com.obs.rmg.rmgmodel.RmgSpecific;
import com.obs.rmg.rmgmodel.SkillsList;


public class RmgRequest {
	
	private String transactiontype;
	
	private RMG rmgInfo;
	
	private Set<RmgSpecific> rmgSpecificList;
	
	private SkillsList skilllist;
	
	private Set<RmgGeneric> rmgGenericList;
		
	private Set<RmgGenericResourceMap> rmgGenericResourceList;
	
	private Set<ProjectTasks> projectTasks;
	
    private ProjectList projectlist;
   
   private RmgEmployeeList rmgemployeelist;
   
   private EmployeeSkills employeeskills;
   
   private EmployeeProjects employeeprojects;
   
   private List<HoursList> hourslist;
   
   private ResourceProjectTasks resourceprojecttasks;
   
  
	public ResourceProjectTasks getResourceprojecttasks() {
	return resourceprojecttasks;
}

public void setResourceprojecttasks(ResourceProjectTasks resourceprojecttasks) {
	this.resourceprojecttasks = resourceprojecttasks;
}

	public List<HoursList> getHourslist() {
	return hourslist;
}

public void setHourslist(List<HoursList> hourslist) {
	this.hourslist = hourslist;
}

	public ProjectList getProjectlist() {
		return projectlist;
	}

	public void setProjectlist(ProjectList projectlist) {
		this.projectlist = projectlist;
	}

	public String getTransactiontype() {
		return transactiontype;
	}

	public void setTransactiontype(String transactiontype) {
		this.transactiontype = transactiontype;
	}

	public RMG getRmgInfo() {
		return rmgInfo;
	}

	public void setRmgInfo(RMG rmgInfo) {
		this.rmgInfo = rmgInfo;
	}

	public Set<RmgSpecific> getRmgSpecificList() {
		return rmgSpecificList;
	}

	public void setRmgSpecificList(Set<RmgSpecific> rmgSpecificList) {
		this.rmgSpecificList = rmgSpecificList;
	}

	public SkillsList getSkilllist() {
		return skilllist;
	}

	public void setSkilllist(SkillsList skilllist) {
		this.skilllist = skilllist;
	}

	public Set<RmgGeneric> getRmgGenericList() {
		return rmgGenericList;
	}

	public void setRmgGenericList(Set<RmgGeneric> rmgGenericList) {
		this.rmgGenericList = rmgGenericList;
	}

	

	public Set<RmgGenericResourceMap> getRmgGenericResourceList() {
		return rmgGenericResourceList;
	}

	public void setRmgGenericResourceList(Set<RmgGenericResourceMap> rmgGenericResourceList) {
		this.rmgGenericResourceList = rmgGenericResourceList;
	}

	public RmgEmployeeList getRmgemployeelist() {
		return rmgemployeelist;
	}

	public void setRmgemployeelist(RmgEmployeeList rmgemployeelist) {
		this.rmgemployeelist = rmgemployeelist;
	}

	
	public EmployeeSkills getEmployeeskills() {
		return employeeskills;
	}

	public void setEmployeeskills(EmployeeSkills employeeskills) {
		this.employeeskills = employeeskills;
	}

	public EmployeeProjects getEmployeeprojects() {
		return employeeprojects;
	}

	public void setEmployeeprojects(EmployeeProjects employeeprojects) {
		this.employeeprojects = employeeprojects;
	}
	
	public Set<ProjectTasks> getProjectTasks() {
		return projectTasks;
	}

	public void setProjectTasks(Set<ProjectTasks> projectTasks) {
		this.projectTasks = projectTasks;
	}

}
