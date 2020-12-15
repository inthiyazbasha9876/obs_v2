package com.obs.rmg.rmgresponse;

import java.util.List;

import com.obs.rmg.rmgmodel.ProjectList;
import com.obs.rmg.rmgmodel.ResourceProjectTasks;

public class ProjectListResponse
{
	private String message;
	private String statusCode;
	private List<ProjectList> projectlist;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public List<ProjectList> getProjectlist() {
		return projectlist;
	}
	public void setProjectlist(List<ProjectList> projectlist) {
		this.projectlist = projectlist;
	}
	
}
