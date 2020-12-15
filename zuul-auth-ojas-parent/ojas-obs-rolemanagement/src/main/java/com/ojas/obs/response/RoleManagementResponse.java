package com.ojas.obs.response;

import java.util.List;

import com.ojas.obs.model.RoleManagement;

/**
 * 
 * @author asuneel
 *
 */
public class RoleManagementResponse {

	private List<RoleManagement> roleManagementList;

	private String Message;
	private String statusCode;
	public List<RoleManagement> getRoleManagementList() {
		return roleManagementList;
	}
	public void setRoleManagementList(List<RoleManagement> roleManagementList) {
		this.roleManagementList = roleManagementList;
	}
	public String getMessage() {
		return Message;
	}
	public void setMessage(String message) {
		Message = message;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Message == null) ? 0 : Message.hashCode());
		result = prime * result + ((roleManagementList == null) ? 0 : roleManagementList.hashCode());
		result = prime * result + ((statusCode == null) ? 0 : statusCode.hashCode());
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
		RoleManagementResponse other = (RoleManagementResponse) obj;
		if (Message == null) {
			if (other.Message != null)
				return false;
		} else if (!Message.equals(other.Message))
			return false;
		if (roleManagementList == null) {
			if (other.roleManagementList != null)
				return false;
		} else if (!roleManagementList.equals(other.roleManagementList))
			return false;
		if (statusCode == null) {
			if (other.statusCode != null)
				return false;
		} else if (!statusCode.equals(other.statusCode))
			return false;
		return true;
	}

	

}
